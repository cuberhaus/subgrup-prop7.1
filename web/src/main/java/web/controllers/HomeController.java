package web.controllers;

import domini.controladors.ControladorDomini;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final ControladorDomini domini;

    public HomeController(ControladorDomini domini) {
        this.domini = domini;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("tipusItems", domini.obtenirNomsTipusItemsCarregats());
        model.addAttribute("tipusItemSeleccionat", domini.obtenirNomTipusItemSeleccionat());
        model.addAttribute("numUsuaris", domini.obtenirUsuaris().size());
        model.addAttribute("numItems", domini.existeixTipusItemSeleccionat() ? domini.obtenirItems().size() : 0);
        model.addAttribute("numValoracions", domini.existeixTipusItemSeleccionat() ? domini.obtenirValoracions().size() : 0);
        model.addAttribute("sessioIniciada", domini.sessioIniciada());
        return "home";
    }
}
