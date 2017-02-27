package box.chronos.userk.chronos.Objects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by ChronosTeam on 27/02/2017.
 */

public class Article {
    private String id;

    private String title;
    private String description;
    private String shop;

    private String shopId;
    private String shopChain;
    private String price;
    private String category;
    private List<String> genres;
    private Article[] Related;
    private String[] tags;


    Article () {}


    Article(String id, String title,String category){
        //this.category = new ArrayList<String>();
        this.id = id;
        this.title = title;
        this.category = category;
    }


    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getShopChain() {
        return shopChain;
    }

    public void setShopChain(String shopChain) {
        this.shopChain = shopChain;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public  String  getCategories() {
        return category;
    }

    public void setCategories( String  categories) {
        this.category = categories;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public Article[] getRelated() {
        return Related;
    }

    public void setRelated(Article[] related) {
        Related = related;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }
}
