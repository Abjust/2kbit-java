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

public class MentalHealth {
    public static void Execute(long group, MessageChain messageChain) {
        List<String> disorders = new ArrayList<>() {
            {
                add("焦虑症");
                add("强迫症");
                add("神经衰弱");
                add("恐惧症");
                add("抑郁症");
                add("双相情感障碍");
                add("精神分裂症");
                add("妄想症");
                add("分裂情感性障碍");
                add("厌食症");
                add("贪食症");
                add("孤独症");
                add("多动症");
            }
        };
        List<String> explanations = new ArrayList<>() {
            {
                add("焦虑症是指在日常情况下，出现强烈、过度和持续的担忧和恐惧，可在几分钟之内达到顶峰。这种症状会干扰日常活动，难以控制。常见的焦虑症有广泛性焦虑障碍、惊恐障碍、社交恐惧症、特定恐惧症和分离焦虑障碍等。");
                add("强迫症是一种较为常见的精神疾病，以反复出现的强迫观念、强迫冲动或强迫行为等为主要表现。多数患者认为这些观念和行为不必要或不正常，违反了自己的意愿，但无法摆脱，为此深感焦虑和痛苦。");
                add("神经衰弱是在长期紧张和压力下，产生以脑和躯体功能衰弱为主要特征的一种心理疾病，主要表现为精神活动减弱，更易疲劳，注意力难集中，常伴有情绪易激惹、烦恼、紧张，睡眠障碍及肌肉紧张性疼痛等症状。");
                add("恐惧症又称恐怖症、恐怖性神经症，是一种以过分和不合理地惧怕外界某种客观事物或处境为主要表现的神经症。患者发作时常伴有明显的焦虑、自主神经紊乱和回避反应，患者难以自控，症状反复出现，以致影响其正常活动。分为广场恐惧症、社交恐惧症和特定恐惧症三种类型。");
                add("抑郁症是一种患病率高、临床治愈率高的精神障碍，但由于老百姓对该病认知不足，导致坚持接受正规治疗的患者较少，因此也有接受治疗率低、复发率高的特征。它以显著而持久的心境低落为主要特征，部分患者有存在自伤、自杀行为，可伴有妄想、幻觉等精神病性症状，严重时可能发生抑郁性木僵，可表现为面部表情固定、对刺激缺乏反应、话少甚至不言语、少动甚至不动等。抑郁症发作时一般表现为情绪低落、兴趣减退、精力缺乏等。");
                add("双相情感障碍又名双相障碍，是一类既有躁狂发作或者轻躁狂发作，又有抑郁发作（典型特征）的常见精神障碍，首次发病可见于任何年龄。躁狂发作时，患者有情感高涨、言语活动增多、精力充沛等表现；抑郁发作时，患者常表现出情绪低落、愉快感丧失、言语活动减少、疲劳迟钝等症状。患者的临床表现复杂，其复杂性体现在情绪低落或者高涨反复、交替、不规则呈现的同时，伴有注意力分散、轻率、夸大、思维奔逸、高反应性、睡眠减少和言语增多等紊乱症状。还常见焦虑、强迫、物质滥用，也可出现幻觉、妄想或紧张症状等精神病症状。病程多形演变，发作性、循环往复性、混合迁徙性、潮起潮落式病程不一而足，比如3个抑郁期跟着2个躁狂期。间歇期或长或短，间歇期社会功能相对正常，但也可有社会功能损害，多次反复发作后出现发作频率快、病情越发复杂的情况。");
                add("精神分裂症，定义为一种慢性的、严重的精神障碍，包括个人的感知觉、情感与行为的异常。患者很难区分出真实和想象，患者反应迟钝、行为退缩或过激，严重者难以进行正常社交。医学上，疾病分类体系定义它不是一种疾病，而是一种障碍。该病常常发作在青年或者壮年时期，发作时，身体感觉、思维逻辑、情感体验和行为表现等方面产生障碍，但是既不昏迷，也不智障。现阶段研究显示其可能为遗传、大脑结构、妊娠问题以及后天生活的家庭、周围环境因素共同激发，但具体发病机制及病因并未完全明确，因而这种疾病难以治愈，不过，通过适当的治疗手段可以控制病情。该病病期多漫长，约一半的患者因为精神的残疾状态，给自身、家庭、周围带来不同程度影响。");
                add("妄想性障碍是以长期（三个月及以上）持续性、系统性妄想为最主要临床特征的一组精神障碍。患者除了妄想症状外，少有其他精神病症状，其人格和智能通常可保待完整，在不涉及妄想的情况下，情感、言语和行为基本正常。妄想性障碍起病隐匿，病程演进缓慢，甚至可伴随患者终生。");
                add("分裂情感性障碍又称分裂情感性精神病，是一组精神分裂症状（幻觉、妄想等精神病性症状）和情感症状（躁狂、抑郁）同时存在或交替发生，症状又同样典型，常有反复发作的倾向。有人认为它是精神分裂症和情感性障碍的共病体，有人认为是精神分裂症和情感障碍连续谱系上的一个中点，也有人认为是伴有精神病性症状的情感障碍，而非一类独立的疾病。它是由遗传因素、生物化学等因素共同作用引起，对患者的精神、社会功能造成损伤，以药物治疗为主，辅以物理及心理治疗。");
                add("神经性厌食症简称厌食症，是一种慢性进食障碍的临床表现。神经性厌食症的原意为精神性食欲丧失，此症的主要特点为特殊的精神心理变态、以瘦为美的躯体形象障碍，自我造成的拒食、导吐或腹泻，极度的营养不良和消瘦、闭经，甚至死亡。");
                add("贪食症又称为神经性贪食症，是一种精神心理性进食障碍。这个病多见于女性，患者发病多在青春期或者成年早期，主要表现为反复发作、不可以控制的暴饮暴食的情况。暴饮暴食之后又常常采用自我催吐、催泻，以及禁食、过度运动等等不恰当的方式来过度的减肥，这些行为与患者对于自身的体重、体型的过度关注和不客观的评价有关系。");
                add("孤独症也称自闭症，是广泛性发育障碍中最常见、最具有代表性的疾病。该疾病起病于婴幼儿时期，以社会交往障碍、交流障碍、局限的兴趣、刻板与重复行为方式为主要临床表现，多数患儿还会伴有不同程度的精神发育迟滞。孤独症的患病率报道不一，一般认为约为儿童人口的2～5/万人，男女比例约为3～4：1，男孩是女孩的3-4倍。");
                add("注意缺陷多动障碍，俗称多动症，是一种起病于儿童时期，以与年龄水平不相称的注意缺陷、行为多动和情绪冲动为主要表现的神经发育障碍。ADHD多于学龄前起病，慢性病程，有70%的患儿症状持续到青春期，30%~50%持续到成年期。这些患儿常同时患有学习障碍、对立违抗性障碍、情绪障碍、以及适应障碍等，对其学业、工作、社会及家庭生活等方面产生广泛而消极的负面影响，给家庭和社会造成沉重的负担。");
            }
        };
        if (messageChain.contentToString().equals("精神疾病") || messageChain.contentToString().equals("心理疾病")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("""
                        精神心理疾病科普大全：
                        1.神经症类
                        焦虑症|强迫症
                        神经衰弱|恐惧症
                        2.情感障碍
                        抑郁症|双相情感障碍
                        3.精神病性障碍
                        精神分裂症|妄想症
                        分裂情感性障碍
                        4.进食障碍
                        厌食症|贪食症
                        5.神经发育障碍
                        孤独症|多动症
                        """);
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
        }
        for (String q : disorders) {
            if (messageChain.contentToString().equals(q)) {
                try {
                    Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(explanations.get(disorders.indexOf(q)));
                } catch (Exception ex) {
                    System.out.println("群消息发送失败");
                }
            }
        }
        // 量表测试链接推荐
        List<String> scales = new ArrayList<>() {
            {
                add("MBTI");
                add("艾森克");
                add("大五");
                add("九型");
                add("SCL90");
                add("SDS");
                add("SAS");
                add("MDQ");
                add("HCL32");
                add("偏执量表");
                add("强迫量表");
                add("精神质量表");
            }
        };
        List<String> links = new ArrayList<>() {
            {
                add("(1)https://16personalities.com/ch/\n(2)https://www.jungus.cn/zh-hans/test/\n(3)https://www.zxgj.cn/g/mbti28");
                add("https://www.zxgj.cn/g/askj/");
                add("http://www.apesk.com/bigfive/");
                add("http://www.cnenn.cn/html/jiuxingrengeceshi/test.asp");
                add("http://www.ntneuro.org/scale/scl90.asp");
                add("https://www.zxgj.cn/g/yiyuzheng");
                add("https://www.zxgj.cn/g/jiaolv");
                add("https://www.zxgj.cn/g/mdq");
                add("https://www.zxgj.cn/g/hcl32");
                add("https://www.zxgj.cn/g/mpa");
                add("https://www.zxgj.cn/g/qiangpozheng");
                add("https://www.zxgj.cn/g/mpsy");
            }
        };
        if (messageChain.contentToString().equals("量表") || messageChain.contentToString().equals("测试")) {
            try {
                Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage("""
                        量表测试大全
                        人格/性格测试：
                        MBTI|艾森克
                        大五|九型
                        精神心理量表：
                        SCL90（综合）|SDS（抑郁）
                        SAS（焦虑）|MDQ（双相）
                        HCL32（轻躁狂）|偏执量表
                        强迫量表|精神质量表
                        （精神心理量表结果仅供参考，如出现相关症状，还请尽快前往当地医院就诊）
                        """);
            } catch (Exception ex) {
                System.out.println("群消息发送失败");
            }
            for (String q : scales) {
                if (messageChain.contentToString().toUpperCase().equals(q)) {
                    try {
                        Objects.requireNonNull(Bot.getInstance(Global.bot_qq).getGroup(group)).sendMessage(links.get(scales.indexOf(q)));
                    } catch (Exception ex) {
                        System.out.println("群消息发送失败");
                    }
                }
            }
        }
    }
}
