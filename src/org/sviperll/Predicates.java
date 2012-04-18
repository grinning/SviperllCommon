/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author vir
 */
public class Predicates {
    public static <T> Predicate<T> not(Predicate<T> p) {
        return new NotPredicate<T>(p);
    }
    public static <T> Predicate<T> and(Predicate<T> a, Predicate<T> b) {
        List<Predicate<T>> predicates = new ArrayList<Predicate<T>>(2);
        predicates.add(a);
        predicates.add(b);
        return new AndPredicate<T>(predicates);
    }
    public static <T> Predicate<T> or(Predicate<T> a, Predicate<T> b) {
        List<Predicate<T>> predicates = new ArrayList<Predicate<T>>(2);
        predicates.add(a);
        predicates.add(b);
        return new OrPredicate<T>(predicates);
    }
    public static <T> Predicate<T> and(List<Predicate<T>> predicates) {
        return new AndPredicate<T>(predicates);
    }
    public static <T> Predicate<T> or(List<Predicate<T>> predicates) {
        return new OrPredicate<T>(predicates);
    }
    private Predicates() {}

    private static class NotPredicate<T> implements Predicate<T> {
        private final Predicate<T> p;
        public NotPredicate(Predicate<T> p) {
            this.p = p;
        }

        @Override
        public boolean eval(T t) {
            return !p.eval(t);
        }

        @Override
        public Predicate<T> not() {
            return Predicates.not(this);
        }

        @Override
        public Predicate<T> and(Predicate<T> p) {
            return Predicates.and(this, p);
        }

        @Override
        public Predicate<T> or(Predicate<T> p) {
            return Predicates.or(this, p);
        }
    }

    private static class AndPredicate<T> implements Predicate<T> {
        private final List<Predicate<T>> predicates;
        public AndPredicate(List<Predicate<T>> predicates) {
            this.predicates = predicates;
        }

        @Override
        public boolean eval(T t) {
            for (Predicate<T> predicate: predicates)
                if (!predicate.eval(t))
                    return false;
            return true;
        }

        @Override
        public Predicate<T> not() {
            return Predicates.not(this);
        }

        @Override
        public Predicate<T> and(Predicate<T> p) {
            return Predicates.and(this, p);
        }

        @Override
        public Predicate<T> or(Predicate<T> p) {
            return Predicates.or(this, p);
        }
    }

    private static class OrPredicate<T> implements Predicate<T> {
        private final List<Predicate<T>> predicates;
        public OrPredicate(List<Predicate<T>> predicates) {
            this.predicates = predicates;
        }

        @Override
        public boolean eval(T t) {
            for (Predicate<T> predicate: predicates)
                if (predicate.eval(t))
                    return true;
            return false;
        }

        @Override
        public Predicate<T> not() {
            return Predicates.not(this);
        }

        @Override
        public Predicate<T> and(Predicate<T> p) {
            return Predicates.and(this, p);
        }

        @Override
        public Predicate<T> or(Predicate<T> p) {
            return Predicates.or(this, p);
        }
    }

}
