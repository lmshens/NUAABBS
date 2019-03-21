package com.example.nuaabbs.object;

public class CommonRequest {
    String requestCode;
    String param1;
    String param2;

    public CommonRequest(){
        requestCode = "";
        param1 = "";
        param2 = "";
    }

    public CommonRequest(String reqCode, String arg1, String arg2){
        requestCode = reqCode;
        param1 = arg1;
        param2 = arg2;
    }

    public String getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(String requestCode) {
        this.requestCode = requestCode;
    }

    public String getParam1() {
        return param1;
    }

    public void setParam1(String param1) {
        this.param1 = param1;
    }

    public String getParam2() {
        return param2;
    }

    public void setParam2(String param2) {
        this.param2 = param2;
    }
}
