package com.shopmax.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="cart")
@Getter
@Setter
@ToString
public class Cart {
	
	//기본키생성
	@Id
	@Column(name="cart_id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	//외래키설정	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="member_id")	
	private Member member;
}
