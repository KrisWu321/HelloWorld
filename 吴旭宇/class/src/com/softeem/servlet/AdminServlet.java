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
		//��������ͷ
				request.setCharacterEncoding("utf-8");
				//������Ӧͷ
				response.setCharacterEncoding("utf-8");
				response.setContentType("text/html;charset=utf-8");
				
				String method = request.getParameter("method");
				switch (method) {
				
				case "login" :
					//��¼
					login(request,response);
					break;
				case "loginOut" :
					//��ȫ�˳�
					loginOut(request,response);
					break;
				case "subCour" :
					//��ӿγ�
					subCour(request,response);
					break;
				case "findAll"://��ѯ�����ѷ����γ̲���ʾ
					findAll(request,response);
					break;
				case "delCourse" :
					//����ɾ���γ�
					delCourse(request,response);
					break;	
				case "updateCourse" :
					//�޸Ŀγ�
					updateCourse(request,response);
					break;
				case "findStudent" :
					//�鿴ѧ����Ϣ
					findStudent(request,response);
					break;
				case "delStudent" :
					//ɾ��ѧ����Ϣ
					delStudent(request,response);
					break;
				default:
					break;
				}
	}
		
	
	//�鿴ѧ����Ϣ
	private void findStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute("admin");
		if(obj == null){
			//�ض��򵽵�¼ҳ��
			response.sendRedirect("adminlogin.jsp");
			return;
		}
		//Ĭ��Ϊ��һҳ��ÿҳ5��
		int pageSize = 2;
		int currentPage = 1;
		int totalNum = 0;//�ܼ�¼��
		int totalPage = 0;//��ҳ��
		//��ҳ
		String cp = request.getParameter("cp");
		if(cp != null){
			currentPage=Integer.parseInt(cp);
		}
		
		//��ֹ������һҳ
		if(currentPage<1){
			currentPage = 1;
		}
		
		//��ȡ�ܵ�����
		AdminDAO adminDao = new AdminDAO(null);
		totalNum = adminDao.findStuNum();
		//��ҳ��
		if(totalNum%pageSize==0){
			totalPage = totalNum/pageSize;
		}else{
			totalPage = totalNum/pageSize+1;
		}
		//��ֹ������һҳ
		if(currentPage>totalPage){
			currentPage = totalPage;
		}
		//����ҵ���߼��㴦��
		AdminService dsi = new AdminService();
		ResultModel result = dsi.findStu(pageSize, currentPage);
		System.out.println(result.getData());
//		//���������session
		request.getSession().setAttribute("students", result.getData());
//		//����ǰҳ����session
		request.getSession().setAttribute("cp", currentPage);
		//����ҳ������session
		request.getSession().setAttribute("tp", totalPage);
		//�ض��򵽹���Ա����γ�ҳ��
		response.sendRedirect("adminstudent.jsp");
}
		



		//��ѯ�����ѷ����γ̲���ʾ
		private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
				//��session�л�ȡѧ������
				Object obj = request.getSession().getAttribute("admin");
				if(obj == null){
					//�ض��򵽵�¼ҳ��
					response.sendRedirect("adminlogin.jsp");
					return;
				}
				//Ĭ��Ϊ��һҳ��ÿҳ5��
				int pageSize = 2;
				int currentPage = 1;
				int totalNum = 0;//�ܼ�¼��
				int totalPage = 0;//��ҳ��
				//��ҳ
				String cp = request.getParameter("cp");
				if(cp != null){
					currentPage=Integer.parseInt(cp);
				}
				
				//��ֹ������һҳ
				if(currentPage<1){
					currentPage = 1;
				}
				
				//��ȡ�ܵ�����
				AdminDAO adminDao = new AdminDAO(null);
				totalNum = adminDao.findCount();
				//��ҳ��
				if(totalNum%pageSize==0){
					totalPage = totalNum/pageSize;
				}else{
					totalPage = totalNum/pageSize+1;
				}
				//��ֹ������һҳ
				if(currentPage>totalPage){
					currentPage = totalPage;
				}
				//����ҵ���߼��㴦��
				AdminService dsi = new AdminService();
				ResultModel result = dsi.findAll(pageSize, currentPage);
				System.out.println(result.getData());
//				//���������session
				request.getSession().setAttribute("courses", result.getData());
//				//����ǰҳ����session
				request.getSession().setAttribute("cp", currentPage);
				//����ҳ������session
				request.getSession().setAttribute("tp", totalPage);
				//�ض��򵽹���Ա����γ�ҳ��
				response.sendRedirect("adminupdate.jsp");
		}
	
		//ɾ��ѧ��
		private void delStudent(HttpServletRequest request, HttpServletResponse response) throws IOException {
			String id = request.getParameter("id");
			int sid = Integer.parseInt(id);
			System.out.println(sid);
			AdminService dsi = new AdminService();
			ResultModel result = dsi.delStu(sid);
			request.getSession().setAttribute("admin_msg", result.getMsg());
			findStudent(request, response);
		}	
	
	//ɾ���γ�
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
		
		//�޸Ŀγ�
			private void updateCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
				//��ȡ����
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
	
		//���ѡ��
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


		//����Ա��¼
		private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			//��ȡ������
			String sid = request.getParameter("id").trim();
			int id = Integer.valueOf(sid);
			String password = request.getParameter("psw").trim();
			Admin admin = new Admin();
			admin.setId(id);
			admin.setPassword(password);
			System.out.println(admin.getId());
			System.out.println(admin.getPassword());
			//����service����
			AdminService ssi = new AdminService();
			result = ssi.login(admin);
			if(result.getCode() == 0){
				//�ɹ���¼
				//���ɹ���¼�Ĺ���Ա����Ϣ���뵽session
				request.getSession().setAttribute("admin", result.getData());
				response.sendRedirect("admin.jsp");
			}else{
				//��¼ʧ��
				request.setAttribute("msg", result.getMsg());
				//����ת������¼ҳ��
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}
		
		//��ȫ�˳�
		private void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//�˳�ϵͳʱ���ϵͳ��¼���û�
			request.getSession().removeAttribute("admin");
			String contextPath = request.getContextPath();
			//ת������¼����
			response.sendRedirect("login.jsp");
		}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
