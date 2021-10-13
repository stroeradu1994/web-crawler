package com.crawler.scrapecom.crawler;

import com.crawler.scrapecom.crawler.model.ShopModel;
import com.crawler.scrapecom.crawler.repo.ShopCrawlerRepository;
import com.crawler.scrapecom.util.DateTimeUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ShopCrawler implements Crawler {
    public static final String BASE_URL = "https://www.shop.ro/";
    public static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.130 Safari/537.36";

    @Autowired
    ShopCrawlerRepository shopCrawlerRepository;

    public void scrape(List<String> startPages) {
        try {
            for (String startPage : startPages) {
                retrieveDataForCategoryPage(startPage);
            }

        } catch (IOException e) {
            log.error("Something went wrong");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void retrieveDataForCategoryPage(String page) throws IOException, InterruptedException {
        Optional<String> nextPage = retrieveDataForPage(page);
        Thread.sleep(2000);
        if (nextPage.isPresent()) {
            retrieveDataForCategoryPage(nextPage.get());
        }
    }

    private Optional<String> retrieveDataForPage(String page) throws IOException {
        Document document = Jsoup.connect(BASE_URL + page)
                .userAgent(USER_AGENT)
                .get();
        Elements titles = document.getElementsByClass("product-title");
        Elements prices = document.getElementsByClass("product-new-price");
        Elements nextPage = document.getElementsByClass("js-change-page");
        for (int i = 0; i < titles.size(); i++) {
            String title = titles.get(i).attr("title");
            Element currentPrice = prices.get(i);
            String firstPrice = getFirstPrice(currentPrice);
            String secondPrice = getSecondPrice(currentPrice);

            ShopModel shopModel = new ShopModel(title, firstPrice + "." + secondPrice, DateTimeUtil.getDateTimeFormattedNow());
            shopCrawlerRepository.save(shopModel);
            System.out.println(shopModel.getTitle());
            log.info(shopModel.getTitle() + " has been saved.");
        }
        return getNextPage(nextPage);
    }

    private Optional<String> getNextPage(Elements nextPageElements) {
        if (nextPageElements != null && !nextPageElements.isEmpty() && nextPageElements.get(0) != null) {
            return Optional.of(nextPageElements.get(0).attr("href"));
        } else {
            return Optional.empty();
        }
    }

    private String getFirstPrice(Element price) {
        return ((TextNode) price.childNodes().get(0)).getWholeText();
    }

    private String getSecondPrice(Element price) {
        return ((TextNode) ((Element) price.childNodes().get(1)).childNodes().get(0)).getWholeText();
    }
}
