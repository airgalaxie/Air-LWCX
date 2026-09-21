/*
 * Copyright 2011 Tyler Blair. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are
 * permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ''AS IS'' AND ANY EXPRESS OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 * FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 * ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 * The views and conclusions contained in the software and documentation are those of the
 * authors and contributors and should not be interpreted as representing official policies,
 * either expressed or implied, of anybody else.
 */
package com.griefcraft.cache;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MethodCounter {

    /**
     * A map of the counts
     */
    private final Map<String, Integer> counts = new HashMap<>();

    /**
     * Increment a method in the counts
     *
     * @param method
     */
    public void increment(String method) {
        deltaMethod(method, 1);
    }

    /**
     * Decrement a method in the cache
     *
     * @param method
     */
    public void decrement(String method) {
        deltaMethod(method, -1);
    }

    /**
     * Get the counts for a method
     *
     * @param method
     * @return
     */
    public int get(String method) {
        return counts.containsKey(method) ? counts.get(method) : 0;
    }

    /**
     * Sorts the method counts by the value and returns an unmodifiable map for it
     *
     * @return
     */
    public Map<String, Integer> sortByValue() {
        return Collections.unmodifiableMap(sortByComparator(counts, false));
    }

    /**
     * Handle increment / decrement
     *
     * @param method
     * @param delta
     */
    private void deltaMethod(String method, int delta) {
        if (!counts.containsKey(method)) {
            counts.put(method, delta);
            return;
        }

        counts.put(method, counts.get(method) + delta);
    }

    /**
     * Sort a hashmap by the values
     * <p/>
     * credits: http://stackoverflow.com/questions/8119366/sorting-hashmap-by-values/13913206#13913206
     *
     * @param unsortMap
     * @param order
     * @return
     */
    private static Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap, final boolean order) {
        List<Map.Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2) {
                if (order) {
                    return o1.getValue().compareTo(o2.getValue());
                } else {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Integer> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
