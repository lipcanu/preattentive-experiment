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
	

	protected CExtensionalTag instructions = new CExtensionalTag() { };
	
	protected CExtensionalTag shapes = new CExtensionalTag() { };

	// enter key listener
	protected KeyListener enterListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				start();
			}
		};
	};
	
	// space bar listener
	protected KeyListener spaceBarListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_SPACE) {
				validateChoice();
			}
		};
	};

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
		
		// TODO
		// display graphical scene with the matrix of shapes
		switch(objectsCount){
		case(9):
			int activeAreaWidth = 400;
			int marginWidth = 300;
			int x = (canvasWidth-(marginWidth*2))/3;
			drawShapes(canvas, marginWidth, marginWidth, x, x);	
		case(24):
			
		}
	}
	public void drawShapes(Canvas canvas, int marginWidth, int marginHeight, int x, int y){
		int count = 1;
		Random rand = new Random();
		int randomNum = 1 + rand.nextInt(objectsCount);
		for(int i=0; i<objectsCount/3; i++){
			for(int j=0; j<objectsCount/3; j++){
				
				
				if (count == randomNum) {
					CRectangle rect = canvas.newRectangle((x*i)+x/2 + marginWidth, (y*j)+y/2 + marginHeight, radius, radius);
					rect.addTag(shapes);
				} else {
				CEllipse circle = new CEllipse((x*i)+x/2 + marginWidth, (y*j)+y/2 + marginHeight, radius, radius);
				canvas.addShape(circle);
				circle.addTag(shapes);
				}
				count++;
			}
		}
	}

	public void validateChoice() {
				
		System.out.println("validateChoice: "+this);
		
		Canvas canvas = experiment.getCanvas();

		// remove key listener for space bar
		canvas.removeKeyListener(spaceBarListener);
		
		// TODO
		// display placeholders to replace the actual shapes
		// clear the scene from shapes
		canvas.removeShapes(shapes);
		
		// TODO
		// install a mouse listener for listening clicks on a shape that will call stop		
	}

	public void stop() {
		experiment.log(this);
		
		// TODO
		// remove mouse listener for listening clicks on a shape
	}

	public long getDuration() {
		// TODO should return the perception time recorded for this trial
		return 0;
	}

	public int error() {
		// TODO should return 1 in case of error, 0 otherwise 
		return 0;
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
