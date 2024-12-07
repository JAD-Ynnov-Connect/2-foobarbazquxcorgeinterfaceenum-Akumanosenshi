package com.jad;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


class QuxTest {
    private static final int NB_OF_FIELDS = 0;
    private static final int NB_OF_NON_CONSTRUCTOR_METHOD = 1;
    private static Class<?> quxClass;

    @BeforeAll
    static void beforeAll() {
        QuxTest.quxClass = Utils.testIfClassExists("com.jad.classe.Qux");
    }

    @Test
    void doAllTests() {
        this.topLevelClassTest();
        this.countAttributesTest();
        this.countMethodsTest();
        this.implementsTest();
        this.hasDoSomethingLikeAQuxMethodTest();
    }

    @Test
    void topLevelClassTest() {
        Utils.testIfIsATopLevelClass(QuxTest.quxClass);
    }

    @Test
    void countAttributesTest() {
        Utils.testCountAttributes(QuxTest.quxClass, QuxTest.NB_OF_FIELDS);
    }

    @Test
    void countMethodsTest() {
        Utils.testCountNonConstructorMethods(QuxTest.quxClass, QuxTest.NB_OF_NON_CONSTRUCTOR_METHOD);
    }

    @Test
    void implementsTest() {
        Utils.testImplementsInterface(QuxTest.quxClass, "com.jad.IQux");
    }

    @Test
    void hasDoSomethingLikeAQuxMethodTest() {
        Utils.testIfMethodExists(QuxTest.quxClass, "doSomethingLikeAQux");
    }
}