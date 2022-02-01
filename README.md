# Grandmas-cookies
IT 326 Project

This repository hosts the code for a website to be used by the TACOS organization to schedule and manage technical assistance tickets.

The URL for the production website is https://isutacos.azurewebsites.net

We will use the Gitflow strategy for managing the repository new code commits.
It is suggested that you install git-flow on your computer for this, 
more information here: https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow


---------------------------------------SETUP INSTRUCTIONS----------------------------------------

In order to run this project, the following local installations need to be on your computer:

Java Development Kit: https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html

Apache Maven: https://maven.apache.org/download.cgi

You can check that your computer has these packages installed by entering the following in a terminal/command line:

java --version

mvn -v

------------------------------------------SIDE NOTES----------------------------------------------

Any commits to the main branch of this repository will automatically trigger a pipeline to deploy changes
to the production version of the website via Microsoft Azure Cloud. Be cautious when committing
to main or merging your branch.

This is a Spring Boot project. Spring Boot is an open source Java-based framework used to create microservices.
If you are new to Spring Boot, you can visit https://www.tutorialspoint.com/spring_boot/index.htm to learn more
on how to use Spring Boot to create a web application.


-----------------------------------RUNNING THE PROJECT LOCALLY-----------------------------------

1) Clone (download) the contents of this repository
2) Navigate to the location of the downloaded files in a terminal window
3) Enter the command: mvn clean install
4) Wait for installation of packages to complete
5) Enter the command: mvn spring-boot:run
6) Open the website in a Web Browser by going to the URL: localhost:8080
