package com.shopmax.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="order_item")
@Getter
@Setter
@ToString
public class OrderItem {

	@Id
	@Column(name="order_item_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name= "item_id")
	private Item item;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="order_id")
	private Order order;
	
	private int orderPrice; //주문가격
	
	private int count;		//수량
	
	//주문할 상품하고 주문수량을 통해 orderItem 객체를 만듬
	public static OrderItem createOrderItem(Item item, int count) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItem(item);
		orderItem.setCount(count);
		orderItem.setOrderPrice(item.getPrice());
		
		item.removeStock(count);
		
		return orderItem;
	}
	
	//총 가격 구하기
	public int getTotalPrice() {
		return orderPrice * count;
	}
	
	//
	public void cancel() {
		this.getItem().addStock(count);
	}
}
