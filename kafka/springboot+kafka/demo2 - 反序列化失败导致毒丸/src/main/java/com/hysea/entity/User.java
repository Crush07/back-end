package com.hysea.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    @JsonProperty(value = "userId")
    Integer userId;

    @JsonProperty(value = "userName")
    String userName;

}
