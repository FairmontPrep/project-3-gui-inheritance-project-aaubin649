import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


class Ingredient extends JPanel {
    protected Image img;
    protected String description = "";

    public Ingredient(String fileName) {
        loadImage(fileName);
    }

    protected void loadImage(String fileName) {
        try {
            img = ImageIO.read(new File(fileName));
        } catch (Exception e) {
            System.out.println("Image not found: " + fileName);
        }
    }

    public void draw(Graphics g, int width, int height, JPanel panel) {
        if (img != null) g.drawImage(img, 0, 0, width, height, panel);
    }

    public String getDescription() {
        return description;
    }
}


class Crust extends Ingredient {
    public Crust() {
        super("crust.png");
        description = "Crust, ";
    }
}

class Sauce extends Ingredient {
    public Sauce() {
        super("marinara.png");
        description = "Marinara sauce, ";
    }
}

class Cheese extends Ingredient {
    public Cheese() {
        super("cheese.png");
        description = "Cheese, ";
    }
}

class Topping extends Ingredient {
    public Topping() {
        super(Math.random() < 0.5 ? "pepperoni.png" : "bacon.png");
        description = (img.toString().contains("pepperoni")) ? "pepperoni" : "bacon";
    }
}


class Pizza extends JPanel {
    private Crust crust;
    private Sauce sauce;
    private Cheese cheese;
    private Topping topping;

    private String description = "Pizza with ";

    public Pizza() {
        setTopping();
    }

    public void setTopping() {
        crust = new Crust();
        sauce = new Sauce();
        cheese = new Cheese();
        topping = new Topping();

        description = crust.getDescription() + sauce.getDescription() + cheese.getDescription() + topping.getDescription();
        repaint();
    }

    public String getDescription() {
        return description;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        crust.draw(g, this.getWidth(), this.getHeight(), this);
        sauce.draw(g, this.getWidth(), this.getHeight(), this);
        cheese.draw(g, this.getWidth(), this.getHeight(), this);
        topping.draw(g, this.getWidth(), this.getHeight(), this);
    }
}


public class PizzaGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Pizza Builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.setLayout(new BorderLayout());

        Pizza pizza = new Pizza();
        frame.add(pizza, BorderLayout.CENTER);

        JLabel descriptionLabel = new JLabel(pizza.getDescription(), SwingConstants.CENTER);
        frame.add(descriptionLabel, BorderLayout.SOUTH);

        JButton randomButton = new JButton("Random Topping");
        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pizza.setTopping();
                descriptionLabel.setText(pizza.getDescription());
            }
        });

        frame.add(randomButton, BorderLayout.NORTH);
        frame.setVisible(true);
    }
}
