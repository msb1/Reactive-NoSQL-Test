package com.toptech.cassTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;
import java.util.Map;

@Table(value = "epd01")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataRecord {

    @PrimaryKey
    private String uuid;
    private Long currentTime;
    private String topic;
    private Map<String, Integer> categories;
    private Map<String, Double> sensors;
    private int result;
}
