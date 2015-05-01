package DBLayer;
/**
 * Interface of the DBSettingState
 * @author Group 3/C
 */
public interface IFDBSettings {
   //get one Setting by its id
    public String findSetting(String settingCode, boolean retriveAssociation) throws Exception;
    //insert a new Setting
    public int insertSetting(String settingCode, String value) throws Exception;
    //update information about an SettingState
    public int updateSettingState(String settingCode, String value) throws Exception;
    
}