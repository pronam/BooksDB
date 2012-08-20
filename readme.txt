      install rabbitmq
      install maven
      install sqlfire

      Right now there is not public repo having the sqlfire jars , so use the following command to install the maven artifact. Replace -Dfile value with the
      actual location of the file.

      mvn install:install-file -DgroupId=com.vmware.sqlfire -DartifactId=sqlfire-client -Dpackaging=jar -Dversion=1.0.3 -Dfile=/Volumes/SSD/Development/installs/sqlfire/vFabric_SQLFire_103/lib/sqlfireclient.jar -DgeneratePom=true

      The below dependency is now already added. So additional steps are required.
       <dependency>
            <groupId>com.vmware.sqlfire</groupId>
            <artifactId>sqlfire-client</artifactId>
            <version>1.0.0-BETA</version>
        </dependency>

In sqlfire create a directory called server1 under the server root folder. Use the below query to create the tables.


create table BOOKS.BOOKS(ID int, NAME varchar(500), TAG varchar(300), SIZE int, LOCATION varchar(500));

Run the ImportProductsIntegrationTests to insert rows into the RabbitMq
Run SQLFireInserterFromRabbit to import data from the queue into the sqlfire server.