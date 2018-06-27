package uav.Utils;

import org.antlr.stringtemplate.StringTemplate;
import java.util.LinkedHashMap;

public class SQLStatements {
    
    static public String createTable(String tableName, LinkedHashMap<String, String> columns) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(tableName);
        builder.append(" (");
        columns.forEach((name, type) -> builder.append(name.concat(" ")).append(type.concat(", ")));
        builder.append(");");
        return builder.toString();
    }

    static public String alterTable(String tableName, String columnName, String dataType) {
        return String.format("ALTER TABLE %s ADD %s %s;", tableName, columnName, dataType);
    }

    static public String columnAvg(String columnName, String tableName) {
        return String.format("SELECT AVG(%s) FROM %s;", columnName, tableName);
    }

    static public String columnCount(String columnName, String tableName) {
        return String.format("SELECT COUNT(%s) FROM %s;", columnName, tableName);
    }

    static public String selectWithAND(String whatSelect, String tableName, String fcn, String fValue, String scn, String sValue) {
        return String.format("SELECT %s FROM %s WHERE %s = %s AND %s = %s;", whatSelect, tableName, fcn, fValue, scn, sValue);
    }

    static public String selectWithBetween(String whatSelect, String tableName, String columnName, String fValue, String sValue) {
        return String.format("SELECT %s FROM %s WHERE %s BETWEEN %s AND %s;", whatSelect, tableName, columnName, fValue, sValue);
    }
}


