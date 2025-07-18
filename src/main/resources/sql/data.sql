INSERT INTO item (name, price) VALUES
('大葉', 100),
('小松菜', 120),
('キャベツ', 200),
('ほうれん草', 150);

INSERT INTO users (username, password, enabled) VALUES
('user', '{noop}password', true),
('admin', '{noop}adminpass', true);

INSERT INTO authorities (username, authority) VALUES
('user', 'ROLE_USER'),
('admin', 'ROLE_ADMIN');