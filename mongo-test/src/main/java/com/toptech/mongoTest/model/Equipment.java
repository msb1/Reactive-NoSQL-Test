package com.toptech.mongoTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document(collection = "equipment")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Equipment {
    @Id
    private String id;
    private String name;
    private List<String> labels;
    private List<String> continuous;
    private List<Map<String, List<String>>> discrete;
}

