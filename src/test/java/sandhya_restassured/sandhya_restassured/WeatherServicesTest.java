//Author: Sandhya singireddy
////This test validates the Api

package sandhya_restassured.sandhya_restassured;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import static org.hamcrest.Matchers.equalTo;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.hamcrest.Matchers;
import org.hamcrest.core.Is;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;
import static io.restassured.RestAssured.delete;
import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class WeatherServicesTest {
	
	final String LONDON_URL = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b6907d289e10d714a6e88b30761fae22";
	final String NEWYORK_URL = "http://samples.openweathermap.org/data/2.5/weather?q=NewYork,us&appid=b6907d289e10d714a6e88b30761fae22";
	
	Response response = null;
	Properties props = null;
	
	@BeforeSuite
	public void beforeSuite() {
		response = RestAssured.get(LONDON_URL);
		
		try {
			props = new Properties();
			FileInputStream inputStream = new FileInputStream("src\\test\\java\\sandhya_restassured\\sandhya_restassured\\resources\\WeatherTest.properties");
			props.load(inputStream);

			System.out.println("Weather properties are: " + props);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	@AfterSuite
	public void afterSuite() {
		response = null;
	}
	
	// Status Validation if the API is functional 
	@Test(priority = 1)
	public void checkStatusCode() {
		response.then().assertThat().statusCode(Is.is(Integer.parseInt(props.getProperty("statuscode"))));
	}
	
	//Root level validations
	@Test(priority = 2)
	public void checkID() {
		response.then().assertThat().body("id", Is.is(Integer.parseInt(props.getProperty("id"))));
	}

	@Test(priority = 3)
	public void checkName() {
		response.then().assertThat().body("name", Is.is(props.getProperty("name")));
	}
	
	@Test(priority = 4)
	public void checkCod() {
		response.then().assertThat().body("cod", Is.is(Integer.parseInt(props.getProperty("cod"))));
	}
	@Test(priority = 5)
	public void checkBase() {
		response.then().assertThat().body("base", Is.is(props.getProperty("base")));
		
	}
	
	@Test(priority = 6)
	public void checkVisibility() {
		response.then().assertThat().body("visibility", Is.is(Integer.parseInt(props.getProperty("visibility"))));
	}
	
	@Test(priority = 7)
	public void checkDt() {
		response.then().assertThat().body("dt", Is.is(Integer.parseInt(props.getProperty("dt"))));
	}
	
	// Coordinates level validations
	@Test(priority = 8)
	public void checkCoordLongitude() {
		double actuaLongitude = response.then().extract().jsonPath().getDouble("coord.lon");
		double expectedLongitude = Double.parseDouble(props.getProperty("coord.lon"));
		System.out.println("expectedLatitude:" + expectedLongitude + "actualLatitude: " + actuaLongitude);
		org.testng.Assert.assertTrue(expectedLongitude == actuaLongitude);
	}
	
	@Test(priority = 9)
	public void checkCoordLatitude() {
		double actualLatitude = response.then().extract().jsonPath().getDouble("coord.lat");
		double expectedLatitude = Double.parseDouble(props.getProperty("coord.lat"));
		System.out.println("expectedLatitude:" + expectedLatitude + "actualLatitude: " + actualLatitude);
		org.testng.Assert.assertTrue(expectedLatitude == actualLatitude);
	}
	
	
	// Weather level validations
	@Test(priority = 10)
	public void checkWhethermain() {
		response.then().assertThat().body("weather.main[0]", Is.is(props.getProperty("weather.main")));
	}
		
	
	@Test(priority = 11)
	public void checkWhetherid() {
		response.then().assertThat().body("weather.id[0]", Is.is(Integer.parseInt(props.getProperty("weather.id"))));
		
	}
	
	@Test(priority = 12)
	public void checkWhetherDesc() {
		response.then().assertThat().body("weather.description[0]", Is.is(props.getProperty("weather.description")));
		
	}
	
	@Test(priority = 13)
	public void checkWhetherIcon() {
		response.then().assertThat().body("weather.icon[0]", Is.is(props.getProperty("weather.icon")));
		
	}
	
	// Main level validations
	@Test(priority = 14)
	public void checkMainTemp() {
		double actualMainTemp = response.then().extract().jsonPath().getDouble("main.temp");
		double expectedMainTemp = Double.parseDouble(props.getProperty("main.temp"));
		System.out.println("expectedMainTemp" + expectedMainTemp + "actualMainTemp: " + actualMainTemp);
		org.testng.Assert.assertTrue(actualMainTemp == actualMainTemp);
			
	}
	
	@Test(priority = 15)
	public void checkMainPressure() {
		response.then().assertThat().body("main.pressure", Is.is(Integer.parseInt(props.getProperty("main.pressure"))));
		
	}
	
	@Test(priority = 16)
	public void checkMainHumidity() {
		response.then().assertThat().body("main.humidity", Is.is(Integer.parseInt(props.getProperty("main.humidity"))));
		
	}
	
	@Test(priority = 17)
	public void checkMainTempMin() {
		double actualMainTempMin = response.then().extract().jsonPath().getDouble("main.temp_min");
		double expectedMainTempMin = Double.parseDouble(props.getProperty("main.temp_min"));
		System.out.println("expectedMainTempMin" + expectedMainTempMin + "actualMainTempMin: " + actualMainTempMin);
		org.testng.Assert.assertTrue(expectedMainTempMin == actualMainTempMin);
	}
	
	@Test(priority = 18)
	public void checkMainTempMax() {
		double actualMainTempMax = response.then().extract().jsonPath().getDouble("main.temp_max");
		double expectedMainTempMax = Double.parseDouble(props.getProperty("main.temp_max"));
		System.out.println("expectedMainTempMax" + expectedMainTempMax + "actualMainTempMax: " + actualMainTempMax);
		org.testng.Assert.assertTrue(expectedMainTempMax == actualMainTempMax);
	}
	
	// Wind level validations
	@Test(priority = 19)
	public void checkWindSpeed() {
		double actualWindSpeed = response.then().extract().jsonPath().getDouble("wind.speed");
		double expectedWindSpeed =  Double.parseDouble(props.getProperty("wind.speed"));
		System.out.println("expectedMainTempMin" + expectedWindSpeed + "actualWindSpeed: " + actualWindSpeed);
		org.testng.Assert.assertTrue(expectedWindSpeed == actualWindSpeed);
	}
	
	@Test(priority = 20)
	public void checkWindDeg() {
		response.then().assertThat().body("wind.deg", Is.is(Integer.parseInt(props.getProperty("wind.deg"))));
	}
	
	// Cloud level validations
	@Test(priority = 21)
	public void checkCloudsAll() {
		response.then().assertThat().body("clouds.all", Is.is(Integer.parseInt(props.getProperty("clouds.all"))));
	}
	
	// Sys level validations
	@Test(priority = 22)
	public void checkSysType() {
		response.then().assertThat().body("sys.type", Is.is(Integer.parseInt(props.getProperty("sys.type"))));
	}

	@Test(priority = 23)
	public void checkSysId() {
		response.then().assertThat().body("sys.id", Is.is(Integer.parseInt(props.getProperty("sys.id"))));
	}
	
	@Test(priority = 24)
	public void checkSysMessage() {
		double actualSysMessage = response.then().extract().jsonPath().getDouble("sys.message");
		double expectedSysMessage = Double.parseDouble(props.getProperty("sys.message"));
		System.out.println("expectedSysMessage" + expectedSysMessage + "actualSysMessage: " + actualSysMessage);
		org.testng.Assert.assertTrue(expectedSysMessage == actualSysMessage);
	}
	
	@Test(priority = 25)
	public void checkSysCountry() {
		response.then().assertThat().body("sys.country", Is.is(props.getProperty("sys.country")));
	}
	
	@Test(priority = 26)
	public void checkSysSunrise() {
		response.then().assertThat().body("sys.sunrise", Is.is(Integer.parseInt(props.getProperty("sys.sunrise"))));
	}
	
	@Test(priority = 27)
	public void checkSysSunset() {
		response.then().assertThat().body("sys.sunset", Is.is(Integer.parseInt(props.getProperty("sys.sunset"))));
	}
	
	@Test(priority = 28)
	public void checkNewYorkUrl() {
		Response response = RestAssured.get(NEWYORK_URL);
		response.then().assertThat().body("name", Is.is(props.getProperty("newyork")));
	}
	
	
}
