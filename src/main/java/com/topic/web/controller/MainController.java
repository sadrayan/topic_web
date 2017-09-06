package com.topic.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Controller;

@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	

	@RequestMapping({ "/", "/index"})
	public String index(Model model) throws Exception {
		model.addAttribute("template", "home");
		return "index";
	}
	
	
	@RequestMapping({"/windows"})
	public String windows(Model model) throws Exception {
		model.addAttribute("template", "windowTopics");
		return "index";
	}
	
	
	@RequestMapping({"/dynamic"})
	public String dynamic(Model model) throws Exception {
		model.addAttribute("template", "dynamicTopics");
		return "index";
	}
	
		
	
	
}