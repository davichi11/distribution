$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'cardorderinfo/list',
        datatype: "json",
        colModel: [			
			{ label: 'id', name: 'id', index: 'id', width: 50, key: true },
			{ label: '', name: 'orderName', index: 'order_name', width: 80 }, 			
			{ label: '', name: 'orderId', index: 'order_id', width: 80 }, 			
			{ label: '', name: 'orderMobile', index: 'order_mobile', width: 80 }, 			
			{ label: '', name: 'orderIdcardno', index: 'order_idcardno', width: 80 }, 			
			{ label: '', name: 'orderEmail', index: 'order_email', width: 80 }, 			
			{ label: '订单状态 0:失败,1:成功,2:申请中', name: 'orderStatus', index: 'order_status', width: 80 }, 			
			{ label: '用户关联ID', name: 'memberId', index: 'member_id', width: 80 }, 			
			{ label: '关联信用卡ID', name: 'cardId', index: 'card_id', width: 80 }, 			
			{ label: '', name: 'isDelete', index: 'is_delete', width: 80 }, 			
			{ label: '', name: 'addTime', index: 'add_time', width: 80 }, 			
			{ label: '', name: 'updateTime', index: 'update_time', width: 80 }			
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
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
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
		cardOrderInfo: {}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增";
			vm.cardOrderInfo = {};
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
			var url = vm.CardOrderInfo.id == null ? "cardorderinfo/save" : "cardorderinfo/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.cardOrderInfo),
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
				    url: baseURL + "cardorderinfo/delete",
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
			$.get(baseURL + "cardorderinfo/info/"+id, function(r){
                vm.cardOrderInfo = r.cardOrderInfo;
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