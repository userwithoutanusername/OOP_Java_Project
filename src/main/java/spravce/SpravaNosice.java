package spravce;

import data.HudebniNosice;
import data.TypHudebnihoNosice;
import generator.Generator;
import kolekce.KolekceException;
import kolekce.SpojovySeznam;
import perzistence.BinarniPerzistence;
import perzistence.TextovaPerzistence;

import java.io.IOException;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

import static data.TypHudebnihoNosice.CD;


public class SpravaNosice implements Ovladani {

    SpojovySeznam<HudebniNosice> seznam = new SpojovySeznam<>();

    private static final Generator generator = new Generator();
    private static final BinarniPerzistence bp = new BinarniPerzistence();
    private static final TextovaPerzistence tp = new TextovaPerzistence();


    public int generujId() {
        return generator.generujId();
    }



    public SpravaNosice() {
        seznam = new SpojovySeznam<>();
    }

    @Override
    public void novy(HudebniNosice data) throws OvladaniException {
        if (data == null) {
            throw new OvladaniException("Data nesmí být null");
        }
        seznam.vlozPosledni(data);
    }

    @Override
    public HudebniNosice najdi(int id) throws OvladaniException {
        Iterator iterator = seznam.iterator();
        HudebniNosice temp1 = null;
        while (iterator.hasNext()) {
            HudebniNosice temp = (HudebniNosice) iterator.next();
            if (temp.getId() == id) {
                temp1 = temp;
            }
        }
        return temp1;
    }

    @Override
    public HudebniNosice odeber(int id) throws OvladaniException {

            HudebniNosice removed = null;
            try {
                seznam.nastavPrvni();
                while (seznam.jeDalsi()) {
                    HudebniNosice temp = seznam.dejAktualni();
                    if (temp.getId() == id) {
                        removed = seznam.odeberAktualni();
                        break;
                    } else {
                        seznam.dalsi();
                    }
                }

                if (!seznam.jeDalsi() && removed == null) {
                    HudebniNosice temp = seznam.dejAktualni();
                    if (temp.getId() == id) {
                        removed = seznam.odeberPosledni();
                    }
                }
            } catch (KolekceException ex) {
                System.err.println(ex.getMessage());
            }
            return removed;

//        if (id == 0) {
//            throw new OvladaniException("Id nesmí být null");
//        }
//
//        HudebniNosice temp = null;
//        Iterator iterator = seznam.iterator();
//
//        while (iterator.hasNext()) {
//            HudebniNosice temp1 = (HudebniNosice) iterator.next();
//            if (temp1.getId() == id) {
//                temp = temp1;
//                iterator.remove();
//            }
//        }
//
//        if (temp == null) {
//            throw new OvladaniException("Hudební nosič s id " + id + " nebyl nalezen.");
//        } else {
//            return temp;
//        }
    }

    @Override
    public HudebniNosice dej() throws OvladaniException {
        HudebniNosice temp = null;
        try {
            temp = seznam.dejAktualni();
        } catch (KolekceException e) {
            throw new OvladaniException("Chyba při získávání aktuální položky.");
        }
        return temp;
    }

    @Override
    public void edituj(HudebniNosice data) throws OvladaniException {
        if (data == null) {
            throw new OvladaniException("Data nesmí být null");
        }
        try {
            seznam.vlozZaAktualni(data);
            seznam.odeberAktualni();
        } catch (KolekceException e) {
            throw new OvladaniException("Chyba při nastavování aktuální položky.");
        }
    }

    @Override
    public void update(int id, HudebniNosice nosic) throws OvladaniException {
        if (id == 0) {
            throw new OvladaniException("Id nesmi byt null!");
        }
        if (nosic == null) {
            throw new OvladaniException("Nosic nesmi byt null!");
        }
        boolean updated = false;
        try {
            prvni();
            while (seznam.jeDalsi()) {
                if (dej().getId() == id) {
                    edituj(nosic);
                    updated = true;
                    break;
                }
                seznam.dalsi();
            }
            if (!updated && dej().getId() == id) {
                edituj(nosic);
                updated = true;
            }
        } catch (KolekceException e) {
            throw new OvladaniException(e.getMessage());
        }
        if (!updated) {
            throw new OvladaniException("Letadlo s id " + id + " nebylo nalezeno!");
        }
    }

    @Override
    public HudebniNosice vyjmi() throws OvladaniException {
        HudebniNosice temp = null;
        try {
            temp = seznam.odeberAktualni();
        } catch (KolekceException e) {
            throw new OvladaniException("Chyba při odebírání aktuální položky.");
        }
        return temp;
    }

    @Override
    public void prvni() throws OvladaniException {
        try {
            seznam.nastavPrvni();
        } catch (KolekceException e) {
            throw new OvladaniException("Chyba při nastavování na první položku.");
        }

    }

    @Override
    public void dalsi() throws OvladaniException {
        try {
            seznam.dalsi();
        } catch (KolekceException e) {
            throw new OvladaniException("Chyba při nastavování na další položku.");
        }
    }

    public void predchozi() throws OvladaniException {
        try {
            seznam.nastavPredchozi();
        } catch (KolekceException e) {
            throw new OvladaniException("Chyba při nastavování na předchozí položku.");
        }
    }

    @Override
    public void posledni() throws OvladaniException {
        try {
            seznam.nastavPosledni();
        } catch (KolekceException e) {
            throw new OvladaniException("Chyba při nastavování na poslední položku.");
        }
    }

    @Override
    public int pocet() {
        return seznam.size();
    }

    @Override
    public void obnov() throws OvladaniException {
        try {
            seznam = new SpojovySeznam<>();
            bp.nacti("soubor.bin", seznam);
            System.out.println("Seznam byl uspesne nacten ze souboru test.bin");
        } catch (IOException e) {
            throw new OvladaniException("Chyba při obnovování seznamu.");
        }
    }

    @Override
    public void zalohuj() throws OvladaniException {
        try {
            BinarniPerzistence.uloz("soubor.bin", seznam);
            System.out.println("Seznam byl uspesne ulozen do souboru soubor.bin");
        } catch (IOException e) {
            throw new OvladaniException("Nepodařilo se zapsat seznam do souboru soubor.bin!");
        }
    }

    @Override
    public SpojovySeznam<HudebniNosice> vypis() throws OvladaniException {
//        if (seznam.size() == 0) {
//            throw new OvladaniException("Seznam je prázdný.");
//        }
        return seznam;
    }

    @Override
    public void nactitext() throws OvladaniException {
        SpojovySeznam<HudebniNosice> temp = TextovaPerzistence.nacti("soubor.txt");
        if (temp.size() > 0) {
            seznam = temp;
        } else {
            throw new OvladaniException("Chyba při načítání ze souboru.");
        }
    }

    @Override
    public void uloztext() throws OvladaniException {
        tp.uloz("soubor.txt", seznam);
    }

    @Override
    public void generuj(int pocet) {
            generator.setPocetID(1);
            seznam.zrus();
            seznam = generator.generate(pocet);
    }

    @Override
    public void zrus() {
        seznam.zrus();
    }

    @Override
    public Iterator<HudebniNosice> iterator() {
        return null;
    }
}
