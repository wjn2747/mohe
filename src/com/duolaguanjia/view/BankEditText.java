package com.duolaguanjia.view;

import android.content.Context;
import android.text.*;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by Administrator on 2015/12/18.
 */
public class BankEditText extends EditText
{

    public BankEditText(Context context) {
        super(context);
        init();
    }

    public BankEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BankEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private  void init()
    {

//设置监听器
        addTextChangedListener(   new TextWatcher() {
            int beforeTextLength = 0;
            int onTextLength = 0;
            boolean isChanged = false;

            int location = 0;// 记录光标的位置
            private char[] tempChar;
            private StringBuffer buffer = new StringBuffer();
            int konggeNumberB = 0;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
                beforeTextLength = s.length();
                if (buffer.length() > 0) {
                    buffer.delete(0, buffer.length());
                }
                konggeNumberB = 0;
                for (int i = 0; i < s.length(); i++) {
                    if (s.charAt(i) == ' ') {
                        konggeNumberB++;
                    }
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                onTextLength = s.length();
                buffer.append(s.toString());
                if (onTextLength == beforeTextLength || onTextLength <= 3
                        || isChanged) {
                    isChanged = false;
                    return;
                }
                isChanged = true;
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isChanged) {
                    location = getSelectionEnd();
                    int index = 0;
                    while (index < buffer.length()) {
                        if (buffer.charAt(index) == ' ') {
                            buffer.deleteCharAt(index);
                        } else {
                            index++;
                        }
                    }

                    index = 0;
                    int konggeNumberC = 0;
                    while (index < buffer.length()) {
                        if ((index == 4 || index == 9 || index == 14 || index == 19)) {
                            buffer.insert(index, ' ');
                            konggeNumberC++;
                        }
                        index++;
                    }

                    if (konggeNumberC > konggeNumberB) {
                        location += (konggeNumberC - konggeNumberB);
                    }

                    tempChar = new char[buffer.length()];
                    buffer.getChars(0, buffer.length(), tempChar, 0);
                    String str = buffer.toString();
                    if (location > str.length()) {
                        location = str.length();
                    } else if (location < 0) {
                        location = 0;
                    }
                   if (!TextUtils.isEmpty(str) && str.length()>4)
                   {
                       //除了最后4位 别的都为x
                       int  wndLength=str.length()-4;
                       for (int i = 0; i < wndLength; i++) {
                           String c=  str.substring(i,i+1);

                           if (!c.equalsIgnoreCase(" "))
                           {
                               //获取指定字符位置 例如取 替换c字符
                               int ii=str.indexOf(c);
                               String str2=String.valueOf(str.charAt(ii));
                               str=str.replaceFirst(str2,"*");
                           }
                       }

                   }
                    setText(str);
                    Editable etable = getText();
                    Selection.setSelection(etable, location);
                    isChanged = false;
                }
            }
        });

    }
}
