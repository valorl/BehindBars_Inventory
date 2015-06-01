package ControlLayer;

import static org.junit.Assert.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import DBLayer.DBProduct;
import DBLayer.DBWeek;
import ModelLayer.ItemResult;
import ModelLayer.Measurable;
import ModelLayer.ProductState;
import ModelLayer.QuantLoc;
import ModelLayer.Week;
import ModelLayer.WeekResult;

public class ResultsTest {

	DBWeek dbWeek;
	InventoryCtr invCtr;
	Week weekA;
	Week weekB;
	Measurable mes;

	SimpleDateFormat df;

	@Before
	public void setUp() {
		dbWeek = new DBWeek();
		invCtr = new InventoryCtr();

		df = new SimpleDateFormat("dd.MM.yyyy");


		DBProduct dbProd = new DBProduct();
		try {
			mes = (Measurable) dbProd.findProduct(36, true);
		} catch (Exception e) {
			e.printStackTrace();
		}

		weekA = new Week();
		weekA.setNumber(1);
		weekA.setMonth(1);
		weekA.setYear(2015);


		weekB = new Week();
	}

	@Test
	public void testCase1() {
		weekB.setNumber(2);
		weekB.setMonth(1);
		weekB.setYear(2015);

		ProductState stateA = new ProductState();
		stateA.setProduct(mes);
		QuantLoc qlA = new QuantLoc(500, "storage");
		stateA.addQuantLoc(qlA);
		weekA.addState(stateA);
		
		ProductState stateB = new ProductState();
		stateB.setProduct(mes);
		QuantLoc qlB = new QuantLoc(300,"storage");
		stateB.addQuantLoc(qlB);
		stateB.setCurrentPrice(20);
		weekB.addState(stateB);
		try {
			dbWeek.insertWeek(weekA);
			dbWeek.insertWeek(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date = null;
		try {
			date = df.parse("05.01.2015"); //  Translates to Week n.2 in 2015
		}
		catch(ParseException ex) {
			ex.printStackTrace();
		}
		WeekResult wr = null;
		try {
			wr = invCtr.getResults(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ItemResult result = wr.getResults().get(0);
		// DB cleanup
		try {
			dbWeek.delete(weekA);
			dbWeek.delete(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(-200,result.getVariance(),1e-6); //Check variance
		assertEquals(-4000,result.getRevenue(),1e-6); //Check revenue
	}
	
	@Test
	public void testCase2() {
		weekB.setNumber(2);
		weekB.setMonth(1);
		weekB.setYear(2015);

		ProductState stateA = new ProductState();
		stateA.setProduct(mes);
		QuantLoc qlA = new QuantLoc(300, "storage");
		stateA.addQuantLoc(qlA);
		weekA.addState(stateA);
		
		ProductState stateB = new ProductState();
		stateB.setProduct(mes);
		QuantLoc qlB = new QuantLoc(500,"storage");
		stateB.addQuantLoc(qlB);
		stateB.setCurrentPrice(20);
		weekB.addState(stateB);
		try {
			dbWeek.insertWeek(weekA);
			dbWeek.insertWeek(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date = null;
		try {
			date = df.parse("05.01.2015"); //  Translates to Week n.2 in 2015
		}
		catch(ParseException ex) {
			ex.printStackTrace();
		}
		WeekResult wr = null;
		try {
			wr = invCtr.getResults(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ItemResult result = wr.getResults().get(0);
		// DB cleanup
		try {
			dbWeek.delete(weekA);
			dbWeek.delete(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(200,result.getVariance(),1e-6); //Check variance
		assertEquals(4000,result.getRevenue(),1e-6); //Check revenue
	}
	
	@Test
	public void testCase3() {
		weekB.setNumber(2);
		weekB.setMonth(1);
		weekB.setYear(2015);

		ProductState stateA = new ProductState();
		stateA.setProduct(mes);
		QuantLoc qlA = new QuantLoc(500, "storage");
		stateA.addQuantLoc(qlA);
		weekA.addState(stateA);
		
		ProductState stateB = new ProductState();
		stateB.setProduct(mes);
		QuantLoc qlB = new QuantLoc(300,"storage");
		stateB.addQuantLoc(qlB);
		stateB.setCurrentPrice(20);
		weekB.addState(stateB);
		try {
			dbWeek.insertWeek(weekA);
			dbWeek.insertWeek(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date = null;
		try {
			date = df.parse("12.01.2015"); //  Translates to Week n.2 in 2015
		}
		catch(ParseException ex) {
			ex.printStackTrace();
		}
		WeekResult wr = null;
		try {
			wr = invCtr.getResults(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// DB cleanup
		try {
			dbWeek.delete(weekA);
			dbWeek.delete(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertNull(wr);
		
	}
	
	@Test
	public void testCase4() {
		weekB.setNumber(2);
		weekB.setMonth(1);
		weekB.setYear(2015);

		mes.setPrice(0);
		ProductState stateA = new ProductState();
		stateA.setProduct(mes);
		QuantLoc qlA = new QuantLoc(500, "storage");
		stateA.addQuantLoc(qlA);
		weekA.addState(stateA);
		
		ProductState stateB = new ProductState();
		stateB.setProduct(mes);
		QuantLoc qlB = new QuantLoc(300,"storage");
		stateB.addQuantLoc(qlB);
		stateB.setCurrentPrice(0);
		weekB.addState(stateB);
		try {
			dbWeek.insertWeek(weekA);
			dbWeek.insertWeek(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Date date = null;
		try {
			date = df.parse("05.01.2015"); //  Translates to Week n.2 in 2015
		}
		catch(ParseException ex) {
			ex.printStackTrace();
		}
		WeekResult wr = null;
		try {
			wr = invCtr.getResults(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		ItemResult result = wr.getResults().get(0);
		// DB cleanup
		try {
			dbWeek.delete(weekA);
			dbWeek.delete(weekB);
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertEquals(-200,result.getVariance(),1e-6); //Check variance
		assertEquals(0,result.getRevenue(),1e-6); //Check revenue
	}
	
	
}
