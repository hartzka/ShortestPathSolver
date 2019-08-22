## Yksikkötestaus

Yksikkötestaus on tehty luokkakohtaisesti Javan Junitilla. Kattavuusraportti löytyy [täältä](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/images/test_report.png).
Testikattavuus on keskimäärin >= 90%, joten testejä on tehty melko kattavasti, mutta kaikkia käyttöliittymään liittyviä ominaisuuksia ei ole pystytty testaamaan muuten kuin manuaalisesti. Esimerkiksi graafiset elementit ja ui:n metodit ovat tällaisia. 

### ShortestRouteTest

Testeissä on testattu ohjelman logiikan perustoiminnot kattavasti. Kaikkia käyttöliittymään liittyviä ominaisuuksia ei ole pystytty testaamaan. Näitä ovat mm. graafiset elementit ja ui:n toiminnot.

### AStarTest

Testit testaavat kattavasti A*-algoritmiluokan toimintaa eri randomoiduilla syötteillä sekä JPS-haulla että ilman. Myös tapauksissa, joissa polkua ei löydy.

### DijkstraTest

Testit testaavat Dijkstran algoritmin toimintaa randomoiduilla syötteillä. Myös tapauksissa, joissa polkua ei löydy.

### NodeTest

Solmuissa ei ole paljon testattavaa, kuitenkin paria solmun arvoa testataan.

### CustomArrayListTest

Tässä testataan oman ArrayListin toimivuutta eri syötteillä ja myös tapauksissa, joissa pitäisi tulla virhe, kuten esim. ArrayIndexOutOfBoundsException.


## Manuaalinen testaus

Ohjelmaa on testattu lukuisilla eri syötteillä manuaalisesti ja käyttöliittymän kautta eri ruudukon kokoluokilla ja eri alku- ja loppupisteiden sijainneilla.

 
## Suorituskykytestaus

Suorituskykyyn liittyvä testaus löytyy tiedostosta [Performance.java](https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/src/test/java/com/shortestpathsolver/performance/PerformanceTest.java)

Testaus on tehty 22.8.2019.

Taulukko suoritusajoista eri algoritmeilla ja eri ruudukon kokoluokilla:

|  Algoritmi | Ruudukon rivien määrä | Processing time average
|---------------------------|:--:|:--:
| A* | 9 | 20 ms
| A* | 24 | 137 ms
| A* | 39 | 377 ms
| A* | 54 | 777 ms
| A* | 69 | 1361 ms
| A* | 84 | 2180 ms
| A* | 99 | 2770 ms
| JPS | 9 | 18 ms
| JPS | 24 | 123 ms
| JPS | 39 | 281 ms
| JPS | 54 | 565 ms
| JPS | 69 | 946 ms
| JPS | 84 | 1486 ms
| JPS | 99 | 1862 ms
| Dijkstra | 9 | 55 ms
| Dijkstra | 24 | 99 ms
| Dijkstra | 39 | 239 ms
| Dijkstra | 54 | 456 ms
| Dijkstra | 69 | 714 ms
| Dijkstra | 84 | 1095 ms
| Dijkstra | 99 | 1519 ms


Preprocessing time on jokaisessa tapauksessa ~2s.
