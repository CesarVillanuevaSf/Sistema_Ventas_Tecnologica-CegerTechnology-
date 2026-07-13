/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package infraestructure;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.net.URL;
import domain.Empleados;
/**
 *
 * @author cesarvillanueva
 */

public class Menu_Principal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = 
            java.util.logging.Logger.getLogger(Menu_Principal.class.getName());

    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblHora;
    private javax.swing.JLabel lblCopyright;
    private Empleados empleadoActual;
    /**
     * Creates new form Menu_Principal
     * @param empleado
     */
    public Menu_Principal(Empleados empleado) {
        this.empleadoActual = empleado;
        initComponents();
    
        getContentPane().setPreferredSize(new java.awt.Dimension(1000, 700));
        panelPrincipal.setPreferredSize(new java.awt.Dimension(1000, 700));
        
        this.getContentPane().setBackground(new java.awt.Color(242, 242, 242));

        configurarCabecera(empleado);
        configurarFooter();
    
 // ➔ AJUSTE DE ICONOS AUTOMÁTICO POR CÓDIGO
    colocarIconoBoton(btnProductos, "/imagenes/cart_icon.png");
    colocarIconoBoton(btnCategorias, "/imagenes/cate_icon.png");
    colocarIconoBoton(btnProveedores, "/imagenes/provee_icon.png");
    colocarIconoBoton(btnInventario, "/imagenes/inven_icon.png");
    colocarIconoBoton(btnVentas, "/imagenes/vent_icon.png");
    colocarIconoBoton(btnCompras, "/imagenes/comp_icon.png");
    colocarIconoBoton(btnEmpleados, "/imagenes/emple_icon.png");
    colocarIconoBoton(btnReportes, "/imagenes/repor_icon.png");
    colocarIconoBoton(btnConfiguracion, "/imagenes/config_icon.png");
    colocarIconoBoton(btnInformacion, "/imagenes/vent_icon.png");   
    
    //Metodo para esconder opciones que no sean ADMINISTRADOR
    if (!empleado.getRol().equals("ADMINISTRADOR")) {
    btnEmpleados.setVisible(false);
    btnConfiguracion.setVisible(false); // opcional, si también quieres ocultarlo
}
    this.setSize(1000, 720);
    this.setResizable(false);
    this.setLocationRelativeTo(null);
}

    private Menu_Principal() {
       // Constructor vacío requerido, no usar directamente
    }
    

    private void ajustarLogoEmpresa(javax.swing.JLabel label, String ruta) {
        java.net.URL url = getClass().getResource(ruta);
    if (url != null) {
        javax.swing.ImageIcon icon = new javax.swing.ImageIcon(url);
        java.awt.Image img = icon.getImage().getScaledInstance(
            200, 90, java.awt.Image.SCALE_SMOOTH); // ← tamaño fijo, no depende del label
        label.setIcon(new javax.swing.ImageIcon(img));
        label.setText("");
    } else {
        System.err.println("No se encontró el logo en: " + ruta);
    }
}
    
    private void configurarCabecera(Empleados empleado) {
    // Limpiamos lo que puso el Form Editor y rediseñamos la cabecera
    panelCabecera.removeAll();
    panelCabecera.setBackground(new java.awt.Color(114, 145, 226));
    panelCabecera.setPreferredSize(new java.awt.Dimension(1000, 120));
    panelCabecera.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 100));
    panelCabecera.setLayout(new java.awt.BorderLayout(15, 0));

    // Logo izquierda
    BannerEmpresa.setText("");
    BannerEmpresa.setPreferredSize(new java.awt.Dimension(220, 110));
    ajustarLogoEmpresa(BannerEmpresa, "/imagenes/Logo-Empresa.png");
    panelCabecera.add(BannerEmpresa, java.awt.BorderLayout.WEST);

    // Nombre y rol del empleado logueado (derecha)
    lblSaludoAdmin.setText("Hola, " + empleado.getNombre() + " " + empleado.getApellido());
    lblSaludoAdmin.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 14));
    lblSaludoAdmin.setForeground(java.awt.Color.WHITE);
    lblSaludoAdmin.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    lblRol.setText(empleado.getRol());
    lblRol.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 13));
    lblRol.setForeground(java.awt.Color.WHITE);
    lblRol.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

    javax.swing.JPanel panelSaludo = new javax.swing.JPanel(
        new java.awt.GridLayout(2, 1, 0, 4));
    panelSaludo.setOpaque(false);
    panelSaludo.add(lblSaludoAdmin);
    panelSaludo.add(lblRol);

    javax.swing.JPanel wrapper = new javax.swing.JPanel(
        new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 20, 38));
    wrapper.setOpaque(false);
    wrapper.add(panelSaludo);
    panelCabecera.add(wrapper, java.awt.BorderLayout.EAST);

    panelCabecera.revalidate();
    panelCabecera.repaint();
}

    private void configurarFooter() {
    panelFooter.removeAll();
    panelFooter.setBackground(new java.awt.Color(105, 135, 222));
    panelFooter.setPreferredSize(new java.awt.Dimension(1000, 50));
    panelFooter.setLayout(new java.awt.BorderLayout());
    
   java.awt.Container padre = panelFooter.getParent();
     if (padre != null) {
        java.awt.GridBagLayout gbl = (java.awt.GridBagLayout) padre.getLayout();
        java.awt.GridBagConstraints gbc = gbl.getConstraints(panelFooter);
        gbc.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gbc.fill = java.awt.GridBagConstraints.BOTH; // ← BOTH en vez de HORIZONTAL
        gbc.weightx = 1.0;
        gbc.weighty = 0;
        gbl.setConstraints(panelFooter, gbc);
    }

    // LA FECHA Y HORA
    javax.swing.JPanel panelFechaHora = new javax.swing.JPanel(
        new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 12));
    panelFechaHora.setOpaque(false);

    javax.swing.JLabel lblFecha = new javax.swing.JLabel();
    lblFecha.setForeground(java.awt.Color.WHITE);
    lblFecha.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13));
    lblFecha.setText("  📅  " + java.time.LocalDate.now().format(
        java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));

    javax.swing.JLabel lblHora = new javax.swing.JLabel("  🕐  --:--:--");
    lblHora.setForeground(java.awt.Color.WHITE);
    lblHora.setFont(new java.awt.Font("SansSerif", java.awt.Font.BOLD, 13));

    // Reloj en tiempo real
    new javax.swing.Timer(1000, e ->
        lblHora.setText("  🕐  " + java.time.LocalTime.now().format(
            java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")))
    ).start();

    panelFechaHora.add(lblFecha);
    panelFechaHora.add(lblHora);
    panelFooter.add(panelFechaHora, java.awt.BorderLayout.WEST);

    // Derecha: copyright
    javax.swing.JLabel lblCopyright = new javax.swing.JLabel(
        "© 2026 Ceger Technology. Todos los derechos reservados.  ");
    lblCopyright.setForeground(java.awt.Color.WHITE);
    lblCopyright.setFont(new java.awt.Font("SansSerif", java.awt.Font.PLAIN, 12));
    panelFooter.add(lblCopyright, java.awt.BorderLayout.EAST);

    panelFooter.revalidate();
    panelFooter.repaint();
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new javax.swing.JPanel();
        panelCabecera = new javax.swing.JPanel();
        BannerEmpresa = new javax.swing.JLabel();
        lblSaludoAdmin = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        panelContenido = new javax.swing.JPanel();
        panelCuadricula = new javax.swing.JPanel();
        btnProductos = new javax.swing.JButton();
        btnCategorias = new javax.swing.JButton();
        btnProveedores = new javax.swing.JButton();
        btnInventario = new javax.swing.JButton();
        btnVentas = new javax.swing.JButton();
        btnCompras = new javax.swing.JButton();
        btnEmpleados = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnConfiguracion = new javax.swing.JButton();
        btnInformacion = new javax.swing.JButton();
        panelFooter = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 1000, 70));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        panelPrincipal.setPreferredSize(new java.awt.Dimension(1000, 700));
        panelPrincipal.setLayout(new java.awt.BorderLayout());

        panelCabecera.setBackground(new java.awt.Color(114, 145, 226));
        panelCabecera.setPreferredSize(new java.awt.Dimension(1000, 100));
        panelCabecera.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        BannerEmpresa.setText("jLabel1");
        panelCabecera.add(BannerEmpresa, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 50, -1, -1));

        lblSaludoAdmin.setFont(new java.awt.Font("Kefa III", 1, 13)); // NOI18N
        lblSaludoAdmin.setText("HOLA, CESAR VILLANUEVA ");
        panelCabecera.add(lblSaludoAdmin, new org.netbeans.lib.awtextra.AbsoluteConstraints(1630, 70, 230, 20));

        lblRol.setFont(new java.awt.Font("Kohinoor Gujarati", 1, 13)); // NOI18N
        lblRol.setText("Administrador");
        panelCabecera.add(lblRol, new org.netbeans.lib.awtextra.AbsoluteConstraints(1670, 100, -1, -1));

        panelPrincipal.add(panelCabecera, java.awt.BorderLayout.PAGE_START);

        panelContenido.setOpaque(false);
        panelContenido.setLayout(new java.awt.GridBagLayout());

        panelCuadricula.setOpaque(false);
        panelCuadricula.setPreferredSize(new java.awt.Dimension(950, 350));
        panelCuadricula.setLayout(new java.awt.GridBagLayout());

        btnProductos.setText("Productos");
        btnProductos.setBorderPainted(false);
        btnProductos.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProductos.setMaximumSize(new java.awt.Dimension(165, 145));
        btnProductos.setMinimumSize(new java.awt.Dimension(165, 145));
        btnProductos.setPreferredSize(new java.awt.Dimension(165, 145));
        btnProductos.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProductosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnProductos, gridBagConstraints);

        btnCategorias.setText("Categorias");
        btnCategorias.setBorderPainted(false);
        btnCategorias.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCategorias.setMaximumSize(new java.awt.Dimension(165, 145));
        btnCategorias.setMinimumSize(new java.awt.Dimension(165, 145));
        btnCategorias.setPreferredSize(new java.awt.Dimension(165, 145));
        btnCategorias.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCategorias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCategoriasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnCategorias, gridBagConstraints);

        btnProveedores.setText("Proveedores");
        btnProveedores.setBorderPainted(false);
        btnProveedores.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnProveedores.setMaximumSize(new java.awt.Dimension(165, 145));
        btnProveedores.setMinimumSize(new java.awt.Dimension(165, 145));
        btnProveedores.setPreferredSize(new java.awt.Dimension(165, 145));
        btnProveedores.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnProveedores.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProveedoresActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnProveedores, gridBagConstraints);

        btnInventario.setText("Inventario");
        btnInventario.setBorderPainted(false);
        btnInventario.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInventario.setMaximumSize(new java.awt.Dimension(165, 145));
        btnInventario.setMinimumSize(new java.awt.Dimension(165, 145));
        btnInventario.setPreferredSize(new java.awt.Dimension(165, 145));
        btnInventario.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInventario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInventarioActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnInventario, gridBagConstraints);

        btnVentas.setText("Ventas");
        btnVentas.setBorderPainted(false);
        btnVentas.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnVentas.setMaximumSize(new java.awt.Dimension(165, 145));
        btnVentas.setMinimumSize(new java.awt.Dimension(165, 145));
        btnVentas.setPreferredSize(new java.awt.Dimension(165, 145));
        btnVentas.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVentasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnVentas, gridBagConstraints);

        btnCompras.setText("Compras");
        btnCompras.setBorderPainted(false);
        btnCompras.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnCompras.setMaximumSize(new java.awt.Dimension(165, 145));
        btnCompras.setMinimumSize(new java.awt.Dimension(165, 145));
        btnCompras.setPreferredSize(new java.awt.Dimension(165, 145));
        btnCompras.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnCompras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnComprasActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnCompras, gridBagConstraints);

        btnEmpleados.setText("Empleados");
        btnEmpleados.setBorderPainted(false);
        btnEmpleados.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnEmpleados.setMaximumSize(new java.awt.Dimension(165, 145));
        btnEmpleados.setMinimumSize(new java.awt.Dimension(165, 145));
        btnEmpleados.setPreferredSize(new java.awt.Dimension(165, 145));
        btnEmpleados.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnEmpleados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEmpleadosActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnEmpleados, gridBagConstraints);

        btnReportes.setText("Reportes");
        btnReportes.setBorderPainted(false);
        btnReportes.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnReportes.setMaximumSize(new java.awt.Dimension(165, 145));
        btnReportes.setMinimumSize(new java.awt.Dimension(165, 145));
        btnReportes.setPreferredSize(new java.awt.Dimension(165, 145));
        btnReportes.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnReportes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReportesActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnReportes, gridBagConstraints);

        btnConfiguracion.setText("Clientes");
        btnConfiguracion.setBorderPainted(false);
        btnConfiguracion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnConfiguracion.setMaximumSize(new java.awt.Dimension(165, 145));
        btnConfiguracion.setMinimumSize(new java.awt.Dimension(165, 145));
        btnConfiguracion.setPreferredSize(new java.awt.Dimension(165, 145));
        btnConfiguracion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnConfiguracion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfiguracionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnConfiguracion, gridBagConstraints);

        btnInformacion.setText("Informacion");
        btnInformacion.setBorderPainted(false);
        btnInformacion.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnInformacion.setMaximumSize(new java.awt.Dimension(165, 145));
        btnInformacion.setMinimumSize(new java.awt.Dimension(165, 145));
        btnInformacion.setPreferredSize(new java.awt.Dimension(165, 145));
        btnInformacion.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        btnInformacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInformacionActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(12, 12, 12, 12);
        panelCuadricula.add(btnInformacion, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panelContenido.add(panelCuadricula, gridBagConstraints);

        panelFooter.setBackground(new java.awt.Color(105, 135, 222));
        panelFooter.setPreferredSize(new java.awt.Dimension(1000, 50));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        panelContenido.add(panelFooter, gridBagConstraints);

        panelPrincipal.add(panelContenido, java.awt.BorderLayout.CENTER);

        getContentPane().add(panelPrincipal, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1000, 700));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        new Ventana_Producto(empleadoActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnCategoriasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCategoriasActionPerformed
        new Ventana_Categoria(empleadoActual).setVisible(true);
            this.dispose();
    }//GEN-LAST:event_btnCategoriasActionPerformed

    private void btnProveedoresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProveedoresActionPerformed
        new Ventana_Proveedores(empleadoActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnProveedoresActionPerformed

    private void btnInventarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInventarioActionPerformed
        new Ventana_Inventario(empleadoActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInventarioActionPerformed

    private void btnVentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVentasActionPerformed
        new Ventana_Ventas(empleadoActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVentasActionPerformed

    private void btnComprasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnComprasActionPerformed
        new MantenedorCompras(empleadoActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnComprasActionPerformed

    private void btnEmpleadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEmpleadosActionPerformed
        try {
        MantenedorEmpleados ventana = new MantenedorEmpleados(empleadoActual);
        ventana.setVisible(true);
        this.dispose();
    } catch (Exception e) {
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(this,
            "Error al abrir Empleados: " + e.getMessage() + "\n" + e.getClass().getName(),
            "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_btnEmpleadosActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
       new MantenedorReportes(empleadoActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnInformacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInformacionActionPerformed
       new MantenedorInformacion(empleadoActual).setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInformacionActionPerformed

    private void btnConfiguracionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfiguracionActionPerformed
       new Ventana_Clientes(empleadoActual).setVisible(true);
       this.dispose();
    }//GEN-LAST:event_btnConfiguracionActionPerformed

    
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Menu_Principal().setVisible(true));
    }
    
    //Metodo para arreglar el tamaño predeterminado de la imagen y se pueda ver mejor
    private void colocarIconoBoton(javax.swing.JButton boton, String rutaIcono) {
    try {
        java.net.URL urlIcono = getClass().getResource(rutaIcono);
        if (urlIcono != null) {
            javax.swing.ImageIcon imgIcono = new javax.swing.ImageIcon(urlIcono);
            // Escalamos el ícono a 55x55 píxeles para que se acomode de forma ordenada
            java.awt.Image imgEscalada = imgIcono.getImage().getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
            boton.setIcon(new javax.swing.ImageIcon(imgEscalada));
        } else {
            System.err.println("No se encontró el ícono en: " + rutaIcono);
        }
    } catch (Exception e) {
        System.err.println("Error al cargar ícono en botón: " + e.getMessage());
    }
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel BannerEmpresa;
    private javax.swing.JButton btnCategorias;
    private javax.swing.JButton btnCompras;
    private javax.swing.JButton btnConfiguracion;
    private javax.swing.JButton btnEmpleados;
    private javax.swing.JButton btnInformacion;
    private javax.swing.JButton btnInventario;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnProveedores;
    private javax.swing.JButton btnReportes;
    private javax.swing.JButton btnVentas;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblSaludoAdmin;
    private javax.swing.JPanel panelCabecera;
    private javax.swing.JPanel panelContenido;
    private javax.swing.JPanel panelCuadricula;
    private javax.swing.JPanel panelFooter;
    private javax.swing.JPanel panelPrincipal;
    // End of variables declaration//GEN-END:variables

    private void iniciarReloj() {
         // Constructor vacío requerido, no usar directamente
    }
}
