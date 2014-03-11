/* 
 * ATLAS Group - Virtual Campfire - www.dbis.rwth-aachen.de
 * Copyright © 2010-2012 Lehrstuhl Informatik V, RWTH Aachen, Germany. All Rights Reserved.
 * 
 */

package i5.atlas.xmpp.mensabot;

import java.io.IOException;
import java.util.Calendar;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * This class provides methods for web scrpaing the the mensa plan
 * 
 * @author kovachev
 *
 */
public class MensaPlan {

	public MensaPlan() {
		super();
	}
	
	/**
	 * This method fetches the daily mensa menu and parses for relevant data
	 * 
	 * @return cleaned data about the daily mensa menu
	 */
	public String getMensaPlan() {
		Document doc;
		String menu = "\n------------Mensa Today-------------\n";
		
		Calendar calendar = Calendar.getInstance();
		menu += calendar.getTime() + "\n";
		
		try {
			doc = Jsoup.connect(Constants.MENSA_URL).get();
			Elements Meals = doc.select("tr.bgnd_1:not(.Nw)");
			
			for(Element Meal : Meals) {
				Meal.child(1).select("span").remove();
				menu += Meal.child(1).text() + Meal.child(2).text() + "\n";
			}
			
			System.out.println(menu);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return menu + Constants.MENSA_URL + "\n------------Guten Appetit!-------------\n";
	}

	
	/**
	 * 
	 * Test driver for this class
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		MensaPlan mp = new MensaPlan();
		mp.getMensaPlan();
		
	}

}
