create database zupdb;

use zupdb;

create table models
    (id int not null auto_increment,
    name varchar(15) not null unique,
    creationdate date,
    primarykey varchar(15),
    primary key(id)
    );
    
insert into models (name,creationdate,primarykey) values('Product',sysdate(),'sku');    
/*
{
    "modelName":"Product",
    "attributes":{
        "sku":"String",
        "description":"String",
        "price":"Double",
        "quantity":"Int"
    },
    "primarykey":"sku"
}
*/

select * from models;
insert into models (name,creationdate,primarykey) values('adminunittest',sysdate(),'key1');
delete from models where name = 'TestTable';
drop table models;


create table modelattributes
    (id int not null auto_increment,
    modelname varchar(15)  not null,
    attrname varchar(18),
    attrtype varchar(15),    
    primary key(id)
    );
    
insert into modelattributes (modelname, attrname,attrtype) values('adminunittest','key1','String');
insert into modelattributes (modelname, attrname,attrtype) values('adminunittest','key2','Int');
insert into modelattributes (modelname, attrname,attrtype) values('Product','price','Double');
insert into modelattributes (modelname, attrname,attrtype) values('Product','quantity','Int');
drop table modelattributes;

select * from modelattributes;
delete from modelattributes where 1=1;

create table adminunittest
    (key1 varchar(10) not null,
    key2 int,
    primary key(key1)
    );
    
select * from adminunittest;    
delete from adminunittest;    

select * from Product;
insert into Product (sku,description,price,quantity) values('123','TV',1000.0, 5);
insert into Product (sku,description,price,quantity) values('1234','TV 3D',1000.75, 10);
drop table Product;

select * from Price;
drop table Price;

select * from Person;

select * from TestTable;
drop table TestTable;

update Person set (name,age,dateofbirth) values ('',10,'1991-06-06');