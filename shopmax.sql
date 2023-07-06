create database shop;

use shop;

select * from member;
select * from item;
select * from item_img;
select * from orders;
select * from cart;

-- 데이터 내용 바꾸기
update member set role = 'ADMIN' where member_id = 1;

-- 자식테이블 부터 삭제하기
truncate table item_img;
set FOREIGN_KEY_CHECKS = 0;
truncate table item;
set FOREIGN_KEY_CHECKS = 1;

commit;