package com.biography;


import com.updatedb.DBconnection;
import com.results.AthleteUtils;
import com.updatedb.Athlete;
import org.junit.Test;

import java.io.IOException;
import java.sql.*;

import static org.junit.Assert.*;

public class AthleteTest {





	@Test
	public void test2() {
		System.out.println("//////////");

		try {
			Athlete ted = new Athlete(138136);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void test3() {
		Connection con = new DBconnection().connect();
		try {
			Statement statement = con.createStatement();
			ResultSet rs = null;
			String query = "SELECT SLhigh, SLlow, SLpoints FROM fispoints262" +";";

			rs = statement.executeQuery(query);
			int count = 0;
			int total = 0;
			int relevant = 0;
			while (rs.next()) {
				total++;
				double high = Double.parseDouble(rs.getString("SLhigh"));
				double low = Double.parseDouble(rs.getString("SLlow"));
				String points = rs.getString("SLpoints");
				if (!points.equals("NA") && high != 990.0 && high != low) {
					relevant++;
					double numPoints = Double.parseDouble(points);
					double avg = (high + low) / 2;
//					(Math.abs(avg - numPoints) < 1e-0)
					if ((Math.abs(avg - numPoints) > 0.02)) {

						System.out.println(total);
					} else {
						count++;
					}
				}
			}
			System.out.println("Count " + ": " + count); //7449
			System.out.println("Relevant " +  ": " +relevant);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void  test31() {
		try {
			Athlete mufuckin = new Athlete(Integer.parseInt("172438"));
//			Athlete mufuckin = new Athlete(172438);
		} catch (IOException e) {
			System.out.println("FUCK");
			e.printStackTrace();
		}
	}

	@Test
	public void  test32() {
		try {
			Athlete mufuckin = new Athlete(156292);
//			Athlete mufuckin = new Athlete(172438);
		} catch (IOException e) {
			System.out.println("FUCK");
			e.printStackTrace();
		}
	}

	@Test
	public void  test4() {
		try {
			Athlete mufuckin = new Athlete(422705);
			Double[] hilo = mufuckin.getPointsMadeWith(248, 1,
					new Double[]{97.55, 82.34}, "SL", new AthleteUtils());
			assertEquals(97.55, hilo[0], .01);
		} catch (IOException e) {
			System.out.println("FUCK");
			e.printStackTrace();
		}
	}


	@Test
	public void  test6() {
		try {
			Athlete mufuckin = new Athlete(194652);
			Double[] hilo = mufuckin.getPointsMadeWith(247, 1,
					new Double[]{48.94, 39.79}, "SL", new AthleteUtils());
			assertEquals(97.55, hilo[1], .01);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	@Test
	public void  test7() {
		try {
			Athlete mufuckin = new Athlete(163601);
			Double[] hilo = mufuckin.getPointsMadeWith(235, 1,
					new Double[]{85.59, 85.59}, "SL", new AthleteUtils());
			assertEquals(80.09, hilo[1], .01);
			assertEquals(88.28, hilo[0], .01);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void  test8() {
		try {
			Athlete mufuckin = new Athlete(194696);
			Double[] hilo = mufuckin.getPointsMadeWith(233, 1,
					new Double[]{98.74, 98.74}, "SL", new AthleteUtils());
			assertEquals(91.61, hilo[1], .01);
			assertEquals(97.20 , hilo[0], .01);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test9() {
		try {
			Athlete bertram = new Athlete(187642);
			Double[] hilo = bertram.getMakeUpAfterIncrease(252,  14, "GS",
					new AthleteUtils());
			assertEquals(54.29, hilo[0], 0.01);
			assertEquals(52.00 , hilo[1], 0.01);


		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test10() {
		try {
			Athlete bertram = new Athlete(187642);
			Double[] hilo = bertram.getMakeUpAfterIncrease(252,  14, "SL",
					new AthleteUtils());
			assertEquals(55.64, hilo[0], 0.01);
			assertEquals(47.16 , hilo[1], 0.01);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test11() {
		try {
			Athlete bertram = new Athlete(173323);
			Double[] hilo = bertram.getMakeUpAfterIncrease(211,  13, "SL",
					new AthleteUtils());
			assertEquals(55.64, hilo[0], 0.01);
			assertEquals(47.16 , hilo[1], 0.01);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	@Test
	public void  test5() throws IOException {
			Athlete mufuckin = new Athlete(2000000000);
	}



}