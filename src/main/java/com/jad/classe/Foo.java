package com.jad.classe;

import com.jad.*;

import java.util.ArrayList;
import java.util.List;

public class Foo implements IFoo {
    private IBaz baz;
    private List<IBar> bars;
    private IQux qux;
    private ICorge corge;


    public Foo(IBaz baz) {
        this.baz = baz;
        this.bars = new ArrayList<>();
        this.qux = new Qux();
    }

    public IBaz getBaz() {
        return baz;
    }

    public List<IBar> getBars() {
        return bars;
    }

    public void addBar(IBar bar) {
        bars.add(bar);
    }

    public IQux getQux() {
        return qux;
    }


    @Override
    public ICorge getCorge() {
        return corge;
    }

    @Override
    public void setCorge(ICorge corge) {
        this.corge = corge;
    }

}
