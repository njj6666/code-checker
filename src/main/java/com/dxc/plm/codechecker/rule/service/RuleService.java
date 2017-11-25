package com.dxc.plm.codechecker.rule.service;

import com.dxc.plm.codechecker.model.CodeLine;

public interface RuleService {
	
	// 由各个service自己实现
	public CodeLine parseLine(String line);
	public String checkLineType(String line);
	public void checkGlobalVar(String var);
	public String getFunctionName(String line);
	
	// 公用函数逻辑，在 GeneralRuleService中实现
	public void checkInitCap(String line);
	public void checkHungrian(String var);
	public boolean checkVarsCount(String line);
	
	
	

}
