import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

/**
 * Created by AZERNAX on 12/02/2017.
 */
public class Snake extends JFrame{

    static JButton start;


    public Snake() throws FileNotFoundException {
        super("Snake Online!");                                 //tytul okna
        setSize(700,540);                               //wymiary okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);              //nacisniecie X zamyka program
        setResizable(false);                                        //brak mozliwosci zmiany rozmiaru
        setLocation(500,500);                                    //ustawienie poczatkowej lokazlizacji okna


        start = new JButton("Start");                       //utworzenie przycisku start
        start.setBounds(560, 100, 80, 30);  //ustawienie lokalizacji i wielkosci przycisku start
        start.addActionListener(new Start());                   //skutek nacisniecia przycisku start



        Video video = new Video();                                //utworzenie obiektu video (panelu)
        video.setBounds(0, 0, 510, 510);    //ustawienie lokalizacji i wielkosci pola do gry
        video.setLayout(null);                                   //wyzerowanie layautu - polozenia


        video.add(start);                                       //dodanie przycisku start do panelu video
        add(video);                                             //dodanie pola do gry do okna
        setVisible(true);                                       //wlaczenie widocznosci okna
    }

    private class Start implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            start.setEnabled(false);                            //ustawnienie przycisku start na wylaczony
            Video.timer.start();                                //uruchomienie timera
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        new Snake();                                            //utworzenie obiektu snake
    }



}
