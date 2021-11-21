package domini.tests;

import domini.classes.ConjuntItems;
import domini.classes.Id;
import domini.classes.Item;
import domini.classes.TipusItem;
import domini.classes.atributs.TipusAtribut;
import domini.classes.atributs.distancia.DistanciaLevenshtein;
import domini.classes.atributs.valors.ValorCategoric;
import domini.classes.recomanador.filtre.FiltreInclusiu;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Unit tests de la classe FiltreInclusiu
 */

@RunWith(Enclosed.class)
public class FiltreInclusiuTest {

    private static class MockConjuntItems extends ConjuntItems {
        public static final TipusItem mockTipusItemAutorTitol = new TipusItem("Llibres", new TreeMap<>(Map.of(
                "Autor", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein()),
                "Titol", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
        )));

        public static final TipusItem mockTipusItemAutor = new TipusItem("Llibres", new TreeMap<>(Map.of(
                "Autor", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
        )));

        public static final TipusItem mockTipusItemTitol = new TipusItem("Llibres", new TreeMap<>(Map.of(
                "Titol", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
        )));

        public static final TreeMap<Id, Item> mockElementsAutorTitol = new TreeMap<>(Map.of(
                new Id(1, true),
                new Item(new Id(1, true), new TipusItem("Llibres", new TreeMap<>(Map.of(
                        "Autor", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein()),
                        "Titol", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
                ))),
                        new TreeMap<>(Map.of(
                                "Autor", new ValorCategoric("Gabriel García Márquez"),
                                "Titol", new ValorCategoric("Cien años de soledad")
                        )), new TreeMap<>()),
                new Id(2, true),
                new Item(new Id(2, true), new TipusItem("Llibres", new TreeMap<>(Map.of(
                        "Autor", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein()),
                        "Titol", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
                ))),
                        new TreeMap<>(Map.of(
                                "Autor", new ValorCategoric("Gabriel García Márquez"),
                                "Titol", new ValorCategoric("El general en su laberinto")
                        )), new TreeMap<>())));

        public static final TreeMap<Id, Item> mockElementsAutor = new TreeMap<>(Map.of(
                        new Id(1, true),
                        new Item(new Id(1, true), new TipusItem("Llibres", new TreeMap<>(Map.of(
                                "Autor", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
                        ))),
                                new TreeMap<>(Map.of(
                                        "Autor", new ValorCategoric("Gabriel García Márquez")
                                )), new TreeMap<>()),
                        new Id(2, true),
                        new Item(new Id(2, true), new TipusItem("Llibres", new TreeMap<>(Map.of(
                                "Autor", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
                        ))),
                                new TreeMap<>(Map.of(
                                        "Autor", new ValorCategoric("Gabriel García Márquez")
                                )), new TreeMap<>())));

        public static final TreeMap<Id, Item> mockElementsTitol = new TreeMap<>(Map.of(
                        new Id(1, true),
                        new Item(new Id(1, true), new TipusItem("Llibres", new TreeMap<>(Map.of(
                                "Titol", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
                        ))),
                                new TreeMap<>(Map.of(
                                        "Titol", new ValorCategoric("Cien años de soledad")
                                )), new TreeMap<>()),
                        new Id(2, true),
                        new Item(new Id(2, true), new TipusItem("Llibres", new TreeMap<>(Map.of(
                                "Titol", new TipusAtribut(new ValorCategoric(), new DistanciaLevenshtein())
                        ))),
                                new TreeMap<>(Map.of(
                                        "Titol", new ValorCategoric("El general en su laberinto")
                                )), new TreeMap<>())));

        public MockConjuntItems(TipusItem tipusItem, TreeMap<Id, Item> elements) {
            super(tipusItem, elements);
        }

        @Override
        public void esborrarAtributs(TreeSet<String> nomAtributs) {
            System.out.println("¿¿¿" + nomAtributs.toString());
            if (nomAtributs.contains("Autor") && nomAtributs.contains("Titol")) {
                this.tipusItem = new TipusItem("Llibres");
                this.elements = new TreeMap<>();
            } else if (nomAtributs.contains("Autor")) {
                this.tipusItem = mockTipusItemTitol;
                this.elements = mockElementsTitol;
            } else if (nomAtributs.contains("Titol")) {
                this.tipusItem = mockTipusItemAutor;
                this.elements = mockElementsAutor;
            } else {
                this.tipusItem = mockTipusItemAutorTitol;
                this.elements = mockElementsAutorTitol;
            }
        }
    }

    @RunWith(Parameterized.class)
    public static class ParametrizedFiltrarTest {
        private final ArrayList<String> nomAtributs;
        private final MockConjuntItems conjuntItems;
        private final ConjuntItems conjuntItemsResultat;

        public ParametrizedFiltrarTest(ArrayList<String> nomAtributs, MockConjuntItems conjuntItems,
                                       ConjuntItems conjuntItemsResultat) {
            this.nomAtributs = nomAtributs;
            this.conjuntItems = conjuntItems;
            this.conjuntItemsResultat = conjuntItemsResultat;
        }

        @Parameterized.Parameters(name = "{index}: filtrar")
        public static Collection<Object[]> dades() {
            return Arrays.asList(new Object[][]{
                    {new ArrayList<>(List.of("Cap")),
                            new MockConjuntItems(MockConjuntItems.mockTipusItemAutorTitol, MockConjuntItems.mockElementsAutorTitol),
                            new ConjuntItems(new TipusItem("Llibres"), new TreeMap<>(Map.of(
                                    new Id(1, true),
                                    new Item(new Id(1, true), new TipusItem("Llibres", new TreeMap<>()),
                                            new TreeMap<>(), new TreeMap<>()),
                                    new Id(2, true),
                                    new Item(new Id(2, true), new TipusItem("Llibres", new TreeMap<>()),
                                            new TreeMap<>(), new TreeMap<>()))))
                    },
                    {new ArrayList<>(List.of("Titol")),
                            new MockConjuntItems(MockConjuntItems.mockTipusItemAutorTitol, MockConjuntItems.mockElementsAutorTitol),
                            new ConjuntItems(MockConjuntItems.mockTipusItemTitol, MockConjuntItems.mockElementsTitol)
                    },
                    {new ArrayList<>(List.of("Autor")),
                            new MockConjuntItems(MockConjuntItems.mockTipusItemAutor, MockConjuntItems.mockElementsAutor),
                            new ConjuntItems(MockConjuntItems.mockTipusItemAutor, MockConjuntItems.mockElementsAutor)
                    },
                    {new ArrayList<>(Arrays.asList("Autor", "Titol")),
                            new MockConjuntItems(MockConjuntItems.mockTipusItemAutorTitol, MockConjuntItems.mockElementsAutorTitol),
                            new ConjuntItems(MockConjuntItems.mockTipusItemAutorTitol, MockConjuntItems.mockElementsAutorTitol)
                    },
            });
        }

        @Test
        public void filtrar_HauriaDeFiltrarConjuntItems() {
            FiltreInclusiu filtreInclusiu = new FiltreInclusiu(nomAtributs);
            ConjuntItems conjuntItemsFiltrat = conjuntItems.copiar();
            filtreInclusiu.filtrar(conjuntItemsFiltrat);
            assertEquals(conjuntItemsFiltrat, conjuntItemsResultat);
        }
    }
}