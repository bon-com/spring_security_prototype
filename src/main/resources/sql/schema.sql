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
