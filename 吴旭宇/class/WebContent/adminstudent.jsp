<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="UTF-8">
<title>学生管理</title>
<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
<link rel="stylesheet" href="css/animate.css" />
<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
<script type="text/javascript" src="bootstrap/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="bootstrapvalidator/js/bootstrapValidator.min.js" ></script>
<script type="text/javascript" src="bootstrapvalidator/js/language/zh_CN.js" ></script>
<script type="text/javascript" src="layer/layer.js" ></script>
<style>
	.navbar{
		border-radius:0;
	}
	.table td{
		/*vertical-align: middle;*/
		white-space:nowrap;/*不自动换行*/
		overflow: hidden;/*超出的部分隐藏*/
		text-overflow: ellipsis;/*超出的文本用省略号*/
		max-width: 200px;
	}
	.table td:last-child{
		text-align:center;
	}
	.btn-group{
		margin-bottom:10px;
	}
	
</style>
</head>
<!-- 该页面为学生选课页面，存放所有课程信息，还有学生选课的按钮-->
<body>

<!-- 	导航区 -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
<!-- 			<div class="navbar-header"> -->
<!-- 				<a href="" class="navbar-brand">学生选课系统</a> -->
<!-- 			</div> -->
<!-- 			<div class="navbar-collapse collapse" id="userInfo"> -->
<!-- 			 <ul class="nav navbar-nav navbar-right"> -->
<!-- 		        <li><a href="#"><span class="glyphicon glyphicon-user"></span></a></li> -->
<!-- 		        <li class="dropdown"> -->
<%-- 		          <a href="#" class="dropdown-toggle" data-toggle="dropdown">欢迎你，${student.name }<span class="caret"></span></a> --%>
<!-- 		          <ul class="dropdown-menu"> -->
<!-- 		            <li><a href="student.jsp">查看选课</a></li> -->
<!-- 		            <li><a href="#">安全退出</a></li> -->
<!-- 		          </ul> -->
<!-- 		        </li> -->
<!-- 		      </ul> -->
<!-- 			</div> -->
		</div>
	</nav>	
<!-- 	内容区 -->
	<div class="container-fluid">
		
<!-- 		数据库区
			用于显示课程信息 -->
		<div class="data-container">
			<table class="table table-striped table-bordered">
				<tr>
					<th>学号</th>
					<th>学生姓名</th>
					<th>学生性别</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${students }" var="student" varStatus="students">
					<tr>
						<td>${student.id }</td>
						<td>${student.name }</td>
						<td>${student.sex }</td>
						<td>
							<button class="btn btn-primary btnDel" name="id" data-id="${student.id }">注销学生</button>
						</td>
					</tr>
				</c:forEach>
				

			</table>
<!-- 			分页标签 -->
			<div class="text-right">
				<ul class="pagination">
					<c:choose>
						<c:when test="${cp eq 1 }">
							<li class="disabled"><a href="admin?method=findStudent&cp=${cp-1 }"><span>&laquo;</span></a></li>
						</c:when>
						<c:otherwise>
							<li><a href="admin?method=findStudent&cp=${cp-1 }"><span>&laquo;</span></a></li>
						</c:otherwise>
					</c:choose>
					
					<c:forEach begin="1" end="${tp }" var="page">
						<c:choose>
							<c:when test="${cp eq page }">
								<li class="active"><a href="">${page }</a></li>
							</c:when>
							<c:otherwise>
								<li class=""><a href="admin?method=findStudent&cp=${page }">${page }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
<!-- 					<li class="active"><a href="">1</a></li> -->
<!-- 					<li><a href="">2</a></li> -->
<!-- 					<li><a href="">3</a></li> -->
					<c:choose>
						<c:when test="${cp eq tp }">
							<li class="disabled"><a href="admin?method=findStudent&cp=${cp+1 }"><span>&raquo;</span></a></li>
						</c:when>
						<c:otherwise>
							<li><a href="admin?method=findStudent&cp=${cp+1 }"><span>&raquo;</span></a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			
		</div>
	</div>
	

<script>
	$(function(){
		//有提示就弹提示窗
		if('${stucour_msg}'){
			layer.msg('${stucour_msg }');
		}
		
		//获取学生id
		$('.btnDel').click(function(){
			//获取data-daily-id中的值
			var id = $(this).data('id');
			layer.confirm('确认注销？', {
				  btn: ['确认','取消'], //按钮
				  icon:1
				}, function(){
				  location.href='admin?method=delStudent&id='+id;
				});
		})
		
	})
	
	
	
	
</script>
</body>
</html>