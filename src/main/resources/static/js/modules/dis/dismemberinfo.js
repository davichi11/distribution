$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'dismemberinfo/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '全路径', name: 'disFullIndex', index: 'dis_full_index', width: 80},
            {label: '用户名称', name: 'disUserName', index: 'dis_user_name', width: 80},
            {label: '级别', name: 'disLevel', index: 'dis_level', width: 80},
            {label: '身份类型', name: 'disUserType', index: 'dis_user_type', width: 80},
            {label: '上级', name: 'disMemberParent.disUserName', index: 'dis_model_id', width: 80},
            {label: '备注', name: 'disNote', index: 'dis_note', width: 80},
            {label: '添加时间', name: 'addTime', index: 'add_time', width: 80}
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
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
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
            disLevel: null,
            disUserType: null
        },
        showList: true,
        title: null,
        disMemberInfo: {}
    },
    methods: {
        query: () => {
            vm.reload();
        },
        reset: () => {
            vm.q.disLevel = null;
            vm.q.disUserType = null;
        },
        add: () => {
            vm.showList = false;
            vm.title = "新增";
            vm.disMemberInfo = {};
        },
        update: () => {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
        },
        saveOrUpdate: () => {
            let url = vm.disMemberInfo.id == null ? "dismemberinfo/save" : "dismemberinfo/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.disMemberInfo),
                success: r => {
                    if (r.code === 0) {
                        alert('操作成功', () => {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        del: () => {
            let ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', () => {
                $.ajax({
                    type: "POST",
                    url: baseURL + "dismemberinfo/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', () => {
                                $("#jqGrid").trigger("reloadGrid");
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        getInfo: id => {
            $.get(baseURL + "dismemberinfo/info/" + id, r => {
                vm.disMemberInfo = r.disMemberInfo;
            });
        },
        reload: () => {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'disLevel': vm.q.disLevel, 'disUserType': vm.q.disUserType},
                page: page
            }).trigger("reloadGrid");
        }
    }
});