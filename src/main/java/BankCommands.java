public interface BankCommands {
    public static Account create(String type, int ID, double APR) {
        return null;
    }

    public void deposit(int amount);

    public void withdraw(double amount);
}
