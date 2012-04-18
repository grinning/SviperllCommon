/*
 * Copyright (C) 2011 Victor Nazarov <asviraspossible@gmail.com>
 */

package org.sviperll.collection.elist;

import java.util.List;

/**
 *
 * @author vir
 */
public class ELists {
    public static <T> EList<T> wrap(List<T> list) {
        return ListEList.createInstance(list);
    }

    private ELists() {}
}
