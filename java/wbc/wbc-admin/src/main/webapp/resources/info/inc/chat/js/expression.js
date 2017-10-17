(function($) {
    "use strict";

    $.SunnyExpression = function(triggers, options) {
        // 修改继承
        var O = {};
        $.extend(O, $.SunnyExpression.DEFAULTS, options);

        var _container = $(O.container);
        if (_container.length == 0) {
            console.error("expression container is undefined!");
            return;
        }

        var _chatInput = $(O.chatInput);
        if (_chatInput.length == 0) {
            console.error("expression chat input is undefined!");
            return;
        }
        ;

        // 初始化操作
        function init() {
            $(triggers).on(
                    "click",
                    function() {
                        if (isHidden()) {
                            if ($(O.container + ">span").length == 0) {
                                $.each($.SunnyExpression.CONSTANTS.expression, function(k, v) {
                                    _container.append("<span><a href='javascript:;' title='" + k + "'><img src='"
                                            + $.SunnyExpression.CONSTANTS.baseUrl + v + ".gif'/></a></span>");
                                });

                                // 点击某个表情
                                $(O.container + ">span>a").on("click", function() {
                                    _chatInput.val(_chatInput.val() + "[" + $(this).attr("title") + "]");
                                    hideContainer();
                                });
                            }
                            showContainer();
                        } else {
                            hideContainer();
                        }
                    });

        }

        function isHidden() {
            return _container.css("display") == "none" ? true : false;
        }

        function showContainer() {
            _container.slideDown(300);
        }

        function hideContainer() {
            _container.slideUp(300);
        }

        init();
    }

    $.fn.SunnyExpression = function(triggers, options) {
        $.SunnyExpression(this, triggers, options);
    }

    // 常量配置
    $.SunnyExpression.CONSTANTS = {
        baseUrl : "inc/chat/expression/",
        expression : {
            "微笑" : "smile",
            "撇嘴" : "curl",
            "色" : "color",
            "发呆" : "trance",
            "得意" : "proud",
            "流泪" : "tears",
            "害羞" : "shy",
            "闭嘴" : "shut",
            "睡" : "sleep",
            "大哭" : "crying",
            "尴尬" : "embarrass",
            "发怒" : "torment",
            "调皮" : "naughty",
            "龇牙" : "growl",
            "惊讶" : "surprise",
            "难过" : "sad",
            "酷" : "cool",
            "冷汗" : "cold",
            "抓狂" : "crazy",
            "吐" : "spit",
            "偷笑" : "titter",
            "可爱" : "lovely",
            "白眼" : "whiteeye",
            "傲慢" : "arrogant",
            "饥饿" : "hunger",
            "困" : "sleepy",
            "惊恐" : "panic",
            "流汗" : "sweating",
            "憨笑" : "mirth",
            "大兵" : "soldier",
            "奋斗" : "fight",
            "咒骂" : "curse",
            "疑问" : "doubt",
            "嘘…" : "hiss",
            "晕" : "Halo",
            "折磨" : "torture",
            "衰" : "wane",
            "骷髅" : "skeleton",
            "敲打" : "beating",
            "再见" : "goodbye",
            "擦汗" : "wipe",
            "抠鼻" : "pullnose",
            "鼓掌" : "applause",
            "糗大了" : "humiliating",
            "坏笑" : "grin",
            "左哼哼" : "lefthum",
            "右哼哼" : "righthum",
            "哈欠" : "yawn",
            "鄙视" : "despise",
            "委屈" : "aggrieved",
            "快哭了" : "gonnacry",
            "阴险" : "sinister",
            "亲亲" : "kiss",
            "吓" : "scared",
            "可怜" : "poor",
            "菜刀" : "cookknife",
            "西瓜" : "watermelon",
            "啤酒" : "beer",
            "篮球" : "basketball",
            "兵乓" : "pingpang",
            "咖啡" : "coffee",
            "饭" : "rice",
            "猪头" : "pig",
            "玫瑰" : "rose",
            "凋谢" : "withered",
            "示爱" : "affection",
            "爱心" : "heart",
            "心碎" : "brokenheart",
            "蛋糕" : "cake",
            "闪电" : "lighting",
            "炸弹" : "bomb",
            "刀" : "knife",
            "足球" : "soccer",
            "瓢虫" : "ladybug",
            "便便" : "shit",
            "月亮" : "moon",
            "太阳" : "sun",
            "礼物" : "gift",
            "拥抱" : "hug",
            "强" : "strong",
            "弱" : "weak",
            "握手" : "hands",
            "胜利" : "victory",
            "抱拳" : "holdfist",
            "勾引" : "seduce",
            "拳头" : "fist",
            "差劲" : "bad",
            "爱你" : "loveu",
            "NO" : "NO",
            "OK" : "OK"// 定义表情
        }
    }

    $.SunnyExpression.DEFAULTS = {
        chatInput : "#sunny-chat-input",// 聊天输入框
        container : "#sunny-expression-container",// 表情存放的容器
    }
})(jQuery)