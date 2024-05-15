package com.thomas.verdant.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

	
	public static void makeMethodPublic(Class<?> clazz, String methodName) throws NoSuchMethodException, IllegalAccessException {
        // Get the Method object
        Method method = clazz.getDeclaredMethod(methodName);
        
        // Set the method accessible if it's private
        if (Modifier.isPrivate(method.getModifiers())) {
            method.setAccessible(true);
            // Check if the method is private, as it might be already accessible
            if (!Modifier.isPublic(method.getModifiers())) {
                // Get the method's declaring class
                Class<?> declaringClass = method.getDeclaringClass();
                // Change the method's modifiers to public
                Method reflectedMethod = declaringClass.getDeclaredMethod(methodName, method.getParameterTypes());
                java.lang.reflect.Field modifiersField = null;
				try {
					modifiersField = Method.class.getDeclaredField("modifiers");
				} catch (NoSuchFieldException e) {
					e.printStackTrace();
				} catch (SecurityException e) {
					e.printStackTrace();
				}
                modifiersField.setAccessible(true);
                modifiersField.setInt(reflectedMethod, reflectedMethod.getModifiers() & ~Modifier.PRIVATE);
                modifiersField.setAccessible(false);
            }
        }
    }
	
	
	@SuppressWarnings("rawtypes")
	public static Object getFromStaticFinal(Class clazz, String fieldName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		// Get the Field object representing the private static final field
		Field field = clazz.getDeclaredField(fieldName);
		
		// Since the field is private, we need to make it accessible
		field.setAccessible(true);

		// Ensure that the field is static and final
		int modifiers = field.getModifiers();
		if (Modifier.isStatic(modifiers) && Modifier.isFinal(modifiers)) {
			// Get the value of the field
			Object value = field.get(null); // Pass null for static fields

			// Now you can use the value
			return value;
		} else {
			throw new IllegalAccessException(
					"This field is either non-static or non-final, use the appropriate method to access it.");
		}
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getFromFinal(Object instance, String fieldName)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Class clazz = instance.getClass();
		
		// Get the Field object representing the private static final field
		Field field = clazz.getDeclaredField(fieldName);
		
		// Since the field is private, we need to make it accessible
		field.setAccessible(true);

		// Ensure that the field is final
		int modifiers = field.getModifiers();
		if (Modifier.isFinal(modifiers)) {
			// Get the value of the field
			Object value = field.get(instance); // Pass null for static fields

			// Now you can use the value
			return value;
		} else {
			throw new IllegalAccessException(
					"This field is either non-final, use the appropriate method to access it.");
		}

	}
}
