INSERT INTO users (id, username, password)
VALUES (1,'adminCM', '$2a$12$kfv6IHAZeoMJvqKCjaOCruqY71AQE8H3GaQFjdQFAYtw0g43utoeS');
-- Senha: admin123

INSERT INTO user_roles (user_id, roles)
VALUES (1, 'ROLE_ADMIN');