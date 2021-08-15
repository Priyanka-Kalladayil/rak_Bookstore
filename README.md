## RAK Bank - Book Store Assessment API

##### Requirement / Instructions :

We would like you to build a restful API that provides Book store APIs. Your API must be able to handle all CURD operations:


## Technology Used

	1. Java 8
	2. Spring boot 2.x
	3. lombok
	4. JPA
	5. H2 Database
	6. Maven


### Constrain

Its recommended to compile and start the application through terminal. if you are going to run through IDE, install **lombok** plugin to generate the data annotated code.

## How to Run and Build


To Build the application, just execute:

```mvn clean install```

To run the application, just execute:

```mvn spring-boot:run```

Please use **http://localhost:8080/swagger-ui.html**  or you can use any API testing tools to test the same by using below APIs.

	http://localhost:8080/bookstore/1.0.0/books 	                                         -X GET
	http://localhost:8080/bookstore/1.0.0/books 	                                         -X POST
	http://localhost:8080/bookstore/1.0.0/books/{book_id} 	                                 -X DELETE
	http://localhost:8080/bookstore/1.0.0/books/{book_id} 	                                 -X PUT 
    http://localhost:8080/bookstore/1.0.0/books/{book_id} 	                                 -X GET
    http://localhost:8080/bookstore/1.0.0/books/{book_id}?author=&price=&classification= 	 -X PATCH



## Design Pattern

* Builder design Pattern


### Builder design Pattern

I decided to use **_Builder design Pattern_** to store book details and retrieve the same from repository since its providing a more flexible way to separate the object creation, functionality from service layer and scalable.


## Way to Test API

Once the server is started, we can test this API in **_three ways_**.

##### Swagger

This project completely integrated with Swagger API's to test API in a more convenient and easy way. To launch swagger and test, Please use this below URL in any browser.

http://localhost:8080/swagger-ui.html

##### Curl

```	
curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \ 
   "authorEmailAddress": "user%40example.com", \ 
   "authorName": "string", \ 
   "category": "string", \ 
   "classification": "FICTION", \ 
   "description": "string", \ 
   "id": 0, \ 
   "isbn": "string", \ 
   "name": "string", \ 
   "price": 15 \ 
 }' 'http://localhost:8080/bookstore/1.0.0/books'
```

#### API Testing Tool
```	

http://localhost:8080/bookstore/1.0.0/books 	                                         -X GET
http://localhost:8080/bookstore/1.0.0/books 	                                         -X POST
http://localhost:8080/bookstore/1.0.0/books/{book_id} 	                                 -X DELETE
http://localhost:8080/bookstore/1.0.0/books/{book_id} 	                                 -X PUT 
http://localhost:8080/bookstore/1.0.0/books/{book_id} 	                                 -X GET
http://localhost:8080/bookstore/1.0.0/books/{book_id}?author=&price=&classification= 	 -X PATCH
```

