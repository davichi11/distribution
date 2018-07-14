$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'integralorder/list',
        datatype: "json",
        colModel: [
            {label: 'id', name: 'id', index: 'id', width: 50, key: true, hidden: true},
            {label: '兑换人手机号', name: 'mobile', index: 'mobile', width: 80},
            {label: '兑换人会员等级', name: 'disLevel', index: 'dis_level', width: 80},
            {label: '兑换产品', name: 'prodName', index: 'd.prod_detail_name', width: 80},
            {
                label: '卷码图片', name: 'img', index: 'img', width: 80,
                formatter: (value, options, row) => value ? `<a href="#" onclick="showImg('${value}')">点击查看图标</a>`
                    : '无图片'
            },
            {label: '短信', name: 'record', index: 'record', width: 180},
            {label: '申请时间', name: 'addTime', index: 'add_time', width: 80},
            {
                label: '审核状态', name: 'status', index: 'status', width: 80,
                formatter: (value, options, row) => {
                    if (value === 0) {
                        return '<span class="label label-danger">失败</span>'
                    } else if (value === 1) {
                        return '<span class="label label-success">成功</span>'
                    } else {
                        return '<span class="label label-info">申请中</span>'
                    }

                }
            },
            {label: '分润金额', name: 'profiMoney', index: 'profi_money', width: 80}
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
        integralOrder: {},
        imgs: []
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reset: () => {
            vm.q.mobile = ""
            vm.q.prodName = ""
        },
        detail: () => {
            let id = getSelectedRow();
            if (id == null) {
                return;
            }
            vm.showList = false;
            vm.title = "修改";

            vm.getInfo(id)
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
                imgs = vm.integralOrder.img.split(",")
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
            let status = 1;
            if (isNull(id)) {
                alert("请选择一个记录");
                return;
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
                btn: ['确定', '取消'],
                btn1: function (index, layero) {
                    let money = $('#fenrunmoney').val()
                    axios.patch(`${baseURL}integralorder/${id}/${status}/${money}`, {}, {
                        headers: {token: token}
                    }).then(response => {
                        console.log(response)
                        if (response.data.code === 0) {
                            alert('审核成功', () => {
                                layer.closeAll();
                                vm.reload();
                            });
                        } else {
                            alert(response.data.msg);
                            layer.close(index);
                        }
                    }).catch(error => {
                        console.log(error)
                        layer.close(index);
                    });
                },
                btn2: function (index, layero) {
                    layer.close(index);
                }

            });

        },
        approveFailure: () => {
            let id = getSelectedRows();
            if (isNull(id)) {
                alert("请选择一个记录");
                return;
            }
            let status = 0;
            axios.patch(`${baseURL}integralorder/${id}/${status}/0`, {}, {
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