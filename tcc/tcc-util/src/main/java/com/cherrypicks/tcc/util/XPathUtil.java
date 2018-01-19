package com.cherrypicks.tcc.util;

import java.io.IOException;
import java.io.StringWriter;

import javax.xml.xpath.XPathExpressionException;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

public class XPathUtil {

    private final Document doc;

    public XPathUtil(final String content) throws DocumentException, IOException {
        this.doc = DocumentHelper.parseText(content);
    }

    public String getText(final String expression) throws XPathExpressionException {
        final Node node = this.doc.selectSingleNode(expression);
        return node == null ? "" : node.getText();
    }

    public String getXml(final String expression) throws XPathExpressionException {

        final Node node = this.doc.selectSingleNode(expression);
        return node.asXML();
    }

    public String getEntityString(final String expression) {
        try {
            final StringWriter out = new StringWriter();
            final OutputFormat o = new OutputFormat();
            o.setExpandEmptyElements(true);

            final XMLWriter writer = new XMLWriter(out, o);
            final Node node = this.doc.selectSingleNode(expression);
            writer.write(node);
            writer.flush();

            return out.toString();
        } catch (final IOException e) {
            throw new RuntimeException("IOException while generating " + "textual representation: " + e.getMessage());
        }
    }
}
