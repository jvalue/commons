package org.jvalue.commons.utils;


import mockit.Mock;
import mockit.MockUp;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JMockit.class)
public class HttpServiceCheckTest {

	@Tested
	HttpServiceCheck mock;

	@Test
	public void testCheckSuccess() {

		new MockUp<HttpServiceCheck>() {
			@Mock
			private boolean isReachable(String url) {
				return true;
			}
		};

		Assert.assertTrue(mock.check("http://example.com", 0, 1));
	}


	@Test
	public void testCheckFailure() {

		new MockUp<HttpServiceCheck>() {
			@Mock
			private boolean isReachable(String url) {
				return false;
			}
		};

		Assert.assertFalse(mock.check("http://example.com", 0 , 1), "Should fail");
	}


	@Test
	public void testRetries() {

		new MockUp<HttpServiceCheck>() {
			private int counter = 0;

			@Mock
			private boolean isReachable(String url) {
				counter++;
				return counter > 3;
			}
		};

		boolean resultFail = mock.check("http://example.com", 0, 1);
		boolean resultSuccess = mock.check("http://example.com", 3, 1);

		Assert.assertFalse(resultFail, "Should fail");
		Assert.assertTrue(resultSuccess);
	}
}
