package UILayer;

import javafx.util.StringConverter;

public class DoubleStringConverter extends StringConverter<Double>{

	@Override
	public Double fromString(String string) {
		double number;
		try {
			return Double.parseDouble(string);
		}
		catch(NumberFormatException ex) 
		{
			ex.printStackTrace();
			return -1.0;
		}
	}

	@Override
	public String toString(Double number) {
		return "" + number;
	}
	
}
