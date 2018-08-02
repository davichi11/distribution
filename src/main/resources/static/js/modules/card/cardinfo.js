$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'cardinfo/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '银行代号', name: 'bankNum', index: 'bank_num', width: 50},
            {label: '信用卡代号', name: 'cardNum', index: 'card_num', width: 50},
            {label: '信用卡名称', name: 'cardName', index: 'card_name', width: 80},
            {
                label: '信用卡图片', name: 'cardImg', index: 'card_img', width: 80,
                formatter: (value, options, row) => `<a href="#" onclick="showImg('${value}')">点击查看图标</a>`
            },
            {label: '信用卡详情', name: 'cardInfo', index: 'card_info', width: 80},
            {
                label: '信用卡办理链接',
                name: 'cardUrl',
                index: 'card_url',
                width: 180,
                formatter: (value, options, row) => `<a href="#">${value}</a>`
            },
            {label: '佣金返利', name: 'rebate', index: 'rebate', width: 80},
            {
                label: '是否推送第三方',
                name: 'useThirdPart',
                index: 'use_third_part',
                width: 80,
                formatter: (value, options, row) => value === 0 ?
                    '<span class="label label-danger">不推送</span>' :
                    '<span class="label label-success">推送</span>'
            }
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
        cardInfo: {},
        type: [{
            "code": 0,
            "value": "否"
        }, {
            "code": 1,
            "value": "是"
        }]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.cardInfo = {};
        },
        update: function (event) {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            let url = vm.cardInfo.id == null ? "cardinfo/save" : "cardinfo/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.cardInfo),
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
            let ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要禁用选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "cardinfo/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code === 0) {
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
            $.get(baseURL + "cardinfo/info/" + id, function (r) {
                vm.cardInfo = r.cardInfo;
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});