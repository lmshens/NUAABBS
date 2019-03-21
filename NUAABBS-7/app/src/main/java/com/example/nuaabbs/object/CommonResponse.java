package com.example.nuaabbs.object;

public class CommonResponse {
    private String resCode;
    private String resMsg;
    private String resParam;
    private String resParam2;

    public CommonResponse(){
    }

    public CommonResponse(String resCode, String resMsg, String resParam){
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.resParam = resParam;
        this.resParam2 = "";
    }

    public CommonResponse(String resCode, String resMsg, String resParam, String resParam2){
        this.resCode = resCode;
        this.resMsg = resMsg;
        this.resParam = resParam;
        this.resParam2 = resParam2;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public String getResParam() {
        return resParam;
    }

    public void setResParam(String resParam) {
        this.resParam = resParam;
    }

    public String getResParam2() {
        return resParam2;
    }

    public void setResParam2(String resParam2) {
        this.resParam2 = resParam2;
    }
}
