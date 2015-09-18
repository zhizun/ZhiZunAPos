package com.ch.epw.utils;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.zhizun.pos.bean.HostConfig;

public class PullHostConfigParser {
	/**
	 * 解析输入流 得到HostConfig对象集合
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	public HostConfig parse(XmlResourceParser parser) throws Exception {
		HostConfig hostConfig = null;

		// XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
		// XmlPullParser parser = factory.newPullParser();

		// XmlPullParser parser = Xml.newPullParser(); //
		// 由android.util.Xml创建一个XmlPullParser实例
		// parser.setInput(is, "UTF-8"); // 设置输入流 并指明编码方式

		int eventType = parser.getEventType();
		while (eventType != XmlResourceParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlResourceParser.START_TAG:
				if (parser.getName().equals("epeiwang_server")) {
					HostConfig.setEpeiwang_server(parser.nextText());
				} else if (parser.getName().equals("epeiwang_img_server")) {
					HostConfig.setEpeiwang_img_server(parser.nextText());
				} else if (parser.getName().equals("epeiwang_url")) {
					HostConfig.setEpeiwang_url(parser.nextText());
				}
				break;
			}
			eventType = parser.next();
		}
		return hostConfig;
	}

	// /**
	// * 序列化HostConfig对象集合 得到XML形式的字符串
	// * @param books
	// * @return
	// * @throws Exception
	// */
	// public String serialize(List<Book> books) throws Exception {
	// // XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
	// // XmlSerializer serializer = factory.newSerializer();
	//
	// XmlSerializer serializer = Xml.newSerializer(); //
	// 由android.util.Xml创建一个XmlSerializer实例
	// StringWriter writer = new StringWriter();
	// serializer.setOutput(writer); // 设置输出方向为writer
	// serializer.startDocument("UTF-8", true);
	// serializer.startTag("", "books");
	// for (Book book : books) {
	// serializer.startTag("", "book");
	// serializer.attribute("", "id", book.getId() + "");
	//
	// serializer.startTag("", "name");
	// serializer.text(book.getName());
	// serializer.endTag("", "name");
	//
	// serializer.startTag("", "price");
	// serializer.text(book.getPrice() + "");
	// serializer.endTag("", "price");
	//
	// serializer.endTag("", "book");
	// }
	// serializer.endTag("", "books");
	// serializer.endDocument();
	//
	// return writer.toString();
	// }
}
