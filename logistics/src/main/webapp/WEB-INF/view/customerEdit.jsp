<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	String path = request.getContextPath(); 
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	// http://localhost:8080/logistics/
%>
<!DOCTYPE HTML>
<html>
<head>
<!-- 设置页面的 基本路径，页面所有资源引入和页面的跳转全部基于 base路径 -->
<base href="<%=basePath%>">
<meta charset="utf-8">
<meta name="renderer" content="webkit|ie-comp|ie-stand">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,customer-scalable=no" />
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="stylesheet" type="text/css" href="static/h-ui/css/H-ui.min.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/H-ui.admin.css" />
<link rel="stylesheet" type="text/css" href="lib/Hui-iconfont/1.0.8/iconfont.css" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/skin/default/skin.css" id="skin" />
<link rel="stylesheet" type="text/css" href="static/h-ui.admin/css/style.css" />

</head>
<body>
<article class="page-container">
	<form  class="form form-horizontal" action="${empty customer ?'customer/insert.do' : 'customer/update.do'}" id="customerForm">
	<!-- 修改用户隐藏域 id -->
	<input type="hidden" name="customerId" value="${customer.customerId}">
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户名称：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text"   value="${customer.customerName}" placeholder="请输入客户名称" id="customerName" name="customerName">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户电话：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${customer.phone}" placeholder="" id="phone" name="phone">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户邮箱：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${customer.email}" placeholder="" id="email" name="email">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">客户地址：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${customer.address}" placeholder="" id="address" name="address">
		</div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">客户身份证：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<input type="text" class="input-text" value="${customer.idCard}" placeholder="" id="idCard" name="idCard">
		</div>
	</div>
	
	
	<div class="row cl">
			<label class="form-label col-xs-4 col-sm-3"><span class="c-red">*</span>客户性别：</label>
			<div class="formControls col-xs-8 col-sm-9 skin-minimal">
				<div class="radio-box">
					<input  type="radio" name="gender"  ${customer.gender eq 1 ?'checked':''}  value="1">
					<label for="sex-1">男</label>
				</div>
				<div class="radio-box">
					<input type="radio"  name="gender"  ${customer.gender eq 2 ?'checked':''}  value="2">
					<label for="sex-2">女</label>
				</div>
			</div>
		</div>
 	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">业务员：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<!-- 判断是否是业务员 -->
			<c:choose>
			
				<c:when test="${user.rolename eq '业务员'}">
					<shiro:principal property='realname'/>
					<input type="hidden" name="userId" value="${customer.userId}">
				</c:when>
				
				<c:otherwise>
					<select class="select" name="userId" size="1">
						<option value="-1">=请选择=</option>
						<c:forEach items="${userList}" var="u">
							<option  ${u.userId eq customer.userId ? 'selected':''} value="${u.userId}">${u.realname}</option>
						</c:forEach>
					</select>
				</c:otherwise>
				
			</c:choose>
			
			
			</span> </div>
	</div>
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">客户区间：</label>
		<div class="formControls col-xs-8 col-sm-9"> <span class="select-box" style="width:150px;">
			<select class="select" name="baseId" size="1">
				<option value="-1">=请选择=</option>
				<c:forEach items="${baseList}" var="b">
					<option  ${b.baseId eq customer.baseId ? 'selected':''} value="${b.baseId}">${b.baseName}</option>
				</c:forEach>
			</select>
			</span> </div>
	</div>
	
	<div class="row cl">
		<label class="form-label col-xs-4 col-sm-3">客户备注：</label>
		<div class="formControls col-xs-8 col-sm-9">
			<textarea name="remark" cols="" rows="" class="textarea" placeholder="请输入客户描述">${customer.remark}</textarea>
		</div>
	</div>
	
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
	
	/* 
		使用jquery.validate 进行表单校验
	*/
	$("#customerForm").validate({
		rules:{
			customerName:{
				required:true,
			},
			phone:{
				required:true,
			},
			email:{
				required:true,
			},
			address:{
				required:true,
			},
			idCard:{
				required:true,
			},
			userId:{
				min:1
			},
			baseId:{
				min:1
			}
		},
		messages:{
			customerName:{
				required:"客户名称不能为空",
			},
			phone:{
				required:"客户电话不能为空",
			},
			email:{
				required:"客户邮件不能为空",
			},
			address:{
				required:"客户地址不能为空",
			},
			idCard:{
				required:"客户身份证不能为空",
			},
			userId:{
				min:"请选择业务员"
			},
			baseId:{
				min:"请选择客户区间"
			}
		},
		submitHandler:function(form){
			//form ： 是原生js dom对象
			/* 
				原生js对象转jquery对象    $(dom对象)
				jq对象转原生对象  jq对象[0]
			*/
			//将表单转换成jq对象
			var jqForm = $(form);
			console.log(jqForm);
			
			//使用jquery表单异步提交
			jqForm.ajaxSubmit(function(data){
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