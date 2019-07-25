# Vaatimusmäärittely

## Sovelluksen tarkoitus

Sovellus etsii lyhimmän polun käyttäjän luomassa ruuduista koostuvassa labyrintissä. Labyrinttiin voi lisätä ja labyrintistä voi poistaa esteitä. Lisäksi labyrinttiin voi määrittää polun aloitus- ja lopetuspisteen sekä mahdollisesti välipisteitä, joiden kautta polku kulkee. Tarkoitus on vertailla ja animoida muutaman eri hakualgoritmin toimintaa lyhimmän polun etsimiseksi. Sovelluksessa on graafinen käyttöliittymä ja algoritmien toimintaa simuloidaan eri värein ja animaatioin. Ideana on havainnollistaa eri algoritmien tehokkuutta ja logiikkaa sekä antaa mahdollisuus tutkia algoritmien välisiä eroja eri syötteillä. Lisäksi tarkoituksena on antaa hauskaa ajanvietettä miellyttävällä käyttökokemuksella.

## Toiminnallisuus

Sovelluksessa on mahdollisuus valita käytettävä algoritmi kolmesta vaihtoehdosta. Esteiden lisäys ruudukkoon tapahtuu lisäysmoodilla (insert) ja esteiden poisto poistomoodilla (clear). Esteitä lisätään ja poistetaan hiiren avulla. Aloitus- ja lopetuspisteet ovat korostettu ruudukkoon. Niitä voi siirtää hiiren avulla. Sovellus laskee ja visualisoi calculate path -painikkeesta alkupisteestä lopetuspisteeseen kulkevan lyhimmän yhtenäisen vapaan polun, joka ei kulje yhdenkään esteen kautta. Sovellus ilmoittaa, jos polkua ei löydy. Ruudukon saa tyhjennettyä clear all -painikkeesta. Polun visualisointiin käytettävän värin voi muuttaa erillisestä värinvalitsinpaneelista.

## Käytettävät algoritmit ja tietorakenteet sekä tehokkuus

Lyhimmän polun etsimisessä käytetään kolmea algoritmia. Näihin kuuluvat A*, Dijkstran algoritmi ja leveyshaku. Tietorakenteina käytetään pinoa, jonoa, listaa ja hajautustaulua. Valitsin nämä algoritmit mielenkiinnon vuoksi. Tarkoitus on vertailla näiden algoritmien eroja ja oppia tuntemaan algoritmit paremmin. Sovelluksessa käytetään algoritmien ja tiedon visualisoinnin vaatimia tietorakenteita. Tärkein ominaisuus tässä ei ole tehokkuus, vaikkakin sekin on olennainen osa. Tärkein lopputulos on algoritmien toimintalogiikka. Käytettävät verkot ja solmujen määrät ovat sen verran pieniä, että tehokkuus ei nouse olennaisesti esiin, vaan algoritmit toimivat tehokkaasti käytettävillä syötteillä.

## Luokkarakenne

Käyttöliittymälle, logiikalle ja algoritmeille on omat pakkauksensa. Päälogiikka on logiikkapakkauksessa ja kaikki algoritmiluokat ovat erikseen algoritmipakkauksessa. Ohjelman käynnistävä Main-luokka on erikseen main-pakkauksessa.

## Jatkokehitysideat

Päivittyy loppuvaiheessa kun huomataan, mitä ei ehdi tekemään


