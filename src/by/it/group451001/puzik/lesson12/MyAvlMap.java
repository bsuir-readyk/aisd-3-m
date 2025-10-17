package by.it.group451001.puzik.lesson12;

import java.util.*;

public class MyAvlMap implements Map<Integer, String> {

    private static class Entry {
        Integer key;
        String value;
        Entry(Integer k, String v) { key = k; value = v; }
    }

    private Entry[] data = new Entry[8];
    private int size = 0;

    @Override
    public String toString() {
        StringBuilder out = new StringBuilder();
        out.append('{');
        for (int i = 0; i < size; i++) {
            if (i > 0) out.append(", ");
            out.append(data[i].key).append("=").append(data[i].value);
        }
        out.append('}');
        return out.toString();
    }

    @Override public int size() { return size; }
    @Override public void clear() { Arrays.fill(data, 0, size, null); size = 0; }
    @Override public boolean isEmpty() { return size == 0; }

    @Override
    public String put(Integer key, String value) {
        int i = findIndex(key);
        if (i >= 0) { String old = data[i].value; data[i].value = value; return old; }
        int pos = -i - 1;
        ensureCapacity(size + 1);
        System.arraycopy(data, pos, data, pos + 1, size - pos);
        data[pos] = new Entry(key, value);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        int i = findIndex((Integer) key);
        if (i < 0) return null;
        String old = data[i].value;
        int numMoved = size - i - 1;
        if (numMoved > 0) System.arraycopy(data, i + 1, data, i, numMoved);
        data[--size] = null;
        return old;
    }

    @Override
    public String get(Object key) {
        int i = findIndex((Integer) key);
        return i >= 0 ? data[i].value : null;
    }

    @Override
    public boolean containsKey(Object key) { return findIndex((Integer) key) >= 0; }

    // ---- helpers ----
    private int findIndex(Integer key) {
        int lo = 0, hi = size - 1;
        while (lo <= hi) {
            int mid = (lo + hi) >>> 1;
            int mk = data[mid].key;
            int cmp = Integer.compare(mk, key);
            if (cmp < 0) lo = mid + 1; else if (cmp > 0) hi = mid - 1; else return mid;
        }
        return -(lo + 1);
    }

    private void ensureCapacity(int min) {
        if (data.length < min) data = Arrays.copyOf(data, Math.max(min, data.length * 2));
    }

    // ---- boilerplate unsupported ----
    @Override public boolean containsValue(Object value) { throw new UnsupportedOperationException(); }
    @Override public void putAll(Map<? extends Integer, ? extends String> m) { for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) put(e.getKey(), e.getValue()); }
    @Override public Set<Integer> keySet() { throw new UnsupportedOperationException(); }
    @Override public Collection<String> values() { throw new UnsupportedOperationException(); }
    @Override public Set<Map.Entry<Integer, String>> entrySet() { throw new UnsupportedOperationException(); }
}


