package diseño; 

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

public class PanelDegradado extends JPanel {

    private Color colorInicio = new Color(40, 43, 48);   // Gris oscuro 
    private Color colorFin = new Color(15, 15, 18);      // Casi negro

    public PanelDegradado() {
        setOpaque(false); // Permite que el degradado se vea detrás
    }

    // Aquí definimos los colores si queremos cambiarlos desde fuera
    public void setColores(Color inicio, Color fin) {
        this.colorInicio = inicio;
        this.colorFin = fin;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        
        // Activamos la sincronización de suavizado para que el degradado se vea fluido
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        int ancho = getWidth();
        int alto = getHeight();

        // Creamos el degradado: (0,0) es arriba-izquierda, (0, alto) es abajo-izquierda (Degradado Vertical)
        GradientPaint gp = new GradientPaint(0, 0, colorInicio, 0, alto, colorFin);
        
        // Si quisieras un degradado Diagonal, usa esto:
        // GradientPaint gp = new GradientPaint(0, 0, colorInicio, ancho, alto, colorFin);

        g2d.setPaint(gp);
        g2d.fillRect(0, 0, ancho, alto);
    }
}