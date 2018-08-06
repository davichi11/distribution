@file:Suppress("unused")

package com.distribution.common.utils


import java.io.Serializable
import java.math.BigDecimal
import java.util.*


class Money : Serializable, Comparable<Any> {

    /**
     * 金额，以分为单位。
     */
    /**
     * 获取本货币对象代表的金额数。
     *
     * @return 金额数，以分为单位。
     */
    /**
     * 设置货币的分值。
     *
     * @param l
     */
    var cent: Long = 0

    /**
     * 币种。
     */
    /**
     * 获取本货币对象代表的币种。
     *
     * @return 本货币对象所代表的币种。
     */
    var currency: Currency? = null
        private set

    // Bean方法 ====================================================

    /**
     * 获取本货币对象代表的金额数。
     *
     * @return 金额数，以元为单位。
     */
    /**
     * 设置本货币对象代表的金额数。
     *
     * @param amount 金额数，以元为单位。
     */
    var amount: BigDecimal?
        get() = BigDecimal.valueOf(cent, currency!!.defaultFractionDigits)
        set(amount) {
            if (amount != null) {
                cent = rounding(amount.movePointRight(2), BigDecimal.ROUND_HALF_EVEN)
            }
        }

    /**
     * 获取本货币币种的元/分换算比率。
     *
     * @return 本货币币种的元/分换算比率。
     */
    val centFactor: Int
        get() = centFactors[currency!!.defaultFractionDigits]

    /**
     * 构造器。
     *
     *
     *
     * 创建一个具有金额`yuan`元`cent`分和指定币种的货币对象。
     *
     * @param yuan 金额元数。
     * @param cent 金额分数。
     */
    @JvmOverloads constructor(yuan: Long, cent: Int, currency: Currency = Currency.getInstance(DEFAULT_CURRENCY_CODE)) {
        this.currency = currency

        this.cent = yuan * centFactor + cent % centFactor
    }

    /**
     * 构造器。
     *
     *
     *
     * 创建一个具有金额`amount`元和指定币种`currency`的货币对象。
     *
     * @param amount   金额，以元为单位。
     * @param currency 币种。
     */
    @JvmOverloads constructor(amount: String, currency: Currency = Currency.getInstance(DEFAULT_CURRENCY_CODE)) : this(BigDecimal(amount), currency)

    /**
     * 构造器。
     *
     *
     *
     * 创建一个具有金额`amount`元和指定币种`currency`的货币对象。
     * 如果金额不能转换为整数分，则使用指定的取整模式`roundingMode`取整。
     *
     * @param amount       金额，以元为单位。
     * @param currency     币种。
     * @param roundingMode 取整模式。
     */
    constructor(amount: String, currency: Currency, roundingMode: Int) : this(BigDecimal(amount), currency, roundingMode)

    /**
     * 构造器。
     *
     *
     *
     * 创建一个具有金额`amount`和指定币种的货币对象。
     * 如果金额不能转换为整数分，则使用四舍五入方式取整。
     *
     *
     *
     * 注意：由于double类型运算中存在误差，使用四舍五入方式取整的
     * 结果并不确定，因此，应尽量避免使用double类型创建货币类型。
     * 例：
     * `
     * assertEquals(999, Math.round(9.995 * 100));
     * assertEquals(1000, Math.round(999.5));
     * money = new Money((9.995));
     * assertEquals(999, money.getCent());
     * money = new Money(10.005);
     * assertEquals(1001, money.getCent());
    ` *
     *
     * @param amount   金额，以元为单位。
     * @param currency 币种。
     */
    @JvmOverloads constructor(amount: Double = 0.0, currency: Currency = Currency.getInstance(DEFAULT_CURRENCY_CODE)) {
        this.currency = currency
        this.cent = Math.round(amount * centFactor)
    }

    /**
     * 构造器。
     *
     *
     *
     * 创建一个具有参数`amount`指定金额和缺省币种的货币对象。
     * 如果金额不能转换为整数分，则使用指定的取整模式`roundingMode`取整。
     *
     * @param amount       金额，以元为单位。
     * @param roundingMode 取整模式
     */
    constructor(amount: BigDecimal, roundingMode: Int) : this(amount, Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode)

