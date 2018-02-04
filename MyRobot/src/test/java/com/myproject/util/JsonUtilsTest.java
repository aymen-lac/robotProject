package com.myproject.util;

import static org.junit.Assert.*;


import org.junit.Test;

import com.myproject.dto.InputObject;
import com.myproject.exception.MyException;

public class JsonUtilsTest {

	@Test
	public void testInput() {
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
			InputObject inputObject = JsonUtils.convertJsonInput(input);
			assertEquals(inputObject.getCoords().getCoordX(), 1);
			assertEquals(inputObject.getCoords().getCoordY(), 2);
			assertEquals(inputObject.getRoomSizeX(), 5);
			assertEquals(inputObject.getRoomSizeY(), 5);
			assertEquals(inputObject.getPatches().size(), 3);
			assertEquals(inputObject.getMovements().size(), 11);

		} catch (MyException e) {
			isException = true;
		}
		assertFalse(isException);
	}
	
	@Test
	public void testInputWithDuplicatePatch() {
		String input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		boolean isException = false;
		
		try {
			InputObject inputObject = JsonUtils.convertJsonInput(input);
			assertEquals(inputObject.getPatches().size(), 3);

		} catch (MyException e) {
			isException = true;
		}
		assertFalse(isException);
	}
	
	@Test
	public void testVariousIncorrectInputs() {
		
		// without roomSize
		String input = "{\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		boolean isException = false;
		String exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.ROOMSIZE_NOT_FOUND, exceptionDetails);
		
		// incorrect roomSize
		input = "{\n"
				+ "\"roomSize\" : [5, 5, 6],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.ROOMSIZE_NEEDS_TWO_VALUES, exceptionDetails);
		
		// incorrect roomSize
		input = "{\n"
				+ "\"roomSize\" : [5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.ROOMSIZE_NEEDS_TWO_VALUES, exceptionDetails);

		// incorrect roomSize
		input = "{\n"
				+ "\"roomSize\" : [a, C],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.ROOMSIZE_VALUE_NOT_INT, exceptionDetails);

		// incorrect roomSize
		input = "{\n"
				+ "\"roomSize\" : [2, 3],\n"
				+ "\"coords\" : [5],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.COORDS_NEEDS_TWO_VALUES, exceptionDetails);

		// incorrect coords
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, b],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.COORDS_VALUE_NOT_INT, exceptionDetails);

		// incorrect patches
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2, 3],\n"
				+ "\"patches\" : [\n"
				+ "[1, g],\n"
				+ "[2, 2],\n"
				+ "[2, p]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.COORDS_NEEDS_TWO_VALUES, exceptionDetails);

		
		// incorrect patches
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.PATCHES_LINE_NEEDS_TWO_VALUES, exceptionDetails);

		// incorrect patches
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, d]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.PATCHES_VALUE_NOT_INT, exceptionDetails);

		
		// incorrect instructions
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNEsEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.INSTRUCTIONS_NOT_VALID, exceptionDetails);
		
		// incorrect instructions
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"N, N, S\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.INSTRUCTIONS_NOT_VALID, exceptionDetails);

		
		// no instructions
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.INSTRUCTIONS_NOT_FOUND, exceptionDetails);

		
		// no patches
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.PATCHES_NOT_FOUND, exceptionDetails);

		
		// incorrect instructions
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"121\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.INSTRUCTIONS_NOT_VALID, exceptionDetails);
		
		// empty instructions
		input = "{\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.INSTUCTIONS_EMPTY, exceptionDetails);

		
		// incorrect json format
		input = "[\n"
				+ "\"roomSize\" : [5, 5],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "]";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.JSON_FORMAT_NOT_VALID, exceptionDetails);
		
		// incorrect json format
		input = "{\n"
				+ "\"roomSize\" : {5, 5},\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"NNESEESWNWW\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.JSON_FORMAT_NOT_VALID, exceptionDetails);

		
		// coords incorrect according to roomsize
		input = "{\n"
				+ "\"roomSize\" : [5, 6],\n"
				+ "\"coords\" : [1, 7],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[2, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.COORDS_OUTSIDE_ROOM, exceptionDetails);


		
		// patch incorrect according to roomsize
		input = "{\n"
				+ "\"roomSize\" : [5, 6],\n"
				+ "\"coords\" : [1, 2],\n"
				+ "\"patches\" : [\n"
				+ "[1, 0],\n"
				+ "[5, 2],\n"
				+ "[2, 3]\n"
				+ "],\n"
				+ "\"instructions\" : \"\"\n"
				+ "}";
		
		isException = false;
		exceptionDetails = null;
		
		try {
			JsonUtils.convertJsonInput(input);

		} catch (MyException e) {
			isException = true;
			exceptionDetails = e.getDetails();
		}
		assertTrue(isException);
		assertEquals(JsonUtils.PATCH_OUTSIDE_ROOM, exceptionDetails);


	}

}
