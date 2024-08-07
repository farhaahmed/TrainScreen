package ca.ucalgary.ensf380;

public class Advertisement {
	//Private variables to hold properties of an advertisement
	private int id; //Unique identifier for the ad
    private String adName; //Name of ad
    private String adType; //Type of ad (PDF, MPG, JPEG, BMP)
    private String adFile; //Binary content of the ad file

    Advertisement() {};
    
    // Public Advertisements Other Constructor
    Advertisement(int id, String anName, String adType, String adFile) {
    	this.setId(id);
    	this.setAdName(adName);
    	this.setAdType(adType);
    	this.setAdFile(adFile);
    }
    
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

    public String getAdFile() {
        return adFile; //Returns value of 'adFile' as a byte array
    }

    public void setAdFile(String adFile) {
        this.adFile = adFile; // Sets value of 'adFile' as a byte array
    }
}