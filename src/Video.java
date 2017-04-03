import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

/**
 * Created by AZERNAX on 19/02/2017.
 */
public class Video extends JPanel implements ActionListener {


    //deklaracja zmiennych
    private Image head;
    private Image body;
    private Image cegla;
    private Image apple;
    private ArrayList<Integer> x,y;
    static Timer timer;
    private final int DELAY = 150; //opoznienie
    static int dots;
    private boolean left = true;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private int apple_x;
    private int apple_y;
    private int tmp = 0;
    private boolean status = true;
    private int sizeField = 500;


    //utworzenie pliku do zapisywania logow
    File plik = new File("log.txt");
    PrintWriter zapis = new PrintWriter("log.txt");




        public Video() throws FileNotFoundException {
            addKeyListener(new KAdapter());
            setBackground(Color.black);

                ImageIcon ih = new ImageIcon(this.getClass().getResource("head.jpg"));
                head = ih.getImage();

                ImageIcon ib = new ImageIcon(this.getClass().getResource("body.jpg"));
                body = ib.getImage();

                ImageIcon ic = new ImageIcon(this.getClass().getResource("cegla2.jpg"));
                cegla = ic.getImage();

                ImageIcon ia = new ImageIcon(this.getClass().getResource("apple.jpg"));
                apple = ia.getImage();

            setFocusable(true);
            rozpocznijGre();
        }

        public void rozpocznijGre()
        {
            x = new ArrayList<Integer>();
            y = new ArrayList<Integer>()
            ;

            dots = 3;

            for (int i = 0; i < dots; i++) {
                x.add(180 + i*10);
                y.add(180);
            }
            locApple();
            timer = new Timer(DELAY,this);
        }

        public void paint(Graphics g)
        {
            super.paint(g);

            if (true) {
                g.drawImage(apple, apple_x, apple_y, this);

                for (int i = 0; i < dots; i++) {
                    if (i == 0)
                        g.drawImage(head, x.get(i), y.get(i), this);
                    else
                        g.drawImage(body, x.get(i), y.get(i), this);
                }


                //rysowanie cegiel

     /* y0 -> */for(int j = 0; j<sizeField+1; j++)
                {
                    g.drawImage(cegla,j,0,this);
                    g.drawImage(cegla,j,sizeField,this);
                    j+=9;
                }


                for(int j = 0; j<sizeField; j++)
                {
                    g.drawImage(cegla,0,j,this);
                    g.drawImage(cegla,sizeField,j,this);
                    j+=9;
                }
            }
        }




    public void ruch() {

        for (int i = dots-1; i > 0; i--) {
            x.set(i, x.get(i-1));
            y.set(i, y.get(i-1));
        }

        if (left) {
            tmp = x.get(0);
            x.set(0, tmp-10);
        }
        if (right) {
            tmp = x.get(0);
            x.set(0, tmp+10);
        }
        if (up) {
            tmp = y.get(0);
            y.set(0, tmp-10);
        }
        if (down) {
            tmp = y.get(0);
            y.set(0, tmp+10);
        }
    }


    public void checkCollision()
    {
        if(x.get(0) > (sizeField - 10) ) status = false;
        if(y.get(0) > (sizeField - 10) ) status = false;
        if(x.get(0) < 10 ) status = false;
        if(y.get(0) < 10 ) status = false;
    }



    public void locApple()
    {
        boolean czyJest= true;

        while(czyJest)
        {
            int l = (int) (Math.random()*sizeField/10);
            apple_x = l*10;
            l = (int) (Math.random()*sizeField/10);
            apple_y = l*10;

            if ((x.contains(apple_x)) && (y.contains(apple_y)))
                continue;
            else
                czyJest = false;
        }
    }

    public void checkApple()
    {

        zapis.println("x = " + x.get(0));
        zapis.println("y = " + y.get(0));
        zapis.println("apple_X = " + apple_x);
        zapis.println("apple_Y = " + apple_y);


        if((x.get(0) == apple_x) && (y.get(0) == apple_y))
        {
            zapis.println("znalazlem jablko");
            dots++;

            x.add(x.get(1));
            y.add(y.get(1));

            locApple();
        }
    }
    public void actionPerformed(ActionEvent e) {
            if(status) {
                checkApple();
                checkCollision();
                ruch();
                repaint();
            }else
            {
                timer.stop();
                zapis.close();
            }
    }

    private class KAdapter extends KeyAdapter {

        public void keyPressed(KeyEvent e) {

            int key = e.getKeyCode();

            if ((key == KeyEvent.VK_LEFT) && (!right)) {
                left = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_RIGHT) && (!left)) {
                right = true;
                up = false;
                down = false;
            }

            if ((key == KeyEvent.VK_UP) && (!down)) {
                up = true;
                right = false;
                left = false;
            }

            if ((key == KeyEvent.VK_DOWN) && (!up)) {
                down = true;
                right = false;
                left = false;
            }
        }
    }

}
