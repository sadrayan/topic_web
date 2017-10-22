package com.topic.web.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.topic.domain.Topic;
import com.topic.domain.TopicLabel;
import com.topic.dto.TopicLabelDto;
import com.topic.services.TopicService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class MainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MainController.class);
	
	@Autowired
	private TopicService topicService;

	@RequestMapping({ "/", "/index"})
	public String index(Model model) throws Exception {
		model.addAttribute("template", "home");
		return "index";
	}
	
	
	@RequestMapping({"/windows"})
	public String windows(Model model) throws Exception {
		model.addAttribute("template", "windowTopics");
		
		// list all available topic label
		List<TopicLabel> topicLabelList = topicService.listAll();
//		for (TopicLabel topicLabel : topicLabelList)
//			logger.info(topicLabel.toString());
		
		model.addAttribute("topicLabelList", topicLabelList);
		model.addAttribute("topicForm", new Topic());
		return "index";
	}
	
	
	@RequestMapping({"/dynamic"})
	public String dynamic(Model model) throws Exception {
		// list all available topic label
		List<TopicLabel> topicLabelList = topicService.listAll();
//		for (TopicLabel topicLabel : topicLabelList)
//			logger.info(topicLabel.toString());
		model.addAttribute("template", "dynamicTopics");
		model.addAttribute("topicLabelList", topicLabelList);
		model.addAttribute("topicForm", new Topic());
		return "index";
	}
	
		
	
	
}