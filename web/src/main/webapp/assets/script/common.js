(function ($) {
    $.request = function (options) {
        if (typeof options.wait == 'undefined') {
            options.wait = true
        }
        if (options.wait) {
        }
        options.cb = options.cb || function () {
        };
        $.ajax({
            url: options.url,
            contentType : (options.contentType == undefined) ? "application/json; charset=UTF-8" : options.contentType,
            data: options.data,
            dataType: "json",
            cache: false,
            timeout: options.timeout || 60 * 1000,
            type: (options.type == undefined) ? "POST" : options.type,
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                if (!!textStatus && textStatus == 'abort') {
                    window.setTimeout(function () {
                    }, 3000);
                    if (options.wait) {
                    }
                    return;
                }
                window.setTimeout(function () {
                }, 3000);
                if (options.wait) {
                }
                if (window.confirm('页面会话可能已失效，是否刷新页面？')) {
                    window.location.reload();
                }
            },
            success: function (result) {
                if (options.wait) {
                }
                if (!result) {
                    return;
                }
                if (result) {
                    if (typeof options.cb == 'function') {
                        options.cb(result);
                        return;
                    }
                }
                alert('无结果！');
            }
        });
    };
    $.getParam = function(name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
        var r = window.location.search.substr(1).match(reg);
        if (r != null) {
            return decodeURI(r[2]);
        }
        return null;
    }
})(jQuery);