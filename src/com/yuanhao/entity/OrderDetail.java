package com.yuanhao.entity;

/**
 * 1. 实体类设计 —— 订单详细设计
 * @author YuanHao
 * @since 2017年2月25日
 */
public class OrderDetail {
	private int id;
	private int orderId;
	private int foodId;
	private int foodCount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getOrderId() {
		return orderId;
	}
	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}
	public int getFoodId() {
		return foodId;
	}
	public void setFoodId(int foodId) {
		this.foodId = foodId;
	}
	public int getFoodCount() {
		return foodCount;
	}
	public void setFoodCount(int foodCount) {
		this.foodCount = foodCount;
	}
	
}
