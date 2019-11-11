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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,permission-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<!--[if lt IE 9]>
<script type="text/javascript" src="lib/html5shiv.js"></script>
<script type="text/javascript" src="lib/respond.min.js"></script>
<![endif]-->
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />
<!--[if IE 6]>
<script type="text/javascript" src="lib/DD_belatedPNG_0.0.8a-min.js" ></script>
<script>DD_belatedPNG.fix('*');</script>
<![endif]-->
<title>添加管理员 - 管理员管理 - H-ui.admin v3.1</title>
<meta name="keywords" content="H-ui.admin v3.1,H-ui网站后台模版,后台模版下载,后台管理系统模版,HTML后台模版下载">
<meta name="description" content="H-ui.admin v3.1，是一款由国人开发的轻量级扁平化网站后台模板，完全免费开源的网站后台管理系统模版，适合中小型CMS后台系统。">
</head>
<body>
<article class="page-container">
	<form class="form form-horizontal" action="${empty permission ? 'permission/insert.do':'permission/update.do'}" id="form-admin-add">
<!-- 	隐藏域,因为现在当是修改的时候，需要知道修改的是哪一个，而这个时候，名字不能传过去 -->
	<input type="hidden" name="permissionId" value="${permission.permissionId}"/>
<!-- 	名字 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${permission.name}" ${empty permission ? '': 'disabled'}  placeholder="请输入权限名" id="name" name="name">
		</div>
	</div>
<!-- 	url地址 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限地址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${permission.url}" placeholder="请输入权限地址" id="url" name="url">
		</div>
	</div>
<!-- 	表达式 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限表达式：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" autocomplete="off" value="${permission.expression}" placeholder="请输入权限表达式" id="expression" name="expression">
		</div>
	</div>
<!-- 	菜单权限 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>权限类型：</label>
		<div class="formControls col-xs-8 col-sm-9 skin-minimal">
			<div class="radio-box">
				<input name="type" type="radio" value="menu" id="sex-1" checked>
				<label for="sex-1">菜单权限</label>
			</div>
			<div class="radio-box">
				<input name="type" type="radio" value="permission" id="sex-2" >
				<label for="sex-2">普通权限</label>
			</div>
		</div>
	</div>
<!-- 父类为何 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">父类权限：</label>
		<div class="formControls col-xs-8 col-sm-9">
		    <span class="select-box" style="width:150px;">
			<select class="select" name="parentId" size="1">
				<option value="">请选择</option>
				<c:forEach items="${permissionList}" var="item">
					<option value="${item.permissionId}" ${permission.parentId eq item.permissionId ? 'selected':'' }>${item.name}</option>
				</c:forEach>
			</select>
			</span> 
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
<script type="text/javascript">
$(function(){
	$("#form-admin-add").validate({
		rules:{
			name:{
				required:true,
				remote: {
				    url: "permission/checkName.do",     //后台处理程序
				    type: "get",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				        name: function() {
				            return $("#name").val();
				        }
				    }
				}
			},
			url:{
				required:true,
			},
			expression:{
				required:true,
			}
		},
		messages:{
			name:{
				required:"权限名不能为空！",
				remote: "该权限名已存在！"
			},
			url:{
				required:"权限地址不能为空！",
			},
			expression:{
				required:"权限表达式不能为空！",
			}
		},
		submitHandler:function(form){
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
});
</script> 
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>