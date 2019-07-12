CREATE TABLE pdv (
	id BIGINT(10) PRIMARY KEY AUTO_INCREMENT,
	trading_name VARCHAR(30) DEFAULT '' NOT NULL,
	owner_name VARCHAR(30) DEFAULT '' NOT NULL,
	document VARCHAR(30) DEFAULT '' NOT NULL,
	coverage_area MULTIPOLYGON,
	address POINT
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET @multipolygon = ST_GeomFromText('MULTIPOLYGON((
		(30 20, 45 40, 10 40, 30 20),
		(15 5, 40 10, 10 20, 5 10, 15 5)
))');
              
SET @point = ST_GeomFromText('POINT(-46.57421 -21.785741)');

INSERT INTO pdv (trading_name, owner_name, document, coverage_area, address) 
VALUES ('Adega da Cerveja - Pinheiros', 'Zé da Silva', '1432132123891/0001', @multipolygon, @point);