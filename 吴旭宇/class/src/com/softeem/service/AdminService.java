package com.softeem.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.softeem.dao.AdminDAO;
import com.softeem.dao.StuCourDAO;
import com.softeem.dto.Admin;
import com.softeem.dto.Course;
import com.softeem.dto.ResultModel;
import com.softeem.dto.Student;
import com.softeem.util.DBUtil;

public class AdminService {
	
	Connection conn;
	AdminDAO adminDao;
	
	//��ӿγ�
	public ResultModel register(Course c) {
		ResultModel result = new ResultModel();
		try {
			//��ȡ����
			conn = DBUtil.getConnection();
			adminDao = new AdminDAO(conn);
			Course course = adminDao.findCour(c);
//			System.out.println(student);
			//�жϸÿγ�Id��������ע��
			if(course != null){
				result.setCode(1);
				result.setMsg("�ÿγ��Ѵ���");
			}else{
				//��ӿγ�
				boolean boo = adminDao.insert(c);
				if(boo){
					//�ɹ�
					result.setCode(0);
					result.setMsg("��ӳɹ���");
				}else{
					//���ɹ�
					result.setCode(1);
					result.setMsg("ϵͳ��æ");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, null, null);
		}
		return result;
	}
	
	//�鿴�����ѷ����Ŀγ���Ϣ
		public ResultModel findAll(int pageSize, int currentPage) {
			ResultModel result = new ResultModel();
			adminDao = new AdminDAO(null);
			List<Course> list = adminDao.findAll(pageSize, currentPage);
			if(list != null && list.size()>0){
				result.setCode(0);
				result.setMsg("");
				result.setData(list);
			}else{
				result.setCode(1);
				result.setMsg("��û�пγ�");
				result.setData("");
			}
			return result;
		}
		
	//�鿴���е�ѧ����Ϣ
		public ResultModel findStu(int pageSize, int currentPage) {
			ResultModel result = new ResultModel();
			adminDao = new AdminDAO(null);
			List<Student> list = adminDao.findStudent(pageSize, currentPage);
			if(list != null && list.size()>0){
				result.setCode(0);
				result.setMsg("");
				result.setData(list);
			}else{
				result.setCode(1);
				result.setMsg("��û��ѧ����Ϣ");
				result.setData("");
			}
			return result;
		}
		
	//�޸Ŀγ�
		public ResultModel updateCourse(Course course) {
			ResultModel result = new ResultModel();
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				boolean boo = new AdminDAO(conn).update(course);
				if(boo){
					result.setCode(0);
					result.setMsg("�޸ĳɹ�");
				}else{
					result.setCode(1);
					result.setMsg("�޸�ʧ��");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtil.close(conn,null,null);
			}
			return result;
		}
	
	//ɾ��ѧ��
		public ResultModel delStu(int sid) {
			ResultModel result = new ResultModel();
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				AdminDAO adminDao = new AdminDAO(conn);
				boolean boo = adminDao.deleteStu(sid);
				if(boo){
					result.setCode(0);
					result.setMsg("ע���ɹ���");
				}else{
					result.setCode(1);
					result.setMsg("ϵͳ��æ��");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				DBUtil.close(conn, null, null);
			}
			return result;
		}
		
		
	//ɾ���γ�
			public ResultModel delAll(int cid) {
				ResultModel result = new ResultModel();
				Connection conn = null;
				try {
					conn = DBUtil.getConnection();
					AdminDAO adminDao = new AdminDAO(conn);
					boolean boo = adminDao.deleteAll(cid);
					if(boo){
						result.setCode(0);
						result.setMsg("ɾ���ɹ���");
					}else{
						result.setCode(1);
						result.setMsg("ϵͳ��æ��");
					}
				} catch (SQLException e) {
					e.printStackTrace();
				} finally{
					DBUtil.close(conn, null, null);
				}
				return result;
			}
	
	//��¼�߼�
	public ResultModel login(Admin a) {
	ResultModel result = new ResultModel();
	try {
		conn = DBUtil.getConnection();
		adminDao = new AdminDAO();
		Admin admin = adminDao.findOne(a);
		if(a.equals(admin)){
			result.setCode(0);
			result.setMsg("��¼�ɹ�");
			result.setData(admin);
		}else{
			//�û������������
			result.setCode(1);
			result.setMsg("�û������������");
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return result;
	
	}
}
