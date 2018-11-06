<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
<meta charset="UTF-8">
<title>课程管理</title>
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
<body>
<!-- 添加课程模态框 -->
	<div class="modal animated fadeInUp" id="courAdd">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4>添加课程</h4>
				</div>
				<form action="admin" class="form-sub" id="form-sub">
					<div class="modal-body">
						<!-- 使用隐藏域来区分业务逻辑 -->
						<input type="hidden" name="method" value="subCour"/>
						<div id="" class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">课程编号:</span>
							  <textarea rows="1" name="id" placeholder="请输入课程编号" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
						<div id="" class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">课程名称:</span>
							  <textarea rows="1" name="name" placeholder="请输入课程名称" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">课程人数:</span>
							  <textarea rows="1" name="number" placeholder="请输入课程人数" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">任课老师:</span>
							  <textarea rows="1" name="teaname" placeholder="请输入任课老师" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">课程学分:</span>
							  <textarea rows="1" name="point" placeholder="请输入课程学分" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal">取消</button>
						<button class="btn btn-primary" type="submit" form="form-sub">添加</button>
					</div>
				</form>
			</div>
		</div>
	</div>

<!-- 课程修改模态框 -->
	<div class="modal animated pulse" id="courseEdit">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<h4>课程修改</h4>
				</div>
				<form action="admin" class="update-form" id="update-form">
					<div class="modal-body">
						<!-- 使用隐藏域来区分业务逻辑 -->
						<input type="hidden" name="method" value="updateCourse"/>
<!-- 						//获取课程id -->
						<input type="hidden" name="cid">
						<div class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">课程人数:</span>
							  <textarea rows="1" name="number" placeholder="请输入课程人数" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">任课老师:</span>
							  <textarea rows="1" name="teaname" placeholder="请输入任课老师" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
						<div class="form-group">
							<div class="input-group">
							  <span class="input-group-addon">课程学分:</span>
							  <textarea rows="1" name="point" placeholder="请输入课程学分" required class="form-control" style="resize: none;"></textarea>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button class="btn btn-default" data-dismiss="modal">取消</button>
						<button class="btn btn-primary btnSubId" type="submit" form="update-form">确认修改</button>
					</div>
				</form>
			</div>
		</div>
	</div>
<!-- 	导航区 -->
	<nav class="navbar navbar-default">
		<div class="container-fluid">
		</div>
	</nav>	
<!-- 	内容区 -->
	<div class="container-fluid">
		
<!-- 		按钮组 -->
		<div class="btn-group">
			<button class="btn btn-primary"  data-toggle="modal" data-target="#courAdd"><span class="glyphicon glyphicon-cloud-upload"></span>添加选课</button>
			
			
		</div>
		
<!-- 		数据库区
			用于显示课程信息 -->
		<div class="data-container">
			<table class="table table-striped table-bordered">
				<tr>
					<th>课程编号</th>
					<th>课程名称</th>
					<th>剩余名额</th>
					<th>任课老师</th>
					<th>课程学分</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${courses }" var="course" varStatus="courses">
					<tr>
						<td>${course.id }</td>
						<td>${course.name }</td>
						<td>${course.number }</td>
						<td>${course.teaname }</td>
						<td>${course.point }</td>
						<td>
							<button class="btn btn-info btn-sm btnUpdate" name="id" data-id="${course.id }" data-toggle="modal"  data-target="#courseEdit"><span class="glyphicon glyphicon-edit"></span>课程修改</button>
							<button class="btn btn-danger btnDel" name="id" data-id="${course.id }"><span class="glyphicon glyphicon-remove"></span>删除课程</button>
						</td>
					</tr>
				</c:forEach>
				

			</table>
<!-- 			分页标签 -->
			<div class="text-right">
				<ul class="pagination">
					<c:choose>
						<c:when test="${cp eq 1 }">
							<li class="disabled"><a href="admin?method=findAll&cp=${cp-1 }"><span>&laquo;</span></a></li>
						</c:when>
						<c:otherwise>
							<li><a href="admin?method=findAll&cp=${cp-1 }"><span>&laquo;</span></a></li>
						</c:otherwise>
					</c:choose>
					
					<c:forEach begin="1" end="${tp }" var="page">
						<c:choose>
							<c:when test="${cp eq page }">
								<li class="active"><a href="">${page }</a></li>
							</c:when>
							<c:otherwise>
								<li class=""><a href="admin?method=findAll&cp=${page }">${page }</a></li>
							</c:otherwise>
						</c:choose>
					</c:forEach>
<!-- 					<li class="active"><a href="">1</a></li> -->
<!-- 					<li><a href="">2</a></li> -->
<!-- 					<li><a href="">3</a></li> -->
					<c:choose>
						<c:when test="${cp eq tp }">
							<li class="disabled"><a href="admin?method=findAll&cp=${cp+1 }"><span>&raquo;</span></a></li>
						</c:when>
						<c:otherwise>
							<li><a href="admin?method=findAll&cp=${cp+1 }"><span>&raquo;</span></a></li>
						</c:otherwise>
					</c:choose>
				</ul>
			</div>
			
		</div>
	</div>
	
<%-- 	${dailys[0].id }<br> --%>
<%-- 	${dailys[1].id }<br> --%>
<%-- 	${dailys[2].id } --%>
<!-- 	<hr> -->
<!-- 	<p>f8139c4f-5da6-4677-b9e6-8bdcfcfce1cb</p> -->
<!-- 	<p>8103e9e5-a89b-4ad5-847b-f69c96b2e3a4</p> -->
<!-- 	759a74ae-bd37-4a1c-aca4-ad16c16a0d79 -->

<script>
	$(function(){
		//有提示就弹提示窗
		if('${admin_msg}'){
			layer.msg('${admin_msg }');
		}
		
		
		//修改课程
		$('.btnUpdate').click(function(){
			var id = $(this).data('id');
			$('#update-form input[name=cid]').val(id);
			
		});
		
		
 		//删除课程
		$('.btnDel').click(function(){
			//获取data-daily-id中的值
			var id = $(this).data('id');
			console.info(id);
			layer.confirm('确认删除？', {
				  btn: ['确认','取消'], //按钮
				  icon:2
				}, function(){
					location.href='admin?method=delCourse&id='+id;
				});
		})
		
		
		
		//表单验证
		$('.form-sub,.update-form').bootstrapValidator({
			message:'输入的字段符合要求',
			feedbackIcons:{
				valid:'glyphicon glyphicon-ok',
				invalid:'glyphicon glyphicon-remove',
				validating:'glyphicon glyphicon-refresh',	
			},
			fields:{
				todaytask:{
					message:'输入有误',
					validators:{
						stringLength:{
							message:'字符长度最少20个字',
							min:20
						}
					}
				},
				completeinfo:{
					message:'输入有误',
					validators:{
						stringLength:{
							message:'字符长度最少20个字',
							min:20
						}
					}
				},
				nextplan:{
					message:'输入有误',
					validators:{
						stringLength:{
							message:'字符长度最少20个字',
							min:20
						}
					}
				}
				
				
			}
		})
		
		
// 		$('.btnInfo').click(function(){
// 			console.info(eval("("+$(this).data('daily')+")"));
// 		})
	})
</script>
</body>
</html>