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

@WebMvcTest(UsuariController.class)
public class UsuariControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ControladorDomini domini;

    @Test
    public void testIndex() throws Exception {
        when(domini.obtenirUsuaris()).thenReturn(new ArrayList<>(Arrays.asList(
                new ArrayList<>(Arrays.asList("1", "User1")),
                new ArrayList<>(Arrays.asList("2", "User2"))
        )));
        when(domini.sessioIniciada()).thenReturn(true);
        when(domini.obtenirSessio()).thenReturn(1);

        mockMvc.perform(get("/usuaris"))
                .andExpect(status().isOk())
                .andExpect(view().name("usuaris"))
                .andExpect(model().attributeExists("usuaris", "sessioIniciada", "sessioId"))
                .andExpect(model().attribute("sessioIniciada", true))
                .andExpect(model().attribute("sessioId", "1"));
    }

    @Test
    public void testAfegirSuccess() throws Exception {
        when(domini.afegirUsuari("newuser", "password123")).thenReturn(3);

        mockMvc.perform(post("/usuaris/afegir")
                .param("nom", "newuser")
                .param("contrasenya", "password123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuaris"))
                .andExpect(flash().attributeExists("success"));
        
        verify(domini, times(1)).afegirUsuari("newuser", "password123");
    }

    @Test
    public void testAfegirError() throws Exception {
        when(domini.afegirUsuari("baduser", "")).thenThrow(new RuntimeException("Invalid password"));

        mockMvc.perform(post("/usuaris/afegir")
                .param("nom", "baduser")
                .param("contrasenya", ""))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuaris"))
                .andExpect(flash().attributeExists("error"));
    }

    @Test
    public void testIniciarSessioSuccess() throws Exception {
        doNothing().when(domini).iniciarSessio(1, "password");

        mockMvc.perform(post("/usuaris/iniciar-sessio")
                .param("id", "1")
                .param("contrasenya", "password"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuaris"))
                .andExpect(flash().attributeExists("success"));
        
        verify(domini, times(1)).iniciarSessio(1, "password");
    }

    @Test
    public void testIniciarSessioError() throws Exception {
        doThrow(new RuntimeException("Wrong password")).when(domini).iniciarSessio(1, "wrongpass");

        mockMvc.perform(post("/usuaris/iniciar-sessio")
                .param("id", "1")
                .param("contrasenya", "wrongpass"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuaris"))
                .andExpect(flash().attributeExists("error"));
        
        verify(domini, times(1)).iniciarSessio(1, "wrongpass");
    }

    @Test
    public void testTancarSessio() throws Exception {
        doNothing().when(domini).tancarSessio();

        mockMvc.perform(post("/usuaris/tancar-sessio"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/usuaris"))
                .andExpect(flash().attributeExists("success"));
        
        verify(domini, times(1)).tancarSessio();
    }
}