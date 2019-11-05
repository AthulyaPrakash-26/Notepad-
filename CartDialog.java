package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
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
import javax.swing.ImageIcon;
import java.awt.Color;

public class CartDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTable table;
	private static DefaultTableModel dtmProduct;
	private JTextField textField;
	private int row;
	private double total = 0.0;
	private List<Cart> cartList = null;
	private Object obj;

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
		setBounds(100, 100, 669, 478);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			dtmProduct = new DefaultTableModel(new String[] { "Product Name", "Quantity", "Price", "Total" }, 0);
			{
				JScrollPane scrollPane = new JScrollPane();
				scrollPane.setOpaque(false);
				scrollPane.getViewport().setOpaque(false);
				scrollPane.setBounds(41, 64, 558, 259);
				contentPanel.add(scrollPane);
				table = new JTable(dtmProduct);
				table.setRowHeight(24);
				table.setFont(new Font("Times New Roman", Font.ITALIC, 15));
				table.setOpaque(false);
				 table.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC, 15));
				scrollPane.setViewportView(table);
				table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				table.setModel(dtmProduct);
				table.setRowSelectionAllowed(true);
//				table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//					public void valueChanged(ListSelectionEvent e) {
//						obj = dtmProduct.getDataVector().elementAt(table.getSelectedRow());					
//					}
//				});
			}
			try {
				dtmProduct.setRowCount(0);
				{
					textField = new JTextField();
					textField.setBounds(513, 346, 86, 20);
					contentPanel.add(textField);
					textField.setColumns(10);
				}
				{
					JLabel lblNewLabel = new JLabel("Grand Total :");
					lblNewLabel.setForeground(Color.GREEN);
					lblNewLabel.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
					lblNewLabel.setBounds(411, 348, 92, 14);
					contentPanel.add(lblNewLabel);
				}
				cartList = ECommerceViewController.viewSelectAll();	
				
				{

					JPanel buttonPane = new JPanel();
					buttonPane.setBackground(Color.GREEN);
					buttonPane.setBounds(436, 378, 163, 35);
					contentPanel.add(buttonPane);
					buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
					{
						JButton buyButton = new JButton("Buy Now");
						buyButton.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {

								try {
									obj = dtmProduct.getDataVector().elementAt(table.getSelectedRow());		
									int row=table.getSelectedRow();
									String str = obj.toString();
									System.out.println(str);
									String[] orderList = str.split(",");
									String name = orderList [0];
									String qnty = orderList [1];
									String price = orderList [2];
									String total = orderList [3];
									ECommerceViewController.insertOrder(name, qnty, price, total);
								//	ECommerceViewController.viewDelete();
									javax.swing.JOptionPane.showMessageDialog(buyButton, "Order placed", "ECommerce", 2);
									table.remove(0);
									dtmProduct.removeRow(table.getSelectedRow());
								
								} catch (Exception e) {
									javax.swing.JOptionPane.showMessageDialog(buyButton, "please select a product", "ECommerce", 2);
								}
							}
						});
						buyButton.setFont(new Font("Times New Roman", Font.ITALIC, 13));
						buyButton.setActionCommand("Buy Now");
						buttonPane.add(buyButton);
						getRootPane().setDefaultButton(buyButton);
					}
					{
						JButton deleteButton = new JButton("Delete");
						buttonPane.add(deleteButton);
						deleteButton.setFont(new Font("Times New Roman", Font.ITALIC, 13));
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
					}
				}
				{
					JLabel lblNewLabel_1 = new JLabel("New label");
					lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\home2.jpg"));
					lblNewLabel_1.setBounds(0, 0, 663, 439);
					contentPanel.add(lblNewLabel_1);
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
	}

}