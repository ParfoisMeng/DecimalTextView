package com.parfoismeng.decimaltextviewlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.parfoismeng.decimaltextviewlib.R;

import java.text.DecimalFormat;
import java.util.Locale;

/**
 * <pre>
 *     author : parfoismeng
 *     e-mail : youshi520000@163.com
 *     time   : 2017/11/10
 *     desc   : do_数字TextView
 *     version: 1.0
 * </pre>
 */
public class ParfoisDecimalTextView extends AppCompatTextView {

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

    public ParfoisDecimalTextView(Context context) {
        super(context);
        initView(context, null);
    }

    public ParfoisDecimalTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    /**
     * 初始化
     * 获取自定义参数
     */
    private void initView(Context context, AttributeSet attrs) {
        if (attrs != null) {
            final TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.ParfoisDecimalTextView, 0, 0);
            try {
                String symbol = attrArray.getString(R.styleable.ParfoisDecimalTextView_decimal_symbol);
                if (symbol != null) mSymbol = symbol;

                mShowSymbol = attrArray.getBoolean(R.styleable.ParfoisDecimalTextView_decimal_show_symbol, true);
                mShowCommas = attrArray.getBoolean(R.styleable.ParfoisDecimalTextView_decimal_show_commas, false);
                mUpperDecimal = attrArray.getFloat(R.styleable.ParfoisDecimalTextView_decimal_upper, 1000000);
                mDecimalScale = attrArray.getInteger(R.styleable.ParfoisDecimalTextView_decimal_scale, 2);
                mDecimalFill = attrArray.getBoolean(R.styleable.ParfoisDecimalTextView_decimal_fill_zero, false);
            } finally {
                attrArray.recycle();
            }
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setDecimalValue(getText().toString());
    }

    /**
     * 设置数字
     */
    public void setDecimalValue(double decimal) {
        setText(formatDecimal2String(decimal));
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
        return this.mShowSymbol;
    }

    public void setShowCommas(boolean showCommas) {
        this.mShowCommas = showCommas;
        setDecimalValue(getText().toString());
    }

    public boolean isShowCommas() {
        return this.mShowCommas;
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
        return this.mDecimalScale;
    }

    public void setDecimalFill(boolean decimalFill) {
        this.mDecimalFill = decimalFill;
    }

    public boolean isDecimalFill() {
        return this.mDecimalFill;
    }
}