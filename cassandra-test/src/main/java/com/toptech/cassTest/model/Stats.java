package com.toptech.cassTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.Map;


@Table(value = "stats01")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Stats {

    @PrimaryKey
    private String equipmentName;
    private Long totalCounts;
    private Map<String, Long> labelCounts;
    private Map<String, String> discreteCounts;                  // Map<String, List<Long>>  (list long is json serialized)
    private Map<String, Double> means;
    private Map<String, Double> stdDev;
    private Map<String, Double> upper;
    private Map<String, Double> lower;
}
