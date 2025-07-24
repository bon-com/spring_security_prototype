INSERT INTO item (name, price, deleted) VALUES
('大葉', 100, false),
('小松菜', 120, false),
('キャベツ', 200, false),
('ほうれん草', 150, false);

-- ※BCryptで暗号化済み（user01:passowrd, admin01:adminpass）
INSERT INTO users (login_id, username, password, enabled, account_non_locked, login_failure_count, last_login_at, account_expiry_at, password_expiry_at) VALUES
('user01', '山田太郎', '$2a$10$w3sqJwreV8PfAHjID.TES.4ZjAZ1uMnJWIE9EQiC32d2h51nmyrhy', true, true, 0, NULL, '2025-12-31 23:59:59', '2025-07-27 23:59:59'), 
('admin01', '管理者', '$2a$10$W31Sy.1CEW.zYy1pBz4J5uc1uYzq8ItjeG9U0pzfGIpgiNV1Gaa3O', true, true, 0, NULL, '9999-12-31 23:59:59', '9999-12-31 23:59:59');

INSERT INTO authorities (login_id, authority) VALUES
('user01', 'ROLE_USER'),
('admin01', 'ROLE_ADMIN');