package madiyarzhenis.kz.universityguide.models;

/**
 * Created by Zhenis Madiyar on 8/13/2015.
 */
public class University {
    String name;
    String objectId;
    String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public University(String name, String objectId, String imageUrl) {
        this.name = name;

        this.objectId = objectId;
        this.imageUrl = imageUrl;
    }
}
