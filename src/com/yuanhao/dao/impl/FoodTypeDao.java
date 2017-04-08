package com.yuanhao.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.yuanhao.dao.IFoodTypeDao;
import com.yuanhao.entity.FoodType;
import com.yuanhao.utils.JdbcUtils;

public class FoodTypeDao implements IFoodTypeDao{

	@Override
	public void add(FoodType foodType) {
		String sql="insert into foodtype(typeName) values (?)";
		try {
			JdbcUtils.getQueryRunner().update(sql,foodType.getTypeName());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		String sql = "delete from foodtype where id = ?";
		try {
			JdbcUtils.getQueryRunner().update(sql,id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(FoodType foodType) {
		String sql ="update foodtype set typeName=? where id=?";
		try {
			JdbcUtils.getQueryRunner().update(sql,foodType.getTypeName(),foodType.getId());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public FoodType findById(int id) {
		String sql = "select * from foodtype where id = ?";
		try {
			return JdbcUtils.getQueryRunner().query(sql, new BeanHandler<FoodType>(FoodType.class),id);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<FoodType> getAll() {
		String sql = "select * from foodtype";
		try {
			return JdbcUtils.getQueryRunner().query(sql, new BeanListHandler<FoodType>(FoodType.class));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<FoodType> getAll(String typeName) {
		String sql = "select * from foodtype where typeName like ?";
		try {
			return JdbcUtils.getQueryRunner().query(sql, new BeanListHandler<FoodType>(FoodType.class),"%"+typeName+"%");
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

}
