package com.ar.codoacodo.controllers;


import org.springframework.web.servlet.ModelAndView;

//@Controller
public class HomeController {

//	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView swaggerUi() {
		return new ModelAndView("redirect:/swagger-ui/index.html");
	}
}
