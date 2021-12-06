package utilitats;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Conté funcions estàtiques que faciliten la lectura de tipus bàsics des de la consola.
 * @author maria.prat
 */
public class consola {
    private static final inout consola = new inout();
    private static final BufferedReader lector = new BufferedReader(new InputStreamReader(System.in));

    /**
     * @param missatgeConsulta Missatge que es mostrarà a la terminal a l'hora de fer la consulta.
     * @param missatgeError Missatge que es mostrarà a la terminal si hi ha un error en la lectura.
     * @param valorMin Valor mínim de l'enter que es vol llegir.
     * @param valorMax Valor màxim de l'enter que es vol llegir.
     * @return <code>int</code> llegit des de la terminal.
     */
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

    /**
     * @param missatgeConsulta Missatge que es mostrarà a la terminal a l'hora de fer la consulta.
     * @return <code>String</code> llegida des de la terminal.
     */
    public static String llegirString(String missatgeConsulta) {
        while (true) {
            try {
                System.out.println(missatgeConsulta);
                return lector.readLine();
            } catch (Exception ignored) {
            }
        }
    }

    /**
     * @param missatgeConsulta Missatge que es mostrarà a la terminal a l'hora de fer la consulta.
     * @param missatgeError Missatge que es mostrarà a la terminal si hi ha un error en la lectura.
     * @param valorMin Valor mínim del double que es vol llegir.
     * @param valorMax Valor màxim del double que es vol llegir.
     * @return <code>double</code> llegit des de la terminal.
     */
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
