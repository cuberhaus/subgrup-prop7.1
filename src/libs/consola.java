package libs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Conté funcions estàtiques que faciliten la lectura de tipus bàsics des de la consola.
 * @author maria.prat
 */
public class consola {
    private static final inout consola = new inout();
    private static final BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

    public consola() {}

    public static int llegirInt(String missatgeConsulta, String missatgeError, int valorMin, int valorMax) {
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

    public static String llegirString(String missatgeConsulta) {
        while (true) {
            try {
                System.out.println(missatgeConsulta);
                return lector.readLine();
            } catch (Exception ignored) {
            }
        }
    }

    public static double llegirDouble(String missatgeConsulta, String missatgeError, double valorMin, double valorMax) {
        boolean incorrecte = false;
        while (true) {
            try {
                if (incorrecte) {
                    System.out.println(missatgeError);
                }
                System.out.println(missatgeConsulta);
                double entrada = consola.readdouble();
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
}
