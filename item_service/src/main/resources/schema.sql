DROP TABLE IF EXISTS item;
CREATE TABLE item (
    id BIGINT NOT NULL AUTO_INCREMENT,
    item_name VARCHAR(255),
    price INTEGER,
    quantity INTEGER,
    PRIMARY KEY (id)
)