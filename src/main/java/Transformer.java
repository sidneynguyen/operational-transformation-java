public class Transformer {

    public OperationPair transform(Operation clientOperation, Operation serverOperation) {
        Operation parsedClientOperation = new Operation(clientOperation);
        Operation parsedServerOperation = new Operation(serverOperation);
        int clientIndex = 0;
        int serverIndex = 0;
        while (clientIndex < parsedClientOperation.size() && serverIndex < parsedServerOperation.size()) {
            OperationComponent clientComponent = parsedClientOperation.get(clientIndex);
            OperationComponent serverComponent = parsedServerOperation.get(serverIndex);

            if (clientComponent.getLength() > serverComponent.getLength()) {
                parsedClientOperation.set(clientIndex, clientComponent.subComponent(0, serverComponent.getLength()));
                parsedClientOperation.add(clientIndex + 1, clientComponent.subComponent(serverComponent.getLength(),
                        clientComponent.getLength()));
            } else if (clientComponent.getLength() < serverComponent.getLength()) {
                parsedServerOperation.set(serverIndex, serverComponent.subComponent(0, clientComponent.getLength()));
                parsedServerOperation.add(serverIndex + 1, serverComponent.subComponent(clientComponent.getLength(),
                        serverComponent.getLength()));
            }

            clientComponent = parsedClientOperation.get(clientIndex);
            serverComponent = parsedServerOperation.get(serverIndex);
            if (clientComponent.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                if (serverComponent.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    if (clientComponent.getValue().compareTo(serverComponent.getValue()) < 0) {
                        parsedClientOperation.add(clientIndex + 1, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                serverComponent.getValue(),
                                serverComponent.getLength()
                        ));
                        parsedServerOperation.add(serverIndex, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                clientComponent.getValue(),
                                clientComponent.getLength()
                        ));
                        clientIndex += 2;
                        serverIndex += 2;
                    } else {
                        parsedClientOperation.add(clientIndex, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                serverComponent.getValue(),
                                serverComponent.getLength()
                        ));
                        parsedServerOperation.add(serverIndex + 1, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                clientComponent.getValue(),
                                clientComponent.getLength()
                        ));
                        clientIndex += 2;
                        serverIndex += 2;
                    }
                } else if (serverComponent.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    parsedServerOperation.add(serverIndex, new OperationComponent(
                            OperationComponent.OP_COMP_RETAIN,
                            clientComponent.getValue(),
                            clientComponent.getLength()
                    ));
                } else {
                    parsedServerOperation.add(serverIndex, new OperationComponent(
                            OperationComponent.OP_COMP_RETAIN,
                            clientComponent.getValue(),
                            clientComponent.getLength()
                    ));
                    clientIndex++;
                    serverIndex++;
                }
            } else if (clientComponent.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                if (serverComponent.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    clientIndex++;
                    serverIndex++;
                } else if (serverComponent.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    parsedClientOperation.remove(clientIndex);
                    parsedServerOperation.remove(serverIndex);
                } else {
                    parsedServerOperation.remove(serverIndex);
                    clientIndex++;
                }
            } else {
                if (serverComponent.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    parsedClientOperation.add(clientIndex, new OperationComponent(
                            OperationComponent.OP_COMP_RETAIN,
                            serverComponent.getValue(),
                            serverComponent.getLength()
                    ));
                    clientIndex++;
                    serverIndex++;
                } else if (serverComponent.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    parsedClientOperation.remove(clientIndex);
                    serverIndex++;
                } else {
                    clientIndex++;
                    serverIndex++;
                }
            }
        }
        if (clientIndex < parsedClientOperation.size()) {
            while (clientIndex < parsedClientOperation.size()) {
                OperationComponent operation = parsedClientOperation.get(clientIndex);
                if (operation.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    parsedServerOperation.add(new OperationComponent(
                            OperationComponent.OP_COMP_RETAIN,
                            operation.getValue(),
                            operation.getLength()
                    ));
                    clientIndex++;
                } else {
                    throw new RuntimeException("Uh oh");
                }
            }
        } else if (serverIndex < parsedServerOperation.size()) {
            while (serverIndex < parsedServerOperation.size()) {
                OperationComponent operation = parsedServerOperation.get(clientIndex);
                if (operation.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    parsedClientOperation.add(new OperationComponent(
                            OperationComponent.OP_COMP_RETAIN,
                            operation.getValue(),
                            operation.getLength()
                    ));
                    serverIndex++;
                } else {
                    throw new RuntimeException("Uh oh");
                }
            }
        }

        parsedClientOperation.simplify();
        parsedServerOperation.simplify();
        OperationPair pair = new OperationPair(parsedClientOperation, parsedServerOperation);
        return pair;
    }
}
