package flap;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener, KeyListener {

    public static FlappyBird flappybird;

    public final int WIDTH= 800, HEIGHT= 800;

    public Renderer renderer;

    public Bird bird;

    public int ticks, yMotion, score;

    public ArrayList<Rectangle> columns;

    public Random rand;

    public boolean gameOver, started;

    public FlappyBird() {
        JFrame jframe= new JFrame();
        Timer timer= new Timer(20, this);

        renderer= new Renderer();
        rand= new Random();

        jframe.setTitle("FlappyBird");
        jframe.add(renderer);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(WIDTH, HEIGHT);
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setVisible(true);
        jframe.setResizable(false);

        bird= new Bird();
        columns= new ArrayList<>();

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

        timer.start();
    }

    public void addColumn(boolean start) {
        int space= 300;
        int width= 100;
        int height= 50 + rand.nextInt(300);

        if (start) {
            columns.add(new Rectangle(WIDTH + width + columns.size() * 300, HEIGHT - height - 150,
                width, height));
            columns.add(new Rectangle(WIDTH + width + (columns.size() - 1) * 300, 0, width,
                HEIGHT - height - space));
        } else {
            columns
                .add(new Rectangle(columns.get(columns.size() - 1).x + 600, HEIGHT - height - 150,
                    width, height));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, width,
                HEIGHT - height - space));
        }

    }

    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);

    }

    public void jump() {
        if (gameOver) {

            bird= new Bird();
            columns.clear();
            yMotion= 0;
            score= 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameOver= false;
        }

        if (!started) {
            started= true;
        } else if (!gameOver) {
            if (yMotion > 0) {
                yMotion= 0;
            }
            yMotion-= 10;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        int speed= 10;

        ticks++ ;

        if (started) {

            for (int i= 0; i < columns.size(); i++ ) {
                Rectangle column= columns.get(i);
                column.x-= speed;
            }

            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion= yMotion + 2;
            }

            for (int i= 0; i < columns.size(); i++ ) {
                Rectangle column= columns.get(i);

                if (column.x + column.width < 0) {
                    columns.remove(column);

                    if (column.y == 0) {
                        addColumn(false);
                    }
                }
            }

            bird.Y+= yMotion;
            bird.yEyes+= yMotion;

            for (Rectangle column : columns) {

                if (bird.X + bird.Width / 2 > column.x + column.width / 2 - 10 &&
                    bird.X + bird.Width / 2 < column.x + column.width / 2 + 10) {
                    if (column.y == 0) {
                        score++ ;
                    }
                }
                if (column.intersects(bird.X, bird.Y, bird.Width, bird.Height)) {
                    gameOver= true;

                    bird.X= column.x - bird.Width;
                    bird.xEyes= column.x - bird.wEyes;
                }
            }

            if (bird.Y > HEIGHT - 150 || bird.Y < 0) {
                gameOver= true;
            }

            if (bird.Y + yMotion >= HEIGHT - 150) {
                bird.Y= HEIGHT - 150 - bird.Height;
                bird.yEyes= bird.Y + 5;
            }

            renderer.repaint();
        }
    }

    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.orange);
        g.fillRect(0, HEIGHT - 150, WIDTH, 150);

        g.setColor(Color.green);
        g.fillRect(0, HEIGHT - 150, WIDTH, 20);

        g.setColor(Color.yellow);
        g.fillOval(bird.X, bird.Y, bird.Width, bird.Height);

        g.setColor(Color.white);
        g.fillOval(bird.xEyes, bird.yEyes, bird.wEyes, bird.hEyes);

        for (Rectangle column : columns) {
            paintColumn(g, column);
        }

        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));

        if (!started) {
            g.drawString("Click to Start", 80, HEIGHT / 2 - 50);
        }

        if (gameOver) {
            g.drawString("Game Over!", 110, HEIGHT / 2 - 50);
        }

        if (!gameOver && started) {
            g.drawString(String.valueOf(score), WIDTH / 2 - 25, 100);
        }

    }

    public static void main(String[] args) {

        flappybird= new FlappyBird();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

}
