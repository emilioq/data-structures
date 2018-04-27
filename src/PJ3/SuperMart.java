package PJ3;

import java.util.*;
import java.io.*;

class SuperMart {

  // input parameters
  private int numCashiers, customerQLimit;
  private int chancesOfArrival, maxServiceTime;
  private int simulationTime, dataSource;

  // statistical data
  private int numGoaway, numServed, totalWaitingTime;

  // internal data
  private int counter;	             // customer ID counter
  private CheckoutArea checkoutarea; // checkout area object
  private Scanner dataFile;	     // get customer data from file
  private Random dataRandom;	     // get customer data using random function

  // most recent customer arrival info, see getCustomerData()
  private boolean anyNewArrival;  
  private int serviceTime;

  private SuperMart()
  {
        numGoaway = 0;
        numServed = 0;
        totalWaitingTime = 0;
  }

  private void setupParameters()
  {
	// read input parameters from user
	// setup dataFile or dataRandom
        Scanner input = new Scanner(System.in);
        boolean check = false;

        while (check == false) {
          System.out.print("Enter number of cashiers (1 - 10)             : ");
          numCashiers = input.nextInt();
          if (numCashiers < 1) {
              System.out.println("Invalid number. The number is less than 1.");
          } else if (numCashiers > 10) {
              System.out.println("Invalid number. The number is more than 10.");
          } else {
              check = true;
          }
        }
        

        System.out.println();
        check = false;

        
        while (check == false) {
          System.out.print("Enter customer queue limit (1 - 50)           : ");
          customerQLimit = input.nextInt();
          if (customerQLimit < 1) {
              System.out.println("Invalid number. The number is less than 1.");
          } else if (customerQLimit > 50) {
              System.out.println("Invalid number. The number is more than 50.");
          } else {
              check = true;
          }
        }
        

        System.out.println();
        check = false;

        
        while (check == false) {
          System.out.print("Enter chance of customer arrival (1% - 100%)  : ");
          chancesOfArrival = input.nextInt();
          if (chancesOfArrival < 1) {
              System.out.println("Invalid number. The number is less than 1.");
          } else if (chancesOfArrival > 100) {
              System.out.println("Invalid number. The number is more than 100.");
          } else {
              check = true;
          }
        }
        

        System.out.println();
        check = false;
        

        while (check == false) {
          System.out.print("Enter maximum service time (1 - 500)          : ");
          maxServiceTime = input.nextInt();
          if (maxServiceTime < 1) {
              System.out.println("Invalid number. The number is less than 1.");
          } else if (maxServiceTime > 500) {
              System.out.println("Invalid number. The number is more than 500.");
          } else {
              check = true;
          }
        }
        

        System.out.println();
        check = false;
        

        while (check == false) {
          System.out.print("Enter simulation time (1 - 10000)             : ");
          simulationTime = input.nextInt();
          if (simulationTime < 1) {
              System.out.println("Invalid number. The number is less than 1.");
          } else if (simulationTime > 10000) {
              System.out.println("Invalid number. The number is more than 10000.");
          } else {
              check = true;
          }
        }
        

        System.out.println();
        System.out.println();
        check = false;
        
        
        while (check == false) {
          System.out.println("Enter '1' to get data from file");
          System.out.println();
          System.out.println("               or              ");
          System.out.println();
          System.out.print("Enter '0' for    random    data               : ");
          dataSource = input.nextInt();
          if (dataSource < 0 || dataSource > 1) {
              System.out.println();
              System.out.println("Invalid number.");
              System.out.println();
          } else {
              check = true;
          }
            
        }
        
        System.out.println();
        System.out.println();
        
        switch(dataSource){
            case 1:
                int a = 0;
                while (a == 0) {
                    System.out.print("Enter file name : ");
                    Scanner dataInput = new Scanner(System.in);
                    String fileName = dataInput.nextLine().trim();

                    try {
                        dataFile = new Scanner(new File(fileName));
                        a = 1;
                    } catch (FileNotFoundException x) {
                        System.out.println("File not found.");
                        a = 0;
                    }

                }
                break;
                
            case 0:
                System.out.println("Generating random data...");
                dataRandom = new Random();
                break;
                
            default:
                break;   
        }
  }

  private void getCustomerData()
  {
	// get next customer data : from file or random number generator
	// set anyNewArrival and serviceTime
      switch(dataSource){
          case 1:
              int data1 = dataFile.nextInt();
              int data2 = dataFile.nextInt();
              anyNewArrival = (((data1%100)+1)<= chancesOfArrival);
              serviceTime = (data2%maxServiceTime)+1;
              break;
          case 0:
              anyNewArrival = ((dataRandom.nextInt(100)+1) <= chancesOfArrival);
              serviceTime = dataRandom.nextInt(maxServiceTime)+1;
             
          default:
              break;
      }
  }

