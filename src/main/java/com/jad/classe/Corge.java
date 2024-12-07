package com.jad.classe;

import com.jad.ICorge;
import com.jad.IFoo;

public class Corge implements ICorge {
    private IFoo foo;

    public Corge(IFoo foo) {
        this.foo = foo;
    }

    @Override
    public IFoo getFoo() {
        return foo;
    }

    @Override
    public void setFoo(IFoo foo) {
        this.foo = foo;
    }
}

