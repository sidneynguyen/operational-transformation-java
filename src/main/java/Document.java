/**
 * Models a document.
 */
public class Document {
    private String data;

    public Document(String data) {
        this.data = data;
    }

    /**
     * Applies an operation onto the document.
     * @param operation     the operation to apply
     */
    public void applyOperation(Operation operation) {
        int cursor = 0;
        String appliedData = "";
        for (int i = 0; i < operation.size(); i++) {
            OperationComponent component = operation.get(i);
            if (component.getOperationType() == OperationComponent.OP_COMP_RETAIN) {
                appliedData += data.substring(cursor, cursor + component.getLength());
                cursor += component.getLength();
            } else if (component.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                appliedData += component.getValue();
            } else if (component.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                if (component.getValue().equals(data.substring(cursor, cursor + component.getLength()))) {
                    cursor += component.getLength();
                } else {
                    throw new RuntimeException("Data corrupted");
                }
            }
        }
        data = appliedData;
    }

    public String getData() {
        return data;
    }
}
