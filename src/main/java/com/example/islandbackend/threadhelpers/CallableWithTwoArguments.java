package com.example.islandbackend.threadhelpers;

import java.util.concurrent.Callable;
import java.util.function.BiFunction;

public class CallableWithTwoArguments<T, V, R> implements Callable<R> {

    private final BiFunction<T, V, R> biFunction;
    private final T arg1;
    private final V arg2;

    public CallableWithTwoArguments(BiFunction<T, V, R> function, T arg1, V arg2) {
        this.biFunction = function;
        this.arg1 = arg1;
        this.arg2 = arg2;
    }

    @Override
    public R call() throws Exception {
        return biFunction.apply(arg1, arg2);
    }
}
