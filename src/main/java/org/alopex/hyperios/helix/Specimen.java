package org.alopex.hyperios.helix;

public class Specimen {

	private Object[] genes; // POI @ odd, RES @ even
	private double fitness;
	
	public Specimen() {
		genes = new Object[GASettings.genomeSize];
		
		// Randomize all the genes
		for (int i=0; i < genes.length; i++) {
			//TODO: gene randomization
			//genes[i] = Core.words[(int) (Math.random() * Core.words.length)];
			if (i % 2 == 0) {
				// Get random POI
			} else {
				// Get random RES (addl param: count)
			}
		}
	}
	
	public double getFitness() {
		// Reset fitness and recalculate (can be changed later)
		fitness = 1;
		
		StringBuilder sb = new StringBuilder();
		for (int i=0; i < genes.length; i++) {
			sb.append(genes[i]);
		}
		return fitness;
	}
	
	public Object[] getGenes() {
		return genes;
	}
	
	public void setGene(int index, Object gene) {
		genes[index] = gene;
	}
	
	public void testMutate() {
		for (int i=0; i < genes.length; i++) {
			if (Math.random() <= GASettings.mutateRate) {
				// Randomize gene
				// TODO: gene randomization
			}
		}
	}
}
