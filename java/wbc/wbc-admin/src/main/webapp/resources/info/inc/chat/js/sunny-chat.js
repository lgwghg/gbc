(function($) {
    "use strict";

    $.SunnyChat = function(options) {
        // 修改继承
        var O = {};
        $.extend(O, $.SunnyChat.DEFAULTS, options);

        var _nickname_input = $("#sunny-nickname");
        var _form_signin = $(".form-signin");
        var _sunny_chat_container = $("#sunny-chat-container");
        var _userlist_container = $(O.userListContainer);
        var _content_container = $(O.contentContainer);
        var _chat_input = $(O.chatInput);
        var _sendbtn = $(O.sendBtn);
        var _logoutBtn = $(O.logoutBtn);

        var _nickname = "";
        var _userid = "";

        // 连接websocket后端服务器
        var _socket = io.connect('ws://192.168.11.165:9090');

        // 包装信息
        function wrapItem(msg) {
            if (!msg) {
                return "";
            }
            var re = /\[([\u4e00-\u9fa5]{1,4})\]/ig;
            var r = "";

            var _msg = msg;
            while (r = re.exec(_msg)) {
                if ($.SunnyExpression.CONSTANTS.expression[r[1]]) {
                    _msg = _msg.replace(r[0], "<img src='" + $.SunnyExpression.CONSTANTS.baseUrl + $.SunnyExpression.CONSTANTS.expression[r[1]]
                            + ".gif'/>")
                }
            }
            return _msg;

        }

        // 提交聊天信息
        function submitMsg() {
            var _msg = _chat_input.val();
            if (_msg) {
                _socket.emit('message', {
                    userid : _userid,
                    nickname : _nickname,
                    msg : _msg
                });
                _chat_input.val("");
            }
        }

        // 更新系统信息，用户加入、退出
        function buildSysMsg(o, action) {
            // 当前在线用户列表
            var _online_users = o.onlineUsers;
            // 新加入用户的信息
            var _new_user = o.user;

            // 更新在线人数
            var _userlist_html = '';
            for ( var key in _online_users) {
                if (_online_users.hasOwnProperty(key)) {
                    _userlist_html += "<li><a href='#'>" + _online_users[key] + "</a></li>";
                }
            }
            _userlist_container.html(_userlist_html);

            // 添加系统消息
            var _html = '';
            _html += '<div class="msg-system"><span>';
            _html += _new_user.nickname;
            _html += (action == 'login') ? ' 加入了聊天室' : ' 退出了聊天室';
            _html += '</span></div>';
            _content_container.append(_html);
            _content_container.animate({
                scrollTop : $(document).height()
            }, 0);
        }

        // 生成用户id
        function buildUserId() {
            return new Date().getTime() + "" + Math.floor(Math.random() * 899 + 100);
        }

        // 初始化事件
        function initEvent() {
            _nickname_input.on("keydown", function(e) {
                e = e || event;
                if (e.keyCode === 13) {
                    submitNickName();
                }
            });

            _chat_input.on("keydown", function(e) {
                e = e || event;
                if (e.keyCode === 13) {
                    submitMsg();
                }
            });

            _form_signin.find("button").on("click", function() {
                submitNickName();
            });

            _sendbtn.on("click", function() {
                submitMsg();
            });
            _logoutBtn.on("click", function() {
                window.location.reload();
            });
        }
        // 提交昵称
        function submitNickName() {
            if (_nickname_input.val()) {
                _form_signin.hide();
                _sunny_chat_container.show();
                initClient(_nickname_input.val());
                _nickname_input.val("");
            } else {
                alert("请先输入昵称!");
            }
        }
        // 初始化客户端
        function initClient(nn) {
            _nickname = nn;
            _userid = buildUserId();
            $("#sunny-show-name").find("a").html(nn);

            // 告诉服务器端有用户登录
            _socket.emit('login', {
                userid : _userid,
                nickname : _nickname
            });

            // 监听新用户登录
            _socket.on('login', function(o) {
                buildSysMsg(o, "login");
            });

            // 监听用户退出
            _socket.on('logout', function(o) {
                buildSysMsg(o, "loginout");
            });

            // 监听消息发送
            _socket.on('message', function(obj) {
                // 添加系统消息
                var _html = '';
                _html += '<div class="user-msg-container">';
                _html += '<div class="user-nickname">' + obj.nickname + '&nbsp;' + new Date().format("yyyy/MM/dd hh:mm:ss") + '</div>';
                _html += '<div class="user-msg">' + wrapItem(obj.msg) + '</div>';
                _html += '</div>';
                _content_container.append(_html);
                _content_container.animate({
                    scrollTop : $(document).height()
                }, 0);
            });
        }

        initEvent();
    }

    $.fn.SunnyChat = function(options) {
        $.SunnyChat(this, options);
    }
    $.SunnyChat.DEFAULTS = {
        userListContainer : ".user-list",// 用户列表容器
        contentContainer : "#sunny-chat-info",// 聊天内容显示框
        chatInput : "#sunny-chat-input",// 聊天输入框
        contentMaxSize : 100,// 聊天内容最大长度
        sendBtn : "#sunny-chat-send",
        logoutBtn : "#sunny-chat-logout",
        renderData : function(results) {// 数据渲染
        }
    }

    Date.prototype.format = function(format) {
        var o = {
            "M+" : this.getMonth() + 1, // month
            "d+" : this.getDate(), // day
            "h+" : this.getHours(), // hour
            "m+" : this.getMinutes(), // minute
            "s+" : this.getSeconds(), // second
            "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
            "S" : this.getMilliseconds()
        }

        if (/(y+)/.test(format)) {
            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }

        for ( var k in o) {
            if (new RegExp("(" + k + ")").test(format)) {
                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
            }
        }
        return format;
    }
})(jQuery);