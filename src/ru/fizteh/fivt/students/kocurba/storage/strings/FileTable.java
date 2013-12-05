package ru.fizteh.fivt.students.kocurba.storage.strings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

import ru.fizteh.fivt.storage.strings.Table;

public class FileTable implements Table {

	private String name;
	private String filename;
	private Map<String, String> data;
	private int commitSize;

	public FileTable(String name, String filename) {
		this.name = name;
		if (filename == null) {
			throw new IllegalArgumentException();
		}
		if (!Files.exists(Paths.get(filename))) {
			try {
				Files.createFile(Paths.get(filename));
			} catch (IOException e) {
				throw new IllegalArgumentException();
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

	@Override
	public int rollback() {
		byte[] rawData;
		try {
			rawData = Files.readAllBytes(Paths.get(this.filename));
		} catch (IOException e) {
			e.printStackTrace();
			int result = commitSize;
			commitSize = 0;
			return result;
		}
		this.data = new HashMap<String, String>();

		for (int i = 0; i < rawData.length;) {
			int keyLength = rawData[i++];
			byte[] rawKey = new byte[keyLength];
			for (int j = 0; j < keyLength; ++j) {
				rawKey[j] = rawData[i++];
			}

			int valueLength = rawData[i++];
			byte[] rawValue = new byte[valueLength];
			for (int j = 0; j < valueLength; ++j) {
				rawValue[j] = rawData[i++];
			}

			data.put(new String(rawKey), new String(rawValue));
		}
		int result = commitSize;
		commitSize = 0;
		return result;
	}

	@Override
	public int commit() {
		try {
			Path path = Paths.get(this.filename);
			Files.write(path, new byte[0], StandardOpenOption.CREATE);
			for (Map.Entry<String, String> entry : this.data.entrySet()) {
				byte[] length = new byte[1];
				length[0] = (byte) entry.getKey().length();
				Files.write(path, length, StandardOpenOption.APPEND);
				Files.write(path, entry.getKey().getBytes(), StandardOpenOption.APPEND);

				length[0] = (byte) entry.getValue().length();
				Files.write(path, length, StandardOpenOption.APPEND);
				Files.write(path, entry.getValue().getBytes(), StandardOpenOption.APPEND);
			}
		} catch (IOException exception) {
		}
		int result = commitSize;
		commitSize = 0;
		return result;
	}

	@Override
	public String getName() {
		return this.name;
	}

	@Override
	public int size() {
		return this.commitSize;
	}

}