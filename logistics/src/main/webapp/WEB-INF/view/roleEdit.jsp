<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<link rel="stylesheet" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css">
<title>添加角色 - 角色管理 </title>
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" action="${empty role ? 'role/insert.do':'role/update.do'}" id="form-admin-add">
<!-- 	隐藏域,因为现在当是修改的时候，需要知道修改的是哪一个，而这个时候，名字不能传过去 -->
	<input type="hidden" name="roleId" value="${role.roleId}"/>
<!-- 	角色 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${role.rolename}" placeholder="请输入角色名" id="rolename" name="rolename">
		</div>
	</div>
<!-- 	描述 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色描述：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${role.remark}" placeholder="请输入角色描述" id=remark name="remark">
		</div>
	</div>
<!-- 隐藏域，这个才是真正发送到后台的东西 -->
<input type="hidden" id="permissionIds" name="permissionIds"/>
<!-- 	角色权限 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>角色权限：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<ul id="permissionTree" class="ztree"></ul>
		</div>
	</div>
<!-- 	提交信息 -->
	<div class="row cl">
		<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-3">
			<input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;提交&nbsp;&nbsp;">
		</div>
	</div>
	</form>
</article>

<!--_footer 作为公共模版分离出去--> 
<script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script> 
<script type="text/javascript" src="lib/layer/2.4/layer.js"></script>
<script type="text/javascript" src="static/h-ui/js/H-ui.min.js"></script> 
<script type="text/javascript" src="static/h-ui.admin/js/H-ui.admin.js"></script> <!--/_footer 作为公共模版分离出去-->

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script> 
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script> 
<script src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"> </script>
<script type="text/javascript">
$(function(){
// 	首先是关于提交后验证的
	$("#form-admin-add").validate({
		rules:{
			rolename:{
				required:true,
				<c:if test="${empty role}">
				remote: {
				    url: "role/checkRolename.do",     //后台处理程序
				    type: "get",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				    	rolename: function() {
				            return $("#rolename").val();
				        }
				    }
				}
				</c:if>
			},
			remark:{
				required:true
			}
		},
		messages:{
			rolename:{
				required:"角色名不能为空！",
				remote: "该角色名已存在！"
			},
			remark:{
				required:"角色描述不能为空！",
			}
		},
		submitHandler:function(form){
			getCheckList();
			$(form).ajaxSubmit(function(data){
				layer.msg(data.info,{icon:data.code,time:1000},function(){
					if(data.code==1){
						parent.refreshTable();
						parent.layer.closeAll();
					}
				});
			});
		}
	});
// 	接着是ztree的初始化的
	$.fn.zTree.init($("#permissionTree"), setting);
});
// 下面是进行设置的东西
var setting = {
		/* data ：ztree的数据的相关配置
			simpleData:是否支持简单格式的数据
		*/
		/* 是否支持复选框 */
		check:{enable:true},
		data:{
			simpleData: {
				enable: true
			}
		},
		/* 异步加载数据 */
		async: {
			enable: true,
			url:"permission/getAllpermissions.do",
			dataFilter: filter
		},
//	 	上面异步加载回调的函数
		callback: {
			onAsyncSuccess: zTreeOnAsyncSuccess
		}
	};
// 	上面异步加载回调的函数
function zTreeOnAsyncSuccess(event, treeId, treeNode, msg) {
// 	得到一个树对象
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
// 	接收从数据库得到的id
    var permissionIds="${role.permissionIds}";
    //是一个字符串，所以要先弄成数组
    var permissionArr=permissionIds.split(",");
    //循环没一个数据，根据这个数据得到树节点
    for(var i=0;i<permissionArr.length;i++){
    	var permissionId=permissionArr[i];
    	var node = treeObj.getNodeByParam("id", permissionId, null);
//    	让得到的这个树节点选中状态
    	treeObj.checkNode(node, true, false);
    }
};
// 格式化，因为那边传过来的东西的属性名是permissionId和parentId,在数插件里面，是用id与pid来进行接收的，所以两个是不一样的
	function filter(treeId, parentNode, childNodes) {
		if (!childNodes) return null;
		for (var i=0, l=childNodes.length; i<l; i++) {
			childNodes[i].id = childNodes[i].permissionId;
			childNodes[i].pId = childNodes[i].parentId;
			childNodes[i].open=true;
		}
		return childNodes;
	}
	
// 	设置树的值进表单去
	function getCheckList(){
//	 	得到树对象
		var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
//	 	得到选中的节点
	// 遍历重新组合得到只有id的数组
		var nodes = treeObj.getCheckedNodes(true);
		var checkList=[];
		nodes.forEach(function(element, index) {
			checkList.push(element.permissionId);
		})
//	 	把这个数组弄成字符串再发到后台中
		var checkStr=checkList.join(",");
//	 	dom操作，设置值进隐藏域中
		$("#permissionIds").val(checkStr) 
	}
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>