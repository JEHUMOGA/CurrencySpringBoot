-- dbdivisas

CREATE TABLE historicocurrencies(
	historicoid int GENERATED ALWAYS AS IDENTITY NOT NULL,
	fecha timestamp NOT NULL,
	tiemporespuesta time NOT NULL,
	status smallint,
	PRIMARY KEY(historicoid)
);


CREATE TABLE valuescurrencies(
	valuesid int GENERATED ALWAYS AS IDENTITY NOT NULL,
	historicoid int NOT NULL,
	code varchar(5) NOT NULL,
	valuecurrency numeric(20,2)
);

alter table valuescurrencies add constraint fk_valuescurrencies_historicoid FOREIGN KEY (historicoid) REFERENCES historicocurrencies(historicoid);


