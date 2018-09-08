$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'productdetail/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '产品名称', name: 'prodDetailName', index: 'prod_detail_name', width: 80},
            // {label: '回购价', name: 'prodDetailBuyBack', index: 'prod_detail_buy_back', width: 80},
            {label: '产品类型', name: 'typeName', index: 't.prod_name', width: 80},
            {label: '产品积分数', name: 'prodDetailValue', index: 'prod_detail_value', width: 80},
            {label: '兑换次数', name: 'prodDetailCount', index: 'prod_detail_count', width: 80}
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

class Params {
    constructor(level, buyBackPrice) {
        this.level = level
        this.buyBackPrice = buyBackPrice
    }
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {
            prodDetailName: ""
        },
        showList: true,
        title: null,
        productDetail: {params: []},
        productType: []
    },
    created() {
        this.getProdType()
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: () => vm.q.prodDetailName = "",
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.productDetail = {
                params: []
            };
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
            console.log(vm.productDetail)
            let url = vm.productDetail.id == null ? "productdetail/save" : "productdetail/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.productDetail),
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
                    url: baseURL + "productdetail/delete",
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
            $.get(baseURL + "productdetail/info/" + id, function (r) {
                vm.productDetail = r.productDetail;
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'prodDetailName': vm.q.prodDetailName},
                page: page
            }).trigger("reloadGrid");
        },
        getProdType: function () {
            $.get(baseURL + "producttype/productType/", r => {
                console.log(r)
                vm.productType = r.productTypes
            })
            console.log(this.productType)
        },
        addIndex() {
            this.productDetail.params.push(new Params())
        }
    }
});