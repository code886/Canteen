package com.yuanhao.dao;

import java.util.List;

import com.yuanhao.entity.Food;

public interface IFoodDao {
	/**
	 * 添加
	 */
	void add(Food food);
	
	/**
	 * 修改
	 */
	void update(Food food);
	
	/**
	 * 删除
	 */
	void delete(int id);
	
	/**
	 * 列表展示
	 */
	List<Food> getAll();
	
	/**
	 * 主键查询
	 */
	Food findById(int id);
	
	/**
	 * 菜名模糊查询
	 */
	List<Food> getFoodByName(String foodName);
	
	/**
	 * 菜系模糊查询
	 */
	List<Food> getFoodByType(int foodType);
	
}
