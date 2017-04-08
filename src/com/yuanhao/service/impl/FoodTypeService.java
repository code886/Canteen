package com.yuanhao.service.impl;

import java.util.List;

import com.yuanhao.dao.IFoodTypeDao;
import com.yuanhao.dao.impl.FoodTypeDao;
import com.yuanhao.entity.FoodType;
import com.yuanhao.factory.BeanFactory;
import com.yuanhao.service.IFoodTypeService;

public class FoodTypeService implements IFoodTypeService{
	
	//private IFoodTypeDao foodTypeDao = new FoodTypeDao();
	
	//工厂类方法创建对象，避免写死
	private IFoodTypeDao foodTypeDao = BeanFactory.getInstance("foodTypeDao", FoodTypeDao.class);
	
	@Override
	public void add(FoodType foodType) {
		try {
			foodTypeDao.add(foodType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		try {
			foodTypeDao.delete(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(FoodType foodType) {
		try {
			foodTypeDao.update(foodType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public FoodType findById(int id) {
		try {
			return foodTypeDao.findById(id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<FoodType> getAll() {
		try {
			return foodTypeDao.getAll();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<FoodType> getAll(String typeName) {
		try {
			return foodTypeDao.getAll(typeName);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
