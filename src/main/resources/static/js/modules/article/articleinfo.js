$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'articleinfo/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '文章标题', name: 'title', index: 'title', width: 80},
            {label: '文章内容', name: 'detail', index: 'detail', width: 80},
            {
                label: '文章类别', name: 'type', index: 'type', width: 80,
                formatter: (value, options, row) => value === 0 ?
                    '<span class="label label-info">客户推广</span>' :
                    '<span class="label label-success">热门活动</span>'
            },
            {label: '排序', name: 'order', index: 'order_num', width: 80}
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
    markdown($("#text"), $("#md-button-bar"), $("#md-preview"));
});

var vm = new Vue({
    el: '#rrapp',
    data: {
        showList: true,
        title: null,
        articleInfo: {},
        preview: false,
        type: [{
            "code": "0",
            "value": "客户推广"
        }, {
            "code": "1",
            "value": "热门活动"
        }]
    },
    methods: {
        query: function () {
            vm.reload();
        },
        showInfo: function () {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.preview = true
            vm.title = "查看";
            vm.getInfo(id)
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.articleInfo = {};
            vm.preview = false
        },
        update: function (event) {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.preview = false
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: function (event) {
            let url = vm.articleInfo.id == null ? "articleinfo/save" : "articleinfo/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.articleInfo),
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

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "articleinfo/delete",
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
            $.get(baseURL + "articleinfo/info/" + id, function (r) {
                vm.articleInfo = r.articleInfo;
            });
        },
        reload: function (event) {
            vm.showList = true;
            vm.preview = false
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});