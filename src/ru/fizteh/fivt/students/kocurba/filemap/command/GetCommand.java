package ru.fizteh.fivt.students.kocurba.filemap.command;

import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.ShellState;
import ru.fizteh.fivt.students.kocurba.shell.command.Command;

public class GetCommand implements Command<State> {

	@Override
	public int getArgCount() {
		return 1;
	}

	@Override
	public String getCommandName() {
		return "get";
	}

	@Override
	public void executeCommand(ShellState<State> state, String[] arguments)
			throws IOException {
		if (state.getState().getCurrentTable() == null) {
			System.err.println("no table");
			return;
		}
		String result = state.getState().getCurrentTable().get(arguments[1]);
		if (result == null) {
			System.out.println("not found");
		} else {
			System.out.println("found");
			System.out.println(result);
		}
	}

}
