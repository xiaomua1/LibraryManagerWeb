package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Column;

/**
 * 公交线路实体
 * @author llq
 *
 */
public class Book extends BaseEntity {
	
	private String name;//公交线路名称

	@Column(name="book_category",isForeignEntity=true)
	private BookCategory bookCategory;//公交线路分类
	
	private String start;//起点
	
	private String end;//终点
	
	private String access;//途经公交点
	
	private String begin;//发车时间
	
	private int status;//公交线路状态
	
	private int number = 1;//车票数量
	
	private int freeNumber = 1;//在售车票数量
	
	private String info;//公交线路介绍
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BookCategory getBookCategory() {
		return bookCategory;
	}

	public void setBookCategory(BookCategory bookCategory) {
		this.bookCategory = bookCategory;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}
	
	public String getBegin() {
		return begin;
	}

	public void setBegin(String begin) {
		this.begin = begin;
	}
	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public int getFreeNumber() {
		return freeNumber;
	}

	public void setFreeNumber(int freeNumber) {
		this.freeNumber = freeNumber;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
