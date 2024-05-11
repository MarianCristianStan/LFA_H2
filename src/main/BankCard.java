package main;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class BankCard {

	public BankCard(String accountNumber,String cardCode, String cardPin, String cardHolder, LocalDate expireDateOfCard) {
		this.setAccountNumber(accountNumber);
		this.fiveDigitsCardCode = cardCode;
		this.cardHolder = cardHolder;
		this.expireDateOfCard = expireDateOfCard;
		this.cardPin = cardPin;
	}

	public void showCardInfo() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		String formattedExpireDate = expireDateOfCard.format(formatter);

		System.out.println("Card Code: " + fiveDigitsCardCode);
		System.out.println("Card Pin: " + cardPin);
		System.out.println("Card Holder: " + cardHolder);
		System.out.println("Expiration Date: " + formattedExpireDate);
	}

	public String getCardCode() {
		return fiveDigitsCardCode;
	}

	public void setCardCode(String cardCode) {
		this.fiveDigitsCardCode = cardCode;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public LocalDate getExpireDateOfCard() {
		return expireDateOfCard;
	}

	public void setExpireDateOfCard(LocalDate expireDateOfCard) {
		this.expireDateOfCard = expireDateOfCard;
	}

	public String getCardPin() {
		return cardPin;
	}

	public void setCardPin(String cardPin) {
		this.cardPin = cardPin;
	}
	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	private String accountNumber;
	private String fiveDigitsCardCode;
	private String cardPin;
	private String cardHolder;
	private LocalDate expireDateOfCard;

}
