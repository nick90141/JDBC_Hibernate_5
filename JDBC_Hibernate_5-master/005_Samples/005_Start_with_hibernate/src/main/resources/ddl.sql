USE library;

DELETE FROM author WHERE id > 6;

ALTER TABLE author AUTO_INCREMENT = 6;