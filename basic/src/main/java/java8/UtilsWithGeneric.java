package java8;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * 相对于Utils，这个UtilsWithGeneric更加强大，全部属于泛型来处理。
 * 要想做出这样的设计，最主要是要做好抽象，抽象的算法应该这么想：
 *  把要处理的实际class(Apple)抽象成object，判断的算法也从判断具体的class(Apple)抽象成object。
 *
 * @param <T>
 */
public class UtilsWithGeneric<T> {

    public static <T>List<T> filter(List<T> list, Predicate<T> p) {
        List<T> result = Lists.newArrayList();
        for (T e: list) {
            if (p.test(e)) {
                result.add(e);
            }
        }
        return result;
    }
}
