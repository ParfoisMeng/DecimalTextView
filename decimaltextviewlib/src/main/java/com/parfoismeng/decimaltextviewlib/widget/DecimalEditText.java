package com.parfoismeng.decimaltextviewlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatEditText;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.AbsoluteSizeSpan;
import android.util.AttributeSet;
import android.util.TypedValue;

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
public class DecimalEditText extends AppCompatEditText {

    /**
     * 默认数字符号 // "¥"
     */
    private String mSymbol = "¥"; // Currency.getInstance(Locale.getDefault()).getSymbol();

    /**
     * 数字符号的字体大小 默认与字体大小一致(-1)
     */
    private float mSymbolSize = 0;

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

    public DecimalEditText(Context context) {
        super(context);
        initView(context, null);
    }

    public DecimalEditText(Context context, AttributeSet attrs) {
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
            final TypedArray attrArray = context.obtainStyledAttributes(attrs, R.styleable.DecimalEditText, 0, 0);
            try {
                String symbol = attrArray.getString(R.styleable.DecimalEditText_decimal_symbol);
                if (symbol != null) mSymbol = symbol;
                mSymbolSize = attrArray.getDimensionPixelSize(R.styleable.DecimalEditText_decimal_symbol_size, 0);
                mShowSymbol = attrArray.getBoolean(R.styleable.DecimalEditText_decimal_show_symbol, true);
                mShowCommas = attrArray.getBoolean(R.styleable.DecimalEditText_decimal_show_commas, false);
                mUpperDecimal = attrArray.getFloat(R.styleable.DecimalEditText_decimal_upper, 1000000);
                mDecimalScale = attrArray.getInteger(R.styleable.DecimalEditText_decimal_scale, 2);
                mDecimalFill = attrArray.getBoolean(R.styleable.DecimalEditText_decimal_fill_zero, false);
            } finally {
                attrArray.recycle();
            }
        }

        // 输入模式为数字小数点decimal // 只设置InputType.TYPE_NUMBER_FLAG_DECIMAL不行！！
        setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        // 文本变化监听
        addTextChangedListener(new TextWatcher() {
            String lastDecimalStr;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                lastDecimalStr = s.toString();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //如果当前text为空则直接return，不进入监听Decimal处理
                if (null == s || "".contentEquals(s)) return;

                removeTextChangedListener(this); //移除监听 //防止重复监听不断

                //记录光标位置 //setText后会丢失光标位置
                int position = getSelectionStart();
                //最大值判断
                double decimalDouble = formatDecimal2Double(s.toString());
                if (decimalDouble >= mUpperDecimal) {
                    if (null != onDecimalUpperListener) onDecimalUpperListener.onDecimalUpper();
                    //赋上次的值(格式化)
                    setDecimalValue(lastDecimalStr);
                } else {
                    String inputString = s.toString().replace(mSymbol, "");
                    //小数点后位数判断
                    if (inputString.contains(".") && inputString.lastIndexOf(".") + 1 + mDecimalScale < inputString.length()) {
                        inputString = inputString.substring(0, inputString.lastIndexOf(".") + 1 + mDecimalScale);
                    }
                    //开头0判断
                    while (!inputString.startsWith("0.") && !inputString.equals("0") && inputString.startsWith("0")) {
                        //开头是"0."或者就是"0"或者开头不是"0"，跳出循环
                        if (inputString.startsWith("0") && !inputString.startsWith("0.")) {
                            inputString = inputString.substring(1);
                        }
                    }
                    //前缀符号(大小)设置
                    SpannableStringBuilder result = new SpannableStringBuilder(inputString);
                    if (mShowSymbol) {
                        result.insert(0, mSymbol);
                        if (mSymbolSize == 0) mSymbolSize = getTextSize();
                        result.setSpan(new AbsoluteSizeSpan((int) mSymbolSize), 0, mSymbol.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    }
                    //检查过检值是否为前缀并对应赋值
                    if (mSymbol.contentEquals(result)) {
                        setText("");
                    } else {
                        setText(result);
                    }
                }

                //取之前记录的光标位置与文本长度较小值 //因为只取两位小数，最后位置输入时，记录的光标位置会超出文本长度，election会越界
                setSelection(Math.min(Math.max(mShowSymbol ? mSymbol.length() : 0, position), length()));

                addTextChangedListener(this); //恢复监听
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
            setSelection(Math.min(Math.max(mShowSymbol ? mSymbol.length() : 0, selStart), length()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置数字
     */
    public void setDecimalValue(Double decimal) {
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
    private CharSequence formatDecimal2String(Double decimal) {
        if (null == decimal) {
            return null;
        }

        StringBuilder decimalScaleStr = new StringBuilder();
        String s = mDecimalFill ? "0" : "#";
        for (int i = 0; i < mDecimalScale; i++) {
            decimalScaleStr.append(s);
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

        SpannableStringBuilder result = new SpannableStringBuilder(formatter.format(decimal));
        if (mShowSymbol) {
            if (mSymbolSize == 0) mSymbolSize = getTextSize();
            result.setSpan(new AbsoluteSizeSpan((int) mSymbolSize), 0, mSymbol.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }

        return result;
    }

    /**
     * 格式化数字 string2double
     */
    private Double formatDecimal2Double(String decimalStr) {
        if (null == decimalStr || "".equals(decimalStr)) {
            return null;
        }

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
        Double value = formatDecimal2Double(getText().toString());
        this.mSymbol = symbol;
        setDecimalValue(value);
    }

    public String getSymbol() {
        return this.mSymbol;
    }

    public void setSymbolSize(float symbolSize) {
        setSymbolSize(symbolSize, TypedValue.COMPLEX_UNIT_SP);
    }

    public void setSymbolSize(float symbolSize, int unit) {
        this.mSymbolSize = TypedValue.applyDimension(unit, symbolSize, getResources().getDisplayMetrics());
        setDecimalValue(getText().toString());
    }

    public float getSymbolSize() {
        return this.mSymbolSize;
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
        setDecimalValue(getText().toString());
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