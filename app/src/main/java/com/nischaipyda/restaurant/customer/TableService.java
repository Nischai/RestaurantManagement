package com.nischaipyda.restaurant.customer;


public class TableService {

    private Boolean Cleaned;
    private Boolean Reserved;
    private Boolean Ordered;
    private Boolean Served;
    private Boolean Cooked;

    public Boolean getCooked() {
        return Cooked;
    }

    public Boolean getOrdered() {
        return Ordered;
    }

    public Boolean getServed() {
        return Served;
    }



    public Boolean getCleaned() {
        return Cleaned;
    }

    public Boolean getReserved() {
        return Reserved;
    }

}
