###
### Author: Daniel Rojas
### Date: 09-03-2021
###
### INSTRUCTIONS ON HOW TO USE THIS DOCKERFILE TO BUILD AND RUN CONTAINER
### IF YOU CHANGE NAME OF THIS FILE USE -f fName FLAG IN STEP 1
### START A CMD/POWERSHELL TERMINAL ON THE FILE'S DIRECTORY
###
### 1 (BUILD IMAGE): docker build -t tag:name .
### 2 (RUN CONTAINER): docker run -dit tag:name
### 3 (CHECK CONTAINER IS RUNNING): docker ps -a
### 4 (STOP CONTAINER): docker stop nameOfContainer
### 5 (REMOVE CONTAINER): docker rm nameOfContainer
### 
### ONCE YOU MANUALLY REMOVE DUE TO CHANGES, ETC... 
### YOU MUST REBUILD THE IMAGE FROM STEP 1 AGAIN
###
### TO QUERY THE DATABASE FROM CMD/POWERSHELL DO:
### docker exec nameOfContainer sqlite3 /db/MiYEDB.db "[QUERY]"
###
### TO ISSUE COMMANDS IN INTERACTIVE MODE DO:
### docker exec nameOfContainer [COMMAND]
###
### TO SIMULATE A BASH SESSION WITHIN INTERACTIVE MODE DO:
### docker exec -it nameOfContainer /bin/bash
### TYPE 'exit' TO GET OUT OF INTERACTIVE BASH SESSION
###



### Telling the Docker FIle to start from a Ubuntu Image

FROM ubuntu



### Installing SQL lite 3

RUN apt-get update -y
RUN apt-get install sqlite3 -y



### Checking if installed correctly (DO NOT KNOW HOW TO CHECK OUTPUT OF THIS)

RUN sqlite3 --version



### Installing Java

## Install OpenJDK-11
RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get install -y ant && \
    apt-get clean;
    
## Fix certificate issues
RUN apt-get update && \
    apt-get install ca-certificates-java && \
    apt-get clean && \
    update-ca-certificates -f;

## Setup JAVA_HOME -- useful for docker commandline
ENV JAVA_HOME /usr/lib/jvm/java-11-openjdk-amd64/
RUN export JAVA_HOME



### Installing Git

RUN apt install git -y



### Creating Project Hierarchy

RUN mkdir /MiYE_Project
RUN mkdir /MiYE_Project/class
RUN mkdir /MiYE_Project/dependency
RUN mkdir /MiYE_Project/db



### Creating Database
RUN sqlite3 /MiYE_Project/db/MiYEDB.db



### Creating Hierarchy of Database

## Tables

# USERS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS USERS;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE USERS(USER_ID varchar(10), ADMIN_FLAG bool, F_NAME varchar(50), L_NAME varchar(50), GENDER varchar(25), DATE_START_OF_STAY varchar(25), DATE_END_OF_STAY varchar(25));"
	
# ADMINS					
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS ADMINS;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE ADMINS(USER_ID varchar(10), ADMIN_KEY varchar(10));"

# SERVICES
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS SERVICES;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE SERVICES(SERVICE_ID varchar(5), SERVICE_NAME varchar(50), SERVICE_DESC varchar(1020), PRICE_PER_MINUTE float, DURATION_OPTIONS varchar(50));"

# USERS_ACTIVE_SERVICES
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS USERS_ACTIVE_SERVICES;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE USERS_ACTIVE_SERVICES(USER_ID varchar(10), IN_SERVICE_FLAG bool, SERVICE_ID varchar(5), TIME_STARTED varchar(10), DURATION_PICKED int);"

# USERS_SERVICES_HISTORY								
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS USERS_SERVICES_HISTORY;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE USERS_SERVICES_HISTORY(USER_ID varchar(10), COMPLETED_FLAG bool, CANCELLED_FLAG bool, SERVICE_ID varchar(5), DATE_TIME varchar(25), DURATION_PICKED int, COST float);"



### Populating Database with sample users

## USERS

# ADMINS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('1234567',True,'Bush','Bill','Male','01/01/2020','01/01/9999');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('1111111',True,'Hamilton','Derreck','Male','05/23/2021','01/01/9999');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('2222222',True,'Walter','Delainey','Female','11/30/2017','01/01/9999');"

# CUSTOMERS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('7654321',False,'Clinton','Josh','Male','09/01/2021','09/21/2021');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('1010101',False,'Clinton','Andrea','Female','09/01/2021','09/21/2021');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('3333333',False,'Rogers','Josh','Male','09/05/2021','09/28/2021');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('4444444',False,'Parker','Noah','Male','09/01/2021','09/05/2021');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('5555555',False,'Clinton','Santos','Ashley','08/21/2021','09/21/2021');"


