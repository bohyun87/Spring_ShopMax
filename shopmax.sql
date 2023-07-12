create database shop;

use shop;

select * from member;
select * from item;
select * from item_img;
select * from orders;
select * from cart;
select * from order_item;

-- 데이터 내용 바꾸기
update member set role = 'ADMIN' where member_id = 1;

-- 자식테이블 부터 삭제하기
truncate table item_img;
set FOREIGN_KEY_CHECKS = 0;
truncate table item;
set FOREIGN_KEY_CHECKS = 1;

-- 참조
select @rownum:=@rownum+1, A.* from 테이블명 A,
(select @rownum:=0) r;

-- order 상태인 아이템들의 주문횟수(cnt)를 구한다.
select @rownum:=@rownum+1 num, groupdata.* from
(select order_item.item_id, count(*) cnt
from order_item
inner join orders
on (order_item.order_id = orders.order_id)
where orders.order_status = 'ORDER'
group by order_item.item_id order by cnt desc) groupdata,
(select @rownum:=0) r
limit 6;


-- 수량에 상관없이 주문횟수가 많은 아이템 조회
select data.num num, item.item_id id, item.item_nm itemNm, item.price price, item_img.img_url imgUrl, item_img.repimg_yn repimgYn 
            from item 
			inner join item_img on (item.item_id = item_img.item_id)
			inner join (select @ROWNUM\\:=@ROWNUM+1 num, groupdata.* from
			            (select order_item.item_id, count(*) cnt
			              from order_item
			              inner join orders on (order_item.order_id = orders.order_id)
			              where orders.order_status = 'ORDER'
			             group by order_item.item_id order by cnt desc) groupdata,
                          (SELECT @ROWNUM\\:=0) R 
                          limit 6) data
			on (item.item_id = data.item_id)
			where item_img.repimg_yn = 'Y'
			order by num;
            
            -- ; 은 지우고 이클립스에 복사

commit;
