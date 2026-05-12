-- Tutores
insert into tutor (nome,cpf,email,telefone,data_cadastro) values ('Ana Lima','123.456.789-00','ana@email.com','11999990001',TO_DATE('2024-01-10','YYYY-MM-DD'));
insert into tutor (nome,cpf,email,telefone,data_cadastro) values ('Carlos Souza','987.654.321-00','carlos@email.com','11999990002',TO_DATE('2024-02-15','YYYY-MM-DD'));
insert into tutor (nome,cpf,email,telefone,data_cadastro) values ('Beatriz Melo','456.789.123-00','beatriz@email.com','11999990003',TO_DATE('2024-03-20','YYYY-MM-DD'));

-- Tipos de Cuidado
insert into tipo_cuidado (nome,descricao,intervalo_dias,prioridade) values ('Vacina V10','Vacina polivalente anual',365,'ALTA');
insert into tipo_cuidado (nome,descricao,intervalo_dias,prioridade) values ('Vermifugo','Vermifugacao preventiva',90,'MEDIA');
insert into tipo_cuidado (nome,descricao,intervalo_dias,prioridade) values ('Banho e Tosa','Higiene e estetica',30,'BAIXA');
insert into tipo_cuidado (nome,descricao,intervalo_dias,prioridade) values ('Antipulgas','Aplicacao antipulgas/carrapatos',60,'MEDIA');
insert into tipo_cuidado (nome,descricao,intervalo_dias,prioridade) values ('Consulta Geral','Check-up veterinario',180,'ALTA');

-- Pets (Oracle usa NUMBER(1) para boolean: 1=true, 0=false)
insert into pet (fk_tutor,nome,especie,raca,data_nascimento,peso,ativo) values (1,'Rex','CACHORRO','Labrador',TO_DATE('2020-05-10','YYYY-MM-DD'),28.5,1);
insert into pet (fk_tutor,nome,especie,raca,data_nascimento,peso,ativo) values (1,'Mia','GATO','Siames',TO_DATE('2021-08-22','YYYY-MM-DD'),4.2,1);
insert into pet (fk_tutor,nome,especie,raca,data_nascimento,peso,ativo) values (2,'Thor','CACHORRO','Bulldog',TO_DATE('2019-03-15','YYYY-MM-DD'),24.0,1);
insert into pet (fk_tutor,nome,especie,raca,data_nascimento,peso,ativo) values (3,'Luna','GATO','Persa',TO_DATE('2022-11-01','YYYY-MM-DD'),3.8,1);
insert into pet (fk_tutor,nome,especie,raca,data_nascimento,peso,ativo) values (2,'Bob','CACHORRO','Poodle',TO_DATE('2023-01-30','YYYY-MM-DD'),6.5,1);

-- Eventos de Cuidado
insert into evento_cuidado (fk_pet,fk_tipo_cuidado,data_prevista,data_realizada,status,observacao) values (1,1,TO_DATE('2024-05-10','YYYY-MM-DD'),TO_DATE('2024-05-10','YYYY-MM-DD'),'REALIZADO','Vacina aplicada sem intercorrencias');
insert into evento_cuidado (fk_pet,fk_tipo_cuidado,data_prevista,data_realizada,status,observacao) values (1,3,TO_DATE('2025-01-15','YYYY-MM-DD'),null,'ATRASADO','Aguardando agendamento');
insert into evento_cuidado (fk_pet,fk_tipo_cuidado,data_prevista,data_realizada,status,observacao) values (2,2,TO_DATE('2025-02-20','YYYY-MM-DD'),null,'PENDENTE','Proximo vermifugo');
insert into evento_cuidado (fk_pet,fk_tipo_cuidado,data_prevista,data_realizada,status,observacao) values (3,1,TO_DATE('2024-12-01','YYYY-MM-DD'),TO_DATE('2024-12-01','YYYY-MM-DD'),'REALIZADO','Check-up anual realizado');
insert into evento_cuidado (fk_pet,fk_tipo_cuidado,data_prevista,data_realizada,status,observacao) values (4,5,TO_DATE('2025-03-10','YYYY-MM-DD'),null,'PENDENTE','Primeira consulta geral');
insert into evento_cuidado (fk_pet,fk_tipo_cuidado,data_prevista,data_realizada,status,observacao) values (5,4,TO_DATE('2025-01-05','YYYY-MM-DD'),null,'ATRASADO','Antipulgas vencida');
insert into evento_cuidado (fk_pet,fk_tipo_cuidado,data_prevista,data_realizada,status,observacao) values (3,3,TO_DATE('2025-04-01','YYYY-MM-DD'),null,'PENDENTE','Banho agendado');

-- Usuario (login: RM1 | senha: senha)
insert into pessoa(nome,cpf,data_nascimento,email_pessoal) values('Estudante FIAP','111.444.777-35',TO_DATE('2002-05-05','YYYY-MM-DD'),'estudante@fiap.com');
insert into usuario(fk_pessoa,rm,senha,permissao,data_criacao,status) values(1,'RM1','$2a$12$Du8BqD6ZgbjJ5xly8/NJ3unipbl.WQlI2mNTtGs3VmzwujO2keUnu','USER',TO_DATE('2026-05-05','YYYY-MM-DD'),'ATIVO');
