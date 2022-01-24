package com.example.simplecalculator;


import android.text.Editable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
* The class contains methods for working with user's input information and displayed result
 */
public class PanelTextSupport {
    private static final ArrayList dotAndOperators = new ArrayList( Arrays.asList('+', '-', '×', '÷', '^', '√', '.'));

    /*
    * The method through a loop finds each number in received expression and replaces it with the same but with digit dividers
     */
    protected static String addDividers(String str){
        str = removeDividers(str);

        Pattern pattern = Pattern.compile("\\d+\\.?\\d*");
        Matcher matcher = pattern.matcher(str);

        StringBuffer stringBuffer = new StringBuffer();

        while (matcher.find()){
            matcher.appendReplacement(stringBuffer,divide(matcher.group()));
        }

        matcher.appendTail(stringBuffer);
        return stringBuffer.toString();
    }

    /*
    * the method adds digit dividers into the received number
     */
    private static String divide (String str){
        StringBuilder stringBuilder;
        String tail = "";

        /*
        * The method checks whether the number contains dot
        * if yes, then split it around the dot
        * digits after dot will be added to the result at the end
        * if no, just convert the received number into stringBuilder
         */
        if (str.contains(".")) {
            String[] arrayString = str.split("\\.");
            stringBuilder = new StringBuilder(arrayString[0]);
            tail = ".";
            if (arrayString.length > 1) {
                tail = tail + arrayString[1];
            }
        } else {
            stringBuilder = new StringBuilder(str);
        }

        /*
        * The method goes through each character from the end to the begin within the stringBuilder
        * if the current character is not divider and
        * the next character is not divider and
        * it is not the end of char sequence and
        * the remains of the dividing the current count of digit in number on 3 is 0
        * then add digit divider
         */
        if (stringBuilder.length() > 3) {
            stringBuilder.reverse();
            int count = 0;
            for (int i = 0; i < stringBuilder.length(); i++) {
                if (stringBuilder.charAt(i) != ' ') {
                    count++;
                }
                if (i + 1 == stringBuilder.length() || count % 3 != 0 || stringBuilder.charAt(i + 1) == ' ' || stringBuilder.charAt(i) == ' ') continue;
                stringBuilder.insert(i + 1, ' ');
            }
            stringBuilder.reverse();
        }

        stringBuilder.append(tail);
        return stringBuilder.toString();
    }

    protected static String removeDividers (String str){
        return str.replaceAll(" ", "");
    }

    /*
    * The method checks whether the user's input is correct
    * The method check input from the beginning till the current cursor position
    * If the character to be added is digit, then input is correct
    * If it is operator but is either minus or square root, then input is correct
    * If it the first character and it is math operator other than minus or square root, then the input is not correct
    * Also the if it is dot to be added, the method goes through the last number and check whether this number also contains dot
    * if yes, then input is not correct
     */
    protected static boolean isCorrectInput (Editable editable, int cursorPosition, CharSequence charSequence){
        CharSequence editableLine = editable.subSequence(0, cursorPosition);
        char addedChar = charSequence.charAt(0);

        if(dotAndOperators.contains(addedChar)){
            if(addedChar == '-' || addedChar == '√'){
                return true;
            }
            else {
                if(editableLine.length() > 0) {
                    if (dotAndOperators.contains(editableLine.charAt(editableLine.length() - 1))) {
                        return false;
                    } else {
                        if (addedChar == '.') {
                            boolean isNumberContainsDot = false;

                            for (int i = editableLine.length() - 1; i >= 0; i--) {
                                if (editableLine.charAt(i) == '.') {
                                    isNumberContainsDot = true;
                                    break;
                                }

                                if (dotAndOperators.contains(editableLine.charAt(i))) {
                                    break;
                                }
                            }

                            return !isNumberContainsDot;
                        } else return true;
                    }
                }
                else return false;
            }
        }
        else return true;
    }
}
