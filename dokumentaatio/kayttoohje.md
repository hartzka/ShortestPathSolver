# Käyttöohje

## Sovelluksen käynnistäminen

Sovelluksen voi suorittaa suorittamalla projektin mukana tuleva jar-tiedosto tai generoimalla itse suoritettava jar-tiedosto ja suorittamalla se. Ohjeet tähän löytyvät sovelluksen juurikansiosta [readme-tiedostosta](https://github.com/hartzka/ShortestPathSolver/blob/master/README.md). Myös esim. Netbeans osaa compilata ja suorittaa ohjelman. Kun sovellus on käynnistetty, päästään aloitusnäkymään:

<img src="https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/images/ohje1.png" width="800"/>

## Sovelluksen käyttäminen

Sovelluksen käyttö onnistuu suoraan käyttöliittymän kautta. Ruudukkoon piirretään esteitä, valitaan käytettävä algoritmi neljästä vaihtoehdosta ja lasketaan lyhin polku calculate path -painikkeesta. Tällöin sovellus visualisoi yhden lyhimmän polun. Huom. lyhimpiä polkuja voi olla useita. Rivien määrää voi päivittää syöttämällä rows-kenttään arvo väliltä 3-99 ja painamalla update. Sarakkeet päivittyvät samassa suhteessa. Alku- ja loppusolmuja voi siirtää hiiren avulla. Nämä ohjeet löytyvät myös [vaatimusmäärittelystä](https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/vaatimusmaarittely.md).

Tässä rivien määräksi on asetettu 70, esteitä on piirretty ruudukkoon, siirretty alku- ja loppupisteitä ja visualisoitu polku A*-algoritmilla:

<img src="https://github.com/hartzka/ShortestPathSolver/blob/master/dokumentaatio/images/ohje2.png" width="800"/>


## Tiedoston tallennus ja lukeminen

Estekartan voi tallentaa tekstitiedostoksi save-painikkeella (oletuskansiona maps-kansio) ja sen voi lukea myöhemmin read-painikkeella. Kartta tallennetaan tekstitiedostona seuraavalla tavalla: 
- Tekstitiedoston rivit kuvaavat ruudukon rivejä.
- Rivit koostuvat merkeistä "." ja "@". Nämä kuvaavat sarakkeita.
- Merkki "." tarkoittaa vapaata ruutua ilman estettä.
- Merkki "@" (tai jokin muu kuin ".") kuvaa estettä.

## Vaatimukset

Sovelluksen käyttö edellyttää javan ja javafx:n asennusta. Sovellusta on testattu linuxilla ja windowsilla, ja sen pitäisi toimia, kunhan kaikki vaadittavat komponentit on asennettu. Jos ohjelma ei syystä tai toisesta käynnisty, joitakin vaatimuksia puuttuu. [pom.xml](https://github.com/hartzka/ShortestPathSolver/blob/master/ShortestPathSolver/pom.xml) -tiedostosta löytyy vaatimuksia. Ohjelman saa suoritettua yksinkertaisesti suorittamalla jar-tiedosto.
