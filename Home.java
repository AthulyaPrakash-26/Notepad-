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
import javax.swing.JButton;

public class Home extends JFrame {

	private JPanel contentPane;
	private static Home frame;
	private static JTable tProduct;
	private static DefaultTableModel dtmProduct;
	private static JComboBox<String> comboBox;
	private ViewCartDialog dialog;
	int i;
	private JTextField textField;
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
		setBounds(100, 100, 930, 790);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 904, 32);
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
				textField.setText(type);
			}
		});
		cartegory.add(mobiles);
		
		JMenuItem computers = new JMenuItem("Computers");
		computers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 String type = computers.getText();
			     dispProduct(type);
			     textField.setText(type);
			}
		});
		cartegory.add(computers);
		
		JMenuItem earphones = new JMenuItem("Earphones");
		earphones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = earphones.getText();
			    dispProduct(type);
			    textField.setText(type);
			}
		});
		cartegory.add(earphones);
		
		JMenuItem homeappliances = new JMenuItem("Home Appliances");
		homeappliances.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = homeappliances.getText();
			    dispProduct(type);
			    textField.setText(type);
			}
		});
		cartegory.add(homeappliances);
		
//		JMenuItem viewCart = new JMenuItem("View Cart");
//		viewCart.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
////			    dialog.dispCart();
//				new ViewCartDialog().setVisible(true);
//				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
//				dialog.setVisible(true);
//			}
//		});
//		cart.add(viewCart);

		JMenuItem order = new JMenuItem("Your Orders");
		order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
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
		
		JButton cartButton = new JButton("Cart");
		cartButton.setBounds(795, 34, 89, 32);
		contentPane.add(cartButton);
		
		JButton btnAddtoCart = new JButton("Add to Cart");
		btnAddtoCart.setBounds(714, 691, 106, 33);
		contentPane.add(btnAddtoCart);
		
		textField = new JTextField();
		textField.setBounds(43, 110, 157, 32);
		contentPane.add(textField);
		textField.setColumns(10);
		
		tProduct.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {

			    i = tProduct.getSelectedRow();
				//String value = (String) dtmProduct.getValueAt(i, 4);
			    
//				comboBox.removeAllItems();
//				for (int j = 1; j <= Integer.parseInt(value); j++) {
//					comboBox.addItem(String.valueOf(j));
//				}
			}
		});
	}

	protected void addToCart() {
		String productId = (String) dtmProduct.getValueAt(i, 0);
		String qnty = (String)comboBox.getSelectedItem();
		try {
			ECommerceViewController.insertCart(productId,qnty);
			javax.swing.JOptionPane.showMessageDialog(frame, "Product Added to cart", "ECommerce", 2);
		} catch (PSQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			
	}

	public static void dispProduct(String category) {
		List<Product> list = null;

		try {
			dtmProduct = new DefaultTableModel(
					new String[] { "Product Name", "Price"}, 0);
			dtmProduct.setRowCount(0);
			tProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			tProduct.setModel(dtmProduct);
			tProduct.setRowSelectionAllowed(true);

			list = ECommerceViewController.viewReadByCategory(category);

			if (list != null) {
				for (int i = 0; i < list.size(); i++) {
					Object row[] = { list.get(i).getName(), list.get(i).getPrice()};
					dtmProduct.addRow(row);
					tProduct.setModel(dtmProduct);
				}
			}

		} catch (Exception e) {
			javax.swing.JOptionPane.showMessageDialog(frame, "Product details not found", "ECommerce", 2);
		}

	}

	
	
}
