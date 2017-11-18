package com.dxc.plm.codechecker;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.Test;

import com.dxc.plm.codechecker.utils.Constants;

public class ApplicationTest {

	@Test
	public void testAppWithoutArgs() throws Exception {
		String[] args = {};
		Application.main(args);
		String report_path = System.getProperty("user.home") + "/" + Constants.REPORT;
		boolean exists = Files.exists(Paths.get(report_path));
		assert(exists);
	}
	
	@Test
	public void testWithArgs() throws Exception {
		String[] args = {"testdata/Script"};
		Application.main(args);
		String report_path = System.getProperty("user.home") + "/" + Constants.REPORT;
		boolean exists = Files.exists(Paths.get(report_path));
		assert(exists);
	}
	
	@Test
	public void testGetVersion() throws Exception {
		String version = "1.2.0";
		assert(version.equals(Constants.APP_VERSION));
	}

}
