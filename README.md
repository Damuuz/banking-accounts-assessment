#README
### How to run the project
This project uses mongodb running locally, you can start a mongodb container locally by simply running this command:
```
docker run -d -p  27017:27017 -v ~/data:/data/db mongo
```
Make sure you have maven and java 11 or later installed. Then you can simply start the project locally is follows: 
```
mvn clean spring-boot:run
```
### Testing the project
To test the project use the sample json file given to perform a post. 

The following are endpoints for opening, withdrawing from, depositing and finding one savings account respectively: 
```
POST: localhost:8080/savings
POST: localhost:8080/savings/withdraw
POST: localhost:8080/savings/deposit
GET: localhost:8080/savings/{user id}
```


The following are endpoints for opening, withdrawing from and depositing into current account respectively:
```
POST: localhost:8080/current
POST: localhost:8080/current/withdraw
POST: localhost:8080/current/deposit
```