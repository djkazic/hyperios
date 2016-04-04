package org.alopex.hyperios.helix;

import org.alopex.hyperios.helix.optimize.POI;
import org.alopex.hyperios.helix.optimize.RES;
import org.alopex.hyperios.util.Utils;
import org.json.JSONObject;

public class GACore {
	
	public static JSONObject execute() {
		Population starter = new Population(GASettings.populationSize, 1);
		System.out.println("Starter pop: " + starter.printPopulation());

		while (starter.bestSpecimen().getFitness() < 25) {
			starter = starter.evolve();
			System.out.println();
			System.out.println("Population evolved. " + starter.printPopulation());
		}
		
		JSONObject outArray = new JSONObject();
		
		// Conversion of the genes to a JSON-able array
		Object[] bestGenes = starter.bestSpecimen().getGenes();
		int counter = 1;
		int othercounter = 1;
		for (int i=0; i < bestGenes.length; i++) {
			if (i % 2 == 0) {
				// POI
				POI poi = (POI) bestGenes[i];
				outArray.put("poi_" + counter, poi.getAddress());
				counter++;
			} else {
				// RES
				POI poi = (POI) bestGenes[i - 1];
				RES res = (RES) bestGenes[i];
				if (res.getCount() <= 0) {
					Utils.log("GACore", "Warning: detected invalid res count");
					res.setCount(1);
				}
				
				double adjustedCountMultiplier = res.getCount() / 2;
				if (adjustedCountMultiplier == 0) {
					adjustedCountMultiplier = res.getCount();
				}
				
				if (poi.getDensity() <= 0) {
					Utils.log("GACore", "Warning: detected invalid POI density @ " + poi.getAddress());
					poi.setDensity(1);
				}
				
				double damageRatio = poi.getDensity() * (adjustedCountMultiplier * res.getRadius());
				
				double casFactor = ((3 * Math.log(4 * damageRatio)) / 20) * 100;
				
				double totalResCost = res.getCost() * res.getCount();
				if (totalResCost <= 0) {
					Utils.log("GACore", "Warning: detected invalid res cost | count");
					totalResCost = 1;
				}
				
				if (totalResCost >= 50) {
					totalResCost /= 50;
				}
				
				casFactor /= totalResCost;
				
				if (casFactor < 0) {
					casFactor /= -1;
				}
				System.out.println("casFactor => " + casFactor);
				
				outArray.put("res_" + othercounter, casFactor * 10);
				othercounter++;
			}
		}
		
		// JSON: POI => CASFACTOR
		return outArray;
	}
}
