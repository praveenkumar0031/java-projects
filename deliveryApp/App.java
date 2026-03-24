import java.util.*;

public class App {
    
    static class GlobalData {
        static List<String> hotels = new ArrayList<>(Arrays.asList("vks", "pks", "razika", "anandhas"));
        static Menu menuSystem = new Menu();
        static List<Order> allOrders = new ArrayList<>();
    }

    static class Order {
        String customerName;
        String hotelName;
        Map<String, Integer> items;
        String status; 

        public Order(String customerName, String hotelName, Map<String, Integer> items) {
            this.customerName = customerName;
            this.hotelName = hotelName;
            this.items = items;
            this.status = "Pending";
        }

        @Override
        public String toString() {
            return String.format("[%s] Hotel: %-10s | Items: %s", status, hotelName, items);
        }
    }

    static class Customer extends User {
        public Customer(String name) { this.name = name; }

        void checkOrderStatus() {
            System.out.println("\n--- TRACKING YOUR ORDERS ---");
            boolean found = false;
            for (Order o : GlobalData.allOrders) {
                if (o.customerName.equals(this.name)) {
                    System.out.println(o);
                    found = true;
                }
            }
            if (!found) System.out.println("No orders found for " + this.name);
        }

        public void placeOrder(int hotelIdx, Scanner in) {
            String hotelName = GlobalData.hotels.get(hotelIdx);
            HashMap<String, Integer> menu = GlobalData.menuSystem.hmenu.get(hotelIdx);
            Map<String, Integer> myCart = new HashMap<>();

            System.out.println("\n--- Placing Order at " + hotelName + " ---");
            System.out.println("Instructions: Enter dish name exactly as shown. Type 'done' to checkout.");
            
            while (true) {
                System.out.print("Enter dish name: ");
                String dish = in.next();
                if (dish.equalsIgnoreCase("done")) break;
                
                if (menu.containsKey(dish)) {
                    System.out.print("Enter quantity for " + dish + ": ");
                    int qty = in.nextInt();
                    myCart.put(dish, qty);
                    System.out.println("Added to cart.");
                } else {
                    System.out.println("Dish not found! Please check spelling.");
                }
            }
            
            if (!myCart.isEmpty()) {
                GlobalData.allOrders.add(new Order(this.name, hotelName, myCart));
                System.out.println("\nSUCCESS: Order sent to " + hotelName + "!");
            } else {
                System.out.println("Cart is empty. Order cancelled.");
            }
        }
    }

    static class Restaurant extends User {
        String hotelName;
        public Restaurant(String hotelName) { this.hotelName = hotelName; }

        public void manageOrders(Scanner in) {
            System.out.println("\n--- " + hotelName.toUpperCase() + " INCOMING ORDERS ---");
            List<Order> myOrders = new ArrayList<>();
            for (Order o : GlobalData.allOrders) {
                if (o.hotelName.equals(this.hotelName) && !o.status.equals("Delivered")) {
                    myOrders.add(o);
                }
            }

            if (myOrders.isEmpty()) {
                System.out.println("No active orders for your restaurant right now.");
                return;
            }

            for (int i = 0; i < myOrders.size(); i++) {
                System.out.println(i + ". " + myOrders.get(i));
            }

            System.out.print("\nChoose order index to update (or -1 to go back): ");
            int idx = in.nextInt();
            if (idx >= 0 && idx < myOrders.size()) {
                Order target = myOrders.get(idx);
                if (target.status.equals("Pending")) {
                    target.status = "Preparing";
                    System.out.println("Order status updated to: PREPARING");
                } else if (target.status.equals("Preparing")) {
                    target.status = "Ready for Pickup";
                    System.out.println("Order status updated to: READY FOR PICKUP");
                } else {
                    System.out.println("Order is already " + target.status);
                }
            }
        }
    }

    static class DeliveryBoy extends User {
        public DeliveryBoy(String name) { this.name = name; }

