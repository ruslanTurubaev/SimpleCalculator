package com.example.simplecalculator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String INSERT_PANEL_BUNDLE = "insert_panel";
    private static final String RESULT_PANEL_BUNDLE = "result_panel";

    EditText insertPanel;
    TextView resultPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        insertPanel = findViewById(R.id.insert_panel);
        resultPanel = findViewById(R.id.result_panel);

        insertPanel.setShowSoftInputOnFocus(false);

        if(savedInstanceState != null){
            insertPanel.setText(savedInstanceState.getCharSequence(INSERT_PANEL_BUNDLE));
            resultPanel.setText(savedInstanceState.getCharSequence(RESULT_PANEL_BUNDLE));
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putCharSequence(INSERT_PANEL_BUNDLE, insertPanel.getText());
        outState.putCharSequence(RESULT_PANEL_BUNDLE, resultPanel.getText());
    }

    public void onClickButton(View view) {
        CustomButton customButton = (CustomButton) view;
        int id = view.getId();
        switch (id){
            case (R.id.button_clear):
                customButton.runAnimation();
                AnimatedView animatedView = findViewById(R.id.animatedView);
                animatedView.runAnimation();
                animatedView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        insertPanel.setText("");
                        resultPanel.setText("");
                    }
                }, animatedView.getAnimationDuration());
                break;
            case (R.id.button_equal):
                customButton.runAnimation();
                if(insertPanel.length() > 0) {
                    resultPanel.setText(Calculator.calculate(insertPanel.getText().toString()));
                }
                break;
            case (R.id.button_cursor_back):
                customButton.runAnimation();

                int cursor = insertPanel.getSelectionStart();
                Editable editable = insertPanel.getText();

                if (editable.length() == 0 || cursor == 0) break;

                int currentLength = editable.length();
                editable.delete(cursor - 1, cursor);

                String result = PanelTextSupport.addDividers(editable.toString());
                int cursorDelta = result.length() - currentLength;

                insertPanel.setText(result);
                insertPanel.setSelection(cursor + cursorDelta);
                break;
            default:
                customButton.runAnimation();

                int cursor2 = insertPanel.getSelectionStart();
                Editable editable2 = insertPanel.getText();

                if(PanelTextSupport.isCorrectInput(editable2, cursor2, customButton.getText())){
                    int currentLength2 = editable2.length();
                    editable2.insert(cursor2, customButton.getText());
                    String result2 = PanelTextSupport.addDividers(editable2.toString());
                    int cursorDelta2 = result2.length() - currentLength2;

                    insertPanel.setText(result2);
                    insertPanel.setSelection(cursor2 + cursorDelta2);
                }
        }

    }
}