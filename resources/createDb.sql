create database zupdb;

use zupdb;

create table models
    (id int not null auto_increment,
    name varchar(15) not null unique,
    creationdate date,
    primary key(id)
    );
    
insert into models (name,creationdate) values('Product',sysdate());    

select * from models;
delete from models;


create table modelattributes
    (id int not null auto_increment,
    modelname varchar(15)  not null,
    attrname varchar(18),
    attrtype varchar(15),
    primary key(id)
    );
    
insert into modelattributes (modelname, attrname,attrtype) values('Product','price','Decimal');
insert into modelattributes (modelname, attrname,attrtype) values('Product','quantity','Int');

select * from modelattributes;
delete from modelattributes where 1=1;

select * from XXX;
insert into XXX (name,price,quantity) values ('Diogo',10.2,10);

select * from Product;
insert into Product (name,price,quantity,url) values('TV', 150.0, 5,'tv.com.br');

select * from Price;