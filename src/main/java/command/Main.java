package command;


import data.CD.CD;
import data.CD.CDPouziti;
import data.CD.CDVyrobci;
import data.HudebniNosice;
import data.Kazeta.Kazeta;
import data.Kazeta.KazetaTypCivky;
import data.TypHudebnihoNosice;
import data.VinylovaDeska.VinylovaDeska;
import data.VinylovaDeska.VinylovaDeskaRychlost;
import data.VinylovaDeska.VinylovaDeskaVelikost;
import generator.Generator;
import kolekce.SpojovySeznam;
import spravce.OvladaniException;
import spravce.SpravaNosice;

public class Main {

    private static boolean exit;
    private static Keyboard keyboard = new Keyboard();
    private static String volba;
    private static SpravaNosice seznam = new SpravaNosice();


    public static void main(String[] args) throws OvladaniException {
        exit = true;

        System.out.println("Zadejte 'help' nebo 'h' pro výpis příkazů.");
        do {
            volba = Keyboard.readString("Zadejte volbu: ", "volba");
            switch (volba) {
                case "help":
                case "h":
                    vypisVolb();
                    break;
                case "novy":
                case "no":
                    novy();
                    break;
                case "najdi":
                case "na":
                case "n":
                    najdi();
                    break;
                case "odeber":
                case "od":
                    odeber();
                    break;
                case "dej":
                    dej();
                    break;
                case "edituj":
                case "edit":
                    edituj();
                    break;
                case "vyjmi":
                    vyjmi();
                    break;
                case "prvni":
                case "pr":
                    prvni();
                    break;
                case "dalsi":
                case "da":
                    dalsi();
                    break;
                case "posledni":
                case "po":
                    posledni();
                    break;
                case "pocet":
                    pocet();
                    break;
                case "obnov":
                    obnov();
                    break;
                case "zalohuj":
                    zalohuj();
                    break;
                case "vypis":
                    vypis();
                    break;
                case "nactitext":
                case "nt":
                    nactitext();
                    break;
                case "uloztext":
                case "ut":
                    uloztext();
                    break;
                case "generuj":
                case "g":
                    generuj();
                    break;
                case "zrus":
                    zrus();
                    break;
                case "exit":
                    exit = false;
                    break;
                default:
                    System.out.println("Neplatná volba!");
            }

        } while (exit);
    }

    private static void vypisVolb() {
        System.out.println(" help, h     - výpis příkazů\n" +
                " novy,no     - vytvoř novou instanci a vlož data za aktuální prvek\n" +
                " najdi,na,n  - najdi v seznamu data podle hodnoty nějakém atributu\n" +
                " odeber,od   - odeber data ze seznamu podle nějaké hodnoty atributu \n" +
                " dej         - zobraz aktuální data v seznamu\n" +
                " edituj,edit - edituj aktuální data v seznamu\n" +
                " vyjmi       - vyjmi aktuální data ze seznamu\n" +
                " prvni,pr    - nastav jako aktuální první data v seznamu\n" +
                " dalsi,da    - přejdi na další data\n" +
                " posledni,po - přejdi na poslední data\n" +
                " pocet       - zobraz počet položek v seznamu\n" +
                " obnov       - obnov seznam data z binárního souboru\n" +
                " zalohuj     - zálohuj seznam dat do binárního souboru\n" +
                " vypis       - zobraz seznam dat\n" +
                " nactitext,nt- načti seznam data z textového souboru\n" +
                " uloztext,ut - ulož seznam data do textového souboru\n" +
                " generuj,g   - generuj náhodně data pro testování\n" +
                " zrus        - zruš všechny data v seznamu\n" +
                " exit        - ukončení programu");
    }

