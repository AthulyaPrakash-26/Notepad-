package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import controller.ECommerceViewController;
import exception.ProductNotFoundException;
import model.Cart;
import model.Order;

import javax.swing.JTable;
import javax.swing.JTextField;

public class OrderDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable orderTable;
	private static DefaultTableModel dtmProduct;
	private List<Order> orderList = null;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			OrderDialog dialog = new OrderDialog();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public OrderDialog() {
		setBounds(100, 100, 713, 417);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		
		//orderTable.setBounds(48, 168, 362, -133);
		//contentPanel.add(orderTable);
		{
			dtmProduct = new DefaultTableModel(new String[] { "Product Name", "Quantity", "Price", "Total" }, 0);
			{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(41, 38, 599, 285);
			contentPanel.add(scrollPane);
			orderTable = new JTable();
			scrollPane.setViewportView(orderTable);
			// table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			// table.setSelectionMode(0);//-------------------------------
			orderTable.setModel(dtmProduct);
			orderTable.selectAll();
			}
			try {
				dtmProduct.setRowCount(0);
				orderList = ECommerceViewController.viewOrderSelect();

				if (orderList != null) {
					for (int i = 0; i < orderList.size(); i++) {
						Object row[] = { orderList.get(i).getName(), orderList.get(i).getQuantity(),orderList.get(i).getPrice(),orderList.get(i).getTotal() };
						dtmProduct.addRow(row);
						orderTable.setModel(dtmProduct);
					}
					orderTable.setVisible(true);
				} else {
					System.out.println("HI");
				}
			} catch (ProductNotFoundException e) {
				// javax.swing.JOptionPane.showMessageDialog(dialog, "Product Not found",
				// "ECommerce", 2);
			} catch (Exception ex) {
				ex.printStackTrace();

			}
			
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
}
