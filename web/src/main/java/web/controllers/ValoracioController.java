package web.controllers;

import domini.controladors.ControladorDomini;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/valoracions")
public class ValoracioController {

    private final ControladorDomini domini;

    public ValoracioController(ControladorDomini domini) {
        this.domini = domini;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("existeixTipusItem", domini.existeixTipusItemSeleccionat());
        model.addAttribute("tipusItemNom", domini.obtenirNomTipusItemSeleccionat());
        if (domini.existeixTipusItemSeleccionat()) {
            model.addAttribute("valoracions", domini.obtenirValoracions());
        } else {
            model.addAttribute("valoracions", new ArrayList<>());
        }
        return "valoracions";
    }

    @PostMapping("/afegir")
    public String afegir(@RequestParam String usuariId, @RequestParam String itemId,
                         @RequestParam String valor, RedirectAttributes ra) {
        try {
            domini.afegirValoracio(usuariId, itemId, valor);
            ra.addFlashAttribute("success", "Valoració afegida.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/valoracions";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam String usuariId, @RequestParam String itemId,
                         @RequestParam String valor, RedirectAttributes ra) {
        try {
            domini.editarValoracio(usuariId, itemId, valor);
            ra.addFlashAttribute("success", "Valoració editada.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/valoracions";
    }

    @PostMapping("/esborrar")
    public String esborrar(@RequestParam String usuariId, @RequestParam String itemId, RedirectAttributes ra) {
        try {
            domini.esborraValoracio(usuariId, itemId);
            ra.addFlashAttribute("success", "Valoració esborrada.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/valoracions";
    }

    @PostMapping("/esborrar-totes")
    public String esborrarTotes(RedirectAttributes ra) {
        domini.esborrarTotesLesValoracions();
        ra.addFlashAttribute("success", "Totes les valoracions esborrades.");
        return "redirect:/valoracions";
    }
}
