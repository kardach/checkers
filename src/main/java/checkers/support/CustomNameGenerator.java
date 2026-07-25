package checkers.support;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.DisplayNameGenerator;

@NullMarked
public class CustomNameGenerator extends DisplayNameGenerator.Standard {

    @Override
    public String generateDisplayNameForClass(Class<?> testClass) {
        return formatClassName(super.generateDisplayNameForClass(testClass));
    }

    @Override
    public String generateDisplayNameForNestedClass(List<Class<?>> enclosingInstanceTypes, Class<?> nestedClass) {
        return formatClassName(super.generateDisplayNameForNestedClass(enclosingInstanceTypes, nestedClass));
    }

    @Override
    public String generateDisplayNameForMethod(List<Class<?>> enclosingInstanceTypes, Class<?> testClass,
            Method testMethod) {
        return formatMethodName(super.generateDisplayNameForMethod(enclosingInstanceTypes, testClass, testMethod));
    }

    String camelCaseToSpaceSeparatedLowerCase(String camelCase) {
        StringBuilder stringBuilder = new StringBuilder();
        int i = 0;
        while(i < camelCase.length() && camelCase.charAt(i) != '(') {
            if (Character.isUpperCase(camelCase.charAt(i))) {
                stringBuilder.append(' ');
                stringBuilder.append(Character.toLowerCase(camelCase.charAt(i)));
            } else {
                stringBuilder.append(camelCase.charAt(i));
            }
            i++;
        }
        return stringBuilder.toString();
    }

    String decapitalize(String string) {
        return Pattern
                .compile("^.")
                .matcher(string)
                .replaceFirst(matchResult -> matchResult.group().toLowerCase());
    }

    String formatMethodPart(String string) {
        String[] methodNames = string.split("And");
        StringBuilder stringBuilder = new StringBuilder();
        for (String methodName : methodNames) {
            stringBuilder.append(decapitalize(methodName));
            stringBuilder.append(", ");
        }
        return stringBuilder.toString();
    }

    String formatShouldPart(String string) {
        String stringBuilder = string.substring(0, 6) +
                ": " +
                camelCaseToSpaceSeparatedLowerCase(string.substring(6));
        return stringBuilder;
    }

    String formattedWhenPart(String string) {
        String stringBuilder = string.substring(0, 4) +
                ": " +
                camelCaseToSpaceSeparatedLowerCase(string.substring(4));
        return stringBuilder;
    }

    String formatMethodName(String string) {
        int shouldPartStart = string.indexOf("Should");
        int whenPartStart = string.indexOf("When");
        if (shouldPartStart == -1 && whenPartStart == -1) {
            return formatMethodPart(string);
        } else if (shouldPartStart != -1 && whenPartStart == -1) {
            String methodPart = string.substring(0, shouldPartStart);
            String shouldPart = string.substring(shouldPartStart);
            String formattedMethodPart = formatMethodPart(methodPart);
            String formattedShouldPart = formatShouldPart(shouldPart);
            return formattedMethodPart + formattedShouldPart;
        } else if (shouldPartStart != -1 && whenPartStart != -1) {
            String methodPart = string.substring(0, shouldPartStart);
            String shouldPart = string.substring(shouldPartStart, whenPartStart);
            String whenPart = string.substring(whenPartStart);
            String formattedMethodPart = formatMethodPart(methodPart);
            String formattedShouldPart = formatShouldPart(shouldPart);
            String formattedWhenPart = formattedWhenPart(whenPart);
            return formattedMethodPart + formattedShouldPart + ", " + formattedWhenPart;
        }
        return camelCaseToSpaceSeparatedLowerCase(string);
    }

    String formatClassName(String string) {
        int index = string.indexOf("Test");
        if (index != -1) {
            return "Test " + camelCaseToSpaceSeparatedLowerCase(string.substring(0, index));
        }
        return camelCaseToSpaceSeparatedLowerCase(string);
    }
}