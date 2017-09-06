package com.topic.services;

import java.util.List;

import com.topic.domain.Topic;
import com.topic.domain.TopicLabel;
import com.topic.dto.TopicDto;
import com.topic.dto.TopicLabelDto;

public interface TopicService {

	TopicLabel saveLabel (TopicLabelDto topicLableDto);
	
	List<TopicLabel> listAll();

	TopicLabel getById(Long id);
	
	Topic getTopicById(Long id);
	
	List<Topic> getDynamicTopics ();

	List<Topic> getDynamicTopicByk(int k);
	
	List<Topic> getWindowTopicByk(String window, int k);

	TopicLabel assignTopicLabel(TopicDto topicDto);
}
