package com.crawler.scrapecom.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.ZonedDateTime;

@Data
@NoArgsConstructor
@Document(collection = "Raw Data - Shop")
public class ShopModel {
    @Id
    public String id;
    private String title;
    private String price;
    private String dateTime;
    private RawDataState rawDataState = RawDataState.SCRAPED;

    public ShopModel(String title, String price, String dateTime) {
        this.title = title;
        this.price = price;
        this.dateTime = dateTime;
    }
}
