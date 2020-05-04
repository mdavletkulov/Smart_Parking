insert into usr (id, username, email, password, enabled)
    values (1,'admin', 'm160598@gmail.com', '123', 1);
insert into user_role(user_id, roles)
    values (1, 'USER'), (1, 'ADMIN');
alter sequence hibernate_sequence restart with 2;