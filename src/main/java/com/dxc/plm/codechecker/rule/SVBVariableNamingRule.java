package com.dxc.plm.codechecker.rule;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.dxc.plm.codechecker.Main;
import com.dxc.plm.codechecker.utils.Validator;

public class SVBVariableNamingRule implements Rule {

	@Override
	public void execute() {

		BufferedReader br;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(Main.targetFile), "UTF-8"));
			String lineTxt = null;

			while ((lineTxt = br.readLine()) != null) {
//				if (Main.lineNumber == 1) {
//					Validator.checkOverviewComment(lineTxt);
//				}
				Validator.validateNaming(lineTxt);
			}
			br.close();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
