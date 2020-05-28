package by.pub.storage.app.ui;

import by.pub.storage.app.ingredient.entity.Ingredient;
import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class MainWindow extends JFrame {

  public static final int SCREEN_WIDTH = 1000;
  public static final int SCREEN_HEIGHT = 600;
  private JPanel mainPanel;
  private JPanel authPanel;
  private JPanel ingredientPanel;
  private JPanel ingredientRequestPanel;
  private JList<Ingredient> ingredientJList;
  private JList<IngredientRequest> ingredientRequestJList;
  private JButton fulfillButton;
  private JButton ingredientRequestButton;

  public MainWindow() {
    authPanel = new JPanel();

    mainPanel = new JPanel(new BorderLayout());

    ingredientRequestPanel = new JPanel(new BorderLayout());
    ingredientRequestJList = new JList<>();
    fulfillButton = new JButton("Fulfill request");
    ingredientRequestPanel.add(ingredientRequestJList, BorderLayout.NORTH);
    ingredientRequestJList
        .setPreferredSize(new Dimension(SCREEN_WIDTH / 3, 2 * SCREEN_HEIGHT / 3));
    ingredientRequestPanel.add(fulfillButton, BorderLayout.SOUTH);
    fulfillButton.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 6));

    ingredientPanel = new JPanel(new BorderLayout());
    ingredientJList = new JList<>();
    ingredientRequestButton = new JButton("Request more");
    ingredientPanel.add(ingredientJList, BorderLayout.NORTH);
    ingredientJList.setPreferredSize(new Dimension(SCREEN_WIDTH / 3, 2 * SCREEN_HEIGHT / 3));
    ingredientPanel.add(ingredientRequestButton, BorderLayout.SOUTH);
    ingredientRequestButton
        .setPreferredSize(new Dimension(SCREEN_WIDTH / 3, SCREEN_HEIGHT / 6));

    mainPanel.add(ingredientJList, BorderLayout.EAST);
    mainPanel.add(ingredientRequestJList, BorderLayout.WEST);

    setMainWindowPreferences();
  }

  public static void main(String[] args) {
    MainWindow mainWindow = new MainWindow();
    mainWindow.setVisible(true);
  }

  private void setMainWindowPreferences() {
    setContentPane(mainPanel);
    setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
    setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    setResizable(false);
    pack();
  }
}
