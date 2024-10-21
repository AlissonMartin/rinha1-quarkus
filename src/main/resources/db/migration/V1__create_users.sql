CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE OR REPLACE FUNCTION createconcatsearch(_apelido VARCHAR, _nome VARCHAR, _stack TEXT[]) RETURNS TEXT AS $$ BEGIN RETURN _nome || ' ' || _apelido || ' ' || array_to_string(_stack, ' '); END;
$$ LANGUAGE plpgsql IMMUTABLE;

CREATE TABLE IF NOT EXISTS pessoas (
    id UUID PRIMARY KEY,
    apelido VARCHAR(255),
    nome VARCHAR(255),
    nascimento VARCHAR(255),
    stack TEXT[],
    concatsearch TEXT GENERATED ALWAYS AS (createconcatsearch(nome, apelido, stack)) STORED
);

CREATE INDEX IF NOT EXISTS idx_pessoas_searcheable ON pessoas USING gist (concatsearch gist_trgm_ops);
CREATE INDEX IF NOT EXISTS idx_pessoas_apelido ON pessoas (apelido);
