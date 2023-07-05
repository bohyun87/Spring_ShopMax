package com.shopmax.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shopmax.entity.ItemImg;
import com.shopmax.repository.ItemImgRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ItemImgService {
	
	//@Autowired 가 없어도 @RequiredArgsConstructor 때문에 자동으로 의존성 주입됨
	private String itemImgLocation = "C:/shop/item";	
	private final ItemImgRepository itemImgRepository;
	private final FileService fileService;
	
	//이미지 저장(2가지)
		//1. 파일을 itemImgLocation 에 저장	
		//2.DB에 item_img 테이블에 저장  	
	public void saveItemImg(ItemImg itemImg, MultipartFile itemImgFile) throws Exception {
		String oriImgName = itemImgFile.getOriginalFilename();  //파일이름 -> 이미지1.jpg
		String imgName = "";
		String imgUrl = "";
		
		//1. 파일을 itemImgLocation 에 저장	
		if(!StringUtils.isEmpty(oriImgName)) {
			//oriImgName 이 빈문자열이 아니라면
			imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			imgUrl = "/images/item" + imgName;
		}
		
		//2.DB에 item_img 테이블에 저장 
		itemImg.updateItemImg(oriImgName, imgName, imgUrl);  
						   //(이미지1.jpg, shfkajfhkawjefhkjafs.jpg, /images/item/shfkajfhkawjefhkjafs.jpg) 로 entity 값을 update
		itemImgRepository.save(itemImg);  //DB 에 insert
		
		
		
	}

}
















