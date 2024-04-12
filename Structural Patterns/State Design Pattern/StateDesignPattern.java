interface VendingMachineState
{
    void handleRequest();
}

class ReadyState implements VendingMachineState
{
    @Override
    public void handleRequest()
    {
        System.out.println("Vending Machine started");
    }
}

class ProductSelectState implements VendingMachineState
{
    @Override
    public void handleRequest()
    {
        System.out.println("Please Select Items");
    }
}

class ProceedPaymentState implements VendingMachineState
{
    @Override
    public void handleRequest()
    {
        System.out.println("Select Payment option and pay");
    }
}

class OutOfStockItemState implements VendingMachineState
{
    @Override
    public void handleRequest()
    {
        System.out.println("Ooops Item sold out!!!");
    }
}

class VendingMachineContext
{
    private VendingMachineState state;

    public void setState(VendingMachineState state)
    {
        this.state = state;
    }

    public void request()
    {
        state.handleRequest();
    }
}

/**
 * StateDesignPattern
 */
public class StateDesignPattern {

    public static void main(String[] args) 
    {
        VendingMachineContext vendingMachine = new VendingMachineContext();

        vendingMachine.setState(new ReadyState());
        vendingMachine.request();

        vendingMachine.setState(new ProductSelectState());
        vendingMachine.request();

        vendingMachine.setState(new ProceedPaymentState());
        vendingMachine.request();

        vendingMachine.setState(new OutOfStockItemState());
        vendingMachine.request();
    }
}