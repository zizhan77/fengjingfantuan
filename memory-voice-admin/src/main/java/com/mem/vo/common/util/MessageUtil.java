package com.mem.vo.common.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;



public class MessageUtil {
	public static final String REQ_MESSAGE_TYPE_TEXT="text";
	public static final String REQ_MESSAGE_TYPE_IMAGE="image";
	public static final String REQ_MESSAGE_TYPE_VOICE="voice";
	public static final String REQ_MESSAGE_TYPE_VIDEO="video";
	public static final String REQ_MESSAGE_TYPE_LOCATION="location";
	public static final String REQ_MESSAGE_TYPE_LINK="link";
	
	public static final String REQ_MESSAGE_TYPE_EVENT="event";
	
	public static final String RESP_MESSAGE_TYPE_TEXT="text";
	public static final String RESP_MESSAGE_TYPE_IMAGE="image";
	public static final String RESP_MESSAGE_TYPE_VOICE="voice";
	public static final String RESP_MESSAGE_TYPE_VIDEO="video";
	public static final String RESP_MESSAGE_TYPE_MUSIC="music";
	public static final String RESP_MESSAGE_TYPE_LINK="link";
	public static final String RESP_MESSAGE_TYPE_NEWS="news";
	
	public static final String EVENT_TYPE_SUBSCRIBE="subscribe";
	public static final String EVENT_TYPE_UNSUBSCRIBE="unsubscribe";
	public static final String EVENT_TYPE_CLICK="CLICK";
	public static final String EVENT_TYPE_LOCATION="LOCATION";
	public static final String EVENT_TYPE_SCAN="SCAN";
	
	//自定义菜单拓展
	public static final String EVENT_TYPE_SCANCODE_PUSH="scancode_push";
	public static final String EVENT_TYPE_SCANCODE_WAITMSG="scancode_waitmsg";
	
	/**
	 * 明文模式下解析请求参数
	 * @author 苏雄伟
	 *	@version 创建时间：2017年3月1日  下午3:39:24
	 * @param request
	 * @return
	 * @throws Exception
	 * @throws IOException
	 */
	public static HashMap<String,String> parseXML(HttpServletRequest request) throws Exception, IOException{
		HashMap<String,String> map=new HashMap<String,String>();
		// 通过IO获得Document
		SAXReader reader = new SAXReader();
		Document doc = reader.read(request.getInputStream());
								
		//得到xml的根节点
		Element root=doc.getRootElement();
		recursiveParseXML(root,map);
		return map;
	}

	
	private static void recursiveParseXML(Element root,HashMap<String,String> map){
		//得到根节点的子节点列表
		List<Element> elementList=root.elements();
		//判断有没有子元素列表
		if(elementList.size()==0){
			map.put(root.getName(), root.getTextTrim());
		}
		else{
			//遍历
			for(Element e:elementList){
				recursiveParseXML(e,map);
			}
		}
	}
	private static XStream xstream = new XStream(new XppDriver() {
		public HierarchicalStreamWriter createWriter(Writer out) {
			return new PrettyPrintWriter(out) {
				// 对所有xml节点都增加CDATA标记
				boolean cdata = true;

				public void startNode(String name, Class clazz) {
					super.startNode(name, clazz);
				}

				protected void writeText(QuickWriter writer, String text) {
					if (cdata) {
						writer.write("<![CDATA[");
						writer.write(text);
						writer.write("]]>");
					} else {
						writer.write(text);
					}
				}
			};
		}
	});

	
}
