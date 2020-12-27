package com.mem.vo.business.base.model.po;

import lombok.Data;

@Data
public class MtaData {
    private int pv;
    private int uv;
    private int vv;
    private int iv;
    private String date;
    private String avg_online_time;
    private String avg_online_time_formatted;
}
