package com.toptech.cassTest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.List;

@Table(value = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @PrimaryKey
    private String username;

    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private boolean enabled;
    private List<Role> roles;

}


