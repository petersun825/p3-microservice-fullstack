## p3-microservice-fullstack -  New York City Human Resource Search Engine
full stack microservice application with backend and front end 
  
  
# getting started
1. download or git clone repository.
2. Open terminal and access folder.
3. run 'docker-compose up'
4. Check microservice is up and running.  Localhost:8761 - eureka server, localhost:8080: Zuul API gateway, localhost:8081: City Api gateway with Postgres Database.

REACT- Front End
5. download or git clone react-app respository
6. open terminal and access folder.
7. run 'npm start'

# User Research
Questions to ask potential end users of website
 - What are the information that are important to you and why?
 - What is your biggest frustration with government websites?
 - What are you trying to accomplish when you view NYC open data?

# Client Problem Statement
New York City is one of the biggest employers in NYC with over 150 thousand employees spanning hundreds of departments.  As each department runs as its own silo it becomes increasingly hard for mayor's 
office and general public to understand what is happening to Government of NY's human resources.  The client would like to have a way to present NYC open data on a platform and for users to save/delete data
they find on it for further research.

# Solution
Create a front end user webpage that displays json data from NYC open data and allow users to save and delete the entries.

# Technology
Java
Springboot
Gradle
React
Javascript
Docker

# Technical Proposal - Monolith
The monolith solution would have one api for all back end server things.  The advantage of this setup is it's easier to get started because it doesn't involve cumbersome coding for microservice.  The disadvantage of this is that
later on if more features are to be added the entire backend would need to be tweaked.  As complexity increases so does the backend code.

# Technical Proposal - Microservice
The other method is using the micro-service architecture which as gained popularity in recent years and it has subsequently been adopted by many high-tech companies such as Uber, and such. The advantages of a micro-service architecture are that each feature can be discretized and only have to interact with other features through their APIs. This way many features can be deployed within a single app and minimize chances of whole system crashing. 

# Case Study
Uber successfuly integrated microservice into their architecture and it has allowed the organization tremendous flexibility in manging their database and backend servers.  When they want to switch currency exchange services or change the code it doesn't have to affect their ride hailing back end or other back ends.  See article here for more detail: https://eng.uber.com/building-tincup/

# Gif of application
![application] (https://media.giphy.com/media/9oIONyVij1MVyBIoBT/giphy.gif)

# Reference(s)
Goodman, David.J "With Largest Staff Ever, New York City Reimagines How It Works", June-15, 2017,  New York Times Online, url: https://www.nytimes.com/2017/06/15/nyregion/high-number-city-employees-bill-deblasio.html
