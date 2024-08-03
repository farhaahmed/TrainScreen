package ca.ucalgary.ensf380;

public class Advertisement {
	//Private variables to hold properties of an advertisement
	private int id; //Unique identifier for the ad
    private String adName; //Name of ad
    private String adType; //Type of ad (PDF, MPG, JPEG, BMP)
    private byte[] adFile; //Binary content of the ad file

    //Getter method for the 'id' property
    public int getId() {
        return id; //Returns value of id
    }

    //Setter method 'id'
    public void setId(int id) {
        this.id = id; //Sets value of id
    }

    public String getAdName() {
        return adName; 
    }

    public void setAdName(String adName) {
        this.adName = adName; 
    }

    public String getAdType() {
        return adType; 
    }

    public void setAdType(String adType) {
        this.adType = adType;
    }

    public byte[] getAdFile() {
        return adFile; //Returns value of 'adFile' as a byte array
    }

    public void setAdFile(byte[] adFile) {
        this.adFile = adFile; // Sets value of 'adFile' as a byte array
    }
}