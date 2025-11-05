CREATE TABLE branch (
                        id SERIAL PRIMARY KEY,
                        nome VARCHAR(255) NOT NULL,
                        bairro VARCHAR(255) NOT NULL,
                        cnpj VARCHAR(20) NOT NULL
);

CREATE TABLE patio (
                       id SERIAL PRIMARY KEY,
                       nome VARCHAR(255) NOT NULL,
                       branch_id INT NOT NULL,
                       CONSTRAINT fk_patio_branch FOREIGN KEY (branch_id)
                           REFERENCES branch (id) ON DELETE CASCADE
);

CREATE TABLE motorcycle (
                            id SERIAL PRIMARY KEY,
                            placa VARCHAR(7) NOT NULL UNIQUE,
                            chassi VARCHAR(17) NOT NULL UNIQUE,
                            numeracao_motor VARCHAR(17) NOT NULL,
                            motorcycle_models VARCHAR(255) NOT NULL,
                            patio_id INT,
                            situacao VARCHAR(255) NOT NULL,
                            CONSTRAINT fk_motorcycle_patio FOREIGN KEY (patio_id)
                                REFERENCES patio (id) ON DELETE SET NULL
);

CREATE TABLE motorcycle_groups (
                                   motorcycle_id INT NOT NULL,
                                   model VARCHAR(50) NOT NULL,
                                   CONSTRAINT fk_motorcycle_groups FOREIGN KEY (motorcycle_id)
                                       REFERENCES motorcycle (id) ON DELETE CASCADE
);

CREATE TABLE buzzer_log (
                            id SERIAL PRIMARY KEY,
                            acao VARCHAR(20) NOT NULL,
                            data_hora TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
                            ip_esp32 VARCHAR(45),
                            sucesso CHAR(1),
                            erro TEXT,
                            placa_moto VARCHAR(7)
);