    private static void novy() {

        try {
            int typ = keyboard.readInt("Zadejte typ hudebního nosiče: \n" +
                    "1 - CD\n" +
                    "2 - Kazeta\n" +
                    "3 - Vynilova deska\n", "typ");

            int kapacita = keyboard.readInt("Zadejte kapacitu hudebního nosiče: ", "kapacita");
            int rokVyroby = keyboard.readInt("Zadejte rok výroby hudebního nosiče: ", "rok výroby");

            switch (typ) {
                case 1:
                    int vyrobce = keyboard.readInt("Zadejte výrobce CD: \n" +
                            "1 - Philips\n" +
                            "2 - Sony\n", "vyrobce");
                    int pouziti = keyboard.readInt("Zadejte použití CD: \n" +
                            "1 - audio\n" +
                            "2 - video\n" +
                            "3 - data\n", "použití");
                    HudebniNosice nosic = new CD(seznam.generujId(), kapacita, rokVyroby, CDVyrobci.values()[vyrobce - 1], CDPouziti.values()[pouziti - 1]);
                    seznam.novy(nosic);
                    break;
                case 2:
                    int typCivky = keyboard.readInt("Zadejte typ kazety: \n" +
                                    "1 - hlavni civka\n" +
                                    "2 - prijimaci civka\n"
                            , "typ kazety");
                    int vzacnost = keyboard.readInt("Zadejte vzacnost kazety od 1 do 10: \n", "vzacnost kazety");
                    HudebniNosice nosic2 = new Kazeta(seznam.generujId(), kapacita, rokVyroby, vzacnost, KazetaTypCivky.values()[typCivky - 1]);
                    seznam.novy(nosic2);
                    break;
                case 3:
                    int velikost = keyboard.readInt("Zadejte velikost vynilove desky: \n" +
                            "1 - velka\n" +
                            "2 - stredni\n" +
                            "3 - mala\n", "prumer vynilove desky");
                    int rychlost = keyboard.readInt("Zadejte rychlost vynilove desky: \n" +
                            "1 - rychle (45)\n" +
                            "2 - pomale (33)\n",  "rychlost vynilove desky");
                    HudebniNosice nosic3 = new VinylovaDeska(seznam.generujId(), kapacita, rokVyroby, VinylovaDeskaVelikost.values()[velikost - 1], VinylovaDeskaRychlost.values()[rychlost - 1]);
                    seznam.novy(nosic3);
                    break;
            }
        } catch (OvladaniException | IllegalArgumentException e ) {
            System.out.println(e.getMessage());
        }
    }
    private static void najdi() {
        int id = keyboard.readInt("Zadejte id hudebního nosiče: ", "id");
        try {
            HudebniNosice nosic = seznam.najdi(id);
            System.out.println(nosic.toString());
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void odeber() {
        int id = keyboard.readInt("Zadejte id hudebního nosiče: ", "id");
        try {
            seznam.odeber(id);
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void dej() throws OvladaniException {
        HudebniNosice temp = seznam.dej();
        if (temp != null) {
            System.out.println(temp.toString());
        }

    }

    private static void edituj() {
        try {
            TypHudebnihoNosice typ = seznam.dej().getTyp();

                int id = keyboard.readInt("Zadejte novy id hudebního nosiče: ", "id");
                int kapacita = keyboard.readInt("Zadejte novou kapacitu hudebního nosiče: ", "kapacita");
                int rokVyroby = keyboard.readInt("Zadejte novy rok výroby hudebního nosiče: ", "rok výroby");

                switch (typ) {
                    case CD:
                        int vyrobce = keyboard.readInt("Zadejte novy výrobce CD: \n" +
                                "1 - Philips\n" +
                                "2 - Sony\n", "vyrobce");
                        int pouziti = keyboard.readInt("Zadejte nove použití CD: \n" +
                                "1 - audio\n" +
                                "2 - video\n" +
                                "3 - data\n", "použití");
                        CDVyrobci cdVyrobci = CDVyrobci.values()[vyrobce - 1];
                        CDPouziti cdPouziti = CDPouziti.values()[pouziti - 1];
                        HudebniNosice cd = new CD(id, kapacita, rokVyroby, cdVyrobci, cdPouziti);
                        seznam.edituj(cd);
                        break;
                    case KAZETA:
                        int typCivky = keyboard.readInt("Zadejte novy typ kazety: \n" +
                                        "1 - hlavni civka\n" +
                                        "2 - prijimaci civka\n"
                                , "typ kazety");
                        int vzacnost = keyboard.readInt("Zadejte novou vzacnost kazety od 1 do 10: \n", "vzacnost kazety");
                        HudebniNosice kazeta = new Kazeta(id, kapacita, rokVyroby, vzacnost, KazetaTypCivky.values()[typCivky - 1]);
                        seznam.edituj(kazeta);
                        break;
                    case VINYLOVA_DESKA:
                        int velikost = keyboard.readInt("Zadejte velikost vynilove desky: \n" +
                                "1 - velka\n" +
                                "2 - stredni\n" +
                                "3 - mala\n", "prumer vynilove desky");
                        int rychlost = keyboard.readInt("Zadejte rychlost vynilove desky: \n" +
                                "1 - rychle (45)\n" +
                                "2 - pomale (33)\n",  "rychlost vynilove desky");
                        HudebniNosice vinyl = new VinylovaDeska(id, kapacita, rokVyroby, VinylovaDeskaVelikost.values()[velikost - 1], VinylovaDeskaRychlost.values()[rychlost - 1]);
                        seznam.edituj(vinyl);
                        break;
                    default:
                        System.out.println("Neplatný typ hudebního nosiče");
                }
        } catch (OvladaniException | IllegalArgumentException e) {
            System.err.println(e.getMessage());
        }
    }


    private static void vyjmi() throws OvladaniException {
        HudebniNosice nosic = seznam.vyjmi();
        if (nosic != null) {
            System.out.println(nosic.toString());
        } else {
            System.out.println("Nenastaven aktualni prvek!");
        }
    }

    private static void prvni() throws OvladaniException {
        seznam.prvni();
    }

    private static void dalsi() throws OvladaniException {
        seznam.dalsi();
    }

    private static void posledni() throws OvladaniException {
        seznam.posledni();
    }

    private static void pocet() {
        System.out.println("Pocet hudebnich nosicu: " + seznam.pocet());
    }

    private static void obnov() throws OvladaniException {
        seznam.obnov();
    }

    private static void zalohuj() throws OvladaniException {
        seznam.zalohuj();
    }

    private static void vypis() {
        try {
            for (HudebniNosice nosic : seznam.vypis()) {
                System.out.println(nosic.toString());
            }
        } catch (OvladaniException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void nactitext() throws OvladaniException {
        seznam.nactitext();
    }

    private static void uloztext() throws OvladaniException {
        seznam.uloztext();
    }

    private static void generuj() {
        Keyboard keyboard = new Keyboard();
        int pocet = keyboard.readInt("Zadejte počet hudebních nosičů, které chcete vygenerovat: ", "počet hudebních nosičů");
        seznam.generuj(pocet);
    }

    private static void zrus() {
        seznam.zrus();
    }
}

