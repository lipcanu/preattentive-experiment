package fr.m1m2.advancedEval;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.m1m2.swingstates.canvas.CEllipse;
import fr.m1m2.swingstates.canvas.CExtensionalTag;
import fr.m1m2.swingstates.canvas.CShape;
import fr.m1m2.swingstates.canvas.CText;
import fr.m1m2.swingstates.canvas.Canvas;

public class Trial {

	protected boolean practice = false;
	protected int block;
	protected int trial;
	protected String visualVariable; // VV
	protected int objectsCount;

	protected CExtensionalTag instructions = new CExtensionalTag() { };

	protected KeyListener enterListener = new KeyAdapter() {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == KeyEvent.VK_ENTER) {
				start();
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
		// TODO
		// display graphical scene with the matrix of shapes
		// install a key listener for space bar that will call the validateChoice method
	}

	public void validateChoice() {
		// TODO
		// remove key listener for space bar
		// display placeholders to replace the actual shapes
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
