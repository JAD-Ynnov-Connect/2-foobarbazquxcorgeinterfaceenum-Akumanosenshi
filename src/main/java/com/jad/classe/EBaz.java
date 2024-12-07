package com.jad.classe;

import com.jad.IBaz;


public enum EBaz implements IBaz {
    BAZ1("Baz1"),
    BAZ2("Baz2"),
    BAZ3("Baz3");

    private String name;

    EBaz(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public void doSomethingLikeABaz() {
        System.out.println("Doing something like a Baz: " + name);
    }

}
