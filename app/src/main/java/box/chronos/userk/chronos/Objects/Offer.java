package box.chronos.userk.chronos.Objects;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ChronosTeam on 27/02/2017.
 */

public class Offer extends Article implements Parcelable {
    private String id_offer;
    private String from;
    private String to;
    private String timeout;
    private int drawable_thumb;

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
    }

    public static final Parcelable.Creator CREATOR  = new Parcelable.Creator() {
        public Offer createFromParcel(Parcel source) {
            return new Offer(source);
        }

        public Offer[] newArray(int size) {
            return new Offer[size];
        }
    };
}
