package web.controllers;

import domini.controladors.ControladorDomini;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping("/usuaris")
public class UsuariController {

    private final ControladorDomini domini;

    public UsuariController(ControladorDomini domini) {
        this.domini = domini;
    }

    @GetMapping
    public String index(Model model) {
        ArrayList<ArrayList<String>> usuaris = domini.obtenirUsuaris();
        model.addAttribute("usuaris", new ArrayList<>(usuaris.subList(0, Math.min(500, usuaris.size()))));
        model.addAttribute("sessioIniciada", domini.sessioIniciada());
        try {
            model.addAttribute("sessioId", domini.sessioIniciada() ? String.valueOf(domini.obtenirSessio()) : "");
        } catch (Exception e) {
            model.addAttribute("sessioId", "");
        }
        return "usuaris";
    }

    @PostMapping("/afegir")
    public String afegir(@RequestParam String nom, @RequestParam String contrasenya, RedirectAttributes ra) {
        try {
            int id = domini.afegirUsuari(nom, contrasenya);
            ra.addFlashAttribute("success", "Usuari creat amb id " + id + ".");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuaris";
    }

    @PostMapping("/esborrar")
    public String esborrar(@RequestParam String id, RedirectAttributes ra) {
        try {
            domini.esborrarUsuari(Integer.parseInt(id));
            ra.addFlashAttribute("success", "Usuari " + id + " esborrat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuaris";
    }

    @PostMapping("/iniciar-sessio")
    public String iniciarSessio(@RequestParam String id, @RequestParam String contrasenya, RedirectAttributes ra) {
        try {
            domini.iniciarSessio(Integer.parseInt(id), contrasenya);
            ra.addFlashAttribute("success", "Sessió iniciada com a usuari " + id + ".");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuaris";
    }

    @PostMapping("/tancar-sessio")
    public String tancarSessio(RedirectAttributes ra) {
        try {
            domini.tancarSessio();
            ra.addFlashAttribute("success", "Sessió tancada.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuaris";
    }

    @PostMapping("/editar")
    public String editar(@RequestParam String id, @RequestParam String nom,
                         @RequestParam String contrasenya, RedirectAttributes ra) {
        try {
            if (!nom.isEmpty()) {
                domini.canviaNomUsuari(id, nom);
            }
            if (!contrasenya.isEmpty()) {
                domini.canviaContrasenyaUsuari(id, contrasenya.toCharArray());
            }
            ra.addFlashAttribute("success", "Usuari " + id + " editat.");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/usuaris";
    }

    @PostMapping("/esborrar-tots")
    public String esborrarTots(RedirectAttributes ra) {
        domini.esborraConjuntUsuaris();
        ra.addFlashAttribute("success", "Tots els usuaris esborrats.");
        return "redirect:/usuaris";
    }
}
