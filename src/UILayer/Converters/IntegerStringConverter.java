package UILayer.Converters;

import javafx.util.StringConverter;

public class IntegerStringConverter extends StringConverter<Integer>{

	@Override
	public Integer fromString(String string) {
		int number;
		try {
			return Integer.parseInt(string);
		}
		catch(NumberFormatException ex) 
		{
			ex.printStackTrace();
			return -1;
		}
	}

	@Override
	public String toString(Integer number) {
		return "" + number;
	}
	
}
