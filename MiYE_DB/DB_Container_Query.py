#Author: Daniel Rojas
#Date: 09-03-2021

import os
import time

#FINDING CONTAINER'S NAME
os.system("docker ps > containers.txt")
containers = open("containers.txt","r")
containerFound = False
containerName = ""

for line in containers:
  fields = line.split(" ")
  for elem in fields:
    if elem == "db:miye":
      containerName = fields[-1]
      containerFound = True

containers.close()
if os.path.exists("containers.txt"):
  os.remove("containers.txt")

#CHECKS WHETHER THE CONTAINER WAS RUNNING OR NOT
if containerFound == False:
    print("ERROR: Your container is not running yet! If it is check that its name:tag = db:miye")
    time.sleep(5)
    exit()
else:
    containerName = containerName.strip("\n")
    containerName = containerName.strip("\r")
    print("Container name found!", containerName)

#QUERY
    query = input("Write your query here (EXIT to exit): ")

    while query != "EXIT":
        formattedQuery = "docker exec " + containerName + " sqlite3 /db/MiYEDB.db \"" + query + "\" > query_result.txt"
        #print("DEBUG: Formatted Query =", formattedQuery)  DEBUG
        os.system(formattedQuery)
        print("\n" + open("query_result.txt", "r").read())
        query = input("\nWrite your query here (EXIT to exit): ")
        
    if os.path.exists("query_result.txt"):
        os.remove("query_result.txt")

