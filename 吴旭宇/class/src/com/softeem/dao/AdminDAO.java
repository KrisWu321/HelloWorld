package com.softeem.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.softeem.dto.Admin;
import com.softeem.dto.Course;
import com.softeem.dto.Student;
import com.softeem.util.DBUtil;
import com.softeem.util.DBUtil.CallBack;

public class AdminDAO {
	
	Connection conn = null;
	
	public AdminDAO() {
		super();
		
	}
	public AdminDAO(Connection conn){
		this.conn = conn;
	}
	
	//删除课程
		public boolean deleteAll(int cid){
	
					String sql = "update course set dr=1 where id=?";
					try {
						return DBUtil.executeUpdate(conn, sql, cid);
					} catch (Exception e) {
						e.printStackTrace();
					}
					return false;
				}
	//删除学生
				public boolean deleteStu(int sid){
							String sql = "update student set dr=1 where id=?";
							try {
								return DBUtil.executeUpdate(conn, sql, sid);
							} catch (Exception e) {
								e.printStackTrace();
							}
							return false;
						}
	
	//添加课程
	public boolean insert(Course t) {
		try {
			return DBUtil.executeUpdate(conn, 
					"insert into course(id,name,number,teaname,point) "
					+ "value(?,?,?,?,?)",
					t.getId(),t.getName(),t.getNumber(),
					t.getTeaname(),t.getPoint());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//修改课程
	public boolean update(Course t) {
		String sql = "update course set number=?,teaname=?,point=? "
				+ "where id=?";
		try {
			return DBUtil.executeUpdate(conn, sql, 
					t.getNumber(),
					t.getTeaname(),
					t.getPoint(),
					t.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//查找课程信息
	public Course findCour(Course t) {
		try {
			return DBUtil.queryOne(new CallBack<Course>() {
				@Override
				public Course getData(ResultSet rs) {
					Course course = null;
					try {
						if(rs.next()){
							course = new Course();
							course.setId(rs.getInt("id"));
							course.setName(rs.getString("name"));
							course.setNumber(rs.getInt("number"));
							course.setTeaname(rs.getString("teaname"));
							course.setPoint(rs.getInt("point"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return course;
				}
			}, "select id,name,number,teaname,point from course where id=?", t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//登录逻辑
	public Admin findOne(Admin t) {
		try {
			return DBUtil.queryOne(new CallBack<Admin>() {
				@Override
				public Admin getData(ResultSet rs) {
					Admin admin = null;
					try {
						if(rs.next()){
							admin = new Admin();
							admin.setId(rs.getInt("id"));
							admin.setPassword(rs.getString("password"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return admin;
				}
			}, "select id,password from admin where id=?", t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//查看所有学生信息
	public List<Student> findStudent(int pageSize,int currentPage) {
		String sql = "select id,name,sex"
				+ " from student where dr=0 "
				+ "limit ?,?";
		List<Student> list = new ArrayList<Student>();
		try {
			DBUtil.queryList(new CallBack<Student>() {
				@Override
				public List<Student> getDatas(ResultSet rs) {
					
					try {
						while(rs.next()){
							Student student = new Student();
							student.setId(rs.getInt("id"));
							student.setName(rs.getString("name"));
							student.setSex(rs.getString("sex"));
							list.add(student);
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return list;
				}
			}, sql, (currentPage-1)*pageSize, pageSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	//查看所有课程信息
		public List<Course> findAll(int pageSize,int currentPage) {
			String sql = "select id,name,number,"
					+ "teaname,point from course where dr=0 "
					+ "limit ?,?";
			List<Course> list = new ArrayList<Course>();
			try {
				DBUtil.queryList(new CallBack<Course>() {
					@Override
					public List<Course> getDatas(ResultSet rs) {
						
						try {
							while(rs.next()){
								Course course = new Course();
								course.setId(rs.getInt("id"));
								course.setName(rs.getString("name"));
								course.setNumber(rs.getInt("number"));
								course.setTeaname(rs.getString("teaname"));
								course.setPoint(rs.getInt("point"));
								list.add(course);
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return list;
					}
				}, sql, (currentPage-1)*pageSize, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		//获取已发布的课程总数
			public int findCount(){
				String sql = "select count(id) "
						+ "from course";
				try {
					return DBUtil.queryOne(new CallBack<Integer>() {
						@Override
						public Integer getData(ResultSet rs) {
							Integer i = 0;
							try {
								if(rs.next()){
									int count = rs.getInt(1);
									i = count;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							return i;
						}
					}, sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
			
			
			//获取所有学生总数
			public int findStuNum(){
				String sql = "select count(id) "
						+ "from student where dr=0";
				try {
					return DBUtil.queryOne(new CallBack<Integer>() {
						@Override
						public Integer getData(ResultSet rs) {
							Integer i = 0;
							try {
								if(rs.next()){
									int count = rs.getInt(1);
									i = count;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							return i;
						}
					}, sql);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
	
}
