package com.softeem.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softeem.dao.AdminDAO;
import com.softeem.dao.StuCourDAO;
import com.softeem.dto.Admin;
import com.softeem.dto.Course;
import com.softeem.dto.ResultModel;
import com.softeem.dto.Student;
import com.softeem.service.AdminService;
import com.softeem.service.StuCourService;
import com.softeem.service.StudentService;
import com.softeem.util.Tools;

@WebServlet("/admin")
public class AdminServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
		
	ResultModel result;

	public AdminServlet() {
		super();
	}
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置请求头
				request.setCharacterEncoding("utf-8");
				//设置响应头
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
				
				String method = request.getParameter("method");
				switch (method) {
				
				case "login" :
					//登录
					login(request,response);
					break;
				case "loginOut" :
					//安全退出
					loginOut(request,response);
					break;
				case "subCour" :
					//添加课程
					subCour(request,response);
					break;
				case "findAll"://查询所有已发布课程并显示
					findAll(request,response);
					break;
				case "delCourse" :
					//批量删除课程
					delCourse(request,response);
					break;	
				case "updateCourse" :
					//修改课程
					updateCourse(request,response);
					break;
				case "findStudent" :
					//查看学生信息
					findStudent(request,response);
					break;
				case "delStudent" :
					//删除学生信息
					delStudent(request,response);
					break;
				default:
					break;
				}
	}
		
	
	//查看学生信息
	private void findStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute("admin");
		if(obj == null){
			//重定向到登录页面
			response.sendRedirect("adminlogin.jsp");
			return;
		}
		//默认为第一页，每页5条
		int pageSize = 2;
		int currentPage = 1;
		int totalNum = 0;//总记录数
		int totalPage = 0;//总页数
		//分页
		String cp = request.getParameter("cp");
		if(cp != null){
			currentPage=Integer.parseInt(cp);
		}
		
		//防止无限上一页
		if(currentPage<1){
			currentPage = 1;
		}
		
		//获取总的条数
		AdminDAO adminDao = new AdminDAO(null);
		totalNum = adminDao.findStuNum();
		//总页数
		if(totalNum%pageSize==0){
			totalPage = totalNum/pageSize;
		}else{
			totalPage = totalNum/pageSize+1;
		}
		//防止无限下一页
		if(currentPage>totalPage){
			currentPage = totalPage;
		}
		//调用业务逻辑层处理
		AdminService dsi = new AdminService();
		ResultModel result = dsi.findStu(pageSize, currentPage);
		System.out.println(result.getData());
//		//将结果存入session
		request.getSession().setAttribute("students", result.getData());
//		//将当前页存入session
		request.getSession().setAttribute("cp", currentPage);
		//将总页数存入session
		request.getSession().setAttribute("tp", totalPage);
		//重定向到管理员管理课程页面
		response.sendRedirect("adminstudent.jsp");
}
		



		//查询所有已发布课程并显示
		private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
				//从session中获取学生对象
				Object obj = request.getSession().getAttribute("admin");
				if(obj == null){
					//重定向到登录页面
					response.sendRedirect("adminlogin.jsp");
					return;
				}
				//默认为第一页，每页5条
				int pageSize = 2;
				int currentPage = 1;
				int totalNum = 0;//总记录数
				int totalPage = 0;//总页数
				//分页
				String cp = request.getParameter("cp");
				if(cp != null){
					currentPage=Integer.parseInt(cp);
				}
				
				//防止无限上一页
				if(currentPage<1){
					currentPage = 1;
				}
				
				//获取总的条数
				AdminDAO adminDao = new AdminDAO(null);
				totalNum = adminDao.findCount();
				//总页数
				if(totalNum%pageSize==0){
					totalPage = totalNum/pageSize;
				}else{
					totalPage = totalNum/pageSize+1;
				}
				//防止无限下一页
				if(currentPage>totalPage){
					currentPage = totalPage;
				}
				//调用业务逻辑层处理
				AdminService dsi = new AdminService();
				ResultModel result = dsi.findAll(pageSize, currentPage);
				System.out.println(result.getData());
//				//将结果存入session
				request.getSession().setAttribute("courses", result.getData());
//				//将当前页存入session
				request.getSession().setAttribute("cp", currentPage);
				//将总页数存入session
				request.getSession().setAttribute("tp", totalPage);
				//重定向到管理员管理课程页面
				response.sendRedirect("adminupdate.jsp");
		}
	
		//删除学生
		private void delStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String id = request.getParameter("id");
			int sid = Integer.parseInt(id);
			System.out.println(sid);
			AdminService dsi = new AdminService();
			ResultModel result = dsi.delStu(sid);
			request.getSession().setAttribute("admin_msg", result.getMsg());
			findStudent(request, response);
		}	
	
	//删除课程
			private void delCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
				String ids = request.getParameter("id");
				System.out.println("ids"+ids);
				int cid = Integer.parseInt(ids);
				System.out.println("cid"+cid);
//				System.out.println(Arrays.toString(id));
				AdminService dsi = new AdminService();
				ResultModel result = dsi.delAll(cid);
				request.getSession().setAttribute("admin_msg", result.getMsg());
				findAll(request, response);
			}
		
		//修改课程
			private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
				//获取数据
				String id = request.getParameter("cid");
				int cid = Integer.parseInt(id);
				System.out.println(cid);
				String numbers = request.getParameter("number").trim();
				int number = Integer.parseInt(numbers);
				String teaname = request.getParameter("teaname").trim();
				String points = request.getParameter("point").trim();
				int point = Integer.parseInt(points);
				Course course = new Course();
				course.setId(cid);
				course.setNumber(number);
				course.setTeaname(teaname);
				course.setPoint(point);
				ResultModel result = new AdminService().updateCourse(course);
				request.getSession().setAttribute("admin_msg", result.getMsg());
				findAll(request, response);
				
			}
	
		//添加选课
		private void subCour(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			String ids = request.getParameter("id").trim();
			int id = Integer.parseInt(ids);
			String name = request.getParameter("name").trim();
			String numbers = request.getParameter("number").trim();
			int number = Integer.parseInt(numbers);
			String teaname = request.getParameter("teaname").trim();
			String points = request.getParameter("point").trim();
			int point = Integer.parseInt(points);
			Course course = new Course();
			course.setId(id);
			course.setName(name);
			course.setNumber(number);
			course.setTeaname(teaname);
			course.setPoint(point);
			ResultModel result = new ResultModel();
			AdminService as = new AdminService();
			result = as.register(course);
			request.getSession().setAttribute("admin_msg", result.getMsg());
			findAll(request, response);
	}


		//管理员登录
		private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			//获取表单数据
			String sid = request.getParameter("id").trim();
			int id = Integer.valueOf(sid);
			String password = request.getParameter("psw").trim();
			Admin admin = new Admin();
			admin.setId(id);
			admin.setPassword(password);
			System.out.println(admin.getId());
			System.out.println(admin.getPassword());
			//调用service处理
			AdminService ssi = new AdminService();
			result = ssi.login(admin);
			if(result.getCode() == 0){
				//成功登录
				//将成功登录的管理员的信息存入到session
				request.getSession().setAttribute("admin", result.getData());
				response.sendRedirect("admin.jsp");
			}else{
				//登录失败
				request.setAttribute("msg", result.getMsg());
				//请求转发到登录页面
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
		
		//安全退出
		private void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//退出系统时清除系统登录的用户
			request.getSession().removeAttribute("admin");
			String contextPath = request.getContextPath();
			//转发到登录界面
			response.sendRedirect("login.jsp");
		}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
