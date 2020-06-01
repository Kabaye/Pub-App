package by.pub.storage.app.ui.dialog;

import by.pub.storage.app.element.ingredient.entity.Ingredient;
import by.pub.storage.app.element.ingredient.service.IngredientService;
import by.pub.storage.app.ui.config.WindowConfig;
import by.pub.storage.app.ui.table_model.IngredientTableModel;
import by.pub.storage.app.utils.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

@Component
public class RequestProviderDialog extends JDialog {

    private static final Color UPPER_PANEL_COLOR = Color.GREEN;

    private final JPanel mainPanel;
    private final JPanel upperPanel;
    private final JPanel lowerPanel;
    private final JLabel nameLabel;
    private final JLabel amountLabel;
    private final JTextField nameTextField;
    private final JTextField amountTextField;
    private final JButton requestButton;
    private final JButton cancelButton;

    private final IngredientService ingredientService;
    private final IngredientTableModel ingredientTableModel;

    private final String appVersion;

    public RequestProviderDialog(IngredientService ingredientService, IngredientTableModel ingredientTableModel, String appVersion) {
        super((JFrame) null, "Request for ingredients");
        this.ingredientService = ingredientService;
        this.ingredientTableModel = ingredientTableModel;
        this.appVersion = appVersion;

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
                Ingredient ingredient;
                try {
                    ingredient = ingredientService.orderIngredient(name, amount);
                } catch (RuntimeException exception) {
                    ingredient = ingredientService
                            .saveIngredient(new Ingredient().setName(name).setAmount(amount));
                }
                ingredientTableModel.removeRow(ingredient);
                ingredientTableModel.addRow(ingredient);
            } catch (Exception exception) {
                JOptionPane
                        .showMessageDialog(RequestProviderDialog.this,
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
        upperPanel.setBackground(UPPER_PANEL_COLOR);
        //labels configuration
        nameLabel.setFont(WindowConfig.getDialogHeaderFont());
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        amountLabel.setFont(WindowConfig.getDialogHeaderFont());
        amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        //buttons configuration
        requestButton.setFont(WindowConfig.getDialogHeaderFont());
        cancelButton.setFont(WindowConfig.getDialogHeaderFont());
    }

    private void setWindowPreferences() {
        setTitle("Storage ingredient ordering system " + appVersion);
        setIconImage(ResourceLoader.getImage("icon/provider.png"));
        setContentPane(mainPanel);
        setPreferredSize(
                new Dimension(WindowConfig.getDialogScreenWidth(), WindowConfig.getDialogScreenHeight()));
        setResizable(false);
        pack();
    }
}
