package com.ts.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Problem {
	
	@Id@GeneratedValue
	private int id;
	private String probid;
	private String probname;
	private String probimg;
	
	public Problem() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getProbid() {
		return probid;
	}
	public void setProbid(String probid) {
		this.probid = probid;
	}
	public String getProbname() {
		return probname;
	}
	public void setProbname(String probname) {
		this.probname = probname;
	}
	public String getProbimg() {
		return probimg;
	}
	public void setProbimg(String probimg) {
		this.probimg = probimg;
	}
	@Override
	public String toString() {
		return "Problem [id=" + id + ", probid=" + probid + ", probname=" + probname + ", probimg=" + probimg + "]";
	}	
}
