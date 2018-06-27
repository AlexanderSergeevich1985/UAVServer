/**The MIT License (MIT)
Copyright (c) 2018 by AleksanderSergeevich
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package uav.Utils;

import org.antlr.stringtemplate.StringTemplate;
import java.util.LinkedHashMap;
import java.util.Map;

public class SQLStatements {
    
    static public String createTable(String tableName, LinkedHashMap<String, String> columns) {
        StringBuilder builder = new StringBuilder("CREATE TABLE ");
        builder.append(tableName);
        builder.append(" (");
        int counter = columns.size();
        for(Map.Entry<String, String> entry: columns.entrySet()) {
            if(counter > 1)
                builder.append(entry.getKey().concat(" ")).append(entry.getValue().concat(", "));
            else
                builder.append(entry.getKey().concat(" ")).append(entry.getValue());
            --counter;
        }
        builder.append(");");
        return builder.toString();
    }

    static public String alterTable(String tableName, String columnName, String dataType) {
        return String.format("ALTER TABLE %s ADD %s %s;", tableName, columnName, dataType);
    }
    
    static public String insertIntoTable(String tableName, List<String> columnNames, List<String> columnValues) {
        if(columnNames == null || columnValues == null || columnNames.size() != columnValues.size()) return null;
        StringBuilder builderColumns = new StringBuilder("(");
        StringBuilder builderValues = new StringBuilder("(");
        Iterator<String> cni = columnNames.iterator();
        Iterator<String> cvi = columnValues.iterator();
        while(cni.hasNext() && cvi.hasNext()) {
            builderColumns.append(cni.next());
            builderValues.append(cvi.next());
            if(cni.hasNext()) {
                builderColumns.append(", ");
                builderValues.append(", ");
            }
        }
        builderColumns.append(") ");
        builderValues.append(")");
        return String.format("INSERT INTO %s %s VALUES %s;", tableName, builderColumns.toString(), builderValues.toString());
    }

    
    static public String updateTable(String tableName, String columnName, String newValue, String conditions) {
        return String.format("UPDATE %s SET %s = %s WHERE %s;", tableName, columnName, newValue, conditions);
    }
    
    static public String deleteRecords(String tableName, String conditions) {
        return String.format("DELETE FROM %s WHERE %s;", tableName, conditions);
    }
    
    static public String columnSum(String columnName, String tableName, String conditions) {
        StringBuilder strBuilder = new StringBuilder(String.format("SELECT SUM(%s) FROM %s", columnName, tableName));
        if(conditions == null || conditions.isEmpty()) {
            return strBuilder.append(";").toString();
        }
        return strBuilder.append(" WHERE ".concat(conditions)).append(";").toString();
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
    
    static public String selectWithLike(String whatSelect, String tableName, String columnName, String pattern) {
        return String.format("SELECT %s FROM %s WHERE %s LIKE %s;", whatSelect, tableName, columnName, pattern);
    }
    
    static public String selectWithIn(String whatSelect, String tableName, String columnName, List<String> values) {
        StringBuilder builder = new StringBuilder(String.format("SELECT %s FROM %s WHERE %s IN (", whatSelect, tableName, columnName));
        Iterator<String> iterator = values.iterator();
        values.forEach(value -> {
            if(iterator.hasNext()) {
                builder.append(iterator.next());
                if(iterator.hasNext()) builder.append(", ");
            }
        });
        builder.append(");");
        return builder.toString();
    }
    
    static public String selectWithOrderBY(String whatSelect, String tableName, String columnNames, String order) {
        return (order == null && !order.isEmpty() && (order.equals("ASC") || order.equals("DESC"))) ? String.format("SELECT %s FROM %s ORDER BY %s %s;", whatSelect, tableName, columnNames, order) : String.format("SELECT %s FROM %s ORDER BY %s;", whatSelect, tableName, columnNames);
    }
    
    static public String selectWithHaving(String whatSelect, String tableName, String condition, String columnNames, String condition2, String orderColumns) {
        return String.format("SELECT %s FROM %s WHERE %s GROUP BY %s HAVING %s ORDER BY %s;", whatSelect, tableName, condition, columnNames, condition2, orderColumns);
    }
    
    static public String selectDistinct(String whatSelect, String tableName) {
        return String.format("SELECT %s FROM %s;", whatSelect, tableName);
    }
    
    static public String selectWithUnion(String whatSelect, String tableName, String whatSelect2, String tableName2) {
        return String.format("SELECT %s FROM %s UNION SELECT %s FROM %s;", whatSelect, tableName, whatSelect2, tableName2);
    }
}
