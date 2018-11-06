<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
    <!DOCTYPE html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="UTF-8">
<title>学生</title>
	<link rel="shortcut icon" href="favicon.ico"/>
	<link rel="bookmark" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="easyui/css/default.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/default/easyui.css" />
    <link rel="stylesheet" type="text/css" href="easyui/themes/icon.css" />
    <script type="text/javascript" src="easyui/jquery.min.js"></script>
    <script type="text/javascript" src="easyui/jquery.easyui.min.js"></script>
	<script type="text/javascript" src='easyui/js/outlook2.js'> </script>
	<script type="text/javascript">
	 var _menus = {"menus":[
						{"menuid":"1","icon":"","menuname":"教学管理",
							"menus":[
							        {"menuid":"11","menuname":"学生选课","icon":"icon-book-open","url":"stucour?method=findAll"},
								]
						},
						{"menuid":"3","icon":"","menuname":"选课信息",
							"menus":[
									{"menuid":"31","menuname":"查看选课","icon":"icon-user","url":"stu?method=selectCourse"},
								]
						},
						{"menuid":"2","icon":"","menuname":"系统管理",
							"menus":[
									{"menuid":"21","menuname":"个人信息","icon":"icon-password","url":"person.jsp"},
								]
						}
				]};


    </script>
	
</head>
<body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
	<noscript>
		<div style=" position:absolute; z-index:100000; height:2046px;top:0px;left:0px; width:100%; background:white; text-align:center;">
		    <img src="images/noscript.gif" alt='抱歉，请开启脚本支持！' />
		</div>
	</noscript>
    <div region="north" split="true" border="false" style="overflow: hidden; height: 30px;
        background: url(images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 20px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        <span style="float:right; padding-right:20px;" class="head"><span style="color:pink;">${student.name}</span>，您好&nbsp;&nbsp;&nbsp;<a href="stu?method=loginOut">安全退出</a></span>
        <span style="padding-left:10px; font-size: 16px; ">学生选课系统</span>
    </div>
    <div region="south" split="true" style="height: 30px; background: #D2E0F2; ">
        <div class="footer">Copyright &copy; 吴旭宇</div>
    </div>
    <div region="west" hide="true" split="true" title="导航菜单" style="width:180px;" id="west">
	<div id="nav" class="easyui-accordion" fit="true" border="false">
		<!--  导航内容 -->
	</div>
	
    </div>
    <div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
        <div id="tabs" class="easyui-tabs"  fit="true" border="false" >
        <jsp:include page="/welcome.jsp" />
		</div>
    </div>

	<iframe width=0 height=0 src="refresh.jsp"></iframe>
	
</body>
</html>