# [DecimalTextView](https://github.com/ParfoisMeng/DecimalTextView)
自定义TextView/EditText，实现显示(或输入)小数、数字前缀(¥$...)、千分符、最大值等。

***API > 14***

[![](https://jitpack.io/v/ParfoisMeng/DecimalTextView.svg)](https://jitpack.io/#ParfoisMeng/DecimalTextView)

----------

> ####DecimalTextView演示
![DecimalTextView演示](https://github.com/ParfoisMeng/DecimalTextView/blob/master/screenshots/demo1.gif)
> ####DecimalEditText演示
![DecimalEditText演示](https://github.com/ParfoisMeng/DecimalTextView/blob/master/screenshots/demo2.gif)

----------
  [![](https://jitpack.io/v/ParfoisMeng/DecimalTextView.svg)](https://jitpack.io/#ParfoisMeng/DecimalTextView)
```
	// 1.添加jitpack仓库
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
	// 2.添加项目依赖（last-version替换为最新版本号）
	dependencies {
		compile 'com.github.parfoismeng:decimaltextview:last-version'
	}
```

----------

- 属性说明
```
	<!-- 数字符号 默认¥ -->
	<attr name="decimal_symbol" format="string" />
	<!-- 数字符号的字体大小 默认与字体大小一致(-1) -->
	<attr name="decimal_symbol_size" format="dimension" />
	<!-- 是否显示数字符号 默认true -->
	<attr name="decimal_show_symbol" format="boolean" />
	<!-- 是否显示数字分号 默认false -->
	<attr name="decimal_show_commas" format="boolean" />
	<!-- 上限数字 默认1000000 -->
	<attr name="decimal_upper" format="float" />
	<!-- 小数点后位数 默认2位 -->
	<attr name="decimal_scale" format="integer" />
	<!-- 小数点后是否用0填充 默认false -->
	<attr name="decimal_fill_zero" format="boolean" />
```
- xml里直接引用
```
	<com.parfoismeng.decimaltextviewlib.widget.DecimalTextView
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        app:decimal_symbol="¥"
	        app:decimal_symbol_size="12sp"
	        app:decimal_show_symbol="true"
	        app:decimal_show_commas="false"
	        app:decimal_upper="1000000"
	        app:decimal_scale="2"
	        app:decimal_fill_zero="false" />

	<com.parfoismeng.decimaltextviewlib.widget.DecimalEditText
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        app:decimal_symbol="¥"
	        app:decimal_symbol_size="12sp"
	        app:decimal_show_symbol="true"
	        app:decimal_show_commas="false"
	        app:decimal_upper="1000000"
	        app:decimal_scale="2"
	        app:decimal_fill_zero="false" />
```
----------

> ####Demo下载地址：[https://fir.im/sajf](https://fir.im/sajf)
![Demo下载二维码](https://github.com/ParfoisMeng/DecimalTextView/blob/master/screenshots/qrcode.png)

----------

###更新记录
 1.  控件名移除“Parfois”字符，修复hint无效问题 fixbug [issues1](https://github.com/ParfoisMeng/DecimalTextView/issues/1)  ——  1.0.0（2018.12.18）
 2.  修复EditText输入问题 —— 0.0.8（2018.01.26）
 3.  修复0.00.0的问题 —— 0.0.4（2018.01.13）
 4.  添加数字符号可以设置字体大小 —— 0.0.3（2017.11.17）
 5.  更新README.md —— 0.0.2（2017.11.14）
 6.  初始提交 —— 0.0.1（2017.11.13）