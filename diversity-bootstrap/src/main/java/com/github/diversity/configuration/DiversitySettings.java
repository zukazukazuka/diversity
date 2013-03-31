package com.github.diversity.configuration;

import groovy.util.ConfigObject;
import groovy.util.ConfigSlurper;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.seasar.util.collection.ArrayUtil;
import org.seasar.util.io.FileUtil;

public class DiversitySettings {

	public static final String CONFIG_FILE_NAME = "settings.groovy";

	private ConfigObject config;

	private File customSettings = null;

	protected List<File> pluginDirectories = new ArrayList<File>();

	public DiversitySettings(Properties properties) {
		this.config = new ConfigSlurper().parse(properties);
	}

	public String getUserHomeDirectory() {
		return (String)this.getValue("user.home");
	}

	public String getCurrentDirectory() {
		return (String)this.getValue("diversity.currentDir");
	}

	public String getHomeDirectory() {
		return (String)this.getValue("diversity.home");
	}

	public List<File> getPluginDirectories() {
		return this.pluginDirectories;
	}

	public void addPluginDirectory(File location) {
		this.pluginDirectories.add(location);
	}

	public String getGlobalPluginDirectory() {
		return (String)this.getValue("diversity.plugin.global");
	}

	public void setCustomSettings(File customSettings){
		this.customSettings = customSettings;
	}
	
	public void load() {
		List<String>configs = ArrayUtil.toList(new String[] {
				this.getHomeDirectory() + "/config/" + CONFIG_FILE_NAME,
				this.getUserHomeDirectory() + "/diversity/" + CONFIG_FILE_NAME,
				this.getCurrentDirectory() + "/diversity/" +CONFIG_FILE_NAME });
		if (this.customSettings != null) {
			configs.add(FileUtil.getCanonicalPath(this.customSettings));
		}

		for (String conf : configs) {
			this.loadAndMerge(conf);
		}
		configurePluginDirectories();
	}

	public Object getValue(String key) {
		String lastToken = key;
		ConfigObject toSearch = this.config;
		if (key.indexOf(".") > 0) {
			String[] token = key.split("\\.");
			lastToken = token[token.length - 1];
			for (int i = 0; i < token.length - 1; i++) {
				toSearch = (ConfigObject) (toSearch.get(token[i]));
			}
		}
		if (toSearch == null){
			return null;
		}
		return toSearch.get(lastToken);
	}

	public String getString(String key) {
		return (String) this.getValue(key);
	}

	protected void configurePluginDirectories() {
		List<String> dirs =ArrayUtil.toList(new String[] {
				this.getHomeDirectory() + "/plugins",
				this.getUserHomeDirectory() + "/diversity/plugins" });
		String globalPluginDir = this.getGlobalPluginDirectory();
		if (globalPluginDir != null) {
			dirs.add(globalPluginDir);
		}
		dirs.add(this.getCurrentDirectory() + "/diversity/plugins");
		for (String path : dirs) {
			File dir = new File(path);
			if (dir.exists() && dir.isDirectory()) {
				this.addPluginDirectory(dir);
			}
		}
	}

	protected void loadAndMerge(String path) {
		File configFile = new File(path.trim());
		if (configFile.exists()) {
			ConfigObject localConfig = new ConfigSlurper().parse(FileUtil
					.toURL(configFile));
			@SuppressWarnings("unchecked")
			Map<Object, Object> map = this.config.merge(localConfig);
			this.config.putAll(map);
		}
	}

	protected void loadAndMergeFromResource(String path) {
		ClassLoader lc = Thread.currentThread().getContextClassLoader();
		URL url = lc.getResource(path.trim());
		ConfigObject localConfig = new ConfigSlurper().parse(url);
		@SuppressWarnings("unchecked")
		Map<Object, Object> map = this.config.merge(localConfig);
		this.config.putAll(map);
	}

}
