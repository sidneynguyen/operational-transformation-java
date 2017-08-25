public class Composer {
    public Operation compose(Operation operation1, Operation operation2) {
        Operation parsedOperation1 = new Operation(operation1);
        Operation parsedOperation2 = new Operation(operation2);
        Operation compositeOperation = new Operation();
        int i;
        for (i = 0; i < Math.min(parsedOperation1.size(), parsedOperation2.size()); i++) {
            if (parsedOperation1.get(i).getLength() < parsedOperation2.get(i).getLength()) {
                OperationComponent component = parsedOperation2.get(i);
                parsedOperation2.set(i, component.subOperation(0, parsedOperation1.get(i).getLength()));
                parsedOperation2.add(i + 1, component.subOperation(parsedOperation1.get(i).getLength(),
                        parsedOperation2.get(i).getLength()));
            } else if (parsedOperation1.get(i).getLength() > parsedOperation2.get(i).getLength()) {
                OperationComponent component = parsedOperation1.get(i);
                parsedOperation1.set(i, component.subOperation(0, parsedOperation2.get(i).getLength()));
                parsedOperation1.add(i + 1, component.subOperation(parsedOperation2.get(i).getLength(),
                        parsedOperation1.get(i).getLength()));
            }
            OperationComponent component1 = parsedOperation1.get(i);
            OperationComponent component2 = parsedOperation2.get(i);
            if (component1.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                if (component2.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    compositeOperation.add(component2);
                    compositeOperation.add(component1);
                } else if (component2.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    // Do nothing
                } else {
                    compositeOperation.add(component1);
                }
            } else if (component1.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                if (component2.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    compositeOperation.add(component1);
                    compositeOperation.add(component2);
                } else if (component2.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    compositeOperation.add(component1);
                    compositeOperation.add(component2);
                } else {
                    compositeOperation.add(component1);
                    compositeOperation.add(component2);
                }
            } else {
                if (component2.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    compositeOperation.add(component2);
                    compositeOperation.add(component1);
                } else if (component2.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    compositeOperation.add(component2);
                } else {
                    compositeOperation.add(component1);
                }
            }
        }
        while (i < parsedOperation1.size()) {
            compositeOperation.add(parsedOperation1.get(i));
            i++;
        }
        while (i < parsedOperation2.size()) {
            compositeOperation.add(parsedOperation2.get(i));
            i++;
        }

        return compositeOperation;
    }
}
