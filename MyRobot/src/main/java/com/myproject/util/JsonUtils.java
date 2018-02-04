package com.myproject.util;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.myproject.dto.FinalStatus;
import com.myproject.dto.InputObject;
import com.myproject.dto.Point;
import com.myproject.exception.MyException;

public class JsonUtils {

	private static final String JSON_INPUT_ERROR = "Error while parsing the JSON input.";
	private static final String ROOM_SIZE = "roomSize";
	private static final String COORDS = "coords";
	private static final String PATCHES = "patches";
	private static final String INSTRUCTIONS = "instructions";
	
	private static final Character NORTH_MOVEMENT = 'N';
	private static final Character SOUTH_MOVEMENT = 'S';
	private static final Character EAST_MOVEMENT = 'E';
	private static final Character WEST_MOVEMENT = 'W';
	public static final String JSON_FORMAT_NOT_VALID = "the json input format is invalid";
	public static final String ROOMSIZE_VALUE_NOT_INT = "roomSize values are not correct, these values have to be interer values";
	public static final String ROOMSIZE_NEEDS_TWO_VALUES = "roomSize values are not correct, roomSize needs two values";
	public static final String ROOMSIZE_NOT_FOUND = "roomSize not found";
	public static final String COORDS_VALUE_NOT_INT = "coords values are not correct, these values have to be interer values";
	public static final String COORDS_NEEDS_TWO_VALUES = "coords values are not correct, coords needs two values";
	public static final String COORDS_NOT_FOUND = "coords not found";
	public static final String PATCHES_VALUE_NOT_INT = "patches are incorrect, the values have to be integers";
	public static final String PATCHES_LINE_NEEDS_TWO_VALUES = "patches are incorrect, each patch have to contains two integer values";
	public static final String PATCHES_NOT_FOUND = "patches not found";
	public static final String INSTRUCTIONS_NOT_FOUND = "instructions not found";
	public static final String INSTUCTIONS_EMPTY = "instructions are empty";
	public static final String INSTRUCTIONS_NOT_VALID = "instructions are not valid. Known instructions are only N, S, W, E (case sensitive)";
	public static final String COORDS_OUTSIDE_ROOM = "coords are incorrect according to roomSize";
	public static final String PATCH_OUTSIDE_ROOM = "patches are incorrect according to roomSize";


