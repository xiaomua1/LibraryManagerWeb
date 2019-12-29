package com.ischoolbar.programmer.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.ischoolbar.programmer.dao.BookCategoryDao;
import com.ischoolbar.programmer.dao.BookDao;
import com.ischoolbar.programmer.entity.Book;
import com.ischoolbar.programmer.entity.BookCategory;
import com.ischoolbar.programmer.page.Operator;
import com.ischoolbar.programmer.page.Page;
import com.ischoolbar.programmer.page.SearchProperty;
import com.ischoolbar.programmer.util.StringUtil;
/**
 * 公交信息管理控制器
 * @author llq
 *
 */
public class BookServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4386109520796986005L;

	private BookDao bookDao = new BookDao();
	
	private BookCategoryDao bookCategoryDao = new BookCategoryDao();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		Object attribute = request.getParameter("method");
		String method = "";
		if(attribute != null){
			method = attribute.toString();
		}
		if("toBookListView".equals(method)){
			request.getRequestDispatcher("/WEB-INF/views/book.jsp").forward(request, response);
			return;
		}
		if("BookList".equals(method)){
			getBookList(request,response);
			return;
		}
		if("AddBook".equals(method)){
			addBook(request,response);
			return;
		}
		if("EditBook".equals(method)){
			editBook(request,response);
			return;
		}
		if("DeleteBook".equals(method)){
			deleteBook(request,response);
			return;
		}
		if("GetBookCategoryComboxData".equals(method)){
			getBookCategoryComboxData(request,response);
			return;
		}
	}

	//获取公交线路列表
	private void getBookCategoryComboxData(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		Page<BookCategory> page = new Page<BookCategory>(1, 999);
		page = bookCategoryDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		BookCategory bookCategory = new BookCategory();
		bookCategory.setId(0);
		bookCategory.setName("全部");
		page.getContent().add(bookCategory);
		ret.put("type", "success");
		ret.put("values", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	//删除公交线路
	private void deleteBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String[] ids = request.getParameterValues("ids[]");
		Map<String, String> ret = new HashMap<String, String>();
		if(ids == null || ids.length ==0){
			ret.put("type", "error");
			ret.put("msg", "请选中要删除的数据!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		int[] idArr = new int[ids.length];
		for(int i = 0;i < ids.length; i++){
			idArr[i] = Integer.parseInt(ids[i]);
		}
		if(!bookDao.delete(idArr)){
			ret.put("type", "error");
			ret.put("msg", "删除失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "删除成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	//修改公交线路
	private void editBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int id = Integer.parseInt(request.getParameter("id"));
		int bookCategoryId = Integer.parseInt(request.getParameter("bookCategoryId"));
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String access = request.getParameter("access");
		String begin = request.getParameter("begin");
		int status = Integer.parseInt(request.getParameter("status"));
		int number = Integer.parseInt(request.getParameter("number"));
		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "公交线路名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if(bookCategory == null){
			ret.put("type", "error");
			ret.put("msg", "公交线路分类不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		//检查车票总数是否大于售出的数
		Book oldBook = bookDao.find(id);
		//已经售出的数量
		int borrowedNumber = oldBook.getNumber() - oldBook.getFreeNumber();
		if(number < borrowedNumber){
			ret.put("type", "error");
			ret.put("msg", "数量不能小于已经售出的数量!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		
		Book book = new Book();
		book.setId(id);
		book.setBookCategory(bookCategory);
		book.setName(name);
		book.setStart(start);
		book.setEnd(end);
		book.setAccess(access);
		book.setBegin(begin);
		book.setStatus(status);
		book.setNumber(number);
		book.setFreeNumber(number-borrowedNumber);
		book.setInfo(info);
		if(book.getFreeNumber() == 0){
			book.setStatus(2);
		}
		
		if(!bookDao.update(book)){
			ret.put("type", "error");
			ret.put("msg", "公交线路更新失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "公交线路更新成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	//添加公交线路
	private void addBook(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		int bookCategoryId = Integer.parseInt(request.getParameter("bookCategoryId"));
		String start = request.getParameter("start");
		String end = request.getParameter("end");
		String access = request.getParameter("access");
		String begin = request.getParameter("begin");
		int status = Integer.parseInt(request.getParameter("status"));
		int number = Integer.parseInt(request.getParameter("number"));
		String info = request.getParameter("info");
		Map<String, Object> ret = new HashMap<String, Object>();
		if(StringUtil.isEmpty(name)){
			ret.put("type", "error");
			ret.put("msg", "公交线路名称不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		BookCategory bookCategory = bookCategoryDao.find(bookCategoryId);
		if(bookCategory == null){
			ret.put("type", "error");
			ret.put("msg", "公交线路分类不能为空!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		Book book = new Book();
		book.setBookCategory(bookCategory);
		book.setName(name);
		book.setStart(start);
		book.setEnd(end);
		book.setAccess(access);
		book.setBegin(begin);
		book.setStatus(status);
		book.setNumber(number);
		book.setFreeNumber(number);
		book.setInfo(info);
		if(!bookDao.add(book)){
			ret.put("type", "error");
			ret.put("msg", "公交线路添加失败，请联系管理员!");
			StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
			return;
		}
		ret.put("type", "success");
		ret.put("msg", "公交线路添加成功!");
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}

	//获取公交信息列表
	private void getBookList(HttpServletRequest request,
			HttpServletResponse response) {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		String bbcid = request.getParameter("bookCategoryId");
		if(name == null){
			name = "";
		}
		int pageNumber = Integer.parseInt(request.getParameter("page"));
		int pageSize = Integer.parseInt(request.getParameter("rows"));
		Page<Book> page = new Page<Book>(pageNumber, pageSize);
		page.getSearchProporties().add(new SearchProperty("name", "%"+name+"%", Operator.LIKE));
		if(!StringUtil.isEmpty(bbcid) && !"0".equals(bbcid)){
			page.getSearchProporties().add(new SearchProperty("book_category", bookCategoryDao.find(Integer.parseInt(bbcid)), Operator.EQ));
		}
		page = bookDao.findList(page);
		Map<String, Object> ret = new HashMap<String, Object>();
		ret.put("total", page.getTotal());
		ret.put("rows", page.getContent());
		StringUtil.writrToPage(response, JSONObject.toJSONString(ret));
	}
	
}
