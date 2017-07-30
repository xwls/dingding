/**
 * Created by Kevin on 2017/7/30.
 */
var vm = new Vue({
    el:"#app",
    data:{
        msg: "你好，钉钉！",
        err: "错误信息",
        url: location.href,
        dd_config:{},
        user:{}
    },
    mounted:function () {
      this.get_js_config();
    },
    methods:{
        get_js_config : function () {
            this.$http.get('get_js_config?url='+this.url).then(function (response) {
                this.dd_config = response.body;
                vm.msg = response.body;
                dd.config({
                    agentId: vm.dd_config.agentId,
                    corpId: vm.dd_config.corpId,
                    timeStamp: vm.dd_config.timeStamp,
                    nonceStr: vm.dd_config.nonceStr,
                    signature: vm.dd_config.signature,
                    jsApiList: [
                        'runtime.info',
                        'biz.contact.choose',
                        'device.notification.confirm',
                        'device.notification.alert',
                        'device.notification.prompt',
                        'biz.ding.post',
                        'biz.util.openLink']
                });
                dd.ready(function () {
                    dd.runtime.info({
                        onSuccess: function (info) {
                            // console.log('runtime info: ' + JSON.stringify(info));
                            vm.msg = 'onSuccess'+JSON.stringify(info);
                        },
                        onFail: function (err) {
                            vm.err = 'fail: ' + JSON.stringify(err);
                        }
                    });
                    dd.runtime.permission.requestAuthCode({
                        corpId: vm.dd_config.corpId, //企业id
                        onSuccess: function (info) {
                            Window.authcode = info.code;
                            vm.$http.get('get_user_info?code='+info.code).then(function (response) {
                                vm.user = response.body;
                            });
                        },
                        onFail: function (err) {
                            console.log('requestAuthCode fail: ' + JSON.stringify(err));
                        }
                    });
                });
                dd.error(function (error) {
                    vm.err = JSON.stringify(error)
                })
            });
        }
    }
});