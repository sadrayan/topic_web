package com.topic.repositories;

import org.springframework.data.repository.CrudRepository;

import com.topic.domain.TopicLabel;

public interface TopicLabelRepo extends CrudRepository<TopicLabel, Long> {
	

}
