insert into parking_event(end_time, start_time, automobile_id, place_id)
values (null, current_timestamp, 1, 1),
       (null, current_timestamp, 2, 2),
       (null, current_timestamp, 3, 3),
       (DATEADD(day , -2, current_timestamp), DATEADD(day, -3, current_timestamp), 1, 2),
       (DATEADD(day , -2, current_timestamp), DATEADD(day, -4, current_timestamp), 2, 3),
       (DATEADD(day , -3, current_timestamp), DATEADD(day, -5, current_timestamp), 3, 1);