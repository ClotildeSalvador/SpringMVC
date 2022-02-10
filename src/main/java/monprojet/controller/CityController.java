package monprojet.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.slf4j.Slf4j;
import monprojet.dao.CityRepository;
import monprojet.entity.City;
import org.springframework.web.bind.annotation.ModelAttribute;


@Controller 
@RequestMapping(path = "/ville") 
@Slf4j
public class CityController {

	@Autowired
	private CityRepository dao;
	
	@GetMapping(path = "show")
	public	String afficheToutesLesCategories(Model model) {
		log.info("Show : {}", dao.findAll());
		model.addAttribute("villes", dao.findAll());
		return "showVilles";
	}	

	@GetMapping(path = "add")
	public String montreLeFormulairePourAjout(@ModelAttribute("ville") City city) {
		return "formulaireVille";
	}
	
	@GetMapping(path = "edit")
	public String montreLeFormulairePourEdition(@RequestParam("code") City city, Model model) {
		model.addAttribute("ville", city);
		return "formulaireVille";
	}
	
	@PostMapping(path = "save")
	public String ajouteLaVillePuisMontreLaListe(City city) {
		// cf. https://www.baeldung.com/spring-data-crud-repository-save
		dao.save(city); // Ici on peut avoir une erreur (doublon dans un libellé par exemple)
		return "redirect:show"; // POST-Redirect-GET : on se redirige vers l'affichage de la liste		
	}	
	
	@GetMapping(path = "delete")
	public String supprimeUneVillePuisMontreLaListe(@RequestParam("code") City city) {
		dao.delete(city); // Ici on peut avoir une erreur (Si il y a des produits dans cette catégorie par exemple)
		return "redirect:show"; // on se redirige vers l'affichage de la liste
	}
}
