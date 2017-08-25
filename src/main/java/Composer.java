/**
 * Implements a composer for generating composite operations.
 */
public class Composer {
    public Operation compose(Operation operation1, Operation operation2) {
        // create copies
        Operation parsedOperation1 = new Operation(operation1);
        Operation parsedOperation2 = new Operation(operation2);

        Operation compositeOperation = new Operation();
        int i;
        for (i = 0; i < Math.min(parsedOperation1.size(), parsedOperation2.size()); i++) {
            // slice operations if uneven operations
            if (parsedOperation1.get(i).getLength() < parsedOperation2.get(i).getLength()) {
                OperationComponent component = parsedOperation2.get(i);
                parsedOperation2.set(i, component.subComponent(0, parsedOperation1.get(i).getLength()));
                parsedOperation2.add(i + 1, component.subComponent(parsedOperation1.get(i).getLength(),
                        parsedOperation2.get(i).getLength()));
            } else if (parsedOperation1.get(i).getLength() > parsedOperation2.get(i).getLength()) {
                OperationComponent component = parsedOperation1.get(i);
                parsedOperation1.set(i, component.subComponent(0, parsedOperation2.get(i).getLength()));
                parsedOperation1.add(i + 1, component.subComponent(parsedOperation2.get(i).getLength(),
                        parsedOperation1.get(i).getLength()));
            }

            // choose what operations to add or remove
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

        // add leftover operations
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
