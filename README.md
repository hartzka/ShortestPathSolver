# ShortestPathSolver

Sovellus, joka etsii lyhimmän polun ruudukossa alkupisteestä lopetuspisteeseen eri algoritmien avulla.
Tarkoitus on vertailla ja visualisoida eri hakualgoritmien toimintaa.

Tämä on Helsingin Yliopiston tietorakenteiden ja algoritmien loppukesän 2019 4op harjoitustyö.


## Viikkoraportit

[Viikko1](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti1.md)

[Viikko2](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti2.md)

[Viikko3](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti3.md)

[Viikko4](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti4.md)

[Viikko5](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti5.md)

[Viikko6](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti6.md)

[Viikko7](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/viikkoraportti7.md)


## Dokumentaatio

[Vaatimusmäärittely](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/vaatimusmaarittely.md)

[Toteutusdokumentti](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/toteutusdokumentti.md)

[Testausdokumentti](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/testausdokumentti.md)

[Käyttöohje](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/kayttoohje.md)


## Jar

[ShortestPathSolver-1.1.jar](https://github.com/hartzka/ShortestPathSolver/releases/download/1.1/ShortestPathSolver-1.1.jar)


## Komentorivitoiminnot

Seuraavat komennot ajetaan sovelluksen juurikansiosta, eli samasta kansiosta, jossa pom.xml sijaitsee.

### Testaus

Testit suoritetaan komennolla

```
mvn test
```

Testikattavuusraportti luodaan komennolla

```
mvn jacoco:report
```

Kattavuusraporttia voi tarkastella avaamalla selaimella tiedosto _target/site/jacoco/index.html_

### Suoritettavan jarin generointi

Komento

```
mvn package
```

generoi hakemistoon _target_ suoritettavan jar-tiedoston _ShortestRouteSolver-1.0-SNAPSHOT.jar_

jar-tiedoston voi ajaa komennolla
```
java -jar ShortestRouteSolver-1.0-SNAPSHOT.jar
``` 

### Checkstyle

Tiedostoon [checkstyle.xml](https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/checkstyle.xml) määrittelemät tarkistukset suoritetaan komennolla

```
 mvn jxr:jxr checkstyle:checkstyle
```

Mahdolliset virheilmoitukset selviävät avaamalla selaimella tiedosto _target/site/checkstyle.html_

### JavaDoc

[JavaDoc löytyy täältä](https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/apidocs/index.html)

JavaDoc generoidaan komennolla

```
mvn javadoc:javadoc
```

JavaDocia voi tarkastella avaamalla selaimella tiedosto _target/site/apidocs/index.html_

