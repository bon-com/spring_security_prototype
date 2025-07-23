INSERT INTO item (name, price, deleted) VALUES
('大葉', 100, false),
('小松菜', 120, false),
('キャベツ', 200, false),
('ほうれん草', 150, false);

-- 例（BCryptで暗号化済み）
INSERT INTO users (login_id, username, password, enabled, account_non_locked, login_failure_count, last_login_at) VALUES
('user01', '山田太郎', '$2a$10$w3sqJwreV8PfAHjID.TES.4ZjAZ1uMnJWIE9EQiC32d2h51nmyrhy', true, true, 0, NULL), -- password
('admin01', '管理者', '$2a$10$W31Sy.1CEW.zYy1pBz4J5uc1uYzq8ItjeG9U0pzfGIpgiNV1Gaa3O', true, true, 0, NULL); -- adminpass

INSERT INTO authorities (login_id, authority) VALUES
('user01', 'ROLE_USER'),
('admin01', 'ROLE_ADMIN');