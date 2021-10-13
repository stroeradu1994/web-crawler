package com.crawler.scrapecom.util;

import com.crawler.scrapecom.crawler.CrawlerType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Component
public class SitemapReader {

    public static final String shopFile = "classpath:data/shop.txt";

    @Autowired
    ResourceLoader resourceLoader;

    public List<String> getSiteMap(CrawlerType crawlerType) {
        switch (crawlerType) {
            case SHOP:
                try {
                    return readAndProcessFile(shopFile);
                } catch (IOException e) {
                    return new ArrayList<>();
                }
            default:
                return new ArrayList<>();
        }
    }

    private List<String> readAndProcessFile(String fileToRead) throws IOException {
        Resource resource = resourceLoader.getResource(fileToRead);
        InputStream inputStream = resource.getInputStream();
        BufferedReader r = new BufferedReader(new InputStreamReader(inputStream));
        List<String> sitemap = new ArrayList<>();
        String line;
        while ((line = r.readLine()) != null) {
            sitemap.add(line);
        }
        return sitemap;

    }
}
