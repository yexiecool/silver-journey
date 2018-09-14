!function (e) {
    "use strict";
    function t(e) {
        if (e) {
            if ("string" == typeof o[e])return e;
            e = e.charAt(0).toUpperCase() + e.slice(1);
            for (var t, r = 0, i = n.length; i > r; r++)if (t = n[r] + e, "string" == typeof o[t])return t
        }
    }

    var n = "Webkit Moz ms Ms O".split(" "), o = document.documentElement.style;
    "function" == typeof define && define.amd ? define(function () {
        return t
    }) : "object" == typeof exports ? module.exports = t : e.getStyleProperty = t
}(window);
!function (t) {
    "use strict";
    function e(t) {
        var e = parseFloat(t), r = -1 === t.indexOf("%") && !isNaN(e);
        return r && e
    }

    function r() {
    }

    function i() {
        for (var t = {
            width: 0,
            height: 0,
            innerWidth: 0,
            innerHeight: 0,
            outerWidth: 0,
            outerHeight: 0
        }, e = 0, r = d.length; r > e; e++) {
            var i = d[e];
            t[i] = 0
        }
        return t
    }

    function n(r) {
        function n() {
            if (!p) {
                p = !0;
                var i = t.getComputedStyle;
                if (a = function () {
                        var t = i ? function (t) {
                            return i(t, null)
                        } : function (t) {
                            return t.currentStyle
                        };
                        return function (e) {
                            var r = t(e);
                            return r || o("Style returned " + r + ". Are you running this code in a hidden iframe on Firefox? See http://bit.ly/getsizebug1"), r
                        }
                    }(), u = r("boxSizing")) {
                    var n = document.createElement("div");
                    n.style.width = "200px", n.style.padding = "1px 2px 3px 4px", n.style.borderStyle = "solid", n.style.borderWidth = "1px 2px 3px 4px", n.style[u] = "border-box";
                    var d = document.body || document.documentElement;
                    d.appendChild(n);
                    var f = a(n);
                    g = 200 === e(f.width), d.removeChild(n)
                }
            }
        }

        function f(t) {
            if (n(), "string" == typeof t && (t = document.querySelector(t)), t && "object" == typeof t && t.nodeType) {
                var r = a(t);
                if ("none" === r.display)return i();
                var o = {};
                o.width = t.offsetWidth, o.height = t.offsetHeight;
                for (var f = o.isBorderBox = !(!u || !r[u] || "border-box" !== r[u]), p = 0, l = d.length; l > p; p++) {
                    var y = d[p], c = r[y];
                    c = h(t, c);
                    var m = parseFloat(c);
                    o[y] = isNaN(m) ? 0 : m
                }
                var s = o.paddingLeft + o.paddingRight, v = o.paddingTop + o.paddingBottom, b = o.marginLeft + o.marginRight, x = o.marginTop + o.marginBottom, W = o.borderLeftWidth + o.borderRightWidth, S = o.borderTopWidth + o.borderBottomWidth, w = f && g, B = e(r.width);
                B !== !1 && (o.width = B + (w ? 0 : s + W));
                var L = e(r.height);
                return L !== !1 && (o.height = L + (w ? 0 : v + S)), o.innerWidth = o.width - (s + W), o.innerHeight = o.height - (v + S), o.outerWidth = o.width + b, o.outerHeight = o.height + x, o
            }
        }

        function h(e, r) {
            if (t.getComputedStyle || -1 === r.indexOf("%"))return r;
            var i = e.style, n = i.left, o = e.runtimeStyle, d = o && o.left;
            return d && (o.left = e.currentStyle.left), i.left = r, r = i.pixelLeft, i.left = n, d && (o.left = d), r
        }

        var a, u, g, p = !1;
        return f
    }

    var o = "undefined" == typeof console ? r : function (t) {
        console.error(t)
    }, d = ["paddingLeft", "paddingRight", "paddingTop", "paddingBottom", "marginLeft", "marginRight", "marginTop", "marginBottom", "borderLeftWidth", "borderRightWidth", "borderTopWidth", "borderBottomWidth"];
    "function" == typeof define && define.amd ? define(["get-style-property/get-style-property"], n) : "object" == typeof exports ? module.exports = n(require("desandro-get-style-property")) : t.getSize = n(t.getStyleProperty)
}(window);
!function (e) {
    "use strict";
    function t(e, t) {
        return e[i](t)
    }

    function n(e) {
        if (!e.parentNode) {
            var t = document.createDocumentFragment();
            t.appendChild(e)
        }
    }

    function r(e, t) {
        n(e);
        for (var r = e.parentNode.querySelectorAll(t), o = 0, c = r.length; c > o; o++)if (r[o] === e)return !0;
        return !1
    }

    function o(e, r) {
        return n(e), t(e, r)
    }

    var c, i = function () {
        if (e.matches)return "matches";
        if (e.matchesSelector)return "matchesSelector";
        for (var t = ["webkit", "moz", "ms", "o"], n = 0, r = t.length; r > n; n++) {
            var o = t[n], c = o + "MatchesSelector";
            if (e[c])return c
        }
    }();
    if (i) {
        var u = document.createElement("div"), f = t(u, "div");
        c = f ? t : o
    } else c = r;
    "function" == typeof define && define.amd ? define(function () {
        return c
    }) : "object" == typeof exports ? module.exports = c : window.matchesSelector = c
}(Element.prototype);
(function () {
    "use strict";
    function e() {
    }

    function t(e, t) {
        for (var n = e.length; n--;)if (e[n].listener === t)return n;
        return -1
    }

    function n(e) {
        return function () {
            return this[e].apply(this, arguments)
        }
    }

    var r = e.prototype, i = this, s = i.EventEmitter;
    r.getListeners = function (e) {
        var t, n, r = this._getEvents();
        if (e instanceof RegExp) {
            t = {};
            for (n in r)r.hasOwnProperty(n) && e.test(n) && (t[n] = r[n])
        } else t = r[e] || (r[e] = []);
        return t
    }, r.flattenListeners = function (e) {
        var t, n = [];
        for (t = 0; t < e.length; t += 1)n.push(e[t].listener);
        return n
    }, r.getListenersAsObject = function (e) {
        var t, n = this.getListeners(e);
        return n instanceof Array && (t = {}, t[e] = n), t || n
    }, r.addListener = function (e, n) {
        var r, i = this.getListenersAsObject(e), s = "object" == typeof n;
        for (r in i)i.hasOwnProperty(r) && -1 === t(i[r], n) && i[r].push(s ? n : {listener: n, once: !1});
        return this
    }, r.on = n("addListener"), r.addOnceListener = function (e, t) {
        return this.addListener(e, {listener: t, once: !0})
    }, r.once = n("addOnceListener"), r.defineEvent = function (e) {
        return this.getListeners(e), this
    }, r.defineEvents = function (e) {
        for (var t = 0; t < e.length; t += 1)this.defineEvent(e[t]);
        return this
    }, r.removeListener = function (e, n) {
        var r, i, s = this.getListenersAsObject(e);
        for (i in s)s.hasOwnProperty(i) && (r = t(s[i], n), -1 !== r && s[i].splice(r, 1));
        return this
    }, r.off = n("removeListener"), r.addListeners = function (e, t) {
        return this.manipulateListeners(!1, e, t)
    }, r.removeListeners = function (e, t) {
        return this.manipulateListeners(!0, e, t)
    }, r.manipulateListeners = function (e, t, n) {
        var r, i, s = e ? this.removeListener : this.addListener, o = e ? this.removeListeners : this.addListeners;
        if ("object" != typeof t || t instanceof RegExp)for (r = n.length; r--;)s.call(this, t, n[r]); else for (r in t)t.hasOwnProperty(r) && (i = t[r]) && ("function" == typeof i ? s.call(this, r, i) : o.call(this, r, i));
        return this
    }, r.removeEvent = function (e) {
        var t, n = typeof e, r = this._getEvents();
        if ("string" === n)delete r[e]; else if (e instanceof RegExp)for (t in r)r.hasOwnProperty(t) && e.test(t) && delete r[t]; else delete this._events;
        return this
    }, r.removeAllListeners = n("removeEvent"), r.emitEvent = function (e, t) {
        var n, r, i, s, o = this.getListenersAsObject(e);
        for (i in o)if (o.hasOwnProperty(i))for (r = o[i].length; r--;)n = o[i][r], n.once === !0 && this.removeListener(e, n.listener), s = n.listener.apply(this, t || []), s === this._getOnceReturnValue() && this.removeListener(e, n.listener);
        return this
    }, r.trigger = n("emitEvent"), r.emit = function (e) {
        var t = Array.prototype.slice.call(arguments, 1);
        return this.emitEvent(e, t)
    }, r.setOnceReturnValue = function (e) {
        return this._onceReturnValue = e, this
    }, r._getOnceReturnValue = function () {
        return this.hasOwnProperty("_onceReturnValue") ? this._onceReturnValue : !0
    }, r._getEvents = function () {
        return this._events || (this._events = {})
    }, e.noConflict = function () {
        return i.EventEmitter = s, e
    }, "function" == typeof define && define.amd ? define(function () {
        return e
    }) : "object" == typeof module && module.exports ? module.exports = e : i.EventEmitter = e
}).call(this);
!function (e) {
    "use strict";
    function n(n) {
        var t = e.event;
        return t.target = t.target || t.srcElement || n, t
    }

    var t = document.documentElement, o = function () {
    };
    t.addEventListener ? o = function (e, n, t) {
        e.addEventListener(n, t, !1)
    } : t.attachEvent && (o = function (e, t, o) {
        e[t + o] = o.handleEvent ? function () {
            var t = n(e);
            o.handleEvent.call(o, t)
        } : function () {
            var t = n(e);
            o.call(e, t)
        }, e.attachEvent("on" + t, e[t + o])
    });
    var c = function () {
    };
    t.removeEventListener ? c = function (e, n, t) {
        e.removeEventListener(n, t, !1)
    } : t.detachEvent && (c = function (e, n, t) {
        e.detachEvent("on" + n, e[n + t]);
        try {
            delete e[n + t]
        } catch (o) {
            e[n + t] = void 0
        }
    });
    var i = {bind: o, unbind: c};
    "function" == typeof define && define.amd ? define(i) : "object" == typeof exports ? module.exports = i : e.eventie = i
}(window);
!function (e) {
    "use strict";
    function t(e) {
        "function" == typeof e && (t.isReady ? e() : a.push(e))
    }

    function n(e) {
        var n = "readystatechange" === e.type && "complete" !== o.readyState;
        t.isReady || n || i()
    }

    function i() {
        t.isReady = !0;
        for (var e = 0, n = a.length; n > e; e++) {
            var i = a[e];
            i()
        }
    }

    function d(d) {
        return "complete" === o.readyState ? i() : (d.bind(o, "DOMContentLoaded", n), d.bind(o, "readystatechange", n), d.bind(e, "load", n)), t
    }

    var o = e.document, a = [];
    t.isReady = !1, "function" == typeof define && define.amd ? define(["eventie/eventie"], d) : "object" == typeof exports ? module.exports = d(require("eventie")) : e.docReady = d(e.eventie)
}(window);
!function (s) {
    "use strict";
    function e(s) {
        return new RegExp("(^|\\s+)" + s + "(\\s+|$)")
    }

    function n(s, e) {
        var n = t(s, e) ? c : a;
        n(s, e)
    }

    var t, a, c;
    "classList"in document.documentElement ? (t = function (s, e) {
        return s.classList.contains(e)
    }, a = function (s, e) {
        s.classList.add(e)
    }, c = function (s, e) {
        s.classList.remove(e)
    }) : (t = function (s, n) {
        return e(n).test(s.className)
    }, a = function (s, e) {
        t(s, e) || (s.className = s.className + " " + e)
    }, c = function (s, n) {
        s.className = s.className.replace(e(n), " ")
    });
    var o = {hasClass: t, addClass: a, removeClass: c, toggleClass: n, has: t, add: a, remove: c, toggle: n};
    "function" == typeof define && define.amd ? define(o) : "object" == typeof exports ? module.exports = o : s.classie = o
}(window);
!function (e, t) {
    "use strict";
    "function" == typeof define && define.amd ? define(["doc-ready/doc-ready", "matches-selector/matches-selector"], function (n, r) {
        return t(e, n, r)
    }) : "object" == typeof exports ? module.exports = t(e, require("doc-ready"), require("desandro-matches-selector")) : e.fizzyUIUtils = t(e, e.docReady, e.matchesSelector)
}(window, function (e, t, n) {
    "use strict";
    var r = {};
    r.extend = function (e, t) {
        for (var n in t)e[n] = t[n];
        return e
    }, r.modulo = function (e, t) {
        return (e % t + t) % t
    };
    var o = Object.prototype.toString;
    r.isArray = function (e) {
        return "[object Array]" == o.call(e)
    }, r.makeArray = function (e) {
        var t = [];
        if (r.isArray(e))t = e; else if (e && "number" == typeof e.length)for (var n = 0, o = e.length; o > n; n++)t.push(e[n]); else t.push(e);
        return t
    }, r.indexOf = Array.prototype.indexOf ? function (e, t) {
        return e.indexOf(t)
    } : function (e, t) {
        for (var n = 0, r = e.length; r > n; n++)if (e[n] === t)return n;
        return -1
    }, r.removeFrom = function (e, t) {
        var n = r.indexOf(e, t);
        -1 != n && e.splice(n, 1)
    }, r.isElement = "function" == typeof HTMLElement || "object" == typeof HTMLElement ? function (e) {
        return e instanceof HTMLElement
    } : function (e) {
        return e && "object" == typeof e && 1 == e.nodeType && "string" == typeof e.nodeName
    }, r.setText = function () {
        function e(e, n) {
            t = t || (void 0 !== document.documentElement.textContent ? "textContent" : "innerText"), e[t] = n
        }

        var t;
        return e
    }(), r.getParent = function (e, t) {
        for (; e != document.body;)if (e = e.parentNode, n(e, t))return e
    }, r.getQueryElement = function (e) {
        return "string" == typeof e ? document.querySelector(e) : e
    }, r.handleEvent = function (e) {
        var t = "on" + e.type;
        this[t] && this[t](e)
    }, r.filterFindElements = function (e, t) {
        e = r.makeArray(e);
        for (var o = [], i = 0, u = e.length; u > i; i++) {
            var c = e[i];
            if (r.isElement(c))if (t) {
                n(c, t) && o.push(c);
                for (var a = c.querySelectorAll(t), f = 0, s = a.length; s > f; f++)o.push(a[f])
            } else o.push(c)
        }
        return o
    }, r.debounceMethod = function (e, t, n) {
        var r = e.prototype[t], o = t + "Timeout";
        e.prototype[t] = function () {
            var e = this[o];
            e && clearTimeout(e);
            var t = arguments, i = this;
            this[o] = setTimeout(function () {
                r.apply(i, t), delete i[o]
            }, n || 100)
        }
    }, r.toDashed = function (e) {
        return e.replace(/(.)([A-Z])/g, function (e, t, n) {
            return t + "-" + n
        }).toLowerCase()
    };
    var i = e.console;
    return r.htmlInit = function (n, o) {
        t(function () {
            for (var t = r.toDashed(o), u = document.querySelectorAll(".js-" + t), c = "data-" + t + "-options", a = 0, f = u.length; f > a; a++) {
                var s, d = u[a], l = d.getAttribute(c);
                try {
                    s = l && JSON.parse(l)
                } catch (p) {
                    i && i.error("Error parsing " + c + " on " + d.nodeName.toLowerCase() + (d.id ? "#" + d.id : "") + ": " + p);
                    continue
                }
                var y = new n(d, s), m = e.jQuery;
                m && m.data(d, o, y)
            }
        })
    }, r
});
!function (t, n) {
    "use strict";
    "function" == typeof define && define.amd ? define(["eventEmitter/EventEmitter", "eventie/eventie"], function (o, e) {
        return n(t, o, e)
    }) : "object" == typeof exports ? module.exports = n(t, require("wolfy87-eventemitter"), require("eventie")) : t.Unipointer = n(t, t.EventEmitter, t.eventie)
}(window, function (t, n, o) {
    "use strict";
    function e() {
    }

    function i() {
    }

    i.prototype = new n, i.prototype.bindStartEvent = function (t) {
        this._bindStartEvent(t, !0)
    }, i.prototype.unbindStartEvent = function (t) {
        this._bindStartEvent(t, !1)
    }, i.prototype._bindStartEvent = function (n, e) {
        e = void 0 === e ? !0 : !!e;
        var i = e ? "bind" : "unbind";
        t.navigator.pointerEnabled ? o[i](n, "pointerdown", this) : t.navigator.msPointerEnabled ? o[i](n, "MSPointerDown", this) : (o[i](n, "mousedown", this), o[i](n, "touchstart", this))
    }, i.prototype.handleEvent = function (t) {
        var n = "on" + t.type;
        this[n] && this[n](t)
    }, i.prototype.getTouch = function (t) {
        for (var n = 0, o = t.length; o > n; n++) {
            var e = t[n];
            if (e.identifier == this.pointerIdentifier)return e
        }
    }, i.prototype.onmousedown = function (t) {
        var n = t.button;
        n && 0 !== n && 1 !== n || this._pointerDown(t, t)
    }, i.prototype.ontouchstart = function (t) {
        this._pointerDown(t, t.changedTouches[0])
    }, i.prototype.onMSPointerDown = i.prototype.onpointerdown = function (t) {
        this._pointerDown(t, t)
    }, i.prototype._pointerDown = function (t, n) {
        this.isPointerDown || (this.isPointerDown = !0, this.pointerIdentifier = void 0 !== n.pointerId ? n.pointerId : n.identifier, this.pointerDown(t, n))
    }, i.prototype.pointerDown = function (t, n) {
        this._bindPostStartEvents(t), this.emitEvent("pointerDown", [t, n])
    };
    var r = {
        mousedown: ["mousemove", "mouseup"],
        touchstart: ["touchmove", "touchend", "touchcancel"],
        pointerdown: ["pointermove", "pointerup", "pointercancel"],
        MSPointerDown: ["MSPointerMove", "MSPointerUp", "MSPointerCancel"]
    };
    return i.prototype._bindPostStartEvents = function (n) {
        if (n) {
            for (var e = r[n.type], i = n.preventDefault ? t : document, p = 0, s = e.length; s > p; p++) {
                var u = e[p];
                o.bind(i, u, this)
            }
            this._boundPointerEvents = {events: e, node: i}
        }
    }, i.prototype._unbindPostStartEvents = function () {
        var t = this._boundPointerEvents;
        if (t && t.events) {
            for (var n = 0, e = t.events.length; e > n; n++) {
                var i = t.events[n];
                o.unbind(t.node, i, this)
            }
            delete this._boundPointerEvents
        }
    }, i.prototype.onmousemove = function (t) {
        this._pointerMove(t, t)
    }, i.prototype.onMSPointerMove = i.prototype.onpointermove = function (t) {
        t.pointerId == this.pointerIdentifier && this._pointerMove(t, t)
    }, i.prototype.ontouchmove = function (t) {
        var n = this.getTouch(t.changedTouches);
        n && this._pointerMove(t, n)
    }, i.prototype._pointerMove = function (t, n) {
        this.pointerMove(t, n)
    }, i.prototype.pointerMove = function (t, n) {
        this.emitEvent("pointerMove", [t, n])
    }, i.prototype.onmouseup = function (t) {
        this._pointerUp(t, t)
    }, i.prototype.onMSPointerUp = i.prototype.onpointerup = function (t) {
        t.pointerId == this.pointerIdentifier && this._pointerUp(t, t)
    }, i.prototype.ontouchend = function (t) {
        var n = this.getTouch(t.changedTouches);
        n && this._pointerUp(t, n)
    }, i.prototype._pointerUp = function (t, n) {
        this._pointerDone(), this.pointerUp(t, n)
    }, i.prototype.pointerUp = function (t, n) {
        this.emitEvent("pointerUp", [t, n])
    }, i.prototype._pointerDone = function () {
        this.isPointerDown = !1, delete this.pointerIdentifier, this._unbindPostStartEvents(), this.pointerDone()
    }, i.prototype.pointerDone = e, i.prototype.onMSPointerCancel = i.prototype.onpointercancel = function (t) {
        t.pointerId == this.pointerIdentifier && this._pointerCancel(t, t)
    }, i.prototype.ontouchcancel = function (t) {
        var n = this.getTouch(t.changedTouches);
        n && this._pointerCancel(t, n)
    }, i.prototype._pointerCancel = function (t, n) {
        this._pointerDone(), this.pointerCancel(t, n)
    }, i.prototype.pointerCancel = function (t, n) {
        this.emitEvent("pointerCancel", [t, n])
    }, i.getPointerPoint = function (t) {
        return {x: void 0 !== t.pageX ? t.pageX : t.clientX, y: void 0 !== t.pageY ? t.pageY : t.clientY}
    }, i
});
!function (t, n) {
    "use strict";
    "function" == typeof define && define.amd ? define(["eventie/eventie", "unipointer/unipointer"], function (e, i) {
        return n(t, e, i)
    }) : "object" == typeof exports ? module.exports = n(t, require("eventie"), require("unipointer")) : t.Unidragger = n(t, t.eventie, t.Unipointer)
}(window, function (t, n, e) {
    "use strict";
    function i() {
    }

    function o(t) {
        t.preventDefault ? t.preventDefault() : t.returnValue = !1
    }

    function r() {
    }

    function s() {
        return !1
    }

    r.prototype = new e, r.prototype.bindHandles = function () {
        this._bindHandles(!0)
    }, r.prototype.unbindHandles = function () {
        this._bindHandles(!1)
    };
    var a = t.navigator;
    r.prototype._bindHandles = function (t) {
        t = void 0 === t ? !0 : !!t;
        var e;
        e = a.pointerEnabled ? function (n) {
            n.style.touchAction = t ? "none" : ""
        } : a.msPointerEnabled ? function (n) {
            n.style.msTouchAction = t ? "none" : ""
        } : function () {
            t && c(s)
        };
        for (var i = t ? "bind" : "unbind", o = 0, r = this.handles.length; r > o; o++) {
            var s = this.handles[o];
            this._bindStartEvent(s, t), e(s), n[i](s, "click", this)
        }
    };
    var p = "attachEvent"in document.documentElement, c = p ? function (t) {
        "IMG" == t.nodeName && (t.ondragstart = s);
        for (var n = t.querySelectorAll("img"), e = 0, i = n.length; i > e; e++) {
            var o = n[e];
            o.ondragstart = s
        }
    } : i;
    r.prototype.pointerDown = function (e, i) {
        if ("INPUT" == e.target.nodeName && "range" == e.target.type)return this.isPointerDown = !1, void delete this.pointerIdentifier;
        this._dragPointerDown(e, i);
        var o = document.activeElement;
        o && o.blur && o.blur(), this._bindPostStartEvents(e), this.pointerDownScroll = r.getScrollPosition(), n.bind(t, "scroll", this), this.emitEvent("pointerDown", [e, i])
    }, r.prototype._dragPointerDown = function (t, n) {
        this.pointerDownPoint = e.getPointerPoint(n);
        var i = "touchstart" == t.type, r = t.target.nodeName;
        i || "SELECT" == r || o(t)
    }, r.prototype.pointerMove = function (t, n) {
        var e = this._dragPointerMove(t, n);
        this.emitEvent("pointerMove", [t, n, e]), this._dragMove(t, n, e)
    }, r.prototype._dragPointerMove = function (t, n) {
        var i = e.getPointerPoint(n), o = {x: i.x - this.pointerDownPoint.x, y: i.y - this.pointerDownPoint.y};
        return !this.isDragging && this.hasDragStarted(o) && this._dragStart(t, n), o
    }, r.prototype.hasDragStarted = function (t) {
        return Math.abs(t.x) > 3 || Math.abs(t.y) > 3
    }, r.prototype.pointerUp = function (t, n) {
        this.emitEvent("pointerUp", [t, n]), this._dragPointerUp(t, n)
    }, r.prototype._dragPointerUp = function (t, n) {
        this.isDragging ? this._dragEnd(t, n) : this._staticClick(t, n)
    }, e.prototype.pointerDone = function () {
        n.unbind(t, "scroll", this)
    }, r.prototype._dragStart = function (t, n) {
        this.isDragging = !0, this.dragStartPoint = r.getPointerPoint(n), this.isPreventingClicks = !0, this.dragStart(t, n)
    }, r.prototype.dragStart = function (t, n) {
        this.emitEvent("dragStart", [t, n])
    }, r.prototype._dragMove = function (t, n, e) {
        this.isDragging && this.dragMove(t, n, e)
    }, r.prototype.dragMove = function (t, n, e) {
        o(t), this.emitEvent("dragMove", [t, n, e])
    }, r.prototype._dragEnd = function (t, n) {
        this.isDragging = !1;
        var e = this;
        setTimeout(function () {
            delete e.isPreventingClicks
        }), this.dragEnd(t, n)
    }, r.prototype.dragEnd = function (t, n) {
        this.emitEvent("dragEnd", [t, n])
    }, r.prototype.pointerDone = function () {
        n.unbind(t, "scroll", this), delete this.pointerDownScroll
    }, r.prototype.onclick = function (t) {
        this.isPreventingClicks && o(t)
    }, r.prototype._staticClick = function (t, n) {
        if (!this.isIgnoringMouseUp || "mouseup" != t.type) {
            var e = t.target.nodeName;
            if (("INPUT" == e || "TEXTAREA" == e) && t.target.focus(), this.staticClick(t, n), "mouseup" != t.type) {
                this.isIgnoringMouseUp = !0;
                var i = this;
                setTimeout(function () {
                    delete i.isIgnoringMouseUp
                }, 400)
            }
        }
    }, r.prototype.staticClick = function (t, n) {
        this.emitEvent("staticClick", [t, n])
    }, r.prototype.onscroll = function () {
        var t = r.getScrollPosition(), n = this.pointerDownScroll.x - t.x, e = this.pointerDownScroll.y - t.y;
        (Math.abs(n) > 3 || Math.abs(e) > 3) && this._pointerDone()
    }, r.getPointerPoint = function (t) {
        return {x: void 0 !== t.pageX ? t.pageX : t.clientX, y: void 0 !== t.pageY ? t.pageY : t.clientY}
    };
    var u = void 0 !== t.pageYOffset;
    return r.getScrollPosition = function () {
        return {x: u ? t.pageXOffset : document.body.scrollLeft, y: u ? t.pageYOffset : document.body.scrollTop}
    }, r.getPointerPoint = e.getPointerPoint, r
});
!function (t, e) {
    "use strict";
    "function" == typeof define && define.amd ? define(["unipointer/unipointer"], function (n) {
        return e(t, n)
    }) : "object" == typeof exports ? module.exports = e(t, require("unipointer")) : t.TapListener = e(t, t.Unipointer)
}(window, function (t, e) {
    "use strict";
    function n(t) {
        t.preventDefault ? t.preventDefault() : t.returnValue = !1
    }

    function o(t) {
        this.bindTap(t)
    }

    o.prototype = new e, o.prototype.bindTap = function (t) {
        t && (this.unbindTap(), this.tapElement = t, this._bindStartEvent(t, !0))
    }, o.prototype.unbindTap = function () {
        this.tapElement && (this._bindStartEvent(this.tapElement, !0), delete this.tapElement)
    };
    var i = o.prototype.pointerDown;
    o.prototype.pointerDown = function (t) {
        "touchstart" == t.type && n(t), i.apply(this, arguments)
    };
    var p = void 0 !== t.pageYOffset;
    return o.prototype.pointerUp = function (n, o) {
        var i = e.getPointerPoint(o), r = this.tapElement.getBoundingClientRect(), u = p ? t.pageXOffset : document.body.scrollLeft, s = p ? t.pageYOffset : document.body.scrollTop, a = i.x >= r.left + u && i.x <= r.right + u && i.y >= r.top + s && i.y <= r.bottom + s;
        a && this.emitEvent("tap", [n, o])
    }, o.prototype.destroy = function () {
        this.pointerDone(), this.unbindTap()
    }, o
});
!function (t, e) {
    "use strict";
    "function" == typeof define && define.amd ? define(["get-size/get-size"], function (i) {
        return e(t, i)
    }) : "object" == typeof exports ? module.exports = e(t, require("get-size")) : (t.Flickity = t.Flickity || {}, t.Flickity.Cell = e(t, t.getSize))
}(window, function (t, e) {
    "use strict";
    function i(t, e) {
        this.element = t, this.parent = e, this.create()
    }

    var n = "attachEvent"in t;
    return i.prototype.create = function () {
        this.element.style.position = "absolute", n && this.element.setAttribute("unselectable", "on"), this.x = 0, this.shift = 0
    }, i.prototype.destroy = function () {
        this.element.style.position = "";
        var t = this.parent.originSide;
        this.element.style[t] = ""
    }, i.prototype.getSize = function () {
        this.size = e(this.element)
    }, i.prototype.setPosition = function (t) {
        this.x = t, this.setDefaultTarget(), this.renderPosition(t)
    }, i.prototype.setDefaultTarget = function () {
        var t = "left" == this.parent.originSide ? "marginLeft" : "marginRight";
        this.target = this.x + this.size[t] + this.size.width * this.parent.cellAlign
    }, i.prototype.renderPosition = function (t) {
        var e = this.parent.originSide;
        this.element.style[e] = this.parent.getPositionValue(t)
    }, i.prototype.wrapShift = function (t) {
        this.shift = t, this.renderPosition(this.x + this.parent.slideableWidth * t)
    }, i.prototype.remove = function () {
        this.element.parentNode.removeChild(this.element)
    }, i
});
!function (t, i) {
    "use strict";
    "function" == typeof define && define.amd ? define(["get-style-property/get-style-property", "fizzy-ui-utils/utils"], function (e, s) {
        return i(t, e, s)
    }) : "object" == typeof exports ? module.exports = i(t, require("desandro-get-style-property"), require("fizzy-ui-utils")) : (t.Flickity = t.Flickity || {}, t.Flickity.animatePrototype = i(t, t.getStyleProperty, t.fizzyUIUtils))
}(window, function (t, i, e) {
    "use strict";
    for (var s, n = 0, r = "webkit moz ms o".split(" "), o = t.requestAnimationFrame, h = t.cancelAnimationFrame, l = 0; l < r.length && (!o || !h); l++)s = r[l], o = o || t[s + "RequestAnimationFrame"], h = h || t[s + "CancelAnimationFrame"] || t[s + "CancelRequestAnimationFrame"];
    o && h || (o = function (i) {
        var e = (new Date).getTime(), s = Math.max(0, 16 - (e - n)), r = t.setTimeout(function () {
            i(e + s)
        }, s);
        return n = e + s, r
    }, h = function (i) {
        t.clearTimeout(i)
    });
    var a = {};
    a.startAnimation = function () {
        this.isAnimating || (this.isAnimating = !0, this.restingFrames = 0, this.animate())
    }, a.animate = function () {
        this.applyDragForce(), this.applySelectedAttraction();
        var t = this.x;
        if (this.integratePhysics(), this.positionSlider(), this.settle(t), this.isAnimating) {
            var i = this;
            o(function () {
                i.animate()
            })
        }
    };
    var c = i("transform"), u = !!i("perspective");
    return a.positionSlider = function () {
        var t = this.x;
        this.options.wrapAround && this.cells.length > 1 && (t = e.modulo(t, this.slideableWidth), t -= this.slideableWidth, this.shiftWrapCells(t)), t += this.cursorPosition, t = this.options.rightToLeft && c ? -t : t;
        var i = this.getPositionValue(t);
        c ? this.slider.style[c] = u && this.isAnimating ? "translate3d(" + i + ",0,0)" : "translateX(" + i + ")" : this.slider.style[this.originSide] = i
    }, a.positionSliderAtSelected = function () {
        if (this.cells.length) {
            var t = this.cells[this.selectedIndex];
            this.x = -t.target, this.positionSlider()
        }
    }, a.getPositionValue = function (t) {
        return this.options.percentPosition ? .01 * Math.round(t / this.size.innerWidth * 1e4) + "%" : Math.round(t) + "px"
    }, a.settle = function (t) {
        this.isPointerDown || Math.round(100 * this.x) != Math.round(100 * t) || this.restingFrames++, this.restingFrames > 2 && (this.isAnimating = !1, delete this.isFreeScrolling, u && this.positionSlider(), this.dispatchEvent("settle"))
    }, a.shiftWrapCells = function (t) {
        var i = this.cursorPosition + t;
        this._shiftCells(this.beforeShiftCells, i, -1);
        var e = this.size.innerWidth - (t + this.slideableWidth + this.cursorPosition);
        this._shiftCells(this.afterShiftCells, e, 1)
    }, a._shiftCells = function (t, i, e) {
        for (var s = 0, n = t.length; n > s; s++) {
            var r = t[s], o = i > 0 ? e : 0;
            r.wrapShift(o), i -= r.size.outerWidth
        }
    }, a._unshiftCells = function (t) {
        if (t && t.length)for (var i = 0, e = t.length; e > i; i++)t[i].wrapShift(0)
    }, a.integratePhysics = function () {
        this.velocity += this.accel, this.x += this.velocity, this.velocity *= this.getFrictionFactor(), this.accel = 0
    }, a.applyForce = function (t) {
        this.accel += t
    }, a.getFrictionFactor = function () {
        return 1 - this.options[this.isFreeScrolling ? "freeScrollFriction" : "friction"]
    }, a.getRestingPosition = function () {
        return this.x + this.velocity / (1 - this.getFrictionFactor())
    }, a.applyDragForce = function () {
        if (this.isPointerDown) {
            var t = this.dragX - this.x, i = t - this.velocity;
            this.applyForce(i)
        }
    }, a.applySelectedAttraction = function () {
        var t = this.cells.length;
        if (!this.isPointerDown && !this.isFreeScrolling && t) {
            var i = this.cells[this.selectedIndex], e = this.options.wrapAround && t > 1 ? this.slideableWidth * Math.floor(this.selectedIndex / t) : 0, s = -1 * (i.target + e) - this.x, n = s * this.options.selectedAttraction;
            this.applyForce(n)
        }
    }, a
});
!function (t, e) {
    "use strict";
    if ("function" == typeof define && define.amd)define(["classie/classie", "eventEmitter/EventEmitter", "eventie/eventie", "get-size/get-size", "fizzy-ui-utils/utils", "./cell", "./animate"], function (i, s, l, n, o, r, h) {
        return e(t, i, s, l, n, o, r, h)
    }); else if ("object" == typeof exports)module.exports = e(t, require("desandro-classie"), require("wolfy87-eventemitter"), require("eventie"), require("get-size"), require("fizzy-ui-utils"), require("./cell"), require("./animate")); else {
        var i = t.Flickity;
        t.Flickity = e(t, t.classie, t.EventEmitter, t.eventie, t.getSize, t.fizzyUIUtils, i.Cell, i.animatePrototype)
    }
}(window, function (t, e, i, s, l, n, o, r) {
    "use strict";
    function h(t, e) {
        for (t = n.makeArray(t); t.length;)e.appendChild(t.shift())
    }

    function c(t, e) {
        var i = n.getQueryElement(t);
        return i ? (this.element = i, a && (this.$element = a(this.element)), this.options = n.extend({}, this.constructor.defaults), this.option(e), void this._create()) : void(p && p.error("Bad element for Flickity: " + (i || t)))
    }

    var a = t.jQuery, d = t.getComputedStyle, p = t.console, f = 0, u = {};
    c.defaults = {
        accessibility: !0,
        cellAlign: "center",
        freeScrollFriction: .075,
        friction: .28,
        percentPosition: !0,
        resize: !0,
        selectedAttraction: .025,
        setGallerySize: !0
    }, c.createMethods = [], n.extend(c.prototype, i.prototype), c.prototype._create = function () {
        var e = this.guid = ++f;
        this.element.flickityGUID = e, u[e] = this, this.selectedIndex = this.options.initialIndex || 0, this.restingFrames = 0, this.x = 0, this.velocity = 0, this.accel = 0, this.originSide = this.options.rightToLeft ? "right" : "left", this.viewport = document.createElement("div"), this.viewport.className = "flickity-viewport", c.setUnselectable(this.viewport), this._createSlider(), (this.options.resize || this.options.watchCSS) && (s.bind(t, "resize", this), this.isResizeBound = !0);
        for (var i = 0, l = c.createMethods.length; l > i; i++) {
            var n = c.createMethods[i];
            this[n]()
        }
        this.options.watchCSS ? this.watchCSS() : this.activate()
    }, c.prototype.option = function (t) {
        n.extend(this.options, t)
    }, c.prototype.activate = function () {
        if (!this.isActive) {
            this.isActive = !0, e.add(this.element, "flickity-enabled"), this.options.rightToLeft && e.add(this.element, "flickity-rtl"), this.getSize();
            var t = this._filterFindCellElements(this.element.children);
            h(t, this.slider), this.viewport.appendChild(this.slider), this.element.appendChild(this.viewport), this.reloadCells(), this.options.accessibility && (this.element.tabIndex = 0, s.bind(this.element, "keydown", this)), this.emit("activate"), this.positionSliderAtSelected(), this.select(this.selectedIndex)
        }
    }, c.prototype._createSlider = function () {
        var t = document.createElement("div");
        t.className = "flickity-slider", t.style[this.originSide] = 0, this.slider = t
    }, c.prototype._filterFindCellElements = function (t) {
        return n.filterFindElements(t, this.options.cellSelector)
    }, c.prototype.reloadCells = function () {
        this.cells = this._makeCells(this.slider.children), this.positionCells(), this._getWrapShiftCells(), this.setGallerySize()
    }, c.prototype._makeCells = function (t) {
        for (var e = this._filterFindCellElements(t), i = [], s = 0, l = e.length; l > s; s++) {
            var n = e[s], r = new o(n, this);
            i.push(r)
        }
        return i
    }, c.prototype.getLastCell = function () {
        return this.cells[this.cells.length - 1]
    }, c.prototype.positionCells = function () {
        this._sizeCells(this.cells), this._positionCells(0)
    }, c.prototype._positionCells = function (t) {
        t = t || 0, this.maxCellHeight = t ? this.maxCellHeight || 0 : 0;
        var e = 0;
        if (t > 0) {
            var i = this.cells[t - 1];
            e = i.x + i.size.outerWidth
        }
        for (var s, l = this.cells.length, n = t; l > n; n++)s = this.cells[n], s.setPosition(e), e += s.size.outerWidth, this.maxCellHeight = Math.max(s.size.outerHeight, this.maxCellHeight);
        this.slideableWidth = e, this._containCells()
    }, c.prototype._sizeCells = function (t) {
        for (var e = 0, i = t.length; i > e; e++) {
            var s = t[e];
            s.getSize()
        }
    }, c.prototype._init = c.prototype.reposition = function () {
        this.positionCells(), this.positionSliderAtSelected()
    }, c.prototype.getSize = function () {
        this.size = l(this.element), this.setCellAlign(), this.cursorPosition = this.size.innerWidth * this.cellAlign
    };
    var v = {center: {left: .5, right: .5}, left: {left: 0, right: 1}, right: {right: 0, left: 1}};
    c.prototype.setCellAlign = function () {
        var t = v[this.options.cellAlign];
        this.cellAlign = t ? t[this.originSide] : this.options.cellAlign
    }, c.prototype.setGallerySize = function () {
        this.options.setGallerySize && (this.viewport.style.height = this.maxCellHeight + "px")
    }, c.prototype._getWrapShiftCells = function () {
        if (this.options.wrapAround) {
            this._unshiftCells(this.beforeShiftCells), this._unshiftCells(this.afterShiftCells);
            var t = this.cursorPosition, e = this.cells.length - 1;
            this.beforeShiftCells = this._getGapCells(t, e, -1), t = this.size.innerWidth - this.cursorPosition, this.afterShiftCells = this._getGapCells(t, 0, 1)
        }
    }, c.prototype._getGapCells = function (t, e, i) {
        for (var s = []; t > 0;) {
            var l = this.cells[e];
            if (!l)break;
            s.push(l), e += i, t -= l.size.outerWidth
        }
        return s
    }, c.prototype._containCells = function () {
        if (this.options.contain && !this.options.wrapAround && this.cells.length)for (var t = this.options.rightToLeft ? "marginRight" : "marginLeft", e = this.options.rightToLeft ? "marginLeft" : "marginRight", i = this.cells[0].size[t], s = this.getLastCell(), l = this.slideableWidth - s.size[e], n = l - this.size.innerWidth * (1 - this.cellAlign), o = l < this.size.innerWidth, r = 0, h = this.cells.length; h > r; r++) {
            var c = this.cells[r];
            c.setDefaultTarget(), o ? c.target = l * this.cellAlign : (c.target = Math.max(c.target, this.cursorPosition + i), c.target = Math.min(c.target, n))
        }
    }, c.prototype.dispatchEvent = function (t, e, i) {
        var s = [e].concat(i);
        if (this.emitEvent(t, s), a && this.$element)if (e) {
            var l = a.Event(e);
            l.type = t, this.$element.trigger(l, i)
        } else this.$element.trigger(t, i)
    }, c.prototype.select = function (t, e) {
        if (this.isActive) {
            var i = this.cells.length;
            this.options.wrapAround && i > 1 && (0 > t ? this.x -= this.slideableWidth : t >= i && (this.x += this.slideableWidth)), (this.options.wrapAround || e) && (t = n.modulo(t, i)), this.cells[t] && (this.selectedIndex = t, this.setSelectedCell(), this.startAnimation(), this.dispatchEvent("cellSelect"))
        }
    }, c.prototype.previous = function (t) {
        this.select(this.selectedIndex - 1, t)
    }, c.prototype.next = function (t) {
        this.select(this.selectedIndex + 1, t)
    }, c.prototype.setSelectedCell = function () {
        this._removeSelectedCellClass(), this.selectedCell = this.cells[this.selectedIndex], this.selectedElement = this.selectedCell.element, e.add(this.selectedElement, "is-selected")
    }, c.prototype._removeSelectedCellClass = function () {
        this.selectedCell && e.remove(this.selectedCell.element, "is-selected")
    }, c.prototype.getCell = function (t) {
        for (var e = 0, i = this.cells.length; i > e; e++) {
            var s = this.cells[e];
            if (s.element == t)return s
        }
    }, c.prototype.getCells = function (t) {
        t = n.makeArray(t);
        for (var e = [], i = 0, s = t.length; s > i; i++) {
            var l = t[i], o = this.getCell(l);
            o && e.push(o)
        }
        return e
    }, c.prototype.getCellElements = function () {
        for (var t = [], e = 0, i = this.cells.length; i > e; e++)t.push(this.cells[e].element);
        return t
    }, c.prototype.getParentCell = function (t) {
        var e = this.getCell(t);
        return e ? e : (t = n.getParent(t, ".flickity-slider > *"), this.getCell(t))
    }, c.prototype.getAdjacentCellElements = function (t, e) {
        if (!t)return [this.selectedElement];
        e = void 0 === e ? this.selectedIndex : e;
        var i = this.cells.length;
        if (1 + 2 * t >= i)return this.getCellElements();
        for (var s = [], l = e - t; e + t >= l; l++) {
            var o = this.options.wrapAround ? n.modulo(l, i) : l, r = this.cells[o];
            r && s.push(r.element)
        }
        return s
    }, c.prototype.uiChange = function () {
        this.emit("uiChange")
    }, c.prototype.childUIPointerDown = function (t) {
        this.emitEvent("childUIPointerDown", [t])
    }, c.prototype.onresize = function () {
        this.watchCSS(), this.resize()
    }, n.debounceMethod(c, "onresize", 150), c.prototype.resize = function () {
        this.isActive && (this.getSize(), this.options.wrapAround && (this.x = n.modulo(this.x, this.slideableWidth)), this.positionCells(), this._getWrapShiftCells(), this.setGallerySize(), this.positionSliderAtSelected())
    };
    var m = c.supportsConditionalCSS = function () {
        var t;
        return function () {
            if (void 0 !== t)return t;
            if (!d)return void(t = !1);
            var e = document.createElement("style"), i = document.createTextNode('body:after { content: "foo"; display: none; }');
            e.appendChild(i), document.head.appendChild(e);
            var s = d(document.body, ":after").content;
            return t = -1 != s.indexOf("foo"), document.head.removeChild(e), t
        }
    }();
    c.prototype.watchCSS = function () {
        var t = this.options.watchCSS;
        if (t) {
            var e = m();
            if (!e) {
                var i = "fallbackOn" == t ? "activate" : "deactivate";
                return void this[i]()
            }
            var s = d(this.element, ":after").content;
            -1 != s.indexOf("flickity") ? this.activate() : this.deactivate()
        }
    }, c.prototype.onkeydown = function (t) {
        if (this.options.accessibility && (!document.activeElement || document.activeElement == this.element))if (37 == t.keyCode) {
            var e = this.options.rightToLeft ? "next" : "previous";
            this.uiChange(), this[e]()
        } else if (39 == t.keyCode) {
            var i = this.options.rightToLeft ? "previous" : "next";
            this.uiChange(), this[i]()
        }
    }, c.prototype.deactivate = function () {
        if (this.isActive) {
            e.remove(this.element, "flickity-enabled"), e.remove(this.element, "flickity-rtl");
            for (var t = 0, i = this.cells.length; i > t; t++) {
                var l = this.cells[t];
                l.destroy()
            }
            this._removeSelectedCellClass(), this.element.removeChild(this.viewport), h(this.slider.children, this.element), this.options.accessibility && (this.element.removeAttribute("tabIndex"), s.unbind(this.element, "keydown", this)), this.isActive = !1, this.emit("deactivate")
        }
    }, c.prototype.destroy = function () {
        this.deactivate(), this.isResizeBound && s.unbind(t, "resize", this), this.emit("destroy"), a && this.$element && a.removeData(this.element, "flickity"), delete this.element.flickityGUID, delete u[this.guid]
    }, n.extend(c.prototype, r);
    var g = "attachEvent"in t;
    return c.setUnselectable = function (t) {
        g && t.setAttribute("unselectable", "on")
    }, c.data = function (t) {
        t = n.getQueryElement(t);
        var e = t && t.flickityGUID;
        return e && u[e]
    }, n.htmlInit(c, "flickity"), a && a.bridget && a.bridget("flickity", c), c.Cell = o, c
});
!function (t, e) {
    "use strict";
    "function" == typeof define && define.amd ? define(["classie/classie", "eventie/eventie", "./flickity", "unidragger/unidragger", "fizzy-ui-utils/utils"], function (i, o, r, n, s) {
        return e(t, i, o, r, n, s)
    }) : "object" == typeof exports ? module.exports = e(t, require("desandro-classie"), require("eventie"), require("./flickity"), require("unidragger"), require("fizzy-ui-utils")) : t.Flickity = e(t, t.classie, t.eventie, t.Flickity, t.Unidragger, t.fizzyUIUtils)
}(window, function (t, e, i, o, r, n) {
    "use strict";
    function s(t) {
        t.preventDefault ? t.preventDefault() : t.returnValue = !1
    }

    function a(e) {
        var i = r.getPointerPoint(e);
        return i.y - t.pageYOffset
    }

    n.extend(o.defaults, {
        draggable: !0,
        touchVerticalScroll: !0
    }), o.createMethods.push("_createDrag"), n.extend(o.prototype, r.prototype), o.prototype._createDrag = function () {
        this.on("activate", this.bindDrag), this.on("uiChange", this._uiChangeDrag), this.on("childUIPointerDown", this._childUIPointerDownDrag), this.on("deactivate", this.unbindDrag)
    }, o.prototype.bindDrag = function () {
        this.options.draggable && !this.isDragBound && (e.add(this.element, "is-draggable"), this.handles = [this.viewport], this.bindHandles(), this.isDragBound = !0)
    }, o.prototype.unbindDrag = function () {
        this.isDragBound && (e.remove(this.element, "is-draggable"), this.unbindHandles(), delete this.isDragBound)
    }, o.prototype._uiChangeDrag = function () {
        delete this.isFreeScrolling
    }, o.prototype._childUIPointerDownDrag = function (t) {
        s(t), this.pointerDownFocus(t)
    }, o.prototype.pointerDown = function (o, n) {
        if ("INPUT" == o.target.nodeName && "range" == o.target.type)return this.isPointerDown = !1, void delete this.pointerIdentifier;
        this._dragPointerDown(o, n);
        var s = document.activeElement;
        s && s.blur && s != this.element && s != document.body && s.blur(), this.pointerDownFocus(o), this.dragX = this.x, e.add(this.viewport, "is-pointer-down"), this._bindPostStartEvents(o), this.pointerDownScroll = r.getScrollPosition(), i.bind(t, "scroll", this), this.dispatchEvent("pointerDown", o, [n])
    };
    var h = {touchstart: !0, MSPointerDown: !0}, l = {INPUT: !0, SELECT: !0};
    o.prototype.pointerDownFocus = function (t) {
        !this.options.accessibility || h[t.type] || l[t.target.nodeName] || this.element.focus()
    }, o.prototype.pointerMove = function (t, e) {
        var i = this._dragPointerMove(t, e);
        this.touchVerticalScrollMove(t, e, i), this._dragMove(t, e, i), this.dispatchEvent("pointerMove", t, [e, i])
    }, o.prototype.hasDragStarted = function (t) {
        return !this.isTouchScrolling && Math.abs(t.x) > 3
    }, o.prototype.pointerUp = function (t, i) {
        delete this.isTouchScrolling, e.remove(this.viewport, "is-pointer-down"), this.dispatchEvent("pointerUp", t, [i]), this._dragPointerUp(t, i)
    };
    var c = {touchmove: !0, MSPointerMove: !0};
    return o.prototype.touchVerticalScrollMove = function (e, i, o) {
        var r = this.options.touchVerticalScroll, n = "withDrag" == r ? !r : this.isDragging || !r;
        !n && c[e.type] && !this.isTouchScrolling && Math.abs(o.y) > 10 && (this.startScrollY = t.pageYOffset, this.pointerWindowStartY = a(i), this.isTouchScrolling = !0)
    }, o.prototype.dragStart = function (t, e) {
        this.dragStartPosition = this.x, this.startAnimation(), this.dispatchEvent("dragStart", t, [e])
    }, o.prototype.dragMove = function (t, e, i) {
        s(t), this.previousDragX = this.dragX;
        var o = this.options.rightToLeft ? -1 : 1, r = this.dragStartPosition + i.x * o;
        if (!this.options.wrapAround && this.cells.length) {
            var n = Math.max(-this.cells[0].target, this.dragStartPosition);
            r = r > n ? .5 * (r + n) : r;
            var a = Math.min(-this.getLastCell().target, this.dragStartPosition);
            r = a > r ? .5 * (r + a) : r
        }
        this.dragX = r, this.dragMoveTime = new Date, this.dispatchEvent("dragMove", t, [e, i])
    }, o.prototype.dragEnd = function (t, e) {
        this.options.freeScroll && (this.isFreeScrolling = !0);
        var i = this.dragEndRestingSelect();
        if (this.options.freeScroll && !this.options.wrapAround) {
            var o = this.getRestingPosition();
            this.isFreeScrolling = -o > this.cells[0].target && -o < this.getLastCell().target
        } else this.options.freeScroll || i != this.selectedIndex || (i += this.dragEndBoostSelect());
        delete this.previousDragX, this.select(i), this.dispatchEvent("dragEnd", t, [e])
    }, o.prototype.dragEndRestingSelect = function () {
        var t = this.getRestingPosition(), e = Math.abs(this.getCellDistance(-t, this.selectedIndex)), i = this._getClosestResting(t, e, 1), o = this._getClosestResting(t, e, -1), r = i.distance < o.distance ? i.index : o.index;
        return r
    }, o.prototype._getClosestResting = function (t, e, i) {
        for (var o = this.selectedIndex, r = 1 / 0, n = this.options.contain && !this.options.wrapAround ? function (t, e) {
            return e >= t
        } : function (t, e) {
            return e > t
        }; n(e, r) && (o += i, r = e, e = this.getCellDistance(-t, o), null !== e);)e = Math.abs(e);
        return {distance: r, index: o - i}
    }, o.prototype.getCellDistance = function (t, e) {
        var i = this.cells.length, o = this.options.wrapAround && i > 1, r = o ? n.modulo(e, i) : e, s = this.cells[r];
        if (!s)return null;
        var a = o ? this.slideableWidth * Math.floor(e / i) : 0;
        return t - (s.target + a)
    }, o.prototype.dragEndBoostSelect = function () {
        if (void 0 === this.previousDragX || !this.dragMoveTime || new Date - this.dragMoveTime > 100)return 0;
        var t = this.getCellDistance(-this.dragX, this.selectedIndex), e = this.previousDragX - this.dragX;
        return t > 0 && e > 0 ? 1 : 0 > t && 0 > e ? -1 : 0
    }, o.prototype.staticClick = function (t, e) {
        var i = this.getParentCell(t.target), o = i && i.element, r = i && n.indexOf(this.cells, i);
        this.dispatchEvent("staticClick", t, [e, o, r])
    }, o
});
!function (t, e) {
    "use strict";
    "function" == typeof define && define.amd ? define(["eventie/eventie", "./flickity", "tap-listener/tap-listener", "fizzy-ui-utils/utils"], function (i, n, s, r) {
        return e(t, i, n, s, r)
    }) : "object" == typeof exports ? module.exports = e(t, require("eventie"), require("./flickity"), require("tap-listener"), require("fizzy-ui-utils")) : e(t, t.eventie, t.Flickity, t.TapListener, t.fizzyUIUtils)
}(window, function (t, e, i, n, s) {
    "use strict";
    function r(t, e) {
        this.direction = t, this.parent = e, this._create()
    }

    function o(t) {
        return "string" == typeof t ? t : "M " + t.x0 + ",50 L " + t.x1 + "," + (t.y1 + 50) + " L " + t.x2 + "," + (t.y2 + 50) + " L " + t.x3 + ",50  L " + t.x2 + "," + (50 - t.y2) + " L " + t.x1 + "," + (50 - t.y1) + " Z"
    }

    var a = "http://www.w3.org/2000/svg", h = function () {
        function t() {
            if (void 0 !== e)return e;
            var t = document.createElement("div");
            return t.innerHTML = "<svg/>", e = (t.firstChild && t.firstChild.namespaceURI) == a
        }

        var e;
        return t
    }();
    return r.prototype = new n, r.prototype._create = function () {
        this.isEnabled = !0, this.isPrevious = -1 == this.direction;
        var t = this.parent.options.rightToLeft ? 1 : -1;
        this.isLeft = this.direction == t;
        var e = this.element = document.createElement("button");
        if (e.className = "flickity-prev-next-button", e.className += this.isPrevious ? " previous" : " next", e.setAttribute("type", "button"), i.setUnselectable(e), h()) {
            var n = this.createSVG();
            e.appendChild(n)
        } else this.setArrowText(), e.className += " no-svg";
        var s = this;
        this.onCellSelect = function () {
            s.update()
        }, this.parent.on("cellSelect", this.onCellSelect), this.on("tap", this.onTap), this.on("pointerDown", function (t, e) {
            s.parent.childUIPointerDown(e)
        })
    }, r.prototype.activate = function () {
        this.update(), this.bindTap(this.element), e.bind(this.element, "click", this), this.parent.element.appendChild(this.element)
    }, r.prototype.deactivate = function () {
        this.parent.element.removeChild(this.element), n.prototype.destroy.call(this), e.unbind(this.element, "click", this)
    }, r.prototype.createSVG = function () {
        var t = document.createElementNS(a, "svg");
        t.setAttribute("viewBox", "0 0 100 100");
        var e = document.createElementNS(a, "path"), i = o(this.parent.options.arrowShape);
        return e.setAttribute("d", i), e.setAttribute("class", "arrow"), this.isLeft || e.setAttribute("transform", "translate(100, 100) rotate(180) "), t.appendChild(e), t
    }, r.prototype.setArrowText = function () {
        var t = this.parent.options, e = this.isLeft ? t.leftArrowText : t.rightArrowText;
        s.setText(this.element, e)
    }, r.prototype.onTap = function () {
        if (this.isEnabled) {
            this.parent.uiChange();
            var t = this.isPrevious ? "previous" : "next";
            this.parent[t]()
        }
    }, r.prototype.handleEvent = s.handleEvent, r.prototype.onclick = function () {
        var t = document.activeElement;
        t && t == this.element && this.onTap()
    }, r.prototype.enable = function () {
        this.isEnabled || (this.element.disabled = !1, this.isEnabled = !0)
    }, r.prototype.disable = function () {
        this.isEnabled && (this.element.disabled = !0, this.isEnabled = !1)
    }, r.prototype.update = function () {
        var t = this.parent.cells;
        if (this.parent.options.wrapAround && t.length > 1)return void this.enable();
        var e = t.length ? t.length - 1 : 0, i = this.isPrevious ? 0 : e, n = this.parent.selectedIndex == i ? "disable" : "enable";
        this[n]()
    }, r.prototype.destroy = function () {
        this.deactivate()
    }, s.extend(i.defaults, {
        prevNextButtons: !0,
        leftArrowText: "‹",
        rightArrowText: "›",
        arrowShape: {x0: 10, x1: 60, y1: 50, x2: 70, y2: 40, x3: 30}
    }), i.createMethods.push("_createPrevNextButtons"), i.prototype._createPrevNextButtons = function () {
        this.options.prevNextButtons && (this.prevButton = new r(-1, this), this.nextButton = new r(1, this), this.on("activate", this.activatePrevNextButtons))
    }, i.prototype.activatePrevNextButtons = function () {
        this.prevButton.activate(), this.nextButton.activate(), this.on("deactivate", this.deactivatePrevNextButtons)
    }, i.prototype.deactivatePrevNextButtons = function () {
        this.prevButton.deactivate(), this.nextButton.deactivate(), this.off("deactivate", this.deactivatePrevNextButtons)
    }, i.PrevNextButton = r, i
});
!function (t, e) {
    "use strict";
    "function" == typeof define && define.amd ? define(["eventie/eventie", "./flickity", "tap-listener/tap-listener", "fizzy-ui-utils/utils"], function (o, i, s, n) {
        return e(t, o, i, s, n)
    }) : "object" == typeof exports ? module.exports = e(t, require("eventie"), require("./flickity"), require("tap-listener"), require("fizzy-ui-utils")) : e(t, t.eventie, t.Flickity, t.TapListener, t.fizzyUIUtils)
}(window, function (t, e, o, i, s) {
    "use strict";
    function n(t) {
        this.parent = t, this._create()
    }

    return n.prototype = new i, n.prototype._create = function () {
        this.holder = document.createElement("ol"), this.holder.className = "flickity-page-dots", o.setUnselectable(this.holder), this.dots = [];
        var t = this;
        this.onCellSelect = function () {
            t.updateSelected()
        }, this.parent.on("cellSelect", this.onCellSelect), this.on("tap", this.onTap), this.on("pointerDown", function (e, o) {
            t.parent.childUIPointerDown(o)
        })
    }, n.prototype.activate = function () {
        this.setDots(), this.updateSelected(), this.bindTap(this.holder), this.parent.element.appendChild(this.holder)
    }, n.prototype.deactivate = function () {
        this.parent.element.removeChild(this.holder), i.prototype.destroy.call(this)
    }, n.prototype.setDots = function () {
        var t = this.parent.cells.length - this.dots.length;
        t > 0 ? this.addDots(t) : 0 > t && this.removeDots(-t)
    }, n.prototype.addDots = function (t) {
        for (var e = document.createDocumentFragment(), o = []; t;) {
            var i = document.createElement("li");
            i.className = "dot", e.appendChild(i), o.push(i), t--
        }
        this.holder.appendChild(e), this.dots = this.dots.concat(o)
    }, n.prototype.removeDots = function (t) {
        for (var e = this.dots.splice(this.dots.length - t, t), o = 0, i = e.length; i > o; o++) {
            var s = e[o];
            this.holder.removeChild(s)
        }
    }, n.prototype.updateSelected = function () {
        this.selectedDot && (this.selectedDot.className = "dot"), this.dots.length && (this.selectedDot = this.dots[this.parent.selectedIndex], this.selectedDot.className = "dot is-selected")
    }, n.prototype.onTap = function (t) {
        var e = t.target;
        if ("LI" == e.nodeName) {
            this.parent.uiChange();
            var o = s.indexOf(this.dots, e);
            this.parent.select(o)
        }
    }, n.prototype.destroy = function () {
        this.deactivate()
    }, o.PageDots = n, s.extend(o.defaults, {pageDots: !0}), o.createMethods.push("_createPageDots"), o.prototype._createPageDots = function () {
        this.options.pageDots && (this.pageDots = new n(this), this.on("activate", this.activatePageDots), this.on("cellAddedRemoved", this.onCellAddedRemovedPageDots), this.on("deactivate", this.deactivatePageDots))
    }, o.prototype.activatePageDots = function () {
        this.pageDots.activate()
    }, o.prototype.onCellAddedRemovedPageDots = function () {
        this.pageDots.setDots()
    }, o.prototype.deactivatePageDots = function () {
        this.pageDots.deactivate()
    }, o.PageDots = n, o
});
!function (t, e) {
    "use strict";
    "function" == typeof define && define.amd ? define(["eventEmitter/EventEmitter", "eventie/eventie", "./flickity"], function (t, i, n) {
        return e(t, i, n)
    }) : "object" == typeof exports ? module.exports = e(require("wolfy87-eventemitter"), require("eventie"), require("./flickity")) : e(t.EventEmitter, t.eventie, t.Flickity)
}(window, function (t, e, i) {
    "use strict";
    function n(t) {
        if (this.isPlaying = !1, this.parent = t, o) {
            var e = this;
            this.onVisibilityChange = function () {
                e.visibilityChange()
            }
        }
    }

    var s, o;
    return "hidden"in document ? (s = "hidden", o = "visibilitychange") : "webkitHidden"in document && (s = "webkitHidden", o = "webkitvisibilitychange"), n.prototype = new t, n.prototype.play = function () {
        this.isPlaying = !0, delete this.isPaused, o && document.addEventListener(o, this.onVisibilityChange, !1), this.tick()
    }, n.prototype.tick = function () {
        if (this.isPlaying && !this.isPaused) {
            this.tickTime = new Date;
            var t = this.parent.options.autoPlay;
            t = "number" == typeof t ? t : 3e3;
            var e = this;
            this.timeout = setTimeout(function () {
                e.parent.next(!0), e.tick()
            }, t)
        }
    }, n.prototype.stop = function () {
        this.isPlaying = !1, delete this.isPaused, this.clear(), o && document.removeEventListener(o, this.onVisibilityChange, !1)
    }, n.prototype.clear = function () {
        clearTimeout(this.timeout)
    }, n.prototype.pause = function () {
        this.isPlaying && (this.isPaused = !0, this.clear())
    }, n.prototype.unpause = function () {
        this.isPaused && this.play()
    }, n.prototype.visibilityChange = function () {
        var t = document[s];
        this[t ? "pause" : "unpause"]()
    }, i.createMethods.push("_createPlayer"), i.prototype._createPlayer = function () {
        this.player = new n(this), this.on("activate", this.activatePlayer), this.on("uiChange", this.stopPlayer), this.on("pointerDown", this.stopPlayer), this.on("deactivate", this.deactivatePlayer)
    }, i.prototype.activatePlayer = function () {
        this.options.autoPlay && (this.player.play(), e.bind(this.element, "mouseenter", this), this.isMouseenterBound = !0)
    }, i.prototype.stopPlayer = function () {
        this.player.stop()
    }, i.prototype.deactivatePlayer = function () {
        this.player.stop(), this.isMouseenterBound && (e.unbind(this.element, "mouseenter", this), delete this.isMouseenterBound)
    }, i.prototype.onmouseenter = function () {
        this.player.pause(), e.bind(this.element, "mouseleave", this)
    }, i.prototype.onmouseleave = function () {
        this.player.unpause(), e.unbind(this.element, "mouseleave", this)
    }, i.Player = n, i
});
!function (e, t) {
    "use strict";
    "function" == typeof define && define.amd ? define(["./flickity", "fizzy-ui-utils/utils"], function (i, l) {
        return t(e, i, l)
    }) : "object" == typeof exports ? module.exports = t(e, require("./flickity"), require("fizzy-ui-utils")) : t(e, e.Flickity, e.fizzyUIUtils)
}(window, function (e, t, i) {
    "use strict";
    function l(e) {
        for (var t = document.createDocumentFragment(), i = 0, l = e.length; l > i; i++) {
            var s = e[i];
            t.appendChild(s.element)
        }
        return t
    }

    return t.prototype.insert = function (e, t) {
        var i = this._makeCells(e);
        if (i && i.length) {
            var s = this.cells.length;
            t = void 0 === t ? s : t;
            var n = l(i), h = t == s;
            if (h)this.slider.appendChild(n); else {
                var c = this.cells[t].element;
                this.slider.insertBefore(n, c)
            }
            if (0 === t)this.cells = i.concat(this.cells); else if (h)this.cells = this.cells.concat(i); else {
                var o = this.cells.splice(t, s - t);
                this.cells = this.cells.concat(i).concat(o)
            }
            this._sizeCells(i);
            var r = t > this.selectedIndex ? 0 : i.length;
            this._cellAddedRemoved(t, r)
        }
    }, t.prototype.append = function (e) {
        this.insert(e, this.cells.length)
    }, t.prototype.prepend = function (e) {
        this.insert(e, 0)
    }, t.prototype.remove = function (e) {
        var t, l, s, n = this.getCells(e), h = 0;
        for (t = 0, l = n.length; l > t; t++) {
            s = n[t];
            var c = i.indexOf(this.cells, s) < this.selectedIndex;
            h -= c ? 1 : 0
        }
        for (t = 0, l = n.length; l > t; t++)s = n[t], s.remove(), i.removeFrom(this.cells, s);
        n.length && this._cellAddedRemoved(0, h)
    }, t.prototype._cellAddedRemoved = function (e, t) {
        t = t || 0, this.selectedIndex += t, this.selectedIndex = Math.max(0, Math.min(this.cells.length - 1, this.selectedIndex)), this.emitEvent("cellAddedRemoved", [e, t]), this.cellChange(e, !0)
    }, t.prototype.cellSizeChange = function (e) {
        var t = this.getCell(e);
        if (t) {
            t.getSize();
            var l = i.indexOf(this.cells, t);
            this.cellChange(l)
        }
    }, t.prototype.cellChange = function (e, t) {
        var i = this.slideableWidth;
        this._positionCells(e), this._getWrapShiftCells(), this.setGallerySize(), this.options.freeScroll ? (this.x += i - this.slideableWidth, this.positionSlider()) : (t && this.positionSliderAtSelected(), this.select(this.selectedIndex))
    }, t
});
!function (t, i) {
    "use strict";
    "function" == typeof define && define.amd ? define(["classie/classie", "eventie/eventie", "./flickity", "fizzy-ui-utils/utils"], function (e, o, l, a) {
        return i(t, e, o, l, a)
    }) : "object" == typeof exports ? module.exports = i(t, require("desandro-classie"), require("eventie"), require("./flickity"), require("fizzy-ui-utils")) : i(t, t.classie, t.eventie, t.Flickity, t.fizzyUIUtils)
}(window, function (t, i, e, o, l) {
    "use strict";
    function a(t) {
        if ("IMG" == t.nodeName && t.getAttribute("data-flickity-lazyload"))return [t];
        var i = t.querySelectorAll("img[data-flickity-lazyload]");
        return l.makeArray(i)
    }

    function n(t, i) {
        this.img = t, this.flickity = i, this.load()
    }

    return o.createMethods.push("_createLazyload"), o.prototype._createLazyload = function () {
        this.on("cellSelect", this.lazyLoad)
    }, o.prototype.lazyLoad = function () {
        var t = this.options.lazyLoad;
        if (t) {
            for (var i = "number" == typeof t ? t : 0, e = this.getAdjacentCellElements(i), o = [], l = 0, r = e.length; r > l; l++) {
                var s = e[l], c = a(s);
                o = o.concat(c)
            }
            for (l = 0, r = o.length; r > l; l++) {
                var d = o[l];
                new n(d, this)
            }
        }
    }, n.prototype.handleEvent = l.handleEvent, n.prototype.load = function () {
        e.bind(this.img, "load", this), e.bind(this.img, "error", this), this.img.src = this.img.getAttribute("data-flickity-lazyload"), this.img.removeAttribute("data-flickity-lazyload")
    }, n.prototype.onload = function (t) {
        this.complete(t, "flickity-lazyloaded")
    }, n.prototype.onerror = function () {
        this.complete(event, "flickity-lazyerror")
    }, n.prototype.complete = function (t, o) {
        e.unbind(this.img, "load", this), e.unbind(this.img, "error", this);
        var l = this.flickity.getParentCell(this.img), a = l && l.element;
        this.flickity.cellSizeChange(a), i.add(this.img, o), this.flickity.dispatchEvent("lazyLoad", t, a)
    }, o.LazyLoader = n, o
});
!function (e, t) {
    "use strict";
    "function" == typeof define && define.amd ? define(["eventEmitter/EventEmitter", "eventie/eventie"], function (i, n) {
        return t(e, i, n)
    }) : "object" == typeof exports ? module.exports = t(e, require("wolfy87-eventemitter"), require("eventie")) : e.imagesLoaded = t(e, e.EventEmitter, e.eventie)
}(window, function (e, t, i) {
    "use strict";
    function n(e, t) {
        for (var i in t)e[i] = t[i];
        return e
    }

    function o(e) {
        return "[object Array]" === d.call(e)
    }

    function r(e) {
        var t = [];
        if (o(e))t = e; else if ("number" == typeof e.length)for (var i = 0, n = e.length; n > i; i++)t.push(e[i]); else t.push(e);
        return t
    }

    function s(e, t, i) {
        if (!(this instanceof s))return new s(e, t);
        "string" == typeof e && (e = document.querySelectorAll(e)), this.elements = r(e), this.options = n({}, this.options), "function" == typeof t ? i = t : n(this.options, t), i && this.on("always", i), this.getImages(), c && (this.jqDeferred = new c.Deferred);
        var o = this;
        setTimeout(function () {
            o.check()
        })
    }

    function h(e) {
        this.img = e
    }

    function f(e) {
        this.src = e, p[e] = this
    }

    var c = e.jQuery, a = e.console, u = "undefined" != typeof a, d = Object.prototype.toString;
    s.prototype = new t, s.prototype.options = {}, s.prototype.getImages = function () {
        this.images = [];
        for (var e = 0, t = this.elements.length; t > e; e++) {
            var i = this.elements[e];
            "IMG" === i.nodeName && this.addImage(i);
            var n = i.nodeType;
            if (n && (1 === n || 9 === n || 11 === n))for (var o = i.querySelectorAll("img"), r = 0, s = o.length; s > r; r++) {
                var h = o[r];
                this.addImage(h)
            }
        }
    }, s.prototype.addImage = function (e) {
        var t = new h(e);
        this.images.push(t)
    }, s.prototype.check = function () {
        function e(e, o) {
            return t.options.debug && u && a.log("confirm", e, o), t.progress(e), i++, i === n && t.complete(), !0
        }

        var t = this, i = 0, n = this.images.length;
        if (this.hasAnyBroken = !1, !n)return void this.complete();
        for (var o = 0; n > o; o++) {
            var r = this.images[o];
            r.on("confirm", e), r.check()
        }
    }, s.prototype.progress = function (e) {
        this.hasAnyBroken = this.hasAnyBroken || !e.isLoaded;
        var t = this;
        setTimeout(function () {
            t.emit("progress", t, e), t.jqDeferred && t.jqDeferred.notify && t.jqDeferred.notify(t, e)
        })
    }, s.prototype.complete = function () {
        var e = this.hasAnyBroken ? "fail" : "done";
        this.isComplete = !0;
        var t = this;
        setTimeout(function () {
            if (t.emit(e, t), t.emit("always", t), t.jqDeferred) {
                var i = t.hasAnyBroken ? "reject" : "resolve";
                t.jqDeferred[i](t)
            }
        })
    }, c && (c.fn.imagesLoaded = function (e, t) {
        var i = new s(this, e, t);
        return i.jqDeferred.promise(c(this))
    }), h.prototype = new t, h.prototype.check = function () {
        var e = p[this.img.src] || new f(this.img.src);
        if (e.isConfirmed)return void this.confirm(e.isLoaded, "cached was confirmed");
        if (this.img.complete && void 0 !== this.img.naturalWidth)return void this.confirm(0 !== this.img.naturalWidth, "naturalWidth");
        var t = this;
        e.on("confirm", function (e, i) {
            return t.confirm(e.isLoaded, i), !0
        }), e.check()
    }, h.prototype.confirm = function (e, t) {
        this.isLoaded = e, this.emit("confirm", this, t)
    };
    var p = {};
    return f.prototype = new t, f.prototype.check = function () {
        if (!this.isChecked) {
            var e = new Image;
            i.bind(e, "load", this), i.bind(e, "error", this), e.src = this.src, this.isChecked = !0
        }
    }, f.prototype.handleEvent = function (e) {
        var t = "on" + e.type;
        this[t] && this[t](e)
    }, f.prototype.onload = function (e) {
        this.confirm(!0, "onload"), this.unbindProxyEvents(e)
    }, f.prototype.onerror = function (e) {
        this.confirm(!1, "onerror"), this.unbindProxyEvents(e)
    }, f.prototype.confirm = function (e, t) {
        this.isConfirmed = !0, this.isLoaded = e, this.emit("confirm", this, t)
    }, f.prototype.unbindProxyEvents = function (e) {
        i.unbind(e.target, "load", this), i.unbind(e.target, "error", this)
    }, s
});
!function (e, i) {
    "use strict";
    "function" == typeof define && define.amd ? define(["flickity/js/index", "imagesloaded/imagesloaded"], function (t, o) {
        return i(e, t, o)
    }) : "object" == typeof exports ? module.exports = i(e, require("flickity"), require("imagesloaded")) : e.Flickity = i(e, e.Flickity, e.imagesLoaded)
}(window, function (e, i, t) {
    "use strict";
    return i.createMethods.push("_createImagesLoaded"), i.prototype._createImagesLoaded = function () {
        this.on("activate", this.imagesLoaded)
    }, i.prototype.imagesLoaded = function () {
        function e(e, t) {
            var o = i.getParentCell(t.img);
            i.cellSizeChange(o && o.element), i.options.freeScroll || i.positionSliderAtSelected()
        }

        if (this.options.imagesLoaded) {
            var i = this;
            t(this.slider).on("progress", e)
        }
    }, i
});
!function (e, t) {
    "use strict";
    "function" == typeof define && define.amd ? define(["classie/classie", "flickity/js/index", "fizzy-ui-utils/utils"], function (i, n, o) {
        return t(e, i, n, o)
    }) : "object" == typeof exports ? module.exports = t(e, require("desandro-classie"), require("flickity"), require("fizzy-ui-utils")) : e.Flickity = t(e, e.classie, e.Flickity, e.fizzyUIUtils)
}(window, function (e, t, i, n) {
    "use strict";
    return i.createMethods.push("_createAsNavFor"), i.prototype._createAsNavFor = function () {
        this.on("activate", this.activateAsNavFor), this.on("deactivate", this.deactivateAsNavFor), this.on("destroy", this.destroyAsNavFor);
        var e = this.options.asNavFor;
        if (e) {
            var t = this;
            setTimeout(function () {
                t.setNavCompanion(e)
            })
        }
    }, i.prototype.setNavCompanion = function (e) {
        e = n.getQueryElement(e);
        var t = i.data(e);
        if (t && t != this) {
            this.navCompanion = t;
            var o = this;
            this.onNavCompanionSelect = function () {
                o.navCompanionSelect()
            }, t.on("cellSelect", this.onNavCompanionSelect), this.on("staticClick", this.onNavStaticClick), this.navCompanionSelect()
        }
    }, i.prototype.navCompanionSelect = function () {
        if (this.navCompanion) {
            var e = this.navCompanion.selectedIndex;
            this.select(e), this.removeNavSelectedElement(), this.selectedIndex == e && (this.navSelectedElement = this.cells[e].element, t.add(this.navSelectedElement, "is-nav-selected"))
        }
    }, i.prototype.activateAsNavFor = function () {
        this.navCompanionSelect()
    }, i.prototype.removeNavSelectedElement = function () {
        this.navSelectedElement && (t.remove(this.navSelectedElement, "is-nav-selected"), delete this.navSelectedElement)
    }, i.prototype.onNavStaticClick = function (e, t, i, n) {
        "number" == typeof n && this.navCompanion.select(n)
    }, i.prototype.deactivateAsNavFor = function () {
        this.removeNavSelectedElement()
    }, i.prototype.destroyAsNavFor = function () {
        this.navCompanion && (this.navCompanion.off("cellSelect", this.onNavCompanionSelect), this.off("staticClick", this.onNavStaticClick), delete this.navCompanion)
    }, i
});
!function (t, e) {
    "use strict";
    "function" == typeof define && define.amd ? define(["classie/classie", "get-style-property/get-style-property", "get-size/get-size", "unidragger/unidragger"], function (i, n, o, s) {
        return e(t, i, n, o, s)
    }) : "object" == typeof exports ? module.exports = e(t, require("desandro-classie"), require("desandro-get-style-property"), require("get-size"), require("unidragger")) : t.Draggabilly = e(t, t.classie, t.getStyleProperty, t.getSize, t.Unidragger)
}(window, function (t, e, i, n, o) {
    "use strict";
    function s() {
    }

    function r(t, e) {
        for (var i in e)t[i] = e[i];
        return t
    }

    function a(t, e) {
        this.element = "string" == typeof t ? l.querySelector(t) : t, b && (this.$element = b(this.element)), this.options = r({}, this.constructor.defaults), this.option(e), this._create()
    }

    function p(t, e, i) {
        return i = i || "round", e ? Math[i](t / e) * e : t
    }

    for (var h, l = t.document, d = l.defaultView, u = d && d.getComputedStyle ? function (t) {
        return d.getComputedStyle(t, null)
    } : function (t) {
        return t.currentStyle
    }, c = "object" == typeof HTMLElement ? function (t) {
        return t instanceof HTMLElement
    } : function (t) {
        return t && "object" == typeof t && 1 == t.nodeType && "string" == typeof t.nodeName
    }, g = 0, y = "webkit moz ms o".split(" "), f = t.requestAnimationFrame, m = t.cancelAnimationFrame, v = 0; v < y.length && (!f || !m); v++)h = y[v], f = f || t[h + "RequestAnimationFrame"], m = m || t[h + "CancelAnimationFrame"] || t[h + "CancelRequestAnimationFrame"];
    f && m || (f = function (e) {
        var i = (new Date).getTime(), n = Math.max(0, 16 - (i - g)), o = t.setTimeout(function () {
            e(i + n)
        }, n);
        return g = i + n, o
    }, m = function (e) {
        t.clearTimeout(e)
    });
    var x = i("transform"), P = !!i("perspective"), b = t.jQuery;
    r(a.prototype, o.prototype), a.defaults = {}, a.prototype.option = function (t) {
        r(this.options, t)
    }, a.prototype._create = function () {
        this.position = {}, this._getPosition(), this.startPoint = {x: 0, y: 0}, this.dragPoint = {
            x: 0,
            y: 0
        }, this.startPosition = r({}, this.position);
        var t = u(this.element);
        "relative" != t.position && "absolute" != t.position && (this.element.style.position = "relative"), this.enable(), this.setHandles()
    }, a.prototype.setHandles = function () {
        this.handles = this.options.handle ? this.element.querySelectorAll(this.options.handle) : [this.element], this.bindHandles()
    }, a.prototype.dispatchEvent = function (e, i, n) {
        var o = [i].concat(n);
        this.emitEvent(e, o);
        var s = t.jQuery;
        if (s && this.$element)if (i) {
            var r = s.Event(i);
            r.type = e, this.$element.trigger(r, n)
        } else this.$element.trigger(e, n)
    }, a.prototype._getPosition = function () {
        var t = u(this.element), e = parseInt(t.left, 10), i = parseInt(t.top, 10);
        this.position.x = isNaN(e) ? 0 : e, this.position.y = isNaN(i) ? 0 : i, this._addTransformPosition(t)
    }, a.prototype._addTransformPosition = function (t) {
        if (x) {
            var e = t[x];
            if (0 === e.indexOf("matrix")) {
                var i = e.split(","), n = 0 === e.indexOf("matrix3d") ? 12 : 4, o = parseInt(i[n], 10), s = parseInt(i[n + 1], 10);
                this.position.x += o, this.position.y += s
            }
        }
    }, a.prototype.pointerDown = function (t, i) {
        this._dragPointerDown(t, i);
        var n = l.activeElement;
        n && n.blur && n.blur(), this._bindPostStartEvents(t), e.add(this.element, "is-pointer-down"), this.dispatchEvent("pointerDown", t, [i])
    }, a.prototype.pointerMove = function (t, e) {
        var i = this._dragPointerMove(t, e);
        this.dispatchEvent("pointerMove", t, [e, i]), this._dragMove(t, e, i)
    }, a.prototype.dragStart = function (t, i) {
        this.isEnabled && (this._getPosition(), this.measureContainment(), this.startPosition.x = this.position.x, this.startPosition.y = this.position.y, this.setLeftTop(), this.dragPoint.x = 0, this.dragPoint.y = 0, this.isDragging = !0, e.add(this.element, "is-dragging"), this.dispatchEvent("dragStart", t, [i]), this.animate())
    }, a.prototype.measureContainment = function () {
        var t = this.options.containment;
        if (t) {
            this.size = n(this.element);
            var e = this.element.getBoundingClientRect(), i = c(t) ? t : "string" == typeof t ? l.querySelector(t) : this.element.parentNode;
            this.containerSize = n(i);
            var o = i.getBoundingClientRect();
            this.relativeStartPosition = {x: e.left - o.left, y: e.top - o.top}
        }
    }, a.prototype.dragMove = function (t, e, i) {
        if (this.isEnabled) {
            var n = i.x, o = i.y, s = this.options.grid, r = s && s[0], a = s && s[1];
            n = p(n, r), o = p(o, a), n = this.containDrag("x", n, r), o = this.containDrag("y", o, a), n = "y" == this.options.axis ? 0 : n, o = "x" == this.options.axis ? 0 : o, this.position.x = this.startPosition.x + n, this.position.y = this.startPosition.y + o, this.dragPoint.x = n, this.dragPoint.y = o, this.dispatchEvent("dragMove", t, [e, i])
        }
    }, a.prototype.containDrag = function (t, e, i) {
        if (!this.options.containment)return e;
        var n = "x" == t ? "width" : "height", o = this.relativeStartPosition[t], s = p(-o, i, "ceil"), r = this.containerSize[n] - o - this.size[n];
        return r = p(r, i, "floor"), Math.min(r, Math.max(s, e))
    }, a.prototype.pointerUp = function (t, i) {
        e.remove(this.element, "is-pointer-down"), this.dispatchEvent("pointerUp", t, [i]), this._dragPointerUp(t, i)
    }, a.prototype.dragEnd = function (t, i) {
        this.isEnabled && (this.isDragging = !1, x && (this.element.style[x] = "", this.setLeftTop()), e.remove(this.element, "is-dragging"), this.dispatchEvent("dragEnd", t, [i]))
    }, a.prototype.animate = function () {
        if (this.isDragging) {
            this.positionDrag();
            var t = this;
            f(function () {
                t.animate()
            })
        }
    };
    var E = P ? function (t, e) {
        return "translate3d( " + t + "px, " + e + "px, 0)"
    } : function (t, e) {
        return "translate( " + t + "px, " + e + "px)"
    };
    return a.prototype.setLeftTop = function () {
        this.element.style.left = this.position.x + "px", this.element.style.top = this.position.y + "px"
    }, a.prototype.positionDrag = x ? function () {
        this.element.style[x] = E(this.dragPoint.x, this.dragPoint.y)
    } : a.prototype.setLeftTop, a.prototype.staticClick = function (t, e) {
        this.dispatchEvent("staticClick", t, [e])
    }, a.prototype.enable = function () {
        this.isEnabled = !0
    }, a.prototype.disable = function () {
        this.isEnabled = !1, this.isDragging && this.dragEnd()
    }, a.prototype.destroy = function () {
        this.disable(), x && (this.element.style[x] = ""), this.element.style.left = "", this.element.style.top = "", this.element.style.position = "", this.unbindHandles(), this.$element && this.$element.removeData("draggabilly")
    }, a.prototype._init = s, b && b.bridget && b.bridget("draggabilly", a), a
});
!function (e) {
    "use strict";
    e.utils = e.fizzyUIUtils;
    var l = e.FlickityDocs = {};
    l.modules = {}, l.makeCellElem = function (e) {
        var l = document.createElement("div");
        l.className = "gallery-cell";
        var t = document.createElement("span");
        return t.className = "gallery-cell__number", utils.setText(t, e), l.appendChild(t), l
    }, docReady(function () {
        for (var e = document.querySelectorAll("[data-js-module]"), t = 0, a = e.length; a > t; t++) {
            var c = e[t], n = c.getAttribute("data-js-module"), r = l.modules[n];
            r && r(c)
        }
    })
}(window);
FlickityDocs.modules.append = function (e) {
    "use strict";
    var l = e.querySelector(".gallery"), i = new Flickity(l, {initialIndex: 2}), t = i.cells.length + 1, c = FlickityDocs.makeCellElem, n = e.querySelector(".button");
    eventie.bind(n, "click", function () {
        i.append([c(t++), c(t++)])
    })
};
FlickityDocs.modules["arrow-wiz"] = function (e) {
    "use strict";
    function t(e, t, n, o, r) {
        r && (s.strokeStyle = r), s.beginPath(), s.moveTo(e, t), s.lineTo(n, o), s.stroke(), s.closePath()
    }

    function n() {
        var e, n;
        for (e = 0; 11 > e; e++) {
            var o = 20 * e + .5;
            n = e % 5 ? v : d, t(0, o, f, o, n)
        }
        for (e = 0; 11 > e; e++) {
            var r = 20 * e + .5;
            n = e % 5 ? v : d, t(r, 0, r, h, n)
        }
        t(0, h - .5, f, h - .5, d), t(f - .5, 0, f - .5, h, d)
    }

    function o(e, t) {
        return function () {
            var n = r(e);
            w[t] = n, x()
        }
    }

    function r(e) {
        return {x: e.position.x / 2, y: 50 - e.position.y / 2}
    }

    function i() {
        s.strokeStyle = "#333", s.fillStyle = "hsla(0, 0%, 0%, 0.4)", s.beginPath(), s.moveTo(2 * w[0].x, 100), s.lineTo(2 * w[1].x, 100 - 2 * w[1].y), s.lineTo(2 * w[2].x, 100 - 2 * w[2].y), s.lineTo(2 * w[3].x, 100), s.lineTo(2 * w[2].x, 100 + 2 * w[2].y), s.lineTo(2 * w[1].x, 100 + 2 * w[1].y), s.lineTo(2 * w[0].x, 100), s.fill(), s.stroke(), s.closePath()
    }

    function l() {
        s.clearRect(0, 0, f, h), n(), i()
    }

    function a() {
        m.textContent = "arrowShape: { \n  x0: " + w[0].x + ",\n  x1: " + w[1].x + ", y1: " + w[1].y + ",\n  x2: " + w[2].x + ", y2: " + w[2].y + ",\n  x3: " + w[3].x + "\n}"
    }

    function c() {
        var e = "M " + w[0].x + ",50 L " + w[1].x + "," + (50 + w[1].y) + " L " + w[2].x + "," + (50 + w[2].y) + " L " + w[3].x + ",50  L " + w[2].x + "," + (50 - w[2].y) + " L " + w[1].x + "," + (50 - w[1].y) + " Z";
        b.setAttribute("d", e), L.setAttribute("d", e)
    }

    function x() {
        l(), a(), c()
    }

    var y = e, u = y.querySelector("canvas"), s = u.getContext("2d"), f = u.width, h = u.height, v = "hsla(210, 50%, 50%, 0.25)", d = "hsla(210, 50%, 50%, 0.5)";
    n();
    for (var w = [], S = [], g = 0; 4 > g; g++) {
        var T = y.querySelector(".arrow-wiz-illo__point--" + g), p = new Draggabilly(T, {
            containment: !0,
            grid: [10, 10]
        }), k = o(p, g);
        p.on("dragMove", k), S.push(p), w[g] = r(p)
    }
    var m = y.querySelector(".arrow-wiz-code code"), q = new Flickity(y.querySelector(".gallery"), {initialIndex: 2}), b = q.prevButton.element.querySelector("path"), L = q.nextButton.element.querySelector("path");
    x()
};
FlickityDocs.modules["commercial-license-agreement"] = function (e) {
    "use strict";
    function t(e) {
        var t = o.querySelector(".is-selected");
        t && classie.remove(t, "is-selected"), classie.add(e, "is-selected");
        for (var i = e.getAttribute("data-license-option"), l = r[i], a = 0, s = n.length; s > a; a++) {
            var c = n[a];
            utils.setText(c.element, l[c.property])
        }
    }

    var r = {
        developer: {
            title: "Developer",
            "for-official": "one (1) Licensed Developer",
            "for-plain": "one individual Developer"
        },
        team: {
            title: "Team",
            "for-official": "up to eight (8) Licensed Developer(s)",
            "for-plain": "up to 8 Developers"
        },
        organization: {
            title: "Organization",
            "for-official": "an unlimited number of Licensed Developer(s)",
            "for-plain": "an unlimited number of Developers"
        }
    }, o = e.querySelector(".button-group"), i = e.querySelector("h2"), l = i.cloneNode(!0);
    l.style.borderTop = "none", l.style.marginTop = 0, l.id = "", utils.setText(i, ""), o.parentNode.insertBefore(l, o.nextSibling);
    for (var n = [], a = e.querySelectorAll("[data-license-property]"), s = 0, c = a.length; c > s; s++) {
        var p = a[s], u = {property: p.getAttribute("data-license-property"), element: p};
        n.push(u)
    }
    t(o.querySelector(".button--developer")), eventie.bind(o, "click", function (e) {
        matchesSelector(e.target, ".button") && t(e.target)
    })
};
FlickityDocs.modules["custom-nav"] = function (e) {
    "use strict";
    var t = e.querySelector(".gallery"), c = new Flickity(t, {
        prevNextButtons: !1,
        pageDots: !1
    }), i = e.querySelector(".button-group--cells"), l = utils.makeArray(i.children);
    c.on("cellSelect", function () {
        var e = i.querySelector(".is-selected"), t = i.children[c.selectedIndex];
        classie.remove(e, "is-selected"), classie.add(t, "is-selected")
    }), eventie.bind(i, "click", function (e) {
        if (matchesSelector(e.target, ".button")) {
            var t = utils.indexOf(l, e.target);
            c.select(t)
        }
    });
    var n = e.querySelector(".button--previous");
    eventie.bind(n, "click", function () {
        c.previous()
    });
    var r = e.querySelector(".button--next");
    eventie.bind(r, "click", function () {
        c.next()
    })
};
FlickityDocs.modules.destroy = function (e) {
    "use strict";
    var t = e.querySelector(".gallery"), i = new Flickity(t), c = !0, l = e.querySelector(".button");
    eventie.bind(l, "click", function () {
        c ? i.destroy() : i = new Flickity(t), c = !c
    })
};
FlickityDocs.modules.events = function (e) {
    "use strict";
    function t(e, t) {
        var l = document.createElement("tr"), n = document.createElement("td"), c = document.createElement("td"), r = document.createElement("td");
        n.className = "event-table__time", c.className = "event-table__event", r.className = "event-table__message";
        var i = new Date, s = i.getHours() + ":" + i.getMinutes() + ":" + i.getSeconds() + "." + i.getMilliseconds();
        utils.setText(n, s), utils.setText(c, e), utils.setText(r, t || ""), l.appendChild(n), l.appendChild(c), l.appendChild(r);
        var o = a.children;
        1 == o.length ? a.appendChild(l) : a.insertBefore(l, o[1]);
        var d = a.children[11];
        d && a.removeChild(d)
    }

    function l(e) {
        return function () {
            t(e)
        }
    }

    var n = e.querySelector(".gallery"), c = new Flickity(n), r = e.querySelector(".event-table"), a = r.querySelector("tbody");
    c.on("cellSelect", function () {
        t("cellSelect", "select cell " + (c.selectedIndex + 1))
    }), c.on("settle", function () {
        t("settle", "settled at cell " + (c.selectedIndex + 1))
    }), c.on("staticClick", function (e) {
        var l = "";
        if (matchesSelector(e.target, ".gallery-cell")) {
            var n = c.getCell(e.target), r = utils.indexOf(c.cells, n) + 1;
            l = "clicked cell " + r
        }
        t("staticClick", l)
    });
    for (var i = ["dragStart", "dragMove", "dragEnd", "pointerDown", "pointerMove", "pointerUp"], s = 0, o = i.length; o > s; s++) {
        var d = i[s], u = l(d);
        c.on(d, u)
    }
};
FlickityDocs.modules["hero-gallery"] = function (e) {
    "use strict";
    var r = e.querySelector(".hero-gallery__cell--1"), i = r.querySelector(".hero-illustration");
    if (i) {
        var l = document.createElement("img");
        eventie.bind(l, "load", function () {
            i.src = l.src
        }), l.src = "img/flickity-illustration.gif"
    }
};
FlickityDocs.modules.insert = function (e) {
    "use strict";
    var i = e.querySelector(".gallery"), l = new Flickity(i, {initialIndex: 1}), t = l.cells.length + 1, c = FlickityDocs.makeCellElem, n = e.querySelector(".button");
    eventie.bind(n, "click", function () {
        var e = [c(t++), c(t++)];
        l.insert(e, 2)
    })
};
FlickityDocs.modules.next = function (e) {
    "use strict";
    var t = e.querySelector(".gallery"), n = new Flickity(t), c = e.querySelector(".button--next");
    eventie.bind(c, "click", function () {
        n.next()
    });
    var i = e.querySelector(".button--next-wrapped");
    eventie.bind(i, "click", function () {
        n.next(!0)
    })
};
!function (t) {
    "use strict";
    function e(e) {
        e && (this.element = e, this.isActive = !1, this.isFixed = !1, eventie.bind(t, "resize", this), this.onresize())
    }

    function i(t, e, i) {
        var s = t.prototype[e], n = e + "Timeout";
        t.prototype[e] = function () {
            if (!this[n]) {
                s.apply(this, arguments);
                var t = this;
                this[n] = setTimeout(function () {
                    s.apply(t, arguments), delete t[n]
                }, i || 100)
            }
        }
    }

    e.prototype.handleEvent = utils.handleEvent, e.prototype.onresize = function () {
        var e = getComputedStyle(this.element, ":after").content, i = getSize(this.element);
        -1 != e.indexOf("sticky") && i.innerHeight <= t.innerHeight ? this.activate() : this.deactivate()
    }, utils.debounceMethod(e, "onresize"), e.prototype.activate = function () {
        this.isActive || (this.isActive = !0, this.originalY = this.element.getBoundingClientRect().top + t.pageYOffset, eventie.bind(t, "scroll", this), this.onscroll())
    }, e.prototype.deactivate = function () {
        this.isActive && (this.isActive = !1, this.isFixed = !1, classie.remove(this.element, "is-fixed"), eventie.unbind(t, "scroll", this))
    }, e.prototype.onscroll = function () {
        var e = t.pageYOffset >= this.originalY;
        e != this.isFixed && (classie.toggle(this.element, "is-fixed"), this.isFixed = e)
    }, i(e, "onscroll", 50), t.Stickeroo = e
}(window), docReady(function () {
    "use strict";
    for (var t = window.Stickeroo, e = document.querySelectorAll(".js-stickeroo"), i = 0, s = e.length; s > i; i++) {
        var n = e[i];
        new t(n)
    }
});
FlickityDocs.modules.prepend = function (e) {
    "use strict";
    var l = e.querySelector(".gallery"), c = new Flickity(l), t = c.cells.length + 1, i = FlickityDocs.makeCellElem, n = e.querySelector(".button");
    eventie.bind(n, "click", function () {
        c.prepend([i(t++), i(t++)])
    })
};
FlickityDocs.modules.previous = function (e) {
    "use strict";
    var i = e.querySelector(".gallery"), r = new Flickity(i), t = e.querySelector(".button--previous");
    eventie.bind(t, "click", function () {
        r.previous()
    });
    var o = e.querySelector(".button--previous-wrapped");
    eventie.bind(o, "click", function () {
        r.previous(!0)
    })
};
FlickityDocs.modules.remove = function (e) {
    "use strict";
    var i = e.querySelector(".gallery"), t = new Flickity(i, {initialIndex: 1});
    t.on("staticClick", function (e, i, c) {
        c && t.remove(c)
    })
};
FlickityDocs.modules.reposition = function (i) {
    "use strict";
    var e = i.querySelector(".gallery"), t = new Flickity(e);
    t.on("staticClick", function (i, e, o) {
        o && (classie.toggle(o, "is-expanded"), t.reposition())
    })
};
FlickityDocs.modules["resize-show"] = function (e) {
    "use strict";
    var i = e.querySelector(".gallery"), t = new Flickity(i), c = e.querySelector(".button");
    eventie.bind(c, "click", function () {
        i.style.display = "block", t.resize()
    })
};
FlickityDocs.modules.resize = function (e) {
    "use strict";
    var i = e.querySelector(".gallery"), t = new Flickity(i), c = e.querySelector(".button");
    eventie.bind(c, "click", function () {
        classie.toggle(i, "is-expanded"), t.resize()
    })
};
FlickityDocs.modules.select = function (e) {
    "use strict";
    var t = window.fizzyUIUtils, r = new Flickity(e.querySelector(".gallery")), c = e.querySelector(".button-group"), i = t.makeArray(c.querySelectorAll(".button"));
    eventie.bind(c, "click", function (e) {
        if (matchesSelector(e.target, ".button")) {
            var c = t.indexOf(i, e.target);
            r.select(c)
        }
    })
};
FlickityDocs.modules["static-click"] = function (i) {
    "use strict";
    var e = window.fizzyUIUtils, c = i.querySelector(".gallery"), l = new Flickity(c, {initialIndex: 1}), t = i.querySelector(".logger");
    l.on("staticClick", function (i, l, s, r) {
        if (s) {
            var o = c.querySelector(".is-clicked");
            o && classie.remove(o, "is-clicked"), classie.add(s, "is-clicked"), e.setText(t, "Cell " + (r + 1) + " clicked")
        }
    })
};