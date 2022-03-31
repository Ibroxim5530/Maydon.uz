package uz.arena.stadium;

public class OrderItem {
    String startTime, endTime, startTime2, endTime2, summa, arena_name, dimensions, choosing, location, check_1, check_2,
            radio, rasim1, rasim2, rasim3, rasim4;

    public OrderItem(){}

    public OrderItem(String startTime, String endTime, String summa, String arena_name, String dimensions, String choosing, String location, String check_1, String check_2, String radio, String rasim1, String rasim2, String rasim3, String rasim4) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.summa = summa;
        this.arena_name = arena_name;
        this.dimensions = dimensions;
        this.choosing = choosing;
        this.location = location;
        this.check_1 = check_1;
        this.check_2 = check_2;
        this.radio = radio;
        this.rasim1 = rasim1;
        this.rasim2 = rasim2;
        this.rasim3 = rasim3;
        this.rasim4 = rasim4;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getSumma() {
        return summa;
    }

    public String getArena_name() {
        return arena_name;
    }

    public String getDimensions() {
        return dimensions;
    }

    public String getChoosing() {
        return choosing;
    }

    public String getLocation() {
        return location;
    }

    public String getCheck_1() {
        return check_1;
    }

    public String getCheck_2() {
        return check_2;
    }

    public String getRadio() {
        return radio;
    }

    public String getRasim1() {
        return rasim1;
    }

    public String getRasim2() {
        return rasim2;
    }

    public String getRasim3() {
        return rasim3;
    }

    public String getRasim4() {
        return rasim4;
    }

    public OrderItem(String startTime2, String endTime2) {
        this.startTime2 = startTime2;
        this.endTime2 = endTime2;
    }

    public String getStartTime2() {
        return startTime2;
    }

    public String getEndTime2() {
        return endTime2;
    }
}
