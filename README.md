# Grandmas-cookies
IT 326 Project

This repository hosts the code for a website to be used by the TACOS organization to schedule and manage technical assistance tickets.



---------------------------------------SETUP INSTRUCTIONS----------------------------------------

In order to run this project, the following local installations need to be on your computer:

Java Development Kit: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html

Apache Maven: https://maven.apache.org/download.cgi

You can check that your computer has these packages installed by entering the following in a terminal/command line:

java --version

mvn -v

------------------------------------------SIDE NOTE----------------------------------------------

This is a Spring Boot project. Spring Boot is an open source Java-based framework used to create microservices.
If you are new to Spring Boot, you can visit https://www.tutorialspoint.com/spring_boot/index.htm to learn more
information on how to use Spring Boot to create a web application.


-----------------------------------RUNNING THE PROJECT LOCALLY-----------------------------------

1) Clone the contents of this repository
2) Navigate to the location of the downloaded files in a terminal window (inside the tacos-website folder)
3) Enter the command: mvn clean install
4) Wait for installation of packages to complete
5) Enter the command: mvn spring-boot:run
6) Open the website in a Web Browser by navigating to localhost:8080
