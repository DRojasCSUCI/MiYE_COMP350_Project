GROUP MEMBERS:
Daniel Rojas
Juan FLores
Shane Vea
Roque Garcia


DESCRIPTION:
  
  This is a guide to run Group 4's MiYE_COMP350_Project in a docker container.
  We have decied to provide a Dockerfile rather than a Docker Hub Image due to its size.


REQUIREMENTS:

 - Windows 10 Operating System
 - Docker Desktop Installed and Running in Background
 - Container folder (downloaded from project git repository or provided by a group member)
     

CONTAINER FOLDER CONTENTS:
  
  + Dockerfile
  + run_application_incontainer.bat


INSTRUCTIONS STEPS TO RUN:

  * Run the .bat script to containerize the application. Once it is running it will take around
    20 minutes just to build the Dockerfile into an image. 

    Once the image has been built it will take another 10 minutes to build it into a container
    which will set up its remaining dependencies, and then once you see "". You can move on to
    the next step

  * Use any browser in your Host Computer and navigate to,  localhost:8080

  * If you are seeeing the sign-in screen then you have succesfully containerized the application,
    Use whatever random sign in credentials, or not and click the sign in button to reach the core
    part of our program.


