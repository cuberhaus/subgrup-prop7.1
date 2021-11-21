import domini.classes.atributs.valors.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

public class ValorAtributTest {
    @RunWith(Parameterized.class)
    public static class ParametrizedCopiarTest {
        private final ValorAtribut<?> valor;

        public ParametrizedCopiarTest(ValorAtribut<?> valor) {
            this.valor = valor;
        }

        @Parameterized.Parameters(name = "{index}: copiar")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {new ValorBoolea(true)},
                    {new ValorBoolea(false)},
                    {new ValorCategoric("qualsevol")},
                    {new ValorNumeric(11.0)},
                    {new ValorTextual("qualsevol")},

                    {new ValorConjuntBoolea(new boolean[]{true, false, true})},
                    {new ValorConjuntCategoric(new String[]{"a", "b", "c"})},
                    {new ValorConjuntNumeric(new double[]{0.0, 1.0, -1.0})},
                    {new ValorConjuntTextual(new String[]{"c", "b", "a"})},
            });
        }

        @Test
        public void copiar_HauriaDeRetornarCopia() {
            assertNotSame(valor.copiar(), valor);
            assertEquals(valor.copiar(), valor);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedEqualsTest {
        private final ValorAtribut<?> valor1;
        private final ValorAtribut<?> valor2;
        private final boolean resultat;

        public ParametrizedEqualsTest(ValorAtribut<?> valor1, ValorAtribut<?> valor2, boolean resultat) {
            this.valor1 = valor1;
            this.valor2 = valor2;
            this.resultat = resultat;
        }

        @Parameterized.Parameters(name = "{index}: equals")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    // Valors del mateix tipus
                    {new ValorBoolea(true), new ValorBoolea(true), true},
                    {new ValorBoolea(false), new ValorBoolea(false), true},
                    {new ValorBoolea(true), new ValorBoolea(false), false},
                    {new ValorCategoric("a"), new ValorCategoric("a"), true},
                    {new ValorCategoric("a"), new ValorCategoric("b"), false},
                    {new ValorNumeric(0.0), new ValorNumeric(0.0), true},
                    {new ValorNumeric(0.0), new ValorNumeric(1.0), false},
                    {new ValorTextual("a"), new ValorTextual("a"), true},
                    {new ValorTextual("a"), new ValorTextual("b"), false},

                    {new ValorConjuntBoolea(new boolean[]{true, true}), new ValorConjuntBoolea(new boolean[]{true, true}), true},
                    {new ValorConjuntBoolea(new boolean[]{true, true}), new ValorConjuntBoolea(new boolean[]{true, false}), false},
                    {new ValorConjuntBoolea(new boolean[]{false, false, true}), new ValorConjuntBoolea(new boolean[]{false, false}), false},
                    {new ValorConjuntCategoric(new String[]{"a", "b", "c"}), new ValorConjuntCategoric(new String[]{"a", "b", "c"}), true},
                    {new ValorConjuntCategoric(new String[]{"a", "a"}), new ValorConjuntCategoric(new String[]{"b", "a"}), false},
                    {new ValorConjuntCategoric(new String[]{"a", "b", "c"}), new ValorConjuntCategoric(new String[]{"a"}), false},
                    {new ValorConjuntNumeric(new double[]{0.0, 0.0}), new ValorConjuntNumeric(new double[]{0.0, 0.0}), true},
                    {new ValorConjuntNumeric(new double[]{0.0, 1.0}), new ValorConjuntNumeric(new double[]{0.0, 2.0}), false},
                    {new ValorConjuntNumeric(new double[]{0.0, 0.0, 0.0}), new ValorConjuntNumeric(new double[]{0.0, 0.0}), false},
                    {new ValorConjuntTextual(new String[]{"a", "b", "c"}), new ValorConjuntTextual(new String[]{"a", "b", "c"}), true},
                    {new ValorConjuntTextual(new String[]{"a", "a"}), new ValorConjuntTextual(new String[]{"b", "a"}), false},
                    {new ValorConjuntTextual(new String[]{"a", "b", "c"}), new ValorConjuntTextual(new String[]{"a"}), false},

                    // Valors de tipus diferents
                    {new ValorBoolea(true), new ValorCategoric("true"), false},
                    {new ValorBoolea(true), new ValorNumeric(1.0), false},
                    {new ValorBoolea(true), new ValorTextual("true"), false},
                    {new ValorBoolea(true), new ValorConjuntBoolea(new boolean[]{true}), false},
                    {new ValorBoolea(true), new ValorConjuntCategoric(new String[]{"true"}), false},
                    {new ValorBoolea(true), new ValorConjuntNumeric(new double[]{1.0}), false},
                    {new ValorBoolea(true), new ValorConjuntTextual(new String[]{"true"}), false},

                    {new ValorCategoric("true"), new ValorNumeric(1.0), false},
                    {new ValorCategoric("true"), new ValorTextual("true"), false},
                    {new ValorCategoric("true"), new ValorConjuntBoolea(new boolean[]{true}), false},
                    {new ValorCategoric("true"), new ValorConjuntCategoric(new String[]{"true"}), false},
                    {new ValorCategoric("true"), new ValorConjuntNumeric(new double[]{1.0}), false},
                    {new ValorCategoric("true"), new ValorConjuntTextual(new String[]{"true"}), false},

                    {new ValorNumeric(1.0), new ValorTextual("true"), false},
                    {new ValorNumeric(1.0), new ValorConjuntBoolea(new boolean[]{true}), false},
                    {new ValorNumeric(1.0), new ValorConjuntCategoric(new String[]{"true"}), false},
                    {new ValorNumeric(1.0), new ValorConjuntNumeric(new double[]{1.0}), false},
                    {new ValorNumeric(1.0), new ValorConjuntTextual(new String[]{"true"}), false},

                    {new ValorTextual("true"), new ValorConjuntBoolea(new boolean[]{true}), false},
                    {new ValorTextual("true"), new ValorConjuntCategoric(new String[]{"true"}), false},
                    {new ValorTextual("true"), new ValorConjuntNumeric(new double[]{1.0}), false},
                    {new ValorTextual("true"), new ValorConjuntTextual(new String[]{"true"}), false},

                    {new ValorConjuntBoolea(new boolean[]{true}), new ValorConjuntCategoric(new String[]{"true"}), false},
                    {new ValorConjuntBoolea(new boolean[]{true}), new ValorConjuntNumeric(new double[]{1.0}), false},
                    {new ValorConjuntBoolea(new boolean[]{true}), new ValorConjuntTextual(new String[]{"true"}), false},

                    {new ValorConjuntCategoric(new String[]{"true"}), new ValorConjuntNumeric(new double[]{1.0}), false},
                    {new ValorConjuntCategoric(new String[]{"true"}), new ValorConjuntTextual(new String[]{"true"}), false},

                    {new ValorConjuntNumeric(new double[]{1.0}), new ValorConjuntTextual(new String[]{"true"}), false},

            });
        }

        @Test
        public void equals_HauriaDeRetornar_Cert_Quan_ElsValorsSonIguals_I_Fals_Altrament() {
            assertEquals(valor1.equals(valor2), resultat);
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedObtenirValorTest {
        private final ValorAtribut<?> valor;
        private final Object resultat;


        public ParametrizedObtenirValorTest(ValorAtribut<?> valor, Object resultat) {
            this.valor = valor;
            this.resultat = resultat;
        }

        @Parameterized.Parameters(name = "{index}: obtenirValor")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {new ValorBoolea(true), true},
                    {new ValorBoolea(false), false},
                    {new ValorCategoric("qualsevol"), "qualsevol"},
                    {new ValorNumeric(11.0), 11.0},
                    {new ValorTextual("qualsevol"), "qualsevol"},

                    {new ValorConjuntBoolea(new boolean[]{true, false, true}), new ArrayList<>(Arrays.asList(new ValorBoolea(true), new ValorBoolea(false), new ValorBoolea(true)))},
                    {new ValorConjuntCategoric(new String[]{"a", "b", "c"}), new ArrayList<>(Arrays.asList(new ValorCategoric("a"), new ValorCategoric("b"), new ValorCategoric("c")))},
                    {new ValorConjuntNumeric(new double[]{0.0, 1.0, -1.0}), new ArrayList<>(Arrays.asList(new ValorNumeric(0.0), new ValorNumeric(1.0), new ValorNumeric(-1.0)))},
                    {new ValorConjuntTextual(new String[]{"c", "b", "a"}), new ArrayList<>(Arrays.asList(new ValorTextual("c"), new ValorTextual("b"), new ValorTextual("a")))},
            });
        }

        @Test
        public void obtenirValor_HauriaDeRetornarValor() {
            assertEquals(valor.obtenirValor(), resultat);
        }
    }
}
