package kiwi.castle.avick.com.kiwiassignment.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import io.realm.RealmObject;

/**
 * Created by avick on 10/28/16.
 */

public class RealmRestaurant extends RealmObject {

    private String id;
    private String name;
    private String url;
    private String averageCostForTwo;
    private String priceRange;
    private String currency;
    private String thumb;
    private String featuredImage;
    private String photosUrl;
    private String menuUrl;
    private String rating;
    private String eventsUrl;
    private String hasOnlineDelivery;
    private String isDeliveringNow;
    private String hasTableBooking;
    private String deeplink;
    private String cuisines;
    private String allReviewsCount;
    private String photoCount;
    private String phoneNumbers;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAverageCostForTwo() {
        return averageCostForTwo;
    }

    public void setAverageCostForTwo(String averageCostForTwo) {
        this.averageCostForTwo = averageCostForTwo;
    }

    public String getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(String priceRange) {
        this.priceRange = priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public void setPhotosUrl(String photosUrl) {
        this.photosUrl = photosUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getEventsUrl() {
        return eventsUrl;
    }

    public void setEventsUrl(String eventsUrl) {
        this.eventsUrl = eventsUrl;
    }

    public String getHasOnlineDelivery() {
        return hasOnlineDelivery;
    }

    public void setHasOnlineDelivery(String hasOnlineDelivery) {
        this.hasOnlineDelivery = hasOnlineDelivery;
    }

    public String getIsDeliveringNow() {
        return isDeliveringNow;
    }

    public void setIsDeliveringNow(String isDeliveringNow) {
        this.isDeliveringNow = isDeliveringNow;
    }

    public String getHasTableBooking() {
        return hasTableBooking;
    }

    public void setHasTableBooking(String hasTableBooking) {
        this.hasTableBooking = hasTableBooking;
    }

    public String getDeeplink() {
        return deeplink;
    }

    public void setDeeplink(String deeplink) {
        this.deeplink = deeplink;
    }

    public String getCuisines() {
        return cuisines;
    }

    public void setCuisines(String cuisines) {
        this.cuisines = cuisines;
    }

    public String getAllReviewsCount() {
        return allReviewsCount;
    }

    public void setAllReviewsCount(String allReviewsCount) {
        this.allReviewsCount = allReviewsCount;
    }

    public String getPhotoCount() {
        return photoCount;
    }

    public void setPhotoCount(String photoCount) {
        this.photoCount = photoCount;
    }

    public String getPhoneNumbers() {
        return phoneNumbers;
    }

    public void setPhoneNumbers(String phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }
}
