package com.topic.web.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.topic.domain.Topic;
import com.topic.domain.TopicLabel;
import com.topic.dto.TopicDto;
import com.topic.dto.TopicLabelDto;
import com.topic.repositories.TopicRepo;
import com.topic.services.TopicService;

@Controller
public class TopicController {

	public static final String DYNAMIC = "dynamic";

	private static final Logger logger = LoggerFactory.getLogger(TopicController.class);

	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicRepo topicRepo;

	@RequestMapping({ "/topicLabel/list", "/topicLabel" })
	public String listTopicLabels(Model model) {
		model.addAttribute("template", "topicLabel");
		List<TopicLabel> topicLabelList = topicService.listAll();
		for (TopicLabel topicLabel : topicLabelList)
			logger.info(topicLabel.toString());
		model.addAttribute("topicLabelList", topicLabelList);
		model.addAttribute("topicLabelForm", new TopicLabelDto());
		return "index";
	}

	@RequestMapping(value = "/topicLabel", method = RequestMethod.POST)
	public String saveOrUpdatetopicLabel(@Valid TopicLabelDto topicLabelDto, BindingResult bindingResult) {
		logger.info("saving topicLable" + topicLabelDto.toString());
		TopicLabel savedTopicLabel = topicService.saveLabel(topicLabelDto);
		return "redirect:/topicLabel";
	}

	@RequestMapping("topicLabel/edit/{id}")
	public String edit(@PathVariable String id, Model model) {
		TopicLabel topicLable = topicService.getById(Long.valueOf(id));
		TopicLabelDto topicLabelDto = new TopicLabelDto(topicLable.getId(),
				topicLable.getName(), topicLable.getDescription());
		
		model.addAttribute("topicLabelForm", topicLabelDto);
		model.addAttribute("template", "topicLabelEdit");
		return "index";
	}
	
	
	@RequestMapping("topic/edit/{id}")
	public String editTopic(@PathVariable String id, Model model) {
		logger.info("toic id: " + id);
		// find the topic
		Topic topic = topicService.getTopicById(Long.parseLong(id));
		logger.info(topic.toString());
		// list all available topic label
		List<TopicLabel> topicLabelList = topicService.listAll();
		for (TopicLabel topicLabel : topicLabelList)
			logger.info(topicLabel.toString());
		
		model.addAttribute("topicLabelList", topicLabelList);
		model.addAttribute("topicForm", topic);
		model.addAttribute("template", "topicEdit");
		return "index";
	}
		

}
