# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus etsii lyhimmän polun käyttäjän luomassa ruudukossa. Ruudukkoon voi lisätä ja siitä voi poistaa esteitä tai määrittää satunnaiset esteet. Lisäksi ruudukkoon voi määrittää polun aloitus- ja lopetuspisteen. Ruutujen määrää on mahdollista muuttaa ja ruudukon voi tallentaa tekstitiedostoksi, jonka pystyy lukemaan myöhemmin. Tarkoitus on vertailla ja animoida muutaman eri hakualgoritmin toimintaa lyhimmän polun etsimiseksi. Sovelluksessa on graafinen käyttöliittymä ja algoritmien toimintaa simuloidaan eri värein ja animaatioin. Ideana on havainnollistaa eri algoritmien tehokkuutta ja logiikkaa sekä antaa mahdollisuus tutkia algoritmien välisiä eroja eri syötteillä. Lisäksi tarkoituksena on antaa hauskaa ajanvietettä miellyttävällä käyttökokemuksella.

## Toiminnallisuus

Sovelluksessa on mahdollisuus valita käytettävä algoritmi neljästä vaihtoehdosta. Vaihtoehto valitaan painikkeilla. Esteiden lisäys ruudukkoon tapahtuu lisäysmoodilla (insert) ja esteiden poisto poistomoodilla (clear). Esteitä lisätään ja poistetaan hiiren avulla. Aloitus- ja lopetuspisteet on korostettu ruudukkoon. Niitä voi siirtää hiiren avulla. Sovellus laskee ja visualisoi calculate path -painikkeesta alkupisteestä lopetuspisteeseen kulkevan lyhimmän yhtenäisen vapaan polun, joka ei kulje yhdenkään esteen kautta. Sovellus ilmoittaa, mikäli polkua ei löydy. Ruudukon saa tyhjennettyä clear all -painikkeesta. Käytettävän värin voi muuttaa erillisestä värinvalitsinpaneelista. Randomize blocks -painikkeesta voi määrittää satunnaiset esteet. Tiedoston tallennus onnistuu save-painikkeesta ja tiedoston lukeminen read-painikkeesta. Näissä on otettu virhemahdollisuudet huomioon.

## Käytettävät algoritmit ja tietorakenteet sekä tehokkuus

Lyhimmän polun etsimisessä käytetään neljää algoritmia. Näihin kuuluvat A* , A* tehostettuna Jump Point Search (JPS) -haulla, Dijkstran algoritmi ja leveyshaku. Tietorakenteina käytetään pinoa, jonoa, listaa ja hajautustaulua. Valitsin nämä algoritmit mielenkiinnon vuoksi. Tarkoitus on vertailla näiden algoritmien eroja niin toimintalogiikan kuin tehokkuuden suhteen ja oppia tuntemaan algoritmit paremmin. Ideana on varsinkin tutkia algoritmien eroja eri syötteillä ja eri kokoisissa ruudukoissa sekä eri estemäärillä ja eri esteiden sijoittelulla. Sovelluksessa käytetään algoritmien ja tiedon visualisoinnin vaatimia tietorakenteita. Tärkein ominaisuus tässä ei kuitenkaan ole tehokkuus, vaikkakin sekin on olennainen osa. Tärkein lopputulos on algoritmien toimintalogiikka. Käytettävät verkot ja solmujen määrät ovat sen verran pieniä, että tehokkuus ei nouse olennaisesti esiin, vaan algoritmit toimivat tehokkaasti käytettävillä syötteillä. 

Aikavaativuudet algoritmeille ovat:

- A*: O(n log n)
- Dijkstra: O(n log n)
- Leveyshaku: O(n)

joissa n on solmujen eli ruutujen määrä ja m on kaarten määrä eli solmujen välisten yhteyksien määrä. Tässä huomataan, että koska jokaisen solmun naapurien määrä on korkeintaan 8, niin 8n >= m = O(n).

## Luokkarakenne

Käyttöliittymälle, logiikalle, tietorakenteille ja algoritmeille on omat pakkauksensa. Päälogiikka on logiikkapakkauksessa ja kaikki algoritmiluokat ovat erikseen algoritmipakkauksessa. Tietorakennepakkauksesta löytyvät kaikki itse toteutetut tietorakenteet ja ohjelman käynnistävä Main-luokka on erikseen main-pakkauksessa.

## Jatkokehitysideat

Päivittyy loppuvaiheessa kun huomataan, mitä ei ehdi tekemään


