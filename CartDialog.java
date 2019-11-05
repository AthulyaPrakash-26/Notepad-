package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.ECommerceViewController;
import model.Cart;
import model.Product;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;

public class CartDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static DefaultTableModel dtmProduct;
	private JTextField textField;
	private int row;
	private double total = 0.0;
	private List<Cart> cartList = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			CartDialog dialog = new CartDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public CartDialog() {
		setBounds(100, 100, 698, 478);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			dtmProduct = new DefaultTableModel(new String[] { "Product Name", "Quantity", "Price", "Total" }, 0);
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setBounds(41, 38, 599, 285);
				contentPanel.add(scrollPane);
				table = new JTable(dtmProduct);
				scrollPane.setViewportView(table);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setModel(dtmProduct);
				table.setRowSelectionAllowed(true);
				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
					public void valueChanged(ListSelectionEvent e) {
						// row = table.getSelectedRow();
					}
				});
			}
			try {
				dtmProduct.setRowCount(0);
				{
					textField = new JTextField();
					textField.setBounds(554, 346, 86, 20);
					contentPanel.add(textField);
					textField.setColumns(10);
				}
				{
					JLabel lblNewLabel = new JLabel("Grand Total :");
					lblNewLabel.setBounds(435, 349, 72, 14);
					contentPanel.add(lblNewLabel);
				}

				cartList = ECommerceViewController.viewSelectAll();

				if (cartList != null) {
					for (int i = 0; i < cartList.size(); i++) {
						String product = ECommerceViewController.viewReadPrice(cartList.get(i).getProductName());
						total = Double.parseDouble(cartList.get(i).getQuantity()) * Double.parseDouble(product) + total;
						Object row[] = { cartList.get(i).getProductName(), cartList.get(i).getQuantity(), product,
								Double.parseDouble(cartList.get(i).getQuantity()) * Double.parseDouble(product) };
						dtmProduct.addRow(row);
						table.setModel(dtmProduct);
					}
					textField.setText(Double.toString(total));

					table.setVisible(true);
				} else {
					System.out.println("HI");
				}

			}

			catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		{

			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton buyButton = new JButton("Buy Now");
				buyButton.setActionCommand("Buy Now");
				buttonPane.add(buyButton);
				getRootPane().setDefaultButton(buyButton);
			}
			{
				JButton deleteButton = new JButton("Delete");
				deleteButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						int numRows = table.getSelectedRows().length;
						for (int i = 0; i < numRows; i++) {
							dtmProduct.removeRow(table.getSelectedRow());
							try {
								String price = ECommerceViewController.viewReadPrice(cartList.get(i).getProductName());
								total = total - (Double.parseDouble(price));
								textField.removeAll();
								textField.setText(Double.toString(total));
							} catch (Exception e) {
								e.printStackTrace();
							}

						}

					}
				});
				deleteButton.setActionCommand("Delete");
				buttonPane.add(deleteButton);
			}
		}
	}

}
