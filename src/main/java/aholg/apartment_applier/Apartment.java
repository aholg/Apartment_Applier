package aholg.apartment_applier;

import java.util.ArrayList;

/**
 * Object for storing information about an apartment
 * @author Anton
 */
public class Apartment {

    private String address;
    private String date;
    private String city;
    private String rent;
    private String  applyPage;
    private String livingSpace;
    private String apartmentPage;
    Apartment() {

    }

    public String getApartmentPage() {
        return apartmentPage;
    }

    public void setApartmentPage(String apartmentPage) {
        this.apartmentPage = "https://bostad.stockholm.se"+apartmentPage;
    }

    public String getLivingSpace() {
        return livingSpace;
    }

    public void setLivingSpace(String livingSpace) {
        this.livingSpace = livingSpace;
    }

    void setAddress(String address) {
        this.address = address;
    }

    void setDate(String date) {
        this.date = date;
    }

    void setCity(String city) {
        this.city = city;
    }

    void setRent(String rent) {
        this.rent = rent;
    }


    void setApplyPage(String applyPage) {
        this.applyPage ="https://bostad.stockholm.se"+ applyPage;
    }

    String getAddress() {
        return address;
    }

    String getDate() {
        return date;
    }

    String getRent() {
        return rent;
    }


    String getApplyPage() {
        return  applyPage;
    }

    @Override
    public String toString() {
        return "Apartment{" + "address=" + address + ", date=" + date + ", city=" + city + ", rent=" + rent +  ", applyPage=" + applyPage + ", livingSpace=" + livingSpace + ", apartmentPage=" + apartmentPage + '}';
    }


    
}
