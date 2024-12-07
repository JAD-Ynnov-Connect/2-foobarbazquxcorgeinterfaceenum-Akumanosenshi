package com.jad;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;


class EBazTest {
    private static final int NB_OF_FIELDS = 5;
    private static final int NB_OF_NON_CONSTRUCTOR_METHOD = 4;
    private static Class<?> eBazClass;

    @BeforeAll
    static void beforeAll() {
        EBazTest.eBazClass = Utils.testIfClassExists("com.jad.classe.EBaz");
    }

    @Test
    void doAllTests() {
        this.enumTest();
        this.countAttributesTest();
        this.countMethodsTest();
        this.implementsTest();
        this.hasDoSomethingLikeABazMethodTest();
        this.hasGetNameMethodTest();
        this.enumValuesTest();
    }

    @Test
    void enumTest() {
        Utils.testIfIsAnEnumClass(EBazTest.eBazClass);
    }

    @Test
    void countAttributesTest() {
        Utils.testCountAttributes(EBazTest.eBazClass, EBazTest.NB_OF_FIELDS);
    }

    @Test
    void countMethodsTest() {
        Utils.testCountNonConstructorMethods(EBazTest.eBazClass, EBazTest.NB_OF_NON_CONSTRUCTOR_METHOD);
    }

    @Test
    void implementsTest() {
        Utils.testImplementsInterface(EBazTest.eBazClass, "com.jad.IBaz");
    }

    @Test
    void hasDoSomethingLikeABazMethodTest() {
        Utils.testIfMethodExists(EBazTest.eBazClass, "doSomethingLikeABaz");
    }

    @Test
    void hasGetNameMethodTest() {
        Utils.testIfMethodExists(EBazTest.eBazClass, "getName");
    }

    @Test
    void enumValuesTest() {
        Object[] enumConstants = assertDoesNotThrow(() -> EBazTest.eBazClass.getEnumConstants());
        assertEquals(3, enumConstants.length, "EBaz should have three enum values.");

        java.lang.reflect.Method getNameMethod = assertDoesNotThrow(
                () -> EBazTest.eBazClass.getDeclaredMethod("getName"));
        getNameMethod.setAccessible(true);

        String[] expectedNames = {"BAZ1", "BAZ2", "BAZ3"};
        String[] expectedValues = {"Baz1", "Baz2", "Baz3"};

        for (int i = 0; i < enumConstants.length; i++) {
            assertEquals(expectedNames[i], enumConstants[i].toString(),
                         "Enum name should be " + expectedNames[i] + ".");
            final int finalI = i;
            assertEquals(expectedValues[i],
                         assertDoesNotThrow(() -> (String) getNameMethod.invoke(enumConstants[finalI])),
                         expectedNames[i] + " name should be " + expectedValues[i] + ".");
        }
    }
}