        public void handleDeliveries(Scanner in) {
            System.out.println("\n--- DELIVERY SHIPMENTS ---");
            List<Order> deliverable = new ArrayList<>();
            for (Order o : GlobalData.allOrders) {
                if (o.status.equals("Ready for Pickup") || o.status.equals("On the Way")) {
                    deliverable.add(o);
                }
            }

            if (deliverable.isEmpty()) {
                System.out.println("No packages ready for pickup.");
                return;
            }

            for (int i = 0; i < deliverable.size(); i++) {
                System.out.println(i + ". " + deliverable.get(i));
            }

            System.out.print("\nChoose order index to pick/drop (or -1): ");
            int idx = in.nextInt();
            if (idx >= 0 && idx < deliverable.size()) {
                Order target = deliverable.get(idx);
                if (target.status.equals("Ready for Pickup")) {
                    target.status = "On the Way";
                    System.out.println("Order picked up! Status: ON THE WAY");
                } else if (target.status.equals("On the Way")) {
                    target.status = "Delivered";
                    System.out.println("Success! Order marked as DELIVERED.");
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        Customer praveen = new Customer("Praveen"); 
        
        while (true) {
            System.out.println("\n=========================");
            System.out.println("  SWIGGY CONSOLE SYSTEM  ");
            System.out.println("=========================");
            System.out.println("1. Customer Login");
            System.out.println("2. Restaurant Login");
            System.out.println("3. Delivery Boy Login");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            
            int choice = in.nextInt();
            if (choice == 4) {
                System.out.println("Closing System...");
                break;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n-- Customer Menu --");
                    System.out.println("1. Browse Hotels & Place Order");
                    System.out.println("2. Check My Order Status");
                    System.out.print("Enter choice: ");
                    int cOpt = in.nextInt();
                    if (cOpt == 1) {
                        praveen.viewHotels();
                        System.out.print("Choose a Hotel (1-" + GlobalData.hotels.size() + "): ");
                        int hIdx = in.nextInt() - 1;
                        if (hIdx >= 0 && hIdx < GlobalData.hotels.size()) {
                            GlobalData.menuSystem.viewMenu(hIdx);
                            praveen.placeOrder(hIdx, in);
                        } else {
                            System.out.println("Invalid Hotel Selection.");
                        }
                    } else {
                        praveen.checkOrderStatus();
                    }
                    break;

                case 2:
                    System.out.println("\n-- Restaurant Selection --");
                    for (int i = 0; i < GlobalData.hotels.size(); i++) {
                        System.out.println((i + 1) + ". " + GlobalData.hotels.get(i));
                    }
                    System.out.print("Login to which hotel? (1-4): ");
                    int resIdx = in.nextInt() - 1;
                    if (resIdx >= 0 && resIdx < GlobalData.hotels.size()) {
                        new Restaurant(GlobalData.hotels.get(resIdx)).manageOrders(in);
                    }
                    break;

                case 3:
                    new DeliveryBoy("Ramesh").handleDeliveries(in);
                    break;
                
                default:
                    System.out.println("Invalid input, try again.");
            }
        }
        in.close();
    }

    static class Menu {
        List<HashMap<String, Integer>> hmenu = new ArrayList<>();
        public Menu() {
            for (int i = 0; i < 4; i++) {
                HashMap<String, Integer> hm = new HashMap<>();
                hm.put("Dosa", 65); 
                hm.put("Vada", 12);
                hm.put("Poori", 45);
                hmenu.add(hm);
            }
        }
        void viewMenu(int n) {
            System.out.println("\n--- MENU FOR " + GlobalData.hotels.get(n).toUpperCase() + " ---");
            System.out.println(String.format("%-10s | %s", "Item", "Price"));
            System.out.println("--------------------");
            for (Map.Entry<String, Integer> e : hmenu.get(n).entrySet()) 
                System.out.println(String.format("%-10s | Rs.%d", e.getKey(), e.getValue()));
        }
    }

    static class User { 
        String name; 
        void viewHotels() {
            System.out.println("\n--- AVAILABLE HOTELS ---");
            for (int i=0; i<GlobalData.hotels.size(); i++) 
                System.out.println((i+1) + ". " + GlobalData.hotels.get(i));
        }
    }
}