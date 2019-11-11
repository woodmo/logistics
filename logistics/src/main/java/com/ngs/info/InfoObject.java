package com.ngs.info;

public class InfoObject {
	private int code;
	private String info;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	@Override
	public String toString() {
		return "InfoObject [code=" + code + ", info=" + info + "]";
	}
	public InfoObject(int code, String info) {
		super();
		this.code = code;
		this.info = info;
	}
	public InfoObject() {
		super();
	}
	
}
