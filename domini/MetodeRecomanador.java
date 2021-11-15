package Domini;

import java.util.ArrayList;

public abstract class MetodeRecomanador {
    private Usuari[] usuaris;
    private Item[] items;
    private Valoracio[] valoracionsPubliques;
    private Valoracio[] valoracionsPrivades;

    public MetodeRecomanador(Usuari[] usuaris, Item[] items, Valoracio[] valoracions_publiques, Valoracio[] valoracions_privades) {
        this.usuaris = usuaris;
        this.items = items;
        this.valoracionsPubliques = valoracions_publiques;
        this.valoracionsPrivades = valoracions_privades;
    }

    public abstract ArrayList<Item> getRecomanacions(Usuari usuari, Valoracio[] valoracions_usuari, int numRecomanacions);
    public abstract ArrayList<Item> getRecomanacions(Usuari usuari, int numRecomanacions);
    public abstract ArrayList<Pair<Item, Double>>  getRecomanacions(Usuari usuari, ArrayList<Item> conjuntRecomanable, Valoracio[] valoracions_usuari, int numRecomanacions);
    public abstract String avaluaQualitatRecomanacio(Usuari usuari, ArrayList<Item> recomanacio);
    public abstract String avaluaQualitatRecomanacio(ArrayList<Pair<Item, Double>> recomanacio, Valoracio[] valoracionsGuia);
}
