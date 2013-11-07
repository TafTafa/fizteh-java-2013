package ru.fizteh.fivt.students.kocurba.multifilehashmap.command;

import java.io.IOException;

import ru.fizteh.fivt.storage.strings.Table;
import ru.fizteh.fivt.students.kocurba.filemap.command.State;
import ru.fizteh.fivt.students.kocurba.shell.ShellState;
import ru.fizteh.fivt.students.kocurba.shell.command.Command;

public class UseCommand implements Command<State> {

	@Override
	public int getArgCount() {
		return 1;
	}

	@Override
	public String getCommandName() {
		return "use";
	}

	@Override
	public void executeCommand(ShellState<State> state, String[] arguments)
			throws IOException {
		if (state.getState().getCurrentTable() != null
				&& state.getState().getCurrentTable().size() > 0) {
			System.err.println(state.getState().getCurrentTable().size()
					+ " unsaved changes");
			return;
		}
		Table newTable = state.getState().getTableProvider()
				.getTable(arguments[1]);
		if (newTable == null) {
			System.err.println(arguments[1] + " not exists");
			return;
		}
		state.getState().setCurrentTable(newTable);
		System.out.println("using " + arguments[1]);
	}
}
