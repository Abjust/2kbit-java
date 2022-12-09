// 2kbot Java Edition，2kbot的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbot代码片段的用户：作者（Abjust）并不承担构建2kbot代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbot的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbot;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.MessageChain;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Help {
    public static void Execute(long group, MessageChain messageChain) {
        if (messageChain.contentToString().equals("菜单") || messageChain.contentToString().equals("!menu")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("""
                        2kbot菜单
                        1.群管系统
                        2.复读机
                        3.叫人功能
                        4.精神心理疾病科普
                        5.量表测试
                        6.面包厂功能
                        7.获取源码
                        详情请用!help指令
                        """);
            } catch (Exception ex) {
                System.out.println("菜单消息发送失败");
            }
        }
        // 帮助
        List<String> indexs = new ArrayList<>() {
            {
                add("1");
                add("2");
                add("3");
                add("4");
                add("5");
                add("6");
                add("7");
            }
        };
        List<String> contents = new ArrayList<>() {
            {
                add("""
                        群管功能
                        禁言：!mute <QQ号或at> [时间] （以分钟算）
                        解禁：!unmute <QQ号或at>
                        踢出：!kick <QQ号或at>
                        加黑：!block <QQ号或at>
                        解黑：!unblock <QQ号或at>
                        屏蔽消息（加灰）：!ignore <QQ号或at>
                        给予管理员：!op <QQ号或at>
                        剥夺管理员：!deop <QQ号或at>
                        清理群内所有黑名单人员：!purge
                        从Hanbot同步黑名单：!sync
                        将黑名单反向同步给Hanbot：!rsync
                        合并黑名单：!merge
                        （上述功能都需要机器人管理员）
                        注：在block、unblock、ignore、op、deop前加上g表示应用全局（例子：!gblock）
                        """);
                add("该指令用于复述文本\n用法：!echo <文本>");
                add("该指令用于叫人\n用法：!call <QQ号或at> [次数]");
                add("发送“精神疾病”或者“心理疾病”并按照后续出现的选项发送相应文字即可获得科普文本");
                add("发送“量表”或者“测试”并按照后续出现的选项发送相应文字即可获得链接");
                add("""
                        面包厂功能
                        建造面包厂（初始化）：!build_factory
                        给2kbot面包： !givebread <数量>
                        向2kbot要面包：!getbread <数量>
                        查询面包库存：!querybread
                        改变多样化生产状态：!bread_diversity <on/off>
                        升级面包厂：!upgrade_factory
                        升级库存（满级后）：!upgrade_storage
                        """);
                add("https://github.com/Abjust/2kbot-java");
            }
        };
        if (messageChain.contentToString().startsWith("!help")) {
            String[] result = messageChain.contentToString().split(" ");
            System.out.println(result.length);
            if (result.length == 2) {
                for (String q : indexs) {
                    try {
                        if (result[1].equals(q)) {
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(contents.get(indexs.indexOf(q)));
                            } catch (Exception ex) {
                                System.out.println("帮助消息发送失败");
                            }
                        } else if (Integer.parseInt(result[1]) > indexs.size()) {
                            try {
                                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("未找到相关帮助");
                            } catch (Exception ex) {
                                System.out.println("帮助消息发送失败");
                            }
                        }
                    } catch (Exception ex) {
                        try {
                            Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("请写数字，不要写别的好吗？");
                        } catch (Exception ex1) {
                            System.out.println("帮助消息发送失败");
                        }
                    }
                }
            } else if (messageChain.contentToString().equals("!help")) {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("""
目前有对于以下功能的帮助文档：
[1]群管功能
[2]!echo
[3]!call
[4]精神心理疾病科普
[5]量表测试
[6]面包厂
[7]获取源码
""");
                } catch (Exception ex1) {
                    System.out.println("帮助消息发送失败");
                }
            }
        }
    }
}
