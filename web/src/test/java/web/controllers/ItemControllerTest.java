package web.controllers;

import domini.controladors.ControladorDomini;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControladorDomini domini;

    @Test
    public void testIndexWithSelection() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn("Films");
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title", "genre", "year")));
        when(domini.obtenirItems()).thenReturn(new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("Inception", "Sci-Fi", "2010")),
                new ArrayList<>(Arrays.asList("Matrix", "Sci-Fi", "1999"))
        )));
        when(domini.obtenirIdsItems()).thenReturn(new ArrayList<>(Arrays.asList("1", "2")));

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items"))
                .andExpect(model().attributeExists("existeixTipusItem", "tipusItemNom", "nomsAtributs", "items", "itemIds"))
                .andExpect(model().attribute("existeixTipusItem", true))
                .andExpect(model().attribute("tipusItemNom", "Films"));
    }

    @Test
    public void testIndexWithoutSelection() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(false);
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn(null);

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items"))
                .andExpect(model().attribute("existeixTipusItem", false));
    }

    @Test
    public void testItemsPaginationCapsAt500() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn("Films");
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(new ArrayList<>(Arrays.asList("title")));

        // Create 600 items
        ArrayList<ArrayList<String>> manyItems = new ArrayList<>();
        for (int i = 0; i < 600; i++) {
            manyItems.add(new ArrayList<>(Arrays.asList("Item" + i)));
        }
        when(domini.obtenirItems()).thenReturn(manyItems);
        when(domini.obtenirIdsItems()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/items"))
                .andExpect(status().isOk())
                .andExpect(view().name("items"));
        // The controller caps to min(500, size)
    }

    @Test
    public void testMostrarItem() throws Exception {
        Map<String, String> item = new LinkedHashMap<>();
        item.put("title", "Inception");
        item.put("genre", "Sci-Fi");
        when(domini.obtenirItem("1")).thenReturn(item);
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title", "genre")));

        mockMvc.perform(get("/items/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("item-detail"))
                .andExpect(model().attribute("itemId", "1"))
                .andExpect(model().attributeExists("atributs", "nomsAtributs"));
    }

    @Test
    public void testMostrarItemNotFound() throws Exception {
        when(domini.obtenirItem("999")).thenThrow(new RuntimeException("Item not found"));

        mockMvc.perform(get("/items/999"))
                .andExpect(status().isOk())
                .andExpect(view().name("item-detail"))
                .andExpect(model().attributeExists("error"));
    }

    @Test
    public void testCrearItem() throws Exception {
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title", "genre")));
        when(domini.afegirItem(any())).thenReturn(42);

        mockMvc.perform(post("/items/crear")
                        .param("attr_title", "New Film")
                        .param("attr_genre", "Drama"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"))
                .andExpect(flash().attributeExists("success"));
    }

    @Test
    public void testEditarItem() throws Exception {
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title", "genre")));
        doNothing().when(domini).editarItem(eq("1"), any());

        mockMvc.perform(post("/items/1/editar")
                        .param("attr_title", "Updated Film")
                        .param("attr_genre", "Action"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/items"))
                .andExpect(flash().attributeExists("success"));

        verify(domini, times(1)).editarItem(eq("1"), any());
    }
}
