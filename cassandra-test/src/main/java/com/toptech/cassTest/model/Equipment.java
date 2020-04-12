package com.toptech.cassTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;
import java.util.Map;

@Table(value = "equipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {

    @PrimaryKey
    private String equipmentName;

    private List<String> labels;                  // List<String>
    private List<String> continuous;              // List<String>
    private Map<String, String> discretes;        // List<Map<String, List<String>>>

    // discretes -- key string is name and val string is json string of level names
}

