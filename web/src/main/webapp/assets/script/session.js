session = {

    host: 'http://127.0.0.1:8080',

    account: {

        getAccountProfile: function (callback) {
            OUPENG.request({
                url: session.host + '/account/settings',
                type: 'GET',
                cb: function (data) {
                    callback(data);
                }
            });
        },

        register: function (callback) {
            OUPENG.request({
                url: session.host + '/session/join?verifyCode=' + $('#inputVerifyCode').val(),
                data: JSON.stringify($('form').serializeJSON()),
                cb: function (data) {
                    alert('注册成功');
                    callback(data);
                }
            });
        },

        validate: function (data, callback) {
            OUPENG.request({
                url: session.host + '/session/validate',
                data: data,
                type: 'GET',
                cb: callback
            })
        },

        login: function (callback) {
            OUPENG.request({
                url: session.host + '/session/login',
                contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
                data: $('form').serializeJSON(),
                cb: function (data) {
                    alert("登陆成功");
                    callback(data);
                }
            });
        },

        logout: function (callback) {
            OUPENG.request({
                url: session.host + '/session/logout',
                cb: function (data) {
                    callback(data);
                }
            });
        }
    }
};