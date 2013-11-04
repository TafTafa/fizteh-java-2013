package ru.fizteh.fivt.students.kocurba.shell;

public class ShellState<T> {
	private T state;

	public ShellState(T dataBase) {
		state = dataBase;
	}

	public void setState(T newState) {
		state = newState;
	}

	public T getState() {
		return state;
	}

}
