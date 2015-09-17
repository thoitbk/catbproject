package catb.vanthu.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

public class XMLReader {
	
	@SuppressWarnings("rawtypes")
	public static Map<Integer, String> readFile(String fileName, String childNode) {
		SAXBuilder builder = new SAXBuilder();
		File file = new File(fileName);
		
		try {
			Document document = (Document) builder.build(file);
			Element rootNode = document.getRootElement();
			List list = rootNode.getChildren(childNode);
			Map<Integer, String> map = new HashMap<Integer, String>();
			
			for (int i = 0; i < list.size(); i++) {
				Element node = (Element) list.get(i);
				map.put(Integer.parseInt(node.getChildText("level")), node.getChildText("name"));
			}
			
			return map;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
}
