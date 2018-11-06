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
	
	//添加课程
	public ResultModel register(Course c) {
		ResultModel result = new ResultModel();
		try {
			//获取连接
			conn = DBUtil.getConnection();
			adminDao = new AdminDAO(conn);
			Course course = adminDao.findCour(c);
//			System.out.println(student);
			//判断该课程Id存在则不能注册
			if(course != null){
				result.setCode(1);
				result.setMsg("该课程已存在");
			}else{
				//添加课程
				boolean boo = adminDao.insert(c);
				if(boo){
					//成功
					result.setCode(0);
					result.setMsg("添加成功！");
				}else{
					//不成功
					result.setCode(1);
					result.setMsg("系统繁忙");
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DBUtil.close(conn, null, null);
		}
		return result;
	}
	
	//查看所有已发布的课程信息
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
				result.setMsg("还没有课程");
				result.setData("");
			}
			return result;
		}
		
	//查看所有的学生信息
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
				result.setMsg("还没有学生信息");
				result.setData("");
			}
			return result;
		}
		
	//修改课程
		public ResultModel updateCourse(Course course) {
			ResultModel result = new ResultModel();
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				boolean boo = new AdminDAO(conn).update(course);
				if(boo){
					result.setCode(0);
					result.setMsg("修改成功");
				}else{
					result.setCode(1);
					result.setMsg("修改失败");
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				DBUtil.close(conn,null,null);
			}
			return result;
		}
	
	//删除学生
		public ResultModel delStu(int sid) {
			ResultModel result = new ResultModel();
			Connection conn = null;
			try {
				conn = DBUtil.getConnection();
				AdminDAO adminDao = new AdminDAO(conn);
				boolean boo = adminDao.deleteStu(sid);
				if(boo){
					result.setCode(0);
					result.setMsg("注销成功！");
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
		
		
	//删除课程
			public ResultModel delAll(int cid) {
				ResultModel result = new ResultModel();
				Connection conn = null;
				try {
					conn = DBUtil.getConnection();
					AdminDAO adminDao = new AdminDAO(conn);
					boolean boo = adminDao.deleteAll(cid);
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
	
	//登录逻辑
	public ResultModel login(Admin a) {
	ResultModel result = new ResultModel();
	try {
		conn = DBUtil.getConnection();
		adminDao = new AdminDAO();
		Admin admin = adminDao.findOne(a);
		if(a.equals(admin)){
			result.setCode(0);
			result.setMsg("登录成功");
			result.setData(admin);
		}else{
			//用户名或密码错误
			result.setCode(1);
			result.setMsg("用户名或密码错误");
		}
		
	} catch (SQLException e) {
		e.printStackTrace();
	}
	return result;
	
	}
}
