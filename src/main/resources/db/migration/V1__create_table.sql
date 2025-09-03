CREATE TABLE branch (
                        id BIGSERIAL PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL,
                        bairro VARCHAR(255) NOT NULL,
                        cnpj VARCHAR(20) NOT NULL
);

CREATE TABLE patio (
                       id BIGSERIAL PRIMARY KEY,
                       nome VARCHAR(255) NOT NULL,
                       branch_id BIGINT NOT NULL,
                       CONSTRAINT fk_patio_branch FOREIGN KEY (branch_id) REFERENCES branch (id) ON DELETE CASCADE
);

CREATE TABLE motorcycle (
                            id BIGSERIAL PRIMARY KEY,
                            placa VARCHAR(7) NOT NULL UNIQUE,
                            chassi VARCHAR(17) NOT NULL UNIQUE,
                            numeracao_motor VARCHAR(17) NOT NULL,
                            motorcycle_models VARCHAR(255) NOT NULL,
                            patio_id BIGINT,
                            situacao VARCHAR(255) NOT NULL,
                            CONSTRAINT fk_motorcycle_patio FOREIGN KEY (patio_id) REFERENCES patio (id) ON DELETE SET NULL
);

CREATE TABLE motorcycle_groups (
                                   motorcycle_id BIGINT NOT NULL,
                                   model VARCHAR(50) NOT NULL,
                                   CONSTRAINT fk_motorcycle_groups FOREIGN KEY (motorcycle_id) REFERENCES motorcycle (id) ON DELETE CASCADE
);
