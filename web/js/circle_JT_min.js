/* 
 *auther:wjt
 *url:https://github.com/wjtGitHub/circle_JT
 * */
(function($) {
    var startRadian = -0.5 * Math.PI;
    var cancvsObj;
    var endRadian;
    var circleObj;

    function DrawClock(circle) {
        var drawtimer = window.setInterval(function() {
            var X = 0;
            cancvsObj = document.getElementById("Circle_JT_cancvsDom" + circle.domId);
            // console.log(cancvsObj);
            circleObj = cancvsObj.getContext('2d');
            console.log(circleObj);
            if (circle.totalValue == 99) {
                circle.pbColor = "#ff0103";
                circleObj.strokeStyle = "#8c0102";
                X = circle.value * 2 / 99;
            } else if (circle.totalValue == 10) {
                circle.pbColor = "#009a00";
                circleObj.strokeStyle = "#005500";
                X = circle.value * 2 / 10;
            }
            circleObj.clearRect(0, 0, circle.radius * 3, circle.radius * 3);
            circleObj.lineWidth = circle.pbWidth
            circleObj.lineCap = 'round';
            circleObj.beginPath();
            circleObj.arc(circle.radius + 10, circle.radius + 10, circle.radius, 0, 2 * Math.PI, false);
            circleObj.stroke();
            if (X == 0.5) {
                endRadian = 0
            } else {
                endRadian = (X - 0.5) * Math.PI
            }
            circleObj.lineWidth = circle.pbWidth
            circleObj.lineCap = 'round';
            circleObj.strokeStyle = circle.pbColor;
            circleObj.beginPath();
            circleObj.arc(circle.radius + 10, circle.radius + 10, circle.radius, startRadian, endRadian, false);
            circleObj.stroke();
            if ((circle.totalValue - circle.value) <= 0) {
                clearInterval(drawtimer);
            }
        }, 1000);
        return "ok"
    }

    function createElementFun(circle) {
        var innerDom = document.createElement("div");
        var side = Math.sqrt(circle.radius * circle.radius + circle.radius * circle.radius);
        var horn = (circle.radius + 10 - side / 2) + circle.pbWidth / 2;
        innerDom.setAttribute("id", "Circle_JT_innerDom" + circle.domId);
        innerDom.setAttribute("class", "Circle_JT_innerDom");
        innerDom.style.position = "absolute";
        innerDom.style.width = side - circle.pbWidth + "px";
        innerDom.style.height = side - circle.pbWidth + "px";
        innerDom.style.left = horn + "px";
        innerDom.style.top = horn + "px";
        innerDom.style.zIndex = 999;
        innerDom.style.textAlign = 'center';
        innerDom.style.lineHeight = side - 10 + "px";
        innerDom.style.color = "#afb0b2";
        innerDom.style.fontSize = circle.fontSize + "px";
        var timeOut = setInterval(function() {
            circle.value++;
            innerDom.innerHTML = circle.totalValue - circle.value + "s";
            if ((circle.totalValue - circle.value) <= 0) {
                innerDom.innerHTML = "0s";
                clearInterval(timeOut);
            }
        }, 1000);

        var cancvsDom = document.createElement("canvas");
        cancvsDom.setAttribute("id", "Circle_JT_cancvsDom" + circle.domId);
        cancvsDom.setAttribute("class", "Circle_JT_cancvsDom");
        cancvsDom.setAttribute("height", circle.radius * 2 + 20);
        cancvsDom.setAttribute("width", circle.radius * 2 + 20);
        var firstDom = document.createElement("div");
        firstDom.setAttribute("id", "Circle_JT_firstDom" + circle.domId);
        firstDom.setAttribute("height", circle.radius * 2 + 20);
        firstDom.setAttribute("width", circle.radius * 2 + 20);
        firstDom.style.position = "relative";
        firstDom.appendChild(cancvsDom);
        firstDom.appendChild(innerDom);
        var ZeroDom = document.getElementById(circle.domId);
        ZeroDom.appendChild(firstDom)
    }
    $.fn.circleJt = function(circle) {
        var Domid = circle.domId;
        circle.pbWidth = 7;
        if (Domid.indexOf('tan') != -1) { // 有tan
            circle.totalValue = 99;
            circle.radius = 40;
            circle.fontSize = 28;
            console.log(circle.radius);
        } else { // mei有tan
            circle.totalValue = 10;
            circle.radius = 20;
            circle.fontSize = 14;
        }
        createElementFun(circle);
        DrawClock(circle);

    }
})(jQuery);