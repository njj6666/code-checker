package com.dxc.plm.codechecker;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dxc.plm.codechecker.configuration.AppConfig;
import com.dxc.plm.codechecker.rule.RuleFactory;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class SpringTest {
	
	@Autowired //Spring专用
	//@Inject 微小差别，与@Autowired 通用
	private RuleFactory factory;
	
	@Autowired
	private SVBChecker checker;
	
	@Test
	public void ruleFactoryShouldNotBeNull() {
		assertNotNull(factory);
	}
	
	@Test
	public void svbCheckerShouldNotBeNull() {
		assertNotNull(checker);
		assertNotNull(checker.getFactory());
	}

}
