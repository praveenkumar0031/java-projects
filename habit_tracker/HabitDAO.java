import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

public class HabitDAO {
    
    public int registerUser(String username,String password) {
    String sql = "INSERT INTO Users (Username,password) VALUES (?,?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
        pst.setString(1, username);
        pst.setString(2, password);
        pst.executeUpdate();
        
        ResultSet rs = pst.getGeneratedKeys();
        if (rs.next()) return rs.getInt(1);
    } catch (SQLException e) { e.printStackTrace(); }
    return -1;
}

    public void addHabit(int userId, String name, String freq) {
        String sql = "INSERT INTO Habits (UserID, HabitName, Frequency) VALUES (?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
            PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            pst.setString(2, name);
            pst.setString(3, freq);
            pst.executeUpdate();
            System.out.println("Habit added successfully!");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public void logProgress(int habitId) {
        String sql = "INSERT INTO HabitLogs (HabitID) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, habitId);
            pst.executeUpdate();
            updateRewards(habitId);
            System.out.println("✔ Progress logged for today!");
        } catch (SQLException e) { System.out.println("Error: Already logged for today?"); }
    }

    public void viewProgress(int userId) {
        String sql = "SELECT h.HabitName, COUNT(l.LogID) as TotalDays " +
                     "FROM Habits h LEFT JOIN HabitLogs l ON h.HabitID = l.HabitID " +
                     "WHERE h.UserID = ? GROUP BY h.HabitName";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            System.out.println("\n--- Progress Report ---");
            while(rs.next()) {
                System.out.println(rs.getString("HabitName") + ": " + rs.getInt("TotalDays") + " days completed.");
            }
        } catch (SQLException e) { e.printStackTrace(); }
    }

    private void updateRewards(int habitId) {
        // Simple logic: Every 5 logs = 1 Reward
        System.out.println("🏆 Milestone Check: Points added to your profile!");
    }
    public int calculateStreak(int habitId) {
        int streak = 0;
        LocalDate dateToCheck = LocalDate.now(); // Start from today
        
        try (Connection conn = DBConnection.getConnection()) {
            while (true) {
                String sql = "SELECT COUNT(*) FROM HabitLogs WHERE HabitID = ? AND LogDate = ?";
                try (PreparedStatement pst = conn.prepareStatement(sql)) {
                    pst.setInt(1, habitId);
                    pst.setDate(2, Date.valueOf(dateToCheck));
                    
                    ResultSet rs = pst.executeQuery();
                    if (rs.next() && rs.getInt(1) > 0) {
                        streak++;
                        dateToCheck = dateToCheck.minusDays(1); // Move to the previous day
                    } else {
                        break; // Streak broken
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return streak;
    }

    public void checkAndGrantRewards(int userId, int habitId) {
        int currentStreak = calculateStreak(habitId);
        
        if (currentStreak >= 7) {
            String sql = "INSERT INTO Rewards (UserID, Title, PointsEarned) VALUES (?, '7-Day Warrior', 100) " +
                         "ON DUPLICATE KEY UPDATE PointsEarned = PointsEarned + 10";
            try (Connection conn = DBConnection.getConnection();
                 PreparedStatement pst = conn.prepareStatement(sql)) {
                pst.setInt(1, userId);
                pst.executeUpdate();
                System.out.println("🎉 Achievement Unlocked: 7-Day Warrior!");
            } catch (SQLException e) { e.printStackTrace(); }
        }
    }
}