package checkers.support;

import java.lang.reflect.Method;
import java.util.List;

import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayNameGenerator;

@NullMarked
public class ReplaceCamelCase extends DisplayNameGenerator.Standard {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return replaceCamelCase(super.generateDisplayNameForClass(testClass));
    }

    @Override
    public String generateDisplayNameForNestedClass(List<Class<?>> enclosingInstanceTypes, Class<?> nestedClass) {
        return replaceCamelCase(super.generateDisplayNameForNestedClass(enclosingInstanceTypes, nestedClass));
    }

    @Override
    public String generateDisplayNameForMethod(List<Class<?>> enclosingInstanceTypes, Class<?> testClass,
            Method testMethod) {
        return replaceCamelCase(super.generateDisplayNameForMethod(enclosingInstanceTypes, testClass, testMethod));
    }

    String replaceCamelCase(String camelCase) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while(i < camelCase.length() && camelCase.charAt(i) != '(') {
            if (Character.isUpperCase(camelCase.charAt(i)) && i != 0) {
                stringBuilder.append(' ');
                stringBuilder.append(Character.toLowerCase(camelCase.charAt(i)));
            } else {
                stringBuilder.append(camelCase.charAt(i));
            }
            i++;
        }
        return stringBuilder.toString();
    }
}