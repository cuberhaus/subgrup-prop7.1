import Domini.*;

import java.util.HashMap;
import java.util.Map;

public class Main{
    public static void main(String[] args) {
        Usuari u1 = new Usuari(4,"Pol", "123");
        System.out.println(u1.getNom());

        /* Correct usage of Item */

        Map<String, TipusAtribut> tipusAtributMap = new HashMap<>();
        tipusAtributMap.put("durada", new NumericEuclidia());

        TipusItem song = new TipusItem("song", tipusAtributMap);

        Map<String, ValorAtribut> valorAtributMap = new HashMap<>();
        valorAtributMap.put("durada", new ValorNumeric(new NumericEuclidia(), 273));

        Item disco2000 = new Item("1", song, valorAtributMap);
        System.out.println(disco2000.getId());

        /* Wrong usage

        Map<String, ValorAtribut> wrongValorAtributMap = new HashMap<>();
        wrongValorAtributMap.put("durada", new ValorNumeric(new NumericDiscret(), 258));

        Item mondayMorning = new Item("1", song, wrongValorAtributMap);
        System.out.println(mondayMorning.getId());

        */
    }
}
