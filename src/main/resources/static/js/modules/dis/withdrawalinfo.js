$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'withdrawal/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '提现订单号', name: 'withdrawNum', index: 'withdraw_num', width: 100},
            {
                label: '提现状态', name: 'withdrawType', index: 'withdraw_type', width: 80,
                formatter: (value, options, row) => value === "1" ?
                    '<span class="label label-success">成功</span>' :
                    '<span class="label label-danger">失败</span>'
            },
            {label: '提现用户名', name: 'withdrawName', index: 'withdraw_name', width: 50},
            {label: '提现用户手机号', name: 'withdrawMobile', index: 'withdraw_mobile', width: 80},
            {label: '提现金额', name: 'withdrawAmount', index: 'withdraw_amount', width: 80},
            {label: '提现手续费', name: 'withdrawPoundage', index: 'withdraw_poundage', width: 80},
            {label: '支付宝账号', name: 'alipayAccount', index: 'alipay_account', width: 80}
        ],
        viewrecords: true,
        height: 385,
        rowNum: 10,
        rowList: [10, 30, 50],
        rownumbers: true,
        rownumWidth: 25,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.pageNum",
            total: "page.pages",
            records: "page.total"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        withdrawMobile: "",
        withdrawName: "",
        withdrawNum: "",
        withdrawalInfo: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: function () {
            this.withdrawMobile = "";
            this.withdrawName = "";
            this.withdrawNum = "";
        },
        getInfo: function (id) {
            $.get(baseURL + "withdrawal/info/" + id, function (r) {
                vm.withdrawalInfo = r.withdrawalInfo;
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "withdrawNum": vm.withdrawNum,
                    "withdrawName": vm.withdrawName,
                    "withdrawMobile": vm.withdrawMobile
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});