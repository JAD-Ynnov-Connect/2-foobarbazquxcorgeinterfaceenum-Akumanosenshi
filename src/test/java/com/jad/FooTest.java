package com.jad;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;


class FooTest {
    private static final int NB_OF_FIELDS = 4;
    private static final int NB_OF_NON_CONSTRUCTOR_METHOD = 6;
    private static Class<?> fooClass;

    @BeforeAll
    static void beforeAll() {
        FooTest.fooClass = Utils.testIfClassExists("com.jad.classe.Foo");
    }


    @Test
    void doAllTests() {

        this.topLevelClassTest();
        this.countAttributesTest();
        this.countMethodsTest();
        this.implementsTest();
        this.bazAttributeTest();
        this.barsAttributeTest();
        this.quxAttributeTest();
        this.corgeAttributeTest();
        this.singleConstructorWithIBazParameterTest();
        this.getCorgeAndSetCorgeTest();
        this.getBazMethodTest();
        this.getQuxMethodTest();
        this.getBarsMethodTest();
        this.addBarMethodTest();
    }

    @Test
    void topLevelClassTest() {
        Utils.testIfIsATopLevelClass(FooTest.fooClass);
    }

    @Test
    void countAttributesTest() {
        Utils.testCountAttributes(FooTest.fooClass, FooTest.NB_OF_FIELDS);
    }

    @Test
    void countMethodsTest() {
        Utils.testCountNonConstructorMethods(FooTest.fooClass, FooTest.NB_OF_NON_CONSTRUCTOR_METHOD);
    }

    @Test
    void implementsTest() {
        Utils.testImplementsInterface(FooTest.fooClass, "com.jad.IFoo");
    }

    @Test
    void bazAttributeTest() {
        Utils.testIfPrivateAttributeExistsWithGoodType("com.jad.IBaz", "baz", FooTest.fooClass);
    }

    @Test
    void barsAttributeTest() {
        Utils.testIfPrivateAttributeExistsWithGenericType("java.util.List<com.jad.IBar>", "bars", FooTest.fooClass);
    }

    @Test
    void quxAttributeTest() {
        Utils.testIfPrivateAttributeExistsWithGoodType("com.jad.IQux", "qux", FooTest.fooClass);
    }

    @Test
    void corgeAttributeTest() {
        Utils.testIfPrivateAttributeExistsWithGoodType("com.jad.ICorge", "corge", FooTest.fooClass);
    }

    @Test
    void singleConstructorWithIBazParameterTest() {
        Class<?> iBazClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBaz"), "The class IBaz does not exist.");
        assertNotNull(iBazClass, "The class IBaz does not exist.");

        Class<?> iQuxClass = assertDoesNotThrow(() -> Class.forName("com.jad.IQux"), "The class IQux does not exist.");
        assertNotNull(iQuxClass, "The class IQux does not exist.");

        Class<?> iBarClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBar"), "The class IBar does not exist.");
        assertNotNull(iBarClass, "The class IBar does not exist.");

        Constructor<?>[] constructors = FooTest.fooClass.getDeclaredConstructors();
        assertEquals(1, constructors.length, "Foo should have only one constructor.");
        Constructor<?> constructor = constructors[0];
        Class<?>[] parameterTypes = constructor.getParameterTypes();
        assertEquals(1, parameterTypes.length, "Foo constructor should have one parameter.");
        assertEquals(iBazClass, parameterTypes[0], "Foo constructor parameter should be of type IBaz.");

        Object mockBaz = mock(iBazClass);
        Object fooInstance = assertDoesNotThrow(() -> constructor.newInstance(mockBaz));

        Field bazField = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredField("baz"));
        bazField.setAccessible(true);
        Object bazValue = assertDoesNotThrow(() -> bazField.get(fooInstance));
        assertEquals(mockBaz, bazValue, "The baz field should be initialized with the constructor parameter.");

