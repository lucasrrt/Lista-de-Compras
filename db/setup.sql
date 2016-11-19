create sequence categorias_id_seq;
create table categorias(
	id integer not null default nextval('categorias_id_seq'),
	nome varchar(255),
	constraint categorias_id primary key (id)
);

create sequence produtos_id_seq;
create table produtos(
	id integer not null default nextval('produtos_id_seq'),
	nome varchar(255),
	descricao varchar(511),
	categoria_id integer,
	constraint produtos_categorias foreign key (categoria_id) references categorias (id),
	constraint produtos_id primary key (id)
);

create sequence usuarios_id_seq;
create table usuarios(
	id integer not null default nextval('usuarios_id_seq'),
	login varchar(255),
	senha varchar(255),
	constraint usuarios_id primary key (id)

);

create sequence compras_id_seq;
create table compras(
	id integer not null default nextval('compras_id_seq'),
	preco float,
	quantidade integer,
	mercado varchar(255),
	data date,
	produto_id integer,
	usuario_id integer,
	constraint compras_id primary key (id),
	constraint compras_produtos foreign key (produto_id) references produtos (id),
	constraint compras_usuarios foreign key (usuario_id) references usuarios (id)
);
