package com.dxc.plm.codechecker.rule.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dxc.plm.codechecker.configuration.AppConfig;
import com.dxc.plm.codechecker.model.Report;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class GeneralRuleServiceTest {
	
	@Autowired
	private GeneralRuleService service;
	
	@Test
	public void testIsInitialize() {
		Report.getReportItems().clear();
		String line = "Set abc";
		service.isInitilize(line);
		assertEquals("variable should be initialize",Report.getReportItems().size(),1);
	}
	
	@Test
	public void testIsMultipleVarsInOneLine() {
		Report.getReportItems().clear();
		String line = "Set abc, efg";
		boolean isMultipul = service.checkVarsCount(line);
		assertTrue("",isMultipul);
		
		line = "Set abc";
		isMultipul = service.checkVarsCount(line);
		assertFalse("",isMultipul);
	}
	
	@Test
	public void testIsHungrian() {
		Report.getReportItems().clear();
		String var = "objABC";
		service.checkHungrian(var);
		assertEquals(var+" should be correct.",Report.getReportItems().size(),0);
		
		var = "dtmABC";
		service.checkHungrian(var);
		assertEquals(var+" should be correct.",Report.getReportItems().size(),0);
		
		var = "arrABC";
		service.checkHungrian(var);
		assertEquals(var+" should be correct.",Report.getReportItems().size(),0);
		
		var = "bABC";
		service.checkHungrian(var);
		assertEquals(var+" should be correct.",Report.getReportItems().size(),0);
		
		var = "sABC";
		service.checkHungrian(var);
		assertEquals(var+" should be correct.",Report.getReportItems().size(),0);
		
		var = "nABC";
		service.checkHungrian(var);
		assertEquals(var+" should be correct.",Report.getReportItems().size(),0);
		
		
		var = "arraBC";
		service.checkHungrian(var);
		assertEquals(var+" should be incorrect.",Report.getReportItems().size(),1);
		
		var = "aABC";
		service.checkHungrian(var);
		assertEquals(var+" should be incorrect.",Report.getReportItems().size(),2);
	}
	
	
}
