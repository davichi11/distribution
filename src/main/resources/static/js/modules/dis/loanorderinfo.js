$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'loanorderinfo/list',
        datatype: "json",
        colModel: [
            // { label: 'id', name: 'id', index: 'id', width: 50, key: true },
            {label: '订单编号', name: 'orderId', index: 'order_id', width: 80},
            {label: '下单手机号', name: 'orderMobile', index: 'order_mobile', width: 80},
            {label: '身份证号', name: 'orderIdcardno', index: 'order_idcardno', width: 160},
            {
                label: '订单状态',
                name: 'orderStatus',
                index: 'order_status',
                width: 80,
                formatter: function (value, options, row) {
                    if (value === 0) {
                        return '<span class="label label-danger">失败</span>'
                    }
                    if (value === 1) {
                        return '<span class="label label-success">成功</span>'
                    }
                    if (value === 2) {
                        return '<span class="label label-success">申请中</span>'
                    }
                }
            },
            {label: '订单状态', name: 'orderStatus', index: 'order_statusnum', width: 80, hidden: true},
            {label: '用户', name: 'memberId', index: 'member_id', width: 80},
            {label: '贷款产品', name: 'loanId', index: 'loan_id', width: 80},
            {label: '本金', name: 'loanAmount', index: 'loan_amount', width: 80},
            {label: '数据状态', name: 'isDelete', index: 'is_delete', width: 80},
            {label: '下单时间', name: 'addTime', index: 'add_time', width: 160},
            {
                label: '分润金额',
                name: 'loanMoney',
                index: 'loan_money',
                width: 80,
                formatter: function (value, options, row) {
                    debugger
                    if (value === 0) {
                        return '<span class="label label-danger">暂无分润</span>'
                    }

                    return value
                }
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
        loanOrderInfo: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.loanOrderInfo = {};
        },
        update: function (event) {
            //页面层-自定义
            var id = getSelectedRow();
            var applydata = getSelectedRowData();
            if (id == undefined) {
                return
            }
            if (applydata.orderStatus != 2) {
                layer.msg('请选择可以分润的订单');
                return
            }
            layer.open({
                type: 1,
                title: '设置分润金额',
                skin: 'layui-layer-rim',
                area: ['450px', 'auto'],
                content: ` 
                     <div class="row" style="width: 420px;  margin-left:7px; margin-top:10px;">
                         <div class="col-sm-12">
                             <div class="input-group">
                                 <span class="input-group-addon"> 金 额 为 :</span>
                                 <input type="text" id="fenrunmoney" placeholder="分润金额" class="form-control"/>
                             </div>
                         </div>
                     </div>
                     `
                ,
                btn: ['保存', '取消'],
                btn1: function (index, layero) {
                    $.ajax({
                        type: "POST",
                        url: baseURL + "loanorderinfo/fenrunmoney",
                        data: {applyforid: id, fenrunmoney: $('#fenrunmoney').val()},
                        contentType: 'application/x-www-form-urlencoded',
                        success: function (r) {
                            if (r.code == 0) {
                                layer.close(index);
                                alert('操作成功', function (index) {
                                    $("#jqGrid").trigger("reloadGrid");
                                });
                            } else {
                                alert(r.msg);
                                layer.close(index);
                            }
                        }
                    });
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }

            });
        },
        saveOrUpdate: function (event) {
            var url = vm.loanOrderInfo.id == null ? "loanorderinfo/save" : "loanorderinfo/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.loanOrderInfo),
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
                    url: baseURL + "loanorderinfo/delete",
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
            $.get(baseURL + "loanorderinfo/info/" + id, function (r) {
                vm.loanOrderInfo = r.loanOrderInfo;
            });
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        }
    }
});