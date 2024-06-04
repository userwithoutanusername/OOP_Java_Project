package spravce;

import data.HudebniNosice;
import kolekce.SpojovySeznam;

import java.util.HexFormat;

public interface Ovladani extends Iterable<HudebniNosice> {
    void novy(HudebniNosice data) throws OvladaniException;

    HudebniNosice najdi(int id) throws OvladaniException;

    HudebniNosice odeber(int id) throws OvladaniException;

    HudebniNosice dej() throws OvladaniException;

    void edituj(HudebniNosice data) throws OvladaniException;


    void update(int id, HudebniNosice letadlo) throws OvladaniException;

    HudebniNosice vyjmi() throws OvladaniException;

    void prvni() throws OvladaniException;

    void dalsi() throws OvladaniException;

    void posledni() throws OvladaniException;

    int pocet();

    void obnov() throws OvladaniException;

    void zalohuj() throws OvladaniException;

    SpojovySeznam<HudebniNosice> vypis() throws OvladaniException;

    void nactitext() throws OvladaniException;

    void uloztext() throws OvladaniException;

    void generuj(int pocet);

    void zrus();
}
