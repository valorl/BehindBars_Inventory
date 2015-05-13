package ControlLayer;

import DBLayer.DBSettings;

public class SettingsCtr {
	
	private DBSettings dbSettings;
	
	public SettingsCtr()
	{
		dbSettings = new DBSettings();
	}
	
	public void addSettings(String settingCode, String value) throws Exception
	{
		dbSettings.insertSetting(settingCode, value);
	}
	
	public void updateSetting(String settingCode, String value) throws Exception
	{
		dbSettings.updateSettingState(settingCode, value);
	}
	
	public void deleteSetting(String settingCode) throws Exception
	{
		dbSettings.delete(settingCode);
	}
	
	public String findSetting(String settingCode, boolean retriveAssociation) throws Exception
	{
		return dbSettings.findSetting(settingCode, retriveAssociation);
	}

}
