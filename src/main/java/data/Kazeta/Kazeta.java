package data.Kazeta;

import data.HudebniNosice;
import data.TypHudebnihoNosice;

import java.util.Locale;

public class Kazeta extends HudebniNosice {

    private int vzacnost; // od 1 do 10
    private KazetaTypCivky typCivky;

    public Kazeta(int id, int kapacita, int rokVyroby, int vzacnost, KazetaTypCivky typCivky) {
        super(id, TypHudebnihoNosice.KAZETA, kapacita, rokVyroby);
        if (vzacnost < 1 || vzacnost > 10) {
            throw new NumberFormatException("Vzacnost musí být v rozmezí 1 - 10");
        }
        this.vzacnost = vzacnost;
        this.typCivky = typCivky;
    }

    public void setVzacnost(int vzacnost) {
        this.vzacnost = vzacnost;
    }

    public void setTypCivky(KazetaTypCivky typCivky) {
        this.typCivky = typCivky;
    }

    public int getVzacnost() {
        return vzacnost;
    }

    public KazetaTypCivky getTypCivky() {
        return typCivky;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, Typ: %s, Kapacita: %s, Rok vyroby: %s, Vzacnost: %s, Typ civky: %s",
                getId(), getTyp(), getKapacita(), getRokVyroby(), getVzacnost(), typCivky.toString());
    }
}
