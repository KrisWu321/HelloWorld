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
		//��������ͷ����
				request.setCharacterEncoding("utf-8");
				//��ȡ����ķ���
				String method = request.getParameter("method");
				//���ݲ�ͬ�����󷽷�����ͬ�Ĵ���
				switch (method) {
				case "findAll"://��ѯ�����ѷ����γ̲���ʾ
					findAll(request,response);
					break;
				case "subCours"://�ύѡ��
					subCours(request,response);
					break;
//				case "delAllCours"://�ύѡ��
//					delAllCours(request,response);
//					break;
				default:
					break;
	}

}

	//���жϿγ������Ƿ������ύѡ��
	private void subCours(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//��ȡsession�е�sid
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
				System.out.println("cid��"+cid);
				StuCourService dsi = new StuCourService();
				//����ѡ���ȡ���Ŀγ�id���Ͷ�Ӧѧ����id����
				result = dsi.number(sid,cid);
				request.getSession().setAttribute("stucour_msg", result.getMsg());
				findAll(request, response);
	}
	
//	//����ɾ��
//		private void delAllCours(HttpServletRequest request, HttpServletResponse response) throws IOException {
//			String ids = request.getParameter("ids");
//			String[] id = ids.split(",");
////			System.out.println(Arrays.toString(id));
//			StuCourService dsi = new StuCourService();
//			ResultModel result = dsi.delAll(id);
//			request.getSession().setAttribute("stucour_msg", result.getMsg());		
//		}
	//��ѯ�����ѷ����γ̲���ʾ
	private void findAll(HttpServletRequest request, HttpServletResponse response) throws IOException {
			//��session�л�ȡѧ������
			Object obj = request.getSession().getAttribute("student");
			if(obj == null){
				//�ض��򵽵�¼ҳ��
				response.sendRedirect("login.jsp");
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
			StuCourDAO stucourDao = new StuCourDAO(null);
			totalNum = stucourDao.findCount();
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
			StuCourService dsi = new StuCourService();
			ResultModel result = dsi.findAll(pageSize, currentPage);
//			System.out.println(result.getData());
//			//���������session
			request.getSession().setAttribute("courses", result.getData());
//			//����ǰҳ����session
			request.getSession().setAttribute("cp", currentPage);
			//����ҳ������session
			request.getSession().setAttribute("tp", totalPage);
			//�ض���ѡ��ҳ��
			response.sendRedirect("stucour.jsp");
	}
}