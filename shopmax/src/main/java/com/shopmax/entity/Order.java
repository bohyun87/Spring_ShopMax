package com.shopmax.entity;

import java.time.LocalDateTime;
import java.util.*;

import com.shopmax.constant.OrderStatus;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="orders")
@Getter
@Setter
@ToString
public class Order extends BaseEntity {

	@Id
	@Column(name="order_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private LocalDateTime orderDate;  //주문일
	
	@Enumerated(EnumType.STRING)
	private OrderStatus orderStatus;  //주문상태
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")
	private Member member;
	
	//order 에서도 orderItem을 참조할 수 있도록 양방향 관계를 만든다.
	//다만 orderItem 은 자식 테이블이 되므로 List 로 만든다             // cascade = CascadeType.ALL  => 영속성 관계
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)   //연관관계 주인 설정(5장 JPA 연관 관계 매핑 11p) => 부모테이블로 설정  
	private List<OrderItem> orderItems= new ArrayList<>();

}
