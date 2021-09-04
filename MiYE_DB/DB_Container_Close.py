#Author: Daniel Rojas
#Datr: 09-03-2021

import os
import time

#CREATING TEMPORARY FILES WITH DATA ABOUT CONTAINERS ON PC
os.system("docker ps > active_containers.txt")
os.system("docker ps -a > all_containers.txt")


#OPENING CONTAINERS FILES
active_containers = open("active_containers.txt", "r")
#print(active_containers.read()) DEBUG

all_containers = open("all_containers.txt", "r")
#print(all_containers.read()) DEBUG


#CHECKING IF CONTAINER HAS ALREADY BEEN STOPPED
already_stopped = True
containerName = ""

for line in active_containers:
  fields = line.split(" ")
  #print("Line = ", line, "\nFields: ", fields) DEBUG
  for elem in fields:
    if elem == "db:miye":
      containerName = fields[-1]
      already_stopped = False

      
#STOPPING CONTAINER IF NECESSARY
#print("Container Name 1 DEBUG:", containerName) DEBUG
if not already_stopped:
  print("Stopping container...")
  os.system("docker stop " + containerName)
else:
  print("Container was already stopped")


#CHECKING IF CONTAINER HAS ALREADY BEEN REMOVED
already_removed = True
containerName = ""

for line in all_containers:
  fields = line.split(" ")
  #print("Line = ", line, "\nFields: ", fields) DEBUG
  for elem in fields:
    if elem == "db:miye":
      containerName = fields[-1]
      already_removed = False


#REMOVING CONTAINER IF NECESSARY
#print("Container Name 1 DEBUG:", containerName)  DEBUG
if not already_removed:
  print("Removing image...")
  os.system("docker rm " + containerName)
else:
  print("Container was already removed")


#DELETING TEMPORARY FILES
active_containers.close()
all_containers.close()

if os.path.exists("active_containers.txt"):
  os.remove("active_containers.txt")
  print("Succesfully deleted temporary file (active_containers.txt)")
else:
  print("ERROR: File with active containers deleted before program exit")

if os.path.exists("all_containers.txt"):
  os.remove("all_containers.txt")
  print("Succesfully deleted temporary file (all_containers.txt)")
else:
  print("ERROR: File with all containers deleted before program exit")
  
time.sleep(3)
