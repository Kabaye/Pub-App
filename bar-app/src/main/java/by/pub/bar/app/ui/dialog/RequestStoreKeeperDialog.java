package by.pub.bar.app.ui.dialog;

import by.pub.bar.app.ingredient_request.service.IngredientRequestService;
import by.pub.bar.app.ui.table_model.IngredientTableModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import org.springframework.stereotype.Component;

@Component
public class RequestStoreKeeperDialog extends JDialog {

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 15);
    private static final int SCREEN_WIDTH = 250;
    private static final int SCREEN_HEIGHT = 100;

    private final JPanel mainPanel;
    private final JPanel upperPanel;
    private final JPanel lowerPanel;
    private final JLabel nameLabel;
    private final JLabel amountLabel;
    private final JTextField nameTextField;
    private final JTextField amountTextField;
    private final JButton requestButton;
    private final JButton cancelButton;

    private final IngredientRequestService ingredientRequestService;
    private final IngredientTableModel ingredientTableModel;

    public RequestStoreKeeperDialog(
        IngredientRequestService ingredientRequestService,
        IngredientTableModel ingredientTableModel) {
        super((JFrame) null, "Request for ingredients");
        this.ingredientRequestService = ingredientRequestService;
        this.ingredientTableModel = ingredientTableModel;

        mainPanel = new JPanel(new BorderLayout());

        upperPanel = new JPanel(new GridLayout(2, 2));
        nameLabel = new JLabel("Name");
        nameTextField = new JTextField();
        amountLabel = new JLabel("Amount");
        amountTextField = new JTextField();

        lowerPanel = new JPanel(new GridLayout(1, 2));
        requestButton = new JButton("Request");
        cancelButton = new JButton("Cancel");

        addListeners();
        addComponentsToMainPanel();
        configureComponents();
        setWindowPreferences();
    }

    private void addListeners() {
        requestButton.addActionListener(e -> {
            try {
                String name = nameTextField.getText();
                Long amount = Long.parseLong(amountTextField.getText());
                ingredientRequestService.createAndSendIngredientRequest(name, amount);
            } catch (Exception exception) {
                JOptionPane.showMessageDialog(RequestStoreKeeperDialog.this,
                    exception.getMessage());
            }
        });
        cancelButton.addActionListener(e -> setVisible(false));
    }

    private void addComponentsToMainPanel() {
        upperPanel.add(nameLabel);
        upperPanel.add(amountLabel);
        upperPanel.add(nameTextField);
        upperPanel.add(amountTextField);
        lowerPanel.add(cancelButton);
        lowerPanel.add(requestButton);

        mainPanel.add(upperPanel, BorderLayout.CENTER);
        mainPanel.add(lowerPanel, BorderLayout.SOUTH);
    }

    private void configureComponents() {
        //panels configuration
        upperPanel.setBorder(BorderFactory.createBevelBorder(1));
        lowerPanel.setBorder(BorderFactory.createBevelBorder(1));
        //labels configuration
        nameLabel.setFont(HEADER_FONT);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountLabel.setFont(HEADER_FONT);
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //buttons configuration
        requestButton.setFont(HEADER_FONT);
        cancelButton.setFont(HEADER_FONT);
    }

    private void setWindowPreferences() {
        setTitle("Bar: Ingredients handler");
        setContentPane(mainPanel);
        setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        setResizable(false);
        pack();
    }
}

