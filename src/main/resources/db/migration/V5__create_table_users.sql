CREATE TABLE users (
                       id INT IDENTITY(1,1) PRIMARY KEY,
                       username NVARCHAR(50) NOT NULL UNIQUE,
                       password NVARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            roles NVARCHAR(20) NOT NULL,
                            CONSTRAINT fk_user_roles FOREIGN KEY (user_id)
                                REFERENCES users (id)
);