## ADMINS

RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO ADMINS (USER_ID, ADMIN_KEY) VALUES('1234567','1111111');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO ADMINS (USER_ID, ADMIN_KEY) VALUES('1111111','2222222');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO ADMINS (USER_ID, ADMIN_KEY) VALUES('2222222','3333333');"


## SERVICES

# MINERAL BATH
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('001', 'Mineral Bath', 'Description', 2.50, '60 minutes / 90 minutes');"

# MASSAGES
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('002', 'Swedish Massage', 'Description', 3.00, '30 minutes / 60 minutes');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('003', 'Shiatsu Massage', 'Description', 3.00, '30 minutes / 60 minutes');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('004', 'Deep Tissue Massage', 'Description', 3.00, '30 minutes / 60 minutes');"

# FACIALS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('005', 'Normal Facial', 'Description', 2.00, '30 minutes / 60 minutes');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('006', 'Collagen Facial', 'Description', 2.00, '30 minutes / 60 minutes');"

# SPECIALTY TREATMENTS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('007', 'Hot Stone', 'Description', 3.50, '60 minutes / 90 minutes');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('008', 'Sugar Scrub', 'Description', 3.50, '60 minutes / 90 minutes');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('009', 'Herbal Body Wrap', 'Description', 3.50, '60 minutes / 90 minutes');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('010', 'Botanical Mud Wrap', 'Description', 3.50, '60 minutes / 90 minutes');"


## USERS_ACTIVE_SERVICES

RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_ACTIVE_SERVICES (USER_ID, IN_SERVICE_FLAG, SERVICE_ID, TIME_STARTED, DURATION_PICKED) VALUES('7654321', True, '001', '2:30pm', 90);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_ACTIVE_SERVICES (USER_ID, IN_SERVICE_FLAG, SERVICE_ID, TIME_STARTED, DURATION_PICKED) VALUES('1010101', True, '001', '3:00pm', 60);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_ACTIVE_SERVICES (USER_ID, IN_SERVICE_FLAG, SERVICE_ID, TIME_STARTED, DURATION_PICKED) VALUES('3333333', True, '002', '3:20pm', 30);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_ACTIVE_SERVICES (USER_ID, IN_SERVICE_FLAG, SERVICE_ID, TIME_STARTED, DURATION_PICKED) VALUES('4444444', False, '003', '3:25pm', 30);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_ACTIVE_SERVICES (USER_ID, IN_SERVICE_FLAG, SERVICE_ID, TIME_STARTED, DURATION_PICKED) VALUES('5555555', False, '007', '3:30pm', 90);"


## USERS_SERVICES_HISTORY								

# RESERVED
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (USER_ID, COMPLETED_FLAG, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('7654321', False, False, '001', '2:30pm', 90, 225.00);"

# CANCELLED
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (USER_ID, COMPLETED_FLAG, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('1010101', False, True, '001', '3:00pm', 60, 150.00);"

# COMPLETED
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (USER_ID, COMPLETED_FLAG, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('3333333', True, False, '007', '09/02/2021 10:05am', 60, 210.00);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (USER_ID, COMPLETED_FLAG, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('4444444', True, False, '003', '09/01/2021 5:15pm', 30, 90.00);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (USER_ID, COMPLETED_FLAG, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('4444444', True, False, '007', '09/01/2021 12:30pm', 90, 315.00);"



### Cloning Project From Repository

RUN git clone -b DRojasBranch https://oauth-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@github.com/DRojasCSUCI/MiYE_COMP350_Project.git /MiYE_Project


### Moving Dependencies to Proper Directory

RUN mv /MiYE_Project/MiYE_COMP350_Project/lib/sqlite-jdbc-3.36.0.1.jar /MiYE_Project/class



### Build Application

## Build .java into .class files

RUN javac -d /MiYE_Project/class -classpath /MiYE_Project/class/sqlite-jdbc-3.36.0.1.jar /MiYE_Project/MiYE_COMP350_Project/src/Test.java

### Setting CLASSPATH

ENV CLASSPATH=/MiYE_Project/class



### Creating Start-Up Bash Script

RUN echo "#!/bin/bash" > /startapp.sh
RUN echo "cd /MiYE_Project/class" >> /startapp.sh
RUN echo "java -cp \".:sqlite-jdbc-3.36.0.1.jar\" Test" >> /startapp.sh
RUN echo "" >> /startapp.sh



### Runnign Start-Up Bash Script

ENTRYPOINT ["bash", "startapp.sh"]



### END ####