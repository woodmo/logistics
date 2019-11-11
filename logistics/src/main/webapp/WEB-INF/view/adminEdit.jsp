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
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
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
	<form class="form form-horizontal" action="${empty user ? 'admin/insert.do':'admin/update.do'}" id="form-admin-add">
<!-- 	隐藏域,因为现在当是修改的时候，需要知道修改的是哪一个，而这个时候，名字不能传过去 -->
	<input type="hidden" name="userId" value="${user.userId}"/>
<!-- 	账号 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>账号：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${user.username}" ${empty user ? '': 'disabled'}  placeholder="请输入账号" id="username" name="username">
		</div>
	</div>
<!-- 	真实姓名 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>真实姓名：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${user.realname}" placeholder="请输入真实姓名" id="realname" name="realname">
		</div>
	</div>
<!-- 	输入密码 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>初始密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off" value="" placeholder="请输入密码" id="password" name="password">
		</div>
	</div>
<!-- 	确认密码 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>确认密码：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="password" class="input-text" autocomplete="off"  placeholder="确认新密码" id="password2" name="password2">
		</div>
	</div>
	
<!-- 	<div class="row cl"> -->
<!-- 		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>性别：</label> -->
<!-- 		<div class="formControls col-xs-8 col-sm-9 skin-minimal"> -->
<!-- 			<div class="radio-box"> -->
<!-- 				<input name="sex" type="radio" id="sex-1" checked> -->
<!-- 				<label for="sex-1">男</label> -->
<!-- 			</div> -->
<!-- 			<div class="radio-box"> -->
<!-- 				<input type="radio" id="sex-2" name="sex"> -->
<!-- 				<label for="sex-2">女</label> -->
<!-- 			</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
<!-- 	何种角色 -->
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">角色：</label>
		<div class="formControls col-xs-8 col-sm-9">
		    <span class="select-box" style="width:150px;">
			<select class="select" name="roleId" size="1">
				<option value="-1">请选择</option>
				<c:forEach items="${roleList}" var="item">
					<option value="${item.roleId}" ${user.roleId eq item.roleId ? 'selected':'' }>${item.rolename}</option>
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
			username:{
				required:true,
				remote: {
				    url: "admin/checkUsername.do",     //后台处理程序
				    type: "get",               //数据发送方式
				    dataType: "json",           //接受数据格式   
				    data: {                     //要传递的数据
				        username: function() {
				            return $("#username").val();
				        }
				    }
				}
			},
			realname:{
				required:true,
				minlength:2,
				isChinese:true
			},
			password:{
				required:true,
				minlength:6
			},
			password2:{
				required:true,
				equalTo: "#password"
			},
			roleId:{
				min:1
			}
		},
		messages:{
			username:{
				required:"账号不能为空！",
				remote: "该账号已存在！"
			},
			realname:{
				required:"真实姓名不能为空！",
				minlength:"姓名不少于两位数！",
				isChinese:"真实姓名必须是中文！"
			},
			password:{
				required:"密码不能为空！",
				minlength:"密码最少六位数！"
			},
			password2:{
				required:"请再次输入密码进行确认！",
				equalTo: "您两次输入的密码不同，请重新输入！"
			},
			roleId:{
				min:"请选择角色！"
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