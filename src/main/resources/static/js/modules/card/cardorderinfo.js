$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'cardorderinfo/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true},
            {label: '用户名', name: 'memberInfo.disUserName', index: 'order_name', width: 80},
            {label: '办卡人手机号', name: 'orderMobile', index: 'order_mobile', width: 80},
            {label: '办卡人身份证号码', name: 'orderIdcardno', index: 'order_idcardno', width: 80},
            {
                label: '订单状态',
                name: 'orderStatus',
                index: 'order_status',
                width: 80,
                formatter: (value, options, row) => {
                    if (value === 0) {
                        return "失败";
                    } else if (value === 1) {
                        return "成功";
                    }
                    else if (value === 2) {
                        return "申请中";
                    }
                }
            },
            {label: '办卡时间', name: 'addTime', index: 'add_time', width: 80},
            {label: '信用卡名称', name: 'cardInfo.cardName', index: 'card_id', width: 80},
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
        cardOrderInfo: {},
        orderMobile: "",
        disUserName: "",
        cardName: ""
    },
    methods: {
        query: function () {
            this.reload();
        },
        reset: function () {
            this.orderMobile="";
            this.disUserName="";
            this.cardName="";
        },
        statusUpdate:function(status) {
            var _self=this;
            let ids = getSelectedRows();
            if(ids.length<1){
                return;
            }
            $.ajax({
                type: "PUT",
                url: baseURL + "/cardorderinfo/statusUpdate",
                contentType: "application/json",
                data: JSON.stringify({
                    ids:ids,
                    orderStatus:status
                }),
                dataType: "json",
                success: function (r) {
                    if (r.code === 0) {
                        layer.alert('修改成功');
                        _self.reload();
                    } else {
                        layer.alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            var _self = this;
            _self.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "orderMobile": _self.orderMobile,
                    "disUserName": _self.disUserName,
                    "cardName": _self.cardName
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});