package com.github.diversity.console;

public interface ConsoleHandleCommand {

	enum STATUS {
		DONE,
		CONTINE,
		STOP;
	}
	
	public STATUS execute(String input);
}
