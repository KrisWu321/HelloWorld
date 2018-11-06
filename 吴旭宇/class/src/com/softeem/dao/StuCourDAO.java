package com.softeem.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.softeem.dto.Course;
import com.softeem.dto.StuCour;
import com.softeem.util.DBUtil;
import com.softeem.util.DBUtil.CallBack;
import com.softeem.util.Tools;



public class StuCourDAO {
	
	Connection conn;
	
	public StuCourDAO(Connection conn){
		this.conn = conn;
	}
	//����ɾ��
	public boolean deleteAll(String[] ids){
		//�����������ڴ洢��?����ռλ��
				String placeholder = "";
				for(int i=0;i<ids.length;i++){
					placeholder += "?,";
				}
				//?,?
				placeholder = placeholder.substring(0, placeholder.length()-1);
				String sql = "update stucour set dr=1 where id in("+placeholder+")";
				try {
					return DBUtil.executeUpdate(conn, sql, ids);
				} catch (Exception e) {
					e.printStackTrace();
				}
				return false;
			}
	
	//ѡ��κ������ݿ����һ���γ�ʣ������
	public boolean updatenumber(int cid) {
		try {
			String sql = "update course set number=number-1 where id=?";
			return DBUtil.executeUpdate(conn, sql, cid);
		} catch (Exception e) {
			e.printStackTrace();
		}
	return false;
	}
	//ѡ��κ������ݿ�����һ���γ�ʣ������
		public boolean addnumber(int cid) {
			try {
				String sql = "update course set number=number+1 where id=?";
				return DBUtil.executeUpdate(conn, sql, cid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
		}
	
	
	//�ж������Ƿ���������������ѡ��ÿγ�
	//�ж��Ƿ���ѡ��ÿγ̣���ѡ������ѡ��ÿγ�
	public int findCourse(int cid){
		String sql = "select count(id) from stucour where cid=?";
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
			}, sql, cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	//�ύѡ��
	public boolean insert(int sid,int cid) {
			try {
				String sql = "insert into stucour(id,sid,cid) value(?,?,?)";
				return DBUtil.executeUpdate(conn, sql, Tools.getUID(),sid,cid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		return false;
	}
	
	//����sid��cid����һ��ѡ�α���Ϣ
	public StuCour findOne(int sid, int cid){
		try {
			return DBUtil.queryOne(new CallBack<StuCour>() {
				@Override
				public StuCour getData(ResultSet rs) {
					StuCour stu = null;
					try {
						if(rs.next()){
							stu = new StuCour();
							stu.setId(rs.getString("id"));
							stu.setSid(rs.getInt("sid"));
							stu.setCid(rs.getInt("cid"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return stu;
				}
			}, "select id,sid,cid from stucour where sid=? and cid=?", sid, cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//��ѯһ�ſγ̵�����
	public Course courNumber(int cid){
		try {
			return DBUtil.queryOne(new CallBack<Course>() {
				@Override
				public Course getData(ResultSet rs) {
					Course cour = null;
					try {
						if(rs.next()){
							cour = new Course();
							cour.setNumber(rs.getInt("number"));
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
					return cour;
				}
			}, "select number from course where id=?", cid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	//�鿴���пγ���Ϣ
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
	//��ȡ�ѷ����Ŀγ�����
		public int findCount(){
			String sql = "select count(id) "
					+ "from course where dr=0";
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
