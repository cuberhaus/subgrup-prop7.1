package web.controllers;

import domini.controladors.ControladorDomini;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utilitats.Pair;

import java.util.Map;

@Controller
@RequestMapping("/tipus-item")
public class TipusItemController {

    private final ControladorDomini domini;

    public TipusItemController(ControladorDomini domini) {
        this.domini = domini;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("tipusItems", domini.obtenirNomsTipusItemsCarregats());
        model.addAttribute("seleccionat", domini.obtenirNomTipusItemSeleccionat());
        model.addAttribute("existeixSeleccionat", domini.existeixTipusItemSeleccionat());
        if (domini.existeixTipusItemSeleccionat()) {
            try {
                model.addAttribute("atributs", domini.obtenirValorsDistanciesTipusAtributsTipusItemSeleccionat());
            } catch (Exception e) {
                model.addAttribute("atributs", Map.of());
            }
        }
        return "tipus-item";
    }

    @PostMapping("/seleccionar")
    public String seleccionar(@RequestParam String nom, RedirectAttributes ra) {
        try {
            domini.seleccionarTipusItem(nom);
            ra.addFlashAttribute("success", "Tipus d'ítem '" + nom + "' seleccionat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tipus-item";
    }

    @PostMapping("/deseleccionar")
    public String deseleccionar(RedirectAttributes ra) {
        try {
            domini.desseleccionarTipusItem();
            ra.addFlashAttribute("success", "Tipus d'ítem deseleccionat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tipus-item";
    }

    @PostMapping("/esborrar")
    public String esborrar(RedirectAttributes ra) {
        try {
            domini.esborrarTipusItemSeleccionat();
            ra.addFlashAttribute("success", "Tipus d'ítem esborrat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tipus-item";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam String nom, @RequestParam Map<String, String> params, RedirectAttributes ra) {
        try {
            java.util.Map<String, Pair<String, String>> atributs = new java.util.LinkedHashMap<>();
            int i = 0;
            while (params.containsKey("atribut_nom_" + i)) {
                String nomAtribut = params.get("atribut_nom_" + i);
                String valor = params.get("atribut_valor_" + i);
                String distancia = params.get("atribut_distancia_" + i);
                if (!nomAtribut.isEmpty()) {
                    atributs.put(nomAtribut, new Pair<>(valor, distancia));
                }
                i++;
            }
            domini.crearTipusItem(nom, atributs);
            ra.addFlashAttribute("success", "Tipus d'ítem '" + nom + "' creat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tipus-item";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam String nouNom, RedirectAttributes ra) {
        try {
            domini.editarTipusItem(nouNom);
            ra.addFlashAttribute("success", "Nom canviat a '" + nouNom + "'.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/tipus-item";
    }
}
