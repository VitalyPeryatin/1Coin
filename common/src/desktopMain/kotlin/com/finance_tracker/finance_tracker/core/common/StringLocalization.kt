package com.finance_tracker.finance_tracker.core.common

import androidx.compose.ui.res.useResource
import org.xml.sax.InputSource
import java.io.StringReader
import javax.xml.parsers.DocumentBuilderFactory

actual fun getLocalizedString(id: String, context: Context): String {
    val document = useResource("values/strings.xml") { stream ->
        val xml = stream.bufferedReader().readText()
        val factory = DocumentBuilderFactory.newInstance()
        val builder = factory.newDocumentBuilder()
        val inputStream = InputSource(StringReader(xml))
        builder.parse(inputStream)
    }
    val stringsNodeList = document.getElementsByTagName("string")
    for (elementIndex in 0 until stringsNodeList.length) {
        val element = stringsNodeList.item(elementIndex)
        if (element.attributes.getNamedItem("name").nodeValue == id) {
            return element.textContent
        }
    }
    return ""
}