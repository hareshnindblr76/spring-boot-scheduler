# spring-boot-scheduler

Spring Boot App to Schedule json requests to a @RestController POST method. 
The request contains a random generated missionId and seed. The controller returns the sum of all amicable numbers < seed 
if missionId is unique. 
Duplicate missionIds are handled by responding with a 409 conflict.