	/**
	 * Convert the input JSON string to an InputObject
	 * @param input
	 * @return the InputObject initialized with the data of the JSON input
	 * @throws MyException 
	 */
	public static InputObject convertJsonInput(String input) throws MyException {

		InputObject inputObject = new InputObject();
		
		JSONObject jsonObject;
		try{
			jsonObject = new JSONObject(input);
		}catch(Exception e){
			// the json format is not valid
			throw new MyException(JSON_INPUT_ERROR, JSON_FORMAT_NOT_VALID);
		}
		
		JSONArray roomSize;
		try{
			roomSize = jsonObject.getJSONArray(ROOM_SIZE);
		}catch(Exception e)
		{
			// the input doesn't contain any roomSize
			throw new MyException(JSON_INPUT_ERROR, ROOMSIZE_NOT_FOUND);
		}
		if(roomSize.length() == 2)
		{
			try{
				inputObject.setRoomSizeX(roomSize.getInt(0));
				inputObject.setRoomSizeY(roomSize.getInt(1));
			}catch(Exception e)
			{
				// the input is invalid if the values aren't int
				throw new MyException(JSON_INPUT_ERROR, ROOMSIZE_VALUE_NOT_INT);
			}
		}else{
			// if the JSON doesn't contain 2 lines for roomSize => exception
			throw new MyException(JSON_INPUT_ERROR, ROOMSIZE_NEEDS_TWO_VALUES);
			}
		
		JSONArray coords;
		try{
			coords = jsonObject.getJSONArray(COORDS);
		}catch(Exception e)
		{
			// the input doesn't contain any coords
			throw new MyException(JSON_INPUT_ERROR, COORDS_NOT_FOUND);
		}
		if(coords.length() == 2)
		{
			Point coordsPoint;
			try{
				coordsPoint = new Point(coords.getInt(0), coords.getInt(1));
			}catch(Exception e)
			{
				// the input is invalid if the values aren't int
				throw new MyException(JSON_INPUT_ERROR, COORDS_VALUE_NOT_INT);
			}
			if((coordsPoint.getCoordX() < inputObject.getRoomSizeX()) && (coordsPoint.getCoordY() < inputObject.getRoomSizeY()))
			{
				inputObject.setCoords(coordsPoint);
			}else{
				// The point is outside the allowed room size
				throw new MyException(JSON_INPUT_ERROR, COORDS_OUTSIDE_ROOM);
			}
		}else{
			// if the JSON doesn't contain 2 lines for coords => exception
			throw new MyException(JSON_INPUT_ERROR, COORDS_NEEDS_TWO_VALUES);
		}

		JSONArray patches;
		try{
			patches = jsonObject.getJSONArray(PATCHES);
		}catch(Exception e)
		{
			// the input doesn't contain any patches
			throw new MyException(JSON_INPUT_ERROR, PATCHES_NOT_FOUND);
		}
		List<Point> patchesPoints = new ArrayList<>(); 
		for(int i=0 ; i<patches.length(); i++)
		{
			JSONArray patch = patches.getJSONArray(i);
			
			if(patch.length()==2)
			{
				Point patchPoint;
				try{
					patchPoint = new Point(patch.getInt(0), patch.getInt(1));
				}catch(Exception e)
				{
					// the input is invalid if the values aren't int
					throw new MyException(JSON_INPUT_ERROR, PATCHES_VALUE_NOT_INT);
				}
				if((patchPoint.getCoordX() < inputObject.getRoomSizeX()) && (patchPoint.getCoordY() < inputObject.getRoomSizeY()))
				{
					if(!patchesContainsPatch(patchesPoints, patchPoint))
					{
						// don't add if it is a duplicate patch
						patchesPoints.add(patchPoint);
					}
				}else{
					// The point is outside the allowed room size
					throw new MyException(JSON_INPUT_ERROR, PATCH_OUTSIDE_ROOM);
				}
			}else{
				// if the JSON doesn't contain 2 lines for each patch => exception
				throw new MyException(JSON_INPUT_ERROR, PATCHES_LINE_NEEDS_TWO_VALUES);
			}
			
		}
		
		inputObject.setPatches(patchesPoints);

		String instructions;
		try{
			instructions = jsonObject.getString(INSTRUCTIONS);
			
		}catch(Exception e)
		{
			// the input doesn't contain any instructions
			throw new MyException(JSON_INPUT_ERROR, INSTRUCTIONS_NOT_FOUND);
		}
		List<String> listInstructions = getMovementsList(instructions);
		
		inputObject.setMovements(listInstructions);

		
		return inputObject;
	}

	private static boolean patchesContainsPatch(List<Point> patchesPoints, Point patchToCheck) {
		for(Point patch : patchesPoints){
			if(patch.getCoordX() == patchToCheck.getCoordX() && patch.getCoordY() == patchToCheck.getCoordY()){
				return true;
			}
		}
		return false;
	}

	/**
	 * convert the input string movement from JSON input to a List of movements
	 * @param instructions
	 * @return
	 * @throws MyException the movements list
	 */
	private static List<String> getMovementsList(String instructions) throws MyException {
		if(instructions == null || instructions.isEmpty()){
			// if the JSON doesn't contain any instruction => exception
			throw new MyException(JSON_INPUT_ERROR, INSTUCTIONS_EMPTY);
		}
		
		List<String> movementsList = new ArrayList<>();
		
		for(Character c : instructions.toCharArray()){
			if(!c.equals(NORTH_MOVEMENT) && !c.equals(SOUTH_MOVEMENT) && !c.equals(WEST_MOVEMENT) && !c.equals(EAST_MOVEMENT)){
				// if the JSON contains an unknown movement => exception
				throw new MyException(JSON_INPUT_ERROR, INSTRUCTIONS_NOT_VALID);
			}
			movementsList.add(c.toString());
		}
		return movementsList;
	}

	/**
	 * get the Json output of the service
	 * @param finalStatus
	 * @return the json output 
	 */
	public static String getJsonOutput(FinalStatus finalStatus) {
		
		JSONObject jsonObject = new JSONObject();
		
		JSONArray coordsArray = new JSONArray();

		coordsArray.put(finalStatus.getFinalPoint().getCoordX());
		coordsArray.put(finalStatus.getFinalPoint().getCoordY());
		

		jsonObject.put(COORDS, coordsArray);
 
		jsonObject.put(PATCHES, finalStatus.getNbPatches());
		
		return jsonObject.toString();
	}

}
