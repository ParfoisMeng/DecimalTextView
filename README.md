# DecimalTextView
自定义TextView/EditText，实现显示(或输入)小数、数字前缀(¥$...)、千分符、最大值等。

***API > 14***

----------

1. 添加jitpack仓库
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
2. 添加项目依赖
```
	dependencies {
		compile 'com.github.parfoismeng:decimaltextview:0.0.1'
	}
```

----------

- 属性说明
```
	<!-- 数字符号 默认¥ -->
	<attr name="decimal_symbol" format="string" />
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
	<com.parfoismeng.decimaltextviewlib.widget.ParfoisDecimalTextView
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        app:decimal_symbol="¥"
	        app:decimal_show_symbol="true"
	        app:decimal_show_commas="false"
	        app:decimal_upper="1000000"
	        app:decimal_scale="2"
	        app:decimal_fill_zero="false" />

	<com.parfoismeng.decimaltextviewlib.widget.ParfoisDecimalEditText
	        android:layout_width="match_parent"
	        android:layout_height="wrap_content"
	        app:decimal_symbol="¥"
	        app:decimal_show_symbol="true"
	        app:decimal_show_commas="false"
	        app:decimal_upper="1000000"
	        app:decimal_scale="2"
	        app:decimal_fill_zero="false" />
```