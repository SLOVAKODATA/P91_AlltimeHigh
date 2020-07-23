package com.example.AllTimeExtreme.nastavenia;


import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;


/**
 * pomocna trieda obsluhuje typove konverzie, vie bezpecne previest retazce na rozne typy
 * @author omen
 *
 */
public class TypoveKonverzie {
	
	public static List<Integer> vratZoznamIntegerovZoStringuOddelenych(String zoznam, String oddelovac) {
		List<Integer> result = new ArrayList<Integer>();
		String[] pole = zoznam.split(oddelovac);
		for (String string : pole) {
			result.add(TypoveKonverzie.prevedRetazecNaInteger(string));
		}
		return result;
	}
	
	public static String vratZoznamIntegerovOddeleneCiarkou(List<Integer> zoznam) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < zoznam.size(); i++) {
			sb.append(zoznam.get(i) + ",");
		}
		String str_zoznam = sb.toString();
		if (str_zoznam.equals("")) {
			str_zoznam = null;
		} else {
			str_zoznam = str_zoznam.substring(0, str_zoznam.length() - 1);
		}
		return str_zoznam;
	}
	
	/**
	 * metodka, ktora pri prevedie lepsie string na double
	 * */
    public static Float prevedRetazecNaFloat(String hodnotaText) {
        if (hodnotaText == null) {
        	return null;
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        ParsePosition poziciaKonverzie = new ParsePosition(0);
        
        // keby dal prazdny retazec
        if (hodnotaText.isEmpty()) {
            return null;
        }
        
        // vyskusame to previest na necisto
        // prvy parameter: co sa ma predavat, druhy parameter: kolko sa toho previedlo
        // number je specialny dat. vyp, aby sa to nedostalo, ked zadam "fdhjkfdhjkfdh394849328"
        Number hodnota = numberFormat.parse(hodnotaText, poziciaKonverzie);
        
        // ak sa to previedlo vsetko, tak vraciam hodnotu
        if (poziciaKonverzie.getIndex() == hodnotaText.length()) {
            return hodnota.floatValue();       // happy day
        }
               
        // pokial sa to dostalo az sem, tak uz to nie je dobre
        // ale skusim previest ciarku za bodku
        try {
            hodnota = Float.parseFloat(hodnotaText);
            return hodnota.floatValue();
        } catch (NumberFormatException ex) {
            return null;
        }
    }
    
    public static Double prevedRetazecNaDouble(String hodnotaText) {
    	if (hodnotaText == null) {
        	return null;
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        ParsePosition poziciaKonverzie = new ParsePosition(0);
        
        // keby dal prazdny retazec
        if (hodnotaText.isEmpty()) {
            return null;
        }
        
        // vyskusame to previest na necisto
        // prvy parameter: co sa ma predavat, druhy parameter: kolko sa toho previedlo
        // number je specialny dat. vyp, aby sa to nedostalo, ked zadam "fdhjkfdhjkfdh394849328"
        Number hodnota = numberFormat.parse(hodnotaText, poziciaKonverzie);
        
        // ak sa to previedlo vsetko, tak vraciam hodnotu
        if (poziciaKonverzie.getIndex() == hodnotaText.length()) {
            return hodnota.doubleValue();       // happy day
        }
               
        // pokial sa to dostalo az sem, tak uz to nie je dobre
        // ale skusim previest ciarku za bodku
        try {
            hodnota = Double.parseDouble(hodnotaText);
            return hodnota.doubleValue();
        } catch (NumberFormatException ex) {
            return null;
        }
    }
    
    public static Integer prevedRetazecNaInteger(String hodnotaText) {
    	if (hodnotaText == null) {
        	return null;
        }
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        ParsePosition poziciaKonverzie = new ParsePosition(0);
        
        // keby dal prazdny retazec
        if (hodnotaText.isEmpty()) {
            return null;
        }
        
        // vyskusame to previest na necisto
        // prvy parameter: co sa ma predavat, druhy parameter: kolko sa toho previedlo
        // number je specialny dat. vyp, aby sa to nedostalo, ked zadam "fdhjkfdhjkfdh394849328"
        Number hodnota = numberFormat.parse(hodnotaText, poziciaKonverzie);
        
        // ak sa to previedlo vsetko, tak vraciam hodnotu
        if (poziciaKonverzie.getIndex() == hodnotaText.length()) {
            return hodnota.intValue();       // happy day
        }
               
        // pokial sa to dostalo az sem, tak uz to nie je dobre
        // ale skusim previest ciarku za bodku
        try {
            hodnota = Integer.parseInt(hodnotaText);
            return hodnota.intValue();
        } catch (NumberFormatException ex) {
            return null;
        }
    }

}
