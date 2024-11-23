package Temp;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.shirtstore.entity.Rate;
import com.shirtstore.entity.Shirt;

public class ShirtRatingTest {

	@Test
	public void testRatingStars1() {
		Shirt shirt = new Shirt();

		Set<Rate>listRates = new HashSet<Rate>();
		Rate rate1 = new Rate();
		rate1.setRatingStars(5);
		listRates.add(rate1);

		shirt.setRates(listRates);

		float averageRatingStars = shirt.getAverageRatingStars();

		assertEquals(5.0, averageRatingStars, 0.0);
	}

	@Test
	public void testRatingStars2() {
		Shirt shirt = new Shirt();
		float averageRatingStars = shirt.getAverageRatingStars();

		assertEquals(0.0, averageRatingStars, 0.0);
	}

	@Test
	public void testRatingStars3() {
		Shirt shirt = new Shirt();

		Set<Rate>listRates = new HashSet<Rate>();
		Rate rate1 = new Rate();
		rate1.setRatingStars(5);
		listRates.add(rate1);

		Rate rate2 = new Rate();
		rate2.setRatingStars(4);
		listRates.add(rate2);

		Rate rate3 = new Rate();
		rate3.setRatingStars(3);
		listRates.add(rate3);

		shirt.setRates(listRates);

		float averageRatingStars = shirt.getAverageRatingStars();

		assertEquals(4.0, averageRatingStars, 0.0);
	}

	@Test
	public void testRatingString1() {
		float averageRating = 0.0f;
		Shirt shirt = new Shirt();

		String actual = shirt.getRatingString(averageRating);
		String expected = "off,off,off,off,off";

		assertEquals(expected, actual);
	}

	@Test
	public void testRatingString2() {
		float averageRating = 5.0f;
		Shirt shirt = new Shirt();

		String actual = shirt.getRatingString(averageRating);
		String expected = "on,on,on,on,on";

		assertEquals(expected, actual);
	}

	@Test
	public void testRatingString3() {
		float averageRating = 3.0f;
		Shirt shirt = new Shirt();

		String actual = shirt.getRatingString(averageRating);
		String expected = "on,on,on,off,off";

		assertEquals(expected, actual);
	}

	@Test
	public void testRatingString4() {
		float averageRating = 4.5f;
		Shirt shirt = new Shirt();

		String actual = shirt.getRatingString(averageRating);
		String expected = "on,on,on,on,half";

		assertEquals(expected, actual);
	}

	@Test
	public void testRatingString5() {
		float averageRating = 2.4f;
		Shirt shirt = new Shirt();

		String actual = shirt.getRatingString(averageRating);
		String expected = "on,on,half,off,off";

		assertEquals(expected, actual);
	}
}
