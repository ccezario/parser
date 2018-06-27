# parser

The goal is to write a parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 

How to use this application:
 - java -jar parser.jar --accesslog=/path/to/file --startDate=2017-01-01.00:00:00 --duration=daily --threshold=50
 - Configure your mysql instance in /src/main/resources/database.properties

This application is built on top of Spring Boot 2.0.3
