## Projektin rakenne

Projektin rakenne on seuraava: 

<img src="https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/images/structure.png" width="800"/>

## Suorituskyky ja toiminnallisuus

Algoritmien aikavaativuudet ovat pysyneet kutakuinkin aikarajojen sisällä. Pahimman tapauksen aikavaativuutta ei ole testattu. Algoritmien toiminnassa on havaittu huomattavia eroja, varsinkin JPS-haku on usealla syötteellä avoimemmassa ruudukossa selvästi perinteistä A*-algoritmia nopeampi ja tehokkaampi. Algoritmien toimintalogiikassa on lisäksi selviä persoonallisia eroavaisuuksia, varsinkin solmujen läpikäymisjärjestyksessä ja avoimen listan toiminnassa. Polun visualisointi vie tietysti oman aikansa ja riippuu algoritmista sekä syötteestä, mutta tätä ei oteta huomioon algoritmien hakuajoissa. Algorimeja on testattu lukuisilla eri syötteillä.

## Mahdolliset parannukset

Toimintaa voisi vielä optimoida ja tehostaa esimerkiksi kehittämällä parempi heuristiikka ja rankkaamalla enemmän pois turhia naapureita. Tehokkuuden kannalta näillä on kuitenkin melko pieni merkitys. 

### Optimointi

Algoritmeja voisi edelleen optimoida varsinkin naapurien läpikäymisen ja solmujen päivittämisen osalta. Tähän ei kuitenkaan ole katsottu olevan tarpeellista käyttää paljoa resursseja.

### Parannukset

Logiikassa on esiintynyt joitakin bugeja ja niitä on korjattu. Ei ole taetta, onko logiikka täysin virheetöntä, mutta testattaessa ei ole havaittu enää uusia virheellisyyksiä. Heuristiikka on säädetty kokemuksen perusteella toimivaksi, mutta sekin saattaa harvinaisemmilla syötteillä osoittautua päivittämistä vaativaksi.
