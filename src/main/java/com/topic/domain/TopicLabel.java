package com.topic.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name="topic_label")
public class TopicLabel {
	
    @javax.persistence.Id
    @GeneratedValue
    private Long id;
    private String name;
    private String description;
    
    public TopicLabel() {
		super();
	}
    
	public TopicLabel(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	

	@Override
	public String toString() {
		return "TopicLabel [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
    
    

}
