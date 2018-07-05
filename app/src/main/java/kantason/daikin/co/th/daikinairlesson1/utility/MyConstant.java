package kantason.daikin.co.th.daikinairlesson1.utility;

public class MyConstant {

//    private String urlInfoString = "http://192.168.1.108/aircon/get_control_info";
    private  String urlInfoString = "/aircon/get_control_info";
    private String urlSetPowerString = "/aircon/set_control_info?pow=";
    private String urlSetModeString = "/aircon/set_control_info?mode=";
    private String urlSettempString = "/aircon/set_control_info?stemp=";
    private String urlgetroomtemp = "/aircon/get_sensor_info";
    private String urlSetfrateString = "/aircon/set_control_info?f_rate=";
    private String urlSetfdirString = "/aircon/set_control_info?f_dir=";





    private String[] frateStrings = new String[]{"Auto","Silent","Level 1","Level 2","Level 3","Level 4","Level 5"};
    private String[] fDirStrings = new String[]{"OFF","Vertical","Horizontal","3D"};

    public String getUrlSetModeString() { return urlSetModeString; }

    public String getUrlSetfrateString() { return urlSetfrateString; }

    public String getUrlSetfdirString() { return urlSetfdirString; }

    public String getUrlSettempString() { return urlSettempString; }

    public String getUrlSetPowerString() {
        return urlSetPowerString;
    }

    public String[] getfDirStrings() {
        return fDirStrings;
    }

    public String[] getFrateStrings() {
        return frateStrings;
    }

    public String getUrlInfoString() { return urlInfoString; }

    public String geturlgetroomtemp() { return urlgetroomtemp; }
}   // main class
