import java.util.*;
import java.io.*;
import java.util.Scanner;

/**
 *
 * Adalia Thao
 */

public class FastFoodKitchen {

    private ArrayList<BurgerOrder> orderList = new ArrayList(); // orderList
    private ArrayList<BurgerOrder> completedOrders = new ArrayList(); // completed orders
    private static int nextOrderNum = 1;
    private static int orderComplete = 0;
    private static int totalItems = 0;
    private static int ordersPlaced = 0;
    private static int items = 0;
    private static int dailyOrders = 0;
    private static int totalOrders;
    private static int totalHam = 0;
    private static int totalVeggie = 0;
    private static int totalCheese = 0;
    private static int totalSoda = 0;
    private static int uncompletedOrders = 0;

    // constructor
    // FastFoodKitchen() {
    //     orderList.add(new BurgerOrder(3, 15, 4, 10, false, getNextOrderNum()));
    //     incrementNextOrderNum();
    //     orderList.add(new BurgerOrder(10, 10, 3, 3, true, getNextOrderNum()));
    //     incrementNextOrderNum();
    //     orderList.add(new BurgerOrder(1, 1, 1, 2, false, getNextOrderNum()));
    //     incrementNextOrderNum();
    // }

    FastFoodKitchen(){
        try {
            File file = new File("burgerOrders.txt");
            Scanner sc = new Scanner(file);
            
            while (sc.hasNext()) {
                String line = sc.nextLine();
                String[] order = line.split(", ");
                
                //Creates order objects
                if (!order[0].equals("orderID")) {
                    int orderID = Integer.parseInt(order[0]);
                    int ham = Integer.parseInt(order[1]);
                    int cheese = Integer.parseInt(order[2]);
                    int veggie = Integer.parseInt(order[3]);
                    int soda = Integer.parseInt(order[4]);
                    boolean toGo = Boolean.parseBoolean(order[5]);
                    orderList.add(new BurgerOrder(ham, cheese, veggie, soda, toGo, orderID));
                    if(orderID > nextOrderNum){
                        nextOrderNum = orderID;
                    }
                    nextOrderNum++;
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException for burgerOrders.txt. Try again making sure the file name and path are correct.");
        }
    }

    public void burgerOrdersUncompleted(){
        try{
            FileOutputStream fs = new FileOutputStream("burgerOrdersUncompleted.txt");
            PrintWriter outFS = new PrintWriter(fs);
            outFS.println("orderID, numHamburgers, numCheeseburgers, numVeggieburgers, numSodas, orderToGo");
            for(int i = 0; i < orderList.size(); i++){
                outFS.println(orderList.get(i).getOrderNum() + ", " + orderList.get(i).getNumHamburger() + ", " + orderList.get(i).getNumCheeseburgers() + ", " + orderList.get(i).getNumVeggieburgers() + ", " + orderList.get(i).getNumSodas() + ", " + orderList.get(i).isOrderToGo());
            }

            outFS.close();
            fs.close();
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException for burgerOrders.text. Try again making sure the file name and path are correct.");
        } catch(IOException e){
            System.out.println("Ioexception");
        }
    }

    public void dayReport(){
        // End of day report
        try { // Outputs text into "dayReport" file
            FileOutputStream fs = new FileOutputStream("dayReport.txt");
            PrintWriter outFS = new PrintWriter(fs);
            int totalOrders = (orderComplete + uncompletedOrders);
            int uncompletedOrders = orderList.size();

            outFS.println("END OF DAY REPORT");
            outFS.println("--------------------------------------");
            if(totalOrders >= 0){
                outFS.println("Total orders: "  /*totalOrders*/ ); // total orders for the kitchen
                outFS.println("\tTotal uncompleted orders:" + uncompletedOrders);
                for(int i = 0; i < orderList.size(); i++){
                    outFS.println("\t\tOrder ID: " + orderList.get(i).getOrderNum());
                    outFS.println("\t\tHamburgers: " + orderList.get(i).getNumHamburger());
                    outFS.println("\t\tCheesebugers: "+ orderList.get(i).getNumCheeseburgers());
                    outFS.println("\t\tVeggieburgers: " + orderList.get(i).getNumVeggieburgers());
                    outFS.println("\t\tSodas: " + orderList.get(i).getNumSodas());
                    outFS.println();
                }
                outFS.println("\tTotal orders completed for today: " + orderComplete); // completed orders
                for(int i = 0; i < completedOrders.size(); i++){
                    outFS.println("\t\tOrder ID: " + completedOrders.get(i).getOrderNum());
                    outFS.println("\t\tHamburgers: " + completedOrders.get(i).getNumHamburger());
                    outFS.println("\t\tCheesebugers: "+ completedOrders.get(i).getNumCheeseburgers());
                    outFS.println("\t\tVeggieburgers: " + completedOrders.get(i).getNumVeggieburgers());
                    outFS.println("\t\tSodas: " + completedOrders.get(i).getNumSodas());
                    outFS.println();
                }
            }
            
            outFS.println("Total items placed for today: "   + totalItems /*orderList.getNumCheeseburgers() + orderList.getNumHamburgers()*/); /*total items ordered*/
            outFS.println("\tHamburgers: "  + totalHam);/*total hamburgers ordered*/
            outFS.println("\tCheeseburgers: " + totalCheese);/*total cheeseburgers ordered*/
            outFS.println("\tVeggieburgers: "  + totalVeggie);/*total veggies ordered*/
            outFS.println("\tSodas: "  + totalSoda); /*total sodas ordered*/
        
            outFS.println();

            outFS.close();
            fs.close();    
        } catch (FileNotFoundException e) {
            System.out.println("Caught FileNotFoundException for burgerOrders.text. Try again making sure the file name and path are correct.");
        } catch(IOException e){
            System.out.println("Ioexception");
        }
    }

    /**
    * returns the size
    * @return
    */
    public static int getNextOrderNum() {
        return nextOrderNum;
    }

    private void incrementNextOrderNum() {
        nextOrderNum++;
    }

    /**
    * Call method to add a new order to orderList
    * @param ham
    * @param cheese
    * @param veggie
    * @param soda
    * @param toGo
    * @return 
    */
    public int addOrder(int ham, int cheese, int veggie, int soda, boolean toGo) {
        int orderNum = getNextOrderNum();
        orderList.add(new BurgerOrder(ham, cheese, veggie, soda, toGo, orderNum));
        incrementNextOrderNum();
        orderCallOut(orderList.get(orderList.size() - 1));
        return orderNum;

    }

    /**
    * This method checks to see if the order of the order ID entered is done (if applicable)
    * @param orderID
    * @return 
    */
    public boolean isOrderDone(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                return false;
            }
        }
        return true;
    }

