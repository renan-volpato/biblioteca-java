-- ==========================================
-- CRIAÇÃO DO BANCO DE DADOS
-- ==========================================

DROP DATABASE IF EXISTS biblioteca;
CREATE DATABASE biblioteca;
USE biblioteca;

-- ==========================================
-- TABELA OBRA
-- ==========================================

CREATE TABLE obra (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100) NOT NULL,
    ano INT NOT NULL,
    categoria VARCHAR(50),
    editora VARCHAR(100)
);

-- ==========================================
-- TABELA LIVRO
-- ==========================================

CREATE TABLE livro (
    id INT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL,
    id_obra INT NOT NULL UNIQUE,

    CONSTRAINT fk_livro_obra
        FOREIGN KEY (id_obra)
        REFERENCES obra(id)
        ON DELETE CASCADE
);

-- ==========================================
-- TABELA REVISTA
-- ==========================================

CREATE TABLE revista (
    id INT AUTO_INCREMENT PRIMARY KEY,
    edicao INT NOT NULL,
    id_obra INT NOT NULL UNIQUE,

    CONSTRAINT fk_revista_obra
        FOREIGN KEY (id_obra)
        REFERENCES obra(id)
        ON DELETE CASCADE
);

-- ==========================================
-- TABELA TCC
-- ==========================================

CREATE TABLE tcc (
    id INT AUTO_INCREMENT PRIMARY KEY,
    curso VARCHAR(100) NOT NULL,
    id_obra INT NOT NULL UNIQUE,

    CONSTRAINT fk_tcc_obra
        FOREIGN KEY (id_obra)
        REFERENCES obra(id)
        ON DELETE CASCADE
);

-- ==========================================
-- TABELA LEITOR
-- ==========================================

CREATE TABLE leitor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100),
    limite_emprestimos INT DEFAULT 3
);

-- ==========================================
-- TABELA FUNCIONARIO
-- ==========================================

CREATE TABLE funcionario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) NOT NULL UNIQUE,
    telefone VARCHAR(20),
    email VARCHAR(100),
    cargo VARCHAR(50) NOT NULL,
    matricula VARCHAR(20)
);

-- ==========================================
-- TABELA COPIA
-- ==========================================

CREATE TABLE copia (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    status VARCHAR(20) NOT NULL,
    localizacao VARCHAR(50),

    id_obra INT NOT NULL,

    CONSTRAINT fk_copia_obra
        FOREIGN KEY (id_obra)
        REFERENCES obra(id)
        ON DELETE CASCADE
);

-- ==========================================
-- TABELA EMPRESTIMO
-- ==========================================

CREATE TABLE emprestimo (
    id INT AUTO_INCREMENT PRIMARY KEY,

    data_emprestimo DATE NOT NULL,
    data_prevista_devolucao DATE NOT NULL,
    data_devolucao DATE,

    status VARCHAR(20) NOT NULL,

    id_leitor INT NOT NULL,
    id_funcionario INT NOT NULL,
    id_copia INT NOT NULL,

    CONSTRAINT fk_emprestimo_leitor
        FOREIGN KEY (id_leitor)
        REFERENCES leitor(id),

    CONSTRAINT fk_emprestimo_funcionario
        FOREIGN KEY (id_funcionario)
        REFERENCES funcionario(id),

    CONSTRAINT fk_emprestimo_copia
        FOREIGN KEY (id_copia)
        REFERENCES copia(id)
);

-- ==========================================
-- TABELA RESERVA
-- ==========================================

CREATE TABLE reserva (
    id INT AUTO_INCREMENT PRIMARY KEY,

    data_reserva DATE NOT NULL,
    status VARCHAR(20) NOT NULL,

    id_leitor INT NOT NULL,
    id_obra INT NOT NULL,

    CONSTRAINT fk_reserva_leitor
        FOREIGN KEY (id_leitor)
        REFERENCES leitor(id),

    CONSTRAINT fk_reserva_obra
        FOREIGN KEY (id_obra)
        REFERENCES obra(id)
);