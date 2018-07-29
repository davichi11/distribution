$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'picture/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {
                label: '图片', name: 'imgUrl', index: 'img_url', width: 80,
                formatter: (value, options, row) => `<a href="#" onclick="showImg('${value}')">点击查看图标</a>`
            },
            {label: '对应的文章标题', name: 'articleTitle', index: 'article_title', width: 80},
            {label: '排序', name: 'order', index: 'order_num', width: 80}
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
        adPicture: {
            imgUrl: "",
            order: 0
        },
        articles: []
    },
    created() {
        this.getArticles()
    },
    methods: {
        query: function () {
            vm.reload();
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增";
            vm.adPicture = {};
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
            let url = vm.adPicture.id == null ? "picture/save" : "picture/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.adPicture),
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
            confirm('确定要禁用选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "picture/delete",
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
            $.get(baseURL + "picture/info/" + id, function (r) {
                vm.adPicture = r.adPicture;
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                page: page
            }).trigger("reloadGrid");
        },
        getArticles: function () {
            $.get(baseURL + "articleinfo/list", function (r) {
                vm.articles = r.page.list;
            });
        },
        addFile: function (event) {
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
                        vm.adPicture.imgUrl = r.url
                    }
                }
            });
        }
    }
});