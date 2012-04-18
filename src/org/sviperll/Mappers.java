/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll;

/**
 *
 * @author vir
 */
public class Mappers {
    private static Mapper<?, ?> id = new IdMapper<Object>();

    @SuppressWarnings("unchecked")
    public static <A> Mapper<A, A> id() {
        return (Mapper<A, A>)id;
    }

    public static <A, B, C> Mapper<A, C> compose(final Mapper<A, B> f, final Mapper<B, C> g) {
        return new ComposeMapper<A, B, C>(f, g);
    }

    private Mappers() {}

    private static class IdMapper<T> implements Mapper<T, T> {
        @Override
        public T map(T t) {
            return t;
        }

        @Override
        public <V> Mapper<T, V> composeWith(Mapper<T, V> f) {
            return Mappers.compose(this, f);
        }

    }

    private static class ComposeMapper<A, B, C> implements Mapper<A, C> {
        private final Mapper<A, B> f;
        private final Mapper<B, C> g;
        public ComposeMapper(Mapper<A, B> f, Mapper<B, C> g) {
            this.f = f;
            this.g = g;
        }

        @Override
        public C map(A t) {
            return g.map(f.map(t));
        }

        @Override
        public <V> Mapper<A, V> composeWith(Mapper<C, V> f) {
            return Mappers.compose(this, f);
        }

    }
}
