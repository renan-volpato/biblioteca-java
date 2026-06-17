# Sistema de Gerenciamento de Biblioteca

Projeto desenvolvido para a disciplina de **Programação Orientada a Objetos (POO)**, com o objetivo de aplicar os principais conceitos da orientação a objetos utilizando **Java**, **Swing** e **MySQL**.

## Funcionalidades

O sistema permite:

- Cadastro de Obras
  - Livros
  - Revistas
  - TCCs
- Cadastro de Leitores
- Cadastro de Funcionários
- Cadastro de Cópias
- Empréstimos
- Devoluções
- Reservas (estrutura implementada)

---

## Tecnologias Utilizadas

- Java
- Java Swing
- JDBC
- MySQL
- Maven
- Visual Studio Code

---

## Arquitetura do Projeto

O projeto foi organizado em camadas para facilitar a manutenção e organização do código.

```
src
└── br.com.biblioteca
    ├── model
    ├── dao
    ├── util
    └── view
```

- **Model:** classes que representam as entidades do sistema.
- **DAO:** acesso ao banco de dados utilizando JDBC.
- **View:** interface gráfica desenvolvida com Swing.
- **Util:** classes auxiliares, como conexão com o banco.

---

## Conceitos de Programação Orientada a Objetos

Durante o desenvolvimento foram aplicados diversos conceitos de POO.

### ✔ Herança

A classe abstrata `Obra` serve como base para:

- Livro
- Revista
- TCC

Assim, atributos comuns como título, autor, categoria e editora são herdados pelas classes filhas.

---

### Polimorfismo

O método abstrato:

```java
obterDetalhes()
```

é implementado de forma diferente em cada tipo de obra, permitindo comportamentos específicos para Livros, Revistas e TCCs.

---

### Associação

Relacionamentos entre classes foram utilizados para representar situações reais da biblioteca.

Exemplos:

- `Copia → Obra`
- `Reserva → Leitor`
- `Reserva → Obra`

---

### Agregação

A classe `Emprestimo` agrega outras entidades do sistema:

- Leitor
- Funcionário
- Cópia

Esses objetos continuam existindo mesmo que um empréstimo seja removido.

---

## Tratamento de Exceções

As exceções foram tratadas próximas à camada de interface (Swing), conforme solicitado no projeto.

Foram utilizadas:

- `try/catch`
- `SQLException`
- `IllegalArgumentException`
- `NumberFormatException`

As mensagens de erro são apresentadas ao usuário através do `JOptionPane`.

---

## Banco de Dados

O sistema utiliza MySQL para persistência dos dados.

Principais tabelas:

- obra
- livro
- revista
- tcc
- copia
- leitor
- funcionario
- emprestimo
- reserva

A comunicação com o banco é realizada utilizando JDBC e o padrão DAO.

---

## Como Executar

### Pré-requisitos

- Java JDK 17 ou superior
- MySQL
- Maven
- Visual Studio Code (ou outra IDE Java)

### Passos

1. Clone este repositório.

```bash
git clone https://github.com/renan-volpato/biblioteca-java.git
```

2. Crie o banco de dados MySQL.

3. Execute o script SQL localizado na pasta do projeto.

4. Configure os dados de conexão com o banco.

5. Execute a classe principal do projeto.

---

## Telas do Sistema

O sistema possui interface gráfica desenvolvida em Java Swing para:

- Cadastro de Obras
- Cadastro de Leitores
- Cadastro de Funcionários
- Cadastro de Cópias
- Empréstimos
- Devoluções

---

## Autor

**Renan Volpato**

Projeto desenvolvido para fins acadêmicos na disciplina de Programação Orientada a Objetos.
