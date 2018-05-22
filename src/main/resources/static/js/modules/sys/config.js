$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'sys/config/list',
        datatype: "json",
        colModel: [
            {label: 'ID', name: 'id', width: 30, key: true},
            {label: '参数名', name: 'key', width: 60},
            {label: '参数值', name: 'value', width: 100},
            {label: '备注', name: 'remark', width: 80}
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
            total: "page.pageSize",
            records: "page.total"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: () => {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            key: null
        },
        showList: true,
        title: null,
        config: {}
    },
    methods: {
        query: () => vm.reload(),
        reset: () => vm.q.key = "",
        add: () => {
            vm.showList = false;
            vm.title = "新增";
            vm.config = {};
        },
        update: () => {
            let id = getSelectedRow();
            if (id === null) {
                return;
            }

            $.get(baseURL + "sys/config/info/" + id, r => {
                vm.showList = false;
                vm.title = "修改";
                vm.config = r.config;
            });
        },
        del: event => {
            let ids = getSelectedRows();
            if (ids === null) {
                return;
            }

            confirm('确定要删除选中的记录？', () => {
                $.ajax({
                    type: "POST",
                    url: baseURL + "sys/config/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (result) {
                        if (result.code === 0) {
                            alert('操作成功', () => vm.reload());
                        } else {
                            alert(result.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: () => {
            let url = isNull(vm.config.id) ? "sys/config/save" : "sys/config/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.config),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', () => vm.reload());
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        reload: () => {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'key': vm.q.key},
                page: page
            }).trigger("reloadGrid");
        }
    }
});