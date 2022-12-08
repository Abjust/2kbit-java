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
    public static List<String> group_ids;
    public static int speed;
    public static void BreadProduce(){
        List<String> groupids = new ArrayList<>();
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            PreparedStatement cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread`", Global.database_name));
            ResultSet rs = cmd.executeQuery();
            while (rs.next()) {
                String groupid = rs.getString("gid");
                groupids.add(groupid);
                group_ids = groupids;
            }
            rs.close();
            while (true) {
                Global.time_now = Instant.now().getEpochSecond();
                if (group_ids != null) {
                    for (String group_id : group_ids) {
                        cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s", Global.database_name, group_id));
                        rs = cmd.executeQuery();
                        while (rs.next()) {
                            int formula = (int) Math.ceil(Math.pow(4, rs.getInt("factory_level")) * Math.pow(0.25, rs.getInt("bread_diversity")));
                            speed = 300 - (20 * (rs.getInt("factory_level") - 1));
                            int maxstorage = (int) (32 * Math.pow(4, rs.getInt("factory_level") - 1) * Math.pow(2, rs.getInt("storage_upgraded")));
                            Random r = new Random();
                            int random = r.nextInt(1, formula);
                            if (Global.time_now - rs.getLong("last_produce") >= speed) {
                                rs.close();
                                cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`bread` WHERE gid = %s", Global.database_name, group_id));
                                rs = cmd.executeQuery();
                                Statement cmd1 = msc.createStatement();
                                if (rs.getInt("breads") + random < maxstorage) {
                                    cmd1.executeUpdate(String.format("UPDATE bread SET breads = %s WHERE gid = %s;", rs.getInt("breads") + random, group_id));
                                } else if (rs.getInt("breads") + random >= maxstorage) {
                                    cmd1.executeUpdate(String.format("UPDATE bread SET breads = %s WHERE gid = %s;", maxstorage, group_id));
                                }
                                cmd1.executeUpdate(String.format("UPDATE bread SET last_produce = %s WHERE gid = %s;", Instant.now().getEpochSecond(), group_id));
                            }
                        }
                    }
                }
                rs.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
