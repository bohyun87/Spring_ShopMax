package com.shopmax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.entity.Item;


@SpringBootTest  //빈객체로 만든다 -> 스프링 컨테이너에 등록된다.
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

	@Autowired
	ItemRepository itemRepository;
	
	
	@Disabled   //실행되지 않게 해주는 어노테이션
	@Test
	@DisplayName("상품 저장 테스트")
	public void createItemTest()	{
		Item item = new Item();
		item.setItemNm("테스트 상품"); 
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세 설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now());
		item.setUpdateTime(LocalDateTime.now());
		
		//insert
		Item savedItem = itemRepository.save(item);
		System.out.println(savedItem.toString());
	}	
	
	// 여러개 저장하기, 테스트 코드 아님, 단순 데이터 저장
	public void createItemList()	{
		for(int i=1; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i); 
			item.setPrice(10000+i);
			item.setItemDetail("테스트 상품 상세 설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
			item.setUpdateTime(LocalDateTime.now());
			
			//insert
			Item savedItem = itemRepository.save(item);
			//System.out.println(savedItem.toString());			
		}		
	}
	
	@Disabled
	@Test
	@DisplayName("상품 조회 테스트")
	public void findByItemNmTest() {
		this.createItemList();   //데이터 10개 insert
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//쿼리메소드 퀴즈1
	/*@Disabled
	@Test
	@DisplayName("퀴즈1-1")
	public void quiz1_1Test() {
		 this.createItemList();
		 List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);
		 
		for(Item item : itemList) {
			System.out.println(item.toString());
		}		
		System.out.println("======================");
	}*/
	
	@Disabled
	@Test
	@DisplayName("퀴즈1-2")
	public void quiz1_2Test() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByPriceBetween(10004, 10008);
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		System.out.println("======================");
	}
	
	@Disabled
	@Test 
	@DisplayName("퀴즈1-3") 
	public void quiz1_3Test() { 
		this.createItemList();
		LocalDateTime regTime = LocalDateTime.of(2023, 1, 1, 12, 12, 44);
		List<Item> itemList = itemRepository.findByRegTimeAfter(regTime);
		for(Item item : itemList) { 
			 System.out.println(item.toString()); 
		}
		 System.out.println("======================");
	}
	 
	@Disabled
	@Test
	@DisplayName("퀴즈1-4")
	public void quiz1_4Test() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemSellStatusNotNull();
		for(Item item : itemList) {
			System.out.println(item.toString());			
		}		
		System.out.println("======================");
	}
	
	@Disabled
	@Test
	@DisplayName("퀴즈1-5")
	public void quiz1_5Test() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemDetailLike("%설명1");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		System.out.println("======================");
	}
	
	@Disabled
	@Test
	@DisplayName("퀴즈1-6")
	public void quiz1_6Test() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세 설명5");
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
		System.out.println("======================");
	}
	
	@Disabled
	@Test
	@DisplayName("퀴즈1-7") 
	public void quiz1_7Test() { 
		this.createItemList();
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005); 
		for(Item item : itemList) { 
			System.out.println(item.toString()); 
		}
		System.out.println("======================"); 
	}
	
	@Disabled
	@Test
	@DisplayName("@Query를 이용한 상품 조회 테스트")
	public void findByItemDetailTest() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세");
		
		for(Item item : itemList) {
			System.out.println(item.toString());			
		}
	}
	
	@Disabled
	@Test
	@DisplayName("@Query(native 쿼리)를 이용한 상품 조회 테스트")
	public void findByItemDetailByNative() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세");
		
		for(Item item : itemList) {
			System.out.println(item.toString());			
		}		
	}
	
	//퀴즈2
	@Disabled
	@Test
	@DisplayName("퀴즈2_1")
	public void findByPriceGreaterThanEqual() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByPriceGreaterThanEqual(10005);
		
		for(Item item : itemList) {
			System.out.println(item.toString());			
		}
	}
	
	@Disabled
	@Test
	@DisplayName("퀴즈2_2")
	public void findByItemNmAndItemSellStatus() {
		this.createItemList();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);
		
		for(Item item : itemList) {
			System.out.println(item.toString());
		}
	}

}