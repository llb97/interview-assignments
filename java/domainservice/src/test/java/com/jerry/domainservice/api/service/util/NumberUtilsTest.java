package com.jerry.domainservice.api.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class NumberUtilsTest {

	@Test
	void testConvert() {
		Assertions.assertEquals(NumberUtils.convert(7).length(), 7);
		Assertions.assertEquals(NumberUtils.convert(8).length(), 8);
	}

}
