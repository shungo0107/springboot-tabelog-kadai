-- rolesテーブル
INSERT IGNORE INTO roles (id, name) VALUES (1, 'ROLE_GENERAL');
INSERT IGNORE INTO roles (id, name) VALUES (2, 'ROLE_PAID');
INSERT IGNORE INTO roles (id, name) VALUES (3, 'ROLE_ADMIN');

-- categoriesテーブル
INSERT IGNORE INTO categories (id, name) VALUES (1,'和食');
INSERT IGNORE INTO categories (id, name) VALUES (2,'おでん');
INSERT IGNORE INTO categories (id, name) VALUES (3,'定食');
INSERT IGNORE INTO categories (id, name) VALUES (4,'焼き鳥');
INSERT IGNORE INTO categories (id, name) VALUES (5,'焼肉');
INSERT IGNORE INTO categories (id, name) VALUES (6,'中華');
INSERT IGNORE INTO categories (id, name) VALUES (7,'ラーメン');
INSERT IGNORE INTO categories (id, name) VALUES (8,'うどん');
INSERT IGNORE INTO categories (id, name) VALUES (9,'そば');
INSERT IGNORE INTO categories (id, name) VALUES (10,'カレー');
INSERT IGNORE INTO categories (id, name) VALUES (11,'ハンバーガー');
INSERT IGNORE INTO categories (id, name) VALUES (12,'お好み焼き');
INSERT IGNORE INTO categories (id, name) VALUES (13,'たこ焼き');
INSERT IGNORE INTO categories (id, name) VALUES (14,'パン');
INSERT IGNORE INTO categories (id, name) VALUES (15,'カフェ');
INSERT IGNORE INTO categories (id, name) VALUES (16,'スイーツ');


--usersテーブル
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (1, '侍 太郎', 'サムライ タロウ',  'taro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (2, '侍 花子', 'サムライ ハナコ',  'hanako.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 3, true);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (3, '侍 義勝', 'サムライ ヨシカツ',  'yoshikatsu.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 2, true);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (4, '侍 幸美', 'サムライ サチミ',  'sachimi.samurai@example.com', 'password', 1, false);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (5, '侍 一郎', 'サムライ イチロウ',  'ichiro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (6, '侍 次郎', 'サムライ ジロウ',  'ziro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 3, true);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (7, '侍 三郎', 'サムライ サブロウ',  'saburo.samurai@example.com', 'password', 2, false);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (8, '侍 四郎', 'サムライ シロウ',  'shiro.samurai@example.com', 'password', 1, false);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (9, '侍 五郎', 'サムライ ゴロウ',  'goro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 1, true);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (10, '侍 六郎', 'サムライ ロクロウ',  'rokuro.samurai@example.com', '$2a$10$2JNjTwZBwo7fprL2X4sv.OEKqxnVtsVQvuXDkI8xVGix.U3W5B7CO', 3, true);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (11, '侍 七郎', 'サムライ ナナロウ',  'nanaro.samurai@example.com', 'password', 2, false);
 INSERT IGNORE INTO users (id, name, furigana, email, password, role_id, enabled) VALUES (12, '侍 八郎', 'サムライ ハチロウ',  'hachiro.samurai@example.com', 'password', 1, false);
 
 
 --restaurantsテーブル
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (1,1,'割烹NAGOYA','washoku.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',7000,39,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (2,4,'炭火串焼NAGOYA','yakitori.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',3000,89,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (3,5,'焼肉NAGOYA','yakiniku.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1900,41,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (4,2,'割烹NAGOYA２','oden.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',2000,51,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (5,7,'NAGOYAラーメン','ramen.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1100,31,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (6,5,'やきにく NAGOYA','yakiniku.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',2100,91,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (7,7,'つけ麺NAGOYA','tsukemen.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1200,65,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (8,4,'炭火やきとり マメ','yakitori.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',3000,63,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (9,12,'お好み焼きNAGOYA','okonomiyaki.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',4000,69,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (10,11,'NAGOYAバーガー','burger.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',5000,63,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (11,1,'NAGOYAおでん','washoku.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',8000,20,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (12,3,'NAGOYA定食','teishoku.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',8000,66,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (13,9,'NAGOYAそば','soba.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1300,7,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (14,13,'たこ焼きNAGOYA','takoyaki.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1000,41,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (15,10,'NAGOYAカレー','curry.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1400,45,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (16,8,'NAGOYAうどん','udon.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1500,49,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (17,14,'NAGOYAパン','bread.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1500,74,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (18,15,'喫茶NAGOYA','cafe.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1600,82,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (19,16,'甘味NAGOYA','sweets.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1700,54,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');
INSERT IGNORE INTO restaurants (id, category_id ,name, image_name, description, price, capacity, postal_code, address, phone_number) VALUES (20,6,'中華NAGOYA','tyuuka.jpg','名古屋老舗のお店。老舗の味をご堪能ください。',1800,66,'333-3333','愛知県名古屋市中区栄X-XX-XX','025-123-4567');

 
 --reservations テーブル
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (1, 1, 1, '2023-04-01', 2, 6000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (2, 2, 1, '2023-04-01', 3, 7000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (3, 3, 1, '2023-04-01', 4, 8000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (4, 4, 1, '2023-04-01', 5, 9000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (5, 5, 1, '2023-04-01', 6, 10000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (6, 6, 1, '2023-04-01', 2, 6000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (7, 7, 1, '2023-04-01', 3, 7000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (8, 8, 1, '2023-04-01', 4, 8000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (9, 9, 1, '2023-04-01', 5, 9000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (10, 10, 1, '2023-04-01', 6, 10000);
INSERT IGNORE INTO reservations (id, restaurant_id, user_id, reserve_date, number_of_people, amount) VALUES (11, 11, 1, '2023-04-01', 2, 6000);
 
 
  -- reviewテーブル
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (1, 1, 1, 1, '店員の接客態度があまりよくない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (2, 1, 2, 2, '好みの味ではない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (3, 1, 3, 3, 'おいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (4, 1, 4, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (5, 2, 1, 1, '店員の接客態度があまりよくない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (6, 2, 2, 2, '好みの味ではない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (7, 2, 3, 3, 'おいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (8, 2, 4, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (9, 3, 1, 5, '店員の接客態度があまりよくない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (10, 3, 2, 1, '好みの味ではない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (11, 3, 3, 2, 'おいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (12, 3, 4, 4, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (13, 4, 1, 2, '店員の接客態度があまりよくない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (14, 4, 2, 5, '好みの味ではない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (15, 4, 3, 1, 'おいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (16, 4, 4, 4, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (17, 5, 1, 3, '店員の接客態度があまりよくない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (18, 5, 2, 3, '好みの味ではない');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (19, 5, 3, 3, 'おいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (20, 5, 4, 3, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (21, 1, 5, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (22, 1, 6, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (23, 1, 7, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (24, 1, 8, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (25, 1, 9, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (26, 1, 10, 5, 'とてもおいしかった');
INSERT IGNORE INTO reviews (id, restaurant_id, user_id, assessment, text_comment) VALUES (27, 1, 11, 5, 'とてもおいしかった');
 -- favoriteテーブル
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (1, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (2, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (3, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (4, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (5, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (6, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (7, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (8, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (9, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (10, 1);
INSERT IGNORE INTO favorites ( restaurant_id, user_id) VALUES (11, 1);
 