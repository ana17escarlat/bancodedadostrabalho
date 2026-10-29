# OrganizaTarefas

Sistema de gerenciamento de tarefas desenvolvido em Java e MySQL para a Atividade Final de Projeto de Banco de Dados.

## Objetivo

O OrganizaTarefas permite cadastrar, visualizar, editar e excluir tarefas, organizando-as por categorias e permitindo acompanhar seus status.

## Tecnologias utilizadas

- Java
- Spring Boot
- Thymeleaf
- HTML
- CSS
- MySQL
- JDBC

## Funcionalidades

- Cadastro de tarefas com título, descrição, data de vencimento e categoria.
- Listagem das tarefas cadastradas.
- Edição das informações e do status das tarefas.
- Exclusão de tarefas.
- Organização por categorias.
- Contagem do total de tarefas cadastradas.
- Consulta SQL com `JOIN` para relacionar tarefas e categorias.

## Banco de dados

O banco utiliza duas tabelas relacionadas:

- `categorias`: armazena as categorias das tarefas.
- `tarefas`: armazena as informações das tarefas e possui uma chave estrangeira que referencia `categorias`.

O projeto utiliza comandos SQL de inserção, consulta, atualização e exclusão, além de consultas com `JOIN` e `COUNT(*)`.

## Estrutura do projeto

- `src/main/java`: código Java da aplicação.
- `src/main/resources/templates`: página HTML.
- `src/main/resources/static/css`: arquivo CSS.
- `src/main/resources/schema.sql`: criação e inicialização das tabelas.
- `src/main/resources/application.properties`: configurações da aplicação e da conexão com o banco de dados.

## Projeto acadêmico

Projeto desenvolvido para a Atividade Final de Projeto de Banco de Dados.