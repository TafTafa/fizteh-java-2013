package ru.fizteh.fivt.students.kocurba.storage.strings.test;

import org.junit.Before;
import org.junit.Test;

import ru.fizteh.fivt.storage.strings.TableProviderFactory;
import ru.fizteh.fivt.students.kocurba.storage.strings.FileTableProviderFactory;

/**
 * Task 05 - JUnit
 * 
 * JUnit tests for {@link FileTableProviderFactory} class
 * 
 * @author Alina Kocurba
 * 
 */
public class FileTableProviderFactoryTest {

    private TableProviderFactory factory;

    @Before
    public void initFactory() {
        factory = new FileTableProviderFactory();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNullDirectoryName() {
        factory.create(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNonExistingDirectoryName() {
        factory.create("/non-existing");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncorrectSymbolsInDirectoryName() {
        factory.create("/\\");
    }

}
