<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<base href="<%= basePath %>" />
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,base-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="Bookmark" href="/favicon.ico">
<link rel="Shortcut Icon" href="/favicon.ico" />
<link rel="stylesheet" type="text/css"
	href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css"
	href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css"
	href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css"
	href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css"
	href="static/h-ui.admin/css/style.css" />
<link rel="stylesheet" type="text/css"
	href="lib/bootstrap-table/bootstrap-table.min.css" />
<link rel="stylesheet" type="text/css"
	href="lib/bootstrap/css/bootstrap.min.css" />
<title>基础数据列表</title>
<style>
	.breadcrumb {
	
	margin-bottom: 0;}
</style>
</head>
<body>
<nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 首页 <span class="c-gray en">&gt;</span> 基础数据管理 <span class="c-gray en">&gt;</span> 基础数据列表 
</nav>
<div class="page-container">
	<span id="toolbar" class="l">
	
			<shiro:hasPermission name="order:delete">
		    	<a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a> 
		  	</shiro:hasPermission>
		   <shiro:hasPermission name="order:insert">
		    	<a href="javascript:;" onclick="base_add('添加基础数据','order/edit.do','800','500')" class="btn btn-primary radius"><i class="Hui-iconfont">&#xe600;</i> 添加基础数据</a>
			</shiro:hasPermission>
	 </span>
	<table id="dataTable">
		
	</table>
