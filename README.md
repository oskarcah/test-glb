# test-glb
Test Exam - Celebrity Problem

# Problem

*Problem*: Find the Celebrity

*Description:* In a team of `n` people, a celebrity is known by everyone but he/she doesn't know anybody.

# Solution

## Problem solution

For solving the problem I took the following approach:

* Assume there are `N > 1` different people, each one identified by his/her name (person's name is case sensitive), lets say `P0, P1,...,P[n-1]`
* To represent the "Is known by" relationship between 2 people. I used a `N x N` integer matrix, lets name it `M`, where:
  - `M[i][j] = 1` if `P[i]` knows `P[j]`, `M[i][j] = 0` otherwise
  - The relationship "Is known by" is not symmetric. That is, `P[i]` knows `P[j]` does not imply that  `P[j]` knows `P[i]`
  - It's trivial that a person knows his/herself, so values `M[i][i]` for `i` in `0...n-1` are not taken into account.

With this approach, the solution could be calculated as follows:

* `(i)` Find person that is known than anybody: `k`-th person is known than everybody `if M[i][k] = 1` for `i != k`, that is, all the elements `k`-th column are equal than 1, without taking into account the diagonal element.
* `(ii)` Find person that doesn't know anybody: `k`-th person doesn't  known everybody if `M[k][i] = 1` for `i != k`, that is, all the elements `k`-th row are equal than `0`, without taking into account the diagonal element.	
* if there is a person that fulfills `(i)` and `(ii)`, then he or she is the celebrity. 

## Example

There are  four people, lets say:

* Peter 
* Amy
* John 
* Mary

with the following relationships

* Peter knows Amy
* Peter knows John
* Amy knows John
* Mary knows Peter
* Mary knows Amy
* Mary knows John 

In this case the celebrity problem could be represented by:

* An array of people `people = {"Peter", "Amy", "John", "Mary"}` so that
	- `people[0] == "Peter"
	- `people[1] == "Amy"
	- `people[2] == "Mary"
	- `people[3] == "John"
* With the array above, we can represent people relationships as ordered tuples as
	-Peter knows Amy `(0,1)`
	-Peter knows John `(0,2)`
	-Amy knows John `(1,0)`
	-Mary knows Peter `(3,0)`
	-Mary knows Amy `(3,1)`
	-Mary knows John  `(3,2)`
* By using the ordered tuples, the matrix could be build (Note that main diagonal values are irrelevant)
   ```
   [
     [0, 1, 1, 0, ],
     [0, 0, 1, 0, ]
     [0, 0, 0, 0, ]
     [1, 1, 1, 0, ]
   ]
   ```
   
With the matrix is easy to verify that all  row-2 elements are zero and  all elements of column-2 (except diagonal one) are one, so, `people[2] == "John" is the celebrity.
  




## Application implementation

The implementation of application for solve Celebrity problem is the following.

* An self contained application written with SpringBoot 2.x, using an embedded web server and H2 in-memory database.
* A RESTful API with the four basic operations for a problem. 
   - A problem is a definition of N people names, people is known by relationships and the solution to be calculated: 
   - Query all problems (`GET`), 
   - Query a problem by id (`GET`) , 
   - Create a new problem (`POST`), 
   - Modify an existing problem  (`PUT`)
   - Delete an existing problem (`DELETE`)

* Test data could be seeded manually in order to play with the API by using the `CommandRunner` class of the application. See the java class `com.oskarcah.exam.celebrity.application.DatabaseSeedDataRunner` for an example of how to 
* A postman project (file `postman_project/TestApi.postman_collection.json`) is provided in order to interact with the API and take the examples of the JSON objects for request and response.
* Relevant application and component are documented.

# Endpoints API

Note: `base_url` depends on the server and port where app was deployed. For example for local deploy an test `base_url = localhost:8080`

* Endpoint `http://base_url/celebrities` Verb `GET`: Get all problemsets. Response with 200 code and  a list of all problemsets with theirs solutions stored in database.

* Endpoint `http://base_url/celebrities/{id}` Verb `GET` Description: Get problemsets with id= {id} Response with 200 code and  a the problem with its solution or Response 404 if problem with given id is not found.

* Endpoint `http://base_url/celebrities/{id}` Verb `DELETE` Description: Delete problemset with id= {id}. Response with 204 codeif problem instance is deleted or Response 404 if problem with given id is not found.

* Endpoint `http://base_url/celebrities/{id}` Verb `PUT` Description: Update problemset with id= {id} and perform the solution algorithm again. Response with 200 code if problem instance and its solution is updated in database or Response 404 if problem with given id is not found.
  
  - Request Example
 
	 ```json
	{
		
		"id" : 2,
		"people": [
			"Pedro",
			"Carlos",
			"Juan",
			"Maria",
			"Eduardo"
		],
		"relations": [
			{
				"x": "Pedro",
				"y": "Carlos"
			},
			{
				"x": "Pedro",
				"y": "Maria"
			},
			{
				"x": "Pedro",
				"y": "Juan"
			},
			{
				"x": "Carlos",
				"y": "Maria"
			},
			{
				"x": "Juan",
				"y": "Maria"
			},
			{
				"x": "Eduardo",
				"y": "Maria"
			},
			{
				"x" : "Maria",
				"y" :"Pedro" 
			}
		]
	}
	```

* Endpoint `http://base_url/celebrities` Verb `POST` Description: create a new problemset instance, perform the solution algorithm and stores the problemset in database. Response with 201 code if problem instance and its solution is updated in database or Response 404 if problem with given id is not found.

   - Request Example
   
	```json
	{
		
		"people": [
			"Pedro",
			"Carlos",
			"Juan",
			"Maria",
			"Eduardo"
		],
		"relations": [
			{
				"x": "Pedro",
				"y": "Carlos"
			},
			{
				"x": "Pedro",
				"y": "Maria"
			},
			{
				"x": "Pedro",
				"y": "Juan"
			},
			{
				"x": "Carlos",
				"y": "Maria"
			},
			{
				"x": "Juan",
				"y": "Maria"
			},
			{
				"x": "Eduardo",
				"y": "Maria"
			},
			{
				"x" : "Maria",
				"y" :"Pedro" 
			}
		]
	}
	```


# Build and run  

## Build

The project was created by using maven 3.x. There is a propper wrapper in order to issue any maven command, thus, is recommended to use the mvn wrapper rather than invoke mvn command directly.

* For building the war, asuming you have a shell in prject's root directory
```bash
 ./mvnw clean package`
 ```
 
* For runnint unit tests
```bash
 ./mvnw clean test
 ```
 

## Run
* For runnint the application with its embedded server 
```bash
./mvnw spirng-boot:run
```

