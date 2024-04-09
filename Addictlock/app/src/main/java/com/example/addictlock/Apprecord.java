package com.example.addictlock;

/**
 * Created by USER on 1/29/2017.
 */
public class Apprecord {

    String packagename;
    String appname;
    String isApplocked;
    String isAddictlocked;
    String starttime;
    String endtime;
    String daysRepeat;

    public Apprecord()
    {

    }

    public Apprecord(String packagename, String appname, String isApplocked, String isAddictlocked, String starttime,String endtime, String daysRepeat){
        this.packagename= packagename;
        this.appname = appname;
        this.isApplocked = isApplocked;
        this.isAddictlocked = isAddictlocked;
        this.starttime = starttime;
        this.endtime = endtime;
        this.daysRepeat = daysRepeat;
    }

    public String getpackagename(){
        return this.packagename;
    }

    public void setpackagename(String packagename){
        this.packagename = packagename;
    }

    public String getappname(){
        return this.appname;
    }

    public void setappname(String appname){
        this.appname = appname;
    }

    public String getisApplocked(){
        return this.isApplocked;
    }

    public void setisApplocked(String isApplocked){
        this.isApplocked = isApplocked;
    }

    public String getIsAddictlocked(){
        return this.isAddictlocked;
    }

    public void setIsAddictlocked(String isAddictlocked){
        this.isAddictlocked = isAddictlocked;
    }

    public String getStartTime(){
        return this.starttime;
    }

    public void setStartTime(String starttime){  this.starttime = starttime;   }

    public String getEndtime(){
        return this.endtime;
    }

    public void setEndtime(String endtime){  this.endtime = endtime;   }

    public String getDaysRepeat() { return this.daysRepeat; }

    public void setDaysRepeat(String daysRepeat){
        this.daysRepeat = daysRepeat;
    }
}
