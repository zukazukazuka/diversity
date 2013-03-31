package com.github.diversity.console.command;

import java.util.Collection;

import com.github.diversity.console.ConsoleHandleCommand;
import com.github.diversity.plugin.PluginRepository;
import com.github.diversity.plugin.ScriptDescriptor;

public class PluginCommand implements ConsoleHandleCommand {

	private PluginRepository pluginRepository;

	public PluginCommand(PluginRepository pluginRepository){
		this.pluginRepository = pluginRepository;
	}
	
	@Override
	public STATUS execute(String input) {
		if (!"plugins".equals(input)){
			return STATUS.CONTINE;
		}
		Collection<ScriptDescriptor> resources = this.pluginRepository.getAllDescriptors();
		for (ScriptDescriptor resource :resources){
			System.out.println(resource.getDescription());
		}
		return STATUS.DONE;
	}

}
