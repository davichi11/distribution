$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'loanorderinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '订单编号', name: 'orderId', index: 'order_id', width: 80 }, 			
			{ label: '下单手机号', name: 'orderMobile', index: 'order_mobile', width: 80 },
			{ label: '身份证号', name: 'orderIdcardno', index: 'order_idcardno', width: 80 },
			{ label: '订单状态 0失败 1成功 2申请中', name: 'orderStatus', index: 'order_status', width: 80 }, 			
			{ label: '用户', name: 'memberId', index: 'member_id', width: 80 },
			{ label: '贷款产品', name: 'loanId', index: 'loan_id', width: 80 },
			{ label: '本金', name: 'loanAmount', index: 'loan_amount', width: 80 },
			{ label: '数据状态', name: 'isDelete', index: 'is_delete', width: 80 },
			{ label: '下单时间', name: 'addTime', index: 'add_time', width: 80 },
			{ label: '更新时间', name: 'updateTime', index: 'update_time', width: 80 }
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        rownumbers: true, 
        rownumWidth: 25, 
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.pageNum",
            total: "page.pages",
            records: "page.total"
        },
        prmNames : {
            page:"page", 
            rows:"limit", 
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#rrapp',
	data:{
		showList: true,
		title: null,
		loanOrderInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.loanOrderInfo = {};
		},
		update: function (event) {
			var id = getSelectedRow();
			if(id == null){
				return ;
			}
			vm.showList = false;
            vm.title = "修改";
            
            vm.getInfo(id)
		},
		saveOrUpdate: function (event) {
			var url = vm.loanOrderInfo.id == null ? "loanorderinfo/save" : "loanorderinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.loanOrderInfo),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(index){
							vm.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		del: function (event) {
			var ids = getSelectedRows();
			if(ids == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "loanorderinfo/delete",
                    contentType: "application/json",
				    data: JSON.stringify(ids),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(index){
								$("#jqGrid").trigger("reloadGrid");
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		getInfo: function(id){
			$.get(baseURL + "loanorderinfo/info/"+id, function(r){
                vm.loanOrderInfo = r.loanOrderInfo;
            });
		},
		reload: function (event) {
			vm.showList = true;
			var page = $("#jqGrid").jqGrid('getGridParam','page');
			$("#jqGrid").jqGrid('setGridParam',{ 
                page:page
            }).trigger("reloadGrid");
		}
	}
});