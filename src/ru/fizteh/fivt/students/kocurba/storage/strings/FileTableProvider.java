package ru.fizteh.fivt.students.kocurba.storage.strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import ru.fizteh.fivt.storage.strings.Table;
import ru.fizteh.fivt.storage.strings.TableProvider;

public class FileTableProvider implements TableProvider {

	private String dir;

	public FileTableProvider(String dir) {
		this.dir = dir + "/";
		if (!Files.isDirectory(Paths.get(this.dir))) {
			throw new IllegalArgumentException();
		}
	}

	public String getFileName(String tableName) {
		String filename = this.dir;
		if (!Files.isDirectory(Paths.get(filename))) {
			return null;
		}
		filename += String.valueOf(tableName.hashCode() % 16) + ".dir/";
		if (Files.exists(Paths.get(filename))
				&& !Files.isDirectory(Paths.get(filename))) {
			return null;
		}
		if (!Files.exists(Paths.get(filename))) {
			try {
				Files.createDirectory(Paths.get(filename));
			} catch (IOException e) {
				return null;
			}
		}
		filename += String.valueOf(tableName.hashCode() / 16 % 16) + ".dat";
		if (Files.isDirectory(Paths.get(filename))) {
			return null;
		}
		return filename;
	}

	@Override
	public Table getTable(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		String filename = getFileName(name);
		if (filename == null) {
			throw new IllegalArgumentException();
		}
		if (!Files.exists(Paths.get(filename))) {
			return null;
		}
		return new FileTable(name, filename);
	}

	@Override
	public Table createTable(String name) {
		if (name == null || name.isEmpty()) {
			throw new IllegalArgumentException();
		}
		String filename = getFileName(name);
		if (filename == null) {
			throw new IllegalArgumentException();
		}
		if (Files.exists(Paths.get(filename))) {
			return null;
		}
		return new FileTable(name, filename);
	}

	@Override
	public void removeTable(String name) {
		if (name == null) {
			throw new IllegalArgumentException();
		}
		try {
			String filename = getFileName(name);
			if (filename == null || !Files.exists(Paths.get(filename))) {
				throw new IllegalArgumentException();
			}
			Files.delete(Paths.get(filename));
		} catch (IOException e) {
		}
	}

}
