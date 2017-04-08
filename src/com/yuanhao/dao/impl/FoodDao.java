package com.yuanhao.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.yuanhao.dao.IFoodDao;
import com.yuanhao.entity.Food;
import com.yuanhao.utils.JdbcUtils;

public class FoodDao implements IFoodDao {

	private QueryRunner qr = JdbcUtils.getQueryRunner();
	
	@Override
	public void add(Food food) {
		try {
			String sql = "insert into food(foodName,foodType_id,price,mprice,introduction,img) values (?,?,?,?,?,?)";
			qr.update(sql,food.getFoodName(),food.getFoodType_id(),food.getPrice(),food.getMprice(),food.getIntroduction(),food.getImg());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(Food food) {
		try {
			String sql = "update food set foodName=?,foodType_id=?,price=?,mprice=?,introduction=?,img=? where id =?";
			qr.update(sql,food.getFoodName(),food.getFoodType_id(),food.getPrice(),food.getMprice(),food.getIntroduction(),food.getImg(),food.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		try {
			String sql = "delete from food where id = ?";
			qr.update(sql,id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<Food> getAll() {
		try {
			String sql = "select * from food";
			List<Food> list = qr.query(sql, new BeanListHandler<Food>(Food.class));
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public Food findById(int id) {
		try {
			String sql = "select * from food where id = ?";
			return qr.query(sql, new BeanHandler<Food>(Food.class),id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Food> getFoodByName(String foodName) {
		try {
			String sql = "select * from food where foodName like ?";
			List<Food> list = qr.query(sql, new BeanListHandler<Food>(Food.class),"%"+foodName+"%");
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<Food> getFoodByType(int foodType) {
		try {
			String sql = "select * from food where foodType_id = ?";
			List<Food> list = qr.query(sql, new BeanListHandler<Food>(Food.class),foodType);
			return list;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