    /**
     * 构造器。
     *
     *
     *
     * 创建一个具有金额`amount`和指定币种的货币对象。
     * 如果金额不能转换为整数分，则使用指定的取整模式`roundingMode`取整。
     *
     * @param amount       金额，以元为单位。
     * @param currency     币种。
     * @param roundingMode 取整模式。
     */
    @JvmOverloads constructor(amount: BigDecimal, currency: Currency = Currency.getInstance(DEFAULT_CURRENCY_CODE), roundingMode: Int = DEFAULT_ROUNDING_MODE) {
        this.currency = currency
        this.cent = rounding(amount.movePointRight(currency.defaultFractionDigits),
                roundingMode)
    }

    // 基本对象方法 ===================================================

    /**
     * 判断本货币对象与另一对象是否相等。
     *
     *
     *
     * 本货币对象与另一对象相等的充分必要条件是：<br></br>
     *
     *  * 另一对象也属货币对象类。
     *  * 金额相同。
     *  * 币种相同。
     *
     *
     * @param other 待比较的另一对象。
     * @return `true`表示相等，`false`表示不相等。
     * @see Object.equals
     */
    override fun equals(other: Any?): Boolean {
        return other is Money && equals(other as Money?)
    }

    /**
     * 判断本货币对象与另一货币对象是否相等。
     *
     *
     *
     * 本货币对象与另一货币对象相等的充分必要条件是：<br></br>
     *
     *  * 金额相同。
     *  * 币种相同。
     *
     *
     * @param other 待比较的另一货币对象。
     * @return `true`表示相等，`false`表示不相等。
     */
    fun equals(other: Money): Boolean {
        return currency == other.currency && cent == other.cent
    }

    /**
     * 计算本货币对象的杂凑值。
     *
     * @return 本货币对象的杂凑值。
     * @see Object.hashCode
     */
    override fun hashCode(): Int {
        return (cent xor cent.ushr(32)).toInt()
    }

    // Comparable接口 ========================================

    /**
     * 对象比较。
     *
     *
     *
     * 比较本对象与另一对象的大小。
     * 如果待比较的对象的类型不是`Money`，则抛出`java.lang.ClassCastException`。
     * 如果待比较的两个货币对象的币种不同，则抛出`java.lang.IllegalArgumentException`。
     * 如果本货币对象的金额少于待比较货币对象，则返回-1。
     * 如果本货币对象的金额等于待比较货币对象，则返回0。
     * 如果本货币对象的金额大于待比较货币对象，则返回1。
     *
     * @param other 另一对象。
     * @return -1表示小于，0表示等于，1表示大于。
     * @throws ClassCastException 待比较货币对象不是`Money`。
     * IllegalArgumentException 待比较货币对象与本货币对象的币种不同。
     * @see Comparable.compareTo
     */
    override fun compareTo(other: Any): Int {
        return compareTo(other as Money)
    }

    /**
     * 货币比较。
     *
     *
     *
     * 比较本货币对象与另一货币对象的大小。
     * 如果待比较的两个货币对象的币种不同，则抛出`java.lang.IllegalArgumentException`。
     * 如果本货币对象的金额少于待比较货币对象，则返回-1。
     * 如果本货币对象的金额等于待比较货币对象，则返回0。
     * 如果本货币对象的金额大于待比较货币对象，则返回1。
     *
     * @param other 另一对象。
     * @return -1表示小于，0表示等于，1表示大于。
     * @throws IllegalArgumentException 待比较货币对象与本货币对象的币种不同。
     */
    operator fun compareTo(other: Money): Int {
        assertSameCurrencyAs(other)

        return when {
            cent < other.cent -> -1
            cent == other.cent -> 0
            else -> 1
        }
    }

