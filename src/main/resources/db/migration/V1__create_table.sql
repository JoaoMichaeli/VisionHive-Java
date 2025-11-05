CREATE TABLE branch (
                        id INT IDENTITY(1,1) PRIMARY KEY,
                        nome NVARCHAR(255) NOT NULL,
                        bairro NVARCHAR(255) NOT NULL,
                        cnpj NVARCHAR(20) NOT NULL
);

CREATE TABLE patio (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       nome NVARCHAR(255) NOT NULL,
                       branch_id INT NOT NULL,
                       CONSTRAINT fk_patio_branch FOREIGN KEY (branch_id)
                           REFERENCES branch (id) ON DELETE CASCADE
);

CREATE TABLE motorcycle (
                            id INT IDENTITY(1,1) PRIMARY KEY,
                            placa NVARCHAR(7) NOT NULL UNIQUE,
                            chassi NVARCHAR(17) NOT NULL UNIQUE,
                            numeracao_motor NVARCHAR(17) NOT NULL,
                            motorcycle_models NVARCHAR(255) NOT NULL,
                            patio_id INT,
                            situacao NVARCHAR(255) NOT NULL,
                            CONSTRAINT fk_motorcycle_patio FOREIGN KEY (patio_id)
                                REFERENCES patio (id) ON DELETE SET NULL
);

CREATE TABLE motorcycle_groups (
                                   motorcycle_id INT NOT NULL,
                                   model NVARCHAR(50) NOT NULL,
                                   CONSTRAINT fk_motorcycle_groups FOREIGN KEY (motorcycle_id)
                                       REFERENCES motorcycle (id) ON DELETE CASCADE
);





CREATE TABLE buzzer_log (
                            id INT IDENTITY(1,1) PRIMARY KEY,
                            acao NVARCHAR(20) NOT NULL,
                            data_hora DATETIME2 DEFAULT CURRENT_TIMESTAMP NOT NULL,
                            ip_esp32 NVARCHAR(45),
                            sucesso CHAR(1),
                            erro NVARCHAR(MAX),
                            placa_moto NVARCHAR(7)
);