package com.toptech.mongoTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "stats01")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {
    @Id
    private String Id;
    private String equipmentName;
    private Long totalCounts;
    private Map<String, Long> labelCounts;
    private Map<String, List<Long>> discreteCounts;
    private Map<String, Double> means;
    private Map<String, Double> stdDev;
    private Map<String, Double> upper;
    private Map<String, Double> lower;
}
