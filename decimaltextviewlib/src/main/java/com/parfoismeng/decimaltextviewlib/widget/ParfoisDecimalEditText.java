package com.parfoismeng.decimaltextviewlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;

import com.parfoismeng.decimaltextviewlib.R;
import com.parfoismeng.decimaltextviewlib.listener.OnDecimalUpperListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Locale;

/**
 * <pre>
 *     author : parfoismeng
 *     e-mail : youshi520000@163.com
 *     time   : 2017/11/10
 *     desc   : do_数字EditText
 *     version: 1.0
 * </pre>
 */
public class ParfoisDecimalEditText extends AppCompatEditText {

    /**
     * 默认数字符号 // "¥"
     */
    private String mSymbol = "¥"; // Currency.getInstance(Locale.getDefault()).getSymbol();
    /**
     * 是否显示数字符号 默认true
     */
    private boolean mShowSymbol = true;
    /**
     * 是否显示数字分号 默认false
     */
    private boolean mShowCommas = false;

    /**
     * 上限数字 默认1000000
     */
    private double mUpperDecimal = 1000000;

    /**
     * 小数点后位数 默认2位
     */
    private int mDecimalScale = 2;

    /**
     * 小数点后是否用0填充 默认false
     */
    private boolean mDecimalFill = false;

    /**
     * 数字上限监听
     */
    private OnDecimalUpperListener onDecimalUpperListener;

    public ParfoisDecimalEditText(Context context) {
        super(context);
        initView(context, null);
    }

    public ParfoisDecimalEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setDecimalValue(getText().toString());
    }

    /**
     * 初始化
     * 自定义参数
     * 输入类型限制数字和小数点
     */
    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.ParfoisDecimalEditText, 0, 0);
            try {
                String symbol = attrArray.getString(R.styleable.ParfoisDecimalEditText_decimal_symbol);
                if (symbol != null) mSymbol = symbol;

                mShowSymbol = attrArray.getBoolean(R.styleable.ParfoisDecimalEditText_decimal_show_symbol, true);
                mShowCommas = attrArray.getBoolean(R.styleable.ParfoisDecimalEditText_decimal_show_commas, false);
                mUpperDecimal = attrArray.getFloat(R.styleable.ParfoisDecimalEditText_decimal_upper, 1000000);
                mDecimalScale = attrArray.getInteger(R.styleable.ParfoisDecimalEditText_decimal_scale, 2);
                mDecimalFill = attrArray.getBoolean(R.styleable.ParfoisDecimalEditText_decimal_fill_zero, false);
            } finally {
                attrArray.recycle();
            }
        }

        // 输入模式为数字小数点decimal // 只设置InputType.TYPE_NUMBER_FLAG_DECIMAL不行！！
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        // 文本变化监听
        addTextChangedListener(new TextWatcher() {
            double lastDecimal;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lastDecimal = formatDecimal2Double(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                removeTextChangedListener(this);

                int position = getSelectionStart(); // 记录光标位置 // setText后会丢失光标位置

                double decimalDouble = formatDecimal2Double(s.toString());
                if (decimalDouble >= mUpperDecimal) {
                    if (null != onDecimalUpperListener) onDecimalUpperListener.onDecimalUpper();

                    decimalDouble = lastDecimal;
                }

                String decimalString = formatDecimal2String(decimalDouble);
                String inputString = s.toString();
                if (inputString.endsWith(".") || inputString.endsWith(".0")) {
                    decimalString += ".0";
                }
                setText(decimalString);

                setSelection(Math.min(Math.max(mSymbol.length(), position), length())); // 取之前记录的光标位置与文本长度较小值 // 因为只取两位小数，最后位置输入时，记录的光标位置会超出文本长度，election会越界
                addTextChangedListener(this);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);

        try {
            setSelection(Math.min(Math.max(mSymbol.length(), selStart), length()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置数字
     */
    public void setDecimalValue(String decimalStr) {
        setText(formatDecimal2String(formatDecimal2Double(decimalStr)));
    }

    /**
     * 格式化数字 double2string
     */
    private String formatDecimal2String(double decimal) {
        String decimalScaleStr = "";
        String s = mDecimalFill ? "0" : "#";
        for (int i = 0; i < mDecimalScale; i++) {
            decimalScaleStr += s;
        }

        DecimalFormat formatter = (DecimalFormat) DecimalFormat.getInstance(Locale.getDefault());
        if (mShowCommas && !mShowSymbol) { // 显示数字分号 && 不显示数字符号
            formatter.applyPattern(",##0." + decimalScaleStr);
        } else if (mShowCommas) { // 显示数字分号 && 显示数字符号
            formatter.applyPattern(mSymbol + ",##0." + decimalScaleStr);
        } else if (mShowSymbol) { // 不显示数字分号 && 显示数字符号
            formatter.applyPattern(mSymbol + "#0." + decimalScaleStr);
        } else { // 不显示数字分号 && 不显示数字符号
            formatter.applyPattern("#0." + decimalScaleStr);
        }
        formatter.setRoundingMode(RoundingMode.DOWN);
        return formatter.format(decimal);
    }

    /**
     * 格式化数字 string2double
     */
    private double formatDecimal2Double(String decimalStr) {
        if (decimalStr.contains(",")) { // 移除逗号
            decimalStr = decimalStr.replace(",", "");
        }
        if (decimalStr.contains(mSymbol)) {
            decimalStr = decimalStr.replace(mSymbol, "");
        }

        Double decimal = 0d;
        try {
            decimal = Double.parseDouble(decimalStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return decimal;
    }

    public Double getDecimalValue() {
        return formatDecimal2Double(getText().toString());
    }

    public void setSymbol(String symbol) {
        this.mSymbol = symbol;
        setDecimalValue(getText().toString());
    }

    public String getSymbol() {
        return this.mSymbol;
    }

    public void setShowSymbol(boolean showSymbol) {
        this.mShowSymbol = showSymbol;
        setDecimalValue(getText().toString());
    }

    public boolean isShowSymbol() {
        return mShowSymbol;
    }

    public void setShowCommas(boolean showCommas) {
        this.mShowCommas = showCommas;
        setDecimalValue(getText().toString());
    }

    public boolean isShowCommas() {
        return mShowCommas;
    }

    public void setUpperDecimal(double upperDecimal) {
        this.mUpperDecimal = upperDecimal;
    }

    public double getUpperDecimal() {
        return this.mUpperDecimal;
    }

    public void setDecimalScale(int decimalScale) {
        this.mDecimalScale = decimalScale;
    }

    public int getDecimalScale() {
        return mDecimalScale;
    }

    public void setDecimalFill(boolean decimalFill) {
        this.mDecimalFill = decimalFill;
    }

    public boolean isDecimalFill() {
        return mDecimalFill;
    }

    public OnDecimalUpperListener getOnDecimalUpperListener() {
        return onDecimalUpperListener;
    }

    public void setOnDecimalUpperListener(OnDecimalUpperListener onDecimalUpperListener) {
        this.onDecimalUpperListener = onDecimalUpperListener;
    }
}