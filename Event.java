/**
 * A class of event in a specific location
 * @author johny
 *
 */
import java.util.*;
public class Event {
	private int id;
	private int x;
	private int y;
	private int ticketNum;
	private double cheapest;
	private Map<String, Ticket> tickets;
	
	/**
	 * Constructor. Create an event given event id and loctaion. Assume 4 types of tickets initialized by a random range
	 * @param id
	 * @param x
	 * @param y
	 */
	public Event(int id, int x, int y) {
		this.id = id;
		this.x = x;
		this.y = y;
		cheapest = Integer.MAX_VALUE;
		tickets = new HashMap<>();
		addTicket("VIP", (int)randomGenerator(200, 300), randomGenerator(900, 1100));
		addTicket("First Class", (int)randomGenerator(800, 1000), randomGenerator(600, 800));
		addTicket("Second Class", (int)randomGenerator(1400, 1600), randomGenerator(200, 400));
		addTicket("Third Class", (int)randomGenerator(2000, 2500), randomGenerator(20, 50));
		findCheapest();
		findAmount();
	}
	/**
	 * Print introduction of this event
	 */
	public void introduce() {
		System.out.println("This event happens in location " + x + "," + y + " ");
		System.out.println("Currently total number of tickets is: " + ticketNum);
		PriorityQueue<Ticket> pq = new PriorityQueue<Ticket>((a, b) -> (int)(b.getPrice() - a.getPrice()));
		for (Ticket t : tickets.values()) {
			pq.offer(t);
		}
		while (pq.size() > 0) {
			Ticket t = pq.poll();
			System.out.println(t.getType() + " tickets remain: " + t.getAmount());
		}
		System.out.println("The cheapest price is:" + getCheapest());
	}
	/**
	 * Get location of this event
	 * @return coordinate pair of location
	 */
	public int[] getLocation() {
		return new int[] {x, y};
	}
	/**
	 * Get event id
	 * @return event id
	 */
	public int getId() {
		return id;
	}
	/**
	 * Get ticket amount
	 * @return ticket amount
	 */
	public int getTicketNum() {
		return ticketNum;
	}
	/**
	 * update ticket amount
	 * @param type A string representation of ticket type
	 * @param num number of updated ticket amount
	 */
	public void updateTicketAmount(String type, int num) {
		tickets.get(type).setAmount(num);
		findAmount();
		findCheapest();
	}
	/**
	 * update ticket price
	 * @param type A string representation of ticket type
	 * @param price price to be updated
	 */
	public void updateTicketPrice(String type, double price) {
		tickets.get(type).setPrice(price);
		findCheapest();
	}
	/**
	 * get cheapest price
	 * @return cheapest price
	 */
	public double getCheapest() {
		return cheapest;
	}
	/**
	 * A random generator to generate random number given range
	 * @param start start number
	 * @param end end number
	 * @return a double number
	 */
	private double randomGenerator(int start, int end) {
		return (Math.random() * (end - start + 1)) + start;
	}
	/**
	 * update the cheapest price by comparing all the tickets whose amount is not zero
	 */
	private void findCheapest() {
		for (Ticket t : tickets.values()) {
			if (t.getAmount() > 0) cheapest = Math.min(cheapest, t.getPrice());
		}
	}
	/**
	 * update the total tickets by sum all the available tickets
	 */
	private void findAmount() {
		ticketNum = 0;
		for (Ticket t : tickets.values()) {
			ticketNum += t.getAmount();
		}
	}
	/**
	 * Add a ticket object
	 * @param type a string representation of ticket type
	 * @param amount ticket amount
	 * @param price ticket price
	 */
	public void addTicket(String type, int amount, double price) {
		tickets.put(type, new Ticket(type, amount, price));
	}
}
