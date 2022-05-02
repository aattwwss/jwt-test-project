set schema 'app';

insert into app_role (role)
values ('ADMIN'),
       ('USER');

insert into app_authority (authority)
values ('/ADMIN/READ'),
       ('/ADMIN/WRITE'),
       ('/ADMIN/UPDATE'),
       ('/ADMIN/DELETE'),
       ('/APP/DELETE'),
       ('/APP/WRITE'),
       ('/APP/UPDATE'),
       ('/APP/READ');

insert into app_role_authority (role_id, authority_id)
values (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (2, 5),
       (2, 6),
       (2, 7),
       (2, 8)