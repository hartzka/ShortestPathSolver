# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus etsii lyhimmän polun käyttäjän luomassa ruudukossa. Ruudukkoon voi lisätä ja siitä voi poistaa esteitä tai määrittää satunnaiset esteet. Lisäksi ruudukkoon voi määrittää polun aloitus- ja lopetuspisteen. Ruutujen määrää on mahdollista muuttaa ja ruudukon voi tallentaa tekstitiedostoksi, jonka pystyy lukemaan myöhemmin. 

Tarkoitus on vertailla ja animoida muutaman eri hakualgoritmin toimintaa lyhimmän polun etsimiseksi. Sovelluksessa on graafinen käyttöliittymä ja algoritmien toimintaa simuloidaan eri värein ja animaatioin. Ideana on havainnollistaa eri algoritmien tehokkuutta ja logiikkaa sekä antaa mahdollisuus tutkia algoritmien välisiä eroja eri syötteillä. Lisäksi tarkoituksena on antaa hauskaa ajanvietettä miellyttävällä käyttökokemuksella.

## Toiminnallisuus

Sovelluksessa on mahdollisuus valita käytettävä algoritmi neljästä vaihtoehdosta. Vaihtoehto valitaan painikkeilla. Ruudulla näytetään, mikä algoritmi on kulloinkin valittuna. Esteiden lisäys ruudukkoon tapahtuu lisäysmoodilla (insert) ja esteiden poisto poistomoodilla (clear). Esteitä lisätään ja poistetaan hiiren avulla. 

Aloitus- ja lopetuspisteet on korostettu ruudukkoon sinisellä (aloituspiste) ja vihreällä (lopetuspiste). Niitä voi siirtää hiiren avulla. Sovellus laskee ja visualisoi calculate path -painikkeesta alkupisteestä lopetuspisteeseen kulkevan lyhimmän yhtenäisen vapaan polun, joka ei kulje yhdenkään esteen kautta. Käsitellyt solmut merkitään valkoisella. Sovellus ilmoittaa, mikäli polkua ei löydy. 

Ruudukon saa tyhjennettyä clear all -painikkeesta. Käytettävän värin voi muuttaa erillisestä värinvalitsinpaneelista. Randomize blocks -painikkeesta voi määrittää satunnaiset esteet. Ruudukon kokoa voi muuttaa syöttämällä rivien määrän rows-kenttään ja vahvistamalla update-painikkeella. Sarakkeiden määrä määräytyy ikkunan koon mukaan niin, että suhde säilyy. Alkuperäinen ikkunan koko on 1000px x 800px, joten sarakkeiden määrä saadaan likimäärin kaavasta (1000/800) x rivien määrä. 

Tiedoston tallennus onnistuu save-painikkeesta ja tiedoston lukeminen read-painikkeesta. Oletuksena kartan voi tallentaa tekstitiedostona sovelluksen maps-kansioon, jossa sijaitsee myös Movin AI Labsin karttoja. Tiedoston muokkauksissa on otettu virhemahdollisuudet huomioon.

## Käytettävät algoritmit ja tietorakenteet sekä tehokkuus

Lyhimmän polun etsimisessä käytetään neljää algoritmia. Näihin kuuluvat A* , A* tehostettuna Jump Point Search (JPS) -haulla, Dijkstran algoritmi ja leveyshaku. JPS on optimoitu versio A*-algoritmista, ja sen avulla käsiteltävien solmujen määrä voi pienentyä tuntuvasti.

Sovelluksessa käytetään algoritmien ja tiedon visualisoinnin vaatimia tietorakenteita. Tietorakenteina käytetään kekoa, jonoa, listaa ja paria. Valitsin nämä algoritmit mielenkiinnon vuoksi. Tarkoitus on vertailla näiden algoritmien eroja niin toimintalogiikan kuin tehokkuuden suhteen ja oppia tuntemaan algoritmit paremmin. Ideana on varsinkin tutkia algoritmien eroja eri syötteillä ja eri kokoisissa ruudukoissa sekä eri estemäärillä ja eri esteiden sijoittelulla. 

Tavoitteena on saada algoritmien tehokkuusluokiksi ja tilavaativuuksiksi O(n) tai O(n log n) -tyyppisiä aika- ja tilavaativuuksia. Tällöin ne toimivat tehokkaasti sovelluksen isoissakin verkoissa. Tärkein havainto lopputuloksessa algoritmien tehokkuuserojen lisäksi on algoritmien toimintalogiikka. Käytettävät verkot ja solmujen määrät ovat sen verran pieniä, että tehokkuus ei nouse olennaisesti esiin, vaan algoritmit toimivat tehokkaasti käytettävillä syötteillä. Polun animointi tulee viemään huomattavasti enemmän aikaa kuin algoritmien laskentaprosessi.

## Luokkarakenne

Käyttöliittymälle, logiikalle, tietorakenteille ja algoritmeille on omat pakkauksensa. Päälogiikka on logiikkapakkauksessa ja kaikki algoritmiluokat ovat erikseen algoritmipakkauksessa. Tietorakennepakkauksesta löytyvät kaikki itse toteutetut tietorakenteet ja ohjelman käynnistävä Main-luokka on erikseen main-pakkauksessa.

## Jatkokehitysideat

Sovellukseen voisi lisätä mahdollisuuden muuttaa ikkunan kokoa, ruutujen määrän maksimia voisi kasvattaa, sekä reitille lisätä yhden tai useampia välipisteitä. Lisäksi voisi lisätä mahdollisuuden tutkia algoritmien toimintaa osissa hitaammin.


