package com.linyp.weixin.util;

import com.thoughtworks.xstream.XStream;
import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by linyp on 2017/4/26.
 */
public class XmlConvertUtil {
    /**
     * xml转换为map对象
     * xml to map xml <node><key label="key1">value1</key><key
     * label="key2">value2</key>......</node>
     *
     * @param request
     * @return
     */
    public static Map<String, String> xmlToMap(HttpServletRequest request) throws IOException, DocumentException {
        Map<String, String> map = new HashMap<>();
        SAXReader saxReader = new SAXReader();
        InputStream inputStream = request.getInputStream();
        Document document = saxReader.read(inputStream);

        Element rootElement = document.getRootElement();

        List<Element> elements = rootElement.elements();
        elements.stream().forEach(element -> map.put(element.getName(), element.getText()));
        IOUtils.closeQuietly(inputStream);
        return map;
    }

    public static String toXml(Object obj, Map<String, Class> aliasMap) {
        XStream xStream = new XStream();
        // 别名替换
        for (Map.Entry<String, Class> entry: aliasMap.entrySet()) {
            xStream.alias(entry.getKey(), entry.getValue());
        }
        return xStream.toXML(obj);
    }
}
