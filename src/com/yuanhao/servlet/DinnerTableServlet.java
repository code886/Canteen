package com.yuanhao.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanhao.entity.DinnerTable;
import com.yuanhao.factory.BeanFactory;
import com.yuanhao.service.IDinnerTableService;
import com.yuanhao.service.impl.DinnerTableService;

/**
 * Servlet implementation class DinnerTable
 */
@WebServlet("/table")
public class DinnerTableServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
   
	private static IDinnerTableService dinnerTableService =  BeanFactory.getInstance("dinnerTableService", DinnerTableService.class);
	
	//servlet初始化，就从数据库里查询所有餐桌信息
	public void init(ServletConfig config) throws ServletException{
		super.init();
		List<DinnerTable> list = dinnerTableService.getAll();
		config.getServletContext().setAttribute("table", list);
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		if("add".equals(method)){
			add(request, response);
		}else if ("delete".equals(method)) {
			delete(request, response);
		}else if ("update".equals(method)) {
			update(request, response);
		}else if ("list".equals(method)) {
			list(request, response);
		}else if ("search".equals(method)) {
			search(request, response);
		}
	}
	
	/**
	 * 添加
	 */
	private void add(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		String tableName = request.getParameter("tableName");
		System.out.println(tableName);
		if(tableName!=null&&!"".equals(tableName)){
			//对象属性封装
			DinnerTable dinnerTable = new DinnerTable();
			dinnerTable.setTableName(tableName);
			dinnerTableService.add(dinnerTable);
			//保存完毕后调回列表展示
			list(request, response);
		}
	}
	
	/**
	 * 删除
	 */
	private void delete(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String id = request.getParameter("id");
		if(id!=null&&!"".equals(id)){
			dinnerTableService.delete(Integer.parseInt(id));
			//保存完毕后调回列表展示
			list(request, response);
		}
	}
	
	/**
	 * 修改(修改预订状态)
	 */
	private void update(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String id = request.getParameter("id");
		System.out.println(id);
		if(id!=null&&!"".equals(id)){
			dinnerTableService.changeStatus(Integer.parseInt(id));
			//操作完毕后跳转list
			list(request, response);
		}
	}
	
	/**
	 * 模糊搜索
	 */
	private void search(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String keyword = request.getParameter("keyword");
		if(keyword!=null&&!"".equals(keyword)){
			List<DinnerTable> list = dinnerTableService.getAll(keyword);
			request.setAttribute("list", list);
			//模糊搜索的跳转
			request.getRequestDispatcher("/sys/board/boardList.jsp").forward(request, response);
		}
	}
	
	/**
	 * 列表展示
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//service查询结果
		List<DinnerTable> list = dinnerTableService.getAll();
		//保存request域   留给jsp页面解析
		request.setAttribute("list",list);
		
		//将餐桌列表存到context里传到前台显示
		request.getServletContext().setAttribute("table", list);
				
		request.getRequestDispatcher("/sys/board/boardList.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//处理页面传递的中文编码
		request.setCharacterEncoding("UTF-8");
		//处理返回显示的中文编码
		response.setContentType("text/html;charset=UTF-8");
		doGet(request, response);
	}

}
