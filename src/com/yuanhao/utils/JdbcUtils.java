package com.yuanhao.utils;

import javax.sql.DataSource;

import org.apache.commons.dbutils.QueryRunner;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class JdbcUtils {
	private static DataSource dataSource;
	static{
		dataSource = new ComboPooledDataSource();
	}
	
	public static DataSource getDataSource() {
		return dataSource;
	}
	
	/**
	 * 创建Dbutils常用工具类对象
	 */
	public static QueryRunner getQueryRunner(){
		return new QueryRunner(dataSource );
	}
}
