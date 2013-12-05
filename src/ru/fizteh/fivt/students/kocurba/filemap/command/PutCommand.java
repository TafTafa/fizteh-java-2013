package ru.fizteh.fivt.students.kocurba.filemap.command;

import java.io.IOException;

import ru.fizteh.fivt.students.kocurba.shell.StateWrap;
import ru.fizteh.fivt.students.kocurba.shell.command.Command;

public class PutCommand implements Command<State> {

	@Override
	public int getArgCount() {
		return 2;
	}

	@Override
	public String getCommandName() {
		return "put";
	}

	@Override
	public void executeCommand(StateWrap<State> state, String[] arguments)
			throws IOException {
		if (state.getState().getCurrentTable() == null) {
			System.err.println("no table");
			return;
		}
		String oldValue = state.getState().getCurrentTable().put(arguments[1], arguments[2]);
		if (oldValue == null) {
			System.out.println("new");
		} else {
			System.out.println("overwrite");
			System.out.println(oldValue);
		}
	}
}
