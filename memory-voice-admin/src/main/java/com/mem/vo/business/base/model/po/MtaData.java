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
    private String count;

    private String click_times;

    private String click_users;

    private String area_name;

    private String area_id;

    private String gender;

    private String pv_day_ratio;

    private String uv_day_ratio;

    private String vv_day_ratio;

    private String iv_day_ratio;

    private String pv_week_ratio;

    private String uv_week_ratio;

    private String vv_week_ratio;

    private String iv_week_ratio;
}
