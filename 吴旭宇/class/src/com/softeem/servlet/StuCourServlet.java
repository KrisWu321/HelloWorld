package com.softeem.servlet;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.softeem.dao.StuCourDAO;
import com.softeem.dto.ResultModel;
import com.softeem.dto.StuCour;
import com.softeem.dto.Student;
import com.softeem.service.StuCourService;



@WebServlet("/stucour")
public class StuCourServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
    public StuCourServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置请求头编码
				request.setCharacterEncoding("utf-8");
				//获取请求的方法
				String method = request.getParameter("method");
				//根据不同的请求方法做不同的处理
				switch (method) {
				case "findAll"://查询所有已发布课程并显示
					findAll(request,response);
					break;
				case "subCours"://提交选课
					subCours(request,response);
					break;
//				case "delAllCours"://提交选课
//					delAllCours(request,response);
//					break;
				default:
					break;
	}

}

	//先判断课程人数是否已满提交选课
	private void subCours(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取session中的sid
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
				System.out.println("cid是"+cid);
				StuCourService dsi = new StuCourService();
				//将多选框获取到的课程id，和对应学生的id传入
				result = dsi.number(sid,cid);
				request.getSession().setAttribute("stucour_msg", result.getMsg());
				findAll(request, response);
	}
	
//	//批量删除
//		private void delAllCours(HttpServletRequest request, HttpServletResponse response) throws IOException {
//			String ids = request.getParameter("ids");
//			String[] id = ids.split(",");
////			System.out.println(Arrays.toString(id));
//			StuCourService dsi = new StuCourService();
//			ResultModel result = dsi.delAll(id);
//			request.getSession().setAttribute("stucour_msg", result.getMsg());		
//		}
	//查询所有已发布课程并显示
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
			//从session中获取学生对象
			Object obj = request.getSession().getAttribute("student");
			if(obj == null){
				//重定向到登录页面
				response.sendRedirect("login.jsp");
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
			StuCourDAO stucourDao = new StuCourDAO(null);
			totalNum = stucourDao.findCount();
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
			StuCourService dsi = new StuCourService();
			ResultModel result = dsi.findAll(pageSize, currentPage);
//			System.out.println(result.getData());
//			//将结果存入session
			request.getSession().setAttribute("courses", result.getData());
//			//将当前页存入session
			request.getSession().setAttribute("cp", currentPage);
			//将总页数存入session
			request.getSession().setAttribute("tp", totalPage);
			//重定向到选课页面
			response.sendRedirect("stucour.jsp");
	}
}