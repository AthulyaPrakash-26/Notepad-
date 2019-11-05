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
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class Home extends JFrame {

	private JPanel contentPane;
	private static Home frame;
	private static JTable tProduct;
	private static DefaultTableModel dtmProduct = new DefaultTableModel(new String[] { "Product Name", "Price" }, 0);
	private static JComboBox<String> comboBox;
	private JLabel lblName;
	private JButton btnAddtoCart;
	int i;
	private JTextField tfSearch;

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
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		int h = d.height;
		int w = d.width;
	//	setBounds(0, 0, w, h);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 914, 32);
		contentPane.add(menuBar);

		JMenu cartegory = new JMenu("Categories");
		cartegory.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		menuBar.add(cartegory);
		JMenu orders = new JMenu("Orders");
		orders.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 15));
		menuBar.add(orders);

		JMenuItem mobiles = new JMenuItem("Mobiles");
		mobiles.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		mobiles.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = mobiles.getText();
				dispProductByCategory(type);
				lblName.setText(type);

			}
		});
		cartegory.add(mobiles);

		JMenuItem computers = new JMenuItem("Computers");
		computers.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		computers.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = computers.getText();
				dispProductByCategory(type);
				lblName.setText(type);
			}
		});
		cartegory.add(computers);

		JMenuItem earphones = new JMenuItem("Earphones");
		earphones.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		earphones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = earphones.getText();
				dispProductByCategory(type);
				lblName.setText(type);
			}
		});
		cartegory.add(earphones);

		JMenuItem homeappliances = new JMenuItem("Home Appliances");
		homeappliances.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		homeappliances.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = homeappliances.getText();
				System.out.println(type);
				dispProductByCategory(type);
				lblName.setText(type);
			}
		});
		cartegory.add(homeappliances);

		// JMenuItem viewCart = new JMenuItem("View Cart");
		// viewCart.addActionListener(new ActionListener() {
		// public void actionPerformed(ActionEvent arg0) {
		//// dialog.dispCart();
		// new ViewCartDialog().setVisible(true);
		// dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		// dialog.setVisible(true);
		// }
		// });
		// cart.add(viewCart);

		JMenuItem order = new JMenuItem("Your Orders");
		order.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		order.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		orders.add(order);

		tProduct = new JTable();
		tProduct.setShowVerticalLines(false);
		tProduct.setShowHorizontalLines(false);
		tProduct.setShowGrid(false);
		tProduct.setRowHeight(24);
		tProduct.setFont(new Font("Times New Roman", Font.ITALIC, 18));
		tProduct.setOpaque(false);
		 tProduct.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC, 18));
		tProduct.setBounds(214, 341, 1, 1);
		tProduct.setAutoCreateRowSorter(true);
		tProduct.setUpdateSelectionOnSort(true);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(46, 179, 801, 502);
		scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		contentPane.add(scrollPane);
		scrollPane.setViewportView(tProduct);
		tProduct.setBorder(new LineBorder(new Color(0, 0, 0)));

		comboBox = new JComboBox();
		comboBox.setBounds(642, 692, 58, 32);
		contentPane.add(comboBox);

		JLabel lblNewLabel_1 = new JLabel("Select Quantity  :");
		lblNewLabel_1.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		lblNewLabel_1.setBounds(526, 698, 106, 21);
		contentPane.add(lblNewLabel_1);

		JButton cartButton = new JButton("");
		cartButton.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\Cart2.png"));
		cartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CartDialog dialog = new CartDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		cartButton.setBounds(805, 43, 42, 41);
		contentPane.add(cartButton);

		JButton btnAddtoCart = new JButton("Add to Cart");
		btnAddtoCart.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 13));
		btnAddtoCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToCart();
			}
		});
		btnAddtoCart.setBounds(741, 692, 106, 33);
		contentPane.add(btnAddtoCart);

		lblName = new JLabel("");
		lblName.setBounds(55, 142, 128, 32);
		contentPane.add(lblName);
		
		tfSearch = new JTextField();
		tfSearch.setBounds(45, 112, 749, 32);
		contentPane.add(tfSearch);
		tfSearch.setColumns(10);
		
		JButton btnSearch = new JButton("srch");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if ("".equals(tfSearch.getText()) || tfSearch.getText() == null) {
					javax.swing.JOptionPane.showMessageDialog(frame, "Please enter the product", "ECommerce", 2);
				}
				else {
				String type = tfSearch.getText();
				dispProductByName(type);
				//dispProductByCategory(type);
				}
			}});
		btnSearch.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\Search.png"));
		btnSearch.setBounds(805, 112, 42, 32);
		contentPane.add(btnSearch);
		
		JLabel lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\home1.jpg"));
		lblNewLabel.setBounds(46, 179, 801, 502);
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_2 = new JLabel("New label");
		lblNewLabel_2.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\blue.jpg"));
		lblNewLabel_2.setBounds(0, 0, 932, 751);
		contentPane.add(lblNewLabel_2);

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
		String productName = (String) dtmProduct.getValueAt(i, 0);
		String qnty = (String) comboBox.getSelectedItem();
		try {
			ECommerceViewController.insertCart(productName, qnty);
			javax.swing.JOptionPane.showMessageDialog(frame, "Product Added to cart", "ECommerce", 2);
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dispProductByName(String name) {
		List<Product> list = null;

		try {
			//dtmProduct.setRowCount(0);
			System.out.println(name);

			 dtmProduct.setRowCount(0);
			 tProduct.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			 tProduct.setModel(dtmProduct);
			 tProduct.setRowSelectionAllowed(true);

			list = ECommerceViewController.viewReadByName(name);

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
	public static void dispProductByCategory(String category) {
		List<Product> list = null;

		try  {
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