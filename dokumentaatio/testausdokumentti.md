# Yksikkötestaus

Yksikkötestaus on tehty luokkakohtaisesti Javan Junitilla. Kuva kattavuusraportista löytyy [täältä](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/images/test_report.png).

Testikattavuus on keskimäärin >= 90%, joten testejä on tehty melko kattavasti, mutta kaikkia käyttöliittymään liittyviä ominaisuuksia ei ole pystytty testaamaan muuten kuin manuaalisesti. Esimerkiksi graafiset elementit ja ui:n metodit ovat tällaisia. Kaikki testit ovat automaattisia ja ne voi suorittaa koska tahansa uudelleen.

## Domain-luokkien testit

### ShortestRouteTest

Testeissä on testattu ohjelman logiikan perustoiminnot kattavasti. Kaikkia käyttöliittymään liittyviä ominaisuuksia ei ole pystytty testaamaan. Näitä ovat mm. canvaksen päivitystoiminnot.

### NodeTest

Solmuissa ei ole paljon testattavaa, kuitenkin paria solmun arvoa testataan.


## Algoritmiluokkien testit

### AStarTest

Testit testaavat kattavasti A*-algoritmiluokan toimintaa eri randomoiduilla syötteillä sekä JPS-haulla että ilman. Myös tapauksissa, joissa polkua ei löydy.

### DijkstraTest

Testit testaavat Dijkstran algoritmin toimintaa randomoiduilla syötteillä, myös tapauksissa, joissa polkua ei löydy.

### BfsTest

Tämä on hyvin samanlainen muiden algoritmien testiluokkien kanssa. Leveyshaun toimintaa testataan erilaisilla syötteillä, myös silloin, kun polkua ei löydy.


## Tietorakenteiden testit

### CustomArrayListTest

Tässä testataan oman ArrayListin toimivuutta eri syötteillä ja myös tapauksissa, joissa pitäisi tulla virhe, kuten esim. ArrayIndexOutOfBoundsException.

### QueueTest

Jonon toimintaa testataan esimerkiksi lisäämällä jonoon alkioita ja poistamalla siitä alkioita, ja tarkistamalla, että metodit toimivat oikein.

### HeapTest

Keon toimintaa testataan samaan tapaan. Kekoon lisätään alkioita ja poistetaan siitä alkioita, ja tarkistetaan, että metodit toimivat oikein.


## Manuaalinen testaus

Ohjelmaa on testattu lukuisilla eri syötteillä manuaalisesti ja käyttöliittymän kautta eri ruudukon kokoluokilla ja eri alku- ja loppupisteiden sijainneilla.

 
## Suorituskykytestaus

Suorituskykyyn liittyvä testaus löytyy tiedostosta [PerformanceTest.java](https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/src/test/java/com/shortestpathsolver/performance/PerformanceTest.java)

Testeissä on testattu kaikkien algoritmien toimintaa eri ruudukon kokoluokilla ja randomoiduilla estemäärillä.

Suorituskykytestit saa tehtyä muuttamalla PerformanceTest-luokan muuttujan _test_ trueksi. Suoritusaikaa voi testata asettamalla muuttujan _testTimeOrNodes_ arvon trueksi ja käsiteltyjä solmuja asettamalla arvon falseksi. Testatessa kannattaa kommentoida pois kaikki polunetsinnässä tehtävät tulostukset algoritmiluokista.

Testaus on tehty 4.9.2019.

Taulukko suoritusajoista eri algoritmeilla ja eri ruudukon kokoluokilla:

|  Algoritmi | Ruudukon rivien määrä | Keskimääräinen suoritusaika
|---------------------------|:--:|:--:
| A* | 9 | 17 ms
| A* | 24 | 97 ms
| A* | 39 | 240 ms
| A* | 54 | 478 ms
| A* | 69 | 783 ms
| A* | 84 | 1207 ms
| A* | 99 | 1495 ms
| JPS | 9 | 18 ms
| JPS | 24 | 103 ms
| JPS | 39 | 201 ms
| JPS | 54 | 384 ms
| JPS | 69 | 610 ms
| JPS | 84 | 747 ms
| JPS | 99 | 1088 ms
| Dijkstra | 9 | 150 ms
| Dijkstra | 24 | 170 ms
| Dijkstra | 39 | 292 ms
| Dijkstra | 54 | 507 ms
| Dijkstra | 69 | 769 ms
| Dijkstra | 84 | 1114 ms
| Dijkstra | 99 | 1444 ms
| BFS | 9 | 88 ms
| BFS | 24 | 109 ms
| BFS | 39 | 180 ms
| BFS | 54 | 286 ms
| BFS | 69 | 408 ms
| BFS | 84 | 575 ms
| BFS | 99 | 794 ms


