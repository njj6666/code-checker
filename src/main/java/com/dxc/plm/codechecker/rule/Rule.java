package com.dxc.plm.codechecker.rule;

import java.io.IOException;

public interface Rule {

	public void executeRule(String line) throws IOException;

	
}
