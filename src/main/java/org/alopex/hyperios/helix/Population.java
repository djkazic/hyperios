package org.alopex.hyperios.helix;
import java.util.ArrayList;

import org.alopex.hyperios.helix.optimize.POI;
import org.alopex.hyperios.helix.optimize.RES;
import org.alopex.hyperios.util.Utils;

public class Population {
	
	private int generationNumber;
	private final int size;
	private ArrayList<Specimen> specimen;
	
	public Population(int size, int generationNumber, boolean requireSpecimen) {
		this.size = size;
		this.generationNumber = generationNumber;
		this.specimen = new ArrayList<Specimen> (size);
		randomizeSpecimen(requireSpecimen);
	}

	public Population evolve() {		
		// Create new Population
		Utils.log(this, "> Creating evolved population");
		final Population newPop = new Population(GASettings.populationSize, this.generationNumber + 1, false);
		
		// Keep our best individual
		Utils.log(this, "> Activating elitism");
		newPop.addSpecimen(this.bestSpecimen());
		
		Utils.log(this, "> Crossing over [experimental multithreaded]");
		// Crossover population
		final Population origin = this;
		for (int i=1; i < size; i++) {
			Utils.log(this, "\t> Crossover selection " + i);
			Specimen mateOne = origin.tournamentSelect();
			Specimen mateTwo = origin.tournamentSelect();

			Specimen newSpec = crossover(mateOne, mateTwo);
			newPop.addSpecimen(newSpec);
		}
		
		Utils.log(this, "> Mutating");
		// Mutate population
		for (int i=0; i < newPop.size; i++) {
			newPop.getSpecimen(i).testMutate();
		}
		
		Utils.log(this, "* Evolution complete");
		return newPop;
	}
	
	public void addSpecimen(Specimen spec) {
		specimen.add(spec);
	}
	
	public Specimen bestSpecimen() {
		double maxSpec = specimen.get(0).getFitness();
		Specimen bestSpec = specimen.get(0);
		for (Specimen spec : specimen) {
			if (spec.getFitness() > maxSpec) {
				maxSpec = spec.getFitness();
				bestSpec = spec;
			}
		}
		return bestSpec;
	}
	
	public Specimen tournamentSelect() {
		Population tournamentPop = new Population((int) (GASettings.populationSize * GASettings.tournamentRatio), -1, false);
		// For each place in the tournament get a random individual
		for (int i=0; i < tournamentPop.size; i++) {
			tournamentPop.addSpecimen(this.getSpecimen((int) (Math.random() * this.size)));
		}
		
		// Return fittest member of this tournament populatoin
		return tournamentPop.bestSpecimen();
	}
	
	public Specimen crossover(Specimen mateOne, Specimen mateTwo) {
		Specimen output = new Specimen(true);
		//System.out.println("Output gene length: " + output.getGenes().length);
		for (int i=0; i < output.getGenes().length; i++) {
			if (Math.random() <= GASettings.crossoverRate) {
				output.setGene(i, mateOne.getGenes()[i]);
			} else {
				output.setGene(i, mateOne.getGenes()[i]);
			}
		}
		return output;
	}
	
	public int getGeneration() {
		return generationNumber;
	}
	
	public Specimen getSpecimen(int index) {
		return specimen.get(index);
	}
	
	public void randomizeSpecimen(boolean pullFromDB) {
		if (pullFromDB) {
			for (int i=0; i < size; i++) {
				specimen.add(new Specimen(false));
			}
		}
	}
	
	public String printPopulation() {
		if (this.generationNumber != -1) {
			String preCursor = "Generation # [" + generationNumber + "], Best = " + this.bestSpecimen().getFitness();
			Object[] bestSpecDetails = this.bestSpecimen().getGenes();
			for (Object o : bestSpecDetails) {
				if (o instanceof POI) {
					POI poi = (POI) o;
					System.out.println(poi);
				} else if (o instanceof RES) {
					RES res = (RES) o;
					System.out.println(res);
				}
			}
			System.out.println("==================================================================================\n");
			return preCursor;
		} else {
			return "";
		}
	}
}
