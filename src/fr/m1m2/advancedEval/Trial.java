package fr.m1m2.advancedEval;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

import fr.lri.swingstates.canvas.CEllipse;
import fr.lri.swingstates.canvas.CExtensionalTag;
import fr.lri.swingstates.canvas.CRectangle;
import fr.lri.swingstates.canvas.CShape;
import fr.lri.swingstates.canvas.CText;
import fr.lri.swingstates.canvas.Canvas;

public class Trial {

	protected boolean practice = false;
	protected int block;
	protected int trial;
	protected String visualVariable; // VV
	protected int canvasWidth = 1000;
	protected int canvasHeight = 1000;
	protected int objectsCount;
	protected int marginWidth;
	protected int marginHeight;
	protected int radius = 40;
	protected long startTime;
	protected long endTime;
	protected long duration;
	protected boolean error = false;
	protected int oddX;
	protected int oddY;


	protected CExtensionalTag instructions = new CExtensionalTag() { };

	protected CExtensionalTag shapes = new CExtensionalTag() { };

	// enter key listener
	protected KeyListener enterListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				start();
				startTime = System.currentTimeMillis(); // start timer
			}
		};
	};

	// space bar listener
	protected KeyListener spaceBarListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				validateChoice();
				endTime = System.currentTimeMillis(); // stop timer
			}
		};
	};

	// mouse listener
	protected MouseListener clickListener = new MouseAdapter() {
		public void mousePressed(MouseEvent e) {
			
			System.out.println("this is the mouse " + e.getX() + ", " + e.getY());
			System.out.println("this is the odd one " + oddX + ", " + oddY);
			System.out.println("the distance " + distance(e.getX(), e.getY(), oddX, oddY));

			// TODO
			// if clicked shape correct
			if (distance(e.getX(), e.getY(), oddX, oddY) < 30) {
				error = false;		
			} else {
				error = true;		
			}
			
			System.out.println("error is " + error);
			
			stop(); // end trial
		};
	};
	
	public int distance (float x1, float y1, float x2, float y2) {
		// http://stackoverflow.com/a/14431081
		int distance = (int)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
		return distance;
	}

	protected Experiment experiment;

	public Trial(Experiment experiment, boolean practice, int block, int trial, String targetChange, int nonTargetsCount) {
		this.practice = practice;
		this.block = block;
		this.trial = trial;
		this.visualVariable = targetChange;
		this.objectsCount = nonTargetsCount;
		this.experiment = experiment;
	}

	public void displayInstructions() {
		Canvas canvas = experiment.getCanvas();
		CText text1 = canvas.newText(0, 0, "A scene with multiple shapes will get displayed", Experiment.FONT);
		CText text2 = canvas.newText(0, 50, "Identify the shape that is different from all other shapes", Experiment.FONT);
		CText text3 = canvas.newText(0, 100, "    1. Press Space bar", Experiment.FONT);
		CText text4 = canvas.newText(0, 150, "    2. Click on the identified shape", Experiment.FONT);
		CText text5 = canvas.newText(0, 200, "Do it AS FAST AND AS ACCURATELY AS POSSIBLE", Experiment.FONT);
		CText text6 = canvas.newText(0, 350, "--> Press Enter key when ready", Experiment.FONT.deriveFont(Font.PLAIN, 15));
		text1.addTag(instructions);
		text2.addTag(instructions);
		text3.addTag(instructions);
		text4.addTag(instructions);
		text5.addTag(instructions);
		text6.addTag(instructions);
		double textCenterX = instructions.getCenterX();
		double textCenterY = instructions.getCenterY();
		double canvasCenterX = canvas.getWidth()/2;
		double canvasCenterY = canvas.getHeight()/2;
		double dx = canvasCenterX - textCenterX;
		double dy = canvasCenterY - textCenterY;
		instructions.translateBy(dx, dy);
		canvas.setAntialiased(true);

		canvas.addKeyListener(enterListener);
		canvas.requestFocus();
	}

	public void start() {
		Canvas canvas = experiment.getCanvas();
		// remove key listener for enter key
		canvas.removeKeyListener(enterListener);
		// clear the scene from instructions
		canvas.removeShapes(instructions);

		System.out.println("start: "+this);

		// a key listener for space bar that will call the validateChoice method
		canvas.addKeyListener(spaceBarListener);
		int marginHeight;
		int marginWidth;
		int x;
		int y;
		int colNr;
		int rowNr;
		// TODO
		// display graphical scene with the matrix of shapes
		switch(objectsCount){
		
		case(9):
			marginWidth = 300;
			rowNr = 3;
			x = (canvasWidth-(marginWidth*2))/rowNr;
			drawShapes(canvas, marginWidth, marginWidth, x, x, rowNr, rowNr);
		break;
		
		case(24):
			marginWidth = 150;
			marginHeight = 250;
			rowNr = 4;
			colNr = 6;
			x = (canvasWidth-(marginWidth*2))/colNr;
			y = (canvasHeight-(marginHeight*2))/rowNr;
			drawShapes(canvas, marginWidth, marginHeight, x, y, rowNr, colNr);
		break;

		case(30):
			marginWidth = 150;
			marginHeight = 150;
			rowNr = 5;
			colNr = 6;
			x = (canvasWidth-(marginWidth*2))/colNr;
			y = (canvasHeight-(marginHeight*2))/rowNr;
			drawShapes(canvas, marginWidth, marginHeight, x, y, rowNr, colNr);
		break;

		}

	}
	public void drawShapes(Canvas canvas, int marginWidth, int marginHeight, int x, int y, int rows, int columns){
		int count = 1;
		Random rand = new Random();
		int randomNum = 1 + rand.nextInt(objectsCount);
		for(int i=0; i<objectsCount/rows; i++){
			for(int j=0; j<objectsCount/columns; j++){

				if (count == randomNum) {
					CRectangle rect = canvas.newRectangle(((x*i)+x/2 + marginWidth) - (radius/2), ((y*j)+y/2) - (radius/2) + marginHeight, radius, radius);
					rect.addTag(shapes);
<<<<<<< HEAD
					
=======
					oddX = (x*i)+x/2 + marginWidth;
					oddY = (y*j)+y/2 + marginHeight;
>>>>>>> francesco
				} else {
					CEllipse circle = canvas.newEllipse((x*i)+x/2 + marginWidth, (y*j)+y/2 + marginHeight, radius, radius);
					circle.addTag(shapes);
					
					CEllipse circle2 = canvas.newEllipse(((x*i)+x/2 + marginWidth) - (radius + 5), (y*j)+y/2 + marginHeight, radius, radius);
					CEllipse circle3 = canvas.newEllipse(((x*i)+x/2 + marginWidth) - (radius/2), ((y*j)+y/2 + marginHeight) - (radius + 5), radius, radius);
					circle2.addTag(shapes);
					circle3.addTag(shapes);
					
				}
				count++;
			}
		}
	}

	public void drawPlaceHolders(Canvas canvas, int marginWidth, int marginHeight, int x, int y, int rows, int columns){
		for(int i=0; i<objectsCount/columns; i++){
			for(int j=0; j<objectsCount/rows; j++){

				CText text = canvas.newText((x*i)+ x/2 + radius + marginWidth, (y*j)+y/2 + marginHeight + radius, "+", new Font("verdana", Font.PLAIN, 24));
				text.addTag(shapes);
			}
		}
	}

	public void validateChoice() {

		System.out.println("validateChoice: "+this);

		Canvas canvas = experiment.getCanvas();

		// remove key listener for space bar
		canvas.removeKeyListener(spaceBarListener);

		// clear the scene from shapes
		canvas.removeShapes(shapes);

		int marginWidth;
		int x;
		
		// display place holders to replace the actual shapes
		switch(objectsCount){
		
		case(9):
			//int activeAreaWidth = 400;
			marginWidth = 300;
			x = (canvasWidth-(marginWidth*2))/3;
			drawPlaceHolders(canvas, marginWidth, marginWidth, x, x, 3, 3);	
		break;
		case(24):
			marginWidth = 150;
			x = (canvasWidth-(marginWidth*2))/6;
			drawPlaceHolders(canvas, marginWidth, marginWidth, x, x, 6, 4);
		break;
		case(30):
			marginWidth = 150;
			x = (canvasWidth-(marginWidth*2))/6;
			drawPlaceHolders(canvas, marginWidth, marginWidth, x, x, 5, 6);
		break;
		}

		// a mouse listener for listening clicks on a shape that will call stop	
		canvas.addMouseListener(clickListener);
	}

	public void stop() {
		experiment.log(this);

		Canvas canvas = experiment.getCanvas();

		// clear the scene from place holders
		canvas.removeShapes(shapes);

		// remove mouse listener for listening clicks on a shape
		canvas.removeMouseListener(clickListener);
	}

	public long getDuration() {
		duration =  endTime - startTime;
		return duration;	
	}

	public int error() {
		// TODO should return 1 in case of error, 0 otherwise 
		
		if (error) {
			return 1;
		} else {
		
		return 0;
		}
	}


	public boolean isPractice() {
		return practice;
	}

	public void setPractice(boolean practice) {
		this.practice = practice;
	}

	public int getBlock() {
		return block;
	}

	public void setBlock(int block) {
		this.block = block;
	}

	public int getTrial() {
		return trial;
	}

	public void setTrial(int trial) {
		this.trial = trial;
	}

	public String getVisualVariable() {
		return visualVariable;
	}

	public void setVisualVariable(String visualVariable) {
		this.visualVariable = visualVariable;
	}

	public int getObjectsCount() {
		return objectsCount;
	}

	public void setObjectsCount(int objectsCount) {
		this.objectsCount = objectsCount;
	}

	public Experiment getExperiment() {
		return experiment;
	}

	public void setExperiment(Experiment experiment) {
		this.experiment = experiment;
	}

	public String toString() {
		return practice+","+block+","+trial+","+objectsCount+","+visualVariable;
	}

}
