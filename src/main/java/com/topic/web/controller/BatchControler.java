package com.topic.web.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.topic.domain.Topic;
import com.topic.repositories.TopicRepo;
import com.topic.services.TopicService;


@Controller
public class BatchControler {
	
	
	private static final Logger logger = LoggerFactory.getLogger(BatchControler.class);
	
	
	@Autowired
	private TopicService topicService;
	@Autowired
	private TopicRepo topicRepo;
	
	@RequestMapping("/topicDic")
	public List <Topic> getWindowTopicsDic() throws Exception {
		List <Topic> topicList = topicService.listAllTopics();
		
		List <String> topicDic = new ArrayList<>();
		
		PrintWriter writer = new PrintWriter(new FileWriter("/home/sonic/topic_output.txt", true)); 
		for (Topic topic : topicList) {
			writer.write(topic.getDescription().replace(",", " ") + "\n");
		}
		writer.close();
		
//		logger.info("Dictionary size:" + topicDic.size());
//		logger.info(Arrays.toString(topicDic.toArray()));
		
		return topicList;
		
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
				String window = TopicController.DYNAMIC;
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
