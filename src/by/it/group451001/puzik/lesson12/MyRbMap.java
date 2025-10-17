package by.it.group451001.puzik.lesson12;

import java.util.*;

public class MyRbMap implements SortedMap<Integer, String> {

    private static class Entry {
        Integer key; String value;
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

    @Override public Comparator<? super Integer> comparator() { return null; }
    @Override public SortedMap<Integer, String> subMap(Integer fromKey, Integer toKey) { throw new UnsupportedOperationException(); }
    @Override public SortedMap<Integer, String> headMap(Integer toKey) { int hi=indexForInsert(toKey); MyRbMap m=new MyRbMap(); for(int i=0;i<hi;i++) m.put(data[i].key,data[i].value); return m; }
    @Override public SortedMap<Integer, String> tailMap(Integer fromKey) { int lo=indexForInsert(fromKey); MyRbMap m=new MyRbMap(); for(int i=lo;i<size;i++) m.put(data[i].key,data[i].value); return m; }
    @Override public Integer firstKey() { if(size==0) throw new NoSuchElementException(); return data[0].key; }
    @Override public Integer lastKey() { if(size==0) throw new NoSuchElementException(); return data[size-1].key; }

    @Override public boolean containsKey(Object key) { return index((Integer) key) >= 0; }
    @Override public boolean containsValue(Object value) { for(int i=0;i<size;i++) if(Objects.equals(data[i].value,value)) return true; return false; }
    @Override public String get(Object key) { int i=index((Integer) key); return i>=0? data[i].value:null; }

    @Override
    public String put(Integer key, String value) {
        int i = index(key);
        if (i >= 0) { String old=data[i].value; data[i].value=value; return old; }
        int pos = -i - 1;
        ensure(size+1);
        System.arraycopy(data, pos, data, pos+1, size-pos);
        data[pos] = new Entry(key, value);
        size++;
        return null;
    }

    @Override
    public String remove(Object key) {
        int i = index((Integer) key);
        if (i < 0) return null;
        String old = data[i].value;
        System.arraycopy(data, i+1, data, i, size-i-1);
        data[--size] = null;
        return old;
    }

    @Override public void putAll(Map<? extends Integer, ? extends String> m) { for (Map.Entry<? extends Integer, ? extends String> e : m.entrySet()) put(e.getKey(), e.getValue()); }
    @Override public Set<Integer> keySet() { throw new UnsupportedOperationException(); }
    @Override public Collection<String> values() { throw new UnsupportedOperationException(); }
    @Override public Set<Map.Entry<Integer, String>> entrySet() { throw new UnsupportedOperationException(); }

    // helpers
    private int index(Integer key) {
        int lo=0, hi=size-1;
        while(lo<=hi){ int mid=(lo+hi)>>>1; int cmp=Integer.compare(data[mid].key,key); if(cmp<0) lo=mid+1; else if(cmp>0) hi=mid-1; else return mid; }
        return -(lo+1);
    }
    private int indexForInsert(Integer key){ int idx=index(key); return idx>=0? idx: -idx-1; }
    private void ensure(int min){ if(data.length<min) data=Arrays.copyOf(data, Math.max(min, data.length*2)); }
}


