package leetcode;

import java.util.Comparator;

public class InterviewCommon {

    /**
     * substring(i)      → [i, end)     从 i 到末尾
     * substring(i, j)   → [i, j)       左闭右开！
     */
    public void stringOperations() {
        String s = "hello world";

        // substring(int beginIndex) — [beginIndex, end)
        String sub1 = s.substring(6);    // "world"
        System.out.println("s.substring(6):       \"" + sub1 + "\"");

        // substring(int beginIndex, int endIndex) — 左闭右开 [beginIndex, endIndex)
        String sub2 = s.substring(0, 5);  // "hello", [0,5)
        String sub3 = s.substring(6, 8);  // "wo",    [6,8)
        System.out.println("s.substring(0, 5):    \"" + sub2 + "\"");
        System.out.println("s.substring(6, 8):    \"" + sub3 + "\"");

        // j = length 不越界，等同于 s.substring(beginIndex)
        String sub4 = s.substring(6, s.length());  // "world"
        System.out.println("s.substring(6, len):  \"" + sub4 + "\"");

        // LeetCode 常用模式
        String text = "abcdef";
        String first3 = text.substring(0, 3);                // "abc"  前 n 个
        String last3  = text.substring(text.length() - 3);   // "def"  后 n 个
        String mid    = text.substring(2, 4);                // "cd"   中间 [i,j)
        System.out.println("前3: " + first3 + ", 后3: " + last3 + ", 中间[2,4): " + mid);

        // 对比 substring 与 charAt
        for (int i = 0; i < s.length(); i++) {
            System.out.print(s.charAt(i) + " ");
        }
        System.out.println();
    }

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
