package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import org.postgresql.util.PSQLException;
import controller.ECommerceViewController;
import model.Product;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class Home extends JFrame {

	private JPanel contentPane;
	private static Home frame;
	private static JTable tProduct;
	private static DefaultTableModel dtmProduct = new DefaultTableModel(new String[] { "Product Name", "Price" }, 0);
	private static JComboBox<String> comboBox;
	private JLabel lblName;
	private JButton btnAddtoCart;
	int i;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frame = new Home();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Home() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1284, 811);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 1268, 32);
		contentPane.add(menuBar);

		JMenu cartegory = new JMenu("Categories");
		menuBar.add(cartegory);
		JMenu orders = new JMenu("Orders");
		menuBar.add(orders);
		JMenu search = new JMenu("Search");
		menuBar.add(search);

		JMenuItem mobiles = new JMenuItem("Mobiles");
		mobiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = mobiles.getText();
				dispProduct(type);
				lblName.setText(type);

			}
		});
		cartegory.add(mobiles);

		JMenuItem computers = new JMenuItem("Computers");
		computers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = computers.getText();
				dispProduct(type);
				lblName.setText(type);
			}
		});
		cartegory.add(computers);

		JMenuItem earphones = new JMenuItem("Earphones");
		earphones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = earphones.getText();
				dispProduct(type);
				lblName.setText(type);
			}
		});
		cartegory.add(earphones);

		JMenuItem homeappliances = new JMenuItem("Home Appliances");
		homeappliances.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = homeappliances.getText();
				dispProduct(type);
				lblName.setText(type);
			}
		});
		cartegory.add(homeappliances);

		JMenuItem order = new JMenuItem("Your Orders");
		order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				OrderDialog dialog = new OrderDialog();
				dialog.setVisible(true);
				
			}
		});
		orders.add(order);

		JMenuItem find = new JMenuItem("Find");
		search.add(find);

		tProduct = new JTable();
		tProduct.setBounds(214, 341, 1, 1);
		tProduct.setAutoCreateRowSorter(true);
		tProduct.setUpdateSelectionOnSort(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(36, 176, 848, 487);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(tProduct);
		tProduct.setBorder(new LineBorder(new Color(0, 0, 0)));

		comboBox = new JComboBox();
		comboBox.setBounds(601, 691, 89, 32);
		contentPane.add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("Select Quantity  :");
		lblNewLabel_1.setBounds(485, 697, 106, 21);
		contentPane.add(lblNewLabel_1);
	
//		 JLabel label = new JLabel();
//	     label.setIcon(new ImageIcon("C:\\Users\\1026808\\eclipse-workspace\\ECommerce\\images\\ography-of-assorted-color-clothes-hanged-1078958"));// your image here
//	     contentPane.add(label);

		JButton cartButton = new JButton("Cart");
		cartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CartDialog dialog = new CartDialog();
				dialog.setVisible(true);
			}
		});
		cartButton.setBounds(1179, 34, 89, 32);
		contentPane.add(cartButton);

		btnAddtoCart = new JButton("Add to Cart");
		btnAddtoCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToCart();
			}
		});
		btnAddtoCart.setBounds(714, 691, 106, 33);
		contentPane.add(btnAddtoCart);

		lblName = new JLabel("");
		lblName.setBounds(56, 112, 128, 32);
		contentPane.add(lblName);
//***************************************************************		
		
		JButton btnBuyNow = new JButton("Buy Now");
		btnBuyNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Double total = 0.0;
				String name = tProduct.getModel().getValueAt(tProduct.getSelectedRow(), 0).toString();
				String price = tProduct.getModel().getValueAt(tProduct.getSelectedRow(), 1).toString();
     			String qnty = (String) comboBox.getSelectedItem();
				total = Double.parseDouble(qnty) * Double.parseDouble(price);
				//System.out.println(total);
				try {
					ECommerceViewController.insertOrder(name, qnty, price, total.toString());
					javax.swing.JOptionPane.showMessageDialog(btnBuyNow, "Order placed", "ECommerce", 2);
				} catch (PSQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnBuyNow.setBounds(1030, 173, 89, 23);
		contentPane.add(btnBuyNow);
		
//*******************************************

		tProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				for (int i = 0; i < dtmProduct.getRowCount(); i++) {
					if (tProduct.isRowSelected(i)) {
						String name = tProduct.getModel().getValueAt(tProduct.getSelectedRow(), 0).toString();
						try {
							String stock = ECommerceViewController.viewReadStock(name);
							comboBox.removeAllItems();
							for (int j = 1; j <= Integer.parseInt(stock); j++) {
								comboBox.addItem(String.valueOf(j));
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});	
	        
	}

	protected void addToCart() {
		String productName = (String) dtmProduct.getValueAt(tProduct.getSelectedRow(), 0);//-------------------
		String qnty = (String) comboBox.getSelectedItem();
		try {
			ECommerceViewController.insertCart(productName, qnty);
			javax.swing.JOptionPane.showMessageDialog(btnAddtoCart, "Product Added to cart", "ECommerce", 2);
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dispProduct(String category) {
		List<Product> list = null;

		try {
			dtmProduct.setRowCount(0);
			System.out.println(category);
			list = ECommerceViewController.viewReadByCategory(category);
			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Object row[] = { list.get(i).getName(), list.get(i).getPrice() };
					dtmProduct.addRow(row);
					tProduct.setModel(dtmProduct);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			javax.swing.JOptionPane.showMessageDialog(frame, "Product details not found", "ECommerce", 2);
		}

	}
}
