insert into usr(id, username, password, active)
    values (1, "admin", "$2a$08$rfmkINQ4BaPcT7RqAzu5UuO5aNlFDWoy0FKoHpKXORS2p4WZ0l626
$2a$08$rfmkINQ4BaPcT7RqAzu5UuO5aNlFDWoy0FKoHpKXORS2p4WZ0l626
", true);
insert into user_role (user_id, roles)
    values (1, "USER"), (1, "ADMIN");