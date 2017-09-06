package com.topic;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.topic.domain.Topic;
import com.topic.web.controller.MainController;

@SpringBootApplication
public class TopicWebApplication {
	
	private static final Logger logger = LoggerFactory.getLogger(TopicWebApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(TopicWebApplication.class, args);
	}

}
