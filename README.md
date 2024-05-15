- Otevřte projekt v NetBeans a přejmenujte projekt tak, že místo koncovky *Prijmeni vložíte svoje příjmení.

Jenom přejmenovaný projekt bude hodnocen.
- Vytvořte tyto povinné balíčky:

  -  kolekce pro všechny třídy, a rozhraní, které souvisí s interfejsem Seznam
  -  data pro třídy, které popisuj datové typy
  -  generator pro třídy generátoru testovacího vzorku dat.
  -  perzistence pro třídy, které zajistí lehkou perzistenci dat seznamu
  -  command pro třídy uživatelského rozhraní příkazového řádku
- Do do balíčku kolekce překopírujte z předchozího projektu SpojovySeznam třídy a rozhraní včetně jejich testů. ✅

- V balíčku data vytvořte vlastní datové třídy, které například vyberete podle vašeho projektu z BSWIN. Příklady skupin tříd: Rezervace, Vypujcka nebo Recepcni, Technik, AutoOpravar. Již nebudou povoleny tyto třídy: OsobniAutomobil, NakladniAutomobil a Traktor. Při výběru tříd volte takové třídy, aby měly společného předka. Každý student si zvolí svoji specifickou kombinaci tříd a jejich atributů, které musí schválit vyučující. Z toho plyne, že nemůže dojít k tomu, aby dva studenti měli stejné semestrální práce.

- Do balíčku generator vložte třídy, které budou generovat náhodně objekty podle tříd z balíčku data. Vygenerované objekty vložte do spojového seznamu.

- Do balíčku perzistence vložte třídy, které budou

  -  zapisovat data seznamu do binárního souboru
  -  obnovovat data z binárního souboru do seznamu
  -  dále obsahovat stejné funkce, ale pro textové soubory
- Balíček command bude obsahovat třídy, které budou dekódovat uživatelské příkazy a vykonávat je. Příklady příkazů

   -  help, h     - výpis příkazů
   -  novy,no     - vytvoř novou instanci a vlož data za aktuální prvek
  -  najdi,na,n  - najdi v seznamu data podle hodnoty nějakém atributu
   -  odeber,od   - odeber data ze seznamu podle nějaké hodnoty atributu 
   -  dej         - zobraz aktuální data v seznamu
  -  edituj,edit - edituj aktuální data v seznamu
   -  vyjmi       - vyjmi aktuální data ze seznamu
   -  prvni,pr    - nastav jako aktuální první data v seznamu
   -  dalsi,da    - přejdi na další data
   -  posledni,po - přejdi na poslední data
   -  pocet       - zobraz počet položek v seznamu
   -  obnov       - obnov seznam data z binárního souboru
   -  zalohuj     - zálohuj seznam dat do binárního souboru
   -  vypis       - zobraz seznam dat
   -  nactitext,nt- načti seznam data z textového souboru
   -  uloztext,ut - ulož seznam data do textového souboru
   -  generuj,g   - generuj náhodně data pro testování
   -  zrus        - zruš všechny data v seznamu
   -  exit        - ukončení programu
- Uložte projekt do repository (commit a push) ve větvi "main".
