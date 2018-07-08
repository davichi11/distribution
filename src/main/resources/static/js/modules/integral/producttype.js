$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'producttype/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '产品类型名称', name: 'prodName', index: 'prod_name', width: 80},
            {label: '结算周期', name: 'prodRate', index: 'prod_rate', width: 80},
            {label: '产品描述', name: 'prodRemark', index: 'prod_remark', width: 80},
            {label: '图标', name: 'prodImg', index: 'prod_img', width: 80}
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
    constructor(level, exchangePercent) {
        this.level = level
        this.exchangePercent = exchangePercent
    }
}
var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {prodName: ""},
        showList: true,
        title: null,
        productType: {params: []}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: () => vm.q.prodName = "",
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.productType = {params: []};
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
            let url = vm.productType.id == null ? "producttype/save" : "producttype/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.productType),
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
                    url: baseURL + "producttype/delete",
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
            $.get(baseURL + "producttype/info/" + id, function (r) {
                vm.productType = r.productType;
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'prodName': vm.q.prodName},
                page: page
            }).trigger("reloadGrid");
        },
        triggerFile(event) {
            let file = event.target.files[0]; // (利用console.log输出看结构就知道如何处理档案资料)
            console.log(file)
            // do something...
            let formData = new FormData();
            formData.append("file", file);
            this.$http.upload(this.$http.adornUrl("/api/upload"), formData).then(({data}) => {
                if (data.code === 0) {
                    this.productType.prodImg = data.results.url
                }
            })
        },
        addIndex() {
            this.productType.params.push(new Params())
        }
    }
});