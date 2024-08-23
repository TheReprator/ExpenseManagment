package dev.reprator.accountbook.common.ui.resources

private val PATTERNS_REGEX = "%[\\d|.]*[sdf]".toRegex()

/**
 * Yes, this is gross, but it's the only way I could get it to work.
 * Related: https://youtrack.jetbrains.com/issue/KT-25506
 */
@Suppress("USELESS_CAST")
actual fun String.fmt(vararg args: Any?): String {
    val formats = PATTERNS_REGEX.findAll(this).map { it.groupValues.first() }.toList()

    var result = this

    formats.forEachIndexed { i, format ->
        val arg = args[i]
        
        val formatted = when (arg) {
            is Double ->  (arg as Double).toString()
            is Float -> (arg as Float).toString()
            is Int -> (arg as Int).toString()
            is Long -> (arg as Long).toString()
            else -> arg as String
        }
        result = result.replaceFirst(format, formatted)
    }

    // We put the string through stringWithFormat again, to remove any escaped characters
    return  result
}