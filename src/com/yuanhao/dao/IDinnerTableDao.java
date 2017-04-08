package com.yuanhao.dao;

import java.util.List;

import com.yuanhao.entity.DinnerTable;

public interface IDinnerTableDao {
	/**
	 * 添加
	 */
	void add(DinnerTable dinnerTable);
	
	/**
	 * 删除
	 */
	void delete(int id);
	
	/**
	 * 更新
	 */
	void update(DinnerTable dinnerTable);
	
	/**
	 * 显示全部
	 */
	List<DinnerTable> getAll();
	
	/**
	 * 条件模糊查询
	 */
	List<DinnerTable> getAll(String keyword);
	
	/**
	 * 主键查询
	 */
	DinnerTable findById(int id);
	
	/**
	 * 退桌
	 */
	void unsubscribe(int id);
}
