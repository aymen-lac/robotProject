package com.myproject.dto;

public class Point {
  
	private int coordX;
	
	private int coordY;

	public int getCoordX() {
		return coordX;
	}

	public void setCoordX(int coordX) {
		this.coordX = coordX;
	}

	public int getCoordY() {
		return coordY;
	}

	public void setCoordY(int coordY) {
		this.coordY = coordY;
	}

	public Point(int coordX, int coordY) {
		super();
		this.coordX = coordX;
		this.coordY = coordY;
	}
	
	
}
