package main.lombokapp;

import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;


public class BaseClassTest {

    /**
     * class.forName()
     * ClassEntendsBase.class
     * Class<T> 的用法
     * https://www.cnblogs.com/sharpest/p/7807009.html
     */
    @Test
    public void testBaseClass() {
        BaseClass x0 = new BaseClass();
        ClassExtendsBase x1 = new ClassExtendsBase();
        ClassExtendsBase2 x2 = new ClassExtendsBase2();
        System.out.println(x0);
        System.out.println(x1);
        System.out.println(x2);
        System.out.println();

        // 从实例中获取Class
        System.out.println("Get Class from instance");
        Class c0 = x0.getClass();
        Class c1 = x1.getClass();
        Class c2 = x2.getClass();
        System.out.println(c0);
        System.out.println(c1);
        System.out.println(c2);
        System.out.println();

        //使用静态方法Class.forName获取
        System.out.println("Get Class from Class.forName");
        try {
            Class cc0 = Class.forName(x0.getClass().getName());
            Class cc1 = Class.forName(x1.getClass().getName());
            Class cc2 = Class.forName(x2.getClass().getName());
            // must use full name.
//            Class cc3 = Class.forName(x0.getClass().getSimpleName());
            System.out.println(cc0);
            System.out.println(cc1);
            System.out.println(cc2);
            System.out.println("Constructor");
            System.out.println(cc0.getConstructor());
            System.out.println("Fields");
            System.out.println(cc0.getDeclaredFields());
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // *.class获取
        System.out.println("Get Class .class");
        Class ccc0 = BaseClass.class;
        Class ccc1 = ClassExtendsBase.class;
        Class ccc2 = ClassExtendsBase2.class;
        System.out.println(ccc0);
        System.out.println(ccc1);
        System.out.println(ccc2);
        System.out.println();

        /**
         * Class是类的meta data，有什么用？
         *  生成新的instance，相当于无参数构造函数
         */
        System.out.println("Use Class to create new instance");
        try {
            // 默认生成Object类型
            BaseClass xx0 = (BaseClass) ccc0.getDeclaredConstructor().newInstance();
            ClassExtendsBase xx1 = (ClassExtendsBase) ccc1.getDeclaredConstructor().newInstance();
            BaseClass xx2 = (BaseClass) ccc2.getDeclaredConstructor().newInstance();
            System.out.println(xx0);
            System.out.println(xx1);
            System.out.println(xx2);
            System.out.println();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        // 看看有什么方法, fully qualified parameter and return types.
        try {
            Class c = Class.forName("java.util.Stack");
            Method m[] = c.getDeclaredMethods();
            for (int i = 0; i < m.length; i++)
                System.out.println(m[i].toString());
        } catch (Throwable e) {
            System.err.println(e);
        }
        System.out.println();

        /**
         * 调用方法, 注意这种情况还是需要一个实例
         * 所以为什么需要这种用法?
         */
        System.out.println("Use Class to call method");
        try {
            Class cls = MyCalculator.class;
            Class partypes[] = new Class[2];
            partypes[0] = Integer.TYPE;
            partypes[1] = Integer.TYPE;
            Method add = cls.getMethod("add", partypes);
            MyCalculator calculator = new MyCalculator();
            Object arglist[] = new Object[2];
            arglist[0] = new Integer(37);
            arglist[1] = new Integer(47);
            Object retobj = add.invoke(calculator, arglist);
            Integer retval = (Integer) retobj;
            System.out.println(retval.intValue());
        } catch (Throwable e) {
            System.err.println(e);
        }
        System.out.println();

        /**
         * 改变value, 注意这种情况还是需要一个实例
         * 所以为什么需要这种用法?
         *
         */
        System.out.println("Use Class to set field");
        try {
            Class cls = MyCalculator.class;
            Field fld = cls.getDeclaredField("x");
            MyCalculator cal = new MyCalculator();
            System.out.println(cal);
            fld.setInt(cal, 2);
            System.out.println(cal);
        } catch (Throwable e) {
            System.err.println(e);
        }
        System.out.println();

        /**
         *
         */
        System.out.println("Use Class to create array");
        try {
            Class cls = Class.forName("java.lang.String");
            Object arr = Array.newInstance(cls, 10);
            Array.set(arr, 5, "this is a test");
            String s = (String) Array.get(arr, 5);
            System.out.println(s);
        } catch (Throwable e) {
            System.err.println(e);
        }
        System.out.println();
    }
}
