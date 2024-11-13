package com.hysea.scanner;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.hysea.domain.MybatisTable;
import com.hysea.domain.Table;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.RegexPatternTypeFilter;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Component
public class MybatisTablesScanner {

    /**
     * 扫描指定包路径下所有带 @TableName 注解的类
     *
     * @param basePackageRegex 正则表达式形式的包路径
     * @return Set<Class<?>> 所有匹配的类
     * @throws ClassNotFoundException 如果类不存在
     */
    public Set<Class<?>> scanClassesWithTableName(String basePackageRegex) throws ClassNotFoundException {
        Set<Class<?>> tableNameClasses = new HashSet<>();

        // 创建一个 ClassPathScanningCandidateComponentProvider 实例，用于扫描带注解的类
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);

        // 添加对 @TableName 注解的过滤器
        provider.addIncludeFilter(new AnnotationTypeFilter(TableName.class));

        // 添加对包路径正则表达式的过滤器
        provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(basePackageRegex.replace(".", "\\."))));

        // 扫描符合条件的 bean
        for (BeanDefinition beanDefinition : provider.findCandidateComponents("")) {
            String className = beanDefinition.getBeanClassName();
            if (className != null) {
                Class<?> clazz = Class.forName(className);
                tableNameClasses.add(clazz);
            }
        }

        return tableNameClasses;
    }


    public Set<Class<?>> scanClassesWithTableName(String[] basePackageRegexArray) throws ClassNotFoundException {
        Set<Class<?>> res = new HashSet<>();
        for (String basePackageRegex : basePackageRegexArray) {
            res.addAll(scanClassesWithTableName(basePackageRegex));
        }
        return res;
    }

    String extractField(String field){
        field = field.replace("`", "");
        return field;
    }

    public Set<MybatisTable> scanTablesWithTableName(String... basePackageRegexArray) throws ClassNotFoundException {
        Set<Class<?>> classes = scanClassesWithTableName(basePackageRegexArray);
        return classes.stream().map(cls -> {

            MybatisTable mybatisTable = new MybatisTable();
            TableName tableName = cls.getAnnotation(TableName.class);

            mybatisTable.setTableName(tableName.value());

            mybatisTable.setFields(Arrays.stream(cls.getDeclaredFields()).map(f -> {

                Table.Field field = new Table.Field();
                TableField tableField = f.getAnnotation(TableField.class);

                if(tableField != null){
                    if(!tableField.exist()){
                        return null;
                    }
                    field.setFieldName(extractField(tableField.value()));
                }

                TableId tableId = f.getAnnotation(TableId.class);
                if(tableId != null){
                    field.setFieldName(extractField(tableId.value()));
                }

                if(field.getFieldName() == null){
                    return null;
                }

                return field;

            }).filter(Objects::nonNull).collect(Collectors.toList()));
            return mybatisTable;
        }).collect(Collectors.toSet());

    }
}
