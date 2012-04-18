/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection;

import java.util.Map;
import org.sviperll.collection.Table.TableEntry;
import org.sviperll.collection.ecollection.ECollection;
import org.sviperll.collection.eiterable.EIterables;
import org.sviperll.collection.elist.EList;
import org.sviperll.collection.emap.EMap;
import org.sviperll.collection.emap.EMaps;
import org.sviperll.collection.eset.EListESet;
import org.sviperll.collection.eset.ESet;
import org.sviperll.Comparables;
import org.sviperll.CompareException.Greater;
import org.sviperll.CompareException.Less;
import org.sviperll.Mapper;

/**
 *
 * @author vir
 */
public class TreeTable<K1 extends Comparable<? super K1>, K2 extends Comparable<? super K2>, V> implements Table<K1, K2, V> {
    private final Mapper<EMap<K2, V>, ECollection<V>> valuesMapper = EMaps.valuesMapper();
    private final EMap<K1, EMap<K2, V>> k12Map = Collections.newTreeMap();
    private final EMap<K2, EMap<K1, V>> k21Map = Collections.newTreeMap();

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean containsCol(K1 k1) {
        return k12Map.containsKey(k1);
    }

    @Override
    public boolean containsRow(K2 k2) {
        return k21Map.containsKey(k2);
    }

    @Override
    public EMap<K2, V> getCol(K1 k1) {
        return k12Map.get(k1);
    }

    @Override
    public EMap<K1, V> getRow(K2 k2) {
        return k21Map.get(k2);
    }

    @Override
    public V get(K1 k1, K2 k2) {
        return k12Map.get(k1).get(k2);
    }

    @Override
    public V put(K1 k1, K2 k2, V value) {
        EMap<K2, V> m1 = k12Map.get(k1);
        if (m1 == null) {
            m1 = Collections.newTreeMap();
            k12Map.put(k1, m1);
        }
        if (!m1.containsKey(k2))
            size++;
        m1.put(k2, value);

        EMap<K1, V> m2 = k21Map.get(k2);
        if (m2 == null) {
            m2 = Collections.newTreeMap();
            k21Map.put(k2, m2);
        }

        /*
         * We do not need to do this:
         *
         *      if (!m2.containsKey(k1))
         *          size++;
         *
         * since we already increased size value above
         */

        return m2.put(k1, value);
    }

    @Override
    public EMap<K2, V> removeCol(K1 k1) {
        EMap<K2, V> m1 = k12Map.remove(k1);
        if (m1 != null) {
            for (K2 k2: m1.keySet()) {
                EMap<K1, V> m2 = k21Map.get(k2);
                if (m2 != null) {
                    if (m2.containsKey(k1))
                        size--;
                    m2.remove(k1);
                }
            }
        }
        return m1;
    }

    @Override
    public EMap<K1, V> removeRow(K2 k2) {
        EMap<K1, V> m1 = k21Map.remove(k2);
        if (m1 != null) {
            for (K1 k1: m1.keySet()) {
                Map<K2, V> m2 = k12Map.get(k1);
                if (m2 != null) {
                    if (m2.containsKey(k2))
                        size--;
                    m2.remove(k2);
                }
            }
        }
        return m1;
    }

    @Override
    public V remove(K1 k1, K2 k2) {
        EMap<K2, V> m1 = k12Map.get(k1);
        if (m1 != null) {
            if (m1.containsKey(k2))
                size--;
            m1.remove(k2);
        }
        EMap<K1, V> m2 = k21Map.get(k2);
        if (m2 != null) {
            /*
             * We do not need to do this:
             *     if (m1.containsKey(k2))
             *         size--;
             * since we already decreased size above
             */
            return m2.remove(k1);
        } else
            return null;
    }

    @Override
    public void clear() {
        size = 0;
        k12Map.clear();
        k21Map.clear();
    }

    @Override
    public ESet<K1> colSet() {
        return k12Map.keySet();
    }

    @Override
    public ESet<K2> rowSet() {
        return k21Map.keySet();
    }

    @Override
    public ECollection<V> values() {
        return Collections.newArrayList(EIterables.concat(k12Map.values().map(valuesMapper)));
    }

    @Override
    public ESet<TableEntry<K1, K2, V>> entrySet() {
        EList<TableEntry<K1, K2, V>> list = Collections.newArrayList();
        for (Map.Entry<K1, EMap<K2, V>> col: k12Map.entrySet()) {
            K1 colID = col.getKey();
            for (Map.Entry<K2, V> row: col.getValue().entrySet()) {
                K2 rowID = row.getKey();
                V value = row.getValue();
                list.add(new TreeTableEntry<K1, K2, V>(colID, rowID, value));
            }
        }
        return new EListESet<TableEntry<K1, K2, V>>(list);
    }

    @Override
    public void putAll(Table<K1, K2, ? extends V> src) {
        for (TableEntry<K1, K2, ? extends V> e: src.entrySet())
            put(e.getCol(), e.getRow(), e.getValue());
    }

    private static class TreeTableEntry<C extends Comparable<? super C>, R extends Comparable<? super R>, V> implements TableEntry<C, R, V> {
        private final C col;
        private final R row;
        private final V value;
        public TreeTableEntry(C col, R row, V value) {
            this.col = col;
            this.row = row;
            this.value = value;
        }

        @Override
        public C getCol() {
            return col;
        }

        @Override
        public R getRow() {
            return row;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public int compareTo(TableEntry<C, R, V> that) {
            try {
                Comparables.compareTo(this.getCol(), that.getCol());
                Comparables.compareTo(this.getRow(), that.getRow());
                Comparables.compareTo(this.getCol(), that.getCol());
                return 0;
            } catch (Less ex) {
                return -1;
            } catch (Greater ex) {
                return 1;
            }
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object thatObject) {
            if (thatObject instanceof TableEntry) {
                TableEntry<C, R, V> that = (TableEntry<C, R, V>) thatObject;
                return this == that || this.compareTo(that) == 0;
            } else
                throw new ClassCastException();
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 23 * hash + (this.col != null ? this.col.hashCode() : 0);
            hash = 23 * hash + (this.row != null ? this.row.hashCode() : 0);
            hash = 23 * hash + (this.value != null ? this.value.hashCode() : 0);
            return hash;
        }
    }
}
