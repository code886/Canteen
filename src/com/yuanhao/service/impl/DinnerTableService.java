package com.yuanhao.service.impl;

import java.util.Date;
import java.util.List;

import com.yuanhao.dao.IDinnerTableDao;
import com.yuanhao.dao.impl.DinnerTableDao;
import com.yuanhao.entity.DinnerTable;
import com.yuanhao.factory.BeanFactory;
import com.yuanhao.service.IDinnerTableService;

public class DinnerTableService implements IDinnerTableService {

	private IDinnerTableDao dinnerTableDao = BeanFactory.getInstance("dinnerTableDao", DinnerTableDao.class);
	
	@Override
	public void add(DinnerTable dinnerTable) {
		try{
			dinnerTableDao.add(dinnerTable);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		try{
			dinnerTableDao.delete(id);;
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(DinnerTable dinnerTable) {
		try{
			dinnerTableDao.update(dinnerTable);
		}catch(Exception e){
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<DinnerTable> getAll() {
		try{
			return dinnerTableDao.getAll();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DinnerTable> getAll(String keyword) {
		try{
			return dinnerTableDao.getAll(keyword);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public DinnerTable findById(int id) {
		try{
			return dinnerTableDao.findById(id);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}

	@Override
	public void unsubscribe(int id) {
		try{
			dinnerTableDao.unsubscribe(id);
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void changeStatus(int id) {
		DinnerTable dinnerTable = dinnerTableDao.findById(id);
		System.out.println(dinnerTable);
		int status = dinnerTable.getTableStatus();
		if(status==0){
			//餐桌 空闲--->预订(0--->1)
			status = 1;
			Date orderDate = new Date();
			dinnerTable.setOrderDate(orderDate);
		}else if(status==1){
			//餐桌 空闲<---预订(0<---1)
			status = 0;
			dinnerTable.setOrderDate(null);
		}
		dinnerTable.setTableStatus(status);
		update(dinnerTable);
	}

}
