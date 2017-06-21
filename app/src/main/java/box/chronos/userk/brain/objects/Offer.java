package box.chronos.userk.brain.objects;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;

import static box.chronos.userk.brain.utils.algebra.MathUtils.fixFloatFormat;

/**
 * Created by ChronosTeam on 27/02/2017.
 */

public class Offer extends Article implements Parcelable {
    private String id_offer;
    private String offer_name;

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }

    private String from;
    private String to;
    private String timeout;
    private String discount;
    private int drawable_thumb;
    private boolean isChecked;

    String category;
    String categoryid;
    String categoryphoto;
    String photoactive;
    String businessname;
    String businessphone;
    String businessaddress;
    String offerdescription;
    String latitude;
    String longitude;
    String distance;
    String price;
    int progressValue;
    int isDummy;

    public Offer(){
        super();
        this.availablePictures = new HashMap<>();
        this.isDummy = 0;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public String getCategory() { return category; }

    @Override
    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryphoto() {
        return categoryphoto;
    }

    public void setCategoryphoto(String categoryphoto) {
        this.categoryphoto = categoryphoto;
    }

    public String getPhotoactive() {
        return photoactive;
    }

    public void setPhotoactive(String photoactive) {
        this.photoactive = photoactive;
    }

    public String getBusinessname() {
        return businessname;
    }

    public void setBusinessname(String businessname) {
        this.businessname = businessname;
    }

    public String getBusinessphone() {
        return businessphone;
    }

    public void setBusinessphone(String businessphone) {
        this.businessphone = businessphone;
    }

    public String getBusinessaddress() {
        return businessaddress;
    }

    public void setBusinessaddress(String businessaddress) {
        this.businessaddress = businessaddress;
    }

    public String getOfferdescription() {
        return offerdescription;
    }

    public void setOfferdescription(String offerdescription) {
        this.offerdescription = offerdescription;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String getPrice() {
        return fixFloatFormat(price);
    }

    @Override
    public void setPrice(String price) {
        this.price = price;
    }

    public int getProgressValue() {
        return progressValue;
    }

    public void setProgressValue(int progressValue) {
        this.progressValue = progressValue;
    }


    public Offer(String id, String title, int d, String cat) {
        super(id,title,cat);
        this.drawable_thumb = d;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getId_offer() {
        return id_offer;
    }

    public void setId_offer(String id_offer) {
        this.id_offer = id_offer;
    }

    private String status;

    public int getDrawable_thumb() {
        return drawable_thumb;
    }

    public void setDrawable_thumb(int drawable_thumb) {
        this.drawable_thumb = drawable_thumb;
    }


    public Offer(Parcel parcel) {
        this.id_offer = parcel.readString();
        this.from = parcel.readString();
        this.to = parcel.readString();
        this.timeout = parcel.readString();
        super.setTitle(parcel.readString());
        super.setDescription(parcel.readString());
        super.setCategories(parcel.readString());
        this.drawable_thumb = Integer.parseInt(parcel.readString());
        this.setPrice(parcel.readString());
        this.discount = parcel.readString();
        this.setShopId(parcel.readString());
        this.category = parcel.readString();
        this.categoryid = parcel.readString();
        this.categoryphoto = parcel.readString();
        this.photoactive = parcel.readString();
        this.businessname = parcel.readString();
        this.businessphone = parcel.readString();
        this.businessaddress = parcel.readString();
        this.offerdescription = parcel.readString();
        this.latitude = parcel.readString();
        this.longitude = parcel.readString();
        this.distance = parcel.readString();
        this.isDummy = parcel.readInt();

        //Bundle b = parcel.readBundle();
        //if(b != null) {
         //   HashMap<String,String> a  =  (HashMap<String,String>) b.getSerializable("HashMap");
          //  this.availablePictures = a;
        //}

    }


    private HashMap<String,String> availablePictures;

    public HashMap<String, String> getAvailablePictures() {
        return availablePictures;
    }

    public void setAvailablePictures(HashMap<String, String> availablePictures) {
        this.availablePictures = availablePictures;
    }

    public boolean hasPicture() {
        boolean result = false;

        if (this.availablePictures != null && this.availablePictures.size()>0)
            result = true;

        return result;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id_offer);
        parcel.writeString(from);
        parcel.writeString(to);
        parcel.writeString(timeout);
        parcel.writeString(super.getTitle());
        parcel.writeString(super.getDescription());
        parcel.writeString(super.getCategories());
        parcel.writeString(String.valueOf(this.drawable_thumb));
        parcel.writeString(this.getPrice());
        parcel.writeString(discount);
        parcel.writeString(super.getShopId());
        parcel.writeString(this.getCategory());
        parcel.writeString(this.getCategoryid());
        parcel.writeString(this.getCategoryphoto());
        parcel.writeString(this.getPhotoactive());
        parcel.writeString(this.getBusinessname());
        parcel.writeString(this.getBusinessphone());
        parcel.writeString(this.getBusinessaddress());
        parcel.writeString(this.getOfferdescription());
        parcel.writeString(this.getLatitude());
        parcel.writeString(this.getLongitude());
        parcel.writeString(this.getDistance());
        parcel.writeInt(this.isDummy());
        //Bundle extras = new Bundle();
        //    extras.putSerializable("HashMap",this.getAvailablePictures());
        //parcel.writeBundle(extras);
    }

    public static final Parcelable.Creator CREATOR  = new Parcelable.Creator() {
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };

    public String getDiscount() {
        return fixFloatFormat(discount);
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }


    public int isDummy() {
        return this.isDummy;
    }

    public void setDummy(int dummy) {
        this.isDummy = dummy;
    }
}
