package com.shopmax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.entity.Item;
					  		   //<연결하려고 하는 엔티티 클래스명, 프라이머리 키 타입>
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{  
												
	// select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);   //findBy + 컬럼명
	
	
	
	
	//퀴즈1
	//select * from item where item_nm = ? and item_sell_status = ?
	//List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus itemSellStatus);
	List<Item> findByPriceBetween(int lowPrice, int HighPrice);
	List<Item> findByRegTimeAfter(LocalDateTime regTime);
	List<Item> findByItemSellStatusNotNull();
	List<Item> findByItemDetailLike(String itemDetail);
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
	
	//JPQL(non native 쿼리) -> ★★★★★컬럼명, 테이블명은 entity 클래스 기준으로 작성한다.
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
	
	
	//JPQL(native 쿼리) -> ★★★★★컬럼명, 테이블명은 entity 클래스 기준으로 작성한다.
	@Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	
	//퀴즈2
	@Query("select i from Item i where i.price >= :price")
	List<Item> findByPriceGreaterThanEqual(@Param("price") int price);
	
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :sell")
	List<Item> findByItemNmAndItemSellStatus(@Param("itemNm") String itemNm, @Param("sell") ItemSellStatus sell);
	
	
}