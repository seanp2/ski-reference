package com.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {


	@Test
	public void dateAsTest() {

		assertEquals("03-04-2017", Date.monthAsLetters("April 03, 2017").toString());

	}

}