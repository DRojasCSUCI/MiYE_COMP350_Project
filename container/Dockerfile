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
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE USERS(USER_ID varchar(7), ADMIN_FLAG bool, F_NAME varchar(50), L_NAME varchar(50), GENDER varchar(25), DATE_START_OF_STAY varchar(25), DATE_END_OF_STAY varchar(25));"
	
# ADMINS					
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS ADMINS;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE ADMINS(USER_ID varchar(7), ADMIN_KEY varchar(7));"

# SERVICES
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS SERVICES;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE SERVICES(SERVICE_ID varchar(7), SERVICE_NAME varchar(50), SERVICE_DESC varchar(1020), PRICE_PER_MINUTE float, DURATION_OPTIONS varchar(50));"

# USERS_SERVICES_HISTORY								
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "DROP TABLE IF EXISTS USERS_SERVICES_HISTORY;"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "CREATE TABLE USERS_SERVICES_HISTORY(RESERVATION_ID varchar(7), USER_ID varchar(7), CANCELLED_FLAG bool, SERVICE_ID varchar(7), DATE_TIME varchar(25), DURATION_PICKED int, COST float);"



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
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000001', 'Mineral Bath', 'Description', 2.50, '60/90');"

# MASSAGES
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000002', 'Swedish Massage', 'Description', 3.00, '30/60');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000003', 'Shiatsu Massage', 'Description', 3.00, '30/60');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000004', 'Deep Tissue Massage', 'Description', 3.00, '30/60');"

# FACIALS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000005', 'Normal Facial', 'Description', 2.00, '30/60');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000006', 'Collagen Facial', 'Description', 2.00, '30/60');"

# SPECIALTY TREATMENTS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000007', 'Hot Stone', 'Description', 3.50, '60/90');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000008', 'Sugar Scrub', 'Description', 3.50, '60/90');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000009', 'Herbal Body Wrap', 'Description', 3.50, '60/90');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO SERVICES (SERVICE_ID, SERVICE_NAME, SERVICE_DESC, PRICE_PER_MINUTE, DURATION_OPTIONS) VALUES('0000010', 'Botanical Mud Wrap', 'Description', 3.50, '60/90');"


## USERS_SERVICES_HISTORY								

# RESERVED
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('0z0z0z0z0z', '7654321', False, '0000003', '2021-09-22 09:15:00', 60, 180.00);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('1a1a1a1a1a', '7654321', False, '0000001', '2021-09-21 14:30:00', 90, 225.00);"

# CANCELLED
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('2b2b2b2b2b', '1010101', True, '0000001', '2021-09-21 15:00:00', 60, 150.00);"

# COMPLETED
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('3c3c3c3c3c', '3333333', False, '0000007', '2021-09-02 10:05:00', 60, 210.00);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('4d4d4d4d4d', '4444444', False, '0000003', '2021-09-01 05:15:00', 30, 90.00);"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS_SERVICES_HISTORY (RESERVATION_ID, USER_ID, CANCELLED_FLAG, SERVICE_ID, DATE_TIME, DURATION_PICKED, COST) VALUES('5e5e5e5e5e', '4444444', False, '0000007', '2021-09-01 12:30:00', 90, 315.00);"


### Using an ENV Variable to Avoid Using an Old Cache (Old Cache Means Old Code)

ARG CACHEBUST


### Cloning Project From Repository

# IMPORTANT NOTE 1: To avoid entering username and passowrd, a OAUTH Token generated through github is used, it expires on December 24, 2021
# IMPORTANT NOTE 2: -b BranchName can be '-b master' however, this is used to test each team member's code, so it's best to use the proper branch
RUN git clone -b DRojasBranch https://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:x-oauth-basic@github.com/DRojasCSUCI/MiYE_COMP350_Project.git /MiYE_Project/MiYE_COMP350_Project

### Moving Dependencies to Proper Directory

RUN mv /MiYE_Project/MiYE_COMP350_Project/lib/sqlite-jdbc-3.36.0.1.jar /MiYE_Project/class

### Build Application

## Build .java into .class files

RUN javac -d /MiYE_Project/class -classpath /MiYE_Project/class/sqlite-jdbc-3.36.0.1.jar /MiYE_Project/MiYE_COMP350_Project/src/miyedatamanager/*.java

### Setting CLASSPATH

ENV CLASSPATH=/MiYE_Project/class



### Creating Start-Up Bash Script

RUN echo "#!/bin/bash" > /startapp.sh
RUN echo "cd /MiYE_Project/class" >> /startapp.sh
RUN echo "java -cp \".:sqlite-jdbc-3.36.0.1.jar\" miyedatamanager.Main" >> /startapp.sh
RUN echo "" >> /startapp.sh



### Runnign Start-Up Bash Script

ENTRYPOINT ["bash", "startapp.sh"]



### END ####