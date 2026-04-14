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

@WebMvcTest(RecomanacioController.class)
public class RecomanacioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControladorDomini domini;

    @Test
    public void testIndexWithSelection() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn("Films");
        when(domini.sessioIniciada()).thenReturn(true);
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title", "genre", "year")));

        mockMvc.perform(get("/recomanacions"))
                .andExpect(status().isOk())
                .andExpect(view().name("recomanacions"))
                .andExpect(model().attributeExists("existeixTipusItem", "tipusItemNom", "sessioIniciada", "nomsAtributs"))
                .andExpect(model().attribute("sessioIniciada", true));
    }

    @Test
    public void testIndexWithoutSelection() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(false);
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn(null);
        when(domini.sessioIniciada()).thenReturn(false);

        mockMvc.perform(get("/recomanacions"))
                .andExpect(status().isOk())
                .andExpect(view().name("recomanacions"))
                .andExpect(model().attribute("existeixTipusItem", false));
    }

    @Test
    public void testObtenirCollaborative() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.sessioIniciada()).thenReturn(true);
        when(domini.obtenirRecomanacioCollaborative(any(), eq(true)))
                .thenReturn(new ArrayList<>(Arrays.asList("1", "2", "3")));
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title")));
        when(domini.obtenirItem("1")).thenReturn(Map.of("title", "Film A"));
        when(domini.obtenirItem("2")).thenReturn(Map.of("title", "Film B"));
        when(domini.obtenirItem("3")).thenReturn(Map.of("title", "Film C"));

        mockMvc.perform(post("/recomanacions/obtenir")
                        .param("metode", "collaborative")
                        .param("filtreInclusiu", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("recomanacions"))
                .andExpect(model().attributeExists("resultats", "resultatIds", "metode"));
    }

    @Test
    public void testObtenirContentBased() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.sessioIniciada()).thenReturn(true);
        when(domini.obtenirRecomanacioContentBased(any(), eq(true)))
                .thenReturn(new ArrayList<>(Arrays.asList("1")));
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title")));
        when(domini.obtenirItem("1")).thenReturn(Map.of("title", "Film A"));

        mockMvc.perform(post("/recomanacions/obtenir")
                        .param("metode", "content-based")
                        .param("filtreInclusiu", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("recomanacions"));
    }

    @Test
    public void testObtenirHybrid() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.sessioIniciada()).thenReturn(true);
        when(domini.obtenirRecomanacioHibrida(any(), eq(true)))
                .thenReturn(new ArrayList<>(Arrays.asList("1")));
        when(domini.obtenirNomsAtributsTipusItemSeleccionat()).thenReturn(
                new ArrayList<>(Arrays.asList("title")));
        when(domini.obtenirItem("1")).thenReturn(Map.of("title", "Film A"));

        mockMvc.perform(post("/recomanacions/obtenir")
                        .param("metode", "hybrid")
                        .param("filtreInclusiu", "true"))
                .andExpect(status().isOk())
                .andExpect(view().name("recomanacions"));
    }

    @Test
    public void testObtenirUnknownMethod() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.sessioIniciada()).thenReturn(true);

        mockMvc.perform(post("/recomanacions/obtenir")
                        .param("metode", "unknown"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recomanacions"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    public void testObtenirWithoutSession() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.sessioIniciada()).thenReturn(false);

        mockMvc.perform(post("/recomanacions/obtenir")
                        .param("metode", "collaborative"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recomanacions"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    public void testObtenirWithoutSelection() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(false);

        mockMvc.perform(post("/recomanacions/obtenir")
                        .param("metode", "collaborative"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/recomanacions"))
                .andExpect(flash().attributeExists("error"));
    }
}
