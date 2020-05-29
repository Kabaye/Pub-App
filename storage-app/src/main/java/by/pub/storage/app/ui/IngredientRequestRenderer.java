package by.pub.storage.app.ui;

import by.pub.storage.app.ingredient_request.entity.IngredientRequest;
import by.pub.storage.app.ingredient_request.entity.IngredientRequestStatus;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class IngredientRequestRenderer extends JLabel implements
    ListCellRenderer<IngredientRequest> {

    private static final Font HEADER_FONT = new Font("Serif", Font.PLAIN, 20);

    @Override
    public Component getListCellRendererComponent(JList<? extends IngredientRequest> list,
        IngredientRequest ingredientRequest,
        int index,
        boolean isSelected, boolean cellHasFocus) {

        setText(getStringRepresentation(ingredientRequest));
        setFont(HEADER_FONT);

        if (ingredientRequest.getStatus().equals(IngredientRequestStatus.ACCEPTED)) {
            setForeground(Color.GREEN);
        } else {
            setForeground(Color.RED);
        }

        if (isSelected) {
            setForeground(list.getSelectionForeground());
        }

        return this;
    }

    String getStringRepresentation(IngredientRequest ingredientRequest) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ingredientRequest.getRequestId());
        stringBuffer.append(" ");
        stringBuffer.append(ingredientRequest.getStatus());
        stringBuffer.append(" ");
        stringBuffer.append(ingredientRequest.getIngredientAmount());
        stringBuffer.append(" ");
        stringBuffer.append(ingredientRequest.getIngredientName());
        return stringBuffer.toString();
    }

}
