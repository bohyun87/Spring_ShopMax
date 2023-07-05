package com.shopmax.dto;

import org.modelmapper.ModelMapper;

import com.shopmax.entity.ItemImg;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ItemImgDto {

	private Long id;
	
	private String imgName;
	
	private String oriImgName;
	
	private String imgUrl;
	
	private String repimgYn;
	
	//단순하게 데이터 가져오는 것은 modelMapper 이용
	private static ModelMapper modelMapper = new ModelMapper();
	
	//entity -> dto 로 변환하는 메소드
	public static ItemImgDto of(ItemImg itemImg){
		return modelMapper.map(itemImg, ItemImgDto.class);
	}
}
