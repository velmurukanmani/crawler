package com.dbs.web.crawler.service;

import java.util.List;

import com.dbs.web.crawler.model.Crawler;

public interface CrawlerService {
	
	List<Crawler> findAllURL(String query);
}
