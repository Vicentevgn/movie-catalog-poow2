-- Tabela de Usuários para Autenticação
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    uuid UUID NOT NULL UNIQUE,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

-- Tabela de Gêneros
CREATE TABLE genero (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de Atores
CREATE TABLE ator (
    id BIGSERIAL PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    biografia TEXT
);

-- Tabela de Filmes
CREATE TABLE filme (
    id BIGSERIAL PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    sinopse TEXT,
    ano_lancamento INTEGER
);

-- Tabela de Junção N:M entre Filme e Gênero
CREATE TABLE filme_genero (
    id BIGSERIAL PRIMARY KEY,
    filme_id BIGINT NOT NULL,
    genero_id BIGINT NOT NULL,
    FOREIGN KEY (filme_id) REFERENCES filme (id) ON DELETE CASCADE,
    FOREIGN KEY (genero_id) REFERENCES genero (id) ON DELETE CASCADE,
    UNIQUE (filme_id, genero_id)
);

-- Tabela de Junção N:M entre Filme e Ator (com atributo extra 'papel')
CREATE TABLE filme_ator (
    id BIGSERIAL PRIMARY KEY,
    filme_id BIGINT NOT NULL,
    ator_id BIGINT NOT NULL,
    papel VARCHAR(255) NOT NULL,
    FOREIGN KEY (filme_id) REFERENCES filme (id) ON DELETE CASCADE,
    FOREIGN KEY (ator_id) REFERENCES ator (id) ON DELETE CASCADE,
    UNIQUE (filme_id, ator_id)
);
