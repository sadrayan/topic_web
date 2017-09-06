package com.topic.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.topic.domain.Topic;

public interface TopicRepo extends CrudRepository<Topic, Long> {
	
	List<Topic> findBywindowBin(String window);
	
	List<Topic> findByKAndWindowBin (int k, String window);


}
