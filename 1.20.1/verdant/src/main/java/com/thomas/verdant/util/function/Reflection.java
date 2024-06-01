package com.thomas.verdant.util.function;

import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

	public static void makeMethodPublic(Class<?> clazz, String methodName)
			throws NoSuchMethodException, IllegalAccessException {
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
			throw new IllegalAccessException("This field is non-final, use the appropriate method to access it.");
		}
	}

	@SuppressWarnings("rawtypes")
	public static void setFromFinal(Object instance, String fieldName, Object value)
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Class clazz = instance.getClass();

		// Get the Field object representing the private final field
		Field field = clazz.getDeclaredField(fieldName);

		// Since the field is private, we need to make it accessible
		field.setAccessible(true);

		// Ensure that the field is final
		int modifiers = field.getModifiers();
		if (Modifier.isFinal(modifiers)) {
			// Set the value of the field
			field.set(instance, value);
		} else {
			throw new IllegalAccessException("This field is non-final, use the appropriate method to access it.");
		}
	}

	public static void setPrivateField(Object object, String fieldName, Class<?> fieldClass, Object newValue,
			Class<?> newValueClass) throws NoSuchFieldException, IllegalAccessException {
		Reflection.setPrivateFieldAs(object, object.getClass(), fieldName, fieldClass, newValue, newValueClass);
	}

	public static void setPrivateFieldAs(Object object, Class<?> clazz, String fieldName, Class<?> fieldClass,
			Object newValue, Class<?> newValueClass) throws NoSuchFieldException, IllegalAccessException {

		Field field = clazz.getDeclaredField(fieldName);
		field.setAccessible(true); // Allow access to private field

		// Remove the final modifier temporarily
		Field modifiersField = Field.class.getDeclaredField("modifiers");
		modifiersField.setAccessible(true);
		int modifiers = field.getModifiers();
		modifiersField.setInt(field, modifiers & ~Modifier.FINAL);

		// Now actually set the field.

		// Step 1: Get a Lookup object
		MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());

		// Step 2: Get the VarHandle for the private field
		VarHandle varHandle = lookup.findVarHandle(clazz, fieldName, fieldClass);

		// Step 3: Set the value of the private field.
		varHandle.set(object, newValue);
	}

	public static Object getPrivateField(Object object, String fieldName, Class<?> fieldClass)
			throws NoSuchFieldException, IllegalAccessException, InstantiationException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {
		return Reflection.getPrivateFieldAs(object, object.getClass(), fieldName, fieldClass);
	}

	public static Object getPrivateFieldAs(Object object, Class<?> clazz, String fieldName, Class<?> fieldClass)
			throws NoSuchFieldException, IllegalAccessException, InstantiationException, IllegalArgumentException,
			InvocationTargetException, NoSuchMethodException, SecurityException {

		// Step 1: Get a Lookup object
		MethodHandles.Lookup lookup = MethodHandles.privateLookupIn(clazz, MethodHandles.lookup());

		// Step 2: Get the VarHandle for the private field
		VarHandle varHandle = lookup.findVarHandle(clazz, fieldName, fieldClass);

		// Step 3: Get the value of the private field
		return varHandle.get(object);
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
