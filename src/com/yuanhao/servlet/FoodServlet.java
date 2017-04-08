package com.yuanhao.servlet;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.yuanhao.entity.Food;
import com.yuanhao.entity.FoodType;
import com.yuanhao.factory.BeanFactory;
import com.yuanhao.service.IFoodService;
import com.yuanhao.service.IFoodTypeService;
import com.yuanhao.service.impl.FoodService;
import com.yuanhao.service.impl.FoodTypeService;
import com.yuanhao.utils.WebUtils;

/**
 * Servlet implementation class FoodServlet
 */
@WebServlet("/food")
public class FoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    private static IFoodService foodService = BeanFactory.getInstance("foodService",FoodService.class);
    private static IFoodTypeService foodTypeService = BeanFactory.getInstance("foodTypeService", FoodTypeService.class);
    
    private Object uri;

    //servlet初始化，就从数据库里查询所有餐桌信息
  	public void init(ServletConfig config) throws ServletException{
  		super.init();
  		List<Food> list = foodService.getAll();
  		config.getServletContext().setAttribute("list", list);
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
		}else if ("show".equals(method)) {
			show(request, response);
		}else if ("findFoodType".equals(method)) {
			findFoodType(request, response);
			
		}
	}
	
	private void findFoodType(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException  {
		List<FoodType> types = foodTypeService.getAll();
		request.setAttribute("types", types);
		uri = request.getRequestDispatcher("/sys/food/saveFood.jsp");
		WebUtils.goTo(request, response, uri);
	}

	/**
	 * 添加
	 */
	private void add(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException {
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(10 * 1024 * 1024); // 单个文件大小限制
			upload.setSizeMax(50 * 1024 * 1024); // 总文件大小限制
			upload.setHeaderEncoding("UTF-8"); // 对中文文件编码处理
			if (upload.isMultipartContent(request)) {
				Food food = new Food();
				@SuppressWarnings("unchecked")
				List<FileItem> list = upload.parseRequest(request);
				for (FileItem item : list) {
					if (item.isFormField()) {// 普通本文内容
						String name = item.getFieldName();
						// 获取值
						String value = item.getString();
						value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
						BeanUtils.setProperty(food, name, value);
					} else {// 上传内容
						String fieldName = item.getFieldName();
						String path = request.getServletContext().getRealPath("/upload");
						File f = new File(path);
						if (!f.exists()) {
							f.mkdir();
						}
						// 全部绝对路径
						String name = item.getName();
						BeanUtils.setProperty(food, fieldName, "upload/" + name);
						// a2. 拼接文件名
						File file = new File(path, name);
						// d. 上传
						if(!file.isDirectory()){
							item.write(file);
						}
						item.delete(); // 删除组件运行时产生的临时文件
					}
				}
				foodService.add(food);
			}
			list(request, response);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 删除
	 */
	private void delete(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		String id = request.getParameter("id");
		if(id!=null&&!"".equals(id)){
			foodService.delete(Integer.parseInt(id));
			//保存完毕后调回列表展示
			list(request, response);
		}
	}
	
	/**
	 * 修改(修改预订状态)
	 */
	private void update(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try {
			FileItemFactory factory = new DiskFileItemFactory();
			ServletFileUpload upload = new ServletFileUpload(factory);
			upload.setFileSizeMax(10 * 1024 * 1024); // 单个文件大小限制
			upload.setSizeMax(50 * 1024 * 1024); // 总文件大小限制
			upload.setHeaderEncoding("UTF-8"); // 对中文文件编码处理
			if (upload.isMultipartContent(request)) {
				Food food = new Food();
				List<FileItem> list = upload.parseRequest(request);
				for (FileItem item : list) {
					if (item.isFormField()) {// 普通本文内容
						String name = item.getFieldName();
						// 获取值
						String value = item.getString();
						value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
						BeanUtils.setProperty(food, name, value);
					} else {// 上传内容
						String fieldName = item.getFieldName();
						String path = request.getServletContext().getRealPath("/upload");
						File f = new File(path);
						if (!f.exists()) {
							f.mkdir();
						}
						String name = item.getName();
						if(name!=null && !"".equals(name.trim())){
							BeanUtils.setProperty(food, fieldName,("upload/" + name));
							// a2. 拼接文件名
							File file = new File(path, name);
							// d. 上传
							if (!file.isDirectory()) {
								item.write(file);
							}
							item.delete(); // 删除组件运行时产生的临时文件
						}else{
							int id = food.getId();
							String img = foodService.findById(id).getImg();
							BeanUtils.setProperty(food, "img",img);
						}
					}
				}
				foodService.update(food);
			}
			list(request, response);
		} catch (Exception e) {
			e.printStackTrace();
			uri = "/error/error.jsp";
			WebUtils.goTo(request, response, uri);
		}
	}
	
	/**
	 * 传递要修改的参数到另一个jsp
	 */
	private void show(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<FoodType> types = foodTypeService.getAll();
		request.setAttribute("types", types);
		String id = request.getParameter("id");
		Food food = foodService.findById(Integer.parseInt(id));

		request.setAttribute("food", food);
		// 得到食物里面的食物类型ID
		int foodType_id = food.getFoodType_id();

		// 通过
		FoodType type = foodTypeService.findById(foodType_id);
		request.setAttribute("type", type);

		uri = request.getRequestDispatcher("/sys/food/updateFood.jsp");
		WebUtils.goTo(request, response, uri);

	}
	
	/**
	 * 模糊搜索
	 */
	private void search(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
		try {
			String keyword = request.getParameter("keyword");
			System.out.println(keyword);
			if (keyword != null) {
				List<Food> list = foodService.getFoodByName(keyword);
				List<FoodType> types = new ArrayList<FoodType>();
				if (list != null) {
					for (Food food : list) {
						FoodType foodtype = foodTypeService.findById(food.getFoodType_id());
						types.add(foodtype);
					}
				}
				request.setAttribute("types", types);
				request.setAttribute("list", list);
				uri = request.getRequestDispatcher("/sys/food/foodList.jsp");
			}
		} catch (Exception e) {
			uri = "/error/error.jsp";
			e.printStackTrace();
		}
		WebUtils.goTo(request, response, uri);
	}
	
	/**
	 * 列表展示
	 */
	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//service查询结果
		List<Food> list = foodService.getAll();
		List<FoodType> types = new ArrayList<>();
		if(list!=null){
			for (Food food : list) {
				FoodType foodType = foodTypeService.findById(food.getFoodType_id());
				types.add(foodType);
			}
		}
		//保存request域   留给jsp页面解析
		request.setAttribute("list",list);
		request.setAttribute("types", types);
		request.getRequestDispatcher("/sys/food/foodList.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//处理页面传递的中文编码
		request.setCharacterEncoding("UTF-8");
		//处理返回显示的中文编码
		response.setContentType("text/html;charset=UTF-8");
		doGet(request, response);
	}

}
