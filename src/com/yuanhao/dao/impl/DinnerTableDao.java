package com.yuanhao.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.yuanhao.dao.IDinnerTableDao;
import com.yuanhao.entity.DinnerTable;
import com.yuanhao.utils.JdbcUtils;

public class DinnerTableDao implements IDinnerTableDao {
	
	private QueryRunner qr = JdbcUtils.getQueryRunner();

	@Override
	public void add(DinnerTable dinnerTable) {
		try {
			String sql = "insert into dinnertable(tableName) values (?)";
			qr.update(sql, dinnerTable.getTableName());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void delete(int id) {
		try {
			String sql = "delete from dinnertable where id = ?";
			qr.update(sql, id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void update(DinnerTable dinnerTable) {
		try {
			String sql = "update dinnertable set tableStatus = ?,orderDate = ? where id = ?";
			qr.update(sql, dinnerTable.getTableStatus(),dinnerTable.getOrderDate(),dinnerTable.getId());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	public List<DinnerTable> getAll() {
		try {
			String sql = "select * from dinnertable";
			return qr.query(sql, new BeanListHandler<DinnerTable>(DinnerTable.class));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public List<DinnerTable> getAll(String keyword) {
		try {
			String sql = "select * from dinnertable where tableName like ?";
			return qr.query(sql, new BeanListHandler<DinnerTable>(DinnerTable.class),"%"+keyword+"%");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public DinnerTable findById(int id) {
		try {
			String sql = "select * from dinnertable where id= ?";
			return qr.query(sql, new BeanHandler<DinnerTable>(DinnerTable.class),id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void unsubscribe(int id) {
		//退桌 tableStatus = 0   预订日期为null
		try {
			String sql = "update dinnertable set orderStatus = ? , orderDate = ? where id = ?";
			qr.update(sql,0,null,id);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

}
