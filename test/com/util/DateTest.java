package com.util;

import org.junit.Test;

import static org.junit.Assert.*;

public class DateTest {


	@Test
	public void dateAsTest() {
		assertEquals("", Date.monthAsLetters("April 03, 2017").toString());
	}

}