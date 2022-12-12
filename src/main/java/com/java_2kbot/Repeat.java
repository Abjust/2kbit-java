// 2kbot Java Edition，2kbot的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbot代码片段的用户：作者（Abjust）并不承担构建2kbot代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbot的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.*;

import java.sql.*;
import java.time.Instant;
import java.util.Objects;

public class Repeat {
    public static void Execute(long group, long executor, MessageChain messageChain) {
        String[] repeatwords =
                {
                        "114514",
                        "1919810",
                        "1145141919810",
                        "ccc",
                        "c",
                        "草",
                        "tcl",
                        "?",
                        "。",
                        "？",
                        "e",
                        "额",
                        "呃",
                        "6",
                        "666",
                        "faceid:178",
                        "faceid:14",
                        "faceid:277",
                        "faceid:298"
                };
        // 复读机
        if (messageChain.contentToString().startsWith("!echo")) {
            String[] result = messageChain.contentToString().split(" ");
            if (result.length > 1) {
                try {
                    StringBuilder results = new StringBuilder();
                    if ((Global.ignores == null || !Global.ignores.contains(String.format("%s_%s", group, executor)) && (Global.g_ignores == null || !Global.g_ignores.contains(Long.toString(executor))))) {
                        for (int i = 1; i < result.length; i++) {
                            if (i == 1) {
                                results = new StringBuilder(result[i]);
                            } else {
                                results.append(" ").append(result[i]);
                            }
                        }
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.valueOf(results));
                        } catch (Exception ex) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } catch (Exception ex) {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("油饼食不食？");
                    } catch (Exception e1) {
                        System.out.println("群消息发送失败");
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你个sb难道没发觉到少了些什么？");
                } catch (Exception e1) {
                    System.out.println("群消息发送失败");
                }
            }
        }
        // 主动复读
        else {
            for (String item : repeatwords) {
                if (item.contains("faceid:")) {
                    String face_id = item.replace("faceid:","");
                    if (messageChain.serializeToMiraiCode().equals(String.format("[mirai:face:%s]", face_id))) {
                        Global.time_now = Instant.now().getEpochSecond();
                        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                            Statement cmd = msc.createStatement();
                            String sql = String.format("SELECT COUNT(*) gid FROM `%s`.`repeatctrl` WHERE gid = %s;", Global.database_name, group);
                            ResultSet rs = cmd.executeQuery(sql);
                            if (!rs.isBeforeFirst()) {
                                cmd.executeUpdate(String.format("INSERT INTO `%s`.`repeatctrl` (qid,gid) VALUES (%s,%s);", Global.database_name, executor, group));
                            }
                            rs.close();
                            PreparedStatement cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`repeatctrl` WHERE gid = %s;", Global.database_name, group));
                            rs = cmd1.executeQuery();
                            while (rs.next()) {
                                if (Global.time_now - rs.getLong("last_repeatctrl") >= Global.repeat_cd) {
                                    if (Global.time_now - rs.getLong("last_repeat") <= Global.repeat_interval) {
                                        if (rs.getInt("repeat_count") <= Global.repeat_threshold) {
                                            cmd.executeUpdate(String.format("UPDATE `%s`.`repeatctrl` SET last_repeat = %s, repeat_count = %s WHERE qid = %s AND gid = %s;", Global.database_name, Global.time_now, rs.getInt("repeat_count") + 1, executor, group));
                                            try {
                                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain.get(1));
                                            } catch (Exception e1) {
                                                System.out.println("群消息发送失败");
                                            }
                                            break;
                                        } else {
                                            MessageChain messageChain1 = new MessageChainBuilder()
                                                    .append(new At(executor))
                                                    .append(String.format(" 你话太多了（恼）（你的消息将在 %s 秒内不被复读）", Global.repeat_cd))
                                                    .build();
                                            try {
                                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain1);
                                            } catch (Exception e1) {
                                                System.out.println("群消息发送失败");
                                            }
                                            break;
                                        }
                                    } else {
                                        cmd.executeUpdate(String.format("UPDATE `%s`.`repeatctrl` SET repeat_count = 1, last_repeat = %s WHERE qid = %s AND gid = %s;", Global.database_name, Global.time_now, executor, group));
                                        try {
                                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain.get(1));
                                        } catch (Exception e1) {
                                            System.out.println("群消息发送失败");
                                        }
                                        break;
                                    }
                                }
                            }
                        } catch (SQLException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if (item.equals(messageChain.contentToString())) {
                    Global.time_now = Instant.now().getEpochSecond();
                    try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                        Statement cmd = msc.createStatement();
                        String sql = String.format("SELECT COUNT(*) gid FROM `%s`.`repeatctrl` WHERE gid = %s;", Global.database_name, group);
                        ResultSet rs = cmd.executeQuery(sql);
                        if (!rs.isBeforeFirst()) {
                            cmd.executeUpdate(String.format("INSERT INTO `%s`.`repeatctrl` (qid,gid) VALUES (%s,%s);", Global.database_name, executor, group));
                        }
                        rs.close();
                        PreparedStatement cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`repeatctrl` WHERE gid = %s;", Global.database_name, group));
                        rs = cmd1.executeQuery();
                        while (rs.next()) {
                            if (Global.time_now - rs.getLong("last_repeatctrl") >= Global.repeat_cd) {
                                if (Global.time_now - rs.getLong("last_repeat") <= Global.repeat_interval) {
                                    if (rs.getInt("repeat_count") <= Global.repeat_threshold) {
                                        cmd.executeUpdate(String.format("UPDATE `%s`.`repeatctrl` SET last_repeat = %s, repeat_count = %s WHERE qid = %s AND gid = %s;", Global.database_name, Global.time_now, rs.getInt("repeat_count") + 1, executor, group));
                                        try {
                                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain.contentToString());
                                        } catch (Exception e1) {
                                            System.out.println("群消息发送失败");
                                        }
                                        break;
                                    } else {
                                        MessageChain messageChain1 = new MessageChainBuilder()
                                                .append(new At(executor))
                                                .append(String.format(" 你话太多了（恼）（你的消息将在 %s 秒内不被复读）", Global.repeat_cd))
                                                .build();
                                        try {
                                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain1);
                                        } catch (Exception e1) {
                                            System.out.println("群消息发送失败");
                                        }
                                        break;
                                    }
                                } else {
                                    cmd.executeUpdate(String.format("UPDATE `%s`.`repeatctrl` SET repeat_count = 1, last_repeat = %s WHERE qid = %s AND gid = %s;", Global.database_name, Global.time_now, executor, group));
                                    try {
                                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain.contentToString());
                                    } catch (Exception e1) {
                                        System.out.println("群消息发送失败");
                                    }
                                    break;
                                }
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}
