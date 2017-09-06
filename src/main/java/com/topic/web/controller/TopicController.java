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
	
	@RequestMapping(value = "/topic", method = RequestMethod.POST)
	public String saveOrUpdatetopic(@Valid TopicDto topicDto, BindingResult bindingResult) {
		logger.info("Assigning topic label: " + topicDto.toString());

		
		TopicLabel savedTopicLabel = topicService.assignTopicLabel(topicDto);
		return "redirect:/topicLabel";
	}

	/*
	 * @RequestMapping("/product/show/{id}") public String getProduct(@PathVariable
	 * String id, Model model){ model.addAttribute("product",
	 * productService.getById(Long.valueOf(id))); return "product/show"; }
	 */
	
	
	public static void batch () {
		
		
	}
	
	@RequestMapping("/topicBatch")
	public List <List<String>> getwindows() throws Exception {
		
		File[] files = new File("/home/sonic/sonic/eosdb/dynamic_nmf/data/windowbin/csv/").listFiles();

		List <Topic> topicList = new ArrayList<>();
		for (File file : files) {
			if (!file.isFile()) { continue; }
		    if (file.getName().contains("tf_idf_tokenized_window") && file.getName().endsWith("terms.csv")) {
				String window = file.getName().replace("tf_idf_tokenized_window_", "").substring(0, 7);
				String k = file.getName().substring(file.getName().length() - 18, file.getName().length() - 16);
				getTopic(file, window, k, topicList);
		    } else if (file.getName().startsWith("dynamictopics_")) {
				String window = DYNAMIC;
				String k = file.getName().substring(15, 17);
				getTopic(file, window, k, topicList);
		 
		    } else {
		    	continue;
		    }
		}
		topicRepo.save(topicList);
		
//		for (Topic topic : topicRepo.findAll())
//			logger.info(topic.toString());
		
		for (Topic topic : topicService.getDynamicTopicByk(40))
			logger.info("Dynamic topic found: " + topic.toString());
		

		return null;
		
	}

	private void getTopic(File file, String window, String k, List<Topic> topicList) throws Exception {
		Reader in = new FileReader(file);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in);
		logger.info("window: " + window + " k: " + k);	
		records.iterator().next();
		for (CSVRecord record : records) {
			List<String> topicWords = new ArrayList<>();
			for (int i = 2; i < record.size(); i++) {
				topicWords.add(record.get(i));
			}
			Topic topic = new Topic();
			topic.setK(Integer.parseInt(k));
			topic.setWindowBin(window);
			topic.setDescription(String.join(", ", topicWords));
			logger.info("length" + topic.getDescription().length());
	//				topic.setLable(new TopicLabel());
			topicList.add(topic);
		}
	}
	
	public List <List<String>> getdynamic(@RequestParam("value") String value) throws Exception {
		
		String fileName = "/home/sonic/sonic/eosdb/dynamic_nmf/data/windowbin/csv/dynamictopics_k{k}_top30_terms.csv";
		value = String.format("%02d", Integer.parseInt(value));
		fileName = fileName.replace("{k}", value);
		logger.info(fileName);
		
		Reader in = new FileReader(fileName);
		Iterable<CSVRecord> records = CSVFormat.EXCEL.parse(in) ;

		List <List<String>> resutls = new ArrayList<>();
		records.iterator().next();
		for (CSVRecord record : records) {
//			logger.info(record.get(1));
			List<String> topicWords = new ArrayList<>();
			for (int i = 2; i < record.size(); i++) {
				topicWords.add(record.get(i));
			}
			resutls.add(Arrays.asList(record.get(1), String.join(", ", topicWords)));
		}
		return resutls;
	}
	
	public List<String> getTopicWindows() throws Exception {
		List<String> results = new ArrayList<String>();

		File[] files = new File("/home/sonic/sonic/eosdb/dynamic_nmf/data/windowbin/csv/").listFiles();

		for (File file : files) {
		    if (file.isFile() && (file.getName().contains("tf_idf_tokenized_window") && file.getName().endsWith("terms.csv"))) {
		        results.add(file.getName());
		    }
		}
		Collections.sort(results);
		return results;
	}

}
