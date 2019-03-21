package com.shanke.forum.entity;

import lombok.Data;

@Data
public class DeviceInfo {

    private String IMEI;
    private String androidId;
    private String mac;
    private String serialnumber;


    public DeviceInfo(String IMEI, String androidId, String mac, String serialnumber) {
        this.IMEI = IMEI;
        this.androidId = androidId;
        this.mac = mac;
        this.serialnumber = serialnumber;
    }
}
