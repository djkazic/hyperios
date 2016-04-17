package org.alopex.hyperios.helix;

import org.alopex.hyperios.helix.optimize.POI;
import org.alopex.hyperios.helix.optimize.RES;

public class Specimen {

	private Object[] genes; // POI @ odd, RES @ even
	private double fitness;
	
	public Specimen(boolean isCrossover) {
		genes = new Object[GASettings.genomeSize];
		
		if (!isCrossover) {
			// Randomize all genes
			for (int i=0; i < genes.length; i++) {
				if (i % 2 == 0) {
					// Get random POI
					genes[i] = new POI(true);
				} else {
					// Get random RES (addl param: count)
					genes[i] = new RES(true);
				}
			}
		}
	}
	
	public double getFitness() {
		for (int i=0; i < genes.length - 1; i += 2) {
			if (i % 2 == 0) {
				if (genes[i] != null) {
					// POI
					POI thisPoi = (POI) genes[i];
					RES poiRes = (RES) genes[i + 1];
					double adjustedCountMultiplier = poiRes.getCount() / 2;
					if (adjustedCountMultiplier == 0) {
						adjustedCountMultiplier = poiRes.getCount();
					}
					double damageRatio = thisPoi.getDensity() * (adjustedCountMultiplier * poiRes.getRadius());
					double casFactor = ((3 * Math.log(4 * damageRatio)) / 20) * 100;
					casFactor /= ((poiRes.getCost() * poiRes.getCount()) / 50);
					fitness = casFactor;
					return fitness;
				}
			}
		}
		fitness = -1;
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
				if (i % 2 == 0) {
					// Get random POI
					genes[i] = new POI(true);
				} else {
					// Get random RES (addl param: count)
					genes[i] = new RES(true);
				}
			}
		}
	}
}