    /**
     * 货币比较。
     *
     *
     *
     * 判断本货币对象是否大于另一货币对象。
     * 如果待比较的两个货币对象的币种不同，则抛出`java.lang.IllegalArgumentException`。
     * 如果本货币对象的金额大于待比较货币对象，则返回true，否则返回false。
     *
     * @param other 另一对象。
     * @return true表示大于，false表示不大于（小于等于）。
     * @throws IllegalArgumentException 待比较货币对象与本货币对象的币种不同。
     */
    fun greaterThan(other: Money): Boolean {
        return compareTo(other) > 0
    }

    // 货币算术 ==========================================

    /**
     * 货币加法。
     *
     *
     *
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为
     * 两货币对象金额之和，本货币对象的值不变。
     * 如果两货币对象币种不同，抛出`java.lang.IllegalArgumentException`。
     *
     * @param other 作为加数的货币对象。
     * @return 相加后的结果。
     * @throws IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    fun add(other: Money): Money {
        assertSameCurrencyAs(other)

        return newMoneyWithSameCurrency(cent + other.cent)
    }

    /**
     * 货币累加。
     *
     *
     *
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之和，并返回本货币对象的引用。
     * 如果两货币对象币种不同，抛出`java.lang.IllegalArgumentException`。
     *
     * @param other 作为加数的货币对象。
     * @return 累加后的本货币对象。
     * @throws IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    fun addTo(other: Money): Money {
        assertSameCurrencyAs(other)

        this.cent += other.cent

        return this
    }

    /**
     * 货币减法。
     *
     *
     *
     * 如果两货币币种相同，则返回一个新的相同币种的货币对象，其金额为
     * 本货币对象的金额减去参数货币对象的金额。本货币对象的值不变。
     * 如果两货币币种不同，抛出`java.lang.IllegalArgumentException`。
     *
     * @param other 作为减数的货币对象。
     * @return 相减后的结果。
     * @throws IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    fun subtract(other: Money): Money {
        assertSameCurrencyAs(other)

        return newMoneyWithSameCurrency(cent - other.cent)
    }

    /**
     * 货币累减。
     *
     *
     *
     * 如果两货币币种相同，则本货币对象的金额等于两货币对象金额之差，并返回本货币对象的引用。
     * 如果两货币币种不同，抛出`java.lang.IllegalArgumentException`。
     *
     * @param other 作为减数的货币对象。
     * @return 累减后的本货币对象。
     * @throws IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    fun subtractFrom(other: Money): Money {
        assertSameCurrencyAs(other)

        this.cent -= other.cent

        return this
    }

    /**
     * 货币乘法。
     *
     *
     *
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。
     *
     * @param val 乘数
     * @return 乘法后的结果。
     */
    fun multiply(`val`: Long): Money {
        return newMoneyWithSameCurrency(cent * `val`)
    }

    /**
     * 货币累乘。
     *
     *
     *
     * 本货币对象金额乘以乘数，并返回本货币对象。
     *
     * @param val 乘数
     * @return 累乘后的本货币对象。
     */
    fun multiplyBy(`val`: Long): Money {
        this.cent *= `val`

        return this
    }

    /**
     * 货币乘法。
     *
     *
     *
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，则四舍五入。
     *
     * @param val 乘数
     * @return 相乘后的结果。
     */
    fun multiply(`val`: Double): Money {
        return newMoneyWithSameCurrency(Math.round(cent * `val`))
    }

    /**
     * 货币乘法。
     *
     *
     *
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。进一取整
     *
     * @param val 乘数
     * @return 相乘后的结果。
     */
    fun multiplyCeil(`val`: Double): Money {
        return newMoneyWithSameCurrency(Math.round(cent * `val`))
    }

    /**
     * 货币累乘。
     *
     *
     *
     * 本货币对象金额乘以乘数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，则使用四舍五入。
     *
     * @param val 乘数
     * @return 累乘后的本货币对象。
     */
    fun multiplyBy(`val`: Double): Money {
        this.cent = Math.round(this.cent * `val`)

        return this
    }

