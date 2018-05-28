package com.cocos.cocosapp.data.entities;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by kath on 27/12/17.
 */

public class ScheduleEntity implements Serializable {
    private int id;
    private String name;
    private Date time_start;
    private Date time_end;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime_start() {
        return time_start;
    }

    public void setTime_start(Date time_start) {
        this.time_start = time_start;
    }

    public Date getTime_end() {
        return time_end;
    }

    public void setTime_end(Date time_end) {
        this.time_end = time_end;
    }
}
