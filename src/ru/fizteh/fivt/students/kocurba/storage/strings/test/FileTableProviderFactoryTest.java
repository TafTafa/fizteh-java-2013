package ru.fizteh.fivt.students.kocurba.storage.strings.test;

import org.junit.*;
import org.junit.rules.TemporaryFolder;
import ru.fizteh.fivt.storage.strings.TableProviderFactory;
import java.io.IOException;
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

    @Rule
    public TemporaryFolder folder = new TemporaryFolder();

    @Test
    public void testCreateNotNull() throws IOException {
        TableProviderFactory factory = new FileTableProviderFactory();
        Assert.assertNotNull(factory.create(folder.newFolder("folder").getCanonicalPath()));
    }
    @Test(expected = IllegalArgumentException.class)
    public void testCreateNull() {
        TableProviderFactory factory = new FileTableProviderFactory();
        factory.create(null);
    }

}
