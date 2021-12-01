# GROUP MEMBERS:

 Daniel Rojas,
 Juan Flores, 
 Shane Vea,
 Roque Garcia,
 Jeffrey Foyil



## DESCRIPTION:
  
  This is a guide to run Group 4's MiYE_COMP350_Project in a docker container.
  We have decied to provide a Dockerfile rather than a Docker Hub Image due to its
  large size.



## REQUIREMENTS:

 - Windows 10 Operating System
 - Docker Desktop Installed and Running in Background
 - Container folder (downloaded from project git repository or provided by a group member)
     
     

## CONTAINER FOLDER CONTENTS:
  
  + Dockerfile
  + run_application_incontainer.bat



## INSTRUCTIONS STEPS TO RUN:

  1: Run the .bat script to containerize the application. Once it is running it will take around
     20 minutes just to build the Dockerfile into an image. It needs to install java and other 
     dependencies so please be patient. This is partly a one-time setup thanks to the Docker cache system.
     Once the image has been built it will take another 10 minutes to build it into a container
     which will set up its remaining dependencies, and then once you see "[INFO] Started Jetty Server"
     in the terminal window, you know the container has been built and run successfully. 

  2: Use any browser in your Host Computer and navigate to,  localhost:8080

  3: If you are seeeing the sign-in screen then you have succesfully containerized the application,
     Use whatever random sign in credentials, or not and click the sign in button to reach the core
     part of our program.


## STOP PROGRAM EXECUTION:
  
  While the program is running, navigate to the terminal window which the run script opened once it
  was executed. Hit 'CTRL + C' within the terminal window once to allow the script to move on to its
  cleanup stages, if it does not do anything, press the keys ocne more. The cleanup stages will remove
  both teh container and image from the system, ensuring there is no leftover data besides the image cache.
