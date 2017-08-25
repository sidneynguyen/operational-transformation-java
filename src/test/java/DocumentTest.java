import org.junit.Test;

import static org.junit.Assert.*;

public class DocumentTest {
    @Test
    public void applyOperation1() throws Exception {
        Document document = new Document("ABCDEFGHI");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, "DE", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "OK", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 4));
        document.applyOperation(operation);

        assertEquals("ABCOKFGHI", document.getData());
    }

    @Test
    public void applyOperation2() throws Exception {
        Document document = new Document("ABCDEFGHI");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "OK", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, "DE", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 4));
        document.applyOperation(operation);

        assertEquals("ABCOKFGHI", document.getData());
    }

    @Test
    public void applyOperation3() throws Exception {
        Document document = new Document("ABCDEFGHI");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "OK", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, "GH", 2));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 1));
        document.applyOperation(operation);

        assertEquals("ABCOKDEFI", document.getData());
    }

    @Test
    public void applyOperation4() throws Exception {
        Document document1 = new Document("ABCDEFGHIJK");
        Operation clientOperation = new Operation();
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 4));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, "EFGHI", 5));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 2));
        document1.applyOperation(clientOperation);

        assertEquals("ABCDJK", document1.getData());

        Document document2 = new Document("ABCDEFGHIJK");
        Operation serverOperation = new Operation();
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 3));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "VWXYZ", 5));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 8));
        document2.applyOperation(serverOperation);

        assertEquals("ABCVWXYZDEFGHIJK", document2.getData());
    }

    @Test
    public void applyOperation5() throws Exception {
        Document document = new Document("ABC");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 3));
        operation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "DE", 2));
        document.applyOperation(operation);

        assertEquals("ABCDE", document.getData());
    }

    @Test
    public void applyOperation6() throws Exception {
        Document document = new Document("ABC");
        Operation operation = new Operation();
        operation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, "ABC", 3));
        document.applyOperation(operation);

        assertEquals("", document.getData());
    }
}