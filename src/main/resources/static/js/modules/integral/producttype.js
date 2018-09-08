$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'producttype/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '产品类型名称', name: 'prodName', index: 'prod_name', width: 80},
            {label: '结算周期', name: 'prodRate', index: 'prod_rate', width: 30},
            {label: '兑换比率', name: 'exchangePercent', index: 'exchange_percent', width: 30},
            {
                label: '提单类型',
                name: 'prodType',
                index: 'prod_type',
                width: 30,
                formatter: (value, options, row) => value === '0' ?
                    '<span class="label label-info">短信</span>' :
                    '<span class="label label-success">图片</span>'
            },
            {label: '产品描述', name: 'prodRemark', index: 'prod_remark', width: 80},
            {
                label: '图标',
                name: 'prodImg',
                index: 'prod_img',
                width: 80,
                formatter: (value, options, row) => `<a href="#" onclick="showImg('${value}')">点击查看图标</a>`
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

class Tutorial {
    constructor(step, textDescribe, imgDescribe) {
        this.step = step
        this.imgDescribe = imgDescribe
        this.textDescribe = textDescribe
    }
}

var vm = new Vue({
    el: '#rrapp',
    data: {
        q: {prodName: ""},
        showList: true,
        title: null,
        productType: {
            prodImg: '',
            prodName: '',
            prodRate: 0,
            prodRemark: '',
            prodType: '',
            exchangePercent: 0.00,
            tutorials: []
        },
        type: [{value: 0, name: '短信'}, {value: 1, name: '图片'}]

    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: () => vm.q.prodName = "",
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.productType = {
                prodImg: '',
                prodName: '',
                prodRate: 0,
                prodRemark: '',
                prodType: '',
                exchangePercent: 0.00,
                tutorials: [new Tutorial()]
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
        triggerFile: function (event) {
            let file = event.target.files[0]; // (利用console.log输出看结构就知道如何处理档案资料)
            // do something...
            console.log(file)
            let formData = new FormData();
            formData.append("file", file);
            $.ajax({
                type: "POST",
                url: baseURL + "api/upload",
                dataType: "json",
                processData: false,  // 注意：让jQuery不要处理数据
                contentType: false,
                data: formData,
                success: r => {
                    if (r.code === 0) {
                        vm.productType.prodImg = r.url
                        console.log(vm.productType)
                    }
                }
            });
        },
        addFile: function (event, index) {
            // console.log(index)
            let file = event.target.files[0]; // (利用console.log输出看结构就知道如何处理档案资料)
            // do something...
            // console.log(file)
            let formData = new FormData();
            formData.append("file", file);
            $.ajax({
                type: "POST",
                url: baseURL + "api/upload",
                dataType: "json",
                processData: false,  // 注意：让jQuery不要处理数据
                contentType: false,
                data: formData,
                success: r => {
                    if (r.code === 0) {
                        vm.productType.tutorials[index].imgDescribe = r.url
                        vm.productType.tutorials[index].step = index + 1
                    }
                }
            });
        },
        addTutorials: function () {
            vm.productType.tutorials.push(new Tutorial())
        }
    }
});

