package com.softeem.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.softeem.util.DBUtil.CallBack;
import com.softeem.dto.Course;
import com.softeem.dto.Student;
import com.softeem.util.DBUtil;


//处理数据库相关操作
public class StudentDAO {
	Connection conn = null;
	
	public StudentDAO() {
		super();
		
	}
	public StudentDAO(Connection conn){
		this.conn = conn;
	}
	//学生注册
	public boolean insert(Student t) {
		try {
			return DBUtil.executeUpdate(conn, 
					"insert into student(id,name,sex,password,dr) "
					+ "value(?,?,?,?,?)",
					t.getId(),t.getName(),t.getSex(),
					t.getPassword(),t.getDr());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	//删除选课
	public boolean deleteAll(int sid, int cid){
				String sql = "delete from stucour where sid=? and cid=? ";
				try {
					return DBUtil.executeUpdate(conn, sql, sid, cid);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
	
	//查看已选课程信息
		public List<Course> findAll(int sid, int pageSize,int currentPage) {
			String sql = "select c.id,c.name,c.number,"
					+ "c.teaname,c.point from course c, stucour stu "
					+ "where stu.cid=c.id and stu.sid=?"
					+ " limit ?,?";
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
				}, sql, sid, (currentPage-1)*pageSize, pageSize);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return list;
		}
		//获取已选的课程总数
			public int findCount(int sid){
				String sql = "select count(id) "
						+ "from stucour where sid=?";
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
					}, sql, sid);
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0;
			}
	
	//修改学生信息逻辑
	public boolean update(Student t) {
		try {
			
			return DBUtil.executeUpdate(conn, 
					"update student set name=?,sex=?,password=? where id=?",
					t.getName(),t.getSex(),
					t.getPassword(),t.getId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	
	
	//查找单个学生信息
	public Student findOne(Student t) {
		try {
			return DBUtil.queryOne(new CallBack<Student>() {
				@Override
				public Student getData(ResultSet rs) {
					Student student = null;
					try {
						if(rs.next()){
							student = new Student();
							student.setId(rs.getInt("id"));
							student.setName(rs.getString("name"));
							student.setSex(rs.getString("sex"));
							student.setPassword(rs.getString("password"));
							student.setDr(rs.getInt("dr"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return student;
				}
			}, "select id,name,sex,password,dr from student where id=?", t.getId());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
