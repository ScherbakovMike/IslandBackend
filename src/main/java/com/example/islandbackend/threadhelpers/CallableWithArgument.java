package com.example.islandbackend.threadhelpers;

import java.util.concurrent.Callable;
import java.util.function.Function;

public class CallableWithArgument<T, R> implements Callable<R> {

    private final Function<T, R> function;
    private final T arg1;

    public CallableWithArgument(Function<T, R> function, T arg1) {
        this.function = function;
        this.arg1 = arg1;
    }

    @Override
    public R call() throws Exception {
        return function.apply(arg1);
    }
}
