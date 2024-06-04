package data.CD;

import data.HudebniNosice;
import data.TypHudebnihoNosice;

import java.util.Locale;

public class CD extends HudebniNosice {

    private CDVyrobci vyrobec;
    private CDPouziti pouziti;

    public CD(int id, int kapacita, int rokVyroby, CDVyrobci vyrobec, CDPouziti pouziti) {
        super(id, TypHudebnihoNosice.CD, kapacita, rokVyroby);
        this.vyrobec = vyrobec;
        this.pouziti = pouziti;
    }

    public void setVyrobec(CDVyrobci vyrobec) {
        this.vyrobec = vyrobec;
    }

    public void setPouziti(CDPouziti pouziti) {
        this.pouziti = pouziti;
    }

    public CDVyrobci getVyrobec() {
        return vyrobec;
    }

    public CDPouziti getPouziti() {
        return pouziti;
    }

    @Override
    public String toString() {
        return String.format("%s, Typ: %s, Kapacita: %s,, Rok vyroby: %s, Vyrobec: %s, Pouziti: %s",
                getId(), getTyp(), getKapacita(), getRokVyroby(), vyrobec.toString(), pouziti.toString());
    }
}
