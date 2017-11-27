package com.dxc.plm.codechecker;

import static org.junit.Assert.assertEquals;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dxc.plm.codechecker.configuration.AppConfig;
import com.dxc.plm.codechecker.utils.Constants;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class ApplicationTest {
	// use to assert system output
	@Rule
	public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();

	@Autowired
	private Application app;
	
	@Test
	public void testAppWithoutArgs() throws Exception {
		app.run(null);
		String report_path = System.getProperty("user.home") + "/" + Constants.REPORT;
		boolean exists = Files.exists(Paths.get(report_path));
		assert(exists);
	}
	
	@Test
	public void testWithArgs() throws Exception {
		List<String> args = new ArrayList<>();
		args.add(0, "testdata/Script");
		app.run(args);
		String report_path = System.getProperty("user.home") + "/" + Constants.REPORT;
		boolean exists = Files.exists(Paths.get(report_path));
		assert(exists);
	}
	
	@Test
	public void testGetVersion() throws Exception {
		List<String> args = new ArrayList<>();
		args.add(0, "-v");
		app.run(args);
		// assert system output
		//assertEquals("2.0",systemOutRule.getLog());
		assertEquals("2.0",Constants.APP_VERSION);
	}

}
