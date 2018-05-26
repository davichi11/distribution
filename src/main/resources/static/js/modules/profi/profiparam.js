$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'disprofiparam/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '平台id', name: 'disPlatformId', index: 'dis_platform_id', width: 80},
            {
                label: '分润类别',
                name: 'disProType',
                index: 'dis_pro_type',
                width: 80,
                formatter: (value, options, row) => value === 0 ?
                    '交易分润' :
                    '上下级分润'
            },
            {label: '分润值', name: 'disProValue', index: 'dis_pro_value', width: 80},
            {
                label: '分润模型',
                name: 'disProMode',
                index: 'dis_pro_mode',
                width: 80,
                formatter: (value, options, row) => value === '0' ?
                    '百分比' :
                    '金额'
            },
            {label: '从下往上对应的级别关系', name: 'disProLevel', index: 'dis_pro_level', width: 80},
            {
                label: '会员类型',
                name: 'disUserType',
                index: 'dis_user_type',
                width: 80,
                formatter: (value, options, row) => value === 0 ?
                    '<span class="label label-danger">非会员</span>' :
                    '<span class="label label-success">会员</span>'
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
        q: {disProLevel: null},
        showList: true,
        title: null,
        disProfiParam: {}
    },
    methods: {
        query: () => {
            vm.reload();
        },
        reset: () => {
            vm.q.disProLevel = null;
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.disProfiParam = {};
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
            let url = vm.disProfiParam.id == null ? "disprofiparam/save" : "disprofiparam/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.disProfiParam),
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
                    url: baseURL + "disprofiparam/delete",
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
            $.get(baseURL + "disprofiparam/info/" + id, function (r) {
                vm.disProfiParam = r.disProfiParam;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'disProLevel': vm.q.disProLevel},
                page: page
            }).trigger("reloadGrid");
        }
    }
});