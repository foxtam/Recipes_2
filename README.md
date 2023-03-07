About
Let's make a program that stores all recipes in one place. Create a multi-user web service with Spring Boot that allows storing, retrieving, updating, and deleting recipes.

Learning outcomes
Get to know the backend development. Use Spring Boot to complete this project. Learn about JSON, REST API, Spring Boot Security, PostreSQL database, LocalDateTime, Project Lombok, and other concepts useful for the backend.


Stage 1/5: First recipe.
Implement two endpoints:

POST /api/recipe receives a recipe as a JSON object and overrides the current recipe.
GET /api/recipe returns the current recipe as a JSON object.
The initial recipe can have any form.


Stage 2/5: Multiple recipes.
Rearrange the existing endpoints; the service should support the following:

POST /api/recipe/new receives a recipe as a JSON object and returns a JSON object with one id field. This is a uniquely generated number by which we can identify and retrieve a recipe later. The status code should be 200 (Ok).
GET /api/recipe/{id} returns a recipe with a specified id as a JSON object (where {id} is the id of a recipe). The server should respond with the 200 (Ok) status code. If a recipe with a specified id does not exist, the server should respond with 404 (Not found).


Stage 3/5: Store a recipe.
First of all, include all necessary dependencies and configurations in the build.gradle and application.properties files.

For testing reasons, the application.properties file should contain the following line with the database name:

spring.datasource.url=jdbc:postgresql:file:../recipesdb
The service should support the same endpoints as in the previous stage:

POST /api/recipe/new receives a recipe as a JSON object and returns a JSON object with one id field;
GET /api/recipe/{id} returns a recipe with a specified id as a JSON object.
To complete the stage you need to add the following functionality:

Store all recipes permanently in a database: after a server restart, all added recipes should be available to a user;
Implement a new DELETE /api/recipe/{id} endpoint. It deletes a recipe with a specified {id}. The server should respond with the 204 (No Content) status code. If a recipe with a specified id does not exist, the server should return 404 (Not found);
The service should accept only valid recipes – all fields are obligatory, name and description shouldn't be blank, and JSON arrays should contain at least one item. If a recipe doesn't meet these requirements, the service should respond with the 400 (Bad Request) status code.


Stage 4/5: Sort & update.
Don't forget to keep the functionality from the previous stages. This is what your program can do:

POST /api/recipe/new receives a recipe as a JSON object and returns a JSON object with one id field;
GET /api/recipe/{id} returns a recipe with a specified id as a JSON object;
DELETE /api/recipe/{id} deletes a recipe with a specified id.
In this stage, the recipe structure should contain two new fields:

category represents a category of a recipe. The field has the same restrictions as name and description. It shouldn't be blank;
date stores the date when the recipe has been added (or the last update). You can use any date/time format, for example 2021-09-05T18:34:48.227624 (the default LocalDateTime format), but the field should have at least 8 characters.
Also, the service should support the following endpoints:

PUT /api/recipe/{id} receives a recipe as a JSON object and updates a recipe with a specified id. Also, update the date field too. The server should return the 204 (No Content) status code. If a recipe with a specified id does not exist, the server should return 404 (Not found). The server should respond with 400 (Bad Request) if a recipe doesn't follow the restrictions indicated above (all fields are required, string fields can't be blank, arrays should have at least one item);
GET /api/recipe/search takes one of the two mutually exclusive query parameters:
category – if this parameter is specified, it returns a JSON array of all recipes of the specified category. Search is case-insensitive, sort the recipes by date (newer first);
name – if this parameter is specified, it returns a JSON array of all recipes with the names that contain the specified parameter. Search is case-insensitive, sort the recipes by date (newer first).
If no recipes are found, the program should return an empty JSON array. If 0 parameters were passed, or more than 1, the server should return 400 (Bad Request). The same response should follow if the specified parameters are not valid. If everything is correct, it should return 200 (Ok).


Stage 5/5: More chefs to the table.
The service should contain all features from the previous stages. To complete the project, you need to add the following functionality:

New endpoint POST /api/register receives a JSON object with two fields: email (string), and password (string). If a user with a specified email does not exist, the program saves (registers) the user in a database and responds with 200 (Ok). If a user is already in the database, respond with the 400 (Bad Request) status code. Both fields are required and must be valid: email should contain @ and . symbols, password should contain at least 8 characters and shouldn't be blank. If the fields do not meet these restrictions, the service should respond with 400 (Bad Request). Also, do not forget to use an encoder before storing a password in a database. BCryptPasswordEncoder is a good choice.
Include the Spring Boot Security dependency and configure access to the endpoints – all implemented endpoints (except /api/register) should be available only to the registered and then authenticated and authorized via HTTP Basic auth users. Otherwise, the server should respond with the 401 (Unauthorized) status code.
Add additional restrictions – only an author of a recipe can delete or update a recipe. If a user is not the author of a recipe, but they try to carry out the actions mentioned above, the service should respond with the 403 (Forbidden) status code.
