package main.annotationapp;

import org.junit.Test;

public class TestAnnotationApp {

    TableUtils tableUtils = new TableUtils();

    @Test
    public void testCreateTable() throws ClassNotFoundException {
        System.out.println("\nUsing annotation to generate table");
        String sqlCreate = tableUtils.createTable(Member.class.getName());
        System.out.println(sqlCreate);
    }

    @Test
    public void testInsertNewRecord() throws IllegalAccessException, ClassNotFoundException {
        Member member = new Member();
        member.setFirstName("Will");
        member.setLastName("Smith");
        member.setAge(35);
        member.setReference("Generate SQL to insert new record");
        String sqlInsert = tableUtils.createNewRecord(member);
        System.out.println("\nUsing annotation to insert new record");
        System.out.println(sqlInsert);
    }
}
