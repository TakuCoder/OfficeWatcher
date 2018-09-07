package officewatcher.thiyagu.com.officewatcher;

class DataPojoClass {


    private String date;
    private String inTime;
    private String outTime;


    public void setDate(String date) {
        this.date = date;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getDate() {

        return date;
    }

    public String getInTime() {
        return inTime;
    }

    public String getOutTime() {
        return outTime;
    }

    public DataPojoClass(String date, String inTime, String outTime) {

        this.date = date;
        this.inTime = inTime;
        this.outTime = outTime;
    }
}
