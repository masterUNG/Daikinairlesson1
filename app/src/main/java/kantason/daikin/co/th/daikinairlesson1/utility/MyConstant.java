package kantason.daikin.co.th.daikinairlesson1.utility;

public class MyConstant {

//    private String urlInfoString = "http://192.168.1.108/aircon/get_control_info";
    private  String urlInfoString = "/aircon/get_control_info";

    private String[] frateStrings = new String[]{"Auto","Silent","Level 1","Level 2","Level 3","Level 4","Level 5"};

//    3,4,5,6,7,A,B


    public String[] getFrateStrings() {
        return frateStrings;
    }

    public String getUrlInfoString() {
        return urlInfoString;
    }
}   // main class
