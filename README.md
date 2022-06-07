# ShortestPathSolver

An app, which finds the shortest path in a grid from starting point to end point with various algorithms. 
The purpose is to compare and visualize different search algorithms.

This is a Intermediate Studies Software Project: Data Structures Project at University of Helsinki (4cr, summer 2019).


## Reports

[Week1](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti1.md)

[Week2](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti2.md)

[Week3](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti3.md)

[Week4](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti4.md)

[Week5](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti5.md)

[Week6](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti6.md)

[Week7](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti7.md)


## Documentation

[Requirements](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Implementation](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/toteutusdokumentti.md)

[Testing](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/testausdokumentti.md)

[Manual](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/kayttoohje.md)


## Final project

[Loppupalautus](https://github.com/hartzka/ShortestPathSolver/releases/tag/1.3)


## Command line instructions

The next commands are executed from the root directory.

### Testing

```
mvn test
```

Testing report

```
mvn jacoco:report
```

Open the file _target/site/jacoco/index.html_ with your browser.

### Generate the .jar

```
mvn package
```

generates a .jar-file in _target_ folder: _ShortestRouteSolver-1.0-SNAPSHOT.jar_

run the .jar file with:

```
java -jar ShortestRouteSolver-1.0-SNAPSHOT.jar
``` 

### Checkstyle

[checkstyle.xml](https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/checkstyle.xml)

```
mvn jxr:jxr checkstyle:checkstyle
```

Reports: _target/site/checkstyle.html_

### JavaDoc

[JavaDoc](https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/apidocs/index.html)

```
mvn javadoc:javadoc
```

Open the file _target/site/apidocs/index.html_ with your browser.

### Jacoco

[Jacoco test report](http://htmlpreview.github.com/?https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/target/site/jacoco/index.html)
