package com.softeem.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.softeem.dao.StuCourDAO;
import com.softeem.dao.StudentDAO;
import com.softeem.dto.Course;
import com.softeem.dto.ResultModel;
import com.softeem.dto.Student;
import com.softeem.util.DBUtil;


public class StudentService {
	
	Connection conn;
	StudentDAO studentDao;
	
		//ɾ��ѡ��
		public ResultModel delCour(int sid, int cid) {
		ResultModel result = new ResultModel();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			StudentDAO studentDao = new StudentDAO(conn);
			boolean boo = studentDao.deleteAll(sid, cid);
			if(boo){
				StuCourDAO scd = new StuCourDAO(conn);
				scd.addnumber(cid);
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
		
	
		//�޸�ѧ����Ϣ�߼�
		public ResultModel update(Student stu){
			ResultModel result = new ResultModel();
			
			try {
				//��ȡ����
				conn = DBUtil.getConnection();
				studentDao = new StudentDAO(conn);
				boolean boo = studentDao.update(stu);
				//�޸�ѧ����Ϣ
				if(boo){
					//�ɹ�
					result.setCode(0);
					result.setMsg("�޸ĳɹ���");
				}else{
					//���ɹ�
					result.setCode(1);
					result.setMsg("ϵͳ��æ");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
			
		}
	
		//��ѯѧ����ѡ�γ�
		public ResultModel findAll(int sid, int pageSize, int currentPage) {
			ResultModel result = new ResultModel();
			studentDao = new StudentDAO();
			List<Course> list = studentDao.findAll(sid, pageSize, currentPage);
			if(list != null && list.size()>0){
				result.setCode(0);
				result.setMsg("");
				result.setData(list);
			}else{
				result.setCode(1);
				result.setMsg("��û��ѡ��γ�");
				result.setData("");
			}
			return result;
		}
		
		//ѧ���û�ע���߼�
		public ResultModel register(Student stu) {
			//����Ϊsevlet�����Student����
			ResultModel result = new ResultModel();
			try {
				//��ȡ����
				conn = DBUtil.getConnection();
				//1.�鿴�ֻ����Ƿ��ظ�
//				��ʼ��StudentDAO����
				studentDao = new StudentDAO(conn);
				Student student = studentDao.findOne(stu);
//				System.out.println(student);
				//�жϸõ绰�����������ע��
				if(student != null){
					result.setCode(1);
					result.setMsg("���ֻ����ѱ�ע��");
				}else{
					//����˺�
					boolean boo = studentDao.insert(stu);
					if(boo){
						//�ɹ�
						result.setCode(0);
						result.setMsg("ע��ɹ���");
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
		
		
		//ѧ����¼�߼�
		public ResultModel login(Student stu) {
			ResultModel result = new ResultModel();
			try {
				//��ȡ����
				conn = DBUtil.getConnection();
				studentDao = new StudentDAO();
				Student student = studentDao.findOne(stu);
				//�жϸ�ѧ���Ƿ�ע���
				if(stu.equals(student)){
					//��¼�ɹ�
					if(student.getDr() != 0){
						//δ����
						result.setCode(1);
						result.setMsg("�˺������");
					}else{
						//�ɹ���¼
						result.setCode(0);
						result.setMsg("��¼�ɹ�");
						result.setData(student);
					}
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
