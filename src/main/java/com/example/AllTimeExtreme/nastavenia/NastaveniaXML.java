package com.example.AllTimeExtreme.nastavenia;
import java.util.List;
import org.w3c.dom.Document;




/**
 * trieda pre udrziavanie hlavnych nastaveni programu, ako su
 *  - vsetky tabulky, ktore program pouziva
 *  - pristupove udaje do databazy
 *  - zoznam id_typov, ktore ma program stahovat aj s ich casmi a detailami
 * @author omen
 *
 */
public class NastaveniaXML {
	
	private static NastaveniaXML instance = null;
	private Integer casovyRamec;

	public static NastaveniaXML getInstance() {
		if (instance == null) {
	      instance = new NastaveniaXML();
	    }
	    return instance;
	}
	
	private NastaveniaXML() {
		Nacitaj_XML();
		System.out.println("Nastavenia nacitane...");
	}
	
	/**
	 * nacitanie nastaveni z xml suboru
	 */
	public void Nacitaj_XML() {
		
		try {

			Document doc = PracaXmlSubor.vratXMLvStromovejStrukture("nastavenia.xml", false);

			this.casovyRamec = PracaXmlSubor.vratIntegerXmlPodlaTagu("casovyRamec", doc);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Integer getCasovyRamec() {
		return casovyRamec;
	}

	
	
	
}
