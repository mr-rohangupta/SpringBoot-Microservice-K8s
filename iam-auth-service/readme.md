**SpringBoot-Microservice-k8s**

**_This Project is a set of Microservice which we already developed in SpringBoot-Microservice project._**

**_The difference is here we can directly start microservices using Docker and Kubernetes. We need to follow the below steps to make this service up and running using Kubernetes and Docker File._**

**Pre-requisites**

-> The pre-requisites is one should be aware of the basics of docker and kubernetes before starting, have a docker account which can be created using (https://hub.docker.com/).

**Steps to follow**

i) **_Clone this repository._**

ii) **_Install Docker for windows using https://docs.docker.com/docker-for-windows/install/_**

iii) **_If you are now registered with Docker update your settings.xml file add one more server tag in that file with id as docker.io & provide your username and password of docker.io_**

iv)**_Now install chocolatey, to do that first copy the command from its official website then run powershell as Admin and paste that command._**

v)**_Once chocolatey is successfully installed then install the followings:_**
    1)**Kubernetes-Cli:** `choco install kubernetes-cli`
    2)**Minikube:** `choco install minikube`

vi)**_Finally you can start Docker for Windows first then you can start minikube by using `minikube start` command in powershell._**

vii)**_Now you can open the services one by one in intellij Idea and use `mvn clean package dockerfile:push` command to push the docker image of every service._**

viii)**_Once all the images are pushed in docker.io then you can navigate to k8s folder with powershell and use `kubectl apply -f ./` command._**

ix)**_kubectl apply command will create all the deployments, services etc for every service._**

x)**Then you can use the below commands to check the status:**

    1)minikube dashboard: Used to open the kubernetes dashboard

    2)kubectl get all: Give you a list of all the resources and components we had created

    3)kubectl get nodes: Get the nodes available

    4)kubectl cluster-info: Get the information of cluster

    5)minikube service list: Get the list of services available
       