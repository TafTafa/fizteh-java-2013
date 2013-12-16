package ru.fizteh.fivt.students.kocurba.filemap.command;

import ru.fizteh.fivt.storage.strings.Table;
import ru.fizteh.fivt.storage.strings.TableProvider;

public class State {

    private Table currentTable;
    private TableProvider tableProvider;

    public State(TableProvider tableProvider, Table currentTable) {
        this.tableProvider = tableProvider;
        this.currentTable = currentTable;
    }

    public State(TableProvider tableProvider) {
        this(tableProvider, null);
    }

    public Table getCurrentTable() {
        return this.currentTable;
    }

    public TableProvider getTableProvider() {
        return this.tableProvider;
    }

    public void setCurrentTable(Table newTable) {
        this.currentTable = newTable;
    }

}
