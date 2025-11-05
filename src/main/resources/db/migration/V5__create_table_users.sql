CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE user_roles (
                            user_id INT NOT NULL,
                            roles VARCHAR(20) NOT NULL,
                            CONSTRAINT fk_user_roles FOREIGN KEY (user_id)
                                REFERENCES users (id) ON DELETE CASCADE
);
