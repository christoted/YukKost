package com.example.ngekostkuy.Model;

public class BookingTransaction {
    private String BookingId;
    private String UserId;
    private String KostName;
    private String KostFacility;
    private String KostPrice;
    private String KostDesc;
    private String KostLongitude;
    private String KostLatitude;
    private String BookingDate;


    public String getBookingId() {
        return BookingId;
    }

    public void setBookingId(String bookingId) {
        BookingId = bookingId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getKostName() {
        return KostName;
    }

    public void setKostName(String kostName) {
        KostName = kostName;
    }

    public String getKostFacility() {
        return KostFacility;
    }

    public void setKostFacility(String kostFacility) {
        KostFacility = kostFacility;
    }

    public String getKostPrice() {
        return KostPrice;
    }

    public void setKostPrice(String kostPrice) {
        KostPrice = kostPrice;
    }

    public String getKostDesc() {
        return KostDesc;
    }

    public void setKostDesc(String kostDesc) {
        KostDesc = kostDesc;
    }

    public String getKostLongitude() {
        return KostLongitude;
    }

    public void setKostLongitude(String kostLongitude) {
        KostLongitude = kostLongitude;
    }

    public String getKostLatitude() {
        return KostLatitude;
    }

    public void setKostLatitude(String kostLatitude) {
        KostLatitude = kostLatitude;
    }

    public String getBookingDate() {
        return BookingDate;
    }

    public void setBookingDate(String bookingDate) {
        BookingDate = bookingDate;
    }
}
