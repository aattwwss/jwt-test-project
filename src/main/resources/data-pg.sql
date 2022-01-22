set schema 'test';

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