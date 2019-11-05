package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Document;

import org.postgresql.util.PSQLException;

import controller.ECommerceViewController;
import model.Product;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import java.awt.Color;

public class UserHome extends JFrame {

	private static JPanel contentPane;
	private JTextField tfSearchItem;
	private static JTable tProduct;
	private static DefaultTableModel dtmProduct;
	private static JFrame frame1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UserHome frame1 = new UserHome();
					frame1.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public UserHome() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Toolkit t = Toolkit.getDefaultToolkit();
		Dimension d = t.getScreenSize();
		int h = d.height;
		int w = d.width;
		setBounds(0, 0, w, h);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		String[] ListContents = {"Home","Add To Cart","Your Orders","My Cart"};
		contentPane.setLayout(null);
		
		tfSearchItem = new JTextField();
		tfSearchItem.setBounds(163, 64, 840, 32);
		contentPane.add(tfSearchItem);
		tfSearchItem.setColumns(10);
		
		JButton btnSearch = new JButton("");
		btnSearch.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\Search.png"));
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//bSearchByproductNameActionPerformed(null);
				 String type = tfSearchItem.getText();
				dispProduct(type);
//				Document doc = tfSearchItem.getDocument();
//				String text = doc.getText(0, doc.getLength());
//				if (!text.toLowerCase().contains(.toLowerCase())) {
//					throw new Exception("String Not Found");
//				}
			}
				});
		btnSearch.setBounds(1030, 64, 47, 32);
		contentPane.add(btnSearch);
		
		JButton btnMobile = new JButton("Mobiles");
		btnMobile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = btnMobile.getText();
				System.out.println(type);
				dispProduct(type);
			}
		});
		btnMobile.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\mobilephone.png"));
		btnMobile.setBounds(163, 261, 166, 192);
		contentPane.add(btnMobile);
		
		JButton btnTv = new JButton("tv");
		btnTv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = btnTv.getText();
				System.out.println(type);
				dispProduct(type);
			}
		});
		btnTv.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\TV.png"));
		btnTv.setBounds(380, 261, 190, 192);
		contentPane.add(btnTv);
		
		JButton btnLaptop = new JButton("laptop");
		btnLaptop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = btnLaptop.getText();
				System.out.println(type);
				dispProduct(type);
			}
		});
		btnLaptop.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\Laptop.png"));
		btnLaptop.setBounds(614, 261, 180, 192);
		contentPane.add(btnLaptop);
		
		JButton btnCart = new JButton("");
		btnCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CartDialog dialog = new CartDialog();
				dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
				dialog.setVisible(true);
			}
		});
		btnCart.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\Cart2.png"));
		btnCart.setBounds(1327, 53, 47, 43);
		contentPane.add(btnCart);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setOpaque(false);
		scrollPane.getViewport().setOpaque(false);
		scrollPane.setBounds(163, 126, 840, 112);
		contentPane.add(scrollPane);
		
		tProduct = new JTable();
		tProduct.setRowHeight(24);
		tProduct.setFont(new Font("Times New Roman", Font.ITALIC, 15));
		tProduct.setOpaque(false);
		 tProduct.getTableHeader().setFont(new Font("Times New Roman", Font.ITALIC, 15));
		scrollPane.setViewportView(tProduct);
		
		JButton btnEarphones = new JButton("Earphones");
		btnEarphones.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = btnEarphones.getText();
				System.out.println(type);
				dispProduct(type);
			}
		});
		btnEarphones.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\earphone.png"));
		btnEarphones.setBounds(845, 261, 158, 192);
		contentPane.add(btnEarphones);
		
		JLabel lblMobilePhones = new JLabel("Mobile Phones");
		lblMobilePhones.setBounds(173, 464, 110, 14);
		contentPane.add(lblMobilePhones);
		
		JLabel lblTv = new JLabel("tv");
		lblTv.setBounds(380, 464, 46, 14);
		contentPane.add(lblTv);
		
		JLabel lblLaptop = new JLabel("Laptop");
		lblLaptop.setBounds(614, 464, 46, 14);
		contentPane.add(lblLaptop);
		
		JLabel lblEarphones = new JLabel("Earphones");
		lblEarphones.setBounds(845, 464, 71, 14);
		contentPane.add(lblEarphones);
		
		JButton btnAddToCart = new JButton("Add To Cart");
		btnAddToCart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				addToCart();
			}
		});
		btnAddToCart.setBackground(Color.WHITE);
		btnAddToCart.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnAddToCart.setBounds(1248, 126, 124, 43);
		contentPane.add(btnAddToCart);
		
		JButton btnBuyNow = new JButton("Buy Now");
		btnBuyNow.setBackground(Color.WHITE);
		btnBuyNow.setFont(new Font("Times New Roman", Font.BOLD, 14));
		btnBuyNow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		btnBuyNow.setBounds(1250, 180, 124, 43);
		contentPane.add(btnBuyNow);
		
		JButton btnGroceries = new JButton("groceries");
		btnGroceries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = btnGroceries.getText();
				System.out.println(type);
				dispProduct(type);
			}
		});
		btnGroceries.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\grocery.png"));
		btnGroceries.setBounds(163, 520, 166, 172);
		contentPane.add(btnGroceries);
		
		JLabel lblGroceries = new JLabel("Groceries");
		lblGroceries.setBounds(163, 703, 62, 14);
		contentPane.add(lblGroceries);
		
		JButton btnHomeAplnce = new JButton("Home Appliances");
		btnHomeAplnce.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String type = btnHomeAplnce.getText();
				System.out.println(type);
				dispProduct(type);
			}
		});
		btnHomeAplnce.setIcon(new ImageIcon("C:\\Users\\1026807\\Desktop\\Images\\homeappliance.png"));
		btnHomeAplnce.setBounds(380, 520, 190, 172);
		contentPane.add(btnHomeAplnce);
		
		JLabel lblHomeAppliances = new JLabel("Home appliances");
		lblHomeAppliances.setBounds(380, 703, 110, 14);
		contentPane.add(lblHomeAppliances);
		
		JButton btnChangeView = new JButton("Change View");
		btnChangeView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				Home frame = new Home();
				frame.setVisible(true);
				frame1.dispose();
			}
		});
		btnChangeView.setBounds(1264, 11, 110, 23);
		contentPane.add(btnChangeView);
		
		JMenuItem returnMenu = new JMenuItem("action") {
            @Override
            public Dimension getPreferredSize() {
                Dimension preferred = super.getPreferredSize();
                Dimension minimum = getMinimumSize();
                Dimension maximum = getMaximumSize();
                preferred.width = Math.min(Math.max(preferred.width, 200),
                        maximum.width);
                preferred.height = Math.min(Math.max(preferred.height, minimum.height),
                        maximum.height);
                return preferred;
            }
        };
		//ImageIcon image = new ImageIcon(getClass().getResource("search-icon.jpeg"));
	   // contentPane.add(image, BorderLayout.NORTH);
		
		

		
		
		
	}
