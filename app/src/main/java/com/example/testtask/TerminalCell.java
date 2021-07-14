package com.example.testtask;

public class TerminalCell {
    double latitude;
    double longitude;
    boolean receiveCargo;
    boolean giveoutCargo;
    boolean defaultT;
    String city;
    String name;
    String worktableStr;
    WorkTable worktable;
    Map maps_url;

    public TerminalCell(String city ,String name,
                        double latitude, double longitude,
                        boolean receiveCargo, boolean giveoutCargo,
                        boolean defaultT,
                        String worktableStr, String maps_urlStr)
    {
        this.city = city;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.receiveCargo = receiveCargo;
        this.giveoutCargo = giveoutCargo;
        this.defaultT = defaultT;
        this.worktableStr = worktableStr;
        maps_url = MakeMap(maps_urlStr);
    }

    public Map MakeMap(String mapsData)
    {
        Map newMap = new Map(mapsData);
        return newMap;
    }

    public WorkTable MakeWorkTable(String worktableData)
    {
        WorkTable newTable = new WorkTable(worktableData);
        return newTable;
    }

    public double getLatitude() {
        return latitude;
    }


    public double getLongitude() {
        return longitude;
    }

    public boolean isReceiveCargo() {
        return receiveCargo;
    }

    public boolean isGiveoutCargo() {
        return giveoutCargo;
    }

    public String getCity() {
        return city;
    }

    public String getName() {
        return name;
    }

    public String getWorktable() { return worktableStr; }
}
