create table request_header(id int not null auto_increment,method varchar(20),domain_name varchar(1000),path varchar(1200),persistent_cookie varchar(100),session_cookie varchar(100),ip varchar(30),user_agent varchar(200),time datetime,primary key(id));
