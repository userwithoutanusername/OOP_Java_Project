package perzistence;

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
import kolekce.SpojovySeznam;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HexFormat;
import java.util.List;
import java.util.stream.Collectors;

public class TextovaPerzistence {

    public static <E extends HudebniNosice> void uloz(String jmeno, SpojovySeznam<E> seznam) {
        if (seznam == null) {
            throw new IllegalArgumentException("Seznam nesmi byt null");
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(jmeno), StandardCharsets.UTF_8))) {
            List<String> list = seznam.stream()
                    .map(prvek -> {
                        TypHudebnihoNosice typ = prvek.getTyp();
                        switch(typ) {
                            case CD:
                                CD cd = (CD) prvek;
                                return cd.getId() + ";" + cd.getTyp() + ";" + cd.getKapacita() + ";" + cd.getRokVyroby() + ";" + cd.getVyrobec() + ";" + cd.getPouziti();
                            case KAZETA:
                                Kazeta kazeta = (Kazeta) prvek;
                                return kazeta.getId() + ";" + kazeta.getTyp() + ";" + kazeta.getKapacita() + ";" + kazeta.getRokVyroby() + ";" + kazeta.getVzacnost() + ";" + kazeta.getTypCivky();
                            case VINYLOVA_DESKA:
                                VinylovaDeska vinylovaDeska = (VinylovaDeska) prvek;
                                return vinylovaDeska.getId() + ";" + vinylovaDeska.getTyp() + ";" + vinylovaDeska.getKapacita() + ";" + vinylovaDeska.getRokVyroby() + ";" + vinylovaDeska.getRychlost() + ";" + vinylovaDeska.getVelikost();
                            default:
                                throw new IllegalArgumentException("Neznamy typ hudebniho nosice!");
                        }
                    }).collect(Collectors.toList());
            bw .write(String.join(System.lineSeparator(), list));
            System.out.println("Seznam úspěšně zapsán do souboru " + jmeno);
        } catch (IOException e) {
            System.out.println("Chyba při zápisu do souboru " + jmeno);
        }
    }

    public static SpojovySeznam<HudebniNosice> nacti(String jmeno) {

        SpojovySeznam<HudebniNosice> seznam = new SpojovySeznam<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(jmeno), StandardCharsets.UTF_8))) {

            List<String> list = br.lines().collect(Collectors.toList());

            for (String listy : list) {
                String[] pole = listy.split(";");

                int id = Integer.parseInt(pole[0]);
                TypHudebnihoNosice typ = TypHudebnihoNosice.fromString(pole[1]);
                int kapacita = Integer.parseInt(pole[2]);
                int rokVyroby = Integer.parseInt(pole[3]);

                HudebniNosice nosic;

                switch (typ) {
                    case CD:
                        CDVyrobci vyrobec = CDVyrobci.fromString(pole[4]);
                        CDPouziti pouziti = CDPouziti.fromString(pole[5]);
                        nosic = new CD(id, kapacita, rokVyroby, vyrobec, pouziti);
                        break;
                    case KAZETA:
                        int vzacnost = Integer.parseInt(pole[4]);
                        KazetaTypCivky typCivky = KazetaTypCivky.fromString(pole[5]);
                        nosic = new Kazeta(id, kapacita, rokVyroby, vzacnost, typCivky);
                        break;
                    case VINYLOVA_DESKA:
                        VinylovaDeskaRychlost rychlost = VinylovaDeskaRychlost.fromString(Integer.parseInt(pole[4]));
                        VinylovaDeskaVelikost velikost = VinylovaDeskaVelikost.fromString(pole[5]);
                        nosic = new VinylovaDeska(id, kapacita, rokVyroby, velikost, rychlost);
                        break;
                    default:
                        throw new IllegalArgumentException("Neznamy typ hudebniho nosice!");
                }
                seznam.vlozPosledni(nosic);
            }
            System.out.println("Seznam úspěšně načten ze souboru " + jmeno);
        } catch (FileNotFoundException e) {
            System.out.println("Soubor " + jmeno + " nebyl nalezen");
        } catch (IOException e) {
            System.out.println("Chyba při čtení ze souboru " + jmeno);
        } catch (IllegalArgumentException e) {
            System.out.println("Chyba při čtení ze souboru " + jmeno + " - chybný formát dat");
        }
        return seznam;
    }
}
