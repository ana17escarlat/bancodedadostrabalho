
CREATE TABLE IF NOT EXISTS categorias (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS tarefas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    descricao VARCHAR(500),
    data_vencimento DATE,
    status VARCHAR(30) NOT NULL DEFAULT 'Pendente',
    categoria_id INT NOT NULL,
    FOREIGN KEY (categoria_id) REFERENCES categorias(id)
);

INSERT INTO categorias (nome)
SELECT 'Estudos'
WHERE NOT EXISTS (
    SELECT 1 FROM categorias WHERE nome = 'Estudos'
);

INSERT INTO categorias (nome)
SELECT 'Pessoal'
WHERE NOT EXISTS (
    SELECT 1 FROM categorias WHERE nome = 'Pessoal'
);

INSERT INTO categorias (nome)
SELECT 'Trabalho'
WHERE NOT EXISTS (
    SELECT 1 FROM categorias WHERE nome = 'Trabalho'
);
