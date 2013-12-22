package ru.fizteh.fivt.students.kocurba.storage.strings;


import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.StandardCharsets;
import java.io.*;
import java.util.Set;

import ru.fizteh.fivt.storage.strings.Table;
import ru.fizteh.fivt.students.kocurba.shell.Shell;
import ru.fizteh.fivt.students.kocurba.shell.StateWrap;
import ru.fizteh.fivt.students.kocurba.shell.command.Command;
import ru.fizteh.fivt.students.kocurba.shell.command.RmCommand;

public class NewFileTable implements Table {

    private String name;
    private String filename;
    private Map<String, String> data;
    private int commitSize;

    public NewFileTable(String name, String filename) {
        this.name = name;
        if (filename == null) {
            //throw new IllegalArgumentException();
            System.exit(1);
        }
        if (!Files.exists(Paths.get(filename))) {
            try {
                Files.createDirectory(Paths.get(filename));
            } catch (IOException e) {
                //
            }
        }
        this.filename = filename;
        rollback();
    }

    @Override
    public String put(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException();
        }
        ++this.commitSize;
        return data.put(key, value);
    }

    @Override
    public String get(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        return data.get(key);
    }

    @Override
    public String remove(String key) {
        if (key == null) {
            throw new IllegalArgumentException();
        }
        ++this.commitSize;
        return data.remove(key);
    }

    public int rollback() {

        this.data = new HashMap<String, String>();
        for (int i = 0; i < 16 ; ++i) {
            File dir = new File(new File(System.getProperty("fizteh.db.dir"), this.name), new String(i + ".dir"));

            for (int j = 0; j < 16 ; ++j) {
                File file = new File(dir, new String(j + ".dat"));
                   if (file.exists()) {
                DataInputStream inStream;
                try {
                    inStream = new DataInputStream(new FileInputStream(file));
                } catch (IOException e) {
                   // e.printStackTrace();
                    int result = commitSize;
                    commitSize = 0;
                    return result;
                }

                try {
                    for (; ; ) {
                        int keyLength = inStream.readInt();
                        int valueLength = inStream.readInt();

                        byte[] rawKey = new byte[keyLength];
                        for (int q = 0; q < keyLength; ++q) {
                            rawKey[q] = inStream.readByte();
                        }

                        byte[] rawValue = new byte[valueLength];
                        for (int q = 0; q < valueLength; ++q) {
                            rawValue[q] = inStream.readByte();
                        }

                        data.put(new String(rawKey), new String(rawValue));
                    }



                }   catch (EOFException e)  {
                    //do nothing
                }   catch (IOException e2)  {
                    //e2.printStackTrace();
                }  finally {
                    try {
                        inStream.close();
                    } catch (IOException e) {
                    //
                    }
                }
                }
            }
        }
        int result = commitSize;
        commitSize = 0;
        return result;

    }

    void writeMap(File file, Map<String, String> map) {
        try {
            DataOutputStream outStream = new DataOutputStream(new FileOutputStream(file.getCanonicalFile()));

            for (Map.Entry<String, String> entry : map.entrySet()) {
                int length;
                length =  entry.getKey().getBytes(StandardCharsets.UTF_8).length;
                outStream.writeInt(length);

                length = entry.getValue().getBytes(StandardCharsets.UTF_8).length;
                outStream.writeInt(length);

                outStream.write(entry.getKey().getBytes(StandardCharsets.UTF_8));
                outStream.write(entry.getValue().getBytes(StandardCharsets.UTF_8));
            }
            outStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public int commit() {
          try {
              File workingDir = new File(System.getProperty("fizteh.db.dir"), name);
              StateWrap<File> state = new StateWrap<File>(workingDir.getCanonicalFile());
              String[] listFile = workingDir.list();
              if (listFile != null) {
              for (String str : listFile) {
                    String[] args = {"asd", str};
                    Command<File> rm = new RmCommand();
                    rm.executeCommand(state, args);
              }
              }
          } catch (IOException e) {
              //
          }

        Set<String> keys = data.keySet();
        try {
            if (!keys.isEmpty()) {
                for (int i = 0; i < 16; ++i) {
                    File dir = new File(new File(new File(System.getProperty("fizteh.db.dir")), this.name),
                            new String(i + ".dir"));
                    for (int j = 0; j < 16; ++j) {
                        Map<String, String> map = new HashMap<String, String>();
                        //DataTable keysToFile = new DataTable();
                        File file = new File(dir, new String(j + ".dat"));
                        for (String key : keys) {

                            int hashcode = Math.abs(key.hashCode());
                            int ndirectory = hashcode % 16;
                            int nfile = hashcode / 16 % 16 ;

                            if ((ndirectory == i) && (nfile == j)) {
                                if (!dir.getCanonicalFile().exists()) {

                                    dir.getCanonicalFile().mkdir();
                                }
                                if (!file.getCanonicalFile().exists()) {
                                    file.getCanonicalFile().createNewFile();
                                }
                                map.put(key, data.get(key));

                            }
                        }

                        if (!map.isEmpty()) {
                            writeMap(file, map);
                        }

                    }

                    if (dir.getCanonicalFile().listFiles() == null) {
                        dir.delete();
                    }
                }
            }
        } catch (Exception e) {
                e.printStackTrace();
        }

        int result = commitSize;
        commitSize = 0;
        return result;
    }



    @Override
    public String getName() {
        return this.name;
    }

    public String getFilename() {
        return this.filename;
    }
    @Override
    public int size() {
        return this.commitSize;
    }

}
