package com.shopmax.entity;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass
@Getter
@Setter
public abstract class BaseEntity extends BaseTimeEntity{  //최종적으로는 BaseEntity 에 모든 데이터가 있다.
														  //BaseTimeEntity 를 상속받고 있기 때문에

	@CreatedBy
	@Column(updatable = false)
	private String createBy;  //등록자
	
	@LastModifiedBy
	private String modifiedBy;  //수정자
	
}
