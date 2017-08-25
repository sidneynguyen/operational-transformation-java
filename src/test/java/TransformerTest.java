import org.junit.Test;

import static org.junit.Assert.*;

public class TransformerTest {
    @Test
    public void transform2() throws Exception {
        Document document1 = new Document("ABCDEFGHIJK");
        Document document2 = new Document("ABCDEFGHIJK");
        Operation clientOperation = new Operation();
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 4));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_DELETE, "EFGHI", 5));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 2));

        Operation serverOperation = new Operation();
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 3));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "VWXYZ", 5));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, null, 8));

        Transformer transformer = new Transformer();
        OperationPair pair = transformer.transform(clientOperation, serverOperation);

        document1.applyOperation(clientOperation);
        document1.applyOperation(pair.getServerOperation());

        document2.applyOperation(serverOperation);
        document2.applyOperation(pair.getClientOperation());

        assertEquals("ABCVWXYZDJK", document1.getData());
        assertEquals("ABCVWXYZDJK", document2.getData());
    }

    @Test
    public void transform() throws Exception {
        Document document1 = new Document("C");
        Document document2 = new Document("C");

        Operation clientOperation = new Operation();
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "A", 1));
        clientOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, "C", 1));

        Operation serverOperation = new Operation();
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_RETAIN, "C", 1));
        serverOperation.add(new OperationComponent(OperationComponent.OP_COMP_INSERT, "E", 1));

        Transformer transformer = new Transformer();
        OperationPair pair = transformer.transform(clientOperation, serverOperation);

        document1.applyOperation(clientOperation);
        document1.applyOperation(pair.getServerOperation());

        document2.applyOperation(serverOperation);
        document2.applyOperation(pair.getClientOperation());

        assertEquals("ACE", document1.getData());
        assertEquals("ACE", document2.getData());
    }
}