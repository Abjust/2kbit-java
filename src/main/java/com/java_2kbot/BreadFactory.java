// 2kbot Java Edition，2kbot的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbot代码片段的用户：作者（Abjust）并不承担构建2kbot代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbot的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbot;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.util.Random;

public class BreadFactory {
    public static List<String> group_ids1;
    public static List<String> group_ids2;
    public static int speed1;
    public static int speed2;

    public static void MaterialProduce() {
        List<String> groupids = new ArrayList<>();
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            PreparedStatement cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`material`", Global.database_name));
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                String groupid = rs.getString("gid");
                groupids.add(groupid);
                group_ids1 = groupids;
            }
            rs.close();
            while (true) {
                Global.time_now = Instant.now().getEpochSecond();
                if (group_ids1 != null) {
                    for (String group_id : group_ids1) {
                        cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s", Global.database_name, group_id));
                        rs = cmd.executeQuery();
                        while (rs.next()) {
                            int formula = (int) Math.pow(4, rs.getInt("factory_level"));
                            int maxstorage = (int) (32 * Math.pow(4, rs.getInt("factory_level") - 1) * Math.pow(2, rs.getInt("storage_upgraded")));
                            boolean is_full = rs.getInt("breads") == maxstorage;
                            speed1 = 300 - (20 * (rs.getInt("factory_level") - 1));
                            if (rs.getInt("bread_diversity") != 2 && !is_full) {
                                Random r = new Random();
                                int random = r.nextInt(1, formula);
                                rs.close();
                                cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`material` WHERE gid = %s", Global.database_name, group_id));
                                rs = cmd.executeQuery();
                                while (rs.next()) {
                                    if (Global.time_now - rs.getLong("last_produce") >= speed1) {
                                        Statement cmd1 = msc.createStatement();
                                        cmd1.executeUpdate(String.format("UPDATE material SET flour = %s, egg = %s, yeast = %s, last_produce = %s WHERE gid = %s;", rs.getInt("flour") + random * 5, rs.getInt("egg") + random * 2, rs.getInt("yeast") + random, Global.time_now, group_id));
                                    }
                                }
                                rs.close();
                            }
                        }
                        rs.close();
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void BreadProduce() {
        List<String> groupids = new ArrayList<>();
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            PreparedStatement cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread`", Global.database_name));
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                String groupid = rs.getString("gid");
                groupids.add(groupid);
                group_ids2 = groupids;
            }
            rs.close();
            while (true) {
                Global.time_now = Instant.now().getEpochSecond();
                if (group_ids2 != null) {
                    for (String group_id : group_ids2) {
                        cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s", Global.database_name, group_id));
                        rs = cmd.executeQuery();
                        while (rs.next()) {
                            if (rs.getInt("bread_diversity") != 2) {
                                speed2 = 300 - (20 * (rs.getInt("factory_level") - 1));
                                int maxstorage = (int) (32 * Math.pow(4, rs.getInt("factory_level") - 1) * Math.pow(2, rs.getInt("storage_upgraded")));
                                int bread_diversity = rs.getInt("bread_diversity");
                                rs.close();
                                int random = 0;
                                Random r = new Random();
                                cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`material` WHERE gid = %s", Global.database_name, group_id));
                                rs = cmd.executeQuery();
                                while (rs.next()) {
                                    if (Math.floor(rs.getInt("yeast") / Math.pow(4, bread_diversity)) == 1) {
                                        random = 1;
                                    }
                                    else if (rs.getInt("yeast") > 1) {
                                        random = r.nextInt(1, (int)Math.floor(rs.getInt("yeast") / Math.pow(4, bread_diversity)));
                                    }
                                    rs.close();
                                    cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s", Global.database_name, group_id));
                                    rs = cmd.executeQuery();
                                    while (rs.next()) {
                                        if (Global.time_now - rs.getLong("last_produce") >= speed2) {
                                            rs.close();
                                            cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s", Global.database_name, group_id));
                                            rs = cmd.executeQuery();
                                            Statement cmd1 = msc.createStatement();
                                            while (rs.next()) {
                                                if (rs.getInt("breads") + random < maxstorage) {
                                                    cmd1.executeUpdate(String.format("UPDATE `%s`.`bread` SET breads = %s WHERE gid = %s;", Global.database_name, rs.getInt("breads") + random, group_id));
                                                    cmd1.executeUpdate(String.format("UPDATE `%s`.`material` SET flour = %s, egg = %s, yeast = %s WHERE gid = %s;", Global.database_name, rs.getInt("flour") - random * 5 * Math.pow(4, bread_diversity), rs.getInt("egg") - random * 2 * Math.pow(4, bread_diversity), rs.getInt("yeast") - random * Math.pow(4, bread_diversity), group_id));
                                                } else if (rs.getInt("breads") + random >= maxstorage) {
                                                    int difference = maxstorage - rs.getInt("breads");
                                                    cmd1.executeUpdate(String.format("UPDATE `%s`.`bread` SET breads = %s WHERE gid = %s;", Global.database_name, maxstorage, group_id));
                                                    cmd1.executeUpdate(String.format("UPDATE `%s`.`material` SET flour = %s, egg = %s, yeast = %s WHERE gid = %s;", Global.database_name, rs.getInt("flour") - difference * 5 * Math.pow(4, bread_diversity), rs.getInt("egg") - difference * 2 * Math.pow(4, bread_diversity), rs.getInt("yeast") - difference * Math.pow(4, bread_diversity), group_id));
                                                }
                                                cmd1.executeUpdate(String.format("UPDATE `%s`.`bread` SET last_produce = %s WHERE gid = %s;", Global.database_name, Instant.now().getEpochSecond(), group_id));
                                                rs.close();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}