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
<title>Insert title here</title>
<link rel="stylesheet" href="lib/zTree/v3/css/zTreeStyle/zTreeStyle.css">

</head>
<body>
<button onclick="getCheckList()">该接口好看</button>
<ul id="permissionTree" class="ztree"></ul>

<script type="text/javascript" src="lib/jquery/1.11.3/jquery.min.js"></script>
<script src="lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"> </script>
<script>
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

	$(document).ready(function(){
		$.fn.zTree.init($("#permissionTree"), setting);
	});
function getCheckList(){
// 	得到树对象
	var treeObj = $.fn.zTree.getZTreeObj("permissionTree");
// 	得到选中的节点
// 遍历重新组合得到只有id的数组
	var nodes = treeObj.getCheckedNodes(true);
	var checkList=[];
	nodes.forEach(function(element, index) {
		checkList.push(element.permissionId);
	})
// 	把这个数组弄成字符串再发到后台中
	var checkStr=checkList.join(",");
// 	dom操作，设置值进隐藏域中
	$("#permissionIds").val(checkStr) 
}

</script>
</body>
</html>