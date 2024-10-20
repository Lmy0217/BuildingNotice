## 模板字符串
#### 模板
* `{}` 代表顺序结构
	* 例如 `{abc}` 表示一个顺序结构，其中包含三个字母，顺序项被选择则其中包含的内容会被选择
* `[]` 代表选择结构
	* 例如 `[{a}{b}{c}]` 表示一个选择结构，其中包含三个选择项（注意：选择结构不能直接包含字符，而顺序结构能直接包含字符），根据给出的数值对选择项进行选择
* `()` 内包含选择分隔符，用于选择结构
	* 例如 `[(,){a}{b}{c}]` 表示选择结构在多选条件下用 `,` 分开被选择的选择项

#### 模板字符串
* 在 `;` 前的所有字符构成一个数字，表示之后的字符串按照这个数字的长度分割，分割后的每个子串构成一个数字，数字根据先后顺序依次使用来进行选择，对应的选择结构为模板中依次遇到的选择结构
	* 例如 `2;130105` 表示 `;` 之后的每两个字符构成一个数字，即 `13`, `1`, `5`
* 计算选择结构时，数字被转换成二进制来进行选择项选择
	* 例如选择结构 `[{a}{b}{c}]` 使用数字 `5` 来计算，则 `5` 被转换为二进制 `101`，从二进制的最低位 `1` 开始，对选择结构中的第一个选择项（即 `{a}`）进行判断，`1` 表示选中，`0` 表示不选中，则选中 `{a}`，接下来使用 `0` 对 `{b}` 进行判断，没有选中 `{b}`，最后使用 `1` 对 `{c}` 进行判断，选中 `{c}`，最终结果为 `ac`，若是数字转为二进制长度不够，则数字左边添 `0`
* 计算模板时，遇到选择结构则获取数字进行选择，之后计算被选择的选择项（即深度优先）
	* 例如模板 `[[{a}{b}]{c}]` 使用模板字符串 `1;32` 计算，首先计算最外层选择结构，获取数字 `3`  判断 `[{a}{b}]` 是否选中，选中，计算这个选择结构，使用获取数字 `2` 来判断 `{a}` 是否选中，不选中，然后判断 `{b}` 是否选中，选中，选择结构 `[{a}{b}]` 计算完毕，之后接着计算最外层选择结构，使用数字 `3` 判断 `{c}` 是否选中，选中，最终结果为 `bc`
* 对于相同的模板，根据选择不同，模板字符串长度可以不定