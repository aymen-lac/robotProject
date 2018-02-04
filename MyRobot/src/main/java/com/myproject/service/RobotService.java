package com.myproject.service;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.myproject.dto.FinalStatus;
import com.myproject.dto.InputObject;
import com.myproject.dto.Point;
import com.myproject.exception.MyException;
import com.myproject.persistence.DatabaseUtil;
import com.myproject.util.JsonUtils;

public class RobotService {

	
	private static final String NORTH_MOVEMENT = "N";
	private static final String SOUTH_MOVEMENT = "S";
	private static final String EAST_MOVEMENT = "E";
	private static final String WEST_MOVEMENT = "W";

	@Path("/moveRobot")
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public String robotService(final String input) throws MyException {
	  
		InputObject inputObject = JsonUtils.convertJsonInput(input);
		
		FinalStatus finalStatus = moveRobot(inputObject);
		
		String result = JsonUtils.getJsonOutput(finalStatus);
	  
		try {
			DatabaseUtil.saveRobotExecution(input, result);
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			// error while connecting to database
			e.printStackTrace();
		}
		
	  return result;
	}

	/**
	 * launch the movements of the robot according to the instructions
	 * @param inputObject
	 * @return FinalStatus object containing the final point location of the robot and the number of patched cleaned during execution
	 */
	private FinalStatus moveRobot(InputObject inputObject) {

		FinalStatus finalStatus = new FinalStatus();
		
		int nbPatches = 0;
		
		Point currentPoint = inputObject.getCoords();
		
		for(String movement : inputObject.getMovements())
		{
			if(isPatch(currentPoint, inputObject.getPatches()))
			{
				nbPatches++;
			}
			currentPoint = move(currentPoint, movement, inputObject.getRoomSizeX(), inputObject.getRoomSizeY());
		}
		
		finalStatus.setFinalPoint(currentPoint);
		finalStatus.setNbPatches(nbPatches);
		
		return finalStatus;
	}

	/**
	 * Check if a given point is a patch
	 * @param currentPoint
	 * @param patches
	 * @return true if the given point is a patch, false is it isn't
	 */
	private boolean isPatch(Point currentPoint, List<Point> patches) {
		for(Point patch : patches)
		{
			if(patch.getCoordX() == currentPoint.getCoordX() && patch.getCoordY() == currentPoint.getCoordY())
			{
				patches.remove(patch);
				return true;
			}
		}
		return false;
	}

	/**
	 * move the robot according to one instruction movement
	 * @param currentPoint
	 * @param movement
	 * @param sizeX
	 * @param sizeY
	 * @return the point where the robot is located after this movement
	 */
	private Point move(Point currentPoint, String movement, int sizeX, int sizeY) {
		int targetX = currentPoint.getCoordX();
		int targetY = currentPoint.getCoordY();
		switch (movement) {
		case NORTH_MOVEMENT:
			if(targetY<sizeY)
			{
				// if after the movement Y >= size Y => don't move
				targetY++;
			}
			break;
		case SOUTH_MOVEMENT:
			if(targetY>0)
			{
				// if after the movement Y < 0 => don't move
				targetY--;
			}
			break;
		case EAST_MOVEMENT:
			if(targetX<sizeX)
			{
				// if after the movement X >= size X => don't move
				targetX++;
			}
			break;
		case WEST_MOVEMENT:
			if(targetX>0)
			{
				// if after the movement X < 0 => don't move
				targetX--;
			}
			break;
		default:
			break;
		}
		return new Point(targetX, targetY);
	}

	
}
