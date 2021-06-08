# Standalone Base Framework

[![Java](https://img.shields.io/badge/Java-1.8.0-red.svg?style=plastic)](https://www.oracle.com/java/technologies/)
[![Maven](https://img.shields.io/badge/Maven-3.5.3-purple.svg?style=plastic)](https://maven.apache.org)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.0.4.RELEASE-green.svg?style=plastic)](https://spring.io/projects/spring-boot)
[![Hibernate](https://img.shields.io/badge/Hibernate-5.2.17-yellowgreen.svg?style=plastic)](https://hibernate.org)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.50-green.svg?style=plastic)](https://www.thymeleaf.org/doc/articles/springmvcaccessdata.html)
[![Bootstrap](https://img.shields.io/badge/Bootstrap-3.3.5-blueviolet.svg?style=plastic)](https://getbootstrap.com/docs/3.3/)
[![Docker](https://img.shields.io/badge/Docker-19.03.13-blue.svg?style=plastic)](https://docker.com)



Standalone Base Framework is built as as Base Framework for others compatible project, build using Java and SpringBoot Framework, Docker support and Stateless concept. please see the key feature below :

- Stateless Application
- Easy to Scale Up
- Docker Ready
- Sentralized Cache
- ...

## Feature
- Login
- Account Management
- Edit Password and Profile Picture
- ...

## Architecture
<img src="https://raw.githubusercontent.com/altanovela/standalone-baseframework/master/res/base-framework-arch.png" width="80%"/>
<img src="https://raw.githubusercontent.com/altanovela/standalone-baseframework/master/res/preview-login.jpg" width="60%"/></br>
<img src="https://raw.githubusercontent.com/altanovela/standalone-baseframework/master/res/preview-homepage.jpg" width="60%"/><img src="https://raw.githubusercontent.com/altanovela/standalone-baseframework/master/res/preview-account-edit.jpg" width="60%"/>

## Build & Deploy

```
Data Initialization :
---------------------
INSERT INTO t_roles (id, name, name_label) VALUES 
(1, 'ROLE_ADMIN', 'Admin'),
(2, 'ROLE_USER' , 'User');

INSERT INTO t_users (id, email, password, username, status) VALUES 
(1, 'admin', '$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS', 'Admin', 'ACTIVE'),
(2, 'user' , '$2a$10$ByIUiNaRfBKSV6urZoBBxe4UbJ/sS6u1ZaPORHF9AtNWAuVPVz1by', 'User' , 'ACTIVE');

INSERT INTO t_user_role (user_id, role_id) VALUES
(1, 1),
(2, 2);

Login Credential :
------------------
1. Role Administrator (admin/admin)
2. Role Business User (user/user)

Maven Run :
-----------
1. Prepare Project
   $ git clone https://github.com/altanovela/standalone-base-framework.git
   $ cd standalone-base-framework/

2. Build and Package the Project
   $ mvn -e clean spring-boot:run -Dactive.profile=local

Docker :
--------
1. Prepare Project
   $ git clone https://github.com/altanovela/standalone-base-framework.git
   $ cd standalone-base-framework/

2. Build and Package the Project
   $ mvn -e clean package

3. Rebuild Docker Image
   $ docker image rm standalone-base-framework:1.0
   $ docker build --tag standalone-base-framework:1.0 .

4. Rebuild Container and Run
   $ docker-compose -f deploy/docker-compose-local.yml down
   $ docker-compose -f deploy/docker-compose-local.yml up -d

Web Application URL :
http://localhost:8001

* PORT can be configured in application.properties
```

## Package Structure
**Controller > Service > Repository**

| Package Name | Usage  |
| ------------ | ------------ |
|id.altanovela.web|REST API & MVC Controller|
|id.altanovela.services|Business logic & bridges to access repository|
|id.altanovela.dao.repositories|Spring data repository for accessing database (see [Spring Repositories](https://docs.spring.io/spring-data/data-commons/docs/1.6.1.RELEASE/reference/html/repositories.html))|
|id.altanovela.dao.entities|Spring data JPA entity|
|id.altanovela.conf|Extra configuration|
|resources/templates|Thymeleaf template|
|resources/static/assets|JS and CSS|

## Contributor
| Name | Role | Email |
| ------------ | ------------ | ------------ |
|Rio Bastian|Backend Dev|rio.bastian@metranet.co.id|
|||altanovela@gmail.com|
