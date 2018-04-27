package PJ3;

import java.util.*;

class CompareCashier implements Comparator<Cashier>{
	// overide compare() method
 	public int compare(Cashier o1, Cashier o2) {
		return o1.getEndBusyClockTime() - o2.getEndBusyClockTime(); 
	}
}

class CheckoutArea {
  
  private PriorityQueue <Cashier> busyCashierQ;
    
  private Queue<Customer> customerQ;
  private Queue<Cashier> freeCashierQ;

  private int customerQLimit;

  public CheckoutArea() 
  {
        customerQLimit = 0;
  }

  public CheckoutArea(int numCashiers, int customerQlimit)
  {
        this.customerQLimit = customerQlimit;
        customerQ = new ArrayDeque<Customer>(customerQlimit);
        freeCashierQ = new ArrayDeque<Cashier>(numCashiers);

	busyCashierQ= new PriorityQueue<Cashier>( numCashiers, new CompareCashier()); 

         for (int i = 1; i <= numCashiers; i++){
            freeCashierQ.add(new Cashier(i));
        }
  }


  // -------------------------------------------------
  // freeCashierQ methods: remove, insert, empty, size 
  // -------------------------------------------------
  public Cashier removeFreeCashierQ()
  {
	// remove and return a free cashier
	return freeCashierQ.remove();
  }

  public void insertFreeCashierQ(Cashier cashier)
  {
	  // insert a free cashier
      freeCashierQ.add(cashier);
  }

  public boolean emptyFreeCashierQ()
  {
	// is freeCashierQ empty?
	return freeCashierQ.isEmpty();
  }

  public int sizeFreeCashierQ()
  {
	// get number of free cashiers
	return freeCashierQ.size();
  }

  // -------------------------------------------------------
  // busyCashierQ methods: remove, insert, empty, size, peek 
  // -------------------------------------------------------

  public Cashier removeBusyCashierQ() 
  {
	// remove and return a busy cashier
	return busyCashierQ.remove();
  }

  public void insertBusyCashierQ(Cashier cashier)
  {
	// insert a busy cashier
    busyCashierQ.add(cashier);
  }

  public boolean emptyBusyCashierQ()
  {
	// is busyCashierQ empty?
	// Add statetments
	return busyCashierQ.isEmpty();
  }

  public int sizeBusyCashierQ()
  {
	// get number of busy cashiers
	return busyCashierQ.size();
  }

  public Cashier peekBusyCashierQ() 
  {
	// get highest prioirty cashier
	// "retrieve" but not "remove"
	return busyCashierQ.peek();
  }

  // -------------------------------------------------------
  // customerQ methods: remove, insert, empty, size 
  //                    and check isCustomerQTooLong()
  // -------------------------------------------------------

  public Customer removeCustomerQ()
  {
	// remove and return a customer 
	return customerQ.poll();
  }

  public void insertCustomerQ(Customer customer)
  {
	// insert a customer 
    customerQ.add(customer);
  }

  public boolean emptyCustomerQ()
  {
	// is customerQ empty?
	return customerQ.isEmpty();
  }

  public int sizeCustomerQ()
  {
	// get number of customers 
	return customerQ.size();
  }

  public boolean isCustomerQTooLong()
  {
	// is customerQ too long?
	return (customerQLimit == customerQ.size());
  }


  public void printStatistics()
  {
  	System.out.println("\t# waiting customers  : "+sizeCustomerQ());
  	System.out.println("\t# busy cashiers      : "+sizeBusyCashierQ());
  	System.out.println("\t# free cashiers      : "+sizeFreeCashierQ());
  }


  public static void main(String[] args) {

        // quick check

        // create a CheckoutArea and 4 customers
        CheckoutArea sc = new CheckoutArea(4, 5);
        Customer c1 = new Customer(1,18,10);
        Customer c2 = new Customer(2,33,11);
        Customer c3 = new Customer(3,21,12);
        Customer c4 = new Customer(4,37,13);

        // insert customers into customerQ
  	sc.insertCustomerQ(c1);
  	sc.insertCustomerQ(c2);
  	sc.insertCustomerQ(c3);
  	sc.insertCustomerQ(c4);
	System.out.println("customerQ:"+sc.customerQ);
	System.out.println("===============================================");
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("Remove customer:"+sc.removeCustomerQ());
	System.out.println("===============================================");

        // remove cashiers from freeCashierQ
	System.out.println("freeCashierQ:"+sc.freeCashierQ);
	System.out.println("===============================================");
	Cashier p1=sc.removeFreeCashierQ();
	Cashier p2=sc.removeFreeCashierQ();
	Cashier p3=sc.removeFreeCashierQ();
	Cashier p4=sc.removeFreeCashierQ();
	System.out.println("Remove free cashier:"+p1);
	System.out.println("Remove free cashier:"+p2);
	System.out.println("Remove free cashier:"+p3);
	System.out.println("Remove free cashier:"+p4);
	System.out.println("===============================================");
	System.out.println("freeCashierQ:"+sc.freeCashierQ);
	System.out.println("===============================================");

        // insert customers to cashiers
        p1.freeToBusy (c1, 13);
        p2.freeToBusy (c2, 13);
        p3.freeToBusy (c3, 13);
        p4.freeToBusy (c4, 13);

        // insert cashiers to busyCashierQ
	System.out.println("busyCashierQ:"+sc.busyCashierQ);
	System.out.println("===============================================");
	sc.insertBusyCashierQ(p1);
	sc.insertBusyCashierQ(p4);
	sc.insertBusyCashierQ(p2);
	sc.insertBusyCashierQ(p3);
	System.out.println("busyCashierQ:"+sc.busyCashierQ);
	System.out.println("===============================================");

        // remove cashiers from busyCashierQ
	p1=sc.removeBusyCashierQ();
	p2=sc.removeBusyCashierQ();
	p3=sc.removeBusyCashierQ();
	p4=sc.removeBusyCashierQ();
	System.out.println("Remove busy cashier:"+p1);
	System.out.println("Remove busy cashier:"+p2);
	System.out.println("Remove busy cashier:"+p3);
	System.out.println("Remove busy cashier:"+p4);

   }


};

