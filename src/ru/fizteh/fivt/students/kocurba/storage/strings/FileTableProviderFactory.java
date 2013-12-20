package ru.fizteh.fivt.students.kocurba.storage.strings;

import ru.fizteh.fivt.storage.strings.TableProvider;
import ru.fizteh.fivt.storage.strings.TableProviderFactory;

public class FileTableProviderFactory implements TableProviderFactory {

    @Override
    public TableProvider create(String dir) {
        if (dir == null) {
           // throw new IllegalArgumentException();
            System.exit(1);
        }
        return new FileTableProvider(dir);
    }

}
