package calendar;


import no.hal.jex.runtime.JExercise;
import junit.framework.TestCase;
import no.hal.jex.standalone.JexStandalone;


public class PersonTest extends TestCase {

	private Person person;

	@JExercise(
			description="A Person must contain a name, an IDnumber, an email."
		)
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		person = new Person("Kevin Ofstad", 1);
	}
	
	
	private void testExceptionAndValue(Exception e, Class<? extends Exception> c, Object expected, Object actual) {
		assertTrue(e + " should be assignable to " + c, c.isAssignableFrom(e.getClass()));
		assertEquals(expected, actual);
	}
	
	public void testSetName() {
		String name = person.getName();
		testInvalidName("Ola", name);
		testInvalidName("O N", name);
		testInvalidName("O. Nordmann", name);
		try {
			person.setName("Espen Askeladd");
			assertEquals("Espen Askeladd", person.getName());
		} catch (Exception e) {
			fail("Espen Askeladd is a valid name");
		}
	}

	private void testInvalidName(String invalidName, String existingName) {
		try {
			person.setName(invalidName);
			fail(invalidName + " is an invalid name");
		} catch (Exception e) {
			testExceptionAndValue(e, IllegalArgumentException.class, existingName, person.getName());			
		}
	}

	//public void testSetEmail() {
		//person.setName("Ola Nordmann");
		//String email = person.getEmail();
		//testInvalidEmail("ola.nordmann@ntnu", email, IllegalArgumentException.class);
		//testInvalidEmail("ola.nordmann(at)ntnu.no", email, IllegalArgumentException.class);
		//testInvalidEmail("espen.askeladd@eventyr.no", email, IllegalStateException.class);
		//try {
			//person.setEmail("ola.nordmann@ntnu.no");
			//assertEquals("ola.nordmann@ntnu.no", person.getEmail());
		//} catch (Exception e) {
			//fail("ola.nordmann@ntnu.no is a valid email");
		//}
	//}

	/*private void testInvalidEmail(String invalidEmail, String existingEmail, Class<? extends Exception> ex) {
		try {
			person.setEmail(invalidEmail);
			fail(invalidEmail + " is an invalid email");
		} catch (Exception e) {
			testExceptionAndValue(e, ex, existingEmail, person.getEmail());
		}
	*/

	
	public static void main(String[] args) {
		JexStandalone.main(PersonTest.class);
	}

}