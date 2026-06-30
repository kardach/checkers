package org.example.support;

import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayNameGenerator;

import java.lang.reflect.Method;
import java.util.List;

public class ReplaceCamelCase extends DisplayNameGenerator.Standard {

    @NullMarked
    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return replaceCamelCase(super.generateDisplayNameForClass(testClass));
    }

    @NullMarked
    @Override
    public String generateDisplayNameForNestedClass(List<Class<?>> enclosingInstanceTypes, Class<?> nestedClass) {
        return replaceCamelCase(super.generateDisplayNameForNestedClass(enclosingInstanceTypes, nestedClass));
    }

    @NullMarked
    @Override
    public String generateDisplayNameForMethod(List<Class<?>> enclosingInstanceTypes, Class<?> testClass,
                                               Method testMethod) {
        return replaceCamelCase(super.generateDisplayNameForMethod(enclosingInstanceTypes, testClass, testMethod));
    }

    String replaceCamelCase(String camelCase) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(camelCase.charAt(0));
        int stop = camelCase.indexOf("()") == camelCase.length() - 2 ? camelCase.length() - 2 : camelCase.length();
        for (int i = 1; i < stop; i++) {
            if (Character.isUpperCase(camelCase.charAt(i))) {
                stringBuilder.append(' ');
                stringBuilder.append(Character.toLowerCase(camelCase.charAt(i)));
            } else {
                stringBuilder.append(camelCase.charAt(i));
            }
        }
        return stringBuilder.toString();
    }
}