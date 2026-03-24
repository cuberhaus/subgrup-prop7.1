package web.controllers;

import domini.controladors.ControladorDomini;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TipusItemController.class)
public class TipusItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControladorDomini domini;

    @Test
    public void testIndex() throws Exception {
        when(domini.obtenirNomsTipusItemsCarregats()).thenReturn(new ArrayList<>(Arrays.asList("Films")));
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn("Films");
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat()).thenReturn(Map.of());

        mockMvc.perform(get("/tipus-item"))
                .andExpect(status().isOk())
                .andExpect(view().name("tipus-item"))
                .andExpect(model().attributeExists("tipusItems", "seleccionat", "existeixSeleccionat", "atributs"));
    }

    @Test
    public void testSeleccionarSuccess() throws Exception {
        doNothing().when(domini).seleccionarTipusItem("Films");

        mockMvc.perform(post("/tipus-item/seleccionar").param("nom", "Films"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tipus-item"))
                .andExpect(flash().attributeExists("success"));
        
        verify(domini, times(1)).seleccionarTipusItem("Films");
    }

    @Test
    public void testSeleccionarError() throws Exception {
        doThrow(new RuntimeException("Error selecting")).when(domini).seleccionarTipusItem("Invalid");

        mockMvc.perform(post("/tipus-item/seleccionar").param("nom", "Invalid"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tipus-item"))
                .andExpect(flash().attributeExists("error"));
        
        verify(domini, times(1)).seleccionarTipusItem("Invalid");
    }

    @Test
    public void testDeseleccionar() throws Exception {
        doNothing().when(domini).desseleccionarTipusItem();

        mockMvc.perform(post("/tipus-item/deseleccionar"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/tipus-item"))
                .andExpect(flash().attributeExists("success"));
        
        verify(domini, times(1)).desseleccionarTipusItem();
    }
}