package com.shopmax.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import com.shopmax.entity.ItemImg;
import com.shopmax.repository.ItemImgRepository;

import jakarta.persistence.EntityNotFoundException;
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
	
	//이미지 업데이트 메소드
	public void updateItemImg(Long itemImgId, MultipartFile itemImgFile) throws Exception {
		
		//해당 이미지 가져오기
		if(!itemImgFile.isEmpty()) {  //첨부한 이미지 파일이 있으면
			ItemImg savedItemImg = itemImgRepository.findById(itemImgId)
													.orElseThrow(EntityNotFoundException::new);
			
			//기존이미지 파일 c:/shop/item 폴더에서 삭제
			if(!StringUtils.isEmpty(savedItemImg.getImgName())) {  //DB 의 img_name 에 저장된 이름 가져오기
				fileService.deleteFile(itemImgLocation + "/" + savedItemImg.getImgName() );
			}
			
			//수정된 이미지 파일 c:/shop/item 폴더 업로드
			String oriImgName = itemImgFile.getOriginalFilename();
			String imgName = fileService.uploadFile(itemImgLocation, oriImgName, itemImgFile.getBytes());
			String imgUrl = "/images/item/" + imgName;
			
			//update 쿼리문 실행
			savedItemImg.updateItemImg(oriImgName, imgName, imgUrl);

			/*
			 * 한번 insert 를 진행하면 엔티티가 영속성 컨텍스트에 저장이 되므로 
			 * 그 이후에는 변경감지 기능이 동작하기 때문에 개발자는 update
			 * 쿼리문을 쓰지 않고 엔티티 데이터만 변경해주면 된다.
			 */
		}
	}
	

}
