    /**
     * 货币乘法。
     *
     *
     *
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额乘以乘数。
     * 本货币对象的值不变。如果相乘后的金额不能转换为整数分，使用指定的取整方式
     * `roundingMode`进行取整。
     *
     * @param val          乘数
     * @param roundingMode 取整方式
     * @return 相乘后的结果。
     */
    @JvmOverloads
    fun multiply(`val`: BigDecimal, roundingMode: Int = DEFAULT_ROUNDING_MODE): Money {
        val newCent = BigDecimal.valueOf(cent).multiply(`val`)

        return newMoneyWithSameCurrency(rounding(newCent, roundingMode))
    }

    /**
     * 货币累乘。
     *
     *
     *
     * 本货币对象金额乘以乘数，并返回本货币对象。
     * 如果相乘后的金额不能转换为整数分，使用指定的取整方式
     * `roundingMode`进行取整。
     *
     * @param val          乘数
     * @param roundingMode 取整方式
     * @return 累乘后的结果。
     */
    @JvmOverloads
    fun multiplyBy(`val`: BigDecimal, roundingMode: Int = DEFAULT_ROUNDING_MODE): Money {
        val newCent = BigDecimal.valueOf(cent).multiply(`val`)

        this.cent = rounding(newCent, roundingMode)

        return this
    }

    /**
     * 货币除法。
     *
     *
     *
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
     *
     * @param val 除数
     * @return 相除后的结果。
     */
    fun divide(`val`: Double): Money {
        return newMoneyWithSameCurrency(Math.round(cent / `val`))
    }

    /**
     * 货币累除。
     *
     *
     *
     * 本货币对象金额除以除数，并返回本货币对象。
     * 如果相除后的金额不能转换为整数分，使用四舍五入方式取整。
     *
     * @param val 除数
     * @return 累除后的结果。
     */
    fun divideBy(`val`: Double): Money {
        this.cent = Math.round(this.cent / `val`)

        return this
    }

    /**
     * 货币除法。
     *
     *
     *
     * 返回一个新的货币对象，币种与本货币对象相同，金额为本货币对象的金额除以除数。
     * 本货币对象的值不变。如果相除后的金额不能转换为整数分，使用指定的取整模式
     * `roundingMode`进行取整。
     *
     * @param val          除数
     * @param roundingMode 取整
     * @return 相除后的结果。
     */
    @JvmOverloads
    fun divide(`val`: BigDecimal, roundingMode: Int = DEFAULT_ROUNDING_MODE): Money {
        val newCent = BigDecimal.valueOf(cent).divide(`val`, roundingMode)

        return newMoneyWithSameCurrency(newCent.toLong())
    }

    /**
     * 货币累除。
     *
     *
     *
     * 本货币对象金额除以除数，并返回本货币对象。
     * 如果相除后的金额不能转换为整数分，使用指定的取整模式
     * `roundingMode`进行取整。
     *
     * @param val 除数
     * @return 累除后的结果。
     */
    @JvmOverloads
    fun divideBy(`val`: BigDecimal, roundingMode: Int = DEFAULT_ROUNDING_MODE): Money {
        val newCent = BigDecimal.valueOf(cent).divide(`val`, roundingMode)

        this.cent = newCent.toLong()

        return this
    }

    /**
     * 货币分配。
     *
     *
     *
     * 将本货币对象尽可能平均分配成`targets`份。
     * 如果不能平均分配尽，则将零头放到开始的若干份中。分配
     * 运算能够确保不会丢失金额零头。
     *
     * @param targets 待分配的份数
     * @return 货币对象数组，数组的长度与分配份数相同，数组元素
     * 从大到小排列，所有货币对象的金额最多只相差1分。
     */
    fun allocate(targets: Int): Array<Money?> {
        val results = arrayOfNulls<Money>(targets)

        val lowResult = newMoneyWithSameCurrency(cent / targets)
        val highResult = newMoneyWithSameCurrency(lowResult.cent + 1)

        val remainder = cent.toInt() % targets

        for (i in 0 until remainder) {
            results[i] = highResult
        }

        for (i in remainder until targets) {
            results[i] = lowResult
        }

        return results
    }

