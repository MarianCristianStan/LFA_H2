package main;


import java.time.LocalDate;

import javax.swing.*;

public class ATMApp {
	public static void main(String[] args) {

		LocalDate expireDate = LocalDate.of(2025, 5, 31);
		BankCard card = new BankCard("1234567890","12345", "0000", "Marian Cristian", expireDate);
		BankAccount account = new BankAccount("1234567890", "Marian Cristian", 1000.0, card);
		account.showAccountInfo();
		
		System.out.println();
		BankCard card1 = new BankCard("0987654321","54321", "9999", "Raul Constantin", expireDate);
		BankAccount account1 = new BankAccount("0987654321", "Raul Constantin", 2000.0, card1);
		account1.showAccountInfo();
		
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				ATMGUI atmGui = new ATMGUI();
				atmGui.setVisible(true);
				ATMThread atmThread = new ATMThread(atmGui);
				atmThread.addBankAccounts(account);
				atmThread.addBankAccounts(account1);
				atmThread.start();
			}
		});
	}
}
