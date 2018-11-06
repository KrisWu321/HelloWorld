package com.softeem.dto;


public class Course{
	private int id;	//课程编号
	private String name; //课程名字 
	private int number; //课程人数
	private String teaname; //任课老师
	private int point; //课程学分
	private int dr; //状态(删除操作)
	public Course() {
		super();
	}
	public Course(int id, String name, int number, String teaname, int point, int dr) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.teaname = teaname;
		this.point = point;
		this.dr = dr;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getTeaname() {
		return teaname;
	}
	public void setTeaname(String teaname) {
		this.teaname = teaname;
	}
	public int getPoint() {
		return point;
	}
	public void setPoint(int point) {
		this.point = point;
	}
	public int getDr() {
		return dr;
	}
	public void setDr(int dr) {
		this.dr = dr;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Course other = (Course) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	
}
