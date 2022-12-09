// 2kbot Java Edition，2kbot的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbot代码片段的用户：作者（Abjust）并不承担构建2kbot代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbot的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbot;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

import java.sql.*;

public final class Java2kbot extends JavaPlugin {
    public static final Java2kbot INSTANCE = new Java2kbot();

    public Java2kbot() {
        super(new JvmPluginDescriptionBuilder("com.java_2kbot", "1.0.3")
                .name("2kbot Java Edition")
                .author("Abjust")
                .build());
    }

    @Override
    public void onEnable() {
        // 初始化全局变量
        reloadPluginConfig(SetGlobal.MyConfig.INSTANCE);
        Global.owner_qq = SetGlobal.MyConfig.INSTANCE.getOwner_qq();
        Global.bot_qq = SetGlobal.MyConfig.INSTANCE.getBot_qq();
        Global.api = SetGlobal.MyConfig.INSTANCE.getApi();
        Global.api_key = SetGlobal.MyConfig.INSTANCE.getApi_key();
        Global.database_host = SetGlobal.MyConfig.INSTANCE.getDatabase_host();
        Global.database_user = SetGlobal.MyConfig.INSTANCE.getDatabase_user();
        Global.database_passwd = SetGlobal.MyConfig.INSTANCE.getDatabase_passwd();
        Global.database_name = SetGlobal.MyConfig.INSTANCE.getDatabase_name();
        if (Global.owner_qq == 0 || Global.bot_qq == 0 || Global.database_host.equals("") || Global.database_user.equals("") || Global.database_passwd.equals("") || Global.database_name.equals("")) {
            getLogger().error("你需要正确配置2kbot-java的配置文件！");
            System.exit(1);
        }
        // 初始化数据库
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        try (Connection msc = DriverManager.getConnection(String.format("jdbc:mysql://%s:3306", Global.database_host), Global.database_user, Global.database_passwd)) {
            Statement cmd = msc.createStatement();
            String[] sqls = {
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`blocklist` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',`gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`ops` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',`gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`ignores` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',`gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`ignores` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',`gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`g_blocklist` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`g_ops` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`g_ignores` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`repeatctrl` (`id` INT NOT NULL AUTO_INCREMENT,`qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',`gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',`last_repeat` BIGINT NULL COMMENT '上次复读时间',`last_repeatctrl` BIGINT NULL COMMENT '上次复读控制时间',`repeat_count` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '复读计数',PRIMARY KEY (`id`));", Global.database_name),
                    String.format("CREATE TABLE IF NOT EXISTS `%s`.`bread` (`id` int NOT NULL AUTO_INCREMENT,`gid` varchar(10) NOT NULL COMMENT 'Q群号',`factory_level` int NOT NULL DEFAULT '1' COMMENT '面包厂等级',`storage_upgraded` int NOT NULL DEFAULT '0' COMMENT '库存升级次数',`bread_diversity` tinyint NOT NULL DEFAULT '0' COMMENT '多样化生产状态',`factory_exp` int NOT NULL DEFAULT '0' COMMENT '面包厂经验',`breads` int NOT NULL DEFAULT '0' COMMENT '面包库存',`exp_gained_today` int NOT NULL DEFAULT '0' COMMENT '近24小时获取经验数',`last_expfull` bigint NOT NULL DEFAULT '946656000' COMMENT '上次达到经验上限时间',`last_expgain` bigint NOT NULL DEFAULT '946656000' COMMENT '近24小时首次获取经验时间',`last_produce` bigint NOT NULL DEFAULT '946656000' COMMENT '上次完成一轮生产周期时间', PRIMARY KEY (`id`))", Global.database_name)
            };
            for (String sql : sqls) {
                cmd.executeUpdate(sql);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Update.Execute();
        // 启动提示
        getLogger().info("2kbot-java已加载！");
        // 注册监听器
        GlobalEventChannel.INSTANCE.registerListenerHost(new BotMain());
        // 运行面包厂生产任务
        Thread newThread = new Thread(() -> {
            BreadFactory.BreadProduce();
        });
        newThread.start();
    }
}