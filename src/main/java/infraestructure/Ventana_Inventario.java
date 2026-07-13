package infraestructure;

import domain.Empleados;
import domain.Inventario;
import domain.Producto;
import controlador.InventarioService;
import controlador.ProductoService;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ventana_Inventario extends javax.swing.JFrame {

    private final Empleados empleado;
    private final InventarioService inventarioService;
    private final ProductoService productoService;
    private DefaultTableModel modeloTabla;

    public Ventana_Inventario(Empleados empleado) {
        this.empleado = empleado;
        this.inventarioService = presentation.SistemaVentasApplication
                .getBean(InventarioService.class);
        this.productoService = presentation.SistemaVentasApplication
                .getBean(ProductoService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1000, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarTabla();
        cargarComboProductos();
    }

    // ══════════════════════════════════════════════
    //  CABECERA
    // ══════════════════════════════════════════════
    private void configurarCabecera() {
        panelCabecera.removeAll();
        panelCabecera.setBackground(new Color(114, 145, 226));
        panelCabecera.setPreferredSize(new Dimension(1000, 100));
        panelCabecera.setLayout(new BorderLayout(15, 0));

        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(220, 90));
        java.net.URL urlLogo = getClass().getResource("/imagenes/Logo-Empresa.png");
        if (urlLogo != null) {
            Image img = new ImageIcon(urlLogo).getImage()
                    .getScaledInstance(200, 85, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }
        panelCabecera.add(lblLogo, BorderLayout.WEST);

        JPanel panelCentro = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 30));
        panelCentro.setOpaque(false);

        JButton btnRegresar = new JButton("← Regresar");
        btnRegresar.setBackground(new Color(255, 255, 255, 60));
        btnRegresar.setForeground(Color.WHITE);
        btnRegresar.setBorderPainted(false);
        btnRegresar.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnRegresar.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRegresar.addActionListener(e -> {
            new Menu_Principal(empleado).setVisible(true);
            this.dispose();
        });

        JLabel lblTitulo = new JLabel("🏭  Inventario");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        panelCentro.add(btnRegresar);
        panelCentro.add(lblTitulo);
        panelCabecera.add(panelCentro, BorderLayout.CENTER);

        JPanel panelSaludo = new JPanel(new GridLayout(2, 1, 0, 4));
        panelSaludo.setOpaque(false);

        JLabel lblHola = new JLabel("Hola, " + empleado.getNombre()
                + " " + empleado.getApellido());
        lblHola.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHola.setForeground(Color.WHITE);
        lblHola.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel lblRol = new JLabel(empleado.getRol());
        lblRol.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblRol.setForeground(Color.WHITE);
        lblRol.setHorizontalAlignment(SwingConstants.RIGHT);

        panelSaludo.add(lblHola);
        panelSaludo.add(lblRol);

        JPanel wrapperSaludo = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 32));
        wrapperSaludo.setOpaque(false);
        wrapperSaludo.add(panelSaludo);
        panelCabecera.add(wrapperSaludo, BorderLayout.EAST);

        panelCabecera.revalidate();
        panelCabecera.repaint();
    }

    // ══════════════════════════════════════════════
    //  FOOTER
    // ══════════════════════════════════════════════
    private void configurarFooter() {
        panelFooter.removeAll();
        panelFooter.setBackground(new Color(105, 135, 222));
        panelFooter.setPreferredSize(new Dimension(1000, 50));
        panelFooter.setLayout(new BorderLayout());

        JPanel panelFechaHora = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 12));
        panelFechaHora.setOpaque(false);

        JLabel lblFecha = new JLabel("  📅  " + java.time.LocalDate.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel lblHora = new JLabel("  🕐  --:--:--");
        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("SansSerif", Font.BOLD, 13));

        new javax.swing.Timer(1000, e -> lblHora.setText("  🕐  " +
                java.time.LocalTime.now().format(
                        DateTimeFormatter.ofPattern("HH:mm:ss")))).start();

        panelFechaHora.add(lblFecha);
        panelFechaHora.add(lblHora);
        panelFooter.add(panelFechaHora, BorderLayout.WEST);

        JLabel lblCopyright = new JLabel(
                "© 2025 Ceger Technology. Todos los derechos reservados.  ");
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panelFooter.add(lblCopyright, BorderLayout.EAST);

        panelFooter.revalidate();
        panelFooter.repaint();
    }

    // ══════════════════════════════════════════════
    //  TABLA
    // ══════════════════════════════════════════════
    private void cargarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Producto", "Tipo Movimiento",
                    "Cantidad", "Fecha", "Observación"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaInventario.setModel(modeloTabla);
        tablaInventario.setRowHeight(28);
        tablaInventario.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaInventario.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaInventario.setSelectionBackground(new Color(114, 145, 226));
        tablaInventario.setSelectionForeground(Color.WHITE);

        tablaInventario.getColumnModel().getColumn(0).setMinWidth(0);
        tablaInventario.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTabla();
    }

    private void recargarTabla() {
        modeloTabla.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Inventario inv : inventarioService.listarMovimientos()) {
            modeloTabla.addRow(new Object[]{
                inv.getIdInventario(),
                inv.getProducto() != null ? inv.getProducto().getNombre() : "",
                inv.getTipoMovimiento(),
                inv.getCantidad(),
                inv.getFechaMovimiento() != null
                        ? inv.getFechaMovimiento().format(fmt) : "",
                inv.getObservacion()
            });
        }
    }

    private void cargarComboProductos() {
        cmbProducto.removeAllItems();
        cmbProducto.addItem(null);
        for (Producto p : productoService.listarProductos()) {
            cmbProducto.addItem(p);
        }
        cmbProducto.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value instanceof Producto
                        ? ((Producto) value).getNombre() : "Seleccione...");
                return this;
            }
        });
    }

    private void limpiarFormulario() {
        cmbProducto.setSelectedIndex(0);
        cmbTipoMovimiento.setSelectedIndex(0);
        txtCantidad.setText("");
        txtObservacion.setText("");
        tablaInventario.clearSelection();
    }

    // ══════════════════════════════════════════════
    //  ACCIONES
    // ══════════════════════════════════════════════
    private void registrarMovimiento() {
        try {
            Producto productoSeleccionado = (Producto) cmbProducto.getSelectedItem();
            String tipo = (String) cmbTipoMovimiento.getSelectedItem();

            if (productoSeleccionado == null || tipo.equals("Seleccione...")) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione producto y tipo de movimiento.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Inventario inv = new Inventario();
            inv.setProducto(productoSeleccionado);
            inv.setTipoMovimiento(tipo);
            inv.setCantidad(Integer.parseInt(txtCantidad.getText().trim()));
            inv.setObservacion(txtObservacion.getText().trim());

            inventarioService.registrarMovimiento(inv);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Movimiento registrado correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "La cantidad debe ser un número válido.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarMovimiento() {
        String texto = txtBuscar.getText().trim();
        modeloTabla.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        List<Inventario> lista = texto.isEmpty()
                ? inventarioService.listarMovimientos()
                : inventarioService.buscarPorProducto(texto);
        for (Inventario inv : lista) {
            modeloTabla.addRow(new Object[]{
                inv.getIdInventario(),
                inv.getProducto() != null ? inv.getProducto().getNombre() : "",
                inv.getTipoMovimiento(),
                inv.getCantidad(),
                inv.getFechaMovimiento() != null
                        ? inv.getFechaMovimiento().format(fmt) : "",
                inv.getObservacion()
            });
        }
    }

    // ══════════════════════════════════════════════
    //  INIT COMPONENTS
    // ══════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        JPanel panelContenido = new JPanel(new BorderLayout(15, 0));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ── FORMULARIO IZQUIERDA ─────────────────
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Registrar Movimiento",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));
        panelFormulario.setPreferredSize(new Dimension(320, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cmbProducto = new JComboBox<>();
        cmbTipoMovimiento = new JComboBox<>(
                new String[]{"Seleccione...", "ENTRADA", "SALIDA", "AJUSTE"});
        txtCantidad    = new JTextField(15);
        txtObservacion = new JTextField(15);

        Object[][] campos = {
            {"Producto:",         cmbProducto},
            {"Tipo Movimiento:",  cmbTipoMovimiento},
            {"Cantidad:",         txtCantidad},
            {"Observación:",      txtObservacion}
        };

        for (int i = 0; i < campos.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lbl = new JLabel((String) campos[i][0]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
            panelFormulario.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            panelFormulario.add((Component) campos[i][1], gbc);
        }

        // Panel de stock actual (informativo)
        JPanel panelStock = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        panelStock.setOpaque(false);
        lblStockActual = new JLabel("Stock actual: --");
        lblStockActual.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblStockActual.setForeground(new Color(46, 139, 87));
        panelStock.add(lblStockActual);

        // Actualiza stock al cambiar producto
        cmbProducto.addActionListener(e -> {
            Producto p = (Producto) cmbProducto.getSelectedItem();
            lblStockActual.setText(p != null
                    ? "Stock actual: " + p.getStockActual() : "Stock actual: --");
        });

        gbc.gridx = 0; gbc.gridy = campos.length;
        gbc.gridwidth = 2;
        panelFormulario.add(panelStock, gbc);

        // Botón registrar
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panelBotones.setOpaque(false);

        JButton btnRegistrar = crearBoton("+ Registrar Movimiento", new Color(46, 139, 87));
        JButton btnLimpiar   = crearBoton("↺ Limpiar", new Color(128, 128, 128));

        btnRegistrar.addActionListener(e -> registrarMovimiento());
        btnLimpiar.addActionListener(e   -> limpiarFormulario());

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = campos.length + 1;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        // ── TABLA DERECHA ────────────────────────
        JPanel panelTabla = new JPanel(new BorderLayout(0, 8));
        panelTabla.setOpaque(false);

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBuscar.setOpaque(false);
        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnBuscar = crearBoton("🔍 Buscar", new Color(114, 145, 226));
        btnBuscar.addActionListener(e -> buscarMovimiento());
        txtBuscar.addActionListener(e -> buscarMovimiento());
        panelBuscar.add(new JLabel("Buscar producto: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        tablaInventario = new JTable();
        JScrollPane scroll = new JScrollPane(tablaInventario);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Historial de Movimientos",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        panelTabla.add(panelBuscar, BorderLayout.NORTH);
        panelTabla.add(scroll, BorderLayout.CENTER);

        panelContenido.add(panelFormulario, BorderLayout.WEST);
        panelContenido.add(panelTabla, BorderLayout.CENTER);

        panelPrincipal.add(panelCabecera,  BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter,    BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        pack();
    }

    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    // ══════════════════════════════════════════════
    //  VARIABLES
    // ══════════════════════════════════════════════
    private JPanel panelPrincipal, panelCabecera, panelFooter;
    private JComboBox<Producto> cmbProducto;
    private JComboBox<String> cmbTipoMovimiento;
    private JTextField txtCantidad, txtObservacion, txtBuscar;
    private JLabel lblStockActual;
    private JTable tablaInventario;
}