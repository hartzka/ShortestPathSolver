## Projektin rakenne

Projektin rakenne on seuraava: 

<img src="https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/images/structure.png" width="800"/>

## Suorituskyky ja algoritmien toiminnallisuus

### Algoritmien toiminta

#### A*

A*-algoritmi käyttää lyhimmän polun etsimisessä apunaan ns.heuristiikkaa (h), g-arvoa (g) ja f-arvoa (f). Heuristiikka voidaan laskea monella tavalla, ja se kuvaa arviota etäisyydestä loppusolmuun. Esimerkiksi yleisesti käytetty Manhattan-etäisyys lasketaan x- ja y-koordinaattien erotuksien itseisarvojen summalla. G-arvo kuvaa etäisyyttä alkusolmusta tiettyyn solmuun. Sovelluksessa käytetään arvoa 10 viereisille naapureille ja arvoa 14 diagonaalisille naapureille. Nämä vastaavat hyvin luvun sqrt(2) ja yhden yksikön suhdetta. Esimerkiksi, jos alkusolmusta kuljetaan kolme askelta vasemmalle, tämän solmun g-arvoksi tulee 30. Jos edelleen kuljetaan kaksi askelta diagonaalisesti, tulee uuden solmun g-arvoksi 30 + 2 * 14 = 58. F-arvo määräytyy g- ja h-arvojen summana.

Algoritmi lisää kekoon (=avoimeen listaan) solmuja läpikäymisjärjestyksessä. Jokaisella kierroksella keosta otetaan solmu, jonka f-arvo on pienin. Tämä solmu merkitään käsitellyksi. Solmun mahdolliset vapaat naapurit lisätään kekoon ja prosessia jatketaan, kunnes päästään maaliin. Välillä voi tulla tarve päivittää solmun g- ja f-arvoa, mikäli löydetään parempi reitti solmuun jonkin toisen solmun kautta. Algoritmi huolehtii myös tästä.

#### JPS

JPS on muuten samantyyppinen ja käyttää samoja tietorakenteita kuin A*, mutta solmun naapureita haettaessa pyritään löytämään ns. hyppypisteitä, joihin voidaan siirtyä suoraan seuraavalla askeleella. Tämä nopeuttaa hakua, kun kaikkia solmuja ei tarvitse käydä välttämättä läpi.

#### Dijkstra

Dijkstran algoritmi käyttää apunaan minimikekoa, jossa solmut järjestetään niiden etäisyyksien mukaan. Etäisyydellä tarkoitetaan etäisyyttä alkusolmusta. Myös tässä käytetään naapureille etäisyysarvoja 10 ja 14 samaan tapaan kuin A*-algoritmissa. Solmuja lisätään kekoon ja jokaisella kierroksella keosta otetaan solmu, jonka etäisyysarvio on pienin, ja merkitään tämä käsitellyksi. Sen jälkeen kekoon laitetaan solmun vapaat naapurit etäisyysarvioineen. Haku päättyy, kun ollaan päästy loppusolmuun.

#### Leveyshaku

Leveyshaku eli BFS (=Breadth-first search) toimii käyttäen apunaan jonoa. Jonoon lisätään solmuja niiden läpikäymisjärjestyksessä ja jokaisella kierroksella jonosta otetaan solmu, joka on lisätty sinne ennen kaikkia muita jonossa olevia solmuja. Tämän jälkeen jonoon lisätään solmun vapaat naapurit ja haku etenee. Tämä takaa sen, että kaikki solmut, joihin on alkusolmusta etäisyys a, tulevat käsitellyksi ennen solmuja, joiden etäisyys > a. Sovelluksen leveyshaussa käytetään kiinteää yhden yksikön etäisyyttä kaikille naapureille, myös diagonaalisille. Näin ollen toimintalogiikka on hieman erilainen muihin algoritmeihin nähden. Kun ollaan päästy loppusolmuun, haku on valmis.


Kaikissa algoritmeissa käytetään apuna Node-olioita, joiden parent-arvoksi merkitään aina solmun vanhempi, josta lyhin reitti tulee kulkemaan. Kun algoritmien laskenta on valmis, käytetään polun visualisoinnissa apuna parent-arvoja lähtien liikkeelle loppusolmusta ja päätyen alkusolmuun. Näin saadaan selville tarkka polku. 


### Suorituskyky

Aika- ja tilavaativuudet sovelluksessa käytettäville algoritmeille ovat:

- A* ja JPS: O(n)
- Dijkstra: O(n log n)
- Leveyshaku: O(n)

joissa n on solmujen eli ruutujen määrä ja m on kaarten määrä eli solmujen välisten yhteyksien määrä. Tässä huomataan, että koska jokaisen solmun naapurien määrä on korkeintaan 8, niin 8n >= m = O(n).

Algoritmien aikavaativuudet ovat pysyneet kutakuinkin aikarajojen sisällä. Pahimman tapauksen aikavaativuutta ei ole testattu. Algoritmien toiminnassa on havaittu huomattavia eroja, varsinkin JPS-haku on usealla syötteellä avoimemmassa ruudukossa selvästi perinteistä A*-algoritmia nopeampi ja tehokkaampi. JPS vähentääkin A *-algoritmin suoritusaikaa, vaikka aikavaativuus pysyykin samana.

Algoritmien toimintalogiikassa on lisäksi selviä persoonallisia eroavaisuuksia, varsinkin solmujen läpikäymisjärjestyksessä ja avoimen listan toiminnassa. Polun visualisointi vie tietysti oman aikansa ja riippuu algoritmista sekä syötteestä, mutta tätä ei oteta huomioon algoritmien hakuajoissa. Visualisointi tapahtuu vasta algoritmin laskentaprosessin jälkeen. Algoritmeja on testattu lukuisilla eri syötteillä manuaalisesti ja automaattisin testein.

## Mahdolliset parannukset

Toimintaa voisi vielä optimoida ja tehostaa esimerkiksi kehittämällä parempi heuristiikka ja rankkaamalla enemmän pois turhia naapureita. Tehokkuuden kannalta näillä on kuitenkin melko pieni merkitys käytettävillä syötteillä. 

### Optimointi

Algoritmeja voisi edelleen optimoida varsinkin naapurien läpikäymisen ja solmujen päivittämisen osalta. Tähän ei kuitenkaan ole katsottu olevan tarpeellista käyttää paljoa resursseja.

### Parannukset

Logiikassa on esiintynyt joitakin bugeja ja niitä on korjattu. Ei ole taetta, onko logiikka täysin virheetöntä, mutta testattaessa ei ole havaittu enää uusia virheellisyyksiä. Heuristiikka on säädetty kokemuksen perusteella toimivaksi, mutta sekin saattaa harvinaisemmilla syötteillä osoittautua päivittämistä vaativaksi.

Sovellus ei aina aikaisemmin välttämättä kääntynyt kovin helposti konffien takia. Avuksi on olemassa valmis jar-tiedosto. Päivitin pomin, nyt pitäisi kääntyä paremmin.


## Lähteet

[A*](https://www.geeksforgeeks.org/a-search-algorithm/)

[JPS](https://zerowidth.com/2013/a-visual-explanation-of-jump-point-search.html)

[Ja toinen JPS](https://www.gamedev.net/articles/programming/artificial-intelligence/jump-point-search-fast-a-pathfinding-for-uniform-cost-grids-r4220/)

[Dijkstra](https://en.wikipedia.org/wiki/Dijkstra%27s_algorithm)

[Leveyshaku](https://www.geeksforgeeks.org/breadth-first-search-or-bfs-for-a-graph/)

