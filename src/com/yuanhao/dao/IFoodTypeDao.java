package com.yuanhao.dao;

import java.util.List;

import com.yuanhao.entity.FoodType;

/**
 * 2. 菜系模块 ——— dao设计
 * @author YuanHao
 * @since 2017年2月25日
 */
public interface IFoodTypeDao {
	/**
	 * 添加
	 */
	void add(FoodType foodType);
	
	/**
	 * 删除
	 */
	void delete(int id);
	
	/**
	 * 修改
	 */
	void update(FoodType foodType);
	
	/**
	 * 根据主键查询
	 */
	FoodType findById(int id);
	
	/**
	 * 查询全部
	 */
	List<FoodType> getAll();
	
	/**
	 * 根据菜系查询(模糊查询)
	 */
	List<FoodType> getAll(String typeName);
}
