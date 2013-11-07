package ru.fizteh.fivt.students.kocurba.multifilehashmap;

import ru.fizteh.fivt.storage.strings.TableProviderFactory;
import ru.fizteh.fivt.students.kocurba.filemap.command.State;
import ru.fizteh.fivt.students.kocurba.multifilehashmap.command.CommandList;
import ru.fizteh.fivt.students.kocurba.shell.Shell;
import ru.fizteh.fivt.students.kocurba.shell.ShellState;
import ru.fizteh.fivt.students.kocurba.storage.strings.FileTableProviderFactory;

/**
 * Task 04 - MultiFileHashMap
 * 
 * @author Alina Kocurba
 * 
 */
public class Main {

	public static void main(String[] args) {
		TableProviderFactory tableProviderFactory = new FileTableProviderFactory();
		ShellState<State> state = new ShellState<State>(
				new State(tableProviderFactory.create(System
						.getProperty("fizteh.db.dir")), null));
		Shell<State> shell = new Shell<State>(state);

		if (0 != args.length) {
			shell.batchMode(args, CommandList.getCommands());
		} else {
			shell.interactiveMode(CommandList.getCommands());
		}
		System.exit(0);
	}

}
