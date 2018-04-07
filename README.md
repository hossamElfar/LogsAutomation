# Access log automation 

## Main functionality 
* Parse a given log file. 
* Insert the parsd log into mysql database.
* Query the database for requests in a given interval.
* Block the user's who exceeded a given threashold in a given interval.

## Run form the jar
`java -cp "parser.jar" com.ef.Parser --accesslog=\path\to\log\file --startDate=2017-01-01.13:00:00 --duration=hourly --threshold=100 --dbusername=root --password= --dbHost=localhost --dbName=WalletHub
`
* parameters
    * accesslog: the path to the log file [required]
    * startDate: the start date of the interval [required]
    * duration: either hourly or daily [required]
    * threshold: the threshold for the ips whithin a given interval [required].
    * dbusername: the user name of the database [optional, default=root]
    * password: the password for the user [optional, default=]
    * dbHost: the host of the database [optional, default=localhost]
    * dbName: the database name [optional, default=WalletHub]

## Run from the source
This is a maven project. make sure to install dependencies then build the jar and run it as descriped in the previous section.

## Used packages
* `mysql-connector-java` for connecting with mysql.
* `slf4j-jdk14` for better logging experience. 

## SQL queries and tables
Kindly refear to `SQL` folder.    
