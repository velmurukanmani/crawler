package com.dbs.web.crawler.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dbs.web.crawler.model.Crawler;
import com.dbs.web.crawler.service.CrawlerService;

@RestController
@RequestMapping(value = "/api")
public class CrawlerController {

	@Autowired
	public CrawlerService crawlerService;

	@GetMapping(value = "/crawler/{query}")
	public List<Crawler> getCrawlerURL(@PathVariable String query) {
		return crawlerService.findAllURL(query);
	}
}
