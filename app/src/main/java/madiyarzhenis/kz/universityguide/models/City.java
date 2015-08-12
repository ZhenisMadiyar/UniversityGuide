package madiyarzhenis.kz.universityguide.models;

/**
 * Created by Zhenis Madiyar on 8/12/2015.
 */
public class City {
    String name;
    String imageUrl;
    String objectId;

    public City(String name, String imageUrl, String objectId) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.objectId = objectId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
}
