package com.softeem.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.softeem.dao.StuCourDAO;
import com.softeem.dao.StudentDAO;
import com.softeem.dto.ResultModel;
import com.softeem.dto.Student;
import com.softeem.service.StuCourService;
import com.softeem.service.StudentService;
import com.softeem.util.Tools;




@WebServlet("/stu")
public class StudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    //创建返回结果集对象
	ResultModel result;
	
    public StudentServlet() {
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
				case "reg" :
					//注册
					regester(request,response);
					break;
				case "login" :
					//登录
					login(request,response);
					break;
				case "loginOut" :
					//安全退出
					loginOut(request,response);
					break;
				case "updatemsg" :
					//修改个人信息
					updatemsg(request,response);
					break;
				case "selectCourse" :
					//查看选课信息
					selectCourse(request,response);
					break;
				case "delCourse" :
					//删除选课信息
					delCourse(request,response);
					break;
				default:
					break;
				}
	}
	
	//删除选课
	
	private void delCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute("student");
		if(obj == null){
			response.sendRedirect("login.jsp");
			return;
		}
		//获取表单数据
		Student stu = (Student)obj;
		int sid = stu.getId();
		ResultModel result = new ResultModel();
		String id = request.getParameter("id");
		int cid = Integer.parseInt(id);
		StudentService ssi = new StudentService();
		result = ssi.delCour(sid,cid);
		request.getSession().setAttribute("student_msg", result.getMsg());
		selectCourse(request, response);
	}


	//查看已选选课
	private void selectCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//查询所有已发布课程并显示
				//从session中获取学生对象
				Object obj = request.getSession().getAttribute("student");
				if(obj == null){
					//重定向到登录页面
					response.sendRedirect("login.jsp");
					return;
				}
				//从session中获取学生对象
				Student stu = (Student)obj;
				//获取学生id
				int sid = stu.getId();
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
				StudentDAO studentDao = new StudentDAO();
				totalNum = studentDao.findCount(sid);
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
				StudentService dsi = new StudentService();
				ResultModel result = dsi.findAll(sid, pageSize, currentPage);
//				System.out.println(result.getData());
//				//将结果存入session
				request.getSession().setAttribute("courses", result.getData());
//				//将当前页存入session
				request.getSession().setAttribute("cp", currentPage);
				//将总页数存入session
				request.getSession().setAttribute("tp", totalPage);
				//重定向到选课页面
				response.sendRedirect("studentcourse.jsp");
		}


	//修改个人信息
	private void updatemsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute("student");
		Student s = (Student)obj;
		int sid = s.getId();
		// 获取表单数据
		String name = request.getParameter("name").trim();
		String sex = request.getParameter("sex");
		String psw = request.getParameter("psw").trim();
		String password = Tools.getMD5(psw);
		Student stu = new Student();
		stu.setId(sid);
		stu.setName(name);
		stu.setSex(sex);
		stu.setPassword(password);
		System.out.println(stu.getName());
		StudentService ssi = new StudentService();
		result = ssi.update(stu);
		if(result.getCode() == 0){
			//将成功修改后的学员信息存入到session
			request.getSession().setAttribute("student", result.getData());
			response.sendRedirect("welcome.jsp");
		}
	}


		//学生登录
		private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			//获取表单数据
			String sid = request.getParameter("id").trim();
			int id = Integer.valueOf(sid);
			String psw = request.getParameter("psw").trim();
			String password = Tools.getMD5(psw);
			Student stu = new Student();
			stu.setId(id);
			stu.setPassword(password);
			//调用service处理
			StudentService ssi = new StudentService();
			result = ssi.login(stu);
			if(result.getCode() == 0){
				//成功登录
				System.out.println(((Student)result.getData()).getName());
				//将成功登录的学员的信息存入到session
				request.getSession().setAttribute("student", result.getData());
				response.sendRedirect("student.jsp");
			}else{
				//登录失败
				request.setAttribute("msg", result.getMsg());
				//请求转发到登录页面
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}


		//学生注册
		//先执行的servlet里的注册和登录方法获取到前端表单数据并将获取到的表单数据作为参数给到创建的Student对象中
		//然后调用service里的注册和登录方法，新创建的Student对象作为参数
		private void regester(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			
			//获取表单数据
			String sid = request.getParameter("id").trim();
			int id = Integer.valueOf(sid);
			String name = request.getParameter("name").trim();
			String sex = request.getParameter("sex");
			String psw = request.getParameter("psw").trim();
			
			//创建数据传输对象
			int dr = 1;
			String password = Tools.getMD5(psw);
			Student student = new Student(id, name, sex, password, dr);
			//调用service处理逻辑
			StudentService ssi = new StudentService();
			result = ssi.register(student);
			
			if(result.getCode() == 0){
				//注册成功重定向跳转到登录页面
				request.getSession().setAttribute("id", id);
				response.sendRedirect("login.jsp");
			}else{
				//注册失败
				request.setAttribute("msg", result.getMsg());
				request.getRequestDispatcher("reg.jsp").forward(request, response);
			}
			
		}
		
		//安全退出
		private void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//退出系统时清除系统登录的用户
			request.getSession().removeAttribute("student");
			String contextPath = request.getContextPath();
			//转发到登录界面
			response.sendRedirect("login.jsp");
		}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
