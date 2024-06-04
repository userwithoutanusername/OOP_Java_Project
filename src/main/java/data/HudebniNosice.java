package data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Locale;

public abstract class HudebniNosice implements Serializable {

    private int id;
    private TypHudebnihoNosice typ;
    private int kapacita; // v minutech
    private int rokVyroby;

    public HudebniNosice(int id, TypHudebnihoNosice typ, int kapacita, int rokVyroby) {
        if (rokVyroby < 1900 || rokVyroby > 2023) {
            throw new NumberFormatException("Rok vyroby musí být v rozmezí 1900 - 2023");
        }
        if (kapacita < 5) {
            throw new NumberFormatException("Kapacita musí být větší než 5");
        }

        this.id = id;
        this.typ = typ;
        this.kapacita = kapacita;
        this.rokVyroby = rokVyroby;
    }

    public HudebniNosice(int id, int kapacita, int rokVyroby) {
        if (rokVyroby < 1900 || rokVyroby > 2023) {
            throw new NumberFormatException("Rok vyroby musí být v rozmezí 1900 - 2023");
        }
        if (kapacita < 5) {
            throw new IllegalArgumentException("Kapacita musí být větší než 5");
        }

        this.id = id;
        this.kapacita = kapacita;
        this.rokVyroby = rokVyroby;
    }

    public TypHudebnihoNosice getTyp() {
        return typ;
    }

    public int getId() {
        return id;
    }

    public int getKapacita() {
        return kapacita;
    }

    public int getRokVyroby() {
        return rokVyroby;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setKapacita(int kapacita) {
        this.kapacita = kapacita;
    }

    public void setRokVyroby(int rokVyroby) {
        this.rokVyroby = rokVyroby;
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "%s, Typ: %s, Kapacita: %d, Rok vyroby: %s", id, typ.toString(), getKapacita(), getRokVyroby());
    }
}
