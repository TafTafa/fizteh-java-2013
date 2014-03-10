package ru.fizteh.fivt.students.kocurba.storeable;

import ru.fizteh.fivt.storage.structured.ColumnFormatException;
import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.storage.structured.Table;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StoreableTable  implements Table {

    private String name;
    private StoreableTableProvider provider;
    private ArrayList<Class<?>> types;
    private HashMap<String, Storeable> storage;
    private HashMap<String, Storeable> changes;
    private boolean[][] uses;
    private long byteSize;
    private int count;

    public StoreableTable(String tableName, List<Class<?>> columnTypes, StoreableTableProvider parent) {
        name = tableName;
        provider = parent;
        storage = new HashMap<String, Storeable>();
        changes = new HashMap<String, Storeable>();
        byteSize = 0;
        uses = new boolean[16][16];
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                uses[i][j] = false;
            }
        }
        count = storage.size();
        types = new ArrayList<Class<?>>(columnTypes);
    }

    private boolean tryToGetUnnecessaryColumn(Storeable value) {
        try {
            value.getColumnAt(types.size());
        } catch (IndexOutOfBoundsException e) {
            return true;
        }
        return false;
    }
    @Override
    public Storeable put(String key, Storeable value) throws ColumnFormatException {

            if (key == null || value == null) {
                throw new IllegalArgumentException("Incorrect key/value to put.");
            }
            if (key.trim().isEmpty() || key.matches("(.+\\s+.+)+")) {
                throw new IllegalArgumentException("Incorrect key to put.");
            }
            try {
                for (int i = 0; i < types.size(); ++i) {
                    if (value.getColumnAt(i) != null && !types.get(i).equals(value.getColumnAt(i).getClass())) {
                        throw new ColumnFormatException("Incorrect value to put.");
                    }
                }
            } catch (IndexOutOfBoundsException e) {
                throw new ColumnFormatException("Incorrect value to put.");
            }
            if (!tryToGetUnnecessaryColumn(value)) {
                throw new ColumnFormatException("Incorrect value to put.");
            }
            if ((!changes.containsKey(key) && !storage.containsKey(key))
                    || (changes.containsKey(key) && changes.get(key) == null)) {
                ++count;
            }

            int hashCode = Math.abs(key.hashCode());
            int nDirectory = hashCode % 16;
            int nFile = hashCode / 16 % 16 ;

            uses[nDirectory][nFile] = true;
            Storeable v = get(key);
            String copyOfKey = "".concat(key);
            Storeable copyOfValue = provider.createFor(this);
            for (int i = 0; i < types.size(); ++i) {
                copyOfValue.setColumnAt(i, value.getColumnAt(i));
            }
            changes.put(copyOfKey, copyOfValue);
            if (value.equals(storage.get(key))) {
                changes.remove(key);
            }
            return v;
    }

    @Override
    public Storeable remove(String key) {
            if (key == null) {
                throw new IllegalArgumentException("Incorrect key to remove.");
            }
            if (key.trim().isEmpty() || key.matches("(.+\\s+.+)+")) {
                throw new IllegalArgumentException("Incorrect key to remove.");
            }
            if (changes.get(key) != null || (!changes.containsKey(key) && storage.get(key) != null)) {
                --count;
            }

            int hashCode = Math.abs(key.hashCode());
            int nDirectory = hashCode % 16;
            int nFile = hashCode / 16 % 16 ;

            uses[nDirectory][nFile] = true;
            Storeable v = get(key);
            changes.put(key, null);
            if (storage.get(key) == null) {
                changes.remove(key);
            }
            return v;
    }

    @Override
    public int size() {
        return count;
    }

    @Override
    public int commit() throws IOException {
            for (String key : changes.keySet()) {
                if (changes.get(key) == null) {
                    storage.remove(key);
                } else {
                    storage.put(key, changes.get(key));
                }
            }
            int n = changes.size();
            changes.clear();
            return n;
    }

    @Override
    public int rollback() {
        int n = changes.size();
        changes.clear();
        count = storage.size();
        return n;
    }

    @Override
    public int getColumnsCount() {
        return types.size();
    }

    @Override
    public Class<?> getColumnType(int columnIndex) throws IndexOutOfBoundsException {
        if (columnIndex < 0 || columnIndex > types.size()) {
            throw new IndexOutOfBoundsException("Incorrect column number.");
        }
        return types.get(columnIndex);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Storeable get(String key) {
        if (key == null) {
            throw new IllegalArgumentException("Incorrect key to get.");
        }
        if (key.trim().isEmpty() || key.matches("(.+\\s+.+)+")) {
            throw new IllegalArgumentException("Incorrect key to get.");
        }
        if (changes.containsKey(key)) {
            return changes.get(key);
        }
        return storage.get(key);
    }
}