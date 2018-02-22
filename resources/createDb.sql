create database zupdb;

use zupdb;

create table models
    (id int not null auto_increment,
    name varchar(15) not null unique,
    creationdate date,
    primarykey varchar(15),
    primary key(id)
    );
    
insert into models (name,creationdate,primarykey) values('adminunittest',sysdate(),'key1');


create table modelattributes
    (id int not null auto_increment,
    modelname varchar(15)  not null,
    attrname varchar(18),
    attrtype varchar(15),    
    primary key(id)
    );
    
insert into modelattributes (modelname, attrname,attrtype) values('adminunittest','key1','String');
insert into modelattributes (modelname, attrname,attrtype) values('adminunittest','key2','Int');


create table adminunittest
    (key1 varchar(10) not null,
    key2 int,
    primary key(key1)
    );
