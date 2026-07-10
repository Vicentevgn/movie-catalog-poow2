# 🎬 Movie Catalog

Sistema web para gerenciamento de um catálogo de filmes, desenvolvido com **Java**, **Spring Boot**, **Servlets** e **JSP**. A aplicação permite administrar filmes, atores e gêneros por meio de uma interface web integrada a um backend responsável pelas regras de negócio e persistência dos dados.

O projeto foi desenvolvido com o objetivo de consolidar conhecimentos em desenvolvimento web com Java, aplicando conceitos de arquitetura em camadas, persistência de dados e boas práticas de desenvolvimento.

---

## ✨ Funcionalidades

- 🎬 Cadastro de filmes
- 📋 Listagem de filmes
- ✏️ Edição de filmes
- 🗑️ Remoção de filmes
- 🎭 Cadastro de atores
- 👥 Listagem de atores
- ✏️ Edição de atores
- 🗑️ Remoção de atores
- 🎞️ Cadastro de gêneros
- 🗂️ Listagem de gêneros
- ✏️ Edição de gêneros
- 🗑️ Remoção de gêneros
- 🔗 Associação de atores e gêneros aos filmes
- ✔️ Validação de formulários

---

## 🛠️ Tecnologias

- Java
- Spring Boot
- Servlets
- JSP
- Spring Data JPA
- Hibernate
- Maven
- MySQL
- HTML5
- CSS3
- Bootstrap

---

## 📁 Estrutura do Projeto

```text
src/
├── main/
│   ├── java/
│   │   ├── controller/
│   │   ├── service/
│   │   ├── repository/
│   │   ├── model/
│   │   └── config/
│   ├── resources/
│   └── webapp/
└── test/
```

A aplicação foi organizada seguindo uma arquitetura em camadas, separando responsabilidades entre controladores, serviços, repositórios e entidades, facilitando a manutenção e evolução do sistema.

---

## 🚀 Como executar

### Pré-requisitos

- Java 17 (ou versão utilizada no projeto)
- Maven
- MySQL

### Clone o repositório

```bash
git clone https://github.com/Vicentevgn/movie-catalog-poow2.git

cd movie-catalog-poow2
```

### Configure o banco de dados

Crie um banco MySQL e ajuste as credenciais no arquivo `application.properties`.

### Execute o projeto

```bash
mvn spring-boot:run
```

Após iniciar a aplicação, acesse:

```
http://localhost:8080
```

---

## 📚 Conceitos aplicados

- Programação Orientada a Objetos
- Arquitetura MVC
- Spring Boot
- Persistência de dados com JPA/Hibernate
- Injeção de Dependências
- Mapeamento Objeto-Relacional (ORM)
- Desenvolvimento Web com Servlets e JSP
- Organização em camadas
- CRUD completo

---

## 👨‍💻 Autor

Desenvolvido por **Vicente Nascimento**.
