-- 商品テーブル
CREATE TABLE item (
    id INT AUTO_INCREMENT PRIMARY KEY,  -- 商品ID
    name VARCHAR(100) NOT NULL,         -- 商品名
    price INT NOT NULL                  -- 値段
);

-- 購入履歴テーブル
CREATE TABLE purchase_history (
    id INT AUTO_INCREMENT PRIMARY KEY,         -- 購入履歴ID
    purchase_date DATE NOT NULL               -- 購入日付
);

-- 購入商品履歴テーブル
CREATE TABLE purchase_item (
    id INT AUTO_INCREMENT PRIMARY KEY,         -- 購入商品ID
    purchase_id INT NOT NULL,                  -- 購入履歴ID（外部キー）
    item_id INT NOT NULL,                      -- 商品ID（外部キー）
    quantity INT NOT NULL,                     -- 購入数量
    price INT NOT NULL,                        -- 購入時の値段
    FOREIGN KEY (purchase_id) REFERENCES purchase_history(id),
    FOREIGN KEY (item_id) REFERENCES item(id)
);

-- 利用者テーブル
CREATE TABLE users (
  username VARCHAR(50) PRIMARY KEY,
  password VARCHAR(100) NOT NULL,
  enabled BOOLEAN NOT NULL,
  account_non_locked BOOLEAN NOT NULL DEFAULT TRUE,     -- ロック状態（true:ロック無し/false:ロック有り）
  login_failure_count INT NOT NULL DEFAULT 0,           -- 失敗回数
  last_login_at TIMESTAMP                               -- 最終ログイン成功日時
);

-- 権限テーブル
CREATE TABLE authorities (
  username VARCHAR(50),
  authority VARCHAR(50),
  FOREIGN KEY (username) REFERENCES users(username)
);
