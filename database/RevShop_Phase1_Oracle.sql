– REVSHOP PHASE-1 COMPLETE ORACLE DATABASE SCRIPT – Run this file in
Oracle SQL Developer (same schema used by JDBC)

CREATE SEQUENCE users_seq START WITH 1 INCREMENT BY 1; CREATE SEQUENCE
products_seq START WITH 1 INCREMENT BY 1; CREATE SEQUENCE cart_seq START
WITH 1 INCREMENT BY 1; CREATE SEQUENCE orders_seq START WITH 1 INCREMENT
BY 1; CREATE SEQUENCE order_items_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE payments_seq START WITH 1 INCREMENT BY 1; CREATE
SEQUENCE wishlist_seq START WITH 1 INCREMENT BY 1; CREATE SEQUENCE
notifications_seq START WITH 1 INCREMENT BY 1; CREATE SEQUENCE
reviews_seq START WITH 1 INCREMENT BY 1; CREATE SEQUENCE invoices_seq
START WITH 1 INCREMENT BY 1;

CREATE TABLE users ( user_id NUMBER PRIMARY KEY, name VARCHAR2(100),
email VARCHAR2(100) UNIQUE NOT NULL, password VARCHAR2(256) NOT NULL,
role VARCHAR2(20) CHECK (role IN (‘BUYER’,‘SELLER’)), business_name
VARCHAR2(150), gstin VARCHAR2(50), created_at TIMESTAMP DEFAULT
CURRENT_TIMESTAMP );

CREATE TABLE products ( product_id NUMBER PRIMARY KEY, name
VARCHAR2(150) NOT NULL, category VARCHAR2(100), price NUMBER(10,2) NOT
NULL, stock NUMBER NOT NULL, seller_id NUMBER, CONSTRAINT
fk_product_seller FOREIGN KEY (seller_id) REFERENCES users(user_id) );

CREATE TABLE cart ( cart_id NUMBER PRIMARY KEY, user_id NUMBER,
product_id NUMBER, quantity NUMBER, CONSTRAINT fk_cart_user FOREIGN KEY
(user_id) REFERENCES users(user_id), CONSTRAINT fk_cart_product FOREIGN
KEY (product_id) REFERENCES products(product_id) );

CREATE TABLE orders ( order_id NUMBER PRIMARY KEY, user_id NUMBER,
total_amount NUMBER(12,2), status VARCHAR2(20) DEFAULT ‘PLACED’,
order_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, CONSTRAINT fk_order_user
FOREIGN KEY (user_id) REFERENCES users(user_id) );

CREATE TABLE order_items ( order_item_id NUMBER PRIMARY KEY, order_id
NUMBER, product_id NUMBER, quantity NUMBER, price NUMBER(10,2),
CONSTRAINT fk_oi_order FOREIGN KEY (order_id) REFERENCES
orders(order_id), CONSTRAINT fk_oi_product FOREIGN KEY (product_id)
REFERENCES products(product_id) );

CREATE TABLE payments ( payment_id NUMBER PRIMARY KEY, order_id NUMBER,
payment_method VARCHAR2(30), payment_status VARCHAR2(20), payment_date
TIMESTAMP DEFAULT CURRENT_TIMESTAMP, CONSTRAINT fk_payment_order FOREIGN
KEY (order_id) REFERENCES orders(order_id) );

CREATE TABLE wishlist ( wishlist_id NUMBER PRIMARY KEY, user_id NUMBER,
product_id NUMBER, CONSTRAINT fk_wishlist_user FOREIGN KEY (user_id)
REFERENCES users(user_id), CONSTRAINT fk_wishlist_product FOREIGN KEY
(product_id) REFERENCES products(product_id) );

CREATE TABLE notifications ( notification_id NUMBER PRIMARY KEY, user_id
NUMBER, message VARCHAR2(500), created_at TIMESTAMP DEFAULT
CURRENT_TIMESTAMP, CONSTRAINT fk_notification_user FOREIGN KEY (user_id)
REFERENCES users(user_id) );

CREATE TABLE reviews ( review_id NUMBER PRIMARY KEY, product_id NUMBER,
user_id NUMBER, rating NUMBER CHECK (rating BETWEEN 1 AND 5), feedback
VARCHAR2(500), review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT fk_review_product FOREIGN KEY (product_id) REFERENCES
products(product_id), CONSTRAINT fk_review_user FOREIGN KEY (user_id)
REFERENCES users(user_id) );

CREATE TABLE invoices ( invoice_id NUMBER PRIMARY KEY, order_id NUMBER,
generated_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, CONSTRAINT
fk_invoice_order FOREIGN KEY (order_id) REFERENCES orders(order_id) );
