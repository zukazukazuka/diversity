package learning.ivy;

import java.io.File;

import org.apache.ivy.Ivy;
import org.apache.ivy.core.module.id.ModuleRevisionId;
import org.apache.ivy.core.report.ResolveReport;
import org.apache.ivy.core.resolve.ResolveOptions;
import org.apache.ivy.core.settings.IvySettings;
import org.apache.ivy.plugins.resolver.IBiblioResolver;
import org.apache.ivy.plugins.resolver.URLResolver;
import org.junit.Test;

public class IvyLearningTest {

	@Test
	public void testDownloadArtifacts() throws Exception {
		IvySettings settings = new IvySettings();

		File baseDir = new File("target/ivy-test");
		settings.setBaseDir(baseDir);
		settings.setDefaultCache(baseDir);

		URLResolver resolver = new IBiblioResolver();
		resolver.setM2compatible(true);
		resolver.setName("central");
		settings.addResolver(resolver);
		settings.setDefaultResolver(resolver.getName());
		Ivy ivy = Ivy.newInstance(settings);
		ModuleRevisionId id = ModuleRevisionId.newInstance("commons-beanutils",
				"commons-beanutils", "1.8.3");
		String[] conf = new String[] { "default" };
		ResolveOptions o = new ResolveOptions().setConfs(conf);
		ResolveReport report = ivy.resolve(id, o, true);
		File file = report.getAllArtifactsReports()[0].getLocalFile();
		System.out.println(file.getAbsolutePath());
	}

	@Test
	public void testDownloadArtifactsFromNotCentral() throws Exception {
		IvySettings settings = new IvySettings();

		File baseDir = new File("target/ivy-test");
		settings.setBaseDir(baseDir);
		settings.setDefaultCache(baseDir);
		IBiblioResolver resolver = new IBiblioResolver();
		resolver.setM2compatible(true);
		resolver.setName("searsar");
		resolver.setRoot("http://maven.seasar.org/maven2/");
		settings.addResolver(resolver);
		settings.setDefaultResolver(resolver.getName());
		Ivy ivy = Ivy.newInstance(settings);
		ModuleRevisionId id = ModuleRevisionId.newInstance("org.seasar.container",
				"s2-tiger", "2.4.46");
		String[] conf = new String[] { "default" };
		ResolveOptions o = new ResolveOptions().setConfs(conf);

		ResolveReport report = ivy.resolve(id, o, true);
		File file = report.getAllArtifactsReports()[0].getLocalFile();
		System.out.println(file.getAbsolutePath());

	}

}
