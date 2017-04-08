package com.yuanhao.service.impl;

import java.util.List;

import com.yuanhao.dao.IFoodDao;
import com.yuanhao.dao.impl.FoodDao;
import com.yuanhao.entity.Food;
import com.yuanhao.factory.BeanFactory;
import com.yuanhao.service.IFoodService;

public class FoodService implements IFoodService{

	private IFoodDao foodDao = BeanFactory.getInstance("foodDao", FoodDao.class);
	
	@Override
	public void add(Food food) {
		foodDao.add(food);
	}

	@Override
	public void update(Food food) {
		foodDao.update(food);
	}

	@Override
	public void delete(int id) {
		foodDao.delete(id);
	}

	@Override
	public List<Food> getAll() {
		return foodDao.getAll();
	}

	@Override
	public Food findById(int id) {
		return foodDao.findById(id);
	}

	@Override
	public List<Food> getFoodByName(String foodName) {
		return foodDao.getFoodByName(foodName);
	}

	@Override
	public List<Food> getFoodByType(int foodType) {
		return foodDao.getFoodByType(foodType);
	}

}
