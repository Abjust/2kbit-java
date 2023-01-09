// 2kbit Java Edition，2kbit的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbit代码片段的用户：作者（Abjust）并不承担构建2kbit代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbit的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbit;

import net.mamoe.mirai.console.plugin.jvm.JavaPlugin;
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescriptionBuilder;
import net.mamoe.mirai.event.GlobalEventChannel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Java2kbit extends JavaPlugin {
    public static final Java2kbit INSTANCE = new Java2kbit();

    public Java2kbit() {
        super(new JvmPluginDescriptionBuilder("com.java_2kbit", "1.1.1")
                .name("2kbit Java Edition")
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
            getLogger().error("你需要正确配置2kbit-java的配置文件！");
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
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`blocklist` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            `gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`ops` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            `gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`ignores` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            `gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`ignores` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            `gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`g_blocklist` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`g_ops` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`g_ignores` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`repeatctrl` (
                            `id` INT NOT NULL AUTO_INCREMENT,
                            `qid` VARCHAR(10) NOT NULL COMMENT 'QQ号',
                            `gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',
                            `last_repeat` BIGINT NULL COMMENT '上次复读时间',
                            `last_repeatctrl` BIGINT NULL COMMENT '上次复读控制时间',
                            `repeat_count` TINYINT UNSIGNED NOT NULL DEFAULT 0 COMMENT '复读计数',
                            PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`bread` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `gid` varchar(10) NOT NULL COMMENT 'Q群号',
                            `factory_level` int NOT NULL DEFAULT '1' COMMENT '面包厂等级',
                            `storage_upgraded` int NOT NULL DEFAULT '0' COMMENT '库存升级次数',
                            `speed_upgraded` int NOT NULL DEFAULT '0' COMMENT '生产速度升级次数',
                            `output_upgraded` int NOT NULL DEFAULT '0' COMMENT '产量升级次数',
                            `factory_mode` tinyint NOT NULL DEFAULT '0' COMMENT '面包厂生产模式',
                            `factory_exp` int NOT NULL DEFAULT '0' COMMENT '面包厂经验',
                            `breads` int NOT NULL DEFAULT '0' COMMENT '面包库存',
                            `exp_gained_today` int NOT NULL DEFAULT '0' COMMENT '近24小时获取经验数',
                            `last_expfull` bigint NOT NULL DEFAULT '946656000' COMMENT '上次达到经验上限时间',
                            `last_expgain` bigint NOT NULL DEFAULT '946656000' COMMENT '近24小时首次获取经验时间',
                            `last_produce` bigint NOT NULL DEFAULT '946656000' COMMENT '上次完成一轮生产周期时间',
                            PRIMARY KEY (`id`))
                            """, Global.database_name),
                    String.format("""
                            CREATE TABLE IF NOT EXISTS `%s`.`material` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `gid` VARCHAR(10) NOT NULL COMMENT 'Q群号',
                              `flour` INT NOT NULL DEFAULT 0 COMMENT '面粉数量',
                              `egg` INT NOT NULL DEFAULT 0 COMMENT '鸡蛋数量',
                              `yeast` INT NOT NULL DEFAULT 0 COMMENT '酵母数量',
                              `last_produce` bigint NOT NULL DEFAULT '946656000' COMMENT '上次完成一轮生产周期时间',
                              PRIMARY KEY (`id`));
                            """, Global.database_name),
                    String.format("INSERT IGNORE INTO `%s`.`material` (id, gid) SELECT id, gid FROM `%s`.`bread`", Global.database_name, Global.database_name)
            };
            for (String sql : sqls) {
                cmd.executeUpdate(sql);
            }
            // 更新数据表
            try {
                String sql = String.format("""
                        ALTER TABLE `%s`.`bread`
                        ADD COLUMN `speed_upgraded` INT NOT NULL DEFAULT 0 COMMENT '生产速度升级次数' AFTER `storage_upgraded`,
                        ADD COLUMN `output_upgraded` INT NOT NULL DEFAULT 0 COMMENT '产量升级次数' AFTER `speed_upgraded`,
                        CHANGE COLUMN `bread_diversity` `factory_mode` TINYINT NOT NULL DEFAULT '0' COMMENT '面包厂生产模式' ;
                        """, Global.database_name);
                cmd.executeUpdate(sql);
            } catch (Exception ignored) {
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        // 启动提示
        getLogger().info("2kbit-java已加载！");
        // 注册监听器
        GlobalEventChannel.INSTANCE.registerListenerHost(new BotMain());
        // 运行面包厂生产任务
        Thread newThread = new Thread(BreadFactory::MaterialProduce);
        newThread.start();
        Thread newThread1 = new Thread(BreadFactory::BreadProduce);
        newThread1.start();
        // 更新列表
        Update.Execute();
    }
}