package i5.atlas.xmpp.mensabot;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Iterator;

import com.rizzoweb.alarm.*;

/**
 * A temp class for testing the alarm capabilities of the  
 * {@link http://www.rizzoweb.com/java/alarm.html} Alarm Clock/Scheduler
 * 
 * @author kovachev
 *
 */
public class AlarmTest {
	
	/**
	 * Main function of the testing class
	 */
	public static void main(String[] args) throws Exception {

		AlarmManager mgr = new AlarmManager();

		long current = System.currentTimeMillis();
		System.out.println("Current date is " + (new Date(current+6*24*60*60*1000)));
		
		Calendar calendar = Calendar.getInstance();
		System.out.println(calendar.getTime());

		AlarmListener listener = new AlarmListener() {
		  public void handleAlarm(AlarmSchedule schedule) {
			System.out.println("\u0007Alarm : " + schedule);
		  }
		};

//		mgr.addAlarm(new Date(current + (10 * 1000)), listener);
		
//		mgr.addAlarm(new Date(System.currentTimeMillis() + 120000),
//					 new AlarmListener() {
//		  public void handleAlarm(AlarmSchedule schedule) {
//			System.out.println("2 minutes later");
//		  }
//		});

//		// Cron-like alarm (minute, hour, day of month, month, day of week, year)
//		mgr.addAlarm(-1, -1, -1, -1, -1, -1, new AlarmListener() {
//		  public void handleAlarm(AlarmSchedule schedule) {
//			System.out.println("Every minute (" + new Date() + ")");
//		  }
//		});
//
		mgr.addAlarm(46, 1, -1, -1, Calendar.SUNDAY, -1, new AlarmListener() {
		  public void handleAlarm(AlarmSchedule schedule) {
			System.out.println("On every Sunday at 18:00");
		  }
		});

		mgr.addAlarm(46, 1, -1, -1, Calendar.MONDAY, -1, new AlarmListener() {
			  public void handleAlarm(AlarmSchedule schedule) {
				System.out.println("On every Sunday at 18:00");
			  }
			});
		
		System.out.println("Here are the registered alarms: ");
		System.out.println("----------------------------");
		List list = mgr.getAllAlarms();
		for(Iterator it = list.iterator(); it.hasNext();) {
		  System.out.println("- " + it.next());
		}
		System.out.println("----------------------------");
	  }
}