    /**
     * 货币分配。
     *
     *
     *
     * 将本货币对象按照规定的比例分配成若干份。分配所剩的零头
     * 从第一份开始顺序分配。分配运算确保不会丢失金额零头。
     *
     * @param ratios 分配比例数组，每一个比例是一个长整型，代表
     * 相对于总数的相对数。
     * @return 货币对象数组，数组的长度与分配比例数组的长度相同。
     */
    fun allocate(ratios: LongArray): Array<Money?> {
        val results = arrayOfNulls<Money>(ratios.size)

        var total: Long = 0

        for (i in ratios.indices) {
            total += ratios[i]
        }

        var remainder = cent

        for (i in results.indices) {
            results[i] = newMoneyWithSameCurrency(cent * ratios[i] / total)
            remainder -= results[i]!!.cent
        }

        for (i in 0 until remainder) {
            results[i.toInt()]!!.cent++
        }

        return results
    }

    // 格式化方法 =================================================

    /**
     * 生成本对象的缺省字符串表示
     */
    override fun toString(): String {
        return amount.toString()
    }

    // 内部方法 ===================================================

    /**
     * 断言本货币对象与另一货币对象是否具有相同的币种。
     *
     *
     *
     * 如果本货币对象与另一货币对象具有相同的币种，则方法返回。
     * 否则抛出运行时异常`java.lang.IllegalArgumentException`。
     *
     * @param other 另一货币对象
     * @throws IllegalArgumentException 如果本货币对象与另一货币对象币种不同。
     */
    protected fun assertSameCurrencyAs(other: Money) {
        if (currency != other.currency) {
            throw IllegalArgumentException("Money math currency mismatch.")
        }
    }

    /**
     * 对BigDecimal型的值按指定取整方式取整。
     *
     * @param val          待取整的BigDecimal值
     * @param roundingMode 取整方式
     * @return 取整后的long型值
     */
    protected fun rounding(`val`: BigDecimal, roundingMode: Int): Long {
        return `val`.setScale(0, roundingMode).toLong()
    }

    /**
     * 创建一个币种相同，具有指定金额的货币对象。
     *
     * @param cent 金额，以分为单位
     * @return 一个新建的币种相同，具有指定金额的货币对象
     */
    protected fun newMoneyWithSameCurrency(cent: Long): Money {
        val money = Money()

        money.cent = cent

        return money
    }

    // 调试方式 ==================================================

    /**
     * 生成本对象内部变量的字符串表示，用于调试。
     *
     * @return 本对象内部变量的字符串表示。
     */
    fun dump(): String {
        val lineSeparator = System.getProperty("line.separator")

        val sb = StringBuffer()

        sb.append("cent = ").append(cent).append(lineSeparator)
        sb.append("currency = ").append(currency)

        return sb.toString()
    }

    companion object {

        /**
         * Comment for `serialVersionUID`
         */
        private const val serialVersionUID = 6009335074727417445L

        /**
         * 缺省的币种代码，为CNY（人民币）。
         */
        val DEFAULT_CURRENCY_CODE = "CNY"

        /**
         * 缺省的取整模式，为`BigDecimal.ROUND_HALF_EVEN
         * （四舍五入，当小数为0.5时，则取最近的偶数）。
        ` */
        val DEFAULT_ROUNDING_MODE = BigDecimal.ROUND_HALF_EVEN

        /**
         * 一组可能的元/分换算比例。
         *
         *
         *
         * 此处，“分”是指货币的最小单位，“元”是货币的最常用单位，
         * 不同的币种有不同的元/分换算比例，如人民币是100，而日元为1。
         */
        private val centFactors = intArrayOf(1, 10, 100, 1000)
    }


    //    public static void main(String[] arg){
    //    	Long[] l = {15l,15l,15l,16l,16l,16l};
    //    	double[] d = {0.005d,0.01d,0.01d,0.005d,0.01d,0.01d};
    //    	Money money = new Money();
    //    	for(int i = 0; i < l.length;i++){
    //    		money.setCent(l[i]);
    //    		money = money.multiplyCeil(d[i]);
    //    		System.out.println(money.getCent());
    //    	}
    //    }
}

