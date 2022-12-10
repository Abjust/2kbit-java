// 2kbot Java Edition，2kbot的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbot代码片段的用户：作者（Abjust）并不承担构建2kbot代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbot的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.ContactList;
import net.mamoe.mirai.contact.NormalMember;

import java.sql.*;
import java.util.Objects;

public class Admin {
    // 禁言功能
    public static void Mute(long executor, long victim, long group, String permission, int minutes) {
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.ops != null && !Global.ops.contains(String.format("%s_%s", group, victim)) || Global.g_ops != null && !Global.g_ops.contains(Long.toString(victim))) {
                try {
                    Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).get(victim)).mute(minutes * 60);
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已尝试将 %s 禁言 %s 分钟", victim, minutes));
                    } catch (Exception ex1) {
                        System.out.println("群消息发送失败");
                    }
                } catch (Exception ex) {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("执行失败！请检查2kbot是否具备足够权限。。。");
                    } catch (Exception ex1) {
                        System.out.println("执行失败");
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("此人是机器人管理员，无法禁言");
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            }
        } else if (!permission.equals("OWNER")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员，但你是本群群主（你应该使用/op指令将自己设置为本群机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 解禁功能
    public static void Unmute(long executor, long victim, long group, String permission) {
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            try {
                Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).get(victim)).unmute();
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已尝试将 %s 解除禁言", victim));
                } catch (Exception ex1) {
                    System.out.println("群消息发送失败");
                }
            } catch (Exception ex) {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("执行失败！请检查2kbot是否具备足够权限。。。");
                } catch (Exception ex1) {
                    System.out.println("执行失败");
                }
            }
        } else if (!permission.equals("OWNER")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员，但你是本群群主（你应该使用/op指令将自己设置为本群机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 踢人功能
    public static void Kick(long executor, long victim, long group, String permission) {
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.ops != null && !Global.ops.contains(String.format("%s_%s", group, victim)) || Global.g_ops != null && !Global.g_ops.contains(Long.toString(victim))) {
                try {
                    Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).get(victim)).kick("");
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已尝试将 %s 踢出", victim));
                    } catch (Exception ex1) {
                        System.out.println("群消息发送失败");
                    }
                } catch (Exception ex) {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("执行失败！请检查2kbot是否具备足够权限。。。");
                    } catch (Exception ex1) {
                        System.out.println("执行失败");
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("此人是机器人管理员，无法踢出");
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            }
        } else if (!permission.equals("OWNER")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员，但你是本群群主（你应该使用/op指令将自己设置为本群机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 加黑功能
    public static void Block(long executor, long victim, long group, String permission) {
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.ops != null && !Global.ops.contains(String.format("%s_%s", group, victim)) || Global.g_ops != null && !Global.g_ops.contains(Long.toString(victim))) {
                if (!Global.blocklist.contains(Long.toString(victim))) {
                    try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                        Statement cmd = msc.createStatement();
                        cmd.executeUpdate(String.format("INSERT INTO `%s`.`blocklist` (qid,gid) VALUES (%s,%s);", Global.database_name, victim, group));
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已将 %s 加入本群黑名单", victim));
                        } catch (Exception ex1) {
                            System.out.printf("已将 %s 加入 %s 黑名单%n", victim, group);
                        }
                        Update.Execute();
                        try {
                            Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).get(victim)).kick("");
                        } catch (Exception ex2) {
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("在尝试将黑名单对象踢出时执行失败！");
                            } catch (Exception ex) {
                                System.out.println("在尝试将黑名单对象踢出时执行失败");
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 已经在本群黑名单内", victim));
                    } catch (Exception ex) {
                        System.out.printf("%s 已经在 %s 黑名单内%n", victim, group);
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 是机器人管理员，不能加黑", victim));
                } catch (Exception ex) {
                    System.out.printf("%s 是机器人管理员，不能加黑%n", victim);
                }
            }
        } else if (!permission.equals("OWNER")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员，但你是本群群主（你应该使用/op指令将自己设置为本群机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 解黑功能
    public static void Unblock(long executor, long victim, long group, String permission) {
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.blocklist.contains(Long.toString(victim))) {
                try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                    Statement cmd = msc.createStatement();
                    cmd.executeUpdate(String.format("DELETE FROM `%s`.`blocklist` WHERE qid = %s AND gid = %s;", Global.database_name, victim, group));
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已将 %s 移出本群黑名单", victim));
                    } catch (Exception ex1) {
                        System.out.printf("已将 %s 移出 %s 黑名单%n", victim, group);
                    }
                    Update.Execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 不在本群黑名单内", victim));
                } catch (Exception ex) {
                    System.out.printf("%s 不在 %s 黑名单内%n", victim, group);
                }
            }
        } else if (!permission.equals("OWNER")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员，但你是本群群主（你应该使用/op指令将自己设置为本群机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 全局加黑功能
    public static void G_Block(long executor, long victim, long group) {
        if (Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (!Global.g_ops.contains(Long.toString(victim))) {
                if (!Global.g_blocklist.contains(Long.toString(victim))) {
                    try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                        Statement cmd = msc.createStatement();
                        cmd.executeUpdate(String.format("INSERT INTO `%s`.`g_blocklist` (qid) VALUES (%s);", Global.database_name, victim));
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已将 %s 加入全局黑名单", victim));
                        } catch (Exception ex1) {
                            System.out.printf("已将 %s 加入全局黑名单%n", victim);
                        }
                        Update.Execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 已经在全局黑名单内", victim));
                    } catch (Exception ex1) {
                        System.out.printf("%s 已经在全局黑名单内%n", victim);
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 是全局机器人管理员，不能全局加黑", victim));
                } catch (Exception ex1) {
                    System.out.printf("%s 是全局机器人管理员，不能全局加黑%n", victim);
                }
            }
        } else if (victim != Global.owner_qq) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员，但你是机器人主人（你应该使用/gop指令将自己设置为全局机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 全局解黑功能
    public static void G_Unblock(long executor, long victim, long group) {
        if (Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.g_blocklist.contains(Long.toString(victim))) {
                try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                    Statement cmd = msc.createStatement();
                    cmd.executeUpdate(String.format("DELETE FROM `%s`.`g_blocklist` WHERE qid = %s;", Global.database_name, victim));
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已将 %s 移出全局黑名单", victim));
                    } catch (Exception ex1) {
                        System.out.printf("已将 %s 移出全局黑名单%n", victim);
                    }
                    Update.Execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 不在全局黑名单内", victim));
                } catch (Exception ex) {
                    System.out.printf("%s 不在全局黑名单内%n", victim);
                }
            }
        } else if (victim != Global.owner_qq) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员，但你是机器人主人（你应该使用/gop指令将自己设置为全局机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 给OP功能
    public static void Op(long executor, long victim, long group, String permission) {
        if (permission.equals("OWNER") || Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.ops == null || !Global.ops.contains(String.format("%s_%s", group, victim))) {
                try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                    Statement cmd = msc.createStatement();
                    cmd.executeUpdate(String.format("INSERT INTO `%s`.`ops` (qid,gid) VALUES (%s,%s);", Global.database_name, victim, group));
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已将 %s 设置为本群机器人管理员", victim));
                    } catch (Exception ex1) {
                        System.out.printf("已将 %s 设置为 %s 机器人管理员%n", victim, group);
                    }
                    Update.Execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 已经是本群机器人管理员", victim));
                } catch (Exception ex) {
                    System.out.printf("%s 已经是 %s 机器人管理员%n", victim, group);
                }
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员或者本群群主");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 取消OP功能
    public static void Deop(long executor, long victim, long group) {
        ContactList<NormalMember> memberList = Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).getMembers());
        long group_owner = 0;
        for (NormalMember member : memberList) {
            if (member.getPermission().toString().equals("OWNER")) {
                group_owner = member.getId();
            }
        }
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, victim))) {
                if (victim != group_owner) {
                    try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                        Statement cmd = msc.createStatement();
                        cmd.executeUpdate(String.format("DELETE FROM `%s`.`ops` WHERE qid = %s AND gid = %s;", Global.database_name, victim, group));
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已取消 %s 在本群的机器人管理员权限", victim));
                        } catch (Exception ex1) {
                            System.out.printf("已取消 %s 在 %s 的机器人管理员权限%n", victim, group);
                        }
                        Update.Execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 是本群群主，不能被取消本群机器人管理员", victim));
                    } catch (Exception ex1) {
                        System.out.printf("%s 是 %s 群主，不能被取消 %s 机器人管理员%n", victim, group, group);
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 不是本群机器人管理员", victim));
                } catch (Exception ex) {
                    System.out.printf("%s 不是 %s 机器人管理员%n", victim, group);
                }
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 给全局OP功能
    public static void G_Op(long executor, long victim, long group) {
        if (executor == Global.owner_qq || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (!Global.g_ops.contains(Long.toString(victim))) {
                try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                    Statement cmd = msc.createStatement();
                    cmd.executeUpdate(String.format("INSERT INTO `%s`.`g_ops` (qid) VALUES (%s);", Global.database_name, victim));
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已将 %s 设置为全局机器人管理员", victim));
                    } catch (Exception ex1) {
                        System.out.printf("已将 %s 设置为全局机器人管理员%n", victim);
                    }
                    Update.Execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 已经是全局机器人管理员", victim));
                } catch (Exception ex) {
                    System.out.printf("%s 已经是全局机器人管理员%n", victim);
                }
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员或者机器人主人");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 取消全局OP功能
    public static void G_Deop(long executor, long victim, long group) {
        if (Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.g_ops.contains(Long.toString(victim))) {
                if (victim != Global.owner_qq) {
                    try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                        Statement cmd = msc.createStatement();
                        cmd.executeUpdate(String.format("DELETE FROM `%s`.`g_ops` WHERE qid = %s", Global.database_name, victim));
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已取消 %s 的全局机器人管理员权限", victim));
                        } catch (Exception ex1) {
                            System.out.printf("已取消 %s 的全局机器人管理员权限%n", victim);
                        }
                        Update.Execute();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 是机器人主人，不能被取消全局机器人管理员", victim));
                    } catch (Exception ex1) {
                        System.out.printf("%s 是机器人主人，不能被取消全局机器人管理员%n", victim);
                    }
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 不是全局机器人管理员", victim));
                } catch (Exception ex) {
                    System.out.printf("%s 不是全局机器人管理员%n", victim);
                }
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 屏蔽消息功能
    public static void Ignore(long executor, long victim, long group, String permission) {
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.ignores == null || !Global.ignores.contains(String.format("%s_%s", group, victim))) {
                try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                    Statement cmd = msc.createStatement();
                    cmd.executeUpdate(String.format("INSERT INTO `%s`.`ignores` (qid,gid) VALUES (%s,%s);", Global.database_name, victim, group));
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已在本群屏蔽 %s 的消息", victim));
                    } catch (Exception ex1) {
                        System.out.printf("已在 %s 屏蔽 %s 的消息%n", victim, group);
                    }
                    Update.Execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 的消息已经在本群被机器人屏蔽了", victim));
                } catch (Exception ex1) {
                    System.out.printf("%s 的消息已经在 %s 被机器人屏蔽了%n", victim, group);
                }
            }
        } else if (!permission.equals("OWNER")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员，但你是本群群主（你应该使用/op指令将自己设置为本群机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 全局屏蔽消息功能
    public static void G_Ignore(long executor, long victim, long group) {
        if (Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.g_ignores == null || !Global.g_ignores.contains(Long.toString(victim))) {
                try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
                    Statement cmd = msc.createStatement();
                    cmd.executeUpdate(String.format("INSERT INTO `%s`.`g_ignores` (qid) VALUES (%s);", Global.database_name, victim));
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("已屏蔽 %s 在所有群的消息", victim));
                    } catch (Exception ex1) {
                        System.out.printf("已屏蔽 %s 在所有群的消息", victim);
                    }
                    Update.Execute();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("%s 的消息已经在所有群被机器人屏蔽了", victim));
                } catch (Exception ex1) {
                    System.out.printf("%s 的消息已经在所有群被机器人屏蔽了", victim);
                }
            }
        } else if (victim != Global.owner_qq) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是全局机器人管理员，但你是机器人主人（你应该使用/gop指令将自己设置为全局机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 带 清 洗
    public static void Purge(long executor, long group, String permission) {
        if (Global.ops != null && Global.ops.contains(String.format("%s_%s", group, executor)) || Global.g_ops != null && Global.g_ops.contains(Long.toString(executor))) {
            if (Global.g_blocklist != null) {
                for (int i = 0; i < Global.g_blocklist.size(); i++) {
                    try {
                        Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).get(Long.parseLong(Global.g_blocklist.get(i)))).kick("");
                    } catch (Exception ignored) {
                    }
                }
            }
            if (Global.blocklist != null) {
                for (int i = 0; i < Global.blocklist.size(); i++) {
                    String[] blocklist = Global.blocklist.get(i).split("_");
                    if (Long.parseLong(blocklist[0]) == group) {
                        try {
                            Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).get(Long.parseLong(blocklist[1]))).kick("");
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("带清洗发动成功！");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else if (!permission.equals("OWNER")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("你不是机器人管理员，但你是本群群主（你应该使用/op指令将自己设置为本群机器人管理员）");
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }
    // 禁言自己
    public static void MuteMe(long executor, long group, int minutes) {
        try {
            Objects.requireNonNull(Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).get(executor)).mute(minutes * 60);
        } catch (Exception ex) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("执行失败");
            } catch (Exception ex1) {
                System.out.println("群消息发送失败");
            }
        }
    }
}
