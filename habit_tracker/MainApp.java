import java.util.Scanner;

public class MainApp {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HabitDAO dao = new HabitDAO();
        
        // Inside MainApp.java main method
System.out.println("--- Healthy Habit Tracker ---");
System.out.println("1. Login (Existing ID)\n2. Register (New User)");
int entryChoice = sc.nextInt();
sc.nextLine();

int userId = 0;
if (entryChoice == 2) {
    System.out.print("Enter new username: ");
    String newName = sc.nextLine();
    System.out.print("Enter new password: ");
    String password = sc.nextLine();
    // Logic to INSERT INTO Users and get the generated ID
    userId = dao.registerUser(newName,password); 
    System.out.println("Your new User ID is: " + userId);
} else {
    System.out.print("Enter your User ID: ");
    userId = sc.nextInt();
    sc.nextLine();
    System.out.print("Enter your password: ");
    String password= sc.nextLine();
    
    
}

        while (true) {
            System.out.println("\n1. Add Habit\n2. Log Progress\n3. View Progress\n4. View Rewards\n5. Streak check\n6. Exit");
            System.out.print("Choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.print("Habit Name: ");
                    String name = sc.nextLine();
                    dao.addHabit(userId, name, "Daily");
                    break;
                case 2:
                    System.out.print("Enter Habit ID to log: ");
                    int id = sc.nextInt();
                    dao.logProgress(id);
                    break;
                case 3:
                    dao.viewProgress(userId);
                    break;
                case 4:
                    System.out.println("Feature coming soon: Visual Badges!");
                    break;
                // Inside the switch case for View Progress
case 5:
    System.out.print("Enter Habit ID to check streak: ");
    int hId = sc.nextInt();
    int streak = dao.calculateStreak(hId);
    System.out.println("🔥 Current Streak: " + streak + " days");
    dao.checkAndGrantRewards(userId, hId);
    break;
                case 6:
                    System.exit(0);
                
            }
        }
    }
}