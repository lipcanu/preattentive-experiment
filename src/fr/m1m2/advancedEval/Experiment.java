package fr.m1m2.advancedEval;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class Experiment {
	
	protected String participant = "";
	protected int block = -1;
	protected int trial = -1;

	protected File designFile = null;
	
	protected ArrayList<Trial> allTrials = new ArrayList<Trial>();
	protected int currentTrial = 0;
	
	public Experiment() {
		//input fil (design) 
		protected File designFile = null;
		//output file: logs
		protected PrintWriter pwLog = null;
		protected ArrayList<Trial> allTrials = new ArrayList<Trial>();
		protected int currentTrial = 0 ;

		public Experiment(String participant, int block, int trial, File designFile) {
			//... 
			loadTrials();
			initLog;
			nextTrial;
		}

		public void loadTrials() {
			//..smth here...
		}

		public void trialComplete(){
			Trial trial = allTrials.get(currentTrial);
			trial.stop;
			log(trial);
			currentTrial++;
			nextTrial();
		}

		

	 }
	
	public void start(String participantsHeader, String blockHeader, String trialHeader) {
		// 1. parse the experiment design file for feeding allTrials with the list of trials that should be run for that participant
		// 2. init the log file (initLog method)
		// 3. init the graphical scene
		// 4. start the first trial (nextTrial method)
	}
	
	public void initLog() {
		// ...
	}
	
	public void nextTrial() {
		// ...
	}
	
	/*******************************/
	/******GETTERS AND SETTERS******/
	/*******************************/

	public String getParticipant() {
		return participant;
	}

	public void setParticipant(String participant) {
		this.participant = participant;
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

	public File getDesignFile() {
		return designFile;
	}

	public void setDesignFile(File designFile) {
		this.designFile = designFile;
	}
	
	/*********************************************/
	/******METHODS TO START AT A GIVEN POINT******/
	/*********************************************/
	
	/**
	 * @param participantsHeader the name of the column where the participant ID can be found
	 * @return the list of participants found in the experiment file
	 */
	public ArrayList<String> participantsList(String participantsHeader) {
		ArrayList<String> participants = new ArrayList<String>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(designFile));
			String line = br.readLine();
			String[] parts = line.split(",");
			int participantsIndex = 0;
			for (int i = 0; i < parts.length; i++) {
				if(parts[i].compareTo(participantsHeader) == 0) {
					participantsIndex = i;
				}
			}
			line = br.readLine();
			String currentParticipant = "";
			while(line != null) {
				parts = line.split(",");
				String p = parts[participantsIndex];
				if(p.compareTo(currentParticipant) != 0) {
					currentParticipant = p;
					participants.add(p);
				}
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return participants;
	}
	
	/**
	 * @param blockHeader the name of the column where the block number can be found
	 * @param trialHeader the name of the column where the trial number ID can be found
	 * @return an array of size 2 containing the number of blocks in its first cell and the maximum number of trials per block in its second cell 
	 */
	public int[] trialsCounter(String blockHeader, String trialHeader) {
		int[] res = new int[2];
		res[0] = -1;
		res[1] = -1;
		try {
			BufferedReader br = new BufferedReader(new FileReader(designFile));
			String line = br.readLine();
			String[] parts = line.split(",");
			int blockIndex = 0;
			int trialIndex = 0;
			for (int i = 0; i < parts.length; i++) {
				if(parts[i].compareTo(blockHeader) == 0) {
					blockIndex = i;
				} else if(parts[i].compareTo(trialHeader) == 0) {
					trialIndex = i;
				}
			}
			line = br.readLine();
			while(line != null) {
				parts = line.split(",");
				int b = Integer.parseInt(parts[blockIndex]);
				int t = Integer.parseInt(parts[trialIndex]);
				res[0] = Math.max(res[0], b);
				res[1] = Math.max(res[1], t);
				line = br.readLine();
			}
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}
	
	/*******************************/
	/*************MAIN**************/
	/*******************************/
	
	public static void main(String[] args) {
		final Experiment experiment = new Experiment();

		final JFrame starterFrame = new JFrame("Experiment starter");
		starterFrame.getContentPane().setLayout(new GridLayout(4, 2));

		File experimentFile = new File("experiment.csv");
		experiment.setDesignFile(experimentFile);

		ArrayList<String> participantsList = experiment.participantsList("Participant");
		String[] participantsArray = new String[participantsList.size()];
		int i = 0;
		for (Iterator<String> iterator = participantsList.iterator(); iterator.hasNext();) {
			String s = iterator.next();
			participantsArray[i] = s;
			i++;
		}
		JComboBox<String> comboParticipants = new JComboBox<String>(participantsArray);
		starterFrame.getContentPane().add(new JLabel("Participant ID:"));
		starterFrame.getContentPane().add(comboParticipants);
		comboParticipants.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				String p = (String)cb.getSelectedItem();
				experiment.setParticipant(p);
			}
		});
		experiment.setParticipant(participantsArray[0]);
		experiment.setBlock(0);
		experiment.setTrial(0);

		int[] trialsCounter = experiment.trialsCounter("Block", "Trial");
		System.out.println("trialsCounter "+trialsCounter[0]+", "+trialsCounter[1]);
		starterFrame.getContentPane().add(new JLabel("Block:"));
		JSpinner spinnerBlock = new JSpinner();
		spinnerBlock.setModel(new SpinnerNumberModel(1, 1, trialsCounter[0], 1));
		starterFrame.getContentPane().add(spinnerBlock);
		spinnerBlock.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Integer b = (Integer)(((JSpinner)e.getSource()).getModel().getValue());
				experiment.setBlock(b);
			}
		});

		starterFrame.getContentPane().add(new JLabel("Trial:"));
		JSpinner spinnerTrial = new JSpinner();
		spinnerTrial.setModel(new SpinnerNumberModel(1, 1, trialsCounter[1], 1));
		starterFrame.getContentPane().add(spinnerTrial);
		spinnerTrial.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				Integer t = (Integer)(((JSpinner)e.getSource()).getModel().getValue());
				experiment.setTrial(t);
			}
		});

		starterFrame.getContentPane().add(new JLabel(""));
		JButton goButton = new JButton("OK");
		starterFrame.getContentPane().add(goButton);
		goButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				experiment.start("Participant", "Block", "Trial");
				starterFrame.setVisible(false);
			}
		});

		starterFrame.pack();
		starterFrame.setVisible(true);
		starterFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
