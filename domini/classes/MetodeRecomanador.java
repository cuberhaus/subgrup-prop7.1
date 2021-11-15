package domini.classes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
// TODO: javadoc

public abstract class MetodeRecomanador {
    protected Usuari[] usuaris;
    protected Item[] items;
    protected Valoracio[] valoracionsPubliques;

    public MetodeRecomanador(Usuari[] usuaris, Item[] items, Valoracio[] valoracions_publiques) {
        this.usuaris = usuaris;
        this.items = items;
        this.valoracionsPubliques = valoracions_publiques;
    }

    public ConjuntDeRecomanacions obteRecomanacions(Usuari usuari, Valoracio[] valoracions_usuari, int numRecomanacions) {
        return obteRecomanacions(usuari, new ArrayList<>(List.of(items)), valoracions_usuari, numRecomanacions);
    }

    public ConjuntDeRecomanacions obteRecomanacions(Usuari usuari, int numRecomanacions) {
        ArrayList<Valoracio> valoracions_usuari = new ArrayList<>();
        for (var x : valoracionsPubliques) {
            if (x.getUsuari().equals(usuari)) {
                valoracions_usuari.add(x);
            }
        }
        return obteRecomanacions(usuari, valoracions_usuari.toArray(new Valoracio[0]), numRecomanacions);
    }

    public abstract ConjuntDeRecomanacions obteRecomanacions(Usuari usuari, ArrayList<Item> conjuntRecomanable, Valoracio[] valoracions_usuari, int numRecomanacions);
}
