package com.jad;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class CorgeTest {
    private static final int NB_OF_FIELDS = 1;
    private static final int NB_OF_NON_CONSTRUCTOR_METHOD = 2;
    private static Class<?> corgeClass;

    @BeforeAll
    static void beforeAll() {
        CorgeTest.corgeClass = Utils.testIfClassExists("com.jad.classe.Corge");
    }

    @Test
    void doAllTests() {
        this.topLevelClassTest();
        this.countAttributesTest();
        this.countMethodsTest();
        this.implementsTest();
        this.fooAttributeTest();
        this.singleConstructorWithIFooParameterTest();
        this.getFooAndSetFooTest();
    }

    @Test
    void topLevelClassTest() {
        Utils.testIfIsATopLevelClass(CorgeTest.corgeClass);
    }

    @Test
    void countAttributesTest() {
        Utils.testCountAttributes(CorgeTest.corgeClass, CorgeTest.NB_OF_FIELDS);
    }

    @Test
    void countMethodsTest() {
        Utils.testCountNonConstructorMethods(CorgeTest.corgeClass, CorgeTest.NB_OF_NON_CONSTRUCTOR_METHOD);
    }

    @Test
    void implementsTest() {
        Utils.testImplementsInterface(CorgeTest.corgeClass, "com.jad.ICorge");
    }

    @Test
    void fooAttributeTest() {
        Utils.testIfPrivateAttributeExistsWithGoodType("com.jad.IFoo", "foo", CorgeTest.corgeClass);
    }

    @Test
    void singleConstructorWithIFooParameterTest() {
        // Verify that IFoo exists
        Class<?> iFooClass = assertDoesNotThrow(() -> Class.forName("com.jad.IFoo"), "The class IFoo does not exist.");
        assertNotNull(iFooClass, "The class IFoo does not exist.");

        // Verify that Corge has only one constructor with IFoo parameter
        Constructor<?>[] constructors = CorgeTest.corgeClass.getDeclaredConstructors();
        assertEquals(1, constructors.length, "Corge should have only one constructor.");
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        assertEquals(1, parameterTypes.length, "Corge constructor should have one parameter.");
        assertEquals(iFooClass, parameterTypes[0], "Corge constructor parameter should be of type IFoo.");

        // Create a mock instance of IFoo
        Object mockFoo = mock(iFooClass);
        Object corgeInstance = assertDoesNotThrow(() -> constructor.newInstance(mockFoo));

        // Access the private field 'foo' and check its value
        Field fooField = assertDoesNotThrow(() -> CorgeTest.corgeClass.getDeclaredField("foo"));
        fooField.setAccessible(true);
        Object fooValue = assertDoesNotThrow(() -> fooField.get(corgeInstance));
        assertEquals(mockFoo, fooValue, "The foo field should be initialized with the constructor parameter.");
    }

    @Test
    void getFooAndSetFooTest() {
        // Verify that IFoo exists
        Class<?> iFooClass = assertDoesNotThrow(() -> Class.forName("com.jad.IFoo"), "The class IFoo does not exist.");
        assertNotNull(iFooClass, "The class IFoo does not exist.");

        // Verify that Corge has a method getFoo
        Method getFooMethod = assertDoesNotThrow(() -> CorgeTest.corgeClass.getDeclaredMethod("getFoo"));
        assertNotNull(getFooMethod, "The method getFoo does not exist.");

        // Verify that Corge has a method setFoo with IFoo parameter
        Method setFooMethod = assertDoesNotThrow(() -> CorgeTest.corgeClass.getDeclaredMethod("setFoo", iFooClass));
        assertNotNull(setFooMethod, "The method setFoo does not exist.");

        // Verify that IFoo has a method getCorge
        Method getCorgeMethod = assertDoesNotThrow(() -> iFooClass.getDeclaredMethod("getCorge"));
        assertNotNull(getCorgeMethod, "The method getCorge does not exist.");

        // Create a mock instance of IFoo
        Object mockFoo = mock(iFooClass);

        // Create an instance of Corge
        Constructor<?> constructor = assertDoesNotThrow(() -> CorgeTest.corgeClass.getDeclaredConstructor(iFooClass));
        Object corgeInstance = assertDoesNotThrow(() -> constructor.newInstance(mockFoo));

        // Verify that the getFoo method returns the same instance of IFoo
        Object fooValue = assertDoesNotThrow(() -> getFooMethod.invoke(corgeInstance));
        assertEquals(mockFoo, fooValue, "The getFoo method should return the same instance of IFoo.");

        // Create a new mock instance of IFoo
        Object mockFoo2 = mock(iFooClass);

        // Verify that the setFoo method sets the new instance of IFoo
        assertDoesNotThrow(() -> setFooMethod.invoke(corgeInstance, mockFoo2));
        Object fooValue2 = assertDoesNotThrow(() -> getFooMethod.invoke(corgeInstance));
        assertEquals(mockFoo2, fooValue2, "The setFoo method should set the new instance of IFoo.");
    }
}