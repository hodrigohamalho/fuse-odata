DROP SCHEMA IF EXISTS RED CASCADE;
CREATE SCHEMA RED;

CREATE SEQUENCE RED.TERCEIRO_SEQ START WITH 1;
DROP TABLE IF EXISTS RED.TB_TERCEIRO;

CREATE TABLE funcionario (
  ID bigint auto_increment,
  NOME varchar(255) default NULL,
  SOBRENOME varchar(255) default NULL
);

insert into funcionario(nome, sobrenome) values('Rodrigo', 'Ramalho');
insert into funcionario(nome, sobrenome) values('Rafael', 'Ramalho');

