package com.hysea.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Table {

    private String tableName;

    private List<Field> fields = new ArrayList<>();
    public void addField(Field field) {
        fields.add(field);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Field {

        private String fieldName;

    }
}
