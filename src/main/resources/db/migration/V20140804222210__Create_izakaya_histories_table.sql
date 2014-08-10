-- For H2 Database
create table izakaya_histories (
  id bigserial not null primary key,
  izakaya bigint not null,
  date date not null,
  created_at timestamp not null,
  updated_at timestamp not null
)
