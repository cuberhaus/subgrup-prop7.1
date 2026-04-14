package web.controllers;

import domini.controladors.ControladorDomini;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ValoracioController.class)
public class ValoracioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControladorDomini domini;

    @Test
    public void testIndexWithSelection() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn("Films");
        when(domini.obtenirValoracions()).thenReturn(new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("user1", "item1", "5.0")),
                new ArrayList<>(Arrays.asList("user2", "item2", "3.5"))
        )));

        mockMvc.perform(get("/valoracions"))
                .andExpect(status().isOk())
                .andExpect(view().name("valoracions"))
                .andExpect(model().attributeExists("existeixTipusItem", "tipusItemNom", "valoracions"))
                .andExpect(model().attribute("existeixTipusItem", true));
    }

    @Test
    public void testIndexWithoutSelection() throws Exception {
        when(domini.existeixTipusItemSeleccionat()).thenReturn(false);
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn(null);

        mockMvc.perform(get("/valoracions"))
                .andExpect(status().isOk())
                .andExpect(view().name("valoracions"))
                .andExpect(model().attribute("existeixTipusItem", false));
    }

    @Test
    public void testAfegirValoracio() throws Exception {
        doNothing().when(domini).afegirValoracio("user1", "item1", "4.5");

        mockMvc.perform(post("/valoracions/afegir")
                        .param("usuariId", "user1")
                        .param("itemId", "item1")
                        .param("valor", "4.5"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/valoracions"))
                .andExpect(flash().attributeExists("success"));

        verify(domini, times(1)).afegirValoracio("user1", "item1", "4.5");
    }

    @Test
    public void testEditarValoracio() throws Exception {
        doNothing().when(domini).editarValoracio("user1", "item1", "3.0");

        mockMvc.perform(post("/valoracions/editar")
                        .param("usuariId", "user1")
                        .param("itemId", "item1")
                        .param("valor", "3.0"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/valoracions"))
                .andExpect(flash().attributeExists("success"));

        verify(domini, times(1)).editarValoracio("user1", "item1", "3.0");
    }

    @Test
    public void testEsborrarValoracio() throws Exception {
        doNothing().when(domini).esborraValoracio("user1", "item1");

        mockMvc.perform(post("/valoracions/esborrar")
                        .param("usuariId", "user1")
                        .param("itemId", "item1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/valoracions"))
                .andExpect(flash().attributeExists("success"));

        verify(domini, times(1)).esborraValoracio("user1", "item1");
    }

    @Test
    public void testEsborrarTotesValoracions() throws Exception {
        doNothing().when(domini).esborrarTotesLesValoracions();

        mockMvc.perform(post("/valoracions/esborrar-totes"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/valoracions"))
                .andExpect(flash().attributeExists("success"));

        verify(domini, times(1)).esborrarTotesLesValoracions();
    }

    @Test
    public void testAfegirValoracioError() throws Exception {
        doThrow(new RuntimeException("Invalid rating")).when(domini)
                .afegirValoracio("user1", "item1", "invalid");

        mockMvc.perform(post("/valoracions/afegir")
                        .param("usuariId", "user1")
                        .param("itemId", "item1")
                        .param("valor", "invalid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/valoracions"))
                .andExpect(flash().attributeExists("error"));
    }
}
