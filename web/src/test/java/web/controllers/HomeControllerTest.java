package web.controllers;

import domini.controladors.ControladorDomini;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControladorDomini domini;

    @Test
    public void testHome() throws Exception {
        // Setup mock behavior
        when(domini.obtenirNomsTipusItemsCarregats()).thenReturn(new ArrayList<>(Arrays.asList("Films", "Books")));
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn("Films");
        when(domini.obtenirUsuaris()).thenReturn(new ArrayList<>(Arrays.asList(new ArrayList<>(), new ArrayList<>())));
        when(domini.existeixTipusItemSeleccionat()).thenReturn(true);
        when(domini.obtenirNombreItems()).thenReturn(150);
        when(domini.obtenirNombreValoracions()).thenReturn(500);
        when(domini.sessioIniciada()).thenReturn(true);

        // Perform GET request and assert results
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeExists("tipusItems", "tipusItemSeleccionat", "numUsuaris", "numItems", "numValoracions", "sessioIniciada"))
                .andExpect(model().attribute("tipusItemSeleccionat", "Films"))
                .andExpect(model().attribute("numUsuaris", 2))
                .andExpect(model().attribute("numItems", 150))
                .andExpect(model().attribute("numValoracions", 500))
                .andExpect(model().attribute("sessioIniciada", true));
    }

    @Test
    public void testHomeWithoutSelection() throws Exception {
        // Setup mock behavior for when nothing is selected
        when(domini.obtenirNomsTipusItemsCarregats()).thenReturn(new ArrayList<>());
        when(domini.obtenirNomTipusItemSeleccionat()).thenReturn(null);
        when(domini.obtenirUsuaris()).thenReturn(new ArrayList<>());
        when(domini.existeixTipusItemSeleccionat()).thenReturn(false);
        when(domini.sessioIniciada()).thenReturn(false);

        // Perform GET request and assert results
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("home"))
                .andExpect(model().attributeDoesNotExist("tipusItemSeleccionat"))
                .andExpect(model().attribute("numUsuaris", 0))
                .andExpect(model().attribute("numItems", 0))
                .andExpect(model().attribute("numValoracions", 0))
                .andExpect(model().attribute("sessioIniciada", false));
    }
}