package loaders;

import items.Container;
import items.Item;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import utilities.Console;
import utilities.Console.in;
import utilities.Stats;
import characters.Player;

import components.AttackComponent;
import components.GraphicsComponent;
import components.InteractComponent;
import components.ItemInteract;
import components.NPCInput;
import components.PhysicsComponent;

import elements.Decoration;
import elements.Member;
import elements.Prop;
import graphics.Animation;

public class XmlParser {
	private static final int INT_VALUE_NONE = -420420420;
	private static final int INT_VALUE_FAIL = 0;
	private static Document dom, domBounds;
	private static List<Member> members = new ArrayList<Member>();
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
		String name = getTextValue(data, "name");

		String type = data.getAttribute("type");
		String bs;
		int id, width, height;
		int bx, by, bw, bh;

		width = getIntValue(data, "width");
		height = getIntValue(data, "height");

		Bounds b;

		if (getTextValue(data, "bound") == null) {
			bx = getIntValue(data, "bx");
			by = getIntValue(data, "by");

			bw = getIntValue(data, "bw");
			bh = getIntValue(data, "bh");
			b = new Bounds(bx, by, bw, bh);
		} else {
			bs = getTextValue(data, "bound");
			b = bounds.get(bs.trim());
		}

		id = getIntValue(data, "id");

		int attack, def, speed, health, moveSpeed, mana, size;
		attack = getIntValue(data, "attack");
		def = getIntValue(data, "defence");
		speed = getIntValue(data, "speed");
		health = getIntValue(data, "health");
		moveSpeed = getIntValue(data, "movespeed");
		mana = getIntValue(data, "mana");
		size = getIntValue(data, "size");

		String imgName;
		imgName = getTextValue(data, "img");
		
		PhysicsComponent physics = new PhysicsComponent();
		
		physics.setBounds(b);
		
		GraphicsComponent graphics = new GraphicsComponent();
		
		graphics.setWidth(width);
		graphics.setHeight(height);
		
		Animation a = new Animation(0, false);
		a.addImage(imgName);
		graphics.addAnimation("default", a);
				
		switch (type) {
		case "Player":
			Player p = new Player(physics, graphics, new InteractComponent(), new AttackComponent(), new NPCInput());
			
			p.getAttack().setHealth(health);
			p.getAttack().setMana(mana);
			
			Stats ps = new Stats();
			
			p.setType(type);
			ps.attack = attack;
			ps.def = def;
			ps.speed = speed;
			ps.health = health;
			ps.moveSpeed = moveSpeed;
			ps.mana = mana;

			p.setStats(ps);
			
			p.setName(name);

			TypeMaker.addPlayer(id, p);

			return p;
		case "Container":
			Container container = new Container(physics, graphics, size);
			
			TypeMaker.addContainer(id, container);
			return container;
		case "Item":
			Item i = new Item(physics, graphics, new ItemInteract());
			
			Stats is = new Stats();

			i.setType(type);
			is.size = size;
			is.attack = attack;
			is.def = def;
			is.speed = speed;
			is.health = health;
			is.moveSpeed = moveSpeed;
			is.mana = mana;

			i.setStats(is);

			i.setName(name);
			TypeMaker.addItem(id, i);
			return i;
		case "Prop":
			Prop prop = new Prop(physics, graphics, new InteractComponent());
			
			prop.setType(type);
			prop.setName(name);

			TypeMaker.addProp(id, prop);
			return prop;
		case "Decoration":
			Decoration decor = new Decoration(physics, graphics);

			decor.setType(type);

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
