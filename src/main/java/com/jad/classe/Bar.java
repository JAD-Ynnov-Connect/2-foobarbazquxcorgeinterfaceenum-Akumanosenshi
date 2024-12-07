package com.jad.classe;

import com.jad.IBar;

public class Bar implements IBar {
    @Override
    public void doSomethingLikeABar() {
        System.out.println("Doing something like a Bar");
    }
}