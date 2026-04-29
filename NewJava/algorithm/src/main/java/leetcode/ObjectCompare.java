package leetcode;

import java.util.Comparator;

public class ObjectCompare {

    public static class Student {

    int age;
    double score;
    String area;
    double extraScore;

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }
    public String getArea() { return area; }
    public void setArea(String area) { this.area = area; }
    public double getExtraScore() { return extraScore; }
    public void setExtraScore(double extraScore) { this.extraScore = extraScore; }
}

    public static class StudentComparators {

        /**
         * Comparator.comparingxxx().thenComparing()...是默认返回一个串联的Comparator
         * @return
         */
        public Comparator<Student> byScoreThenByAgeComparator() {
           return Comparator.comparingDouble(Student::getScore).reversed().thenComparing(Student::getAge);
        }

        /**
         * 如果比较既包含简单字段，又包含复杂的字段需要比较。应该在将复杂字段的单独抽取出来做一个Comparator，这样比较好写。
         * 因为写多级字段的比较，其实是比较麻烦的。
         * 必须用一个new Comparator的方法， 复杂Comparator放在首位
         * @return
         */
        public Comparator<Student> byAreaThenByScoreComparator() {
            Comparator<Student> areaComparator = new Comparator<Student>() {
                @Override
                public int compare(Student s1, Student s2) {
                    String a1 = s1.getArea();
                    String a2 = s2.getArea();
                    // area 顺序: Beijing > Shanghai > Guangzhou > Shenzhen
                    if (!a1.equals(a2)) {
                        if (a1.equals("Beijing")) return -1;
                        if (a2.equals("Beijing")) return 1;
                        if (a1.equals("Shanghai")) return -1;
                        if (a2.equals("Shanghai")) return 1;
                        if (a1.equals("Guangzhou")) return -1;
                        if (a2.equals("Guangzhou")) return 1;
                        return 0;
                    }
                    return 0;
                }
            };

            return areaComparator.thenComparing(Student::getScore);
        }

        public Comparator<Student> byScoreThenByAreaComparator() {
            Comparator<Student> areaComparator = new Comparator<Student>() {
                @Override
                public int compare(Student s1, Student s2) {
                    String a1 = s1.getArea();
                    String a2 = s2.getArea();
                    // area 顺序: Beijing > Shanghai > Guangzhou > Shenzhen
                    if (!a1.equals(a2)) {
                        if (a1.equals("Beijing")) return -1;
                        if (a2.equals("Beijing")) return 1;
                        if (a1.equals("Shanghai")) return -1;
                        if (a2.equals("Shanghai")) return 1;
                        if (a1.equals("Guangzhou")) return -1;
                        if (a2.equals("Guangzhou")) return 1;
                        return 0;
                    }
                    return 0;
                }
            };
            return Comparator.comparingDouble(Student::getScore).reversed().thenComparing(areaComparator);
        }

        public Comparator<Student> byScoreThenByAreaComparator2() {
            Comparator<Student> totalScoreComparator = new Comparator<Student>() {
                @Override
                public int compare(Student s1, Student s2) {
                    double scoreTotal1 = s1.getScore() + s1.getExtraScore();
                    double scoreTotal2 = s2.getScore() + s2.getExtraScore();
                    return Double.compare(scoreTotal1, scoreTotal2);
                }
            };
            return totalScoreComparator.reversed().thenComparing(Student::getAge);
        }

    }

}
