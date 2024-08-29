### Building the application
This is a maven project. The project is built using `mvn clean package` command. A jar named `cronparser-1.0.jar` should be created in `target/` folder.
For the sake of simplicity I have already done this step and jar is available in `target` directory. 

### Running the application
The jar is already built and packaged. You just need to perform below steps to run the application.

Go to Project root

```
$ cd target/
$ java -jar cronparser-1.0.jar "<expression>"
```

`expression`should be passed in this format : [minute] [hour] [day of month] [month] [day of week] [command]

Example: `*/15 0 1,15 * 1-5 /usr/bin/find`

Sample command to run the application

`java -jar cronparser-1.0.jar "*/15 0 1,15 * 1-5 /usr/bin/find"`


Sample output of the application
```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```
`Tests present inside tests folder under src/main/test/`

### [Optional] Running the application as Webapp

Just run one command and the application can run as webapp

You can hit the endpoint through curl command at http://localhost:8080/parse

Go to project root and execute below commands
```
$ cd target/
$ java -jar cronparser-1.0.jar
By above command webapp starts running on port 8080
```

```
$ curl --get --data-urlencode "expression=*/15 0 1-15 * 1-5 /usr/bin/find" http://localhost:8080/parse
```

Output of curl command

```
minute        0 15 30 45
hour          0
day of month  1 15
month         1 2 3 4 5 6 7 8 9 10 11 12
day of week   1 2 3 4 5
command       /usr/bin/find
```