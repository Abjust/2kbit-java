// 2kbit Java Edition，2kbit的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbit代码片段的用户：作者（Abjust）并不承担构建2kbit代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbit的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。

package com.java_2kbit;

import net.mamoe.mirai.Bot;
import net.mamoe.mirai.message.data.At;
import net.mamoe.mirai.message.data.MessageChain;
import net.mamoe.mirai.message.data.MessageChainBuilder;

import java.time.Instant;
import java.util.Objects;

public class Call {
    public static final int call_cd = 40;
    public static long last_call;

    public static void Execute(long victim, long group, int times) {
        if (times >= 10) {
            times = 10;
        }
        MessageChain messageChain = new MessageChainBuilder()
                .append(new At(victim))
                .append(" 机器人正在呼叫你")
                .build();
        Global.time_now = Instant.now().getEpochSecond();
        if (Global.time_now - last_call >= call_cd) {
            for (int i = 0; i < times; i++) {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(messageChain);
                    last_call = Instant.now().getEpochSecond();
                } catch (Exception ex) {
                    break;
                }
            }
        } else {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(String.format("CD未到，请别急！CD还剩： %s 秒", call_cd - (Global.time_now - last_call)));
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
    }
}
