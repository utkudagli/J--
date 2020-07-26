public abstract class MyInterfaceTest {
    public abstract MyInterface createInstance();

    @Test
    public final void testMyMethod_True() {
        MyInterface instance = createInstance();
        assertTrue(instance.myMethod(true));
    }

    @Test
    public final void testMyMethod_False() {
        MyInterface instance = createInstance();
        assertFalse(instance.myMethod(false));
    }
}

public class MyClass1Test extends MyInterfaceTest {
    public MyInterface createInstance() {
        return new MyClass1();
    }
}

public class MyClass2Test extends MyInterfaceTest {
    public MyInterface createInstance() {
        return new MyClass2();
    }
