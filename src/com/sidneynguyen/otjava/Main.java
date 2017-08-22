package com.sidneynguyen.otjava;

import java.util.UUID;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Hello, World!");
	    testOperationComponent();
    }

    public static void testOperationComponent() {
        String str = "abcdefghi";
        OperationComponent component = new OperationComponent(OperationComponent.OP_COMP_RETAIN, 1, null, 5);
        OperationComponent component1 = component.subOperation(0, 2);
        OperationComponent component2 = component.subOperation(2, 5);
        assert component1.getLength() == 2;
        assert component1.getPosition() == 1;
        assert component2.getLength() == 3;
        assert component2.getPosition() == 2;

        System.out.println("OperationComponent tests passed");
    }

    public static void testTransformer() {

    }

    public static void testClientGraph() {
        OperationNode rootNode = new OperationNode(UUID.randomUUID().toString());
        ClientGraph clientGraph = new ClientGraph(rootNode);
    }
}
