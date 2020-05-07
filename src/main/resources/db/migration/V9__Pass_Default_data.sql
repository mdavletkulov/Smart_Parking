insert into division (name)
values (N'ИнЭТС'),
       (N'ИММ'),
       (N'ИнЭТиП'),
       (N'ИнПИТ'),
       (N'ФТИ'),
       (N'УРБАС'),
       (N'ИСПМ');

insert into color (name)
values (N'Красный'),
       (N'Синий'),
       (N'Белый'),
       (N'Серый'),
       (N'Черный'),
       (N'Желтый'),
       (N'Коричневый'),
       (N'Оранжевый');

insert into job_position(name_position, type_job_position)
values (N'Заведующий кафедрой', N'ППС'),
       (N'Профессор', N'ППС'),
       (N'Доцент', N'ППС'),
       (N'Ассистент', N'ППС'),
       (N'Старший преподаватель', N'ППС'),
       (N'Президент университета', N'АУП'),
       (N'Первый проректор', N'АУП'),
       (N'Ректор', N'АУП');

insert into parking(description, place_number)
values (N'Парковка у первого корпуса', 4);
