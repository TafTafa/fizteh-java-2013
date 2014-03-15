package ru.fizteh.fivt.students.kocurba.storeable;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class StoreableState {
    public Map<String, String> state;

    private HashMap<String, String> diff = new HashMap<String, String>();

    private HashSet<String> deleted = new HashSet<String>();

    public StoreableState(StoreableTable table) {
        state = new HashMap<>();
    }

    public String put(String key, String value) {
        String result = null;

        if (diff.containsKey(key)) {
            result = diff.get(key);
        } else {
            if (state.containsKey(key)) {
                result = state.get(key);
            }
        }

        if (deleted.contains(key)) {
            deleted.remove(key);
            result = null;
        }

        diff.put(key, value);

        return result;
    }

    public String get(String key) {
        if (deleted.contains(key)) {
            return null;
        }

        if (diff.containsKey(key)) {
            return diff.get(key);
        }


        if (state.containsKey(key)) {
            return state.get(key);
        }

        return null;
    }

    public String remove(String key) {
        if (deleted.contains(key)) {
            return null;
        }
        String result = null;

        if (diff.containsKey(key)) {
            result = diff.get(key);
            diff.remove(key);
            deleted.add(key);
            return result;
        }

        if (state.containsKey(key)) {
            result = state.get(key);
            deleted.add(key);
        }

        return result;
    }

    public void commit() {
        deletedSame();

        if (diff.size() == 0 && deleted.size() == 0) {
            return;
        }

        for (Map.Entry<String, String> node : diff.entrySet()) {
            state.put(node.getKey(), node.getValue());
        }

        for (String key : deleted) {
            state.remove(key);
        }


        diff.clear();
        deleted.clear();
    }

    public void rollback() {
        diff.clear();
        deleted.clear();
    }

    public void print(File input) {
        try {
            RandomAccessFile in = new RandomAccessFile(input, "rw");

            in.getChannel().truncate(0);
            for (Map.Entry<String, String> curPair : state.entrySet()) {

                in.writeInt(curPair.getKey().getBytes("UTF-8").length);
                in.writeInt(curPair.getValue().getBytes("UTF-8").length);
                in.write(curPair.getKey().getBytes("UTF-8"));
                in.write(curPair.getValue().getBytes("UTF-8"));

            }
            in.close();
        } catch (IOException e) {
            throw new FileAccessException(e.getMessage());
        }
    }

    public int size() {
        deletedSame();
        int result = diff.size() + state.size() - deleted.size();
        for (String key : diff.keySet()) {
            if (state.containsKey(key)) {
                --result;
            }
        }
        return result;
    }

    void deletedSame() {
        Set<String> newDeleted = new HashSet<>();
        newDeleted.addAll(deleted);

        for (String key : state.keySet()) {
            if (state.get(key).equals(diff.get(key))) {
                diff.remove(key);
            }
            if (newDeleted.contains(key)) {
                newDeleted.remove(key);
            }
        }

        for (String key : deleted) {
            if (diff.containsKey(key)) {
                diff.remove(key);
            }
        }

        for (String key : newDeleted) {
            deleted.remove(key);
        }
    }

    public int getNumbersOfChanges() {
        deletedSame();
        return diff.size() + deleted.size();
    }

    public void read(File input, int ndir, int nfile) {
        try {
            if (input.exists() && input.length() == 0) {
                throw new IOException("Empty File");
            }
            if (input.exists()) {
                RandomAccessFile in = new RandomAccessFile(input, "rw");

                while (in.getFilePointer() < in.length() - 1) {
                    int keyLength = in.readInt();
                    int valueLength = in.readInt();
                    if ((keyLength <= 0) || (valueLength <= 0)) {
                        in.close();
                        throw new IllegalArgumentException("wrong format");
                    }

                    byte[] key;
                    byte[] value;

                    try {
                        key = new byte[keyLength];
                        value = new byte[valueLength];
                    } catch (OutOfMemoryError e) {
                        in.close();
                        throw new IllegalArgumentException("too large key or value");
                    }
                    in.read(key);
                    in.read(value);
                    String keyString = new String(key, "UTF-8");
                    String valueString = new String(value, "UTF-8");
                    int hashCode = keyString.hashCode();
                    hashCode = Math.abs(hashCode);
                    int q = hashCode % 16;
                    int qq = hashCode / 16 % 16;
                    if (q != ndir || qq != nfile) {
                        throw new IOException("wrong key placement");
                    }
                    state.put(keyString, valueString);
                }
                in.close();
            }
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage());
        }
    }

    public void clear() {
        state.clear();
    }

    public boolean isEmpty() {
        return state.isEmpty();
    }
}