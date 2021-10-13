package com.crawler.scrapecom.crawler.repo;

import com.crawler.scrapecom.crawler.model.ShopModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ShopCrawlerRepository extends MongoRepository<ShopModel, String> {
}
