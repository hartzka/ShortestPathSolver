FROM ubuntu:18.04

WORKDIR /app

CMD cd ShortestPathSolver && java -jar ShortestPathSolver.jar
