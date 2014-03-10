package ru.fizteh.fivt.students.kocurba.storeable;

import ru.fizteh.fivt.storage.structured.ColumnFormatException;
import ru.fizteh.fivt.storage.structured.Storeable;
import ru.fizteh.fivt.storage.structured.Table;
import ru.fizteh.fivt.storage.structured.TableProvider;


import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public class StoreableTableProvider implements TableProvider {

    @Override
    public Table getTable(String name) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Table createTable(String name, List<Class<?>> columnTypes) throws IOException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void removeTable(String name) throws IOException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Storeable deserialize(Table table, String value) throws ParseException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String serialize(Table table, Storeable value) throws ColumnFormatException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Storeable createFor(Table table) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Storeable createFor(Table table, List<?> values) throws ColumnFormatException, IndexOutOfBoundsException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
