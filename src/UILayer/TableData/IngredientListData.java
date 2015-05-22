package UILayer.TableData;

import ModelLayer.Measurable;

public class IngredientListData {

	private Measurable ingredient;
	
	private double volume;
	
	public IngredientListData(Measurable ingredient, double volume) {
		this.ingredient = ingredient;
		this.volume = volume;
	}

	public Measurable getIngredient() {
		return ingredient;
	}

	public void setIngredient(Measurable ingredient) {
		this.ingredient = ingredient;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}
	
	
}
