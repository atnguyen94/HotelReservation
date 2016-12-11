/**
 * Using the strategy design pattern, this class managers the receipts of users
 *  * GROUP NAME: Warriors
 * @author Milan Mishra, Tuan Nguyen, Nicholas Lacroix
 *
 */
public class ReceiptContext
{
    private Receipt receipt;

    public ReceiptContext(Receipt r)
    {
        this.receipt = r;
    }

    public double executeTotalPrice()
    {
        return receipt.getTotalPrice();
    }
}