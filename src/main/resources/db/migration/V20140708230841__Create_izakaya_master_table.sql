-- For H2 Database
create table izakaya_master (
  id bigserial not null primary key,
  name varchar(512) not null,
  category varchar(512) not null,
  priority bigint not null,
  address varchar(512) not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
