package com.ischoolbar.programmer.entity;

import java.sql.Timestamp;

import com.ischoolbar.programmer.annotation.Column;

/**
 * 购票实体
 * @author llq
 *
 */
public class Borrow extends BaseEntity {
	
	@Column(name="user",isForeignEntity=true)
	private User user;//用户
	
	private int userId;

	@Column(name="book",isForeignEntity=true)
	private Book book;//公交线路
	
	private int bookId;
	
	private int status = 1;//购票状态：1：已购票，2：已退票
	
	private int number = 1;//购票数量
	
	private Timestamp borrowTime;//购票时间
	
	private Timestamp returnTime;//退票时间

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
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

	public Timestamp getBorrowTime() {
		return borrowTime;
	}

	public void setBorrowTime(Timestamp borrowTime) {
		this.borrowTime = borrowTime;
	}

	public Timestamp getReturnTime() {
		return returnTime;
	}

	public void setReturnTime(Timestamp returnTime) {
		this.returnTime = returnTime;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	
	
	
}
