package web.controllers;

import domini.controladors.ControladorDomini;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.ArrayList;

@RestController
public class TestController {
    private final ControladorDomini domini;

    public TestController(ControladorDomini domini) {
        this.domini = domini;
    }

    @GetMapping("/test-obtenir-item")
    public String test() {
        try {
            if (!domini.existeixTipusItemSeleccionat()) {
                domini.seleccionarTipusItem(domini.obtenirNomsTipusItemsCarregats().get(0));
            }
            try {
                domini.iniciarSessio(101, "basic");
            } catch (Exception ignored) {}
            
            ArrayList<String> nomsAtributs = domini.obtenirNomsAtributsTipusItemSeleccionat();
            ArrayList<String> resultats = domini.obtenirRecomanacioCollaborative(nomsAtributs, true);
            
            StringBuilder sb = new StringBuilder();
            for (String itemId : resultats) {
                sb.append("ID: ").append(itemId).append("\n");
                try {
                    Map<String, String> item = domini.obtenirItem(itemId);
                    for (Map.Entry<String, String> entry : item.entrySet()) {
                        sb.append(entry.getKey()).append(": ").append(entry.getValue()).append(" | ");
                    }
                } catch (Exception e) {
                    sb.append("Exception: ").append(e.toString());
                }
                sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }
}
