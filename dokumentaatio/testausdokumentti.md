## Yksikkötestaus

Yksikkötestaus on tehty luokkakohtaisesti Javan Junitilla. Kattavuusraportti löytyy [täältä]().
Testikattavuus on keskimäärin >= 90%, joten testejä on tehty melko kattavasti, mutta kaikkia käyttöliittymään liittyviä ominaisuuksia ei ole pystytty testaamaan muuten kuin manuaalisesti.

### ShortestRouteTest

Testeissä on testattu ohjelman logiikan perustoiminnot. Kaikkia käyttöliittymään liittyviä ominaisuuksia ei ole pystytty testaamaan.

### AStarTest

Testit testaavat kattavasti A*-algoritmiluokan toimintaa eri syötteillä sekä JPS-haulla että ilman. Myös tapauksissa, joissa polkua ei löydy.

### NodeTest

Solmuissa ei ole paljon testattavaa, kuitenkin paria arvoa testataan.

### CustomArrayListTest

Tässä testataan oman ArrayListin toimivuutta eri syötteillä ja myös tapauksissa, joissa pitäisi tulla virhe, kuten esim. ArrayIndexOutOfBoundsException.


## Manuaalinen testaus

Ohjelmaa on testattu lukuisilla eri syötteillä manuaalisesti ja käyttöliittymän kautta.

 
## Suorituskykytestaus

Suorituskykyyn liittyvä testaus löytyy tiedostosta [Performance.java]()

Taulukko suoritusajoista eri algoritmeilla:

(Päivittyy myöhemmin)
