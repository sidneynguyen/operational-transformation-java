/**
 * Models an operation component;
 */
public class OperationComponent {
    public static final int OP_COMP_RETAIN = 0;
    public static final int OP_COMP_INSERT = 1;
    public static final int OP_COMP_DELETE = 2;

    private int operationType;
    private String value;
    private int length;

    public OperationComponent(int operationType, String value, int length) {
        this.operationType = operationType;
        this.value = value;
        this.length = length;
    }

    public int getOperationType() {
        return operationType;
    }

    public String getValue() {
        return value;
    }

    public int getLength() {
        return length;
    }

    /**
     * Returns a subset of a component.
     * @param start     inclusive
     * @param end       exclusive
     * @return          the resulting component
     */
    public OperationComponent subComponent(int start, int end) {
        if (length < end - start || start < 0 || end > length || end < start || start >= length) {
            throw new IndexOutOfBoundsException();
        }
        if (operationType == OP_COMP_RETAIN) {
            return new OperationComponent(operationType, value, end - start);
        }
        return new OperationComponent(operationType, value.substring(start, end), end - start);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OperationComponent)) {
            return false;
        }
        OperationComponent component = (OperationComponent) obj;
        return component.getOperationType() == operationType && component.getValue().equals(value)
                && component.getLength() == length;
    }
}