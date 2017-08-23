package com.sidneynguyen.otjava;

import java.util.ArrayList;

public class Transformer {
    /*public Operation transform(Operation clientOperation, Operation serverOperation) {
        Operation operation = new Operation();
        Operation parsedClientOperation = new Operation(clientOperation);
        Operation parsedServerOperation = new Operation(serverOperation);
        for (int i = 0; i < Math.max(clientOperation.size(), serverOperation.size()); i++){
            OperationComponent clientComponent = parsedClientOperation.get(i);
            OperationComponent serverComponent = parsedServerOperation.get(i);
            if (clientComponent.getPosition() != serverComponent.getPosition()) {
                throw new RuntimeException("Error in parsing components");
            }
            if (clientComponent.getLength() > serverComponent.getLength()
                    && clientComponent.getOperationType() != OperationComponent.OP_COMP_INSERT) {
                parsedClientOperation.set(i, clientComponent.subOperation(0, serverComponent.getLength()));
                parsedClientOperation.add(i + 1, clientComponent.subOperation(serverComponent.getLength(),
                        clientComponent.getLength()));
            } else if (clientComponent.getLength() < serverComponent.getLength()
                    && serverComponent.getOperationType() != OperationComponent.OP_COMP_INSERT) {
                parsedServerOperation.set(i, serverComponent.subOperation(0, clientComponent.getLength()));
                parsedServerOperation.add(i + 1, serverComponent.subOperation(clientComponent.getLength(),
                        serverComponent.getLength()));
            }
        }
        if (clientOperation.size() != serverOperation.size()) {
            throw new RuntimeException("Error in parsing components");
        }
        for (int i = 0; i < clientOperation.size(); i++) {
            OperationComponent clientComponent = parsedClientOperation.get(i);
            OperationComponent serverComponent = parsedServerOperation.get(i);
            int clientOperationType = clientComponent.getOperationType();
            int serverOperationType = serverComponent.getOperationType();
            if (clientOperationType == serverOperationType) {
                if (clientComponent.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    if (clientComponent.getValue().compareTo(serverComponent.getValue()) < 0) {
                        clientOperation.shiftPositions(i + 1, serverComponent.getLength());
                        serverOperation.shiftPositions(i, clientComponent.getLength());
                        operation.add(clientComponent);
                        operation.add(serverComponent);

                    } else {
                        serverOperation.shiftPositions(i + 1, serverComponent.getLength());
                        clientOperation.shiftPositions(i, serverComponent.getLength());
                        operation.add(serverComponent);
                        operation.add(clientComponent);
                    }
                } else if (clientComponent.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    operation.add(clientComponent);
                } else {
                    operation.add(clientComponent);
                }
            } else {
                if (clientOperationType == OperationComponent.OP_COMP_INSERT) {
                    if (serverOperationType == OperationComponent.OP_COMP_DELETE) {
                        clientOperation.shiftPositions(i + 1, -serverComponent.getLength());
                        serverOperation.shiftPositions(i, clientComponent.getLength());
                        operation.add(clientComponent);
                        operation.add(serverComponent);
                    } else {
                        clientOperation.shiftPositions(i + 1, serverComponent.getLength());
                        serverOperation.shiftPositions(i, clientComponent.getLength());
                        operation.add(clientComponent);
                        operation.add(serverComponent);
                    }
                } else if (clientOperationType == OperationComponent.OP_COMP_DELETE) {
                    if (serverOperationType == OperationComponent.OP_COMP_INSERT) {
                        serverOperation.shiftPositions(i + 1, -clientComponent.getLength());
                        clientOperation.shiftPositions(i, serverComponent.getLength());
                        operation.add(serverComponent);
                        operation.add(clientComponent);
                    } else {
                        serverOperation.shiftPositions(i + 1, -clientComponent.getLength());
                        operation.add(clientComponent);
                    }
                } else {
                    if (serverOperationType == OperationComponent.OP_COMP_INSERT) {
                        serverOperation.shiftPositions(i + 1, clientComponent.getLength());
                        clientOperation.shiftPositions(i, serverComponent.getLength());
                        operation.add(serverComponent);
                        operation.add(clientComponent);
                    } else {
                        clientOperation.shiftPositions(i + 1, -serverComponent.getLength());
                        operation.add(serverComponent);
                    }
                }
            }
        }
        return operation;
    }*/
    public OperationPair transform(Operation clientOperation, Operation serverOperation) {
        Operation parsedClientOperation = new Operation(clientOperation);
        Operation parsedServerOperation = new Operation(serverOperation);
        int clientIndex = 0;
        int serverIndex = 0;
        while (clientIndex < parsedClientOperation.size() && serverIndex < parsedServerOperation.size()) {
            OperationComponent clientComponent = parsedClientOperation.get(clientIndex);
            OperationComponent serverComponent = parsedServerOperation.get(serverIndex);

            if (clientComponent.getLength() > serverComponent.getLength()) {
                parsedClientOperation.set(clientIndex, clientComponent.subOperation(0, serverComponent.getLength()));
                parsedClientOperation.add(clientIndex + 1, clientComponent.subOperation(serverComponent.getLength(),
                        clientComponent.getLength()));
            } else if (clientComponent.getLength() < serverComponent.getLength()) {
                parsedServerOperation.set(serverIndex, serverComponent.subOperation(0, clientComponent.getLength()));
                parsedServerOperation.add(serverIndex + 1, serverComponent.subOperation(clientComponent.getLength(),
                        serverComponent.getLength()));
            }

            clientComponent = parsedClientOperation.get(clientIndex);
            serverComponent = parsedServerOperation.get(serverIndex);
            if (clientComponent.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                if (serverComponent.getOperationType() == OperationComponent.OP_COMP_INSERT) {
                    if (clientComponent.getValue().compareTo(serverComponent.getValue()) < 0) {
                        parsedClientOperation.add(clientIndex + 1, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                0,
                                serverComponent.getValue(),
                                serverComponent.getLength()
                        ));
                        parsedServerOperation.add(serverIndex, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                0,
                                clientComponent.getValue(),
                                clientComponent.getLength()
                        ));
                        clientIndex += 2;
                        serverIndex += 2;
                    } else {
                        parsedClientOperation.add(clientIndex, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                0,
                                serverComponent.getValue(),
                                serverComponent.getLength()
                        ));
                        parsedServerOperation.add(serverIndex + 1, new OperationComponent(
                                OperationComponent.OP_COMP_RETAIN,
                                0,
                                clientComponent.getValue(),
                                clientComponent.getLength()
                        ));
                        clientIndex += 2;
                        serverIndex += 2;
                    }
                } else if (serverComponent.getOperationType() == OperationComponent.OP_COMP_DELETE) {
                    parsedServerOperation.add(serverIndex, new OperationComponent(
                            OperationComponent.OP_COMP_RETAIN,
                            0,
                            clientComponent.getValue(),
                            clientComponent.getLength()
                    ));
                } else {

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
                            clientComponent.getPosition(),
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

        }

        OperationPair pair = new OperationPair(parsedClientOperation, parsedServerOperation);
        return pair;
    }
}
