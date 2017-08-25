import java.util.ArrayList;

/**
 * Models an operation.
 * An operation is a list of operation components;
 */
public class Operation {
    private ArrayList<OperationComponent> operationComponents;

    public Operation(ArrayList<OperationComponent> operationComponents) {
        this.operationComponents = operationComponents;
    }

    public Operation() {
        operationComponents = new ArrayList<>();
    }

    public Operation(Operation operation) {
        operationComponents = new ArrayList<>(operation.getOperationComponents());
    }

    /**
     * Combines adjacent operation components with matching operation types.
     */
    public void simplify() {
        for (int i = 0; i < operationComponents.size() - 1; ) {
            if (operationComponents.get(i).getOperationType() == operationComponents.get(i + 1).getOperationType()) {
                operationComponents.set(i, new OperationComponent(
                        operationComponents.get(i).getOperationType(),
                        operationComponents.get(i).getValue() + operationComponents.get(i + 1).getValue(),
                        operationComponents.get(i).getLength() + operationComponents.get(i + 1). getLength()
                ));
                operationComponents.remove(i + 1);
            } else {
                i++;
            }
        }
    }

    public ArrayList<OperationComponent> getOperationComponents() {
        return operationComponents;
    }

    public OperationComponent set(int index, OperationComponent component) {
        return operationComponents.set(index, component);
    }

    public boolean add(OperationComponent component) {
        return operationComponents.add(component);
    }

    public void add(int index, OperationComponent component) {
        operationComponents.add(index, component);
    }

    public OperationComponent get(int index) {
        return operationComponents.get(index);
    }

    public int size() {
        return operationComponents.size();
    }

    public OperationComponent remove(int index) {
        return operationComponents.remove(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Operation) {
            Operation operation = (Operation) obj;
            if (operationComponents.size() != operation.size()) {
                return false;
            }
            for (int i = 0; i < operationComponents.size(); i++) {
                if (!operationComponents.get(i).equals(operation.get(i))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
