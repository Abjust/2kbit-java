// 2kbot Java Edition，2kbot的Java分支版本
// Copyright(C) 2022 Abjust 版权所有。

// 本程序是自由软件：你可以根据自由软件基金会发布的GNU Affero通用公共许可证的条款，即许可证的第3版或（您选择的）任何后来的版本重新发布它和/或修改它。。

// 本程序的发布是希望它能起到作用。但没有任何保证；甚至没有隐含的保证。本程序的分发是希望它是有用的，但没有任何保证，甚至没有隐含的适销对路或适合某一特定目的的保证。 参见 GNU Affero通用公共许可证了解更多细节。

// 您应该已经收到了一份GNU Affero通用公共许可证的副本。 如果没有，请参见<https://www.gnu.org/licenses/>。

// 致所有构建及修改2kbot代码片段的用户：作者（Abjust）并不承担构建2kbot代码片段（包括修改过的版本）所产生的一切风险，但是用户有权在2kbot的GitHub项目页提出issue，并有权在代码片段修复这些问题后获取这些更新，但是，作者不会对修改过的代码版本做质量保证，也没有义务修正在修改过的代码片段中存在的任何缺陷。


package com.java_2kbot;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.*;
import net.mamoe.mirai.message.data.*;
import net.mamoe.mirai.utils.ExternalResource;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class BotMain extends SimpleListenerHost {
    // 在这里添加你的代码，比如订阅消息/事件之类的
    // 戳一戳效果
    @EventHandler
    private void onEvent(NudgeEvent event) {
        if (event.getTarget().equals(Global.bot_qq) && event.getSubject().equals("Group")) {
            try {
                event.getSubject().sendMessage("握草泥马呀—\n我操尼玛啊啊啊啊—\n我—操—你—妈—\n听到没，我—操—你—妈—");
            } catch (Exception e) {
                System.out.println("握草泥马呀—\n我操尼玛啊啊啊啊—\n我—操—你—妈—\n听到没，我—操—你—妈—");
            }
        }
    }

    // bot加群
    @EventHandler
    private void onEvent(BotInvitedJoinGroupRequestEvent event) {
        if (event.getInvitorId() == Global.owner_qq) {
            event.accept();
        } else {
            event.ignore();
        }
    }

    // 侦测加群请求
    @EventHandler
    private void onEvent(MemberJoinRequestEvent event) {
        if (Global.blocklist == null || Global.blocklist.contains(String.format("%s_%s", event.getGroupId(), event.getFromId())) && Global.g_blocklist == null || Global.g_blocklist.contains(Long.toString(event.getFromId()))) {
            event.reject();
        }
    }

    // 侦测改名
    @EventHandler
    private void onEvent(MemberCardChangeEvent event) {
        if (!event.getNew().equals("")) {
            try {
                event.getGroup().sendMessage(String.format("QQ号：%s\n原昵称：%s\n新昵称：%s", event.getMember().getId(), event.getOrigin(), event.getNew()));
            } catch (Exception e) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 侦测撤回
    @EventHandler
    private void onEvent(MessageRecallEvent.GroupRecall event) {
        MessageChain messageChain = new MessageChainBuilder()
                .append(new At(Objects.requireNonNull(event.getOperator()).getId()))
                .append(" 你又撤回了什么见不得人的东西？")
                .build();
        if (event.getAuthorId() != event.getOperator().getId()) {
            if (!event.getOperator().getPermission().toString().equals("ADMINISTRATOR") && !event.getOperator().getPermission().toString().equals("OWNER")) {
                try {
                    event.getGroup().sendMessage(messageChain);
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
        } else {
            try {
                event.getGroup().sendMessage(messageChain);
            } catch (Exception e) {
                System.out.println("群消息发送失败");
            }
        }
    }

    // 侦测踢人
    @EventHandler
    private void onEvent(MemberLeaveEvent.Kick event) {
        try {
            event.getGroup().sendMessage(String.format("%s (%s) 被踢出去辣，好似，开香槟咯！", event.getMember().getNameCard(), event.getMember().getId()));
        } catch (Exception e) {
            System.out.println("群消息发送失败");
        }
    }

    // 侦测退群
    private void onEvent(MemberLeaveEvent.Quit event) {
        try {
            event.getGroup().sendMessage(String.format("%s (%s) 退群力（悲）", event.getMember().getNameCard(), event.getMember().getId()));
        } catch (Exception ex) {
            System.out.println("群消息发送失败");
        }
    }

    // 侦测入群
    private void onEvent(MemberJoinEvent event) {
        MessageChain messageChain = new MessageChainBuilder()
                .append(new At(Objects.requireNonNull(event.getMember()).getId()))
                .append(" 来辣，让我们一起撅新人！（bushi")
                .build();
        try {
            event.getGroup().sendMessage(messageChain);
        } catch (Exception ex) {
            System.out.println("群消息发送失败");
        }
    }

    // bot对接收消息的处理
    @EventHandler
    private void onEvent(GroupMessageEvent event) {
        if ((event.getSender().getId() != Global.bot_qq && (Global.ignores == null || !Global.ignores.contains(String.format("%s_%s", event.getGroup().getId(), event.getSender().getId()))) && (Global.g_ignores == null || !Global.g_ignores.contains(Long.toString(event.getSender().getId()))))) {
            // 面包厂相关
            String[] command = event.getMessage().contentToString().split(" ");
            if (command.length == 2) {
                int number;
                switch (command[0]) {
                    case "!givebread":
                        try {
                            number = Integer.parseInt(command[1]);
                            Bread.Give(event.getGroup().getId(), event.getSender().getId(), number);
                            break;
                        } catch (Exception ignored) {
                        }
                    case "!getbread":
                        try {
                            number = Integer.parseInt(command[1]);
                            Bread.Get(event.getGroup().getId(), event.getSender().getId(), number);
                            break;
                        } catch (Exception ignored) {
                        }
                    case "!bread_diversity":
                        switch (command[1]) {
                            case "on" -> Bread.Diversity(event.getGroup().getId(), 1);
                            case "off" -> Bread.Diversity(event.getGroup().getId(), 0);
                        }
                }
            } else {
                switch (command[0]) {
                    case "!querybread" -> Bread.Query(event.getGroup().getId(), event.getSender().getId());
                    case "!upgrade_factory" -> Bread.UpgradeFactory(event.getGroup().getId());
                    case "!build_factory" -> Bread.BuildFactory(event.getGroup().getId());
                    case "!upgrade_storage" -> Bread.UpgradeStorage(event.getGroup().getId());
                }
            }
            String s = event.getMessage().contentToString();
            // 计算经验
            Bread.GetExp(event.getGroup().getId(), event.getSender().getId());
            // 复读机
            Repeat.Execute(event.getGroup().getId(), event.getSender().getId(), event.getMessage());
            // 数学计算
            if (s.startsWith("!calc"))
            {
                Mathematics.Execute(event.getGroup().getId(), event.getMessage());
            }
            // 发送公告
            if (s.startsWith("!announce")) {
                Announce.Execute(event.getSender().getId(), event.getGroup().getId(), event.getMessage());
            }
            // 随机图片
            if (s.equals("!photo")) {
                Random r = new Random();
                String url;
                int chance = 3;
                int choice = r.nextInt(chance);
                if (choice == chance - 1) {
                    url = "https://www.dmoe.cc/random.php";
                } else {
                    url = "https://source.unsplash.com/random";
                }
                try {
                    event.getGroup().sendMessage("图片在来的路上...");
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
                try {
                    URL image_url = new URL(url);
                    URLConnection uc = image_url.openConnection();
                    InputStream in = uc.getInputStream();
                    byte[] bytes = in.readAllBytes();
                    ExternalResource er = ExternalResource.create(bytes);
                    Image image = event.getGroup().uploadImage(er);
                    event.getGroup().sendMessage(image);
                    er.close();
                } catch (Exception ex) {
                    try {
                        event.getGroup().sendMessage("图片好像不见了！");
                    } catch (Exception e1) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 叫人
            if (s.startsWith("!call")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                switch (text.length) {
                    case 3:
                        try {
                            if (result1.contains("mirai:at")) {
                                String[] text1 = result1.split(" ");
                                Call.Execute(Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), Integer.parseInt(text1[2]));
                            } else {
                                if (Integer.parseInt(text[2]) >= 1) {
                                    Call.Execute(Long.parseLong(text[1]), event.getGroup().getId(), Integer.parseInt(text[2]));
                                } else {
                                    try {
                                        event.getGroup().sendMessage("nmd，这个数字是几个意思？");
                                    } catch (Exception e1) {
                                        System.out.println("群消息发送失败");
                                    }
                                }
                            }
                        } catch (Exception e) {
                            try {
                                event.getGroup().sendMessage("油饼食不食？");
                            } catch (Exception e1) {
                                System.out.println("群消息发送失败");
                            }
                        }
                        break;
                    case 2:
                        try {
                            if (result1.contains("mirai:at")) {
                                String[] text1 = result1.split(" ");
                                Call.Execute(Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), 3);
                            } else {
                                Call.Execute(Long.parseLong(text[1]), event.getGroup().getId(), 3);
                            }
                        } catch (Exception e) {
                            try {
                                event.getGroup().sendMessage("油饼食不食？");
                            } catch (Exception e1) {
                                System.out.println("群消息发送失败");
                            }
                        }
                        break;
                    default:
                        try {
                            event.getGroup().sendMessage("缺少参数");
                        } catch (Exception e1) {
                            System.out.println("群消息发送失败");
                        }
                        break;
                }
            }
            // 菜单与帮助
            Help.Execute(event.getGroup().getId(), event.getMessage());
            // 遗言
            if (s.equals("遗言") || s.equals("留言")) {
                try {
                    event.getGroup().sendMessage("""
                            对我而言，我曾一直觉得Setup群是个适合我的地方，我的直觉也的确没有错，Setup群确实是个好地方，我在里面学到了不少东西，并且跟群友相谈甚欢。但是，因为群里包括群主在内的不少人和我一样，都饱受抑郁症或者精神心理疾病的困扰，以至于我在面对他们慢慢开始伤害自己的时候，或者说甚至打算终结自己的时候，却显得格外无能。我的一句“赶紧去看医生吧”，此刻显得苍白无力，我理解他们第一次求助，羞于启齿不敢告诉家里人。我不是不能理解群友们的心情，或者自身的悲惨经历。但是对我而言，我真的一时间难以接受这么多负面倾诉。我不是心理咨询师，我对心理学的掌握也有限，其实说是在，我自己也是个病人，我是个双相情感障碍患者，我也是第一次面对这种情况。每次遇到这种情况，我总是想着怎么逃避现实，仿佛精神分裂般，总是觉得事情没有发生，一切都是梦境罢了。我也希望是这样，但是发生的事情终归是发生了，我不可能凭主观意识去改变。
                            有时候我深感愧疚，不为什么，就为病情。不说世界上的人，就群友来说，群里比我惨的大有人在，有些没事，有些是抑郁症，像我这样得双相情感障碍的基本没有。我会自行反思，自己是不是太矫情、懦弱了，是不是抗压能力太差了呢？我怀疑过自己是假抑郁，认为自己不过是在博同情、骗流量。没错，就连我自己都不相信我自己了，那还有谁会相信这么拙劣的谎言？我感觉自己什么都是装出来的，我没有一样是真的，我只是在不懂装懂，我只是在夸大自己的苦楚和不幸，丝毫没有考虑别人的感受。我就是个精致的利己主义者，自私自利，只考虑自己的感受，特别不要脸。
                            我知道如果我离开，那就更加坚定我就是只顾自己的人，但是有时候我真的接受不了现实，我真的很想逃离现实，跟社会隔离开来，我不知道为什么我一直想这样，我也控制不了我自己，唉，现实就是那么残酷又无情，或许别人的痛苦是真正的不幸，我得病只是我活该，是我应有的惩罚，如果真是这么说，我也认罪认罚了。说实话，来了群之后，我的事情就特别的多，我不断地给群里的人制造麻烦，做过的错事实在是太多了，实在是不可饶恕。
                            对不起，Setup群的各位群友们，我觉得我应该就我给你们制造的麻烦，以及我对你们的欺骗谢罪，我可能真的值得离开，如果我离开了，希望你们不要挂念我，我就是个罪人，没什么值得纪念的地方。
                            """);
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
            // 鸣谢
            if (s.equals("鸣谢")) {
                try {
                    event.getGroup().sendMessage("感谢Leobot和Hanbot给我的启发，感谢Leo给我提供C#的技术支持，也感谢Setup群各位群员对我的支持！");
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
            // 精神心理疾病科普
            MentalHealth.Execute(event.getGroup().getId(), event.getMessage());
            // 处理“你就是歌姬吧”（祖安）
            Zuan.Execute(event.getGroup().getId(), event.getSender().getId(), event.getMessage());
            // 群管功能
            // 禁言
            if (s.startsWith("!mute") && !s.startsWith("!muteme")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length != 1) {
                    switch (text.length) {
                        case 3:
                            try {
                                if (result1.contains("mirai:at")) {
                                    String[] text1 = result1.split(" ");
                                    Admin.Mute(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), event.getSender().getPermission().toString(), Integer.parseInt(text[2]));
                                } else {
                                    Admin.Mute(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString(), Integer.parseInt(text[2]));
                                }
                            } catch (NumberFormatException ex) {
                                try {
                                    event.getGroup().sendMessage("参数错误");
                                } catch (Exception e) {
                                    System.out.println("群消息发送失败");
                                }
                            }
                        case 2:
                            try {
                                Admin.Mute(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString(), 10);
                            } catch (NumberFormatException ex) {
                                try {
                                    event.getGroup().sendMessage("参数错误");
                                } catch (Exception e) {
                                    System.out.println("群消息发送失败");
                                }
                            }
                        default:
                            try {
                                event.getGroup().sendMessage("参数错误");
                            } catch (Exception e) {
                                System.out.println("群消息发送失败");
                            }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 解禁
            if (s.startsWith("!unmute")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.Unmute(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), event.getSender().getPermission().toString());
                        } else {
                            Admin.Unmute(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 踢人
            if (s.startsWith("!kick")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.Kick(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), event.getSender().getPermission().toString());
                        } else {
                            Admin.Kick(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 加黑
            if (s.startsWith("!block")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.Block(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), event.getSender().getPermission().toString());
                        } else {
                            Admin.Block(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 解黑
            if (s.startsWith("!unblock")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.Unblock(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), event.getSender().getPermission().toString());
                        } else {
                            Admin.Unblock(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 全局加黑
            if (s.startsWith("!gblock")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.G_Block(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId());
                        } else {
                            Admin.G_Block(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 全局解黑
            if (s.startsWith("!gunblock")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.G_Unblock(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId());
                        } else {
                            Admin.G_Unblock(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 给予机器人管理员
            if (s.startsWith("!op")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.Op(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), event.getSender().getPermission().toString());
                        } else {
                            Admin.Op(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 剥夺机器人管理员
            if (s.startsWith("!deop")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.Deop(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId());
                        } else {
                            Admin.Deop(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 给予全局机器人管理员
            if (s.startsWith("!gop")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.G_Op(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId());
                        } else {
                            Admin.G_Op(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 剥夺全局机器人管理员
            if (s.startsWith("!gdeop")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.G_Deop(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId());
                        } else {
                            Admin.G_Deop(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 屏蔽消息
            if (s.startsWith("!ignore")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.Ignore(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId(), event.getSender().getPermission().toString());
                        } else {
                            Admin.Ignore(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId(), event.getSender().getPermission().toString());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 全局屏蔽消息
            if (s.startsWith("!gignore")) {
                String result1 = event.getMessage().serializeToMiraiCode();
                String[] text = s.split(" ");
                if (text.length == 2) {
                    try {
                        if (result1.contains("mirai:at")) {
                            String[] text1 = result1.split(" ");
                            Admin.G_Ignore(event.getSender().getId(), Long.parseLong(text1[1].replace("[mirai:at:", "").replace("]", "")), event.getGroup().getId());
                        } else {
                            Admin.G_Ignore(event.getSender().getId(), Long.parseLong(text[1]), event.getGroup().getId());
                        }
                    } catch (NumberFormatException ex) {
                        try {
                            event.getGroup().sendMessage("参数错误");
                        } catch (Exception e) {
                            System.out.println("群消息发送失败");
                        }
                    }
                } else {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 禁言自己
            if (s.startsWith("!muteme")) {
                String[] text = s.split(" ");
                if (text.length == 2)
                {
                    Admin.MuteMe(event.getSender().getId(), event.getGroup().getId(), Integer.parseInt(text[1]));
                }
                else if (text.length == 1)
                {
                    Admin.MuteMe(event.getSender().getId(), event.getGroup().getId(), 10);
                }
                else
                {
                    try {
                        event.getGroup().sendMessage("参数错误");
                    } catch (Exception e) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
            // 发动带清洗
            if (s.equals("!purge")) {
                Admin.Purge(event.getSender().getId(), event.getGroup().getId(), event.getSender().getPermission().toString());
            }
            // 同步黑名单
            if (s.equals("!sync")) {
                try {
                    event.getGroup().sendMessage("因HanBot API存在问题，同步功能被暂时禁用！");
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
            // 反向同步黑名单
            if (s.equals("!rsync")) {
                try {
                    event.getGroup().sendMessage("因HanBot API存在问题，同步功能被暂时禁用！");
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
            // 合并黑名单并双向同步
            if (s.equals("!merge")) {
                try {
                    event.getGroup().sendMessage("因HanBot API存在问题，同步功能被暂时禁用！");
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
            // 版本
            if (s.equals("版本")) {
                List<String> splashes = new ArrayList<>() {
                    {
                        add("也试试HanBot罢！Also try HanBot!");
                        add("誓死捍卫微软苏维埃！");
                        add("打倒MF独裁分子！");
                        add("要把反革命分子的恶臭思想，扫进历史的垃圾堆！");
                        add("PHP是世界上最好的编程语言（雾）");
                        add("社会主义好，社会主义好~");
                        add("Minecraft很好玩，但也可以试试Terraria！");
                        add("So Nvidia, f**k you!");
                        add("战无不胜的马克思列宁主义万岁！");
                        add("Bug是杀不完的，你杀死了一个Bug，就会有千千万万个Bug站起来！");
                        add("跟张浩扬博士一起来学Jvav罢！");
                        add("哼哼哼，啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊啊");
                        add("你知道吗？其实你什么都不知道！");
                        add("Tips:这是一条烫...烫..烫知识（）");
                        add("你知道成功的秘诀吗？我告诉你成功的秘诀就是：我操你妈的大臭逼");
                        add("有时候ctmd不一定是骂人 可能是传统美德");
                        add("python不一定是编程语言 也可能是屁眼通红");
                        add("这条标语虽然没有用，但是是有用的，因为他被加上了标语");
                        add("使用Java编写！");
                    }
                };
                Random r = new Random();
                int random = r.nextInt(splashes.size());
                try {
                    event.getGroup().sendMessage(String.format("机器人版本：1.0.8-je\n上次更新日期：2022/12/13\n更新内容：同步了2kbot b_22w25d更新的功能修复\n---------\n%s", splashes.get(random)));
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
            // 获取源码
            if (s.equals("源码") || s.equals("获取源码") || s.equals("怎样做这样的机器人")) {
                try {
                    event.getGroup().sendMessage("请前往https://github.com/Abjust/2kbot-java获取2kbot Java Edition的源码！");
                } catch (Exception e) {
                    System.out.println("群消息发送失败");
                }
            }
        }
    }
}
