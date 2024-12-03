package domain.board;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BoardDay extends Board {
    private Timer skySunTimer;

    public BoardDay(int sunPoints) {
        super(720,1170 ,sunPoints );
    }

    // Inicia la generación automática de soles del cielo
    public void startSkySunGeneration() {
        int interval = 15000; // Cada 15 segundos
        skySunTimer = new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generateSkySun();
            }
        });
        skySunTimer.start();
        System.out.println("Generación de soles del cielo iniciada.");
    }

    // Detiene la generación automática de soles del cielo
    public void stopSkySunGeneration() {
        if (skySunTimer != null) {
            skySunTimer.stop();
            System.out.println("Generación de soles del cielo detenida.");
        }
    }

    // Genera soles automáticamente del cielo
    private void generateSkySun() {
        int sunAmount = 25; // Cantidad de soles generados
        addSun(sunAmount);
        System.out.println("Soles del cielo generados: " + sunAmount + ". Soles actuales: " + getSunPoints());
    }
}

