package com.crawler.scrapecom.crawler;

import com.crawler.scrapecom.util.SitemapReader;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class ScheduledCrawler {
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    ShopCrawler shopCrawler;

    @Autowired
    SitemapReader sitemapReader;

    @Scheduled(fixedRate = 1000)
    public void justSomeLog() throws IOException {
        log.info("Log");
    }

    @Scheduled(fixedRate = 500000000)
    public void scrapeWebsites() throws IOException {
        log.info("Scrape began at {}", dateFormat.format(new Date()));
        scrapeShop();
    }

    private void scrapeShop() {
        List<String> shopCategoryLinks = sitemapReader.getSiteMap(CrawlerType.SHOP);
        shopCrawler.scrape(shopCategoryLinks);
    }
}
