
package infraestructure;

import controlador.ProductoService;
import controlador.ProveedorService;
import controlador.CompraService;
import domain.Compras;
import domain.Detalle_Compras;
import domain.Empleados;
import domain.Producto;
import domain.Proveedores;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MantenedorCompras extends javax.swing.JFrame {

    private final Empleados empleado;
    private final CompraService compraService;
    private final ProveedorService proveedorService;
    private final ProductoService productoService;
    private DefaultTableModel modeloCompras;
    private DefaultTableModel modeloDetalle;
    private List<Detalle_Compras> detallesActuales = new ArrayList<>();

    public MantenedorCompras(Empleados empleado) {
        this.empleado = empleado;
        this.compraService = presentation.SistemaVentasApplication
                .getBean(CompraService.class);
        this.proveedorService = presentation.SistemaVentasApplication
                .getBean(ProveedorService.class);
        this.productoService = presentation.SistemaVentasApplication
                .getBean(ProductoService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1100, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarTablaCompras();
        cargarTablaDetalle();
        cargarCombos();
    }

    private void configurarCabecera() {
        panelCabecera.removeAll();
        panelCabecera.setBackground(new Color(114, 145, 226));
        panelCabecera.setPreferredSize(new Dimension(1100, 100));
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

        JLabel lblTitulo = new JLabel("🛒  Compras");
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

    private void configurarFooter() {
        panelFooter.removeAll();
        panelFooter.setBackground(new Color(105, 135, 222));
        panelFooter.setPreferredSize(new Dimension(1100, 50));
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

    private void cargarTablaCompras() {
        modeloCompras = new DefaultTableModel(
                new String[]{"ID", "Proveedor", "Empleado",
                    "Total", "Fecha"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaCompras.setModel(modeloCompras);
        tablaCompras.setRowHeight(28);
        tablaCompras.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaCompras.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaCompras.setSelectionBackground(new Color(114, 145, 226));
        tablaCompras.setSelectionForeground(Color.WHITE);
        tablaCompras.getColumnModel().getColumn(0).setMinWidth(0);
        tablaCompras.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTablaCompras();

        // Al seleccionar compra → cargar detalles
        tablaCompras.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaCompras.getSelectedRow();
            if (fila >= 0) {
                Long id = (Long) modeloCompras.getValueAt(fila, 0);
                modeloDetalle.setRowCount(0);
                for (Detalle_Compras d : compraService.listarDetalles(id)) {
                    modeloDetalle.addRow(new Object[]{
                        d.getProducto() != null ? d.getProducto().getNombre() : "",
                        d.getCantidad(),
                        d.getPrecioUnitarioCompra(),
                        d.getCantidad() * d.getPrecioUnitarioCompra()
                    });
                }
            }
        });
    }

    private void recargarTablaCompras() {
        modeloCompras.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Compras c : compraService.listarCompras()) {
            modeloCompras.addRow(new Object[]{
                c.getIdCompra(),
                c.getProveedor() != null ? c.getProveedor().getNombre() : "",
                c.getEmpleado() != null ? c.getEmpleado().getNombre()
                        + " " + c.getEmpleado().getApellido() : "",
                String.format("S/ %.2f", c.getTotalCompra()),
                c.getFechaCompra() != null ? c.getFechaCompra().format(fmt) : ""
            });
        }
    }

    private void cargarTablaDetalle() {
        modeloDetalle = new DefaultTableModel(
                new String[]{"Producto", "Cantidad", "P. Unit.", "Subtotal"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaDetalle.setModel(modeloDetalle);
        tablaDetalle.setRowHeight(25);
        tablaDetalle.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaDetalle.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tablaDetalle.setSelectionBackground(new Color(114, 145, 226));
        tablaDetalle.setSelectionForeground(Color.WHITE);
    }

    private void cargarCombos() {
        // Proveedores
        cmbProveedor.removeAllItems();
        cmbProveedor.addItem(null);
        for (Proveedores p : proveedorService.listarProveedores())
            cmbProveedor.addItem(p);
        cmbProveedor.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> l,
                    Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                setText(v instanceof Proveedores
                        ? ((Proveedores) v).getNombre() : "Seleccione...");
                return this;
            }
        });

        // Productos
        cmbProducto.removeAllItems();
        cmbProducto.addItem(null);
        for (Producto p : productoService.listarProductos())
            cmbProducto.addItem(p);
        cmbProducto.setRenderer(new DefaultListCellRenderer() {
            @Override public Component getListCellRendererComponent(JList<?> l,
                    Object v, int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                setText(v instanceof Producto
                        ? ((Producto) v).getNombre() : "Seleccione...");
                return this;
            }
        });

        // Precio automático al seleccionar producto
        cmbProducto.addActionListener(e -> {
            Producto p = (Producto) cmbProducto.getSelectedItem();
            if (p != null) {
                txtPrecioUnitario.setText(String.valueOf(p.getPrecioCompra()));
                lblStock.setText("Stock actual: " + p.getStockActual());
            } else {
                txtPrecioUnitario.setText("");
                lblStock.setText("Stock actual: --");
            }
        });
    }

    private void agregarProductoDetalle() {
        Producto p = (Producto) cmbProducto.getSelectedItem();
        if (p == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            int cantidad = Integer.parseInt(txtCantidad.getText().trim());
            double precio = Double.parseDouble(txtPrecioUnitario.getText().trim());

            if (cantidad <= 0 || precio <= 0) {
                JOptionPane.showMessageDialog(this,
                        "Cantidad y precio deben ser mayores a cero.",
                        "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            Detalle_Compras detalle = new Detalle_Compras();
            detalle.setProducto(p);
            detalle.setCantidad(cantidad);
            detalle.setPrecioUnitarioCompra(precio);
            detallesActuales.add(detalle);

            modeloDetalle.addRow(new Object[]{
                p.getNombre(), cantidad, precio, cantidad * precio
            });

            actualizarTotal();
            txtCantidad.setText("");
            cmbProducto.setSelectedIndex(0);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Verifique cantidad y precio.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarTotal() {
        double total = detallesActuales.stream()
                .mapToDouble(d -> d.getCantidad() * d.getPrecioUnitarioCompra())
                .sum();
        lblTotal.setText(String.format("TOTAL: S/ %.2f", total));
    }

    private void registrarCompra() {
        try {
            Proveedores proveedor = (Proveedores) cmbProveedor.getSelectedItem();
            if (proveedor == null) {
                JOptionPane.showMessageDialog(this,
                        "Seleccione un proveedor.", "Aviso",
                        JOptionPane.WARNING_MESSAGE);
                return;
            }

            Compras compra = new Compras();
            compra.setProveedor(proveedor);
            compra.setEmpleado(empleado);

            compraService.registrarCompra(compra, detallesActuales);
            recargarTablaCompras();
            limpiarCompra();
            JOptionPane.showMessageDialog(this,
                    "Compra registrada correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCompra() {
        detallesActuales.clear();
        modeloDetalle.setRowCount(0);
        cmbProveedor.setSelectedIndex(0);
        cmbProducto.setSelectedIndex(0);
        txtCantidad.setText("");
        txtPrecioUnitario.setText("");
        lblStock.setText("Stock actual: --");
        lblTotal.setText("TOTAL: S/ 0.00");
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        JPanel panelContenido = new JPanel(new BorderLayout(10, 10));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ── PANEL IZQUIERDA: NUEVA COMPRA ────────
        JPanel panelNuevaCompra = new JPanel(new BorderLayout(0, 8));
        panelNuevaCompra.setPreferredSize(new Dimension(370, 0));
        panelNuevaCompra.setOpaque(false);

        // Formulario cabecera compra
        JPanel panelFormCompra = new JPanel(new GridBagLayout());
        panelFormCompra.setBackground(Color.WHITE);
        panelFormCompra.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Nueva Compra",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        cmbProveedor = new JComboBox<>();

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblProv = new JLabel("Proveedor:");
        lblProv.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelFormCompra.add(lblProv, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panelFormCompra.add(cmbProveedor, gbc);

        // Formulario agregar producto
        JPanel panelFormProducto = new JPanel(new GridBagLayout());
        panelFormProducto.setBackground(Color.WHITE);
        panelFormProducto.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Agregar Producto",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 13),
                new Color(114, 145, 226)));

        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.insets = new Insets(6, 8, 6, 8);
        gbc2.fill = GridBagConstraints.HORIZONTAL;

        cmbProducto       = new JComboBox<>();
        txtCantidad       = new JTextField(10);
        txtPrecioUnitario = new JTextField(10);
        txtPrecioUnitario.setEditable(false);
        txtPrecioUnitario.setBackground(new Color(230, 230, 230));
        lblStock = new JLabel("Stock actual: --");
        lblStock.setForeground(new Color(46, 139, 87));
        lblStock.setFont(new Font("SansSerif", Font.BOLD, 12));

        Object[][] camposProducto = {
            {"Producto:",    cmbProducto},
            {"Cantidad:",    txtCantidad},
            {"P. Compra:",   txtPrecioUnitario},
            {"",             lblStock}
        };
        for (int i = 0; i < camposProducto.length; i++) {
            gbc2.gridx = 0; gbc2.gridy = i; gbc2.weightx = 0;
            JLabel lbl = new JLabel((String) camposProducto[i][0]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 12));
            panelFormProducto.add(lbl, gbc2);
            gbc2.gridx = 1; gbc2.weightx = 1.0;
            panelFormProducto.add((Component) camposProducto[i][1], gbc2);
        }

        JButton btnAgregarDetalle = crearBoton("+ Agregar Producto",
                new Color(70, 130, 180));
        btnAgregarDetalle.addActionListener(e -> agregarProductoDetalle());
        gbc2.gridx = 0; gbc2.gridy = camposProducto.length;
        gbc2.gridwidth = 2;
        panelFormProducto.add(btnAgregarDetalle, gbc2);

        // Tabla detalle
        tablaDetalle = new JTable();
        JScrollPane scrollDetalle = new JScrollPane(tablaDetalle);
        scrollDetalle.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Detalle de Compra",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 13),
                new Color(114, 145, 226)));
        scrollDetalle.setPreferredSize(new Dimension(0, 150));

        // Total + botones
        JPanel panelSur = new JPanel(new BorderLayout(0, 5));
        panelSur.setOpaque(false);

        JPanel panelTotal = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 8));
        panelTotal.setBackground(new Color(240, 245, 255));
        lblTotal = new JLabel("TOTAL: S/ 0.00");
        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setForeground(new Color(46, 139, 87));
        panelTotal.add(lblTotal);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 5));
        panelBotones.setOpaque(false);
        JButton btnRegistrar = crearBoton("✔ Registrar Compra", new Color(46, 139, 87));
        JButton btnLimpiar   = crearBoton("↺ Limpiar", new Color(128, 128, 128));
        btnRegistrar.addActionListener(e -> registrarCompra());
        btnLimpiar.addActionListener(e   -> limpiarCompra());
        panelBotones.add(btnRegistrar);
        panelBotones.add(btnLimpiar);

        panelSur.add(panelTotal,   BorderLayout.CENTER);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        panelNuevaCompra.add(panelFormCompra,   BorderLayout.NORTH);
        panelNuevaCompra.add(panelFormProducto, BorderLayout.CENTER);
        panelNuevaCompra.add(scrollDetalle,     BorderLayout.SOUTH);

        // ── PANEL DERECHA: HISTORIAL ─────────────
        JPanel panelHistorial = new JPanel(new BorderLayout(0, 8));
        panelHistorial.setOpaque(false);

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBuscar.setOpaque(false);
        txtBuscar = new JTextField(20);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnBuscar = crearBoton("🔍 Buscar", new Color(114, 145, 226));
        btnBuscar.addActionListener(e -> {
            String txt = txtBuscar.getText().trim();
            modeloCompras.setRowCount(0);
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            List<Compras> lista = txt.isEmpty()
                    ? compraService.listarCompras()
                    : compraService.buscarPorProveedor(txt);
            for (Compras c : lista) {
                modeloCompras.addRow(new Object[]{
                    c.getIdCompra(),
                    c.getProveedor() != null ? c.getProveedor().getNombre() : "",
                    c.getEmpleado() != null ? c.getEmpleado().getNombre()
                            + " " + c.getEmpleado().getApellido() : "",
                    String.format("S/ %.2f", c.getTotalCompra()),
                    c.getFechaCompra() != null ? c.getFechaCompra().format(fmt) : ""
                });
            }
        });
        panelBuscar.add(new JLabel("Buscar proveedor: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        tablaCompras = new JTable();
        JScrollPane scrollCompras = new JScrollPane(tablaCompras);
        scrollCompras.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Historial de Compras",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        // Tabla detalle historial (parte inferior derecha)
        JTable tablaDetalleHistorial = new JTable();
        tablaDetalleHistorial.setModel(new DefaultTableModel(
                new String[]{"Producto", "Cantidad", "P. Unit.", "Subtotal"}, 0));
        JScrollPane scrollDetalleHist = new JScrollPane(tablaDetalleHistorial);
        scrollDetalleHist.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Detalle de Compra Seleccionada",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 13),
                new Color(114, 145, 226)));
        scrollDetalleHist.setPreferredSize(new Dimension(0, 160));

        panelHistorial.add(panelBuscar,        BorderLayout.NORTH);
        panelHistorial.add(scrollCompras,       BorderLayout.CENTER);
        panelHistorial.add(scrollDetalleHist,   BorderLayout.SOUTH);

        // ── ENSAMBLE FINAL ───────────────────────
        JPanel panelIzquierda = new JPanel(new BorderLayout(0, 8));
        panelIzquierda.setOpaque(false);
        panelIzquierda.add(panelNuevaCompra, BorderLayout.CENTER);
        panelIzquierda.add(panelSur,         BorderLayout.SOUTH);

        panelContenido.add(panelIzquierda, BorderLayout.WEST);
        panelContenido.add(panelHistorial,  BorderLayout.CENTER);

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
    private JComboBox<Proveedores> cmbProveedor;
    private JComboBox<Producto> cmbProducto;
    private JTextField txtCantidad, txtPrecioUnitario, txtBuscar;
    private JLabel lblStock, lblTotal;
    private JTable tablaCompras, tablaDetalle;
}
