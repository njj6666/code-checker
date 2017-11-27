package com.dxc.plm.codechecker;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import com.dxc.plm.codechecker.model.Report;
import com.dxc.plm.codechecker.rule.Rule;

public abstract class Checker {
	static Logger log = Logger.getLogger(Checker.class.getName());
	public List<Rule> rules;
	
	public List<Rule> getRules() {
		return rules;
	}

	public void setRules(List<Rule> rules) {
		this.rules = rules;
	}
	
	public abstract void registerRules(List<String> rules);
	
	public void process() throws IOException {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(Report.getTargetFile()), "UTF-8"));
			String lineTxt = null;
			while ((lineTxt = br.readLine()) != null) {
				// TODO - slut - if want to validate the first row of file, add code here
				for (Rule rule : this.rules) {
					rule.executeRule(lineTxt);
				}
				Report.setLineNumber(Report.getLineNumber() + 1);
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
