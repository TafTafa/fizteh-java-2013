package ru.fizteh.fivt.students.kocurba.filemap;

import java.io.IOException;

import ru.fizteh.fivt.storage.strings.Table;
import ru.fizteh.fivt.students.kocurba.filemap.command.CommandList;
import ru.fizteh.fivt.students.kocurba.filemap.command.State;
import ru.fizteh.fivt.students.kocurba.shell.Shell;
import ru.fizteh.fivt.students.kocurba.shell.ShellState;
import ru.fizteh.fivt.students.kocurba.storage.strings.FileTable;

/**
 * Task 03 - FileMap
 * 
 * @author Alina Kocurba
 * 
 */
public class Main {

	public static void main(String[] args) throws IOException {
		Table dataBase = new FileTable("db",
				System.getProperty("fizteh.db.dir") + "/db.dat");
		ShellState<State> state = new ShellState<State>(new State(null,
				dataBase));
		Shell<State> shell = new Shell<State>(state);

		if (0 != args.length) {
			shell.batchMode(args, CommandList.getCommands());
		} else {
			shell.interactiveMode(CommandList.getCommands());
		}
		System.exit(0);
	}

}
