$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'integralorder/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '兑换人手机号', name: 'mobile', index: 'mobile', width: 80},
            {label: '兑换产品', name: 'prodName', index: 'd.prod_detail_name', width: 80},
            {label: '卷码图片', name: 'img', index: 'img', width: 80},
            {label: '申请时间', name: 'addTime', index: 'add_time', width: 80}
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
            mobile: '',
            prodName: ''
        },
        showList: true,
        title: null,
        integralOrder: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: () => {
            vm.q.mobile = ""
            vm.q.prodName = ""
        },
        del: function (event) {
            let ids = getSelectedRows();
            if (ids == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "integralorder/delete",
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
            $.get(baseURL + "integralorder/info/" + id, function (r) {
                vm.integralOrder = r.integralOrder;
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'mobile': vm.q.mobile, 'prodName': vm.q.prodName},
                page: page
            }).trigger("reloadGrid");
        },
        approveSuccess: () => {
            let id = getSelectedRows();
            console.log("id=" + id);
            if (isNull(id)) {
                alert("请选择一个记录");
                return;
            }
            let status = 1;
            axios.patch(`${baseURL}integralorder/${id}/${status}`, {},{
                headers: {token: token}
            }).then(response => {
                console.log(response)
                if (response.data.code === 0) {
                    alert('审核成功', () => {
                        vm.reload();
                    });
                } else {
                    alert(response.data.msg);
                }
            }).catch(error => {
                console.log(error)
            });
        },
        approveFailure: () => {
            let id = getSelectedRows();
            if (isNull(id)) {
                alert("请选择一个记录");
                return;
            }
            let status = 0;
            axios.patch(`${baseURL}integralorder/${id}/${status}`, {},{
                headers: {token: token}
            }).then(response => {
                if (response.data.code === 0) {
                    alert('审核成功', () => {
                        vm.reload();
                    });
                } else {
                    alert(response.data.msg);
                }
            }).catch(error => {
                console.log(error)
            });
        }
    }
});