import java.util.*;

public class Demo {
    // Move the list inside the class and make it static
    static List<String> hotels = new ArrayList<>(Arrays.asList("vks", "pks", "razika", "anandhas"));

    // Make inner classes static
    static class Menu {
        List<HashMap<String, Integer>> hmenu = new ArrayList<>();
        public Menu() {
            for (int i = 0; i < 4; i++) {
                HashMap<String, Integer> hm = new HashMap<>();
                hm.put("Idly", 15); // Fixed spelling
                hm.put("Dosa", 65);
                hmenu.add(hm);
            }
        }

        void viewMenu(int n) {
            // Adjust for 0-based index (n-1)
            if (n > 0 && n <= hmenu.size()) {
                System.out.println(hotels.get(n - 1) + " Hotel Menu");
                HashMap<String, Integer> hm = hmenu.get(n - 1);
                for (Map.Entry<String, Integer> e : hm.entrySet()) {
                    System.out.println(e.getKey() + " " + e.getValue());
                }
            }
        }
    }

    static class User {
        String name;
        private float amount;
        String address;
        int pin;
        // User needs this to be callable from main if reference is User
        void viewHotels() {} 
    }

    static class Customer extends User {
        public Customer(String name, String add, int pin) {
            this.name = name;
            this.address = add;
            this.pin = pin;
            System.out.println("Customer logged in");
        }

        @Override
        void viewHotels() {
            System.out.println("Nearby Hotels:");
            for (int i = 0; i < hotels.size(); i++) {
                System.out.println((i + 1) + " " + hotels.get(i));
            }
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Choose: 1.Customer 2.Restaurant 3.Delivery Boy");
        int us = in.nextInt();

        if (us == 1) {
            Customer cus = new Customer("praveen", "Coimbatore", 609104);
            Menu m = new Menu();
            cus.viewHotels();
            int hotelch = in.nextInt();
            m.viewMenu(hotelch);
        }
    }
}