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