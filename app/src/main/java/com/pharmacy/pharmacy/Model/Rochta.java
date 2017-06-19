package com.pharmacy.pharmacy.Model;

/**
 * Created by Mohamed Hesham on 2017-06-19.
 */

public class Rochta {
    private String rochta_type;
    private String image_location;
    public Rochta(){
        rochta_type="";
        image_location="";
    }

    public void setRochta_type(String rochta_type) {
        this.rochta_type = rochta_type;
    }

    public void setImage_location(String image_location) {
        this.image_location = image_location;
    }

    public String getRochta_type() {
        return rochta_type;
    }

    public String getImage_location() {
        return image_location;
    }
}