Taulukko käsiteltyjen solmujen määrästä eri algoritmeilla ja eri ruudukon kokoluokilla:

|  Algoritmi | Ruudukon rivien määrä | Keskimääräinen käsiteltyjen solmujen määrä
|---------------------------|:--:|:--:
| A* | 9 | 33
| A* | 24 | 306
| A* | 39 | 869
| A* | 54 | 1613
| A* | 69 | 2722
| A* | 84 | 3976
| A* | 99 | 4799
| JPS | 9 | 18
| JPS | 24 | 208
| JPS | 39 | 664
| JPS | 54 | 1297
| JPS | 69 | 2086
| JPS | 84 | 3695
| JPS | 99 | 4464
| Dijkstra | 9 | 44
| Dijkstra | 24 | 331
| Dijkstra | 39 | 780
| Dijkstra | 54 | 1672
| Dijkstra | 69 | 2641
| Dijkstra | 84 | 4295
| Dijkstra | 99 | 5138
| BFS | 9 | 39
| BFS | 24 | 331
| BFS | 39 | 838
| BFS | 54 | 1653
| BFS | 69 | 2567
| BFS | 84 | 4116
| BFS | 99 | 5104


Keskimääräinen esisuoritusaika on jokaisessa tapauksessa ~2s.


### Kaavio suoritusajoista:

<img src="https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/images/performance.png" width="800"/>

Kaaviossa pystyakselilla algoritmien suoritusajat millisekunteina (ms).

Suoritusaikojen taulukosta ja kaaviosta voidaan päätellä, että nopeimmat ja tehokkaimmat algoritmit testatuilla syötteillä keskimäärin ovat JPS ja leveyshaku. Dijkstra ja leveyshaku ovat molemmat A Star- ja JPS-algoritmia hitaampia pienillä syötteillä. Toisaalta erot tasoittuvat syötteen koon kasvaessa. 

Yllättävää kyllä, leveyshaku onkin arvioitua nopeampi suurilla syötteillä, vaikka animaatio kestää leveyshaun käydessä suuren määrän solmuja läpi. Tehokkuutta selittääkin leveyshaun aikavaativuus, joka on tämän sovelluksen tapauksissa O(n). Leveyshaku on myös yksinkertaisuudessaan hyvä.

A Star ja Dijkstra näyttävät olevan hitaimpia suurilla syötteillä. Kaaviossa kuvaajat ovat murtoviivoja, mutta oikeasti kuvaajat voisi sovittaa aineistoon. On myös huomattava, että suoritusajat riippuvat osittain esteiden määrästä. Varsinkin JPS ja A Star näyttävät olevan nopeampia, kun esteitä on vähän. Nyt testiaineistossa on pyritty käyttämään mahdollisimman vaihtelevia estemääriä ja otettu keskiarvo suoritusajoista.

JPS-haun tehokkuus yllätti positiivisesti. JPS käy selvästi vähiten solmuja läpi laskentaprosessinsa aikana useimmilla syötteillä. Tämä huomataan myös JPS:n animaatiossa. Tämän kaltainen ruudukko JPS-combolla näytti optimaalisuutensa, kannatti perehtyä.

Huomioimisen arvoista on se, että sovelluksessa käytettävien solmujen maksimimäärä on noin 12250. Näin ollen algoritmeja ei ole päästy testaamaan todella suurilla syötteillä, mutta suoritusaikojen kuvaajan perusteella voidaan päätellä, että erot voivat kasvaa suuremmilla syötteillä.

Kun tutkitaan käsiteltyjen solmujen taulukkoa, saadaan hieman ristiriitaisia tuloksia. Nyt Dijkstra ja leveyshaku ovat tehottomampia kuin A* ja JPS. Huomataan, että JPS on tehokkain, sillä se käy vähiten solmuja läpi, mikä olikin odotettua. A* sijoittuu tehokkuudessa sijalle 2. Leveyshaun hohdokkuus kärsii. Voi olla, että käsiteltyjen solmujen määrät on parempi mittari kuvaamaan algoritmeja sovelluksen syötteillä syötteiden kokorajoittuneisuuden takia.

Loppujen lopuksi algoritmien välillä voi huomata jonkinlaisia tehokkuuseroja, joskaan ne eivät ole kovin merkittäviä. Merkittävämpää on algoritmien väliset erot niiden toimintalogiikassa, mikä huomataan animaatioissa solmujen läpikäymisjärjestyksellä ja eri syötteillä.
