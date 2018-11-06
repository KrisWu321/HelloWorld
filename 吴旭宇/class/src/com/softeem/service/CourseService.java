package com.softeem.service;

import java.sql.SQLException;

import com.softeem.dao.CourseDAO;
import com.softeem.dto.Course;
import com.softeem.dto.ResultModel;
import com.softeem.util.DBUtil;



public class CourseService {
	//�γ���Ϣ
	CourseDAO courseDao;
	
	//�ύ�����Ŀγ���Ϣ
	public ResultModel submit(Course course) {
		
		ResultModel result = new ResultModel();
		boolean boo = false;
		try {
			boo = new CourseDAO(DBUtil.getConnection()).insert(course);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(boo){
			//�ύ�ɹ�
			result.setCode(0);
			result.setMsg("�ύ�ɹ�");
		}else{
			//�ύʧ��
			result.setCode(1);
			result.setMsg("�ύʧ��");
		}
		return result;	
	}
}
