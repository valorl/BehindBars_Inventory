package UILayer;

import java.util.ArrayList;
import java.util.Arrays;

public class TypeManager {

	private static ArrayList<String> MEASURABLE_DRINKS = new ArrayList<>(Arrays.asList("vodka", "gin", "rum", "tequilla", "scotch", "brandy", "bitter", "liqueur", "draft beer", "table wine", "fortified wine", "pre-batched", "whiskey"));
	private static ArrayList<String> MEASURABLE_OTHER = new ArrayList<>(/*Arrays.asList()*/);
	private static ArrayList<String> MIXED = new ArrayList<String>(Arrays.asList("pre-batched"));
	
	private static ArrayList<String> SPIRITS = new ArrayList<>(Arrays.asList("vodka", "gin", "rum", "tequilla", "scotch", "brandy", "bitter", "liqueur", "whiskey"));
	
	private static ArrayList<String> UNIT_BOTTLE = new ArrayList<String>(Arrays.asList("vodka", "gin", "rum", "tequilla", "scotch", "brandy", "bitter", "liqueur","table wine", "fortified wine", "whiskey"));
	private static ArrayList<String> UNIT_KEG = new ArrayList<String>(Arrays.asList("draft beer"));
	
	// GET & ADD	
	public static ArrayList<String> getMeasurableDrinks() {
		return MEASURABLE_DRINKS;
	}
	public static void addMeasurableDrink(String type) {
		MEASURABLE_DRINKS.add(type.toLowerCase());
	}
	
	public static ArrayList<String> getMeasurableOther() {
		return MEASURABLE_OTHER;
	}
	public static void addMeasurableOther(String type) {
		MEASURABLE_OTHER.add(type.toLowerCase());
	}
	
	public static ArrayList<String> getMixedTypes() {
		return MIXED;
	}
	public static void addMixedType(String type) {
		MIXED.add(type.toLowerCase());
	}
	
	public static ArrayList<String> getSpiritTypes() {
		return SPIRITS;
	}
	public static void addSpiritType(String type) {
		SPIRITS.add(type.toLowerCase());
	}

	
	// IS 
	public static boolean isMeasurableType(String type) {
		if(MEASURABLE_DRINKS.contains(type.toLowerCase()) || MEASURABLE_OTHER.contains(type.toLowerCase())) {
			return true;
		}
		return false;
	}

	public static boolean isMeasurableDrinkType(String type) {
		if(MEASURABLE_DRINKS.contains(type.toLowerCase())) {
			return true;
		}
		return false;

	}
	
	public static boolean isMixed(String type) {
		if(MIXED.contains(type.toLowerCase())) {
			return true;
		}
		else {
			return false;
		}
	}
	

	
	
	
	public static String getUnit(String type) {
		if(UNIT_BOTTLE.contains(type.toLowerCase()) || type.toLowerCase().equals("spirits")) {
			return "bottle";
		}
		if(UNIT_KEG.contains(type.toLowerCase())) {
			return "keg";
		}
		return "N/A";
	}
}
