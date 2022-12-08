// 2kbot Java Edition，2kbot的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbot代码片段的用户：作者（Abjust）并不承担构建2kbot代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbot的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbot;

import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

public class Bread {
    // 建造面包厂
    public static void BuildFactory(long group) {
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            Statement cmd = msc.createStatement();
            String sql = String.format("SELECT COUNT(*) gid FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group);
            ResultSet rs = cmd.executeQuery(sql);
            if (!rs.isBeforeFirst()) {
                cmd.executeUpdate(String.format("INSERT INTO `%s`.`bread` (qid) VALUES (%s);", Global.database_name, group));
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("成功为本群建造面包厂！");
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群已经有面包厂了！");
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 给2kbot面包
    public static void Give(long group, long executor, int number) {
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            PreparedStatement cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
            ResultSet rs = cmd.executeQuery();
            try {
                while (rs.next()) {
                    if (rs.getInt("bread_diversity") == 0) {
                        if (number + rs.getInt("breads") <= (int) (32 * Math.pow(4, rs.getInt("factory_level") - 1) * Math.pow(2, rs.getInt("storage_upgraded")))) {
                            try (Connection msc1 = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                                Statement cmd1 = msc1.createStatement();
                                cmd1.executeUpdate(String.format("UPDATE `%s`.`bread` SET breads = %s WHERE gid = %s;", Global.database_name, rs.getInt("breads") + number, group));
                                rs.close();
                                cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
                                rs = cmd.executeQuery();
                                while (rs.next()) {
                                    MessageChain messageChain = new MessageChainBuilder()
                                            .append(new At(executor))
                                            .append(String.format(" 现在库存有 %s 块面包辣！", rs.getInt("breads")))
                                            .build();
                                    try {
                                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain);
                                    } catch (Exception ex) {
                                        System.out.println("群消息发送失败");
                                    }
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        } else {
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("抱歉，库存已经满了。。。");
                            } catch (Exception ex) {
                                System.out.println("群消息发送失败");
                            }
                        }
                    } else {
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("在开启多样化生产模式的情况下，你不能给2kbot面包！");
                        } catch (Exception ex) {
                            System.out.println("群消息发送失败");
                        }
                    }
                }
            } catch (Exception ex) {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群还没有面包厂！");
                } catch (Exception ex1) {
                    System.out.println("群消息发送失败");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 给我面包
    public static void Get(long group, long executor, int number) {
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            PreparedStatement cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
            ResultSet rs = cmd.executeQuery();
            try {
                while (rs.next()) {
                    if (rs.getInt("breads") >= number) {
                        if (rs.getInt("bread_diversity") == 1) {
                            List<String> bread_types = new ArrayList<>() {
                                {
                                    add("🍞");
                                    add("🥖");
                                    add("🥐");
                                    add("🥯");
                                    add("🍩");
                                }
                            };
                            if (number >= bread_types.size()) {
                                Random rnd = new Random();
                                int[] fields = new int[bread_types.size()];
                                int sum = 0;
                                for (int i = 0; i < fields.length - 1; i++) {
                                    fields[i] = rnd.nextInt(number);
                                    sum += fields[i];
                                }
                                int actualSum = sum * fields.length / (fields.length - 1);
                                sum = 0;
                                for (int i = 0; i < fields.length - 1; i++) {
                                    fields[i] = fields[i] * number / actualSum;
                                    sum += fields[i];
                                }
                                fields[fields.length - 1] = number - sum;
                                StringBuilder text = new StringBuilder();
                                for (int i = 0; i < bread_types.size(); i++) {
                                    if (i == 0) {
                                        text = new StringBuilder(String.format("\n%s*%s", bread_types.get(i), fields[i]));
                                    } else {
                                        text.append(String.format("\n%s*%s", bread_types.get(i), fields[i]));
                                    }
                                }
                                MessageChain messageChain = new MessageChainBuilder()
                                        .append(new At(executor))
                                        .append(text)
                                        .build();
                                try (Connection msc1 = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                                    Statement cmd1 = msc1.createStatement();
                                    cmd1.executeUpdate(String.format("UPDATE `%s`.`bread` SET breads = %s WHERE gid = %s;", Global.database_name, rs.getInt("breads") - number, group));
                                    try {
                                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain);
                                    } catch (Exception ex) {
                                        System.out.println("群消息发送失败");
                                    }
                                } catch (SQLException e) {
                                    throw new RuntimeException(e);
                                }
                            } else {
                                try {
                                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("你请求进货的面包数太少了！（至少要有 %s 块）", bread_types.size()));
                                } catch (Exception ex) {
                                    System.out.println("群消息发送失败");
                                }
                            }
                        } else {
                            try (Connection msc1 = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                                Statement cmd1 = msc1.createStatement();
                                cmd1.executeUpdate(String.format("UPDATE `%s`.`bread` SET breads = %s WHERE gid = %s;", Global.database_name, rs.getInt("breads") - number, group));
                                MessageChain messageChain = new MessageChainBuilder()
                                        .append(new At(executor))
                                        .append(String.format(" 🍞*%s", number))
                                        .build();
                                try {
                                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain);
                                } catch (Exception ex) {
                                    System.out.println("群消息发送失败");
                                }
                            } catch (SQLException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    } else {
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("抱歉，面包不够了。。。");
                        } catch (Exception ex) {
                            System.out.println("群消息发送失败");
                        }
                    }
                }
            } catch (Exception ex) {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群还没有面包厂！");
                } catch (Exception ex1) {
                    System.out.println("群消息发送失败");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 查询面包库存
    public static void Query(long group, long executor) {
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            PreparedStatement cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
            ResultSet rs = cmd.executeQuery();
            try {
                while (rs.next()) {
                    MessageChain messageChain = new MessageChainBuilder()
                            .append(new At(executor))
                            .append(String.format(" 现在库存有 %s 块面包，本群面包厂目前最多可储存 %s 块面包", rs.getInt("breads"), (int) (32 * Math.pow(4, rs.getInt("factory_level") - 1) * Math.pow(2, rs.getInt("storage_upgraded")))))
                            .build();
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain);
                    } catch (Exception ex1) {
                        System.out.println("群消息发送失败");
                    }
                }
            } catch (Exception ex) {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群还没有面包厂！");
                } catch (Exception ex1) {
                    System.out.println("群消息发送失败");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 多样化生产
    public static void Diversity(long group, int status) {
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            Statement cmd = msc.createStatement();
            String sql = String.format("SELECT COUNT(*) gid FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group);
            ResultSet rs = cmd.executeQuery(sql);
            if (rs.isBeforeFirst()) {
                rs.close();
                PreparedStatement cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
                rs = cmd1.executeQuery();
                if (rs.getInt("breads") == 0) {
                    if (status == 1) {
                        cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET bread_diversity = 1 WHERE gid = %s", Global.database_name, group));
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("已为本群启用多样化生产！");
                        } catch (Exception ex) {
                            System.out.println("群消息发送失败");
                        }
                    } else {
                        cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET bread_diversity = 0 WHERE gid = %s", Global.database_name, group));
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("已为本群禁用多样化生产！");
                        } catch (Exception ex) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你必须先清空库存，才能修改生产模式！");
                    } catch (Exception ex) {
                        System.out.println("群消息发送失败");
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群还没有面包厂！");
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 获取经验
    public static void GetExp(long group, long sender) {
        if (Global.blocklist == null || Global.blocklist.contains(String.format("%s_%s", group, sender)) && Global.g_blocklist == null || Global.g_blocklist.contains(Long.toString(sender))) {
            try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                Statement cmd = msc.createStatement();
                String sql = String.format("SELECT COUNT(*) gid FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group);
                ResultSet rs = cmd.executeQuery(sql);
                if (rs.isBeforeFirst()) {
                    rs.close();
                    PreparedStatement cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
                    rs = cmd1.executeQuery();
                    while (rs.next()) {
                        int maxexp_formula = (int) (300 * Math.pow(2, rs.getInt("factory_level") - 1));
                        Global.time_now = Instant.now().getEpochSecond();
                        if (Global.time_now - rs.getLong("last_expfull") >= 86400) {
                            if (Global.time_now - rs.getLong("last_expgain") >= 86400) {
                                cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET exp_gained_today = 0, last_expgain = %s WHERE gid = %s", Global.database_name, Global.time_now, group));
                                if (rs.getInt("exp_gained_today") <= maxexp_formula) {
                                    cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET factory_exp = %s, exp_gained_today = %s WHERE gid = %s;", Global.database_name, rs.getInt("factory_exp") + 1, rs.getInt("exp_gained_today") + 1, group));
                                } else {
                                    cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET last_expfull = %s, exp_gained_today = 0 WHERE gid =%s;", Global.database_name, Global.time_now, group));
                                    try {
                                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群已达到今日获取经验上限！");
                                    } catch (Exception ex) {
                                        System.out.println("群消息发送失败");
                                    }
                                }
                            } else {
                                cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET exp_gained_today = 0, last_expgain = %s WHERE gid = %s", Global.database_name, Global.time_now, group));
                                if (rs.getInt("exp_gained_today") <= maxexp_formula) {
                                    cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET factory_exp = %s, exp_gained_today = %s WHERE gid = %s;", Global.database_name, rs.getInt("factory_exp") + 1, rs.getInt("exp_gained_today") + 1, group));
                                } else {
                                    cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET last_expfull = %s, exp_gained_today = 0 WHERE gid =%s;", Global.database_name, Global.time_now, group));
                                    try {
                                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群已达到今日获取经验上限！");
                                    } catch (Exception ex) {
                                        System.out.println("群消息发送失败");
                                    }
                                }
                            }
                        }
                    }
                } else {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群还没有面包厂！");
                    } catch (Exception ex) {
                        System.out.println("群消息发送失败");
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // 升级工厂
    public static void UpgradeFactory(long group) {
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            Statement cmd = msc.createStatement();
            String sql = String.format("SELECT COUNT(*) gid FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group);
            ResultSet rs = cmd.executeQuery(sql);
            if (rs.isBeforeFirst()) {
                rs.close();
                PreparedStatement cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
                rs = cmd1.executeQuery();
                while (rs.next()) {
                    int exp_formula = (int) (900 * Math.pow(2, rs.getInt("factory_level") - 1));
                    if (rs.getInt("factory_level") < Global.breadfactory_maxlevel) {
                        if (rs.getInt("factory_exp") >= exp_formula) {
                            cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET factory_level = %s, factory_exp = %s WHERE gid = %s", Global.database_name, rs.getInt("factory_level") + 1, rs.getInt("factory_level") - exp_formula, group));
                            rs.close();
                            cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
                            rs = cmd1.executeQuery();
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("恭喜，本群面包厂升级成功辣！现在面包厂等级是 %s 级", rs.getInt("factory_level")));
                            } catch (Exception ex) {
                                System.out.println("群消息发送失败");
                            }
                        } else {
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("很抱歉，目前本群还需要 %s 经验才能升级", exp_formula - rs.getInt("factory_exp")));
                            } catch (Exception ex) {
                                System.out.println("群消息发送失败");
                            }
                        }
                    } else {
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群面包厂已经满级了！（tips：可以输入/upgrade_storage来升级库存）");
                        } catch (Exception ex) {
                            System.out.println("群消息发送失败");
                        }
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群还没有面包厂！");
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    // 升级库存
    public static void UpgradeStorage(long group) {
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            Statement cmd = msc.createStatement();
            String sql = String.format("SELECT COUNT(*) gid FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group);
            ResultSet rs = cmd.executeQuery(sql);
            if (rs.isBeforeFirst()) {
                rs.close();
                PreparedStatement cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
                rs = cmd1.executeQuery();
                while (rs.next()) {
                    if (rs.getInt("factory_level") == Global.breadfactory_maxlevel) {
                        if (rs.getInt("factory_exp") >= 2000) {
                            cmd.executeUpdate(String.format("UPDATE `%s`.`bread` SET storage_upgraded = %s, factory_exp = %s WHERE gid = %s;", Global.database_name, rs.getInt("storage_upgraded") + 1, rs.getInt("factory_exp") - 2000, group));
                            rs.close();
                            cmd1 = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s;", Global.database_name, group));
                            rs = cmd1.executeQuery();
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("恭喜，本群面包厂库存升级成功辣！现在面包厂可以储存 %s 块面包", (int) (32 * Math.pow(4, rs.getInt("factory_level") - 1) * Math.pow(2, rs.getInt("storage_upgraded")))));
                            } catch (Exception ex) {
                                System.out.println("群消息发送失败");
                            }
                        } else {
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("很抱歉，目前本群还需要 %s 经验才能升级", 2000 - rs.getInt("factory_exp")));
                            } catch (Exception ex) {
                                System.out.println("群消息发送失败");
                            }
                        }
                    } else {
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("本群面包厂尚未满级！（tips：面包厂满级为 %s 级）", Global.breadfactory_maxlevel));
                        } catch (Exception ex) {
                            System.out.println("群消息发送失败");
                        }
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("本群还没有面包厂！");
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
