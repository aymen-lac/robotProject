package com.myproject.dto;

import java.util.List;

public class InputObject {

	private int roomSizeX;
	private int roomSizeY;
	private Point coords;
	private List<Point> patches;
	private List<String> movements;
	public int getRoomSizeX() {
		return roomSizeX;
	}
	public void setRoomSizeX(int roomSizeX) {
		this.roomSizeX = roomSizeX;
	}
	public int getRoomSizeY() {
		return roomSizeY;
	}
	public void setRoomSizeY(int roomSizeY) {
		this.roomSizeY = roomSizeY;
	}
	public Point getCoords() {
		return coords;
	}
	public void setCoords(Point coords) {
		this.coords = coords;
	}
	public List<Point> getPatches() {
		return patches;
	}
	public void setPatches(List<Point> patches) {
		this.patches = patches;
	}
	public List<String> getMovements() {
		return movements;
	}
	public void setMovements(List<String> movements) {
		this.movements = movements;
	}
	
	
}
