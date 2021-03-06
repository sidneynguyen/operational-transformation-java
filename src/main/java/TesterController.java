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
        if (!clientDriver1.canSendEdit()) {
            return;
        }

        String client1Text = client1TextArea.getText();
        if (client1Text.equals(clientDriver1.getDocument().getData()) && !clientDriver1.hasUnsentEdit()) {
            return;
        }
        clientDriver1.setHasUnsentEdit(false);

        ServerOperation clientOperation1 = clientDriver1.sendEdits(client1Text);

        serverDriver.enqueueClientOperation(clientOperation1);
        serverDriver.processChange();
        ServerOperation serverOperation1 = serverDriver.sendServerOperationToClient();
        serverDocumentLabel.setText(serverDriver.getDocument().getData());

        clientDriver1.enqueueServerOperation(serverOperation1);
        clientDriver2.enqueueServerOperation(serverOperation1);
        /*
        String client1Text = client1TextArea.getText();
        if (!client1Text.equals(clientDriver1.getDocument().getData())) {
            Operation operation = clientDriver1.getOperationFromEdit(client1Text);
            clientDriver1.enqueueClientOperation(operation);
            while (clientDriver1.canProcess()) {
                clientDriver1.processChange();
            }
            client1TextArea.setText(clientDriver1.getDocument().getData());
        }

        String client2Text = client2TextArea.getText();
        if (!client2Text.equals(clientDriver2.getDocument().getData())) {
            Operation op2 = clientDriver2.getOperationFromEdit(client2Text);
            clientDriver2.enqueueClientOperation(op2);
            while (clientDriver2.canProcess()) {
                clientDriver2.processChange();
            }
            client2TextArea.setText(clientDriver2.getDocument().getData());
        }

        if (!clientDriver1.canSendOperationToServer()) {
            return;
        }

        ServerOperation clientOperation = clientDriver1.sendClientOperationToServer();
        serverDriver.enqueueClientOperation(clientOperation);
        serverDriver.processChange();

        serverDocumentLabel.setText(serverDriver.getDocument().getData());

        ServerOperation serverOperation = serverDriver.sendServerOperationToClient();
        clientDriver1.enqueueServerOperation(serverOperation);
        clientDriver1.processChange();

        clientDriver2.enqueueServerOperation(serverOperation);
        */
    }

    @FXML
    protected void handleClient2PushButtonAction(ActionEvent event) {
        if (!clientDriver2.canSendEdit()) {
            return;
        }

        String client2Text = client2TextArea.getText();
        if (client2Text.equals(clientDriver2.getDocument().getData()) && !clientDriver2.hasUnsentEdit()) {
            return;
        }
        clientDriver2.setHasUnsentEdit(false);

        ServerOperation clientOperation2 = clientDriver2.sendEdits(client2Text);

        serverDriver.enqueueClientOperation(clientOperation2);
        serverDriver.processChange();
        ServerOperation serverOperation2 = serverDriver.sendServerOperationToClient();
        serverDocumentLabel.setText(serverDriver.getDocument().getData());

        clientDriver1.enqueueServerOperation(serverOperation2);
        clientDriver2.enqueueServerOperation(serverOperation2);
        /*
        String client2Text = client2TextArea.getText();
        if (!client2Text.equals(clientDriver2.getDocument().getData())) {
            Operation operation = clientDriver2.getOperationFromEdit(client2Text);
            clientDriver2.enqueueClientOperation(operation);
            while (clientDriver2.canProcess()) {
                clientDriver2.processChange();
            }
            client2TextArea.setText(clientDriver2.getDocument().getData());
        }

        String client1Text = client1TextArea.getText();
        if (!client1Text.equals(clientDriver1.getDocument().getData())) {
            Operation operation = clientDriver1.getOperationFromEdit(client1Text);
            clientDriver1.enqueueClientOperation(operation);
            while (clientDriver1.canProcess()) {
                clientDriver1.processChange();
            }
            client1TextArea.setText(clientDriver1.getDocument().getData());
        }

        if (!clientDriver2.canSendOperationToServer()) {
            return;
        }

        ServerOperation clientOperation = clientDriver2.sendClientOperationToServer();
        serverDriver.enqueueClientOperation(clientOperation);
        serverDriver.processChange();

        serverDocumentLabel.setText(serverDriver.getDocument().getData());

        ServerOperation serverOperation = serverDriver.sendServerOperationToClient();
        clientDriver2.enqueueServerOperation(serverOperation);
        clientDriver2.processChange();

        clientDriver1.enqueueServerOperation(serverOperation);
        */
    }

    @FXML
    protected void handleClient1PullButtonAction(ActionEvent event) {
        /*
        clientDriver1.processChange();
        client1TextArea.setText(clientDriver1.getDocument().getData());
        */
        String client1Text = client1TextArea.getText();
        if (!client1Text.equals(clientDriver1.getDocument().getData())) {
            clientDriver1.applyEdits(client1Text);
            clientDriver1.setHasUnsentEdit(true);
        }
        clientDriver1.receiveEdits();

        client1TextArea.setText(clientDriver1.getDocument().getData());
    }

    @FXML
    protected void handleClient2PullButtonAction(ActionEvent event) {
        /*
        clientDriver2.processChange();
        client2TextArea.setText(clientDriver2.getDocument().getData());
        */
        String client2Text = client2TextArea.getText();
        if (!client2Text.equals(clientDriver2.getDocument().getData())) {
            clientDriver2.applyEdits(client2Text);
            clientDriver2.setHasUnsentEdit(true);
        }
        clientDriver2.receiveEdits();

        client2TextArea.setText(clientDriver2.getDocument().getData());
    }
}
