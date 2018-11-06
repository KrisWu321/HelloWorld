package com.softeem.dao;

import java.sql.Connection;

import com.softeem.dto.Course;
import com.softeem.util.DBUtil;

public class CourseDAO {
	
	Connection conn;
	
	public CourseDAO(Connection conn){
		this.conn = conn;
	}
	
	//添加课程信息
	public boolean insert(Course t) {
		String sql = "insert into course("
				+ "id,name,number,"
				+ "teaname,point,dr) "
				+ "value(?,?,?,?,?,?)";
		try {
			return DBUtil.executeUpdate(conn, sql, 
					t.getId(),
					t.getName(),
					t.getNumber(),
					t.getTeaname(),
					t.getPoint(),
					t.getDr());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//删除课表信息
	public boolean delete(Course t) {
		String sql = "update course set dr = 1 "
				+ "where id = ?";
		try {
			return DBUtil.executeUpdate(conn, sql, t.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//修改课表信息
	public boolean update(Course t) {
		String sql = "update course set name=?,number=?,teaname=?,point=? "
				+ "where id = ?";
		try {
			return DBUtil.executeUpdate(conn, sql, 
					t.getName(),
					t.getNumber(),
					t.getTeaname(),
					t.getPoint(),
					t.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
