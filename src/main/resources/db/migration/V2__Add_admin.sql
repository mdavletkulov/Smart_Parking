insert into usr (id, username, password, enabled, first_name, second_name, middle_name, role)
    values (1, 'm160598@gmail.com', '123', 1, N'Максим', N'Давлеткулов', N'Артурович', 'ADMIN');
alter sequence hibernate_sequence restart with 2;