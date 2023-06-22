-- dbdivisas

CREATE TABLE historicocurrencies(
	historicoid int GENERATED ALWAYS AS IDENTITY NOT NULL,
	fecha timestamp NOT NULL,
	tiemporespuesta time NOT NULL,
	status smallint,
	PRIMARY KEY(historicoid)
);

CREATE TABLE currencies(
	code varchar(5) NOT NULL,
	symbol varchar(5) NOT NULL,
	nombre varchar(50) NOT NULL,
	symbolnative varchar(5) NOT NULL,
	decimaldigits int,
	rounding int,
	nombreplural varchar(50) NOT NULL,
	PRIMARY KEY (code)
);

CREATE TABLE valuescurrencies(
	historicoid int NOT NULL,
	code varchar(5) NOT NULL,
	valuecurrency numeric(20,2)
);

alter table valuescurrencies add constraint fk_valuescurrencies_historicoid FOREIGN KEY (historicoid) REFERENCES historicocurrencies(historicoid);
alter table valuescurrencies add constraint fk_valuescurrencies_code FOREIGN KEY (code) REFERENCES currencies(code);


