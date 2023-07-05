package com.shopmax.entity;


import com.shopmax.constant.ItemSellStatus;

import jakarta.persistence.*;
import lombok.*;

@Entity  //엔티티 클래스로 정의
@Table(name="item")  //테이블 이름 지정
@Getter
@Setter
@ToString
public class Item extends BaseEntity  {
	
	@Id
	@Column(name="item_id")  //테이블로 생성될 때 컬럼이름을 지정해준다.
	@GeneratedValue(strategy = GenerationType.AUTO)  //필드의 값을 자동으로 생성해줌(시퀀스 같은 역할)
	private Long id;  //상품코드  long != Long
	
	@Column(nullable = false, length = 50)   // nullable = false => not null  / length = 50 => varchar(50)
	private String itemNm;  //상품명  -> item_nm
	
	@Column(nullable = false, name="price")
	private int price;  //가격
	
	@Column(nullable = false)
	private int stockNumber; //재고수량  -> stock_number
	
	@Lob  //CLOB 과 같은 큰 타입의 문자타입으로 컬럼을 만든다.
	@Column(nullable = false, columnDefinition = "longtext")
	private String itemDetail;  //상품상세설명  -> item_detail
	
	@Enumerated(EnumType.STRING)  // enum 의 이름을 DB에 저장
	private ItemSellStatus itemSellStatus;  //판매상태(SELL, SOLD_OUT)   ->  item_sell_status

}