//private void bSearchByproductNameActionPerformed(java.awt.event.ActionEvent evt) {
//        
//
//        try {
//
//            String name;
//            name = tfSearchItem.getText();
//            DefaultTableModel dtm = new DefaultTableModel(new String[]{"ProductID","name","category","price","stock"}, 0);
//           
//			tPTable.setModel(dtm);
//            if (name.equals("")) {
//
//                javax.swing.JOptionPane.showMessageDialog(this, "Please Enter the  Product Name!!");
//            } else {
//               // ArrayList<Product> productList = (ArrayList<Product>) ECommerceViewController.searchProduct();
//
//                for (int i = 0; i < productList.size(); ++i) {
//                    Object[] row = {productList.get(i).getName()};
//                    dtm.addRow(row);
//                    tPTable.setModel(dtm);
//                }
//              
//                tfSearchItem.setText("");
//               
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            javax.swing.JOptionPane.showMessageDialog(this, "Product Not Found!!");
//        }
//    }
	protected void addToCart() {
		int i = tProduct.getSelectedRow();
		String productId = (String) dtmProduct.getValueAt(i, 0);
		//String qnty = (String) comboBox.getSelectedItem();
		String qnty = "1";
		try {
			ECommerceViewController.insertCart(productId, qnty);
			javax.swing.JOptionPane.showMessageDialog(frame1, "Product Added to cart", "ECommerce", 2);
		} catch (PSQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
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
			//javax.swing.JOptionPane.showMessageDialog(frame,"Product details not found", "ECommerce", 2);
			System.out.println("Product not found");
		}

	}
}
