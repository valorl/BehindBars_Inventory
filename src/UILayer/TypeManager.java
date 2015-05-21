package UILayer;

import java.util.ArrayList;
import java.util.Arrays;

public class TypeManager {
	
	private static ArrayList<String> MEASURABLE_DRINKS = new ArrayList<>(Arrays.asList("vodka", "gin", "rum", "tequilla", "scotch", "brandy", "bitter", "liqueur"));
	
	
	public static ArrayList<String> getMeasurableDrinks() {
		return MEASURABLE_DRINKS;
	}
	public static void addMeasurableDrink(String type) {
		MEASURABLE_DRINKS.add(type);
	}
	
	public static boolean isMeasurableDrinkType(String type) {
		if(MEASURABLE_DRINKS.contains(type.toLowerCase())) {
			return true;
		}
		else {
			return false;
		}
	}
}
