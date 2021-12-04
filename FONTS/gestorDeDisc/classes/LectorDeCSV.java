package gestorDeDisc.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe que implementa el lector de CSV
 * @author pablo.vega
 */

public class LectorDeCSV {
    public LectorDeCSV() {}

    /**
     * Funció que donat un pathname, et retorna la taula amb el contingut del CSV
     * @param ubicacio <code>String</code> pathname amb la ubicació de l'arxiu.
     * @return <code>ArrayList&lt;ArrayList&lt;String&gt;&gt;</code> amb el contingut de lal CSV
     * @throws IOException si no existeix el ftixer llença exepcio
     */
    public ArrayList<ArrayList<String>> llegirCSV(String ubicacio) throws IOException {
        ArrayList<ArrayList<String>> tabla = new ArrayList<>();

        BufferedReader fitxer = new BufferedReader(new FileReader(ubicacio));
        String fila;

        ArrayList<String> atributs = new ArrayList<>();
        if ((fila = fitxer.readLine()) != null) {
            atributs.addAll(Arrays.asList(fila.split(",")));
            tabla.add(new ArrayList<>(atributs));
        }

        ArrayList<String> valors = new ArrayList<>();
        while ((fila = fitxer.readLine()) != null) {
            fila = fila + ' ';
            char[] caracters = fila.toCharArray();
            boolean cometes = true;
            boolean entreCometes = false;

            ArrayList<Integer> posicioCometes = new ArrayList<>();
            ArrayList<Integer> posicioComes = new ArrayList<>();

            for (int i = 1; i < caracters.length; ++i) {
                if (entreCometes && cometes && caracters[i - 1] == '\"' && caracters[i] == ',') {
                    caracters[i - 1] = ' ';
                    posicioCometes.add(i - 1);
                    entreCometes = false;
                }

                else if (!entreCometes && caracters[i - 1] == ',' && caracters[i] == '\"') {
                    cometes = true;
                    caracters[i] = ' ';
                    posicioCometes.add(i);
                    entreCometes = true;
                }

                if (entreCometes && caracters[i - 1] == '\"') {
                    cometes = !cometes;
                    posicioCometes.add(i - 1);
                    caracters[i - 1] = '\'';
                }

                if (entreCometes && caracters[i] == ',') {
                    posicioComes.add(i);
                    caracters[i] = ';';
                }
            }

            String actual = String.valueOf(caracters);
            String[] temp = actual.split(",");

            valors.addAll(Arrays.asList(temp));

            ArrayList<String> temporal = new ArrayList<>();
            Integer index = 0;
            for (String valor : valors) {
                char[] lletres = valor.toCharArray();
                for (int i = 0; i < lletres.length; ++i) {
                    if (posicioComes.contains(index)) lletres[i] = ',';
                    if (posicioCometes.contains(index)) lletres[i] = '\"';
                    ++index;
                }
                temporal.add(String.valueOf(lletres));
                ++index;
            }

            tabla.add(new ArrayList<>(temporal));
            valors.clear();
        }

        atributs.clear();
        fitxer.close();
        return tabla;
    }
}
