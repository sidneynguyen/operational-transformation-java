package com.sidneynguyen.otjava;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

import java.util.UUID;

// TODO: Fix bugs regarding multiple pushes and pulls during edits
public class TesterController {
    private ClientDriver clientDriver1;
    private ClientDriver clientDriver2;
    private ServerDriver serverDriver;

    @FXML
    private Label serverDocumentLabel;

    @FXML
    private TextArea client1TextArea;

    @FXML
    private TextArea client2TextArea;

    public TesterController() {
        String key = UUID.randomUUID().toString();
        String originalDocument = "Hello";
        clientDriver1 = new ClientDriver(originalDocument, key);
        clientDriver2 = new ClientDriver(originalDocument, key);
        serverDriver = new ServerDriver(originalDocument, key);
    }

    @FXML
    protected void handleClient1PushButtonAction(ActionEvent event) {
        String client1Text = client1TextArea.getText();
        if (!client1Text.equals(clientDriver1.getDocument().getData())) {
            Operation operation = clientDriver1.getOperationFromEdit(client1Text);
            clientDriver1.enqueueClientOperation(operation);
            clientDriver1.processChange();
            client1TextArea.setText(clientDriver1.getDocument().getData());
        }

        String client2Text = client2TextArea.getText();
        if (!client2Text.equals(clientDriver2.getDocument().getData())) {
            Operation op2 = clientDriver2.getOperationFromEdit(client2Text);
            clientDriver2.enqueueClientOperation(op2);
            clientDriver2.processChange();
            client2TextArea.setText(clientDriver2.getDocument().getData());
        }

        ServerOperation clientOperation = clientDriver1.sendClientOperationToServer();
        serverDriver.enqueueClientOperation(clientOperation);
        serverDriver.processChange();

        serverDocumentLabel.setText(serverDriver.getDocument().getData());

        ServerOperation serverOperation = serverDriver.sendServerOperationToClient();
        clientDriver1.enqueueServerOperation(serverOperation);
        clientDriver1.processChange();

        clientDriver2.enqueueServerOperation(serverOperation);
        clientDriver2.processChange();
    }

    @FXML
    protected void handleClient2PushButtonAction(ActionEvent event) {
        String client2Text = client2TextArea.getText();
        if (!client2Text.equals(clientDriver2.getDocument().getData())) {
            Operation operation = clientDriver2.getOperationFromEdit(client2Text);
            clientDriver2.enqueueClientOperation(operation);
            clientDriver2.processChange();
            client2TextArea.setText(clientDriver2.getDocument().getData());
        }

        String client1Text = client1TextArea.getText();
        if (!client1Text.equals(clientDriver1.getDocument().getData())) {
            Operation operation = clientDriver1.getOperationFromEdit(client1Text);
            clientDriver1.enqueueClientOperation(operation);
            clientDriver1.processChange();
            client1TextArea.setText(clientDriver1.getDocument().getData());
        }

        ServerOperation clientOperation = clientDriver2.sendClientOperationToServer();
        serverDriver.enqueueClientOperation(clientOperation);
        serverDriver.processChange();

        serverDocumentLabel.setText(serverDriver.getDocument().getData());

        ServerOperation serverOperation = serverDriver.sendServerOperationToClient();
        clientDriver2.enqueueServerOperation(serverOperation);
        clientDriver2.processChange();

        clientDriver1.enqueueServerOperation(serverOperation);
        clientDriver1.processChange();
    }

    @FXML
    protected void handleClient1PullButtonAction(ActionEvent event) {
        client1TextArea.setText(clientDriver1.getDocument().getData());
    }

    @FXML
    protected void handleClient2PullButtonAction(ActionEvent event) {
        client2TextArea.setText(clientDriver2.getDocument().getData());
    }
}
