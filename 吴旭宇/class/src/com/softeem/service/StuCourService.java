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
	
	//选完课后让数据库更新课程剩余名额
	public ResultModel number(int sid, int cid){
		ResultModel result = new ResultModel();
		try {
			conn = DBUtil.getConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		stucourDao = new StuCourDAO(conn);
		//根据课程id找到课程的人数
		Course cour = new Course();
		cour = stucourDao.courNumber(cid);
		int c = cour.getNumber();
		if(c<=0){
			result.setCode(1);
			result.setMsg("课程人数已满");
		}else{
			submit(sid,cid);
		}
		return result;
	}
	
	
	
//	//根据课程id查找所有选择该课程的人数
//	public ResultModel number(int sid, int cid){
//		ResultModel result = new ResultModel();
//		stucourDao = new StuCourDAO(null);
//		//i为已选择该门课程的人数
//		int i = stucourDao.findCourse(cid);
//		Course cour = new Course();
//		//根据课程id找到课程的人数
//		cour = stucourDao.courNumber(cid);
//		int c = cour.getNumber();
//		if(i>=c){
//			result.setCode(1);
//			result.setMsg("课程人数已满");
//		}else{
//			result.setCode(0);
//			result.setMsg("");
//			submit(sid,cid);
//		}
//		return result;
//	}
	
	
	//查看所有已发布的课程信息
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
			result.setMsg("还没有课程");
			result.setData("");
		}
		return result;
	}
	
	//批量删除
		public ResultModel delAll(String[] ids) {
			ResultModel result = new ResultModel();
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				StuCourDAO stucourDao = new StuCourDAO(conn);
				boolean boo = stucourDao.deleteAll(ids);
				if(boo){
					result.setCode(0);
					result.setMsg("删除成功！");
				}else{
					result.setCode(1);
					result.setMsg("系统繁忙！");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			} finally{
				DBUtil.close(conn, null, null);
			}
			return result;
		}
	
	//提交选课到选课表(通过将课程的id，学生的id添加到选课表)
	public ResultModel submit(int sid,int cid) {
		ResultModel result = new ResultModel();
		try {
			//获取连接
			conn = DBUtil.getConnection();
			//1.查看课程是否重复
			stucourDao = new StuCourDAO(conn);
			StuCour stucour = stucourDao.findOne(sid,cid);
//			System.out.println(student);
			//判断该课程存在则不能注册
			if(stucour != null){
				result.setCode(1);
				result.setMsg("你已选择过该课程");
			}else{
				boolean boo = new StuCourDAO(DBUtil.getConnection()).insert(sid,cid);
				if(boo){
					//动态更新课程剩余名额
					stucourDao.updatenumber(cid);
					//提交成功
					result.setCode(0);
					result.setMsg("提交成功");
				}else{
					result.setCode(1);
					result.setMsg("提交失败");
				}
			} 
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
}
