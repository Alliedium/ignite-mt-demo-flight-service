## FlightService
The FlightService is a demo project, its purpose is to show how [Ignite Migration tool](https://github.com/Alliedium/ignite-migration-tool) works on a concrete example.
The project provides docker-compose.yml in order to make it easy start and run ignite cluster.
The ignite cluster is made up with only one ignite server node which does fit the project purpose.
The project has two branches: main and patches.

The main branch contains code which can generate fake data for ignite cluster, it can be used to see how the CLI solution from Ignite Migration Tool works.
Steps to start ignite cluster and fill it with data:
 1. docker-compose up --build ignite
 2. run LoadFlightService class either from your IDE or build a jar and run from command line

Steps to get a backup with all ignite cluster data:
 1. Get the core jar with dependencies from [Ignite Migration tool](https://github.com/Alliedium/ignite-migration-tool) packages and put it into the project folder 
 2. java -DIGNITE_HOME=$(pwd)/ignite -DIGNITE_CONFIG_HOME=file:$(pwd)/src/main/resources -jar core-0.0.2-jar-with-dependencies.jar -s -p $(pwd)/backup

In order to read data from backup back into ignite cluster use the following command:
 - java -DIGNITE_HOME=$(pwd)/ignite -DIGNITE_CONFIG_HOME=file:$(pwd)/src/main/resources -jar core-0.0.2-jar-with-dependencies.jar -d -p $(pwd)/backup

The patches branch contains code which shows how to write patches using patch tools API from Ignite Migration Tool.
Steps to apply patches (it is assumed that a backup was already prepared from above steps):
 1. run PatchData class either from your IDE or build a jar and run from command line
 2. java -DIGNITE_HOME=$(pwd)/ignite -DIGNITE_CONFIG_HOME=file:$(pwd)/src/main/resources -jar core-0.0.2-jar-with-dependencies.jar -d -p $(pwd)/patchedData
The last command will read patched data from folder patchedData into ignite cluster.
Then in order to ensure that patches were applied correctly you can see it from ignitevisor tool
