INSERT INTO ticket_event(dates,userName,price,numTicket,email,discount) VALUES 
('2022-12-25','Pratik',30.0,5,'pratiksakhiya@gmail.com',true),
('2022-12-25','Kalp',40.0,1,'kalp@gmail.com',false),
('2022-12-25','Utsav',30.0,5,'utsav@gmail.com',false),
('2022-12-14','Jaysheel',50.0,11,'jaysheel@gmail.com',true),
('2022-12-14','Utsav',30.0,1,'utsav@gmail.com',false),
('2022-12-14','Svayam',50.0,1,'svayam@gmail.com',false),
('2022-12-25','Pratik',50.0,8,'pratiksakhiya@gmail.com',true),
('2022-12-25','Utsav',40.0,6,'utsav@gmail.com',true),
('2022-12-14','Kalp',50.0,2,'kalp@gmail.com',false),
('2022-12-14','Jaysheel',40.0,3,'jaysheel@gmail.com',true);

-- 1 data of VENDOR ROLE
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Jon', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

-- 5 data of GUEST ROlE
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Pratik', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Utsav', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Svayam', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Kalp', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);
insert into SEC_User (userName, encryptedPassword, ENABLED)
values ('Jaysheel', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);

insert into sec_role (roleName)
values ('ROLE_GUEST');
 
insert into sec_role (roleName)
values ('ROLE_VENDOR');


--insert into user_role (userId, roleId)
--values (1, 1);
-- 
--insert into user_role (userId, roleId)
--values (1, 2);

insert into user_role (userId, roleId)
values (1, 2);

insert into user_role (userId, roleId)
values (2, 1);
insert into user_role (userId, roleId)
values (3, 1);
insert into user_role (userId, roleId)
values (4, 1);
insert into user_role (userId, roleId)
values (5, 1);
insert into user_role (userId, roleId)
values (6, 1);
