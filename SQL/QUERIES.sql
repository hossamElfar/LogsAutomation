-- first query
SELECT ip, COUNT(ip) as req
FROM logs 
WHERE date BETWEEN "2017-01-01.00:00:00" 
and
"2017-01-01.23:59:59" 
GROUP BY ip HAVING req > 500;

-- second query
SELECT ip, COUNT(ip) as req
FROM logs
WHERE date BETWEEN "2017-01-01.00:00:00" 
and
"2017-01-01.23:59:59" 
GROUP BY ip;

-- third query 
SELECT * 
FROM logs
WHERE ip LIKE '192.168.102.136';