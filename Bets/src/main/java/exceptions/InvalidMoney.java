package exceptions;

public class InvalidMoney extends Exception {
	
	private static final long serialVersionUID = 1L;

	public InvalidMoney()
	{
		super();
	}
	/**This exception is triggered if the event has already finished
	 *@param s String of the exception
	 */
	public InvalidMoney(String s)
	{
		super(s);
	}
}