    /**
    * Call method to remove the most recently added element from orderList
    * @return 
    */
    public boolean cancelOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                orderList.remove(i);
                return true;
            }
        }
        totalOrders--;
        return false;
    }

    /**
    * This method returns the size of orderList
    * @return 
    */
    public int getNumOrdersPending() {
        return orderList.size();
    }

    /**
    * Call method to remove the most recently added element from orderList
    * @return 
    */
    public boolean cancelLastOrder() {

        if (!orderList.isEmpty()) { // same as  if (orderList.size() > 0) 
            orderList.remove(orderList.size() - 1);
            totalOrders--;
            return true;
        }

        return false;
    }

    private void orderCallOut(BurgerOrder order) {
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumHamburger() + " hamburgers");
        }
        if (order.getNumCheeseburgers() > 0) {
            System.out.println("You have " + order.getNumCheeseburgers() + " cheeseburgers");
        }
        if (order.getNumVeggieburgers() > 0) {
            System.out.println("You have " + order.getNumVeggieburgers() + " veggieburgers");
        }
        if (order.getNumSodas() > 0) {
            System.out.println("You have " + order.getNumSodas() + " sodas");
        }
        totalHam = order.getNumHamburger() + totalHam;
        totalCheese = order.getNumCheeseburgers() + totalCheese;
        totalVeggie = order.getNumVeggieburgers() + totalVeggie;
        totalSoda = order.getNumSodas() + totalSoda;

        items = order.getTotalBurgers() + order.getNumSodas();
        totalItems = items + totalItems;
        dailyOrders++;
        // for(int i = 0; i < orderList.size(); i++){
        //     totalOrders = orderList.size();
        // }
        

    }

    public void completeSpecificOrder(int orderID) {
        for (int i = 0; i < orderList.size(); i++) {
            if (orderList.get(i).getOrderNum() == orderID) {
                System.out.println("Order number " + orderID + " is done!");
                if (orderList.get(i).isOrderToGo()) {
                    orderCallOut(orderList.get(i));
                }
                BurgerOrder x = orderList.get(i);
                completedOrders.add(x);
                orderList.remove(i);
                orderComplete++;
                // uncompletedOrders = (totalOrders - orderComplete);
            }
        }

    }

    public void completeNextOrder() {
        int nextOrder = orderList.get(0).getOrderNum();
        completeSpecificOrder(nextOrder);

    }

    // Part 2
    public ArrayList<BurgerOrder> getOrderList() {
        return orderList;
    }

    public int findOrderSeq(int whatWeAreLookingFor) {
        for (int j = 0; j < orderList.size(); j++) {
            if (orderList.get(j).getOrderNum() == whatWeAreLookingFor) {
                return j;
            }
        }
        return -1;
    }

