package main;

import javax.swing.*;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

public class ATMGUI extends JFrame {

	public ATMGUI() {
		initializeUI();
		setIdlePanel();
	}

	private void initializeUI() {
		setTitle("ATM Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(900, 600);
		setResizable(false);
		setLocationRelativeTo(null);

		mainPanel = new JPanel(new BorderLayout());
		mainPanel.setBackground(new Color(44, 62, 80));
		getContentPane().add(mainPanel);
		

		
	}
	
	private void limitTextField(JTextField textField, int maxLength) {
	    AbstractDocument document = (AbstractDocument) textField.getDocument();
	    document.setDocumentFilter(new DocumentFilter() {
	        @Override
	        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
	            if ((fb.getDocument().getLength() + string.length()) <= maxLength) {
	                super.insertString(fb, offset, string, attr);
	            }
	        }

	        @Override
	        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
	            if ((fb.getDocument().getLength() + text.length() - length) <= maxLength) {
	                super.replace(fb, offset, length, text, attrs);
	            }
	        }
	    });
	}


	public void setIdlePanel() {
		clearMainPanel();
		messageLabel = new JLabel("Welcome To Our Bank");
		messageLabel.setFont(new Font("Arial", Font.BOLD, 50));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		mainPanel.add(messageLabel, BorderLayout.NORTH);

		cardButton = createButton("Insert Card");
		cardButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				currentState = ATMState.INSERT_CARD;
			}
		});
		cardButton.setPreferredSize(new Dimension(450, 250));
		cardButton.setForeground(Color.WHITE);

		Font buttonFont = cardButton.getFont();
		cardButton.setFont(new Font(buttonFont.getName(), Font.BOLD, 30));

		cardButton.setText("<html><center>Insert<br>Card</center></html>");

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setBackground(new Color(44, 62, 80));
		addHoverEffect(cardButton);
		centerPanel.add(cardButton);
		mainPanel.add(centerPanel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setInsertCardPanel() {
		clearMainPanel();

		messageLabel = new JLabel("Please introduce your 5 digits code from your CARD");
		messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		messagePanel.setBackground(new Color(44, 62, 80));
		messagePanel.add(messageLabel);

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setBackground(new Color(44, 62, 80));
		setCardField(new JTextField(5));
		limitTextField(cardField, 5);
		cardField.setFont(new Font("Arial", Font.BOLD, 25));
		cardField.setHorizontalAlignment(JTextField.CENTER);
		centerPanel.add(getCardField());

		JPanel fillerPanel1 = new JPanel();
		fillerPanel1.setBackground(new Color(44, 62, 80));

		JPanel fillerPanel2 = new JPanel();
		fillerPanel2.setBackground(new Color(44, 62, 80));

		JButton submitButton = createButton("Submit");
		JButton backButton = createButton("Back");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		buttonPanel.setBackground(new Color(44, 62, 80));
		submitButton.setPreferredSize(new Dimension(150, 60));
		backButton.setPreferredSize(new Dimension(150, 60));
		addHoverEffect(submitButton);
		addHoverEffect(backButton);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);

		submitButton.addActionListener(e -> currentState = ATMState.CHECK_CARD);
		backButton.addActionListener(e -> currentState = ATMState.IDLE);

		JPanel panel = new JPanel(new GridLayout(6, 1, 0, 10));
		panel.setBackground(new Color(44, 62, 80));
		panel.add(messagePanel);
		panel.add(centerPanel);
		panel.add(fillerPanel1);
		panel.add(fillerPanel2);
		panel.add(buttonPanel);

		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setCheckCardPanel() {
		clearMainPanel();

		messageLabel = new JLabel("Verifying your card...");
		messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		mainPanel.add(messageLabel, BorderLayout.NORTH);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setPreferredSize(new Dimension(300, 50));
		progressBar.setForeground(new Color(65, 105, 225));

		JPanel loadingPanel = new JPanel(new GridBagLayout());
		loadingPanel.setBackground(new Color(44, 62, 80));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		loadingPanel.add(progressBar, gbc);

		mainPanel.add(loadingPanel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setInsertPinPanel() {
		clearMainPanel();

		messageLabel = new JLabel("Please introduce your PIN");
		messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		messagePanel.setBackground(new Color(44, 62, 80));
		messagePanel.add(messageLabel);

		JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		centerPanel.setBackground(new Color(44, 62, 80));
		pinField = new JTextField(4);
		limitTextField(pinField, 4);
		pinField.setFont(new Font("Arial", Font.BOLD, 25));
		pinField.setHorizontalAlignment(JTextField.CENTER);
		centerPanel.add(pinField);

		JPanel fillerPanel1 = new JPanel();
		fillerPanel1.setBackground(new Color(44, 62, 80));

		JPanel fillerPanel2 = new JPanel();
		fillerPanel2.setBackground(new Color(44, 62, 80));

		JButton submitButton = createButton("Submit");
		JButton backButton = createButton("Back");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		buttonPanel.setBackground(new Color(44, 62, 80));
		submitButton.setPreferredSize(new Dimension(150, 60));
		backButton.setPreferredSize(new Dimension(150, 60));
		addHoverEffect(submitButton);
		addHoverEffect(backButton);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);

		submitButton.addActionListener(e -> currentState = ATMState.CHECK_PIN);
		backButton.addActionListener(e -> currentState = ATMState.IDLE);

		JPanel panel = new JPanel(new GridLayout(6, 1, 0, 10));
		panel.setBackground(new Color(44, 62, 80));
		panel.add(messagePanel);
		panel.add(centerPanel);
		panel.add(fillerPanel1);
		panel.add(fillerPanel2);
		panel.add(buttonPanel);

		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setCheckPinPanel() {
		clearMainPanel();

		messageLabel = new JLabel("Verifying your PIN...");
		messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		mainPanel.add(messageLabel, BorderLayout.NORTH);

		JProgressBar progressBar = new JProgressBar();
		progressBar.setIndeterminate(true);
		progressBar.setPreferredSize(new Dimension(300, 50));
		progressBar.setForeground(new Color(65, 105, 225));

		JPanel loadingPanel = new JPanel(new GridBagLayout());
		loadingPanel.setBackground(new Color(44, 62, 80));
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = 0;
		loadingPanel.add(progressBar, gbc);

		mainPanel.add(loadingPanel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setChooseTransactionPanel() {
		clearMainPanel();

		messageLabel = new JLabel("Choose Transaction");
		messageLabel.setFont(new Font("Arial", Font.BOLD, 35));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		mainPanel.add(messageLabel, BorderLayout.NORTH);

		JButton accountInfoButton = createButton("Account Info");
		addHoverEffect(accountInfoButton);
		JButton depositButton = createButton("Deposit");
		addHoverEffect(depositButton);
		JButton withdrawButton = createButton("Withdraw");
		addHoverEffect(withdrawButton);
		JButton transferButton = createButton("Transfer");
		addHoverEffect(transferButton);
		JButton backButton = createButton("Back");
		addHoverEffect(backButton);

		JPanel buttonPanel = new JPanel(new GridLayout(5, 1, 0, 15));
		buttonPanel.setBackground(new Color(44, 62, 80));
		buttonPanel.add(accountInfoButton);
		buttonPanel.add(depositButton);
		buttonPanel.add(withdrawButton);
		buttonPanel.add(transferButton);
		buttonPanel.add(backButton);
		mainPanel.add(buttonPanel, BorderLayout.CENTER);

		accountInfoButton.addActionListener(e -> currentState = ATMState.ACCOUNT_INFO);
		depositButton.addActionListener(e -> currentState = ATMState.DEPOSIT);
		withdrawButton.addActionListener(e -> currentState = ATMState.WITHDRAW);
		transferButton.addActionListener(e -> currentState = ATMState.TRANSFER);
		backButton.addActionListener(e -> currentState = ATMState.IDLE);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setDepositPanel() {
		clearMainPanel();

		JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
		panel.setBackground(new Color(44, 62, 80));

		JLabel topLabel = new JLabel("Enter the amount you want to deposit:");
		topLabel.setFont(new Font("Arial", Font.BOLD, 35));
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topLabel.setForeground(Color.WHITE);
		panel.add(topLabel);

		depositAmount = new JTextField(10);
		limitTextField(depositAmount, 10);
		depositAmount.setFont(new Font("Arial", Font.BOLD, 30));
		JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		amountPanel.setBackground(new Color(44, 62, 80));
		depositAmount.setHorizontalAlignment(JTextField.CENTER);
		amountPanel.add(depositAmount);
		panel.add(amountPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		buttonPanel.setBackground(new Color(44, 62, 80));

		JButton submitButton = createButton("Submit");
		submitButton.setPreferredSize(new Dimension(150, 60));
		addHoverEffect(submitButton);
		submitButton.addActionListener(e -> currentState = ATMState.PROCESS_DEPOSIT);
		buttonPanel.add(submitButton);

		JButton backButton = createButton("Back");
		backButton.setPreferredSize(new Dimension(150, 60));
		addHoverEffect(backButton);
		backButton.addActionListener(e -> currentState = ATMState.CHOOSE_TRANSACTION);
		buttonPanel.add(backButton);

		panel.add(buttonPanel);

		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setWithdrawPanel() {
		clearMainPanel();

		JPanel panel = new JPanel(new GridLayout(4, 1, 0, 10));
		panel.setBackground(new Color(44, 62, 80));

		JLabel topLabel = new JLabel("Enter the amount you want to withdraw:");
		topLabel.setFont(new Font("Arial", Font.BOLD, 30));
		topLabel.setHorizontalAlignment(SwingConstants.CENTER);
		topLabel.setForeground(Color.WHITE);
		panel.add(topLabel);

		withdrawAmount = new JTextField(10);
		limitTextField(withdrawAmount, 10);
		withdrawAmount.setFont(new Font("Arial", Font.BOLD, 25));
		withdrawAmount.setHorizontalAlignment(JTextField.CENTER);
		JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		amountPanel.setBackground(new Color(44, 62, 80));
		amountPanel.add(withdrawAmount);
		panel.add(amountPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		buttonPanel.setBackground(new Color(44, 62, 80));

		JButton submitButton = createButton("Submit");
		submitButton.setPreferredSize(new Dimension(150, 60));
		addHoverEffect(submitButton);
		submitButton.addActionListener(e -> currentState = ATMState.PROCESS_WITHDRAW);
		buttonPanel.add(submitButton);

		JButton backButton = createButton("Back");
		backButton.setPreferredSize(new Dimension(150, 60));
		addHoverEffect(backButton);
		backButton.addActionListener(e -> currentState = ATMState.CHOOSE_TRANSACTION);
		buttonPanel.add(backButton);

		panel.add(buttonPanel);

		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setTransferPanel() {
		clearMainPanel();

		JPanel panel = new JPanel(new GridLayout(6, 1, 0, 10));
		panel.setBackground(new Color(44, 62, 80));

		JLabel accountLabel = new JLabel("Enter recipient's 10-digit account number:");
		accountLabel.setFont(new Font("Arial", Font.BOLD, 30));
		accountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		accountLabel.setForeground(Color.WHITE);
		panel.add(accountLabel);

		transferAccountNumber = new JTextField(10);
		limitTextField(transferAccountNumber, 10);
		transferAccountNumber.setFont(new Font("Arial", Font.BOLD, 25));
		transferAccountNumber.setHorizontalAlignment(JTextField.CENTER);
		JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		accountPanel.setBackground(new Color(44, 62, 80));
		accountPanel.add(transferAccountNumber);
		panel.add(accountPanel);

		JLabel amountLabel = new JLabel("Enter the amount you want to transfer:");
		amountLabel.setFont(new Font("Arial", Font.BOLD, 30));
		amountLabel.setHorizontalAlignment(SwingConstants.CENTER);
		amountLabel.setForeground(Color.WHITE);
		panel.add(amountLabel);

		transferAmount = new JTextField(10);
		transferAmount.setFont(new Font("Arial", Font.BOLD, 25));
		transferAmount.setHorizontalAlignment(JTextField.CENTER);
		JPanel amountPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		amountPanel.setBackground(new Color(44, 62, 80));
		amountPanel.add(transferAmount);
		panel.add(amountPanel);

		JPanel fillerPanel = new JPanel();
		fillerPanel.setBackground(new Color(44, 62, 80));
		panel.add(fillerPanel);

		JButton submitButton = createButton("Submit");
		JButton backButton = createButton("Back");
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 20));
		buttonPanel.setBackground(new Color(44, 62, 80));
		submitButton.setPreferredSize(new Dimension(150, 60));
		backButton.setPreferredSize(new Dimension(150, 60));
		addHoverEffect(submitButton);
		addHoverEffect(backButton);
		buttonPanel.add(submitButton);
		buttonPanel.add(backButton);
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		panel.add(buttonPanel);

		submitButton.addActionListener(e -> currentState = ATMState.PROCESS_TRANSFER);
		backButton.addActionListener(e -> currentState = ATMState.CHOOSE_TRANSACTION);

		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setProcessTransferPanel(boolean transferSuccessful) {
		clearMainPanel();

		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(new Color(44, 62, 80));

		JLabel messageLabel = new JLabel();
		messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		if (transferSuccessful) {
			messageLabel.setText("Transfer Successful!");
		} else {
			messageLabel.setText("Transfer Failed. Please try again.");
		}
		panel.add(messageLabel);
		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setProcessWithdrawPanel(boolean withdrawSuccessful) {
		clearMainPanel();

		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(new Color(44, 62, 80));

		JLabel messageLabel = new JLabel();
		messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		if (withdrawSuccessful) {
			messageLabel.setText("Withdrawal Successful!");
		} else {
			messageLabel.setText("Withdrawal Failed. Insufficient funds.");
		}
		panel.add(messageLabel);
		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setProcessDepositPanel(boolean depositSuccessful) {
		clearMainPanel();

		JPanel panel = new JPanel(new GridLayout(3, 1));
		panel.setBackground(new Color(44, 62, 80));

		JLabel messageLabel = new JLabel();
		messageLabel.setFont(new Font("Arial", Font.BOLD, 30));
		messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		messageLabel.setForeground(Color.WHITE);
		if (depositSuccessful) {
			messageLabel.setText("Deposit Successful!");
		} else {
			messageLabel.setText("Deposit Failed. Please try again.");
		}
		panel.add(messageLabel);
		;

		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	public void setAccountInfoPanel(BankAccount account) {
		clearMainPanel();

		JPanel panel = new JPanel(new GridLayout(3, 1, 0, 10));
		panel.setBackground(new Color(44, 62, 80));

		JPanel cardPanel = new JPanel(new GridLayout(4, 1, 0, 10));
		cardPanel.setBackground(new Color(44, 62, 80));

		BankCard card = account.getCard();
		JLabel cardInfoLabel = new JLabel("Card Information");
		cardInfoLabel.setFont(new Font("Arial", Font.BOLD, 25));
		cardInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		cardInfoLabel.setForeground(Color.WHITE);
		cardPanel.add(cardInfoLabel);

		JLabel cardCodeLabel = new JLabel("Card Code: " + card.getCardCode());
		cardCodeLabel.setFont(new Font("Arial", Font.BOLD, 20));
		cardCodeLabel.setForeground(Color.WHITE);
		cardPanel.add(cardCodeLabel);

		JLabel cardHolderLabel = new JLabel("Card Holder: " + card.getCardHolder());
		cardHolderLabel.setFont(new Font("Arial", Font.BOLD, 20));
		cardHolderLabel.setForeground(Color.WHITE);
		cardPanel.add(cardHolderLabel);

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
		JLabel expireDateLabel = new JLabel("Expiration Date: " + card.getExpireDateOfCard().format(formatter));
		expireDateLabel.setFont(new Font("Arial", Font.BOLD, 20));
		expireDateLabel.setForeground(Color.WHITE);
		cardPanel.add(expireDateLabel);

		panel.add(cardPanel);

		JPanel separatorPanel = new JPanel();
		separatorPanel.setBackground(new Color(44, 62, 80));
		panel.add(separatorPanel);

		JPanel accountPanel = new JPanel(new GridLayout(4, 1, 0, 10));
		accountPanel.setBackground(new Color(44, 62, 80));

		JLabel accountInfoLabel = new JLabel("Account Information");
		accountInfoLabel.setFont(new Font("Arial", Font.BOLD, 25));
		accountInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);
		accountInfoLabel.setForeground(Color.WHITE);
		accountPanel.add(accountInfoLabel);

		JLabel accountNumberLabel = new JLabel("Account Number: " + account.getAccountNumber());
		accountNumberLabel.setFont(new Font("Arial", Font.BOLD, 20));
		accountNumberLabel.setForeground(Color.WHITE);
		accountPanel.add(accountNumberLabel);

		JLabel accountHolderLabel = new JLabel("Account Holder: " + account.getAccountHolder());
		accountHolderLabel.setFont(new Font("Arial", Font.BOLD, 20));
		accountHolderLabel.setForeground(Color.WHITE);
		accountPanel.add(accountHolderLabel);

		JLabel balanceLabel = new JLabel("Balance: " + account.getBalance());
		balanceLabel.setFont(new Font("Arial", Font.BOLD, 20));
		balanceLabel.setForeground(Color.WHITE);
		accountPanel.add(balanceLabel);

		panel.add(accountPanel);

		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		buttonPanel.setBackground(new Color(44, 62, 80));

		JButton backButton = createButton("Back");
		backButton.setPreferredSize(new Dimension(150, 40));
		addHoverEffect(backButton);
		backButton.addActionListener(e -> currentState = ATMState.CHOOSE_TRANSACTION);
		buttonPanel.add(backButton);

		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		mainPanel.add(panel, BorderLayout.CENTER);

		mainPanel.revalidate();
		mainPanel.repaint();
	}

	
	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setBackground(new Color(52, 73, 94));
		button.setForeground(Color.WHITE);
		button.setFocusPainted(false);
		return button;
	}

	private void clearMainPanel() {
		mainPanel.removeAll();
		mainPanel.revalidate();
		mainPanel.repaint();
	}

	private void addHoverEffect(JButton button) {

		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(139, 174, 96));
			}

			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(52, 73, 94));
			}
		});
	}

	public ATMState getCurrentState() {
		return currentState;
	}

	public void setCurrentState(ATMState currentState) {
		this.currentState = currentState;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public void setMainPanel(JPanel mainPanel) {
		this.mainPanel = mainPanel;
	}

	public JLabel getMessageLabel() {
		return messageLabel;
	}

	public void setMessageLabel(JLabel messageLabel) {
		this.messageLabel = messageLabel;
	}

	public JButton getCardButton() {
		return cardButton;
	}

	public void setCardButton(JButton cardButton) {
		this.cardButton = cardButton;
	}

	public JTextField getPinField() {
		return pinField;
	}

	public void setPinField(JTextField pinField) {
		this.pinField = pinField;
	}

	public JButton[] getDigitButtons() {
		return digitButtons;
	}

	public void setDigitButtons(JButton[] digitButtons) {
		this.digitButtons = digitButtons;
	}

	public JButton getClearButton() {
		return clearButton;
	}

	public void setClearButton(JButton clearButton) {
		this.clearButton = clearButton;
	}

	public JButton getDepositButton() {
		return depositButton;
	}

	public void setDepositButton(JButton depositButton) {
		this.depositButton = depositButton;
	}

	public JButton getWithdrawButton() {
		return withdrawButton;
	}

	public void setWithdrawButton(JButton withdrawButton) {
		this.withdrawButton = withdrawButton;
	}

	public JButton getTransferButton() {
		return transferButton;
	}

	public void setTransferButton(JButton transferButton) {
		this.transferButton = transferButton;
	}

	public JButton getBackButton() {
		return backButton;
	}

	public void setBackButton(JButton backButton) {
		this.backButton = backButton;
	}

	public JTextField getCardField() {
		return cardField;
	}

	public void setCardField(JTextField cardField) {
		this.cardField = cardField;
	}

	public JTextField getDepositAmount() {
		return depositAmount;
	}

	public void setDepositAmount(JTextField depositeAmount) {
		this.depositAmount = depositeAmount;
	}

	public JTextField getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(JTextField withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public JTextField getTransferAmount() {
		return transferAmount;
	}

	public void setTransferAmount(JTextField transferAmount) {
		this.transferAmount = transferAmount;
	}

	public JTextField getTransferAccountNumber() {
		return transferAccountNumber;
	}

	public void setTransferAccountNumber(JTextField transferAccountNumber) {
		this.transferAccountNumber = transferAccountNumber;
	}

	private static final long serialVersionUID = 1L;
	private JPanel mainPanel;
	private JLabel messageLabel;
	private JButton cardButton;

	private JTextField pinField;
	private JTextField cardField;
	private JTextField depositAmount;
	private JTextField withdrawAmount;
	private JTextField transferAmount;
	private JTextField transferAccountNumber;

	private JButton[] digitButtons;
	private JButton clearButton;
	private JButton depositButton;
	private JButton withdrawButton;
	private JButton transferButton;
	private JButton backButton;

	private ATMState currentState = ATMState.IDLE;

}
