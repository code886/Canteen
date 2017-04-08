package com.yuanhao.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yuanhao.entity.FoodType;
import com.yuanhao.factory.BeanFactory;
import com.yuanhao.service.IFoodTypeService;
import com.yuanhao.service.impl.FoodTypeService;

/**
 * 4. 菜系管理功能开发
 *  a.添加
 *  b.菜系列表展示
 * @author YuanHao
 * @since 2017年2月25日
 */
@WebServlet("/FoodTypeServlet")
public class FoodTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	//调用service
	private IFoodTypeService foodTypeService = BeanFactory.getInstance("foodTypeService", FoodTypeService.class);
	//跳转资源
	private Object uri;
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String method = request.getParameter("method");
		
		if("add".equals(method)){
			this.add(request, response);;
		}else if ("list".equals(method)) {
			this.list(request, response);
		}else if ("delete".equals(method)) {
			this.delete(request, response);
		}else if ("viewUpdate".equals(method)) {
			this.viewUpdate(request, response);
		}else if("update".equals(method)){
			this.update(request, response);
		}
	}

	/**
	 * 添加
	 */
	public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//1. 请求数据封装
			FoodType foodType = new FoodType();
			String foodTypeName = request.getParameter("foodTypeName");
			System.out.println(foodTypeName);
			foodType.setTypeName(foodTypeName);
			
			//2. 调用service处理业务逻辑
			foodTypeService.add(foodType);
			//跳转
			uri = "/FoodTypeServlet?method=list";
		} catch (Exception e) {
			e.printStackTrace();
			uri = "/error/error.jsp";
		}
		goTo(request, response, uri);
	}
	
	/**
	 * 列表展示
	 */
	public void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//调用servic查询
			List<FoodType> foodTypeList = foodTypeService.getAll();
			request.setAttribute("foodTypeList", foodTypeList);
			uri = request.getRequestDispatcher("/sys/type/foodtype_list.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			uri = "/error/error.jsp";
		}
		goTo(request, response, uri);
	}
	
	
	/**
	 * 更新页面
	 */
	public void viewUpdate(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			FoodType foodType = foodTypeService.findById(Integer.parseInt(id));
			request.setAttribute("foodType", foodType);
			uri = request.getRequestDispatcher("/sys/type/foodtype_update.jsp");
		} catch (NumberFormatException e) {
			e.printStackTrace();
			uri = "/error/error.jsp";
		}
		goTo(request, response, uri);
	}
	
	/**
	 * 删除
	 */
	public void delete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String id = request.getParameter("id");
			foodTypeService.delete(Integer.parseInt(id));
			uri = "/FoodTypeServlet?method=list";
		} catch (NumberFormatException e) {
			e.printStackTrace();
			uri = "/error/error.jsp";
		}
		goTo(request, response, uri);
	}
	
	/**
	 * 修改
	 */
	public void update(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			try {
				//1. 获取请求数据封装
				int id = Integer.parseInt(request.getParameter("id"));
				String name = request.getParameter("foodTypeName");
				FoodType foodType = new FoodType();
				foodType.setId(id);
				foodType.setTypeName(name);
				//2. 调用Service更新
				foodTypeService.update(foodType);
				uri = "/FoodTypeServlet?method=list";
			} catch (Exception e) {
				e.printStackTrace();
				uri = "/error/error.jsp";
			}
			// 跳转
			goTo(request, response,uri);
		}
	
	/**
	 * 跳转的通用方法
	 */
	private void goTo(HttpServletRequest request, HttpServletResponse response, Object uri)
			throws ServletException, IOException {
		if (uri instanceof RequestDispatcher){
			((RequestDispatcher)uri).forward(request, response);
		} else if (uri instanceof String) {
			response.sendRedirect(request.getContextPath() + uri);
		} 
	}
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		doGet(request, response);
	}

}
