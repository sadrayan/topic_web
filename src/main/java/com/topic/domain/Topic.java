package com.topic.domain;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;



@Entity
@Table(name="topic")
public class Topic {
	
    @javax.persistence.Id
    @GeneratedValue
    private Long id;
    @Column(length=1000)  
    private String description;
    private String windowBin; 
    private int k; // Topic number
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="lable_id")
    private TopicLabel lable = null;

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

	public void setWindowBin(String window) {
		this.windowBin = window;
	}

	public int getK() {
		return k;
	}

	public void setK(int k) {
		this.k = k;
	}

	public TopicLabel getLable() {
		return lable;
	}

	public void setLable(TopicLabel lable) {
		this.lable = lable;
	}

	@Override
	public String toString() {
		return "Topic [id=" + id + ", description=" + description + ", windowBin=" + windowBin + ", k=" + k + ", lable="
				+ lable + "]";
	}

	
	
}
