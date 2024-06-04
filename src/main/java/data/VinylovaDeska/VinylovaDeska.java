package data.VinylovaDeska;

import data.HudebniNosice;
import data.TypHudebnihoNosice;

public class VinylovaDeska extends HudebniNosice {

    private VinylovaDeskaVelikost velikost;
    private VinylovaDeskaRychlost rychlost;

    public VinylovaDeska(int id, int kapacita, int rokVyroby, VinylovaDeskaVelikost velikost, VinylovaDeskaRychlost rychlost) {
        super(id, TypHudebnihoNosice.VINYLOVA_DESKA, kapacita, rokVyroby);
        this.velikost = velikost;
        this.rychlost = rychlost;
    }

    public void setVelikost(VinylovaDeskaVelikost velikost) {
        this.velikost = velikost;
    }

    public void setRychlost(VinylovaDeskaRychlost rychlost) {
        this.rychlost = rychlost;
    }

    public VinylovaDeskaVelikost getVelikost() {
        return velikost;
    }

    public VinylovaDeskaRychlost getRychlost() {
        return rychlost;
    }

    @Override
    public String toString() {
        return String.format("%s, Typ: %s, Kapacita: %s, Rok vyroby: %s, Velikost: %s, Rychlost: %s",
                getId(), getTyp(), getKapacita(), getRokVyroby(), getVelikost(), getRychlost());
    }
}
