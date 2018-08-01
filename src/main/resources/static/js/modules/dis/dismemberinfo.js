$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'dismemberinfo/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '用户名称', name: 'disUserName', index: 'm.dis_user_name', width: 80},
            {label: '级别', name: 'disLevel', index: 'm.dis_level', width: 80},
            {
                label: '身份类型', name: 'disUserType', index: 'm.dis_user_type', width: 80,
                formatter: (value, options, row) => value === '0' ?
                    '<span class="label label-danger">非会员</span>' :
                    '<span class="label label-success">会员</span>'
            },
            {label: '上级', name: 'disMemberParent.disUserName', index: 'm.dis_model_id', width: 80},
            {label: '用户手机号', name: 'userEntity.mobile', index: 'u.mobile', width: 80},
            {label: '注册时间', name: 'addTime', index: 'm.add_time', width: 80}
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
        gridComplete: () => {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    new AjaxUpload('#upload', {
        action: baseURL + 'dismemberinfo/batchSendMoney?token=' + token,
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: (file, extension) => {
            if (!(extension && /^(xls|xlsx)$/.test(extension.toLowerCase()))) {
                alert('请上传Excel文件！');
                return false;
            }
        },
        onComplete: (file, r) => {
            if (r.code === 0) {
                alert(r.url);
                vm.reload();
            } else {
                alert(r.msg);
            }
        }
    });
});

let vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            disLevel: null,
            disUserType: null,
            disUserName: "",
            mobile: "",
            parentId: ""
        },
        showList: true,
        title: null,
        disMemberInfo: {}
    },
    methods: {
        query: () => {
            vm.reload();
        },
        queryChildren: () => {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = true;
            vm.parentId = id
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    'parent_id': vm.parentId
                },
                page: page
            }).trigger("reloadGrid");
        },
        reset: () => {
            vm.q.disLevel = null;
            vm.q.disUserType = null;
            vm.q.disUserName = "";
            vm.q.mobile = ""
            vm.parentId = ""
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
                postData: {
                    'disLevel': vm.q.disLevel,
                    'disUserType': vm.q.disUserType,
                    'disUserName': vm.q.disUserName,
                    'mobile': vm.q.mobile,
                    'parent_id': ""
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});