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
	
		//删除选课
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
		
	
		//修改学生信息逻辑
		public ResultModel update(Student stu){
			ResultModel result = new ResultModel();
			
			try {
				//获取连接
				conn = DBUtil.getConnection();
				studentDao = new StudentDAO(conn);
				boolean boo = studentDao.update(stu);
				//修改学生信息
				if(boo){
					//成功
					result.setCode(0);
					result.setMsg("修改成功！");
				}else{
					//不成功
					result.setCode(1);
					result.setMsg("系统繁忙");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return result;
			
		}
	
		//查询学生已选课程
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
				result.setMsg("还没有选择课程");
				result.setData("");
			}
			return result;
		}
		
		//学生用户注册逻辑
		public ResultModel register(Student stu) {
			//参数为sevlet传入的Student对象
			ResultModel result = new ResultModel();
			try {
				//获取连接
				conn = DBUtil.getConnection();
				//1.查看手机号是否重复
//				初始化StudentDAO对象
				studentDao = new StudentDAO(conn);
				Student student = studentDao.findOne(stu);
//				System.out.println(student);
				//判断该电话号码存在则不能注册
				if(student != null){
					result.setCode(1);
					result.setMsg("该手机号已被注册");
				}else{
					//添加账号
					boolean boo = studentDao.insert(stu);
					if(boo){
						//成功
						result.setCode(0);
						result.setMsg("注册成功！");
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
		
		
		//学生登录逻辑
		public ResultModel login(Student stu) {
			ResultModel result = new ResultModel();
			try {
				//获取连接
				conn = DBUtil.getConnection();
				studentDao = new StudentDAO();
				Student student = studentDao.findOne(stu);
				//判断该学号是否被注册过
				if(stu.equals(student)){
					//登录成功
					if(student.getDr() != 0){
						//未激活
						result.setCode(1);
						result.setMsg("账号审核中");
					}else{
						//成功登录
						result.setCode(0);
						result.setMsg("登录成功");
						result.setData(student);
					}
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
