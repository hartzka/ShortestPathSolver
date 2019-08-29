# Viikon 6 raportti

Tällä viikolla sain viimeisenkin algoritmin, leveyshaun, tehtyä. Leveyshaun logiikkaa täytyi hieman miettiä. Lopulta päädyin siihen, että kaikkiin tietyn solmun vapaisiin naapureihin, myös kulmanaapureihin, on sama yhden yksikön pituinen etäisyys. Näin haku toimii parhaiten. Erona tässä on siis se, että muissa algoritmeissa kulmanaapurien etäisyys on 14 ja vierusnaapureiden 10. Tämä vastaa aika hyvin luvun sqrt(2) suhdetta yhteen yksikköön. Rakensin leveyshaulle jonoa muistuttavan Queue-tietorakenteen ja lisäsin sovellukseen mahdollisuuden vaihtaa myös leveyshakuun. Päätin toteuttaa algoritmeille abstraktin luokan Algorithm, joka määrittelee joitakin algoritmiluokkien metodeja toisteisen koodin välttämiseksi. Täydensin testejä ja dokumentaatiota, nyt testeissä on uudet HeapTest, QueueTest ja BfsTest. Tein hieman suorituskykytestausta myös leveyshaulle. JPS näyttää selkeästi tehokkaimmalta algoritmeista ja leveyshaku käy eniten solmuja läpi, mutta pysyy silti melko tehokkaana. Nyt sovellus alkaa olla loppuviilausta ja siistimistä vaille valmis.

Käytetty aika: 10h



