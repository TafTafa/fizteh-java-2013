package ru.fizteh.fivt.students.kocurba.filemap.command;

import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.ShellState;
import ru.fizteh.fivt.students.kocurba.shell.command.Command;

public class SizeCommand implements Command<State> {

	@Override
	public int getArgCount() {
		return 0;
	}

	@Override
	public String getCommandName() {
		return "size";
	}

	@Override
	public void executeCommand(ShellState<State> state, String[] arguments)
			throws IOException {
		if (state.getState().getCurrentTable() == null) {
			System.err.println("no table");
			return;
		}
		System.out.println(state.getState().getCurrentTable().size());
	}

}