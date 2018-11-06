package com.softeem.service;

import java.sql.SQLException;

import com.softeem.dao.CourseDAO;
import com.softeem.dto.Course;
import com.softeem.dto.ResultModel;
import com.softeem.util.DBUtil;



public class CourseService {
	//课程信息
	CourseDAO courseDao;
	
	//提交创建的课程信息
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
			//提交成功
			result.setCode(0);
			result.setMsg("提交成功");
		}else{
			//提交失败
			result.setCode(1);
			result.setMsg("提交失败");
		}
		return result;	
	}
}
