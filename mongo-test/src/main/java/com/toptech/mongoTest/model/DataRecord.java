package com.toptech.mongoTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document(collection = "epd01")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRecord {

    @Id
    private String uuid;
    private String CurrentTime;
    private String Topic;
    private Map<String, Integer> Categories;
    private Map<String, Double> Sensors;
    private int Result;
}