//    public int findOrderBin(int whatWeAreLookingFor) {
//        int left = 0;
//        int right = orderList.size() - 1;
//        while (left <= right) {
//            int middle = (left + right) / 2;
//            if (whatWeAreLookingFor < orderList.get(middle).getOrderNum()) {
//                right = middle - 1;
//            } else if (whatWeAreLookingFor > orderList.get(middle).getOrderNum()) {
//                left = middle + 1;
//            } else {
//                return middle;
//            }
//        }
//        return -1;
//    }

    public int findOrderBin(int orderID){
        int left = 0;
        int right = orderList.size()-1;
        while (left <= right){
            int middle = ((left + right)/2);
            if (orderID < orderList.get(middle).getOrderNum()){
                right = middle-1;
            }
            else if(orderID > orderList.get(middle).getOrderNum()){
                left = middle +1;
            }
            else{
                return middle;
            }
        }
        return -1;
        
    }

    public void selectionSort(){
        for (int i = 0; i< orderList.size()-1; i++){
            int minIndex = i;
            for (int k = i+1; k < orderList.size(); k++){
                if (orderList.get(minIndex).getTotalBurgers() > orderList.get(k).getTotalBurgers()){
                    minIndex = k;
                }
            }
            BurgerOrder temp = orderList.get(i);
            orderList.set(i , orderList.get(minIndex));
            orderList.set(minIndex, temp);
        }
    }

    public void insertionSort() {
        for (int j = 1; j < orderList.size(); j++) {
            BurgerOrder temp = orderList.get(j);
            int possibleIndex = j;
            while (possibleIndex > 0 && temp.getTotalBurgers() < orderList.get(possibleIndex - 1).getTotalBurgers()) {
                orderList.set(possibleIndex, orderList.get(possibleIndex - 1));
                possibleIndex--;
            }
            orderList.set(possibleIndex, temp);
        }
    }
    
//    public void selectionSort() { //weird method!
//
//        for (int j = 0; j < orderList.size() - 1; j++) {
//            int minIndex = j;
//            for (int k = j + 1; k < orderList.size(); k++) {
//
//                 if (orderList.get(minIndex).getTotalBurgers() > orderList.get(j).getTotalBurgers()){
//                    minIndex = k;
//                }
//            }
//            BurgerOrder temp = orderList.get(j);
//            orderList.set(j, orderList.get(minIndex));
//            orderList.set(minIndex, temp);
//
//        }
//    }

}
