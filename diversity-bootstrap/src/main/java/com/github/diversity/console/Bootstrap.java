package com.github.diversity.console;

import java.io.IOException;
import java.util.Properties;

import com.github.diversity.plugin.PluginRepository;
import com.github.diversity.scripts.ScriptRunner;
import com.github.diversity.configuration.DiversitySettings;

public class Bootstrap {
	
	public static void main(String... args) throws Exception{
		Bootstrap bootstrap = new Bootstrap();
		bootstrap.run();
	}
	
	public Bootstrap() {
	}
	
	public void run() throws IOException{
		Properties props = System.getProperties();
		DiversitySettings settings = new DiversitySettings(props);
		settings.load();
		PluginRepository pluginRepository = new PluginRepository(settings);
		pluginRepository.load();
		ScriptRunner scriptRunner = this.createScriptRunner();
		
		InteractiveConsole console = InteractiveConsole.getInstance();
		ConsoleMediator mediator = new ConsoleMediator(scriptRunner , console ,pluginRepository);
		mediator.run();
	}

	protected ScriptRunner createScriptRunner() {
		return new ScriptRunner();
	}
}
