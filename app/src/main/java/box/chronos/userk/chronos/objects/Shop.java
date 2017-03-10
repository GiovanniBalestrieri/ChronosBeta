package box.chronos.userk.chronos.objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ChronosTeam on 27/02/2017.
 */

public class Shop extends Firm  implements Parcelable {
    private String shopId;

    private String shopName;
    private String shopAddress;
    private String shopLat;
    private String shopLon;
    private String shopMail;
    private String shopDescription;
    private int shopThumbnailProfile;
    private int shopThumbnailWallpaper;
    private String shopWallpaper;
    private String shopProfilePicture;

    public int getShopThumbnailProfile() {
        return shopThumbnailProfile;
    }

    public void setShopThumbnailProfile(int shopThumbnailProfile) {
        this.shopThumbnailProfile = shopThumbnailProfile;
    }

    public int getShopThumbnailWallpaper() {
        return shopThumbnailWallpaper;
    }

    public void setShopThumbnailWallpaper(int shopThumbnailWallpaper) {
        this.shopThumbnailWallpaper = shopThumbnailWallpaper;
    }

    public String getShopWallpaper() {
        return shopWallpaper;
    }

    public void setShopWallpaper(String shopWallpaper) {
        this.shopWallpaper = shopWallpaper;
    }

    public String getShopProfilePicture() {
        return shopProfilePicture;
    }

    public void setShopProfilePicture(String shopProfilePicture) {
        this.shopProfilePicture = shopProfilePicture;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public Shop(String shopId, String shopName, String shopAddress, String shopDescription) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.shopAddress = shopAddress;
        this.shopDescription = shopDescription;
    }

    public String getShopAddress() {
        return shopAddress;
    }

    public void setShopAddress(String shopAddress) {
        this.shopAddress = shopAddress;
    }

    public String getShopLat() {
        return shopLat;
    }

    public void setShopLat(String shopLoat) {
        this.shopLat = shopLat;
    }

    public String getShopLon() {
        return shopLon;
    }

    public void setShopLon(String shopLon) {
        this.shopLon = shopLon;
    }

    public String getShopMail() {
        return shopMail;
    }

    public void setShopMail(String shopMail) {
        this.shopMail = shopMail;
    }

    public String getShopDescription() {
        return shopDescription;
    }

    public void setShopDescription(String shopDescription) {
        this.shopDescription = shopDescription;
    }


    public static final Parcelable.Creator CREATOR  = new Parcelable.Creator() {
        public Shop createFromParcel(Parcel source) {
            return new Shop(source);
        }
        public Shop[] newArray(int size) {
            return new Shop[size];
        }
    };

    public Shop(Parcel parcel) {
        this.shopId = parcel.readString();
        this.shopAddress = parcel.readString();
        this.shopDescription = parcel.readString();
        this.shopLat = parcel.readString();
        this.shopLon = parcel.readString();
        this.shopName = parcel.readString();
        this.shopThumbnailProfile = Integer.parseInt(parcel.readString());
        this.shopThumbnailWallpaper = Integer.parseInt(parcel.readString());
        this.shopWallpaper = parcel.readString();
        this.shopProfilePicture = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.shopId);
        parcel.writeString(this.shopAddress);
        parcel.writeString(this.shopDescription);
        parcel.writeString(this.shopLat);
        parcel.writeString(this.shopLon);
        parcel.writeString(this.shopName);
        parcel.writeString(String.valueOf(this.shopThumbnailProfile));
        parcel.writeString(String.valueOf(this.shopThumbnailWallpaper));
        parcel.writeString(this.shopWallpaper);
        parcel.writeString(this.shopProfilePicture);
    }
}
