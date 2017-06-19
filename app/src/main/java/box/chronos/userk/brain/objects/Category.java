package box.chronos.userk.brain.objects;

/**
 * Created by userk on 03/05/17.
 */

public class Category {
    private String cat_id;
    private String cat_name;
    private String cat_photo;
    private String cat_photo_active;
    private String is_selected;
    private String count;

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_photo() {
        return cat_photo;
    }

    public void setCat_photo(String cat_photo) {
        this.cat_photo = cat_photo;
    }

    public String getCat_photo_active() {
        return cat_photo_active;
    }

    public void setCat_photo_active(String cat_photo_active) {
        this.cat_photo_active = cat_photo_active;
    }

    public String getIs_selected() {
        return is_selected;
    }

    public void setIs_selected(String is_selected) {
        this.is_selected = is_selected;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
