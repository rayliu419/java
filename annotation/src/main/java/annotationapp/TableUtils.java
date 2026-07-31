package annotationapp;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

/**
 * 用一个真实的例子来演示annotation的作用。
 * 在这个例子中，annotation用来表示存储列的通用属性，这些通用属性在转为sql时被使用。
 * 传统的关系数据库的接口是一个SQL语句，这个SQL语句被数据库用类似编译原理的方式来解释并执行。
 * 但是对于Java程序来说，我们不像拼接字符串传给数据库服务，更想构造object，然后有一个通用的util将object转成sql语句，相应
 * 的程序员只需要调用类似dbClient.save(member)这样的方式来编程。
 *
 * 定义的annotation在框架中必然要被类似class.getAnnotation相关类调用，所以反射是框架经常用到的。
 *
 * 这里有一个问题：理论上我也可以定义每个字段都是一个object，然后object含有TableCreator用来创建sql string的属性啊，
 * 为什么不这样做？原因可能是：
 * 1. 这样需要将每个字段都定义为复杂的object，应用程序员不想关注这些跟它应用逻辑字段不相关的属性，他们只想简单的使用annotation。
 * 然后这些annotation在某些地方被非侵入的方式被调用。
 * 2. 基本类型不支持这样的做法。
 *
 */
public class TableUtils {

    private static String getConstraints(Constraints con) {
        String constraints = "";
        if (!con.allowNull())
            constraints += " NOT NULL";
        if (con.primaryKey())
            constraints += " PRIMARY KEY";
        if (con.unique())
            constraints += " UNIQUE";
        return constraints;
    }

    public String createTable(String className) throws ClassNotFoundException {
        Class<?> cl = Class.forName(className);
        DBTable dbTable = cl.getAnnotation(DBTable.class);
        if (dbTable == null) {
            System.out.println("No DBTable annotations in class " + className);
            return "";
        }
        String tableName = dbTable.name();
        if (tableName.length() < 1) {
            tableName = cl.getName().toUpperCase();
        }
        List<String> columnDefs = new ArrayList<>();
        for (Field field : cl.getDeclaredFields()) {
            String columnName = null;
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1)
                // No annotation, not a db a table column
                continue;
            // 这里默认每个属性只有一个annotation，就是我们定义的，实际上每个字段可以有多个。
            if (anns[0] instanceof SQLInteger) {
                SQLInteger sInt = (SQLInteger) anns[0];
                // Use field name if name not specified
                if (sInt.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sInt.name();
                }
                columnDefs.add(columnName + " INT" + getConstraints(sInt.constraints()));
            }
            if (anns[0] instanceof SQLString) {
                SQLString sString = (SQLString) anns[0];
                if (sString.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sString.name();
                }
                columnDefs.add(columnName + " VARCHAR(" + sString.value() + ")"
                        + getConstraints(sString.constraints()));
            }
        }
        StringBuilder createCommand = new StringBuilder("CREATE TABLE " + tableName + "(");
        for (String columnDef : columnDefs)
            createCommand.append("\n " + columnDef + ",");
        // Remove trailing comma
        String tableCreateCommand = createCommand.substring(0, createCommand.length() - 1) + ");";
        return tableCreateCommand;
    }

    public String createNewRecord(Member newRecord) throws ClassNotFoundException, IllegalAccessException {
        String newRecordCommand = "";
        Class<?> cl = Class.forName(newRecord.getClass().getName());
        DBTable dbTable = cl.getAnnotation(DBTable.class);
        if (dbTable == null) {
            System.out.println("No DBTable annotations in class " + newRecord.getClass().getName());
            return "";
        }
        String tableName = dbTable.name();
        if (tableName.length() < 1) {
            tableName = cl.getName().toUpperCase();
        }
        List<Pair<String, String>> columnDefs = Lists.newArrayList();
        List<String> columnValue = Lists.newArrayList();
        for (Field field : cl.getDeclaredFields()) {
            String columnName = null;
            Annotation[] anns = field.getDeclaredAnnotations();
            if (anns.length < 1)
                continue;
            if (anns[0] instanceof SQLInteger) {
                SQLInteger sInt = (SQLInteger) anns[0];
                // Use field name if name not specified
                if (sInt.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sInt.name();
                }
                // get field value via reflection on instance.
                columnValue.add(String.valueOf((Integer)field.get(newRecord)));
                columnDefs.add(Pair.of(columnName, "Integer"));
            }
            if (anns[0] instanceof SQLString) {
                SQLString sString = (SQLString) anns[0];
                if (sString.name().length() < 1) {
                    columnName = field.getName().toUpperCase();
                } else {
                    columnName = sString.name();
                }
                columnDefs.add(Pair.of(columnName, "String"));
                columnValue.add((String)field.get(newRecord));
            }
        }
        StringBuilder insertCommand1 = new StringBuilder("INSERT INTO TABLE " + tableName + "(");
        for (Pair p: columnDefs)
            insertCommand1.append(p.getLeft() + ", ");
        String insertCommand1Trim = insertCommand1.substring(0, insertCommand1.length() - 2) + ")";
        StringBuilder insertCommand2 = new StringBuilder(" VALUES (");
        for (int i = 0; i < columnValue.size(); i++) {
            Pair p = columnDefs.get(i);
            if (p.getRight().equals("String")) {
                insertCommand2.append("\"" + columnValue.get(i) + "\"" + ", ");
            } else {
                insertCommand2.append(columnValue.get(i) + ", ");
            }
        }
        String insertCommand2Trim = insertCommand2.substring(0, insertCommand2.length() - 2) + ");";
        return insertCommand1Trim + insertCommand2Trim;
    }
}
