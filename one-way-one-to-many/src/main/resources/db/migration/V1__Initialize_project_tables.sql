CREATE TABLE menu
(
    id BIGINT(20) NOT NULL AUTO_INCREMENT,
    PRIMARY KEY (id)
);

CREATE TABLE menu_product
(
    seq     BIGINT(20) NOT NULL AUTO_INCREMENT,
    menu_id BIGINT(20) NOT NULL,
    PRIMARY KEY (seq)
);

ALTER TABLE menu_product
    ADD CONSTRAINT fk_menu_product_menu
        FOREIGN KEY (menu_id) REFERENCES menu (id);

