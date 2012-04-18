/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.eiterator;

import java.util.Iterator;
import java.util.List;
import org.sviperll.Mapper;
import org.sviperll.Mappers;
import org.sviperll.Predicate;
import org.sviperll.Reducer;

/**
 *
 * @author vir
 */
public class MergedEIterator<T> implements EIterator<T> {
    public static <T extends Mergable<T>> EIterator<T> createInstance(List<? extends EIterator<? extends T>> sources) {
        return createInstance(sources, new MergableMerger<T>());
    }
    public static <T> EIterator<T> createInstance(List<? extends EIterator<? extends T>> sources, Merger<T> merger) {
        if (sources.isEmpty())
            throw new IllegalArgumentException("At least one source should be passed");
        else if (sources.size() == 1)
            return sources.get(0).map(Mappers.<T>id());
        else {
            int newSize = sources.size() / 2;
            EIterator<? extends T> firstSource = createInstance(sources.subList(0, newSize), merger);
            EIterator<? extends T> secondSource = createInstance(sources.subList(newSize, sources.size()), merger);
            return new MergedEIterator<T>(merger, firstSource, secondSource);
        }
    }

    private final Merger<T> merger;
    private State<T> state = null;

    private MergedEIterator(Merger<T> merger, EIterator<? extends T> firstIterator, EIterator<? extends T> secondIterator) {
        this.merger = merger;
        if (firstIterator.hasNext()) {
            State<T> first = new PreReadState(firstIterator.next(), firstIterator);
            if (secondIterator.hasNext()) {
                State<T> second = new PreReadState(secondIterator.next(), secondIterator);
                state = new MergeState(first, second);
            } else {
                state = first;
            }
        } else {
            if (secondIterator.hasNext())
                state = new PreReadState(secondIterator.next(), secondIterator);
            else
                state = null;
        }
    }

    @Override
    public boolean hasNext() {
        return state != null;
    }

    @Override
    public T next() {
        T res = state.current();
        state.move();
        return res;
    }

    @Override
    public void remove() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public <U> EIterator<U> map(Mapper<? super T, U> mapper) {
        return EIterators.map(this, mapper);
    }

    @Override
    public EIterator<T> filter(Predicate<? super T> predicate) {
        return EIterators.filter(this, predicate);
    }

    @Override
    public EIterator<T> truncate(int count) {
        return EIterators.truncate(this, count);
    }

    @Override
    public <U> U reduce(U base, Reducer<U, ? super T> reducer) {
        return EIterators.reduce(this, base, reducer);
    }

    private interface State<T> {
        T current();
        void move();
    }

    private class MergeState implements State<T> {
        final State<T> left;
        final State<T> right;

        MergeState(State<T> left, State<T> right) {
            this.left = left;
            this.right = right;
        }

        @Override
        public void move() {
            Move move = new Move();
            int cmp = merger.compare(left.current(), right.current());
            if (cmp < 0) {
                move.leftMove();
            } else if (cmp > 0) {
                move.rightMove();
            } else {
                move.leftMove();
                move.correctState();
                move.rightMove();
            }
            move.correctState();
        }

        @Override
        public T current() {
            int cmp = merger.compare(left.current(), right.current());
            if (cmp < 0) {
                return left.current();
            } else if (cmp > 0) {
                return right.current();
            } else {
                return merger.merge(left.current(), right.current());
            }
        }

        private class Move {
            boolean isLeftEof = false;
            boolean isRightEof = false;

            void leftMove() {
                left.move();
                isLeftEof = (state == null);
            }

            void rightMove() {
                right.move();
                isRightEof = (state == null);
            }

            void correctState() {
                if (!isLeftEof && isRightEof)
                    state = left;
                if (!isRightEof && isLeftEof)
                    state = right;
            }
        }
    }

    private class PreReadState implements State<T> {
        final Iterator<? extends T> iterator;
        T value;

        PreReadState(T value, Iterator<? extends T> iterator) {
            this.value = value;
            this.iterator = iterator;
        }

        @Override
        public T current() {
            return value;
        }

        @Override
        public void move() {
            if (iterator.hasNext())
                value = iterator.next();
            else
                state = null;
        }
    }
}
