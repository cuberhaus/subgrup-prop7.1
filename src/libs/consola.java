package libs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * @author maria.prat
 */
public class consola {
    private static final inout consola = new inout();
    private static final BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

    public consola() {}

    public static int llegeixEnter(String missatgeConsulta, String missatgeError, int valorMin, int valorMax) {
        boolean incorrecte = false;
        while (true) {
            try {
                if (incorrecte) {
                    System.out.println(missatgeError);
                }
                System.out.println(missatgeConsulta);
                int entrada = consola.readint();
                if (entrada >= valorMin && entrada <= valorMax) {
                    return entrada;
                } else {
                    incorrecte = true;
                }
            } catch (Exception e) {
                incorrecte = true;
            }
        }
    }

    public static String obtenirString(String missatgeConsulta) {
        while (true) {
            try {
                System.out.println(missatgeConsulta);
                return lector.readLine();
            } catch (Exception ignored) {
            }
        }
    }
}
