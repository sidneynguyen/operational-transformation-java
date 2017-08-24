package com.sidneynguyen.otjava;

public class ServerOperation {
    private Operation operation;
    private String key;
    private String parentKey;
    private String uuid;

    public ServerOperation(Operation operation, String key, String parentKey, String uuid) {
        this.operation = operation;
        this.key = key;
        this.parentKey = parentKey;
        this.uuid = uuid;
    }

    public Operation getOperation() {
        return operation;
    }

    public String getKey() {
        return key;
    }

    public String getParentKey() {
        return parentKey;
    }

    public String getUuid() {
        return uuid;
    }
}
