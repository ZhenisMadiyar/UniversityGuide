package madiyarzhenis.kz.universityguide.list_of_university.adapters;

/**
 * Created by Justin on 2/2/14.
 */
public class CardItemData {
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

	public CardItemData(String name, String objectId, String imageUrl) {
		this.name = name;

		this.objectId = objectId;
		this.imageUrl = imageUrl;
	}
}
