CREATE TABLE IF NOT EXISTS logs
               (date           DATETIME ,
                ip            VARCHAR(20),
                request          VARCHAR(30),
                status           VARCHAR(10),
                user_agent           TEXT);

CREATE TABLE IF NOT EXISTS blocked
                (ip            VARCHAR(20) UNIQUE,
                comment           TEXT);;                