package web.controllers;

import domini.controladors.ControladorDomini;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import utilitats.Pair;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequestMapping("/recomanacions")
public class RecomanacioController {

    private final ControladorDomini domini;

    public RecomanacioController(ControladorDomini domini) {
        this.domini = domini;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("existeixTipusItem", domini.existeixTipusItemSeleccionat());
        model.addAttribute("tipusItemNom", domini.obtenirNomTipusItemSeleccionat());
        model.addAttribute("sessioIniciada", domini.sessioIniciada());
        if (domini.existeixTipusItemSeleccionat()) {
            model.addAttribute("nomsAtributs", domini.obtenirNomsAtributsTipusItemSeleccionat());
        } else {
            model.addAttribute("nomsAtributs", new ArrayList<>());
        }
        return "recomanacions";
    }

    @PostMapping("/obtenir")
    public String obtenir(@RequestParam String metode,
                          @RequestParam(required = false) java.util.List<String> atributsFiltre,
                          @RequestParam(defaultValue = "true") boolean filtreInclusiu,
                          Model model, RedirectAttributes ra) {
        try {
            if (!domini.existeixTipusItemSeleccionat()) {
                ra.addFlashAttribute("error", "No hi ha cap tipus d'ítem seleccionat.");
                return "redirect:/recomanacions";
            }
            if (!domini.sessioIniciada()) {
                ra.addFlashAttribute("error", "No has iniciat la sessió.");
                return "redirect:/recomanacions";
            }

            ArrayList<String> nomAtributs = new ArrayList<>();
            if (atributsFiltre != null) {
                nomAtributs.addAll(atributsFiltre);
            }

            ArrayList<String> resultats;
            switch (metode) {
                case "collaborative":
                    resultats = domini.obtenirRecomanacioCollaborative(nomAtributs, filtreInclusiu);
                    break;
                case "content-based":
                    resultats = domini.obtenirRecomanacioContentBased(nomAtributs, filtreInclusiu);
                    break;
                case "hybrid":
                    resultats = domini.obtenirRecomanacioHibrida(nomAtributs, filtreInclusiu);
                    break;
                default:
                    ra.addFlashAttribute("error", "Mètode desconegut.");
                    return "redirect:/recomanacions";
            }

            ArrayList<String> nomsAtributs = domini.obtenirNomsAtributsTipusItemSeleccionat();
            ArrayList<ArrayList<String>> itemsRecomanats = new ArrayList<>();
            for (String itemId : resultats) {
                try {
                    Map<String, String> item = domini.obtenirItem(itemId);
                    ArrayList<String> row = new ArrayList<>();
                    row.add(itemId);
                    for (String attr : nomsAtributs) {
                        row.add(item.getOrDefault(attr, ""));
                    }
                    itemsRecomanats.add(row);
                } catch (Exception e) {
                    e.printStackTrace();
                    ArrayList<String> row = new ArrayList<>();
                    row.add(itemId);
                    for (String attr : nomsAtributs) {
                        row.add("ERROR");
                    }
                    itemsRecomanats.add(row);
                }
            }

            model.addAttribute("existeixTipusItem", true);
            model.addAttribute("tipusItemNom", domini.obtenirNomTipusItemSeleccionat());
            model.addAttribute("sessioIniciada", true);
            model.addAttribute("nomsAtributs", nomsAtributs);
            model.addAttribute("resultats", itemsRecomanats);
            model.addAttribute("resultatIds", resultats);
            model.addAttribute("metode", metode);
            return "recomanacions";

        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/recomanacions";
        }
    }

    @PostMapping("/avaluar")
    public String avaluar(@RequestParam Map<String, String> params, RedirectAttributes ra) {
        try {
            ArrayList<Pair<Integer, Double>> valoracions = new ArrayList<>();
            int i = 0;
            while (params.containsKey("item_id_" + i)) {
                int itemId = Integer.parseInt(params.get("item_id_" + i));
                double valor = Double.parseDouble(params.get("item_val_" + i));
                valoracions.add(new Pair<>(itemId, valor));
                i++;
            }
            double ndcg = domini.avaluarRecomanacio(valoracions);
            String ndcgStr = String.valueOf(ndcg);
            if (ndcgStr.length() > 5) ndcgStr = ndcgStr.substring(0, 5);
            ra.addFlashAttribute("success", "NDCG de la recomanació: " + ndcgStr);
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/recomanacions";
    }
}
