package com.shopmax.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopmax.constant.ItemSellStatus;
import com.shopmax.dto.ItemRankDto;
import com.shopmax.entity.Item;
					  		   //<연결하려고 하는 엔티티 클래스명, 프라이머리 키 타입>
public interface ItemRepository extends JpaRepository<Item, Long>, ItemRepositoryCustom{  
												
	// select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);   //findBy + 컬럼명
	
	//join 할 때는 반드시 컬럼명 옆에 별칭을 줘야되는데 별칭은 Dto 의 컬럼명과 동일하게 매칭
	@Query(value = "select data.num num, item.item_id id, item.item_nm itemNm, item.price price, item_img.img_url imgUrl, item_img.repimg_yn repimgYn \r\n"
			+ "            from item \r\n"
			+ "			inner join item_img on (item.item_id = item_img.item_id)\r\n"
			+ "			inner join (select @ROWNUM\\:=@ROWNUM+1 num, groupdata.* from\r\n"
			+ "			            (select order_item.item_id, count(*) cnt\r\n"
			+ "			              from order_item\r\n"
			+ "			              inner join orders on (order_item.order_id = orders.order_id)\r\n"
			+ "			              where orders.order_status = 'ORDER'\r\n"
			+ "			             group by order_item.item_id order by cnt desc) groupdata,\r\n"
			+ "                          (SELECT @ROWNUM\\:=0) R \r\n"
			+ "                          limit 6) data\r\n"
			+ "			on (item.item_id = data.item_id)\r\n"
			+ "			where item_img.repimg_yn = 'Y'\r\n"
			+ "			order by num", nativeQuery= true)
	List<ItemRankDto> getItemRankList();  
	
	
	
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