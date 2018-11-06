package com.softeem.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.softeem.dao.StuCourDAO;
import com.softeem.dto.Course;
import com.softeem.dto.ResultModel;
import com.softeem.dto.StuCour;
import com.softeem.util.DBUtil;


public class StuCourService {
	StuCourDAO stucourDao;
	Connection conn;
	
	//ѡ��κ������ݿ���¿γ�ʣ������
	public ResultModel number(int sid, int cid){
		ResultModel result = new ResultModel();
		try {
			conn = DBUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stucourDao = new StuCourDAO(conn);
		//���ݿγ�id�ҵ��γ̵�����
		Course cour = new Course();
		cour = stucourDao.courNumber(cid);
		int c = cour.getNumber();
		if(c<=0){
			result.setCode(1);
			result.setMsg("�γ���������");
		}else{
			submit(sid,cid);
		}
		return result;
	}
	
	
	
//	//���ݿγ�id��������ѡ��ÿγ̵�����
//	public ResultModel number(int sid, int cid){
//		ResultModel result = new ResultModel();
//		stucourDao = new StuCourDAO(null);
//		//iΪ��ѡ����ſγ̵�����
//		int i = stucourDao.findCourse(cid);
//		Course cour = new Course();
//		//���ݿγ�id�ҵ��γ̵�����
//		cour = stucourDao.courNumber(cid);
//		int c = cour.getNumber();
//		if(i>=c){
//			result.setCode(1);
//			result.setMsg("�γ���������");
//		}else{
//			result.setCode(0);
//			result.setMsg("");
//			submit(sid,cid);
//		}
//		return result;
//	}
	
	
	//�鿴�����ѷ����Ŀγ���Ϣ
	public ResultModel findAll(int pageSize, int currentPage) {
		ResultModel result = new ResultModel();
		stucourDao = new StuCourDAO(null);
		List<Course> list = stucourDao.findAll(pageSize, currentPage);
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
	
	//����ɾ��
		public ResultModel delAll(String[] ids) {
			ResultModel result = new ResultModel();
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				StuCourDAO stucourDao = new StuCourDAO(conn);
				boolean boo = stucourDao.deleteAll(ids);
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
	
	//�ύѡ�ε�ѡ�α�(ͨ�����γ̵�id��ѧ����id��ӵ�ѡ�α�)
	public ResultModel submit(int sid,int cid) {
		ResultModel result = new ResultModel();
		try {
			//��ȡ����
			conn = DBUtil.getConnection();
			//1.�鿴�γ��Ƿ��ظ�
			stucourDao = new StuCourDAO(conn);
			StuCour stucour = stucourDao.findOne(sid,cid);
//			System.out.println(student);
			//�жϸÿγ̴�������ע��
			if(stucour != null){
				result.setCode(1);
				result.setMsg("����ѡ����ÿγ�");
			}else{
				boolean boo = new StuCourDAO(DBUtil.getConnection()).insert(sid,cid);
				if(boo){
					//��̬���¿γ�ʣ������
					stucourDao.updatenumber(cid);
					//�ύ�ɹ�
					result.setCode(0);
					result.setMsg("�ύ�ɹ�");
				}else{
					result.setCode(1);
					result.setMsg("�ύʧ��");
				}
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
