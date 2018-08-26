$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'memberaccounthistory/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '用户姓名', name: 'userName', width: 80},
            {label: '用户手机号', name: 'mobile', width: 80},
            {label: '收入账号', name: 'accountId', index: 'account_id', width: 80},
            {label: '金额', name: 'hisAmount', index: 'his_amount', width: 80},
            {
                label: '收入类型',
                name: 'hisType',
                index: 'his_type',
                width: 80,
                formatter: (value, options, row) => value === '0' ?
                    '<span class="label label-danger">支出</span>' :
                    '<span class="label label-success">收入</span>'
            },
            {label: '收入时间', name: 'addTime', index: 'add_time', width: 80}
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
            mobile: "",
            account: ""
        },
        showList: true,
        title: null,
        memberAccountHistory: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: function () {
            this.q.mobile = "";
            this.q.username = "";
            this.q.account = "";
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.memberAccountHistory = {};
        },
        update: function (event) {
            var id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            var url = vm.memberAccountHistory.id == null ? "memberaccounthistory/save" : "memberaccounthistory/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.memberAccountHistory),
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
            var ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "memberaccounthistory/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
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
        getInfo: function (id) {
            $.get(baseURL + "memberaccounthistory/info/" + id, function (r) {
                vm.memberAccountHistory = r.memberAccountHistory;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'username': vm.q.username,
                    'accountId': vm.q.account,
                    'mobile': vm.q.mobile
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});