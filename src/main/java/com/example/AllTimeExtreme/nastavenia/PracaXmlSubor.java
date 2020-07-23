package com.example.AllTimeExtreme.nastavenia;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
/**
 * pomocna trieda pre pracu s xml subormi... prevedie xml subor do stromovej struktury, dokaze vytahovat textContent z tagu, alebo atributy z tagu, zrychluje parsovanie a pod.
 * @author omen
 *
 */
public class PracaXmlSubor {
	
	public static Integer vratIntegerXmlPodlaTagu(String xmlTag, Document doc) {
		if (doc.getElementsByTagName(xmlTag).getLength() > 0) {
			if (doc.getElementsByTagName(xmlTag).item(0) != null) {
				if ( (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("")) && (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("N/A")) ) {
					return TypoveKonverzie.prevedRetazecNaInteger( doc.getElementsByTagName(xmlTag).item(0).getTextContent() );
				} else {
				}
			} else {
			}
		}
		return null;
	}

	public static String vratStringXmlPodlaTagu(String xmlTag, Document doc) {
		if (doc.getElementsByTagName(xmlTag).getLength() > 0) {
			if (doc.getElementsByTagName(xmlTag).item(0) != null) {
				if ( (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("")) && (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("N/A")) ) {
					return doc.getElementsByTagName(xmlTag).item(0).getTextContent();
				} else {
				}
			} else {
			}
		}
		return null;
	}
	
	public static Double vratDoubleXmlPodlaTagu(String xmlTag, Document doc) {
		if (doc.getElementsByTagName(xmlTag).getLength() > 0) {
			if (doc.getElementsByTagName(xmlTag).item(0) != null) {
				if ( (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("")) && (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("N/A")) ) {
					return TypoveKonverzie.prevedRetazecNaDouble( doc.getElementsByTagName(xmlTag).item(0).getTextContent() );
				} else {
				}
			} else {
			}
		}
		return null;
	}
	
	public static Boolean vratBooleanXmlPodlaTagu(String xmlTag, Document doc) {
		if (doc.getElementsByTagName(xmlTag).getLength() > 0) {
			if (doc.getElementsByTagName(xmlTag).item(0) != null) {
				if ( (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("")) && (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("N/A")) ) {
					return Boolean.valueOf( doc.getElementsByTagName(xmlTag).item(0).getTextContent() );
				} else {
				}
			} else {
			}
		}
		return null;
	}
	
	public static Float vratFloatXmlPodlaTagu(String xmlTag, Document doc) {
		if (doc.getElementsByTagName(xmlTag).getLength() > 0) {
			if (doc.getElementsByTagName(xmlTag).item(0) != null) {
				if ( (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("")) && (!doc.getElementsByTagName(xmlTag).item(0).getTextContent().equals("N/A")) ) {
					String hodnota_str = doc.getElementsByTagName(xmlTag).item(0).getTextContent();
					if (hodnota_str != null)
						if (hodnota_str.equals("Infinity"))
							hodnota_str = "";
					return TypoveKonverzie.prevedRetazecNaFloat( hodnota_str );
				} else {
				}
			} else {
			}
		}
		return null;
	}
	
	public static String vratHodnotuTagu(Node tag) {
		if ( (tag.getTextContent() != null) && (!tag.getTextContent().equals("N/A")) && (!tag.getTextContent().equals("")) ) {
			return tag.getTextContent();
		}
		return null;
	}
	
	public static Document vratXMLvStromovejStrukture(String nazov) throws ParserConfigurationException, SAXException, IOException, MalformedURLException {
		Document result = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		//System.out.println("\n" + nazov);
		try {
			ZrychliParsovanie(dbFactory);
		} catch (ParserConfigurationException e) {
			throw new ParserConfigurationException();
		}
        
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			throw new ParserConfigurationException();
		}
		
		URL url = null;
		try {
			url = new URL(nazov);
		} catch (MalformedURLException e) {
			throw new MalformedURLException();
		}
		try {
			result = dBuilder.parse(url.openStream());
		} catch (SAXException e) {
			throw new SAXException();
		} catch (IOException e) {
			throw new IOException();
		}
		
		result.getDocumentElement().normalize();
		return result;
	}
	
	public static Document vratXMLvStromovejStrukture(String nazov, boolean zUrl)  throws ParserConfigurationException, SAXException, IOException, MalformedURLException {
		Document result = null;
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		try {
			if (zUrl) {
				URL url = new URL(nazov); // z url
				result = dBuilder.parse(url.openStream());

			} else {
				File fXmlFile = new File(nazov); // zo suboru
				result = dBuilder.parse(fXmlFile);
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}
		
		result.getDocumentElement().normalize();
		return result;
	}
	
	private static void ZrychliParsovanie(DocumentBuilderFactory dbf) throws ParserConfigurationException {
		dbf.setNamespaceAware(false);
		dbf.setValidating(false);
		dbf.setFeature("http://xml.org/sax/features/namespaces", false);
		dbf.setFeature("http://xml.org/sax/features/validation", false);
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-dtd-grammar", false);
		dbf.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
	}

	/**
	 * metodka z tagu vrati atribut pomenovany ako nazov_atributu
	 * @param item
	 * @param nazov_atributu
	 * @return
	 */
	public static String vratHodnotuAtributuTagu(Node item, String nazov_atributu) {
		if (item != null) {
			if (item.getAttributes().getNamedItem(nazov_atributu) != null) {
				return item.getAttributes().getNamedItem(nazov_atributu).getNodeValue();
			}
		}
		return null;
	}

	public static List<Integer> vratZoznamIntegerovPodlaTagu(Document doc, String string, String string2) {
		List<Integer> zoznam = new ArrayList<Integer>();
		Node hlavny_tag = doc.getElementsByTagName("cenne_papiere").item(0);
		NodeList deti_hlavneho_tagu = hlavny_tag.getChildNodes();
		for (int i = 0; i < deti_hlavneho_tagu.getLength(); i++) {
			Node jedno_dieta = deti_hlavneho_tagu.item(i);
			if ( (jedno_dieta.getNodeType() == Node.ELEMENT_NODE) && (jedno_dieta.getNodeName().equals("id_cenneho_papiera")) ) {
				zoznam.add(TypoveKonverzie.prevedRetazecNaInteger(PracaXmlSubor.vratHodnotuTagu(jedno_dieta)));
			}
		}
		return zoznam;
	}

}