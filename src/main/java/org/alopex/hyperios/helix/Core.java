package org.alopex.hyperios.helix;
import java.util.ArrayList;

public class Core {
	
	public static void main(String[] args) {
		Population starter = new Population(GASettings.populationSize, 1);
		System.out.println("Starter pop: " + starter.printPopulation());

		while (starter.bestSpecimen().getFitness() <= 1) {
			starter = starter.evolve();
			System.out.println();
			System.out.println("Population evolved. " + starter.printPopulation());
		}
	}
}
