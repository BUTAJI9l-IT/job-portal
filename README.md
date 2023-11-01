# Job-Portal
Bachelor Thesis

Navod na pouziti aplikace:
-
- Stahnout Docker: https://docs.docker.com/engine/install/
- Stahnout aplikaci pomoci prikazu "***git clone https://github.com/BUTAJI9l-IT/Job-Portal.git***"
- Jit do **./Job-Portal/docker-compose.yaml**
- Pokud chcete zapnout email notifikace, tak je treba vyplnit v **services -> job-portal -> environment** nasledujici promenne prostredi:

  - EMAIL_NOTIFICATION_APP_USERNAME - email adresa, ktera bude pouzita pro zaslani notifikaci
  - EMAIL_NOTIFICATION_APP_PASSWORD - heslo, ktere bylo vygenorovano pro aplikaci, aby mela pristup pro odeslani zprav. (Navod pro GMail: https://support.google.com/mail/answer/185833)
  - EMAIL_STMP_HOST - stmp host pro email sluzbu, kterou pouziva uvedena adresa (**smtp.gmail.com** pro GMailovske adresy)
  - NOTIFICATIONS_ENABLED - **true** pokud chcete zapnout notifikace a mate nastavene promenne s prefixem **EMAIL_**
  - Promenne pro nastaveni privatniho a verejneho klicu pro sifrovani JWT. Pokud nebudou vyplnene, pouzijou se klice z projektu.
    - KEY_STORE_LOCATION - cesta k keystore souboru
    - KEY_PAIR_NAME - jmeno (alias) klicu
    - KEY_STORE_PASSWORD - heslo na keystore soubor
    - KEY_PAIR_PASSWORD - heslo na klice v keystoru
    - KEY_STORE_KID - kid pro overovani podpisu
- Provedte prikaz **docker compose up --build** v repozitari **'Job-Portal'**
- Swagger mel by byt pristupny na adrese http://localhost:8080/swagger-ui/index.html#/
- Prikaz **docker compose down** by mel vypnout aplikaci

