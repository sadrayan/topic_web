package com.topic.dto;

public class TopicLabelDto {
	
    public Long id;
    public String name;
    public String description;
    
    public TopicLabelDto() {}
    
    
	public TopicLabelDto(Long id, String name, String description) {
		super();
		this.id = id;
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
		return "TopicLabelDto [id=" + id + ", name=" + name + ", description=" + description + "]";
	}
	
	

}
