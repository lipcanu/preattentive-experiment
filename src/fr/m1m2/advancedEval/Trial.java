package fr.m1m2.advancedEval;

public class Trial {

	protected boolean practice = false;
	protected int block;
	protected int trial;
	protected String targetChange;
	protected int nonTargetsCount;
	
	protected Experiment experiment;
	
	public Trial(Experiment experiment, boolean practice, int block, int trial, String targetChange, int nonTargetsCount) {
		this.practice = practice;
		this.block = block;
		this.trial = trial;
		this.targetChange = targetChange;
		this.nonTargetsCount = nonTargetsCount;
		this.experiment = experiment;
		
		
	}
	public void displayInstructions(){
		//...
	}
	public void hideInstructions(){
		//experiment.getCanvas().removeShapes(experiment.getInstructions());
	}
	
	public void start(){
		//...
		// install the graphical listener and the user input listener
		//call experiment.trialCompleted(); when appropriate
	}
	public void stop(){
		
	}
	
	
}
