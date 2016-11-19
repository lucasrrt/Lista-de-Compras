insert into categorias(nome) values ('alimentos');
insert into categorias(nome) values ('bebidas');
insert into categorias(nome) values ('higiene');
insert into categorias(nome) values ('limpeza');

insert into produtos(nome,categoria_id) values ('margarina',1);
insert into produtos(nome,categoria_id) values ('catchup',1);
insert into produtos(nome,categoria_id) values ('sopa',1);
insert into produtos(nome,categoria_id) values ('sabonete',3);
insert into produtos(nome,categoria_id) values ('inseticida',4);
insert into produtos(nome,categoria_id) values ('amaciante',4);
insert into produtos(nome,categoria_id) values ('cerveja',2);
insert into produtos(nome,categoria_id) values ('suco',2);
insert into produtos(nome,categoria_id) values ('leite',2);
insert into produtos(nome,categoria_id) values ('açucar',1);
insert into produtos(nome,categoria_id) values ('shampoo',3);
insert into produtos(nome,categoria_id) values ('creme dental',3);
insert into produtos(nome,categoria_id) values ('detergente',4);


insert into usuarios(login,senha) values ('brunovcosta','1234');
insert into usuarios(login,senha) values ('lucasrrt','justinbieber');
insert into usuarios(login,senha) values ('tnmarchiore','mariliaS2');
insert into usuarios(login,senha) values ('eduardocandido6','chocolate');
insert into usuarios(login,senha) values ('daviamorim','lucasrrt');

insert into compras(preco,quantidade,mercado,data,usuario_id,produto_id) values
(13,2,'pda','2016-12-23 22:12',1,10);
insert into compras(preco,quantidade,mercado,data,usuario_id,produto_id) values
(11,4,'pda','2016-12-23 22:12',3,8);
insert into compras(preco,quantidade,mercado,data,usuario_id,produto_id) values
(8,3,'pda','2016-12-23 22:12',3,6);
insert into compras(preco,quantidade,mercado,data,usuario_id,produto_id) values
(9,1,'pda','2016-12-23 22:12',2,1);
insert into compras(preco,quantidade,mercado,data,usuario_id,produto_id) values
(7,3,'pda','2016-12-23 22:12',5,3);
insert into compras(preco,quantidade,mercado,data,usuario_id,produto_id) values
(8,3,'pda','2016-12-23 22:12',2,1);
