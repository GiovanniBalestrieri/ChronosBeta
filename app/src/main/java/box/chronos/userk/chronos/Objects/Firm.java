package box.chronos.userk.chronos.Objects;

import java.util.List;

/**
 * Created by ChronosTeam on 27/02/2017.
 */

public class Firm {
    private String id;
    private String name;
    private String pIva;
    private String mail;
    private String location;
    private List<Shop> succursali;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

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

    public String getpIva() {
        return pIva;
    }

    public void setpIva(String pIva) {
        this.pIva = pIva;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Shop> getSuccursali() {
        return succursali;
    }

    public void setSuccursali(List<Shop> succursali) {
        this.succursali = succursali;
    }
}
