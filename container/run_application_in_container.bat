::
:: Description:
:: Run Terminal FIle in Order to Run Application Branch Specified in Dockerfile through Container
::

:: %DATE%%TIME% Are Windows Pseudo-Varibles(Dynamic variables), meaning it won't work on mac or linux
:: We Publish the Port 8080 Within the Container to 8080 in Docker Host PC using -p 8080:8080

:: docker build --no-cache -t miye:image .

docker build -t miye:image --build-arg CACHEBUST="%DATE%%TIME%" . 
docker run --name miye_comp350_container -p 8080:8080 -it miye:image
docker stop miye_comp350_container
docker rm miye_comp350_container
docker rmi miye:image