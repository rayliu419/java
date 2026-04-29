package leetcode;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class ObjectCompareTest extends TestCase {

    private ObjectCompare.StudentComparators comparators;

    @Override
    protected void setUp() throws Exception {
        comparators = new ObjectCompare.StudentComparators();
    }

    private ObjectCompare.Student s(String area, int age, double score) {
        return s(area, age, score, 0.0);
    }

    private ObjectCompare.Student s(String area, int age, double score, double extraScore) {
        ObjectCompare.Student student = new ObjectCompare.Student();
        student.setArea(area);
        student.setAge(age);
        student.setScore(score);
        student.setExtraScore(extraScore);
        return student;
    }

    @Test
    public void testByScoreThenByAgeComparator_scoreDescendingThenAgeAscending() {
        List<ObjectCompare.Student> students = Arrays.asList(
            s("Beijing", 18, 85.0),
            s("Shanghai", 20, 90.0),
            s("Shenzhen", 18, 95.0),
            s("Beijing", 19, 85.0)
        );

        students.sort(comparators.byScoreThenByAgeComparator());

        // 95 > 90 > 85，同分按 age 升序
        assertEquals(95.0, students.get(0).getScore(), 0.01);
        assertEquals(90.0, students.get(1).getScore(), 0.01);
        assertEquals(18, students.get(2).getAge());  // 85分中 age 小的在前
        assertEquals(19, students.get(3).getAge());
    }

    @Test
    public void testByAreaThenByScoreComparator_areaOrderThenScoreDescending() {
        List<ObjectCompare.Student> students = Arrays.asList(
            s("Shenzhen", 18, 95.0),
            s("Beijing", 19, 80.0),
            s("Shanghai", 20, 90.0),
            s("Beijing", 18, 90.0)
        );

        students.sort(comparators.byAreaThenByScoreComparator());

        // area 顺序: Beijing > Shanghai > Guangzhou > Shenzhen，同 area 按 score 升序
        assertEquals("Beijing", students.get(0).getArea());
        assertEquals(80.0, students.get(0).getScore(), 0.01);
        assertEquals("Beijing", students.get(1).getArea());
        assertEquals(90.0, students.get(1).getScore(), 0.01);
        assertEquals("Shanghai", students.get(2).getArea());
        assertEquals("Shenzhen", students.get(3).getArea());
    }

    @Test
    public void testByScoreThenByAreaComparator_scoreDescendingThenAreaOrder() {
        List<ObjectCompare.Student> students = Arrays.asList(
            s("Shenzhen", 18, 85.0),
            s("Beijing", 19, 85.0),
            s("Shanghai", 20, 90.0)
        );

        students.sort(comparators.byScoreThenByAreaComparator());

        // score 降序，同分按 area 顺序: Beijing > Shanghai > Guangzhou > Shenzhen
        assertEquals(90.0, students.get(0).getScore(), 0.01);
        assertEquals("Beijing", students.get(1).getArea());
        assertEquals("Shenzhen", students.get(2).getArea());
    }

    @Test
    public void testByScoreThenByAreaComparator2_totalScoreDescendingThenAgeAscending() {
        List<ObjectCompare.Student> students = Arrays.asList(
            s("Beijing", 19, 80.0, 5.0),   // total=85
            s("Shanghai", 18, 80.0, 5.0),   // total=85
            s("Shenzhen", 20, 80, 0.0)    // total=90
        );

        students.sort(comparators.byScoreThenByAreaComparator2());

        // totalScore 降序，同 total 按 age 升序
        assertEquals(80, students.get(0).getScore(), 0.01);
        assertEquals(18, students.get(0).getAge());  // total=85 中 age 小的在前
        assertEquals(19, students.get(1).getAge());
    }
}
