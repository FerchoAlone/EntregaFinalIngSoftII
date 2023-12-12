# Parcial Final Ing. Software II

Proyecto hecho en el IDE NetBeans
Trabajo hecho por:
  + David Santiago Giron Muñoz <davidgiron@unicauca.edu.co>
  + Jeferson Castaño Ossa <jcastanoossa@unicauca.edu.co>
  + Katherin Alexandra Zuñiga Morales <kzunigam@unicauca.edu.co>
  + David Santiago Fernandez Dejoy <dfernandezd@unicauca.edu.co>


En este trabajo se entrega lo solicitado en documento "Parcial final de Ingeniería de Software II 2023-II" .
Se entrega:
- Proyecto Cliente "Agentefinanciero" : Se encarga de recibir las notifaciones de la cola de RabbitMQ segun el ID del usuario 
- Proyecto Commons "StockMarket-Commons" : Proyecto que contiene las entidades y protocolos en comun entre el cliente y el servidor
- Proyecto Server "API-SocketMarket" : Servidor con API-Rest hecha en Springboot y nortificador implementado  RabbitMQ (Arquitectura hexagonal)
- Documento PDF con la documentacion solicitado
- Carpeta UMLs que contiene todos lo diagramas de diseño que se hiceron durante el proyecto
- Coleccion Postman para las consultas a la API

NOTA: PARA DOCKER SE SIGUIERON LOS SIGUIENTES PASOS:
1. Crear una red por medio del comando : "docker network create redbase"
2. Crear la imagen de la aplicacion Server con el siguiente comando: "docker build -t imagen_api ."
   En la carpeta raiz del proyecto server donde se encuentra el respectivo dockerfile ya configurado
3. Correr RabbitMQ dentro de un contenedor de docker con el siguiente comando: "docker run -d --name rabbitmq-container --network redbase -p 5672:5672 -p 15672:15672 rabbitmq:3-management"
4. Corre el Servidor dentro de un contenedor docker con el siguiente comando : "docker run -p 8080:8080 --name app-container --network redbase --link rabbitmq-container imagen_api"
