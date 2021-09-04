#Author: Daniel Rojas
#Date: 09-03-2021

import os
import time

#TODO: CHECK IF ANOTHER IMAGE WITH IDENTICAL NAME:TAG IS ALREADY BUILT

#TODO: CALL DB_CONTAINER_CLOSE SCRIPT FOR EVERY BUILT CONTAINER 

#BUILDING CONTAINER FROM DOCKERFILE
os.system("docker build -t db:miye .")

#TODO: CHECK IF BUILD WAS SUCCESSFUL
print("Image was built successfully!")

#WE DON'T NEED TO CHECK AGAIN BECAUSE RUNNING CONTAINERS ARE TIED TO IMG BUILD

#RUNNING CONTAINER
os.system("docker run -dit db:miye")

#TODO: CHECK IF RUN WAS SUCCESSFUL
print("Container is running successfully!")

time.sleep(3)
