package ca.ucalgary.ensf380;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class WeatherFetcherTest {
	@Test
	public void testSuccessfulFetch() {
		try {
			ArrayList<String> testData = WeatherFetcher.fetchWeather("Calgary", "CA");
			assertFalse("method should not return empty arraylist: ", testData.isEmpty());
			assertNotNull("ArrayList should not simply be null entries.", testData);
		} catch(Exception c) {
			fail("Exception should not be thrown here.");
		}
	}
	
	@Test
	public void testInvalidCity() {
		try {
			WeatherFetcher.fetchWeather("NotAValidCity", "CA");
			fail("Exception must be thrown before this point.");//if no exception thrown, then fail method is triggered.
		} catch(Exception c) {
			assertTrue("message must also be given saying'No Weather Data found for the given City'", c.getMessage().contains("No Weather Data found for the given City"));
		}
	}
	
	@Test
    public void testFetchWeatherEmptyCity() {
        try {
        	//empty city does not generate 404, so no exception should be thrown.
        	ArrayList<String> testData = WeatherFetcher.fetchWeather("", "CA");
            assertFalse("method should not return empty arraylist: ", testData.isEmpty());
			assertNotNull("ArrayList should not simply be null entries.", testData);
        } catch (Exception e) {
            fail("no exception should be thrown here");
        }
    }
	
	@Test
    public void testFetchWeatherEmptyCountry() {
        try {
        	//empty country does not generate 404, so no exception should be thrown.
        	ArrayList<String> testData = WeatherFetcher.fetchWeather("Tokyo", "");
            assertFalse("method should not return empty arraylist: ", testData.isEmpty());
			assertNotNull("ArrayList should not simply be null entries.", testData);
        } catch (Exception e) {
            fail("no exception should be thrown here");
        }
    }
}
