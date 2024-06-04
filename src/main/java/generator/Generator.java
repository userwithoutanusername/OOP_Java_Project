package generator;

import data.CD.CD;
import data.CD.CDPouziti;
import data.CD.CDVyrobci;
import data.HudebniNosice;
import data.Kazeta.Kazeta;
import data.Kazeta.KazetaTypCivky;
import data.VinylovaDeska.VinylovaDeska;
import data.VinylovaDeska.VinylovaDeskaRychlost;
import data.VinylovaDeska.VinylovaDeskaVelikost;
import kolekce.SpojovySeznam;

import java.util.Random;

public class Generator {

    private final Random random;

    public Generator() {
        pocetID = 1;
        this.random = new Random();
    }

    int pocetID = 1;

    public void setPocetID(int pocetID) {
        this.pocetID = pocetID;
    }

    public int generujId() {
        return pocetID++;
    }

    public SpojovySeznam<HudebniNosice> generate(int pocet) {
        SpojovySeznam<HudebniNosice> seznam = new SpojovySeznam<>();
        for (int i = 0; i < pocet; i++) {
            int temp = random.nextInt(3);
            switch (temp) {
                case 0:
                    seznam.vlozPosledni(generatorCD(generujId()));
                    break;
                case 1:
                    seznam.vlozPosledni(generatorKazeta(generujId()));
                    break;
                case 2:
                    seznam.vlozPosledni(generatorVinyl(generujId()));
                    break;
            }
        }
        return seznam;
    }

    private CD generatorCD(int id) {
        int rokMin = 1900; int rokMax = 2023;
        int kapacitaMin = 5; int kapacitaMax = 200;

        int kapacita = random.nextInt(kapacitaMax - kapacitaMin + 1) + kapacitaMin;
        int rok = random.nextInt(rokMax - rokMin + 1) + rokMin;
        CDVyrobci[] cdVyrobci = CDVyrobci.values();
        CDPouziti[] cdPouziti = CDPouziti.values();

        return new CD(id, kapacita, rok, cdVyrobci[random.nextInt(cdVyrobci.length)],
                cdPouziti[random.nextInt(cdPouziti.length)]);
    }

    private Kazeta generatorKazeta(int id) {
        int rokMin = 1900; int rokMax = 2023;
        int kapacitaMin = 5; int kapacitaMax = 200;
        int vzacnostMin = 1; int vzacnotMax = 10;

        int kapacita = random.nextInt(kapacitaMax - kapacitaMin + 1) + kapacitaMin;
        int rok = random.nextInt(rokMax - rokMin + 1) + rokMin;
        int vzacnost = random.nextInt(vzacnotMax - vzacnostMin + 1) + vzacnostMin;
        KazetaTypCivky[] typ = KazetaTypCivky.values();

        return new Kazeta(id, kapacita, rok, vzacnost, typ[random.nextInt(typ.length)]);
    }

    private VinylovaDeska generatorVinyl (int id) {
        int rokMin = 1900; int rokMax = 2023;
        int kapacitaMin = 5; int kapacitaMax = 200;

        int kapacita = random.nextInt(kapacitaMax - kapacitaMin + 1) + kapacitaMin;
        int rok = random.nextInt(rokMax - rokMin + 1) + rokMin;
        VinylovaDeskaRychlost[] rychlost = VinylovaDeskaRychlost.values();
        VinylovaDeskaVelikost[] velikost = VinylovaDeskaVelikost.values();

        return new VinylovaDeska(id, kapacita, rok, velikost[random.nextInt(velikost.length)],
                rychlost[random.nextInt(rychlost.length)]);

    }
}
