package PJ3;

class Cashier {

   // cashier id and current customer which is served by this cashier 
   private int cashierID;
   private Customer serveCustomer;

   // start time and end time of current (free or busy) interval
   private int startClockTime;
   private int endClockTime;

   // for keeping statistical data
   private int totalFreeTime;
   private int totalBusyTime;
   private int totalCustomers;

   // Constructor
   Cashier()
   {
       cashierID = 0;
       totalFreeTime = 0;
       totalBusyTime = 0;
       totalCustomers = 0;
   }


   // Constructor with cashier id
   Cashier(int cashierId)
   {
        this.cashierID = cashierId;
        totalFreeTime = 0;
        totalBusyTime = 0;
        totalCustomers  = 0;
   }

   // accessor methods

   int getCashierID() 
   {
	return cashierID;
   }

   Customer getCurrentCustomer() 
   {
	return serveCustomer;
   }

   // Transition from free interval to busy interval
   void freeToBusy (Customer serveCustomer, int currentTime)
   {
       this.serveCustomer = serveCustomer;
       totalFreeTime += (currentTime - startClockTime);
       startClockTime = currentTime;
       endClockTime = startClockTime + serveCustomer.getServiceTime();
       totalCustomers++;
   }

   // Transition from busy interval to free interval
   Customer busyToFree ()
   {
        totalBusyTime += (endClockTime - startClockTime);
        startClockTime = endClockTime;
        return serveCustomer;
   }

   // Return end busy clock time, use in priority queue
   int getEndBusyClockTime() 
   {
	return endClockTime;
   }

   // For free interval at the end of simulation, 
   void setEndFreeClockTime (int endsimulationtime)
   {
       endClockTime = endsimulationtime;
       totalFreeTime += (endsimulationtime - startClockTime);
   }

   // For busy interval at the end of simulation, 
   void setEndBusyClockTime (int endsimulationtime)
   {
       endClockTime = endsimulationtime;
       totalBusyTime += (endsimulationtime - startClockTime);
   }

   // functions for printing statistics :
   void printStatistics () 
   {
  	System.out.println("\t\tCashier ID             : "+cashierID);
  	System.out.println("\t\tTotal free time        : "+totalFreeTime);
  	System.out.println("\t\tTotal busy time        : "+totalBusyTime);
  	System.out.println("\t\tTotal # of customers   : "+totalCustomers);
  	if (totalCustomers > 0)
  	    System.out.format("\t\tAverage checkout time  : %.2f%n\n",(totalBusyTime*1.0)/totalCustomers);
   }

   public String toString()
   {
	return "CashierID="+cashierID+":startClockTime="+startClockTime+
               ":endClockTime="+endClockTime+">>serveCustomer:"+serveCustomer;
   }

   public static void main(String[] args) {
        // quick check
       Customer mycustomer = new Customer(1,15,5);
       Cashier mycashier = new Cashier(5);
       mycashier.freeToBusy (mycustomer, 12);
       System.out.println(mycashier);

   }

};

