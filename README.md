**HTTPGetTest**

Test project. Retrieves links from database, sends HTTP "Get" requests and stores HTTP status from links back into database.
***
JDK 1.7 required.
***
MySQL required. Necessary information is located in file DBInfo.txt. 
***
Maven required. Use the following command in CMD.exe or Terminal to install project:
mvn clean compile assembly:single

Project launch for project launch use command:
java -jar HTTPGetTest-1.0-SNAPSHOT-jar-with-dependencies.jar

Program will wait for date input, please use proposed dormat "yyyy.mm.dd".
