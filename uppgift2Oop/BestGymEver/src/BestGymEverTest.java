import static org.junit.Assert.*;
import java.io.IOException;
import java.time.LocalDate;
import org.junit.Test;
import junit.framework.TestCase;

public class BestGymEverTest {

	
	
	@Test
	public void comparingArrayFromInputTest() throws IOException  {
		BestGymEver testing = new BestGymEver();
		String[] arrayName = null;
		//arrayName[0] = "bear belle";
		String inputFromUser = "bear belle";
	
		assertTrue(testing.comparingArrayFromInput(arrayName, inputFromUser));
        assertFalse(testing.comparingArrayFromInput(arrayName, inputFromUser));
	}
	
	@Test
	public void concatenateTwoStringsTest() throws IOException {
		BestGymEver testing = new BestGymEver();
		String result = testing.concatenateTwoStrings("one", "two");
		assertEquals("onetwo", result);
	}
	
	@Test
	public void isDateAYearFromNowTest() throws IOException {
		BestGymEver testing = new BestGymEver();
		//LocalDate formatedDate = LocalDate.parse("2017-01-12");
		LocalDate todaysDate = LocalDate.now();
		LocalDate aYearFromNow = todaysDate.minusYears(1);
		assertTrue(testing.isDateAYearFromNow(aYearFromNow));
		
	}
	
	@Test
	public void felMeddelandeTest() throws IOException {
		BestGymEver testing = new BestGymEver();
		testing.felMeddelande();
	}

}
