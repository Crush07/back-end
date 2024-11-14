package com.hysea.scanner.mapper;

import com.hysea.scanner.domain.DatabaseTable;
import com.hysea.scanner.domain.Table;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TableMapper {

    List<DatabaseTable> selectTableNameList(@Param("baseName") String baseName);

    List<Table.Field> selectField(@Param("tableName") String tableName,
                                  @Param("baseName") String baseName);

}