  private void doSimulation()
  {
        System.out.println("Starting simulation...");
        System.out.println();

        checkoutarea = new CheckoutArea(numCashiers,customerQLimit);

	// Time driver simulation loop
  	for (int currentTime = 0; currentTime < simulationTime; currentTime++) {
            System.out.println("____________________________________________________________");
            System.out.println("Time [" + currentTime + "]");


    		// Step 1: any new customer enters the checkout area?
    		getCustomerData();

    		if (anyNewArrival) {
      		    // Step 1.1: setup customer data
                    counter++;
                    Customer cc = new Customer(counter, serviceTime, currentTime);
                    if(cc.getServiceTime() == 1){
                        System.out.println("\tCustomer #" + counter + " arrives. (Checkout time " + cc.getServiceTime() + " unit)");
                    } else {
                        System.out.println("\tCustomer #" + counter + " arrives. (Checkout time " + cc.getServiceTime() + " units)");
                    }
                    
      		    // Step 1.2: check customer waiting queue too long?
                    if(checkoutarea.isCustomerQTooLong()){
                        System.out.println("\tCustomer #" + counter + " leaves. Queue too long.");
                        numGoaway++;
                    } else {
                        checkoutarea.insertCustomerQ(cc);
                        System.out.println("\tCustomer #" + counter + " waits in line.");
                    }
                    
                    
                    
    		} else {
      		    System.out.println("\tNo new customer!");
    		}

    		// Step 2: free busy cashiers, add to free cashierQ
                for (int i = 0; i < checkoutarea.sizeBusyCashierQ(); i++) {
                    Cashier newCash = checkoutarea.peekBusyCashierQ();
                    if (newCash.getEndBusyClockTime() <= currentTime){
                        newCash = checkoutarea.removeBusyCashierQ();
                        Customer cc = newCash.busyToFree();
                        checkoutarea.insertFreeCashierQ(newCash);
                        System.out.println("\tCustomer #" + cc.getCustomerID() + " is done.");
                        System.out.println("\tCashier  #" + newCash.getCashierID() + " is free.");
                        numServed++;
                    }
                }
    		// Step 3: get free cashiers to serve waiting customers 
                for (int i = 0; i < checkoutarea.sizeFreeCashierQ(); i++) {
                    if (checkoutarea.sizeFreeCashierQ() > 0 && checkoutarea.sizeCustomerQ() > 0) {
                        Cashier newCash = checkoutarea.removeFreeCashierQ();
                        Customer cc = checkoutarea.removeCustomerQ();
                        newCash.freeToBusy(cc, currentTime);
                        checkoutarea.insertBusyCashierQ(newCash);
                        System.out.println("\tCustomer #" + cc.getCustomerID() + " gets a cashier.");
                        if (cc.getServiceTime() == 1) {
                            System.out.println("\tCashier  #" + newCash.getCashierID() + " is serving Customer #" + cc.getCustomerID() + " for " + cc.getServiceTime() + " unit.");
                        } else {
                            System.out.println("\tCashier  #" + newCash.getCashierID() + " is serving Customer #" + cc.getCustomerID() + " for " + cc.getServiceTime() + " units.");
                        }
                        
                        totalWaitingTime += currentTime - cc.getArrivalTime();
                    }
                }
  	} // end simulation loop
        
        System.out.println();
        System.out.println();
        System.out.println("Ending simulation...");
        
  	// clean-up
  }

  private void printStatistics()
  {
      System.out.println("============================================================");
      System.out.println();
      System.out.println("Simulation Statistical Report");
      System.out.println();
      System.out.println("\t * * Customer Information * *");
      System.out.println("\t ____________________________");
      System.out.println();
      System.out.println("\t# total customers - arrived   : " + counter);
      System.out.println("\t# total customers - served    : " + numServed);
      System.out.println("\t# total customers - left      : " + numGoaway);
      System.out.println();
      System.out.println("\tTotal   customer waiting time : " + totalWaitingTime);
      System.out.println("\tAverage customer waiting time : " + (totalWaitingTime/counter));
      System.out.println();
      System.out.println("\t# currently waiting customers : " + checkoutarea.sizeCustomerQ());
      System.out.println();
      System.out.println();
      System.out.println();
      System.out.println("\t * * Cashier  Information * *");
      System.out.println("\t ____________________________");
      System.out.println();
      System.out.println("\t# currently busy cashiers : " + checkoutarea.sizeBusyCashierQ());
      System.out.println("\t# currently free cashiers : " + checkoutarea.sizeFreeCashierQ());
      
      System.out.println();
      System.out.println("\tBusy Cashiers Info. : ");
      System.out.println();
      int sizeQ = checkoutarea.sizeBusyCashierQ();
      for (int i = 1; i <= sizeQ; i++) {
          Cashier newCash = checkoutarea.removeBusyCashierQ();
          newCash.setEndBusyClockTime(simulationTime);
          newCash.printStatistics();
      }
      
      System.out.println();
      System.out.println("\tFree Cashiers Info. : ");
      System.out.println();
      sizeQ = checkoutarea.sizeFreeCashierQ();
      for (int i = 1; i <= sizeQ; i++) {
          Cashier newCash = checkoutarea.removeFreeCashierQ();
          newCash.setEndFreeClockTime(simulationTime);
          newCash.printStatistics();
      } 
  }
  
  // *** main method to run simulation ****
  public static void main(String[] args) {
   	SuperMart runSuperMart=new SuperMart();
   	runSuperMart.setupParameters();
   	runSuperMart.doSimulation();
   	runSuperMart.printStatistics();
  }
}