</div>
	<!--_footer 作为公共模版分离出去-->
	<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script>
	<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script>
	<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script>
	<!--/_footer 作为公共模版分离出去-->
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/My97DatePicker/4.8/WdatePicker.js"></script> 
<script type="text/javascript" src="lib/datatables/1.10.0/jquery.dataTables.min.js"></script> 
<script type="text/javascript" src="lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" src="lib/bootstrap-table/bootstrap-table.min.js"></script>
<script type="text/javascript" src="lib/bootstrap-table/bootstrap-table-zh-CN.min.js"></script>
<script type="text/javascript">
$(function() {
	$('#dataTable').bootstrapTable({
		url: 'order/list.do',//ajax请求的url地址
		/*
			ajax请求以后回调函数的处理
			后台使用返回的PageInfo对象中的 结果 级的key是list，总条数是total
			而前台bootstrapTable插件需要的数据的key叫做rows ，总条数也是叫做total
			那么出现一个问题 : 总条数的key能对上，结果集对不上，就需要在ajax请求完成回调
			responseHandler 这个函数方法处理一下
			并且在自定义一个 json,rows做为key，返回json的 list作为值
				total：还是total
			这样才能满足 bootstrapTable插件数据的需要
		*/
		responseHandler: function(res) { 
			/*
				res: 后台分页对象PageInfo返回对应的json对象
				res.list : 结果集
				res.total : 总记录数
			*/
			var data =  {rows: res.list,total: res.total};
			return data;
		},
		pagination: true,
 		detailView:true,//父子表
		toolbar: "#toolbar",//顶部显示的工具条（添加和批量删除的）
		contentType: 'application/x-www-form-urlencoded',//条件搜索的时候ajax请求给后台数据的数据类型（条件搜索post提交必须设置）
		search: true,//是否显示搜索框
		pageNumber: 1,//默认的页面 第一页
		pageSize: 10,//默认的每页条数
		//pageList:[10,25,50,100],//每页能显示的条数
		sidePagination: "server",//是否是服务器分页，每次请求都是对应的10条数据，下一页发送ajax请求
		paginationHAlign: 'right', //底部分页条
		showToggle: true, //是否显示详细视图和列表视图的切换按钮
		cardView: false, //是否显示详细视图
		showColumns: true, //是否显示所有的列
		showRefresh: true, //是否显示刷新按钮
		columns: [ //表格显示数据对应的表头设置，
			{ checkbox: true},//是否显示前台的复选框（多选）
			/*
				每列数据的表头的设置
				filed:返回json数据对应数据的key
				title:表头要显示的名
			*/
			{field: 'orderId',title: '编号'}, 
			{field: 'userId',title: '业务员'},
			{field: 'customerId',title: '订单客户'},
			{field: 'shippingAddress',title: '发货地址'},
			{field: 'shippingName',title: '发货人'},
			{field: 'shippingPhone',title: '发货电话'},
			{field: 'takeName',title: '收货人'},
			{field: 'takeAddress',title: '收货地址'},
			{field: 'takePhone',title: '收货电话'},
			{field: 'orderStatus',title: '订单状态'},
			{field: 'paymentMethodId',title: '付款方式'},
			{field: 'intervalId',title: '到达区域'},
			{field: 'takeMethodId',title: '取件方式'},
			{field: 'freightMethodId',title: '货运方式'},
			{field: 'orderRemark',title: '订单备注'},
			//操作列的设置（删除，修改）
			/*
			formatter: 格式化这一行，回调一个函数
			*/
			{
				field:'orderId',
				title:'操作',
				align:'center',
				formatter:operationFormatter
				
		}
			],
		/*发送请求的参数，
			params: bootstrapTable的插件内部参数对象包含如下参数
			limit, offset, search, sort
			limit：每页条数
			offset：每页的结束位置
			search:搜索框对应的值
			sort：排序
		*/
		queryParams: function(params) { 
			//此方法在用户分页或者搜索的时候回自动发送ajax请求调用，并把对应的参数传递给后台
			return {
				//页码
				pageNum: params.offset / params.limit + 1, 
				pageSize: params.limit, //页面大小
				keyword: params.search
			};
		},
		onExpandRow: function (index, row, $detail) {
			 //console.log(index,row,$detail);
			 //获取当前展开行对应的 订单id	
			 var orderId = row.orderId;
			 
			 //创建一个表格，用户点击+号时候马上创建一个表格（子表），用于添加详细数据
		     var cur_table = $detail.html('<table></table>').find('table');
		     
			 //把子表变成bootstra-table
		     $(cur_table).bootstrapTable({
		            url: 'order/detail.do',
		            method: 'get',
		            contentType: 'application/json;charset=UTF-8',//这里我就加了个utf-8
		            dataType: 'json',
		            queryParams: { orderId: orderId },
		            clickToSelect: true,
		            columns: [{
		                field: 'orderDetailId',
		                title: '订单明细编号'
		            },{
		                field: 'goodsName',
		                title: '货品名称'
		            },{
		                field: 'goodsNumber',
		                title: '获取数量'
		            },{
		                field: 'goodsTotal',
		                title: '总价'
		            },{
		                field: 'goodsRemark',
		                title: '货品描述'
		            }]
		        });
	    }
	})
	/*
	操作行格式化对应的函数 
	value: 当前列的值
	row：当前行的值
	index：索引位置
*/
function operationFormatter(value,row,index){
	var html ='<a title="编辑" href="javascript:;" onclick="base_edit('+value+')" style="text-decoration:none"><i class="Hui-iconfont"></i></a>';
	html+='<shiro:hasPermission name="order:delete">'
	html += '<a title="删除" href="javascript:;" onclick="base_del('+value+')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont"></i></a>';
	html+='</shiro:hasPermission>'
	return html;			
}


});


/*
	参数解释： 
	title	标题
	url		请求的url
	id		需要操作的数据id
	w		弹出层宽度（缺省调默认值）
	h		弹出层高度（缺省调默认值）
*/
/*基础数据-增加*/
function base_add(title,url,w,h){
	layer_show(title,url,w,h);
}

/*基础数据-编辑*/
function base_edit(orderId){
	layer_show('添加基础数据','order/edit.do'+'?orderId='+orderId,'800','500');
}
/*基础数据-删除*/
 
function base_del(data){
	layer.confirm('确认要删除吗？',{icon:3},function(index){
		$.ajax({
			type: 'GET',
			url: 'order/delect.do',
			data:{
				delList:data
			},
			success: function(data){
				layer.msg(data.info,{icon:data.code,time:2000});
				if(data.code==1){
					refreshTable();
				}
			},
			error:function(data) {
				layer.msg('删除失败，请联系基础数据。',{time:2000});
			},
		});		
	});
}
function refreshTable(){
	$("#dataTable").bootstrapTable("refresh");
}
/*基础数据-多删除*/
function datadel(){
// 	先接受选择的所有
	var delList=[];
	var checkList=$("#dataTable").bootstrapTable('getSelections');
	checkList.forEach(function(element,index){
		delList.push(element.orderId);
	})
	base_del(delList)
}
</script>
</body>
</html>