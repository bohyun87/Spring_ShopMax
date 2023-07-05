package com.shopmax.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.*;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.*;
import lombok.*;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass   //부모클래스를 상속받는 자식클래스에게 매핑정보를 제공하겠다.
@Getter
@Setter
public abstract class BaseTimeEntity {
				   //처음 등록된 날짜
	@CreatedDate   //엔티티가 생성되어 저장될 때 시간을 자동으로 저장
	@Column(updatable = false)  //컬럼의 값을 주정하지 못하게 설정
	private LocalDateTime regTime;   //등록날짜
	
	@LastModifiedDate   //수정될 때 시간을 자동으로 저장  => 마지막 저장된 날짜
	private LocalDateTime updateTime;  //수정날짜
}