        Field barsField = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredField("bars"));
        barsField.setAccessible(true);
        Object barsValue = assertDoesNotThrow(() -> barsField.get(fooInstance));
        assertTrue(barsValue == null || barsValue instanceof List,
                   "The bars field should be null or of type List<IBar>.");

        Field quxField = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredField("qux"));
        quxField.setAccessible(true);
        Object quxValue = assertDoesNotThrow(() -> quxField.get(fooInstance));
        assertNotNull(quxValue, "The qux field should be initialized with an instance of IQux.");
        assertTrue(iQuxClass.isInstance(quxValue), "The qux field should be an instance of IQux.");

        Field corgeField = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredField("corge"));
        corgeField.setAccessible(true);
        Object corgeValue = assertDoesNotThrow(() -> corgeField.get(fooInstance));
        assertNull(corgeValue, "The corge field should be null.");
    }

    @Test
    void getCorgeAndSetCorgeTest() {
        Class<?> iCorgeClass = assertDoesNotThrow(() -> Class.forName("com.jad.ICorge"),
                                                  "The class ICorge does not exist.");
        assertNotNull(iCorgeClass, "The class ICorge does not exist.");

        Method getCorgeMethod = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredMethod("getCorge"));
        assertNotNull(getCorgeMethod, "The method getCorge does not exist.");

        Method setCorgeMethod = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredMethod("setCorge", iCorgeClass));
        assertNotNull(setCorgeMethod, "The method setCorge does not exist.");

        Object mockCorge = mock(iCorgeClass);

        Class<?> iBazClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBaz"), "The class IBaz does not exist.");
        Object mockBaz = mock(iBazClass);

        Object fooInstance = assertDoesNotThrow(
                () -> FooTest.fooClass.getDeclaredConstructor(iBazClass).newInstance(mockBaz));

        assertDoesNotThrow(() -> setCorgeMethod.invoke(fooInstance, mockCorge));

        Object corgeValue = assertDoesNotThrow(() -> getCorgeMethod.invoke(fooInstance));
        assertEquals(mockCorge, corgeValue, "The getCorge method should return the value set by the setCorge method.");
    }

    @Test
    void getBazMethodTest() {
        Class<?> iBazClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBaz"), "The class IBaz does not exist.");
        assertNotNull(iBazClass, "The class IBaz does not exist.");

        Method getBazMethod = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredMethod("getBaz"));
        assertNotNull(getBazMethod, "The method getBaz does not exist.");

        Object mockBaz = mock(iBazClass);

        Object fooInstance = assertDoesNotThrow(
                () -> FooTest.fooClass.getDeclaredConstructor(iBazClass).newInstance(mockBaz));

        Object bazValue = assertDoesNotThrow(() -> getBazMethod.invoke(fooInstance));
        assertEquals(mockBaz, bazValue, "The getBaz method should return the value of the baz attribute.");
    }

    @Test
    void getQuxMethodTest() {
        Class<?> iQuxClass = assertDoesNotThrow(() -> Class.forName("com.jad.IQux"));
        assertNotNull(iQuxClass);

        Method getQuxMethod = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredMethod("getQux"));
        assertNotNull(getQuxMethod);

        Class<?> iBazClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBaz"));
        Object mockBaz = mock(iBazClass);

        Object fooInstance = assertDoesNotThrow(
                () -> FooTest.fooClass.getDeclaredConstructor(iBazClass).newInstance(mockBaz));

        Object quxValue = assertDoesNotThrow(() -> getQuxMethod.invoke(fooInstance));
        assertNotNull(quxValue);
        assertTrue(iQuxClass.isInstance(quxValue));
    }

    @Test
    void getBarsMethodTest() {
        Class<?> iBarClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBar"));
        assertNotNull(iBarClass);

        Method getBarsMethod = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredMethod("getBars"));
        assertNotNull(getBarsMethod);

        Class<?> iBazClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBaz"));
        Object mockBaz = mock(iBazClass);

        Object fooInstance = assertDoesNotThrow(
                () -> FooTest.fooClass.getDeclaredConstructor(iBazClass).newInstance(mockBaz));

        Object barsValue = assertDoesNotThrow(() -> getBarsMethod.invoke(fooInstance));
        assertTrue(barsValue == null || barsValue instanceof List,
                   "The bars field should be null or of type List<IBar>.");
    }

    @Test
    void addBarMethodTest() {
        Class<?> iBarClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBar"));
        assertNotNull(iBarClass);

        Method addBarMethod = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredMethod("addBar", iBarClass));
        assertNotNull(addBarMethod);

        Method getBarsMethod = assertDoesNotThrow(() -> FooTest.fooClass.getDeclaredMethod("getBars"));
        assertNotNull(getBarsMethod);

        Class<?> iBazClass = assertDoesNotThrow(() -> Class.forName("com.jad.IBaz"));
        Object mockBaz = mock(iBazClass);

        Object fooInstance = assertDoesNotThrow(
                () -> FooTest.fooClass.getDeclaredConstructor(iBazClass).newInstance(mockBaz));

        Object mockBar = mock(iBarClass);

        assertDoesNotThrow(() -> addBarMethod.invoke(fooInstance, mockBar));

        Object barsValue = assertDoesNotThrow(() -> getBarsMethod.invoke(fooInstance));
        assertNotNull(barsValue);
        assertInstanceOf(List.class, barsValue, "The bars field should be of type List<IBar>.");
        assertTrue(((List<?>) barsValue).contains(mockBar), "The bars field should contain the added bar.");
    }
}