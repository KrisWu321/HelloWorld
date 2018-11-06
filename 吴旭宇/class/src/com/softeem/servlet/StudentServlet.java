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
    //�������ؽ��������
	ResultModel result;
	
    public StudentServlet() {
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
				case "reg" :
					//ע��
					regester(request,response);
					break;
				case "login" :
					//��¼
					login(request,response);
					break;
				case "loginOut" :
					//��ȫ�˳�
					loginOut(request,response);
					break;
				case "updatemsg" :
					//�޸ĸ�����Ϣ
					updatemsg(request,response);
					break;
				case "selectCourse" :
					//�鿴ѡ����Ϣ
					selectCourse(request,response);
					break;
				case "delCourse" :
					//ɾ��ѡ����Ϣ
					delCourse(request,response);
					break;
				default:
					break;
				}
	}
	
	//ɾ��ѡ��
	
	private void delCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute("student");
		if(obj == null){
			response.sendRedirect("login.jsp");
			return;
		}
		//��ȡ������
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


	//�鿴��ѡѡ��
	private void selectCourse(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//��ѯ�����ѷ����γ̲���ʾ
				//��session�л�ȡѧ������
				Object obj = request.getSession().getAttribute("student");
				if(obj == null){
					//�ض��򵽵�¼ҳ��
					response.sendRedirect("login.jsp");
					return;
				}
				//��session�л�ȡѧ������
				Student stu = (Student)obj;
				//��ȡѧ��id
				int sid = stu.getId();
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
				StudentDAO studentDao = new StudentDAO();
				totalNum = studentDao.findCount(sid);
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
				StudentService dsi = new StudentService();
				ResultModel result = dsi.findAll(sid, pageSize, currentPage);
//				System.out.println(result.getData());
//				//���������session
				request.getSession().setAttribute("courses", result.getData());
//				//����ǰҳ����session
				request.getSession().setAttribute("cp", currentPage);
				//����ҳ������session
				request.getSession().setAttribute("tp", totalPage);
				//�ض���ѡ��ҳ��
				response.sendRedirect("studentcourse.jsp");
		}


	//�޸ĸ�����Ϣ
	private void updatemsg(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Object obj = request.getSession().getAttribute("student");
		Student s = (Student)obj;
		int sid = s.getId();
		// ��ȡ������
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
			//���ɹ��޸ĺ��ѧԱ��Ϣ���뵽session
			request.getSession().setAttribute("student", result.getData());
			response.sendRedirect("welcome.jsp");
		}
	}


		//ѧ����¼
		private void login(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			//��ȡ������
			String sid = request.getParameter("id").trim();
			int id = Integer.valueOf(sid);
			String psw = request.getParameter("psw").trim();
			String password = Tools.getMD5(psw);
			Student stu = new Student();
			stu.setId(id);
			stu.setPassword(password);
			//����service����
			StudentService ssi = new StudentService();
			result = ssi.login(stu);
			if(result.getCode() == 0){
				//�ɹ���¼
				System.out.println(((Student)result.getData()).getName());
				//���ɹ���¼��ѧԱ����Ϣ���뵽session
				request.getSession().setAttribute("student", result.getData());
				response.sendRedirect("student.jsp");
			}else{
				//��¼ʧ��
				request.setAttribute("msg", result.getMsg());
				//����ת������¼ҳ��
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}


		//ѧ��ע��
		//��ִ�е�servlet���ע��͵�¼������ȡ��ǰ�˱����ݲ�����ȡ���ı�������Ϊ��������������Student������
		//Ȼ�����service���ע��͵�¼�������´�����Student������Ϊ����
		private void regester(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			
			//��ȡ������
			String sid = request.getParameter("id").trim();
			int id = Integer.valueOf(sid);
			String name = request.getParameter("name").trim();
			String sex = request.getParameter("sex");
			String psw = request.getParameter("psw").trim();
			
			//�������ݴ������
			int dr = 1;
			String password = Tools.getMD5(psw);
			Student student = new Student(id, name, sex, password, dr);
			//����service�����߼�
			StudentService ssi = new StudentService();
			result = ssi.register(student);
			
			if(result.getCode() == 0){
				//ע��ɹ��ض�����ת����¼ҳ��
				request.getSession().setAttribute("id", id);
				response.sendRedirect("login.jsp");
			}else{
				//ע��ʧ��
				request.setAttribute("msg", result.getMsg());
				request.getRequestDispatcher("reg.jsp").forward(request, response);
			}
			
		}
		
		//��ȫ�˳�
		private void loginOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//�˳�ϵͳʱ���ϵͳ��¼���û�
			request.getSession().removeAttribute("student");
			String contextPath = request.getContextPath();
			//ת������¼����
			response.sendRedirect("login.jsp");
		}
		

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
