package main;

public class BankAccount {

	public BankAccount(String accountNumber, String accountHolder, double balance, BankCard card) {
		this.accountNumber = accountNumber;
		this.accountHolder = accountHolder;
		this.balance = balance;
		this.card = card;
	}

	public void showAccountInfo() {
		System.out.println("Account Number: " + accountNumber);
		System.out.println("Account Holder: " + accountHolder);
		System.out.println("Balance: " + balance);
		System.out.println("Card Information:");
		card.showCardInfo();
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getAccountHolder() {
		return accountHolder;
	}

	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public void addBalance(double amount) {
		this.balance += amount;
	}

	public void subtractBalance(double amount) {

		this.balance -= amount;
		if (this.balance < 0)
			setBalance(0);
	}

	public BankCard getCard() {
		return card;
	}

	public void setCard(BankCard card) {
		this.card = card;
	}

	private String accountNumber;
	private String accountHolder;
	private double balance;
	private BankCard card;
}
