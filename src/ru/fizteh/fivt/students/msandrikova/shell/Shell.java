package ru.fizteh.fivt.students.msandrikova.shell;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.io.File;


public class Shell {

	private Map < String, Command > commandsList;
	private File currentDirectory = new File("").getAbsoluteFile();
	private boolean isInteractive = false;
	
	private void InitMap(Command[] commands) {
		Map< String, Command > m = new HashMap<String, Command>();
		for(Command c : commands){
			m.put(c.getName(), c);
		}
		this.commandsList = Collections.unmodifiableMap(m);
	}
	
	
	
	private void executeOfInstructionLine(String instructionLine) {
		String[] instructionsList = new String[]{};
		String[] argumentsList;
		instructionsList = Utils.parseOfInstructionLine(instructionLine);
		for(String instruction : instructionsList){
			argumentsList = Utils.parseOfInstruction(instruction);
			if(argumentsList[0].equals("")){
				continue;
			}
			if(this.commandsList.containsKey(argumentsList[0])) {
				this.currentDirectory = this.commandsList.get(argumentsList[0]).execute(argumentsList, isInteractive, this.currentDirectory);
			} else {
				Utils.generateAnError("Illegal command's name: \"" + argumentsList[0] + "\"", "", isInteractive);
				continue;
			}
		}
	}
	
	public Shell(Command[] commands) {
		this.InitMap(commands);
	}
	
	public void execute(String[] args) {
		String instructionLine = new String();
		if(args.length == 0) {
			this.isInteractive = true;
			Scanner scanner = new Scanner(System.in);
			
			while(!Thread.currentThread().isInterrupted()) {
				System.out.print("$ ");
				instructionLine = scanner.nextLine();
				this.executeOfInstructionLine(instructionLine);
			}
			scanner.close();
		} else {
			instructionLine = Utils.joinArgs(Arrays.asList(args), " ");
			this.executeOfInstructionLine(instructionLine);
		}
	}
}