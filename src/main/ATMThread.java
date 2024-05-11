package main;

import java.util.ArrayList;

public class ATMThread extends Thread {

	public ATMThread(ATMGUI atmGui) {
		this.atmGui = atmGui;
		lastState = null;
	}

	@Override
	public void run() {
		while (true) {
			ATMState currentState = atmGui.getCurrentState();
			if (currentState != lastState) {
				lastState = currentState;
				updateGUI(currentState);
			}

			switch (currentState) {
			case IDLE:
				this.card = null;
				break;
			case INSERT_CARD:

				break;
			case CHECK_CARD:
				String cardNumber = atmGui.getCardField().getText();
				boolean cardVerified = verifyCard(cardNumber);
				if (cardVerified) {
					atmGui.setCurrentState(ATMState.INSERT_PIN);
				} else {
					atmGui.setCurrentState(ATMState.IDLE);

				}
				break;
			case INSERT_PIN:

				break;
			case CHECK_PIN:
				String cardPin = atmGui.getPinField().getText();
				boolean pinVerified = verifyPin(cardPin);
				if (pinVerified) {
					atmGui.setCurrentState(ATMState.CHOOSE_TRANSACTION);
				} else {
					atmGui.setCurrentState(ATMState.IDLE);

				}
				break;
			case CHOOSE_TRANSACTION:

				break;
			case DEPOSIT:

				break;
			case PROCESS_DEPOSIT:
				double depositAmount = Double.parseDouble(atmGui.getDepositAmount().getText());
				if (depositToCurrentCard(depositAmount)) {
					atmGui.setCurrentState(ATMState.CHOOSE_TRANSACTION);
					atmGui.setProcessDepositPanel(true);
				} else {
					atmGui.setCurrentState(ATMState.DEPOSIT);
					atmGui.setProcessDepositPanel(false);
				}

				break;
			case WITHDRAW:

				break;
			case PROCESS_WITHDRAW:
				double withdrawAmount = Double.parseDouble(atmGui.getWithdrawAmount().getText());
				if (withdrawFromCurrentCard(withdrawAmount)) {
					atmGui.setCurrentState(ATMState.CHOOSE_TRANSACTION);
					atmGui.setProcessWithdrawPanel(true);
				} else {
					atmGui.setCurrentState(ATMState.WITHDRAW);
					atmGui.setProcessWithdrawPanel(false);
				}
				break;
			case TRANSFER:

				break;
			case PROCESS_TRANSFER:
				double transferAmount = Double.parseDouble(atmGui.getTransferAmount().getText());
				String transferAccount = atmGui.getTransferAccountNumber().getText();
				if (transferFromCurrentCard(transferAccount, transferAmount)) {
					atmGui.setProcessTransferPanel(true);
					atmGui.setTransferAccountNumber(null);
					atmGui.setCurrentState(ATMState.CHOOSE_TRANSACTION);

				} else {
					atmGui.setProcessTransferPanel(false);
					atmGui.setTransferAccountNumber(null);
					atmGui.setCurrentState(ATMState.TRANSFER);
				}
				break;
			case ACCOUNT_INFO:

				break;
			default:
				atmGui.setCurrentState(ATMState.IDLE);
				break;
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateGUI(ATMState currentState) {
		switch (currentState) {
		case IDLE:
			atmGui.setIdlePanel();
			break;
		case INSERT_CARD:
			atmGui.setInsertCardPanel();
			break;
		case CHECK_CARD:
			atmGui.setCheckCardPanel();
			break;
		case INSERT_PIN:
			atmGui.setInsertPinPanel();
			break;
		case CHECK_PIN:
			atmGui.setCheckPinPanel();
			break;
		case CHOOSE_TRANSACTION:
			atmGui.setChooseTransactionPanel();
			break;
		case DEPOSIT:
			atmGui.setDepositPanel();
			break;
		case PROCESS_DEPOSIT:
			break;
		case WITHDRAW:
			atmGui.setWithdrawPanel();
			break;
		case PROCESS_WITHDRAW:

			break;
		case TRANSFER:
			atmGui.setTransferPanel();
			break;
		case PROCESS_TRANSFER:

			break;
		case ACCOUNT_INFO:
			for (BankAccount account : bankAccounts) {
				if (account.getAccountNumber().equals(card.getAccountNumber())) {
					atmGui.setAccountInfoPanel(account);
				}
			}

			break;

		default:
			atmGui.setCurrentState(ATMState.IDLE);
			atmGui.setIdlePanel();
			break;
		}
	}

	private boolean transferFromCurrentCard(String transferAccount, double transferAmount) {
		if (transferAmount > 0) {
			for (BankAccount account : bankAccounts) {
				if (account.getAccountNumber().equals(card.getAccountNumber())) {

					if (transferAmount <= account.getBalance()) {

						for (BankAccount Taccount : bankAccounts) {
							if (Taccount.getAccountNumber().equals(transferAccount)) {
								account.subtractBalance(transferAmount);
								Taccount.addBalance(transferAmount);
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	private boolean depositToCurrentCard(double amount) {
		if (amount > 0) {
			String accountNumber = card.getAccountNumber();
			for (BankAccount account : bankAccounts) {
				if (account.getAccountNumber().equals(accountNumber)) {
					account.addBalance(amount);
					return true;
				}
			}
		}
		return false;

	}

	private boolean withdrawFromCurrentCard(double amount) {
		if (amount > 0) {
			String accountNumber = card.getAccountNumber();
			for (BankAccount account : bankAccounts) {
				if (account.getAccountNumber().equals(accountNumber)) {
					if (account.getBalance() >= amount) {
						account.subtractBalance(amount);
						return true;
					} else {
						return false;
					}

				}
			}
		}
		return false;

	}

	private boolean verifyCard(String cardNumber) {
		for (BankAccount account : bankAccounts) {
			if (account.getCard().getCardCode().equals(cardNumber)) {
				this.card = account.getCard();
				return true;
			}
		}
		return false;
	}

	private boolean verifyPin(String cardPin) {

		if (card.getCardPin().equals(cardPin)) {
			return true;
		}

		return false;
	}

	public ArrayList<BankAccount> getBankAccounts() {
		return bankAccounts;
	}

	public void setBankAccounts(ArrayList<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

	public void addBankAccounts(BankAccount acc) {
		this.bankAccounts.add(acc);
	}

	private ATMGUI atmGui;
	private ATMState lastState;
	private ArrayList<BankAccount> bankAccounts = new ArrayList<BankAccount>();
	private BankCard card;

}
