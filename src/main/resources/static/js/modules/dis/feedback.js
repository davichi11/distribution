$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'feedback/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '会员', name: 'member.disUserName', index: 'member_id', width: 80},
            {label: '反馈标题', name: 'title', index: 'title', width: 180},
            {label: '反馈内容', name: 'content', index: 'content', width: 280},
            {label: '反馈时间', name: 'addTime', index: 'add_time', width: 80}
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
        title: "",
        user_name: "",
        mobile: "",
        feedback: {}
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: function () {
            this.mobile = "";
            this.title = "";
            this.user_name = "";
        },
        getInfo: function (id) {
            $.get(baseURL + "feedback/info/" + id, function (r) {
                vm.feedback = r.feedback;
            });
        },
        reload: function (event) {
            vm.showList = true;
            let page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {
                    "mobile": vm.mobile,
                    "title": vm.title,
                    "user_name": vm.user_name
                },
                page: page
            }).trigger("reloadGrid");
        }
    }
});