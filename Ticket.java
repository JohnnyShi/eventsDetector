/**
 * A class of ticket
 * @author johny
 *
 */
public class Ticket {
	String type;
	int amount;
	double price;
	/**
	 * A constructor given ticket type, amount and price
	 * @param type a string representation of ticket type
	 * @param amount amount of ticket
	 * @param price price of ticket
	 */
	public Ticket(String type, int amount, double price) {
		this.type = type;
		this.amount = amount;
		this.price = price;
	}
	/**
	 * get ticket type
	 * @return ticket type
	 */
	public String getType() {
		return type;
	}
	/**
	 * get ticket amount
	 * @return ticket amount
	 */
	public int getAmount() {
		return amount;
	}
	/**
	 * set ticket amount
	 * @param amount ticket amount
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}
	/**
	 * get ticket price
	 * @return ticket price
	 */
	public double getPrice() {
		return price;
	}
	/**
	 * set ticket price
	 * @param price ticket price
	 */
	public void setPrice(double price) {
		this.price = price;
	}
}
