package com.ischoolbar.programmer.entity;

import com.ischoolbar.programmer.annotation.Column;

/**
 * ������·ʵ��
 * @author llq
 *
 */
public class Book extends BaseEntity {
	
	private String name;//������·����

	@Column(name="book_category",isForeignEntity=true)
	private BookCategory bookCategory;//������·����
	
	private String start;//���
	
	private String end;//�յ�
	
	private String access;//;��������
	
	private String begin;//����ʱ��
	
	private int status;//������·״̬
	
	private int number = 1;//��Ʊ����
	
	private int freeNumber = 1;//���۳�Ʊ����
	
	private String info;//������·����
	
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
