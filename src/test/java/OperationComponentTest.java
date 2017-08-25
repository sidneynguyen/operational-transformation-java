import org.junit.Test;

import static org.junit.Assert.*;

public class OperationComponentTest {
    @Test
    public void subOperationRetain() throws Exception {
        OperationComponent component =
                new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 5);
        OperationComponent component1 = component.subOperation(0, 2);
        OperationComponent component2 = component.subOperation(2, 5);

        assertEquals(2, component1.getLength());

        assertEquals(3, component2.getLength());
    }

    @Test
    public void subOperationInsert() throws Exception {
        OperationComponent component =
                new OperationComponent(OperationComponent.OP_COMP_INSERT, "abcde", 5);
        OperationComponent component1 = component.subOperation(0, 2);
        OperationComponent component2 = component.subOperation(2, 5);

        assertEquals(2, component1.getLength());
        assertEquals("ab", component1.getValue());

        assertEquals(3, component2.getLength());
        assertEquals("cde", component2.getValue());
    }

    @Test
    public void subOperationDelete() throws Exception {
        OperationComponent component =
                new OperationComponent(OperationComponent.OP_COMP_DELETE, "abcde", 5);
        OperationComponent component1 = component.subOperation(0, 2);
        OperationComponent component2 = component.subOperation(2, 5);

        assertEquals(2, component1.getLength());
        assertEquals("ab", component1.getValue());

        assertEquals(3, component2.getLength());
        assertEquals("cde", component2.getValue());
    }
}