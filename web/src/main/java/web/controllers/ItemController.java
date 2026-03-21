package web.controllers;

import domini.controladors.ControladorDomini;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/items")
public class ItemController {

    private final ControladorDomini domini;

    public ItemController(ControladorDomini domini) {
        this.domini = domini;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("existeixTipusItem", domini.existeixTipusItemSeleccionat());
        model.addAttribute("tipusItemNom", domini.obtenirNomTipusItemSeleccionat());
        if (domini.existeixTipusItemSeleccionat()) {
            model.addAttribute("nomsAtributs", domini.obtenirNomsAtributsTipusItemSeleccionat());
            ArrayList<ArrayList<String>> totes = domini.obtenirItems();
            model.addAttribute("items", new ArrayList<>(totes.subList(0, Math.min(500, totes.size()))));
            model.addAttribute("itemIds", domini.obtenirIdsItems());
        } else {
            model.addAttribute("nomsAtributs", new ArrayList<>());
            model.addAttribute("items", new ArrayList<>());
            model.addAttribute("itemIds", new ArrayList<>());
        }
        return "items";
    }

    @GetMapping("/{id}")
    public String mostrar(@PathVariable String id, Model model) {
        try {
            Map<String, String> item = domini.obtenirItem(id);
            model.addAttribute("itemId", id);
            model.addAttribute("atributs", item);
            model.addAttribute("nomsAtributs", domini.obtenirNomsAtributsTipusItemSeleccionat());
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
        }
        return "item-detail";
    }

    @PostMapping("/crear")
    public String crear(@RequestParam Map<String, String> params, RedirectAttributes ra) {
        try {
            Map<String, String> atributs = new LinkedHashMap<>();
            for (String nomAtribut : domini.obtenirNomsAtributsTipusItemSeleccionat()) {
                atributs.put(nomAtribut, params.getOrDefault("attr_" + nomAtribut, ""));
            }
            String id = String.valueOf(domini.afegirItem(atributs));
            ra.addFlashAttribute("success", "Ítem creat amb id " + id + ".");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/items";
    }

    @PostMapping("/{id}/editar")
    public String editar(@PathVariable String id, @RequestParam Map<String, String> params, RedirectAttributes ra) {
        try {
            Map<String, String> atributs = new LinkedHashMap<>();
            for (String nomAtribut : domini.obtenirNomsAtributsTipusItemSeleccionat()) {
                atributs.put(nomAtribut, params.getOrDefault("attr_" + nomAtribut, ""));
            }
            domini.editarItem(id, atributs);
            ra.addFlashAttribute("success", "Ítem " + id + " editat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/items";
    }

    @PostMapping("/{id}/esborrar")
    public String esborrar(@PathVariable String id, RedirectAttributes ra) {
        try {
            domini.esborrarItem(id);
            ra.addFlashAttribute("success", "Ítem " + id + " esborrat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/items";
    }

    @PostMapping("/esborrar-tots")
    public String esborrarTots(RedirectAttributes ra) {
        domini.esborrarTotsElsItems();
        ra.addFlashAttribute("success", "Tots els ítems esborrats.");
        return "redirect:/items";
    }
}
