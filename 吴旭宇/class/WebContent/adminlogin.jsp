<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
	<head>
		<meta charset="utf-8" />
		<link rel="stylesheet" href="bootstrap/css/bootstrap.min.css" />
		<link rel="stylesheet" href="css/animate.css">
		<script type="text/javascript" src="js/jquery-3.3.1.min.js" ></script>
		<script type="text/javascript" src="bootstrap/js/bootstrap.min.js" ></script>
		<script type="text/javascript" src="bootstrapvalidator/js/bootstrapValidator.min.js" ></script>
		<script type="text/javascript" src="bootstrapvalidator/js/language/zh_CN.js" ></script>
		<script type="text/javascript" src="layer/layer.js"></script>
		<title>管理员登录</title>
		<style>
		*{
				margin: 0;
				padding: 0;
			}
			body{
			background-image: url("img/599a346aa5506.jpg");
			background-size: 100%;
			}
			.btn-reg{
				width: 50%;
				margin-left: 50%;
			}
		</style>
	</head>
	<body>
		<div class="container animated bounceInLeft">
			<div class="page-header">
				<h3>管理员登录</h3>
			</div>
			<div class="col-sm-5">
				<form action="admin" method="post" class="form-reg">
					<!-- 使用隐藏域来区分该表单是登录还是注册 -->
					<input type="hidden" name="method" value="login">
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">
								<span class="glyphicon glyphicon-user"></span>
							</div>
							<input type="text" name="id" value="${id }" placeholder="输入用户名" class="form-control" required/>
						</div>
					</div>
					<div class="form-group">
						<div class="input-group">
							<div class="input-group-addon">
								<span class="glyphicon glyphicon-lock"></span>
							</div>
							<input type="password" name="psw" placeholder="输入密码" class="form-control" required/>
						</div>
					</div>
					<div class="form-group">
						<button class="btn btn-primary btn-reg" type="submit">登录</button><br>
					</div>
				</form>
			</div>
		</div>
		
		
		<script>
			$(function(){
				$('.form-reg').bootstrapValidator({
					message:'输入的字段符合要求',
					feedbackIcons:{
						valid:'glyphicon glyphicon-ok',
						invalid:'glyphicon glyphicon-remove',
						validting:'glyphicon glyphicon-refresh',
					}
				})
				
				if('${msg }'){
					layer.alert('${msg }',{
						icon:2
					});
					
				}
				
			})
		</script>
	</body>
</html>
