package com.dbs.web.crawler.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.dbs.web.crawler.model.Crawler;
import com.dbs.web.crawler.util.ApplicationConstant;

@Service
public class CrawlerServiceImpl implements CrawlerService {

	@Override
	public List<Crawler> findAllURL(String query) {
		
		List<Crawler> crawlerList = new ArrayList<>();
		String request = "https://www.google.com/search?q=" + query + "&num=20";
		System.out.println("Sending request..." + request);

		try {
			// need http protocol, set this as a Google bot agent
			Document doc = Jsoup
					.connect(request)
					.userAgent(
							"Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
					.timeout(5000).get();

			// get all links
			Elements links = doc.select("a[href]");
			for (Element link : links) {

				String url = link.attr("href");		
				if(url.startsWith("/url?q=")) {
					
					String domainName = "";
					//use regex to get domain name
					Pattern patternDomainName = Pattern.compile(ApplicationConstant.DOMAIN_NAME_PATTERN);
					Matcher matcher = patternDomainName.matcher(url);
					if (matcher.find()) {
						domainName = matcher.group(0).toLowerCase().trim();
					}
					
					crawlerList.add(new Crawler(domainName));
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Set<String> urlSet = new HashSet<>();
		List<Crawler> list = crawlerList.stream().filter(c -> urlSet.add(c.getUrl())).collect( Collectors.toList() );

		return list;
	}
}
