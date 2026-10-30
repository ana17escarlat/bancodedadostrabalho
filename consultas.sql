-- 1. JOIN: tarefas com suas categorias
CREATE OR REPLACE VIEW vw_tarefas_categoria AS
SELECT
    t.id,
    t.titulo,
    t.descricao,
    t.data_vencimento,
    t.status,
    t.categoria_id,
    c.nome AS categoria
FROM tarefas t
INNER JOIN categorias c
    ON t.categoria_id = c.id;


-- 2. COUNT: quantidade de tarefas por status
CREATE OR REPLACE VIEW vw_tarefas_status AS
SELECT
    status,
    COUNT(*) AS quantidade
FROM tarefas
GROUP BY status;


-- 3. SUM: total de tarefas por categoria
CREATE OR REPLACE VIEW vw_total_categoria AS
SELECT
    SUM(quantidade) AS total_tarefas
FROM (
    SELECT COUNT(t.id) AS quantidade
    FROM categorias c
    LEFT JOIN tarefas t
        ON c.id = t.categoria_id
    GROUP BY c.id
) AS resumo;


-- 4. AVG: média de tarefas por categoria
CREATE OR REPLACE VIEW vw_media_categoria AS
SELECT
    AVG(quantidade) AS media_tarefas
FROM (
    SELECT COUNT(t.id) AS quantidade
    FROM categorias c
    LEFT JOIN tarefas t
        ON c.id = t.categoria_id
    GROUP BY c.id
) AS resumo;