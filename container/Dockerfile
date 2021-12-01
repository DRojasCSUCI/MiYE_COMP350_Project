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
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('1234567',True,'Bush','Bill','Male','2020-01-01 01:00:00','9999-01-01 23:59:59');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('1111111',True,'Hamilton','Derreck','Male','2021-05-23 01:00:00','9999-01-01 23:59:59');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('2222222',True,'Walter','Delainey','Female','2021-11-30 01:00:00','9999-01-01 23:59:59');"

# CUSTOMERS
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('7654321',False,'Clinton','Josh','Male','2021-09-01 01:00:00','9999-01-01 23:59:59');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('1010101',False,'Clinton','Andrea','Female','2021-09-01 01:00:00','9999-01-01 23:59:59');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('3333333',False,'Rogers','Josh','Male','2021-09-05 01:00:00','9999-01-01 23:59:59');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('4444444',False,'Parker','Noah','Male','2021-09-01 01:00:00','9999-01-01 23:59:59');"
RUN sqlite3 /MiYE_Project/db/MiYEDB.db "INSERT INTO USERS (USER_ID, ADMIN_FLAG, F_NAME, L_NAME, GENDER, DATE_START_OF_STAY, DATE_END_OF_STAY) VALUES('5555555',False,'Clinton','Santos','Ashley','2021-08-21 01:00:00','9999-01-01 23:59:59');"


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
RUN git clone -b master https://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:x-oauth-basic@github.com/DRojasCSUCI/MiYE_COMP350_Project.git /MiYE_Project/MiYE_COMP350_Project



### Installing Maven Dependencies Manager to Build Program
RUN apt install -y curl


## Environment Variables Used When Setting-Up Maven

ARG MAVEN_HOME="/opt/maven"
ARG MAVEN_VERSION=3.6.3
ARG MAVEN_CONFIG_HOME="/root/.m2"


## Maven Set-Up for OpenJDK-11

RUN mkdir -p /opt/maven
RUN curl -LSso /var/tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz https://apache.org/dist/maven/maven-3/$MAVEN_VERSION/binaries/apache-maven-$MAVEN_VERSION-bin.tar.gz
RUN tar xzvf /var/tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz -C $MAVEN_HOME --strip-components=1
RUN rm /var/tmp/apache-maven-$MAVEN_VERSION-bin.tar.gz
RUN update-alternatives --install /usr/bin/mvn mvn /opt/maven/bin/mvn 10000
RUN mkdir -p $MAVEN_CONFIG_HOME


## Shell Script that Sets Various Environmental Variables If Needed

RUN echo '#!/bin/bash' > /etc/profile.d/custom.sh
RUN echo 'export MAVEN_HOME="/opt/maven"' >> /etc/profile.d/custom.sh
RUN echo 'export MAVEN_VERSION=3.6.3' >> /etc/profile.d/custom.sh
RUN echo 'export MAVEN_CONFIG_HOME="/root/.m2"' >> /etc/profile.d/custom.sh
RUN echo '' >> /etc/profile.d/custom.sh
RUN chmod +x /etc/profile.d/custom.sh
RUN mvn -version


## Building and Runnign the Project

WORKDIR /MiYE_Project/MiYE_COMP350_Project
RUN mvn compile
ENTRYPOINT ["mvn", "jetty:run"]



### END ####
