package junit;

import junit.framework.TestCase;
import pass.Modulo;

public class ModuloTest extends TestCase{
	private Modulo mod;
	
	protected void setUp() throws Exception{
		super.setUp();
		mod = new Modulo();
	}
	protected void tearDown() throws Exception{
		super.tearDown();
	}
	public void testMod() {
		this.assertEquals(0, mod.mod(0, 42));
		this.assertEquals(3, mod.mod(3, 4));
		this.assertEquals(3, mod.mod(7, 4));
	}
}