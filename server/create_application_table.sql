create database springbootTodo default character set utf8;
CREATE USER 'springbootTodoUser'@'localhost' IDENTIFIED BY 'springbootTodoPassword';
grant all privileges on springbootTodo.* to 'springbootTodoUser'@'localhost';
flush privileges;

