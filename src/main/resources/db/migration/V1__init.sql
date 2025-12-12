create table if not exists blog_post (
    id bigserial primary key,
    username varchar(80) not null,
    title varchar(200) not null,
    content text not null,
    created_at timestamp not null default now()
);
