// 2kbit Java Edition，2kbit的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbit代码片段的用户：作者（Abjust）并不承担构建2kbit代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbit的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbit;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Update {
    public static void Execute() {
        String a;
        String b;
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            // 更新op列表
            PreparedStatement cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`ops` WHERE qid IS NOT NULL AND gid IS NOT NULL", Global.database_name));
            ResultSet rs = cmd.executeQuery();
            List<String> ops = new ArrayList<>();
            while (rs.next()) {
                a = rs.getString("gid");
                b = rs.getString("qid");
                ops.add(String.format("%s_%s", a, b));
                Global.ops = ops;
            }
            rs.close();
            // 更新黑名单
            cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`blocklist` WHERE qid IS NOT NULL AND gid IS NOT NULL", Global.database_name));
            rs = cmd.executeQuery();
            List<String> blocklist = new ArrayList<>();
            while (rs.next()) {
                a = rs.getString("gid");
                b = rs.getString("qid");
                ops.add(String.format("%s_%s", a, b));
                Global.blocklist = blocklist;
            }
            rs.close();
            // 更新屏蔽列表
            cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`ignores` WHERE qid IS NOT NULL AND gid IS NOT NULL", Global.database_name));
            rs = cmd.executeQuery();
            List<String> ignores = new ArrayList<>();
            while (rs.next()) {
                a = rs.getString("gid");
                b = rs.getString("qid");
                ops.add(String.format("%s_%s", a, b));
                Global.ignores = ignores;
            }
            rs.close();
            // 更新全局op列表
            cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`g_ops` WHERE qid IS NOT NULL", Global.database_name));
            rs = cmd.executeQuery();
            List<String> g_ops = new ArrayList<>();
            while (rs.next()) {
                a = rs.getString("qid");
                g_ops.add(a);
                Global.g_ops = g_ops;
            }
            rs.close();
            // 更新全局黑名单
            cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`g_blocklist` WHERE qid IS NOT NULL", Global.database_name));
            rs = cmd.executeQuery();
            List<String> g_blocklist = new ArrayList<>();
            while (rs.next()) {
                a = rs.getString("qid");
                g_blocklist.add(a);
                Global.g_blocklist = g_blocklist;
            }
            rs.close();
            // 更新全局屏蔽列表
            cmd = msc.prepareStatement(String.format("SELECT * FROM `%s`.`g_ignores` WHERE qid IS NOT NULL", Global.database_name));
            rs = cmd.executeQuery();
            List<String> g_ignores = new ArrayList<>();
            while (rs.next()) {
                a = rs.getString("qid");
                g_ignores.add(a);
                Global.g_ignores = g_ignores;
            }
            rs.close();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
