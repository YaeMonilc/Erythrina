package cc.kocho.utils;

import java.util.Set;
import java.util.stream.Collectors;

public class ClassUtil {
    public static Set<Class<?>> getInstances(Class supClass) {
        String aPackage = cn.hutool.core.util.ClassUtil.getPackage(supClass);
        Set<Class<?>> classes = cn.hutool.core.util.ClassUtil.scanPackage(aPackage);
        return classes.stream().filter(sonClass -> {
            boolean allAssignableFrom = cn.hutool.core.util.ClassUtil.isAllAssignableFrom(new java.lang.Class[]{supClass},
                    new java.lang.Class[]{sonClass});
            return allAssignableFrom && sonClass != supClass;
        }).collect(Collectors.toSet());
    }
}
