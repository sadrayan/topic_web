package com.topic.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.topic.domain.Topic;
import com.topic.services.TopicService;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@org.springframework.web.bind.annotation.RestController
public class APIController {
	
	private static final Logger logger = LoggerFactory.getLogger(APIController.class);
	public String href = "/topic/edit/{topicKey}";
	
	@Autowired
	private TopicService topicService;
	
	@RequestMapping(value = "/api/topics", method = RequestMethod.POST)
	public List <List<String>> getwindows(@RequestParam("value") String value) throws Exception {
		logger.info(value);
		List <List<String>> resutls = new ArrayList<>();
		String window = value.substring(0 ,7);
		String k = value.substring(8, 10);
		
		for (Topic topic : topicService.getWindowTopicByk(window, Integer.parseInt(k))) {
			logger.info("Window topic found: " + topic.toString());
			String topicKey = topic.getWindowBin() + "_" + topic.getK();
			if (topic.getLable() != null)
				resutls.add(Arrays.asList(topic.getLable().getName(), href.replace("{topicKey}", topic.getId().toString()), topic.getDescription()));
			else
				resutls.add(Arrays.asList(topicKey, href.replace("{topicKey}", topic.getId().toString()), topic.getDescription()));
		}

		return resutls;
	}
	
	@RequestMapping(value = "/api/dynamictopics", method = RequestMethod.POST)
	public List <List<String>> getdynamic(@RequestParam("value") String value) throws Exception {
		
		List <List<String>> resutls = new ArrayList<>();
		value = String.format("%02d", Integer.parseInt(value));
		logger.info("value of k: " + value);
		
		for (Topic topic : topicService.getDynamicTopicByk(Integer.parseInt(value))) {
			logger.info("Dynamic topic found: " + topic.toString());
			String topicKey = topic.getWindowBin() + "_" + topic.getK();
			if (topic.getLable() != null)
				resutls.add(Arrays.asList(topic.getLable().getName(), href.replace("{topicKey}", topic.getId().toString()), topic.getDescription()));
			else
				resutls.add(Arrays.asList(topicKey, href.replace("{topicKey}", topic.getId().toString()), topic.getDescription()));
			
		}

		return resutls;
	}
	
	
	@RequestMapping("/api/windows")
	public List<String> getTopicWindows() throws Exception {
//		tf_idf_tokenized_window_2017_04_windowtopics_k24_top30_terms
		List<String> results = new ArrayList<String>();
		
		List <Topic> topicList = topicService.listAllTopics();
		
		String topicKey = "";
		for (Topic topic : topicList) {
			if (topic.getWindowBin().equalsIgnoreCase(TopicController.DYNAMIC))
				continue;
			topicKey = topic.getWindowBin() + "_" + topic.getK();
			if (!results.contains(topicKey))
				results.add(topicKey);
		}

		Collections.sort(results);
		return results;
	}
	
	
	
}