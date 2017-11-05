package com.dxc.plm.codechecker.rule;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.log4j.Logger;

import com.dxc.plm.codechecker.utils.ApplicationContext;
import com.dxc.plm.codechecker.utils.Validator;

public class SVBVariableNamingRule implements Rule {
	static Logger log = Logger.getLogger(SVBVariableNamingRule.class.getName());

	@Override
	public void execute() throws IOException {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(ApplicationContext.getTargetFile()), "UTF-8"));
			String lineTxt = null;

			while ((lineTxt = br.readLine()) != null) {
				// TODO - slut - if want to validate the first row of file, add code here
				Validator.validateNaming(lineTxt);
			}
		} catch (IOException e) {
			log.error(e.getStackTrace());
		} finally {
			if (br != null) {
				br.close();
			}
		}

	}

}
