SELECT * FROM historicocurrencies;

SELECT historicoid,fecha FROM historicocurrencies 
Order by fecha desc limit 1;

SELECT historicoid,fecha FROM historicocurrencies 
Order by fecha asc limit 1;

SELECT * FROM currencies;

SELECT * FROM valuescurrencies where historicoid = 30;

--Delete from valuescurrencies;

--Delete from historicocurrencies;

UPDATE historicocurrencies SET status = 1 WHERE historicoid = 25

SELECT * FROM historicocurrencies WHERE fecha = '2023-06-19T12:15';

SELECT valuesid, historicoid, code, valuecurrency FROM valuescurrencies 
WHERE historicoid = 12 AND code = 'usd';


SELECT fecha, code, valuecurrency FROM historicocurrencies h
INNER JOIN valuescurrencies v ON v.historicoid = h.historicoid

INSERT INTO divisas(divisacode, symbol, nombre, symbolnative, decimaldigits, rounding, nombreplural, historicoid)
VALUES ('CAD','CA$','Canadian Dollar','$',2,0,'Canadian dollars',1);

INSERT INTO divisas(divisacode, symbol, nombre, symbolnative, decimaldigits, rounding, nombreplural, historicoid)
VALUES ('EUR','€','Euro','€',2,0,'Euros',1);

INSERT INTO divisas(divisacode, symbol, nombre, symbolnative, decimaldigits, rounding, nombreplural, historicoid)
VALUES ('USD','$','US Dollar','$',2,0,'US dollars',1);

Select d.divisacode, d.symbol, d.nombre, d.symbolnative, d.decimaldigits, d.rounding, d.nombreplural, d.historicoid, h.fecha from divisas d 
INNER JOIN historicodivisas h ON h.historicoid = d.historicoid 
WHERE h.fecha = '2023-06-15T22:30:00'

Select divisaid,divisacode, symbol, nombre, symbolnative, decimaldigits, rounding, nombreplural, fecha from divisas d
INNER JOIN historicodivisas h ON h.historicoid = d.historicoid 
WHERE d.divisacode LIKE 'USD' AND Date(h.fecha) = '2023-05-15T20:05:03' order by fecha asc

SELECT d.divisaid, d.divisacode, d.symbol, d.nombre, d.symbolnative, d.decimaldigits, d.rounding, d.nombreplural, h.fecha 
               FROM divisas d 
               INNER JOIN historicodivisas h ON h.historicoid = d.historicoid 
               WHERE d.divisacode LIKE 'MXN' AND 
               (h.fecha = '2023-04-15T20:05:03' OR h.fecha = (SELECT MIN(h2.fecha) FROM historicodivisas h2))
			   
			   
SELECT d.divisaid, d.divisacode, d.symbol, d.nombre, d.symbolnative, d.decimaldigits, d.rounding, d.nombreplural, h.fecha 
FROM divisas d 
INNER JOIN historicodivisas h ON h.historicoid = d.historicoid 
WHERE d.divisacode LIKE 'USD' AND DATE_TRUNC('day', h.fecha) = DATE_TRUNC('month', TIMESTAMP '2023-04-15T20:05:03')
ORDER BY h.fecha ASC 
LIMIT 1