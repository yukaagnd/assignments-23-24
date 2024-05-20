package assignments.assignment3;

import assignments.assignment3.systemCLI.AdminSystemCLI;
import assignments.assignment3.systemCLI.CustomerSystemCLI;
import assignments.assignment3.systemCLI.UserSystemCLI;

// Kelas untuk mengelola proses login pengguna
public class LoginManager {
    private final AdminSystemCLI adminSystem;
    private final CustomerSystemCLI customerSystem;

    // Konstruktor untuk LoginManager
    public LoginManager(AdminSystemCLI adminSystem, CustomerSystemCLI customerSystem) {
        this.adminSystem = adminSystem;
        this.customerSystem = customerSystem;
    }

    // Method untuk mendapatkan sistem yang sesuai berdasarkan peran pengguna
    public UserSystemCLI getSystem(String role){
        if("Customer".equals(role)){
            return customerSystem;
        } else {
            return adminSystem;
        }
    }
}
