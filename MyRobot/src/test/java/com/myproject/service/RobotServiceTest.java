package com.myproject.service;

import static org.junit.Assert.*;

import org.junit.Test;

import com.myproject.exception.MyException;
import com.myproject.util.JsonUtils;

public class RobotServiceTest {
	
	private final RobotService robotService = new RobotService();

	@Test
	public void testServiceWithCorrectInput() {
		String input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		boolean isException = false;
		
		try {
			String output = robotService.robotService(input);
			
			assertTrue(output.contains("\"coords\":[1,3]"));
			assertTrue(output.contains("\"patches\":1"));
			
		} catch (MyException e) {
			isException = true;
		}
		
		assertFalse(isException);
	}

	@Test
	public void testServiceWithInorrectInput() {
		String input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNES123aWNWW\"\n"
				+ "}";
		
		boolean isException = false;
		String exceptionDetails = null;
		
		try {
			robotService.robotService(input);
			
		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		
		assertTrue(isException);
		assertEquals(JsonUtils.INSTRUCTIONS_NOT_VALID, exceptionDetails);
	}

	
	@Test
	public void testServiceWithCorrectInput2() {
		String input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		boolean isException = false;
		
		try {
			String output = robotService.robotService(input);
			
			assertTrue(output.contains("\"coords\":[1,3]"));
			assertTrue(output.contains("\"patches\":0"));
			
		} catch (MyException e) {
			isException = true;
		}
		
		assertFalse(isException);
	}

	
	@Test
	public void testServiceWithCorrectInput3() {
		String input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[1, 3],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		boolean isException = false;
		
		try {
			String output = robotService.robotService(input);
			
			assertTrue(output.contains("\"coords\":[1,3]"));
			assertTrue(output.contains("\"patches\":2"));
			
		} catch (MyException e) {
			isException = true;
		}
		
		assertFalse(isException);
	}


}
