package Domini;

import java.util.ArrayList;

public class RecomanadorCollaborative extends MetodeRecomanador {
    public RecomanadorCollaborative(Usuari[] usuaris, Item[] items, Valoracio[] valoracions_publiques, Valoracio[] valoracions_privades) {
        super(usuaris, items, valoracions_publiques, valoracions_privades);
    }

    @Override
    public ArrayList<Item> getRecomanacions(Usuari usuari, Valoracio[] valoracions_usuari, int numRecomanacions) {
        return null;
    }

    @Override
    public ArrayList<Item> getRecomanacions(Usuari usuari, int numRecomanacions) {
        return null;
    }

    @Override
    public ArrayList<Pair<Item, Double>>  getRecomanacions(Usuari usuari, ArrayList<Item> conjuntRecomanable, Valoracio[] valoracions_usuari, int numRecomanacions) {
        return null;
    }

    @Override
    public String avaluaQualitatRecomanacio(Usuari usuari, ArrayList<Item> recomanacio) {
        return null;
    }

    @Override
    public String avaluaQualitatRecomanacio(ArrayList<Pair<Item, Double>> recomanacio, Valoracio[] valoracionsGuia) {
        return null;
    }
}
