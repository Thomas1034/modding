/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * If you modify this file, please include a notice stating the changes:
 * Example: "Modified by [Your Name] on [Date] - [Short Description of Changes]"
 */
package com.startraveler.verdant.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {

    public static Object getFromFinal(Object instance, String fieldName) {
        try {
            // Get the class of the instance
            Class<?> clazz = instance.getClass();

            // Get the field from the class
            Field field = clazz.getDeclaredField(fieldName);

            // Make the field accessible
            field.setAccessible(true);

            // Return the value of the field
            return field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object getMethodCallableOrRunnable(Object instance, String methodName, Class<?>... paramTypes) {
        try {
            // Find the method with the specified name and parameter types in the class
            // hierarchy
            Method method = findMethodInHierarchy(instance.getClass(), methodName, paramTypes);

            if (method == null) {
                throw new NoSuchMethodException(
                        "Method " + methodName + " with specified parameters not found in class hierarchy.");
            }

            // Make the method accessible if it is private or protected
            method.setAccessible(true);

            // Check if the method returns void
            if (method.getReturnType().equals(Void.TYPE)) {
                // Return a Runnable for void methods
                return (RunnableWithArgs) (args) -> {
                    try {
                        method.invoke(instance, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                };
            } else {
                // Return a CallableWithArgs<Object> for non-void methods
                return (CallableWithArgs<Object>) (args) -> {
                    try {
                        return method.invoke(instance, args);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                };
            }
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private static Method findMethodInHierarchy(Class<?> clazz, String methodName, Class<?>... paramTypes) {
        while (clazz != null) {
            try {
                return clazz.getDeclaredMethod(methodName, paramTypes);
            } catch (NoSuchMethodException e) {
                // Method not found in this class, check the superclass
                clazz = clazz.getSuperclass();
            }
        }
        return null; // Method not found in the class hierarchy
    }

    public static CallableWithArgs<?> getMethodCallable(Object instance, String methodName, Class<?>... paramTypes) {

        Object toReturn = getMethodCallableOrRunnable(instance, methodName, paramTypes);

        return (toReturn instanceof CallableWithArgs<?> callable) ? callable : null;
    }

    public static RunnableWithArgs getMethodRunnable(Object instance, String methodName, Class<?>... paramTypes) {

        Object toReturn = getMethodCallableOrRunnable(instance, methodName, paramTypes);

        return (toReturn instanceof RunnableWithArgs runnable) ? runnable : null;
    }

    // Functional interface for Callable with arguments
    @FunctionalInterface
    public interface CallableWithArgs<V> {
        V call(Object... args);
    }

    // Functional interface for Runnable with arguments
    @FunctionalInterface
    public interface RunnableWithArgs {
        void run(Object... args);
    }

    public static void printAllMethods(Object target) {
        Method[] methods = target.getClass().getMethods();
        System.out.println(target.getClass() + " has:");
        for (Method method : methods) {
            System.out.println(method);
        }
    }

    public static void printAllFields(Object target) {
        Field[] fields = target.getClass().getDeclaredFields();
        System.out.println(target.getClass() + " has:");
        for (Field field : fields) {
            System.out.println(field);
        }
    }

    public static void printAllFields(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();
        System.out.println(clazz + " has:");
        for (Field field : fields) {
            System.out.println(field);
        }
    }

    public static void printAllMethods(Class<?> clazz) {
        Method[] methods = clazz.getMethods();
        System.out.println(clazz + " has:");
        for (Method method : methods) {
            System.out.println(method);
        }
    }

}

