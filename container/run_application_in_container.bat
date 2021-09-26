::
:: Author: Daniel Rojas
:: Date: 09/24/2021
::
:: Description:
:: Run Terminal FIle in Order to Run Application Branch Specified in Dockerfile through Container
::

docker build -t miye:image .
docker run --name miye_comp350_container -it miye:image
docker stop miye_comp350_container
docker rm miye_comp350_container
docker rmi miye:image