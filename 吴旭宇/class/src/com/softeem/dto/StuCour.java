package com.softeem.dto;

public class StuCour {
	private String id;
	private int sid;
	private int cid;
	public StuCour() {
		super();
	}
	public StuCour(int sid, int cid) {
		super();
		this.sid = sid;
		this.cid = cid;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getCid() {
		return cid;
	}
	public void setCid(int cid) {
		this.cid = cid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cid;
		result = prime * result + sid;
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
		StuCour other = (StuCour) obj;
		if (cid != other.cid)
			return false;
		if (sid != other.sid)
			return false;
		return true;
	}
	
}

