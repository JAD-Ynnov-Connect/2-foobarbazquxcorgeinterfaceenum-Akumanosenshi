package com.jad;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class BarTest {
    private static final int NB_OF_FIELDS = 0;
    private static final int NB_OF_NON_CONSTRUCTOR_METHOD = 1;
    private static Class<?> barClass;

    @BeforeAll
    static void beforeAll() {
        BarTest.barClass = Utils.testIfClassExists("com.jad.classe.Bar");
    }

    @Test
    void doAllTests() {
        this.topLevelClassTest();
        this.countAttributesTest();
        this.countMethodsTest();
        this.implementsTest();
        this.hasDoSomethingLikeABarMethodTest();
    }

    @Test
    void topLevelClassTest() {
        Utils.testIfIsATopLevelClass(BarTest.barClass);
    }

    @Test
    void countAttributesTest() {
        Utils.testCountAttributes(BarTest.barClass, BarTest.NB_OF_FIELDS);
    }

    @Test
    void countMethodsTest() {
        Utils.testCountNonConstructorMethods(BarTest.barClass, BarTest.NB_OF_NON_CONSTRUCTOR_METHOD);
    }

    @Test
    void implementsTest() {
        Utils.testImplementsInterface(BarTest.barClass, "com.jad.IBar");
    }

    @Test
    void hasDoSomethingLikeABarMethodTest() {
        Utils.testIfMethodExists(BarTest.barClass, "doSomethingLikeABar");
    }
}