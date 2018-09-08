$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'memberaccount/list',
        datatype: "json",
        colModel: [
            {label: 'accountId', name: 'accountId', index: 'account_id', width: 50, key: true, hidden: true},
            {label: '会员姓名', name: 'member.disUserName', index: 'member_id', width: 80},
            {label: '会员手机号', name: 'member.userEntity.mobile', index: 'member_id', width: 80},
            {label: '用户支付宝账号', name: 'aliPayAccount', index: 'alipay_account', width: 100},
            {label: '账户类型', name: 'memberType', index: 'member_type', width: 80},
            {label: '账户余额', name: 'memberAmount', index: 'member_amount', width: 180}
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
        q: {
            username: "",
            mobile: ""
        },
        showList: true,
        title: null,
        memberAccount: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: function () {
            this.mobile = "";
            this.username = "";
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.memberAccount = {};
        },
        update: function (event) {
            var accountId = getSelectedRow();
            if (accountId == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(accountId)
        },
        saveOrUpdate: function (event) {
            var url = vm.memberAccount.accountId == null ? "memberaccount/save" : "memberaccount/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.memberAccount),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function (index) {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: function (event) {
            var accountIds = getSelectedRows();
            if (accountIds == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "memberaccount/delete",
                    contentType: "application/json",
                    data: JSON.stringify(accountIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function (index) {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: function (accountId) {
            $.get(baseURL + "memberaccount/info/" + accountId, function (r) {
                vm.memberAccount = r.memberAccount;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'username': vm.q.username,
                    'mobile': vm.q.mobile
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});