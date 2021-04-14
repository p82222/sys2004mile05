package mystore;

import java.io.IOException;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class StoreView {

    private final mystore.StoreManager sm;
    private final int cartID;
    private final int PRODUCT_FRAME_SIZE = 240;
    private final JFrame frame;

    public StoreView(mystore.StoreManager sm) {
        this.sm = sm;
        cartID = sm.assignNewCartID();
        frame = new JFrame();
    }

    private JPanel makeButtonPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(200, 650));

        ImageIcon cartIcon = new ImageIcon(this.getClass().getResource("cart.png"));
        JButton viewCartButton = new JButton("View Cart", cartIcon);
        viewCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                StringBuilder sb = new StringBuilder();

                for (mystore.ProductStockPair item : sm.getCartContents(cartID)) {
                    sb.append(item.stock).append(" | ").append(item.product.name).append(" | $").append(item.product.price).append('\n');
                }
                sb.append("Total: $").append(sm.getCartTotal(cartID));

                JOptionPane.showMessageDialog(panel, sb.toString(),"My Cart", 1);
            }
        });

        JButton checkoutButton = new JButton("Checkout");
        checkoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (sm.getCartContents(cartID) != null) {
                    StringBuilder sb = new StringBuilder();

                    for (mystore.ProductStockPair item : sm.getCartContents(cartID)) {
                        sb.append(item.stock).append(" | ").append(item.product.name).append(" | $").append(item.product.price).append('\n');
                    }
                    sb.append("Total: $").append(sm.getCartTotal(cartID));

                    JOptionPane.showMessageDialog(panel, sb.toString(),"Thank you for shopping!", 1);
                    frame.dispose();
                }
            }
        });

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                sm.clearCartContents(cartID);
                frame.dispose();
            }
        });

        JEditorPane editorPane = new JEditorPane();
        editorPane.setEditable(false);
        editorPane.setPreferredSize(new Dimension(200, 450));
        java.net.URL helpURL = this.getClass().getResource("mystore/Banner.html");
        if (helpURL != null) {
            try {
                editorPane.setPage(helpURL);
            } catch (IOException e) {
                System.err.println("Attempted to read a bad URL: " + helpURL);
            }
        } else {
            System.err.println("Couldn't find file: TextSampleDemoHelp.html");
        }
        Border border = BorderFactory.createEtchedBorder(EtchedBorder.RAISED);
        editorPane.setBorder(border);

        JPanel temp = new JPanel();
        temp.setLayout(new BoxLayout(temp, BoxLayout.Y_AXIS));
        temp.setBorder(border);

        viewCartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        checkoutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        temp.add(viewCartButton);
        temp.add(checkoutButton);
        temp.add(quitButton);

        panel.add(temp, BorderLayout.PAGE_START);
        panel.add(editorPane, BorderLayout.PAGE_END);
        return panel;
    }

    private JPanel makeBrowsePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setPreferredSize(new Dimension(500,
                ((sm.getNumOfProducts()/2) + 1) * (PRODUCT_FRAME_SIZE + 8)));

        // refactoring browse page here
        for (mystore.ProductStockPair item : sm.getAvailableProducts()) {
            addProductFrame(item.product, panel);
        }
        return panel;
    }

    private void addProductFrame(mystore.Product p, Container pane) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(PRODUCT_FRAME_SIZE, PRODUCT_FRAME_SIZE));

        JLabel img = new JLabel(new ImageIcon(this.getClass().getResource("books.jpg")));
        JLabel desc = new JLabel("($" + p.price + ")" + " - Stock: " + sm.getProductStock(p));

        JButton removeFromCartButton = new JButton("-");
        JButton addToCartButton = new JButton("+");

        removeFromCartButton.setEnabled(false);
        removeFromCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (sm.removeFromCart(cartID, p, 1)) {
                    desc.setText("($" + p.price + ")" + " - Stock: " + sm.getProductStock(p));
                    addToCartButton.setEnabled(true);
                }

                if (!sm.isInCart(cartID, p)) {
                    removeFromCartButton.setEnabled(false);
                }
            }
        });

        addToCartButton.setEnabled(sm.getProductStock(p) > 0);
        addToCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (sm.addToCart(cartID, p, 1)) {
                    removeFromCartButton.setEnabled(true);
                    desc.setText("($" + p.price + ")" + " - Stock: " + sm.getProductStock(p));
                }

                if (sm.getProductStock(p) < 1) {
                    addToCartButton.setEnabled(false);
                }
            }
        });

        int r = (int)(Math.random()*256);
        int g = (int)(Math.random()*256);
        int b = (int)(Math.random()*256);

        // luma coefficient because fancy
        double luma = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
        while (luma < 75) {
            r = (int)(Math.random()*256);
            g = (int)(Math.random()*256);
            b = (int)(Math.random()*256);
            luma = (0.2126 * r) + (0.7152 * g) + (0.0722 * b);
        }

        Color c = new Color(r, g, b);
        c = c.brighter();

        JPanel temp1 = new JPanel(new GridBagLayout());

        temp1.add(addToCartButton);
        temp1.add(removeFromCartButton);
        temp1.setBackground(c);

        panel.add(desc, BorderLayout.PAGE_START);
        panel.add(img, BorderLayout.CENTER);
        panel.add(temp1, BorderLayout.PAGE_END);
        panel.setBackground(c);

        Border blackLine = BorderFactory.createLineBorder(Color.black);
        TitledBorder title = BorderFactory.createTitledBorder(blackLine, p.name);
        panel.setBorder(title);

        pane.add(panel);
    }

    public void displayGUI() {
        frame.setSize(740,700);
        frame.setResizable(false);

        Container container = frame.getContentPane();
        JLabel welcomeBanner = new JLabel(" Welcome to The Course Store! (ID: " + cartID + ")");
        welcomeBanner.setFont(new Font(Font.DIALOG, Font.BOLD, 25));
        container.add(welcomeBanner, BorderLayout.PAGE_START);

        JPanel browsePanel = makeBrowsePanel();
        JPanel buttonPanel = makeButtonPanel();

        container.add(new JScrollPane(browsePanel,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), BorderLayout.LINE_START);
        container.add(buttonPanel, BorderLayout.LINE_END);

        frame.setTitle("Client StoreView");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if(JOptionPane.showConfirmDialog(frame, "Your cart will be lost. Are you sure?") == JOptionPane.OK_OPTION){
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        StoreManager sm = new StoreManager();
        StoreView sv1 = new StoreView(sm);
        sv1.displayGUI();
    }
}
