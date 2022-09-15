import java.awt.Color;
import java.awt.Rectangle;

public class Sprite {

	private int xPosition, yPosition;
	private int xVelocity, yVelocity;
	private int width, height;
	private int initialXPosition, initialYPosition;
	private Color color;
	
	//Getters & Setters for X + Y Position
	public int getXPosition() {
		return xPosition;
	}
	
	public int getYPosition() {
		return yPosition;
	}
	
	public void setXPosition(int xPosition) {
		this.xPosition = xPosition;
	}
	
	public void setYPosition(int yPosition) {
		this.yPosition = yPosition;
	}
	
	public void setXPosition(int newX, int panelWidth) {
		xPosition = newX;
		if (xPosition < 0) {
			xPosition = 0;
		}
		else if (xPosition + width > panelWidth) {
			xPosition = panelWidth - width;
		}
	}
	
	public void setYPosition(int newY, int panelHeight) {
		yPosition = newY;
		if (yPosition < 0) {
			yPosition = 0;
		}
		else if (yPosition + height > panelHeight) {
			yPosition = panelHeight - height;
		}
	}
	
	public void setInitialPosition(int initialX, int initialY) {
		initialXPosition = initialX;
		initialYPosition = initialY;
		
	}
	
	public void resetToInitialPosition() {
		setXPosition(initialXPosition);
		setYPosition(initialYPosition);
	}
	
	//Getters & Setters for X + Y Velocity
	public int getXVelocity() {
		return xVelocity;
	}
	
	public int getYVelocity() {
		return yVelocity;
	}
	
	public void setXVelocity(int xVelocity) {
		this.xVelocity = xVelocity;
	}
	
	public void setYVelocity(int yVelocity) {
		this.yVelocity = yVelocity;
	}
	
	//Getters & Setters for Width + Height
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public void setWidth(int width) {
		this.width = width;
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	//Getter & Setter for Color
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
	//Code for the Rectangle
	public Rectangle getRectangle() {
		return new Rectangle(getXPosition(), getYPosition(), getWidth(), getHeight());
	}
	
}
