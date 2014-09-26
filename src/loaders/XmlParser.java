package loaders;

import items.Container;
import items.Item;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utilities.Console;
import utilities.Console.in;
import utilities.ItemStats;
import utilities.PlayerStats;
import characters.Player;
import elements.Decoration;
import elements.Member;
import elements.Prop;

public class XmlParser {
	private static final int INT_VALUE_NONE = -420420420;
	private static final int INT_VALUE_FAIL = 0;
	private static Document dom, domBounds;
	private static LinkedList<Member> members = new LinkedList<Member>();
	private static HashMap<String, Bounds> bounds = new HashMap<String, Bounds>();

	public static void parseXmlFile(String fileName) {
		// get the factory
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try {

			// Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			// parse using builder to get DOM representation of the XML file
			dom = db.parse(ResourceLoader.getFile(fileName));

			domBounds = db.parse(ResourceLoader.getFile("bounds_" + fileName));

			parseDocument();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException se) {
			se.printStackTrace();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static void parseDocument() {
		// get the root element
		Element docEle = domBounds.getDocumentElement();
		docEle.normalize();
		System.out.println("Root element :" + docEle.getNodeName());

		NodeList bl = docEle.getElementsByTagName("Bound");
		if (bl != null && bl.getLength() > 0) {
			for (int i = 0; i < bl.getLength(); i++) {

				// get the employee element
				Element el = (Element) bl.item(i);

				// get the Employee object
				Bounds e = getBounds(el);

				// add it to list
				bounds.put(el.getAttribute("type"), e);
			}
		} else {
			Console.log("Bounds never ran!", in.INFO);
		}

		docEle = dom.getDocumentElement();
		docEle.normalize();
		System.out.println("Root element :" + docEle.getNodeName());

		NodeList nl = docEle.getElementsByTagName("Member");
		if (nl != null && nl.getLength() > 0) {
			for (int i = 0; i < nl.getLength(); i++) {

				// get the employee element
				Element el = (Element) nl.item(i);

				// get the Employee object
				Member e = getMember(el);

				if (e == null) {
					Console.log("Member is null!!", in.ERROR);
				} else {
					// add it to list
					members.add(e);
				}
			}
		} else {
			Console.log("Members never ran!", in.INFO);
		}
		Console.log("sizeof members: " + members.size(), in.INFO);
	}

	private static Bounds getBounds(Element data) {
		int bw, bh, bx, by;

		bx = getIntValue(data, "bx");
		by = getIntValue(data, "by");

		bw = getIntValue(data, "bw");
		bh = getIntValue(data, "bh");

		return new Bounds(bx, by, bw, bh);
	}

	/**
	 * I take an employee element and read the values in, create an Employee
	 * object and return it
	 */
	private static Member getMember(Element data) {
		// for each <employee> element get text or int values of
		// name ,id, age and name
		String name = getTextValue(data, "name");

		String type = data.getAttribute("type");
		String bs;
		int id, width, height;
		int bx, by, bw, bh;

		width = getIntValue(data, "width");
		height = getIntValue(data, "height");

		// TODO: fix parent code, currently doesn't work becuase getIntValue
		// returns 0 on purpose to fix item stat issues
		if (getIntValue(data, "parent") != INT_VALUE_NONE) {
			switch (type) {
			case "player":
				if (width == INT_VALUE_NONE) {
					width = TypeMaker.players.get(getIntValue(data, "parent"))
							.getWidth();
				}
				if (height == INT_VALUE_NONE) {
					height = TypeMaker.players.get(getIntValue(data, "parent"))
							.getHeight();
				}
				break;
			case "item":
				if (width == INT_VALUE_NONE) {
					width = TypeMaker.items.get(getIntValue(data, "parent"))
							.getWidth();
				}
				if (height == INT_VALUE_NONE) {
					height = TypeMaker.items.get(getIntValue(data, "parent"))
							.getHeight();
				}
				break;
			case "prop":
				if (width == INT_VALUE_NONE) {
					width = TypeMaker.props.get(getIntValue(data, "parent"))
							.getWidth();
				}
				if (height == INT_VALUE_NONE) {
					height = TypeMaker.props.get(getIntValue(data, "parent"))
							.getHeight();
				}
				break;
			case "decoration":
				if (width == INT_VALUE_NONE) {
					width = TypeMaker.decorations.get(
							getIntValue(data, "parent")).getWidth();
				}
				if (height == INT_VALUE_NONE) {
					height = TypeMaker.decorations.get(
							getIntValue(data, "parent")).getHeight();
				}
				break;
			default:

			}
		}

		if (getTextValue(data, "bound") == null) {
			bx = getIntValue(data, "bx");
			by = getIntValue(data, "by");

			bw = getIntValue(data, "bw");
			bh = getIntValue(data, "bh");
		} else {
			bs = getTextValue(data, "bound");
			Bounds b = bounds.get(bs.trim());
			bx = b.bx;
			by = b.by;
			bw = b.bw;
			bh = b.bh;
		}

		id = getIntValue(data, "id");

		int attk, def, speed, health, moveSpeed, mana, size;
		attk = getIntValue(data, "attk");
		def = getIntValue(data, "def");
		speed = getIntValue(data, "speed");
		health = getIntValue(data, "hp");
		moveSpeed = getIntValue(data, "movesp");
		mana = getIntValue(data, "mana");
		size = getIntValue(data, "size");

		String imgName;
		imgName = getTextValue(data, "img");
		// Images.load(imgName, EXT.NONE);

		switch (type) {
		case "Player":
			Player p = new Player();

			PlayerStats ps = new PlayerStats();

			p.setType(type);
			ps.attk = attk;
			ps.def = def;
			ps.speed = speed;
			ps.health = health;
			ps.moveSpeed = moveSpeed;
			ps.mana = mana;

			p.setImage(imgName);

			p.setStats(ps);
			p.setHealth(health);
			p.setName(name);

			p.setWidth(width);
			p.setHeight(height);

			p.setBounds(new Bounds(bx, by, bw, bh));

			TypeMaker.addPlayer(id, p);

			return p;
		case "Container":
			Container b = new Container(size);
			b.id = id;
			b.setImage(imgName);
			TypeMaker.addContainer(id, b);
			return b;
		case "Item":
			Item i = new Item();
			ItemStats is = new ItemStats();

			i.setType(type);
			is.size = size;
			is.attk = attk;
			is.def = def;
			is.speed = speed;
			is.health = health;
			is.moveSpeed = moveSpeed;
			is.mana = mana;
			i.setImage(imgName);

			i.setStats(is);

			i.setWidth(width);
			i.setHeight(height);

			i.setBounds(new Bounds(bx, by, bw, bh));
			i.setName(name);
			TypeMaker.addItem(id, i);
			return i;
		case "Prop":
			Prop prop = new Prop();

			prop.setType(type);

			prop.setImage(imgName);

			prop.setWidth(width);
			prop.setHeight(height);

			prop.setBounds(new Bounds(bx, by, bw, bh));
			prop.setName(name);

			TypeMaker.addProp(id, prop);
			return prop;
		case "Decoration":
			Decoration decor = new Decoration();

			decor.setType(type);

			decor.setImage(imgName);

			decor.setWidth(width);
			decor.setHeight(height);

			decor.setBounds(new Bounds(bx, by, bw, bh));
			decor.setName(name);

			TypeMaker.addDecoration(id, decor);
			return decor;
		default:
			return null;
		}
	}

	/**
	 * I take a xml element and the tag name, look for the tag and get the text
	 * content i.e for <employee><name>John</name></employee> xml snippet if the
	 * Element points to employee node and tagName is 'name' I will return John
	 */
	private static String getTextValue(Element ele, String tagName) {
		String textVal = null;
		NodeList nl = ele.getElementsByTagName(tagName);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
			textVal = el.getFirstChild().getNodeValue();
		}

		return textVal;
	}

	/**
	 * Calls getTextValue and returns a int value
	 */
	private static int getIntValue(Element ele, String tagName) {
		// in production application you would catch the exception
		int val = 0;
		try {
			if (getTextValue(ele, tagName) == null) {
				return 0;
			}
			val = Integer.parseInt(getTextValue(ele, tagName));
		} catch (Exception e) {
			Console.log(
					"Parse int failed! @getIntValue for tagName " + tagName,
					in.ERROR);
			return INT_VALUE_FAIL;
		}
		return val;
	}
}
