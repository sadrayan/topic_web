package com.topic.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.topic.domain.Topic;
import com.topic.domain.TopicLabel;
import com.topic.dto.TopicDto;
import com.topic.dto.TopicLabelDto;
import com.topic.repositories.TopicLabelRepo;
import com.topic.repositories.TopicRepo;
import com.topic.web.controller.APIController;
import com.topic.web.controller.TopicController;

@Service
public class TopicServiceImp implements TopicService {
	
	private static final Logger logger = LoggerFactory.getLogger(TopicServiceImp.class);
	
	@Autowired
	private TopicRepo topicRepo;
	@Autowired
	private TopicLabelRepo topicLabelRepo;

	public TopicLabel saveLabel(TopicLabelDto topicLabelDto) {
		TopicLabel topicLable = new TopicLabel(topicLabelDto.getName(), topicLabelDto.getDescription());
		topicLable.setId(topicLabelDto.getId());
		topicLabelRepo.save(topicLable);
		return topicLable;
	}

	@Override
	public List<TopicLabel> listAll() {
		List<TopicLabel> topicLabelList = new ArrayList<>();
		topicLabelRepo.findAll().forEach(topicLabelList::add);
		return topicLabelList;
	}

	@Override
	public TopicLabel getById(Long id) {
		return topicLabelRepo.findOne(id);
	}
	
	public List<Topic> getDynamicTopics () {
		List <Topic> topicList = topicRepo.findBywindowBin(TopicController.DYNAMIC); 
/*		for (Topic topic : topicList)
			logger.info(topic.toString());*/
		return topicList;
	}
	
	public List<Topic> getWindowTopic (String window) {
		List <Topic> topicList = topicRepo.findBywindowBin(window);
		
		return topicList;
	}

	@Override
	public List<Topic> getDynamicTopicByk(int k) {
		List <Topic> topicList = topicRepo.findByKAndWindowBin(k, TopicController.DYNAMIC); 
/*		for (Topic topic : topicList)
			logger.info(topic.toString());*/
		return topicList;
	}

	@Override
	public List<Topic> getWindowTopicByk(String window, int k) {
		logger.info("window: " + window + " k: " + k);
		List <Topic> topicList = topicRepo.findByKAndWindowBin(k, window); 
//		for (Topic topic : topicList)
//			logger.info(topic.toString());
		return topicList;
	}

	@Override
	public Topic getTopicById(Long id) {
		return topicRepo.findOne(id);
	}

	@Override
	public TopicLabel assignTopicLabel(TopicDto topicDto) {
		Topic topic = getTopicById(topicDto.getId());
		TopicLabel topicLabel = getById(topicDto.getTopicLabelId());
		topic.setLable(topicLabel);
		topicRepo.save(topic);
		logger.info("Saved" + getTopicById(topicDto.getId()));
		
		return null;
	}

	@Override
	public List<Topic> listAllTopics() {
		List<Topic> topicList = new ArrayList<>();
		topicRepo.findAll().forEach(topicList::add);
		return topicList;
	}
	
	

}
