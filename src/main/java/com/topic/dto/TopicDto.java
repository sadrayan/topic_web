package com.topic.dto;

import javax.persistence.Column;

public class TopicDto {
	
    private Long id; 
    private String description;
    private String windowBin; 
    private int k; // Topic number
    
    private Long topicLabelId;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getWindowBin() {
		return windowBin;
	}
	public void setWindowBin(String windowBin) {
		this.windowBin = windowBin;
	}
	public int getK() {
		return k;
	}
	public void setK(int k) {
		this.k = k;
	}

	
	public Long getTopicLabelId() {
		return topicLabelId;
	}
	public void setTopicLabelId(Long topicLabelId) {
		this.topicLabelId = topicLabelId;
	}

	@Override
	public String toString() {
		return "TopicDto [id=" + id + ", description=" + description + ", windowBin=" + windowBin + ", k=" + k
				+ ", topicLabelId=" + topicLabelId + "]";
	}
	
	public TopicDto () {		
	}

	
    
    
	

}
