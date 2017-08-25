import org.junit.Test;

import static org.junit.Assert.*;

public class OperationTest {
    @Test
    public void simplify() throws Exception {
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "a", 1));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "b", 1));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "c", 1));
        operation.simplify();

        Operation expectedOperation = new Operation();
        expectedOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "abc", 3));

        assertEquals(true, operation.equals(expectedOperation));
    }

    @Test
    public void equals() throws Exception {
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "abc", 3));

        Operation expectedOperation = new Operation();
        expectedOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "abc", 3));

        assertEquals(true, operation.equals(expectedOperation));
    }
}