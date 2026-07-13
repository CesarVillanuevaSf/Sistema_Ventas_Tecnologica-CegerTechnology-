package infraestructure;

import domain.Empleados;
import domain.Ventas;
import controlador.EmpleadoService;
import controlador.VentaService;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MantenedorReportes extends javax.swing.JFrame {

    private final Empleados empleadoActual;
    private final VentaService ventaService;
    private final EmpleadoService empleadoService;
    private DefaultTableModel modeloTabla;

    public MantenedorReportes(Empleados empleadoActual) {
        this.empleadoActual = empleadoActual;
        this.ventaService   = presentation.SistemaVentasApplication
                .getBean(VentaService.class);
        this.empleadoService = presentation.SistemaVentasApplication
                .getBean(EmpleadoService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1000, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarComboEmpleados();
        cargarTabla();
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
            new Menu_Principal(empleadoActual).setVisible(true);
            this.dispose();
        });

        JLabel lblTitulo = new JLabel("📊  Reportes de Empleados");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        panelCentro.add(btnRegresar);
        panelCentro.add(lblTitulo);
        panelCabecera.add(panelCentro, BorderLayout.CENTER);

        JPanel panelSaludo = new JPanel(new GridLayout(2, 1, 0, 4));
        panelSaludo.setOpaque(false);

        JLabel lblHola = new JLabel("Hola, " + empleadoActual.getNombre()
                + " " + empleadoActual.getApellido());
        lblHola.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblHola.setForeground(Color.WHITE);
        lblHola.setHorizontalAlignment(SwingConstants.RIGHT);

        JLabel lblRol = new JLabel(empleadoActual.getRol());
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
                .format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        lblFecha.setForeground(Color.WHITE);
        lblFecha.setFont(new Font("SansSerif", Font.BOLD, 13));

        JLabel lblHora = new JLabel("  🕐  --:--:--");
        lblHora.setForeground(Color.WHITE);
        lblHora.setFont(new Font("SansSerif", Font.BOLD, 13));

        new javax.swing.Timer(1000, e -> lblHora.setText("  🕐  " +
                java.time.LocalTime.now().format(
                        java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss")))).start();

        panelFechaHora.add(lblFecha);
        panelFechaHora.add(lblHora);
        panelFooter.add(panelFechaHora, BorderLayout.WEST);

        JLabel lblCopyright = new JLabel(
                "© 2026 Ceger Technology. Todos los derechos reservados.  ");
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panelFooter.add(lblCopyright, BorderLayout.EAST);

        panelFooter.revalidate();
        panelFooter.repaint();
    }

    // ══════════════════════════════════════════════
    //  COMBO Y TABLA
    // ══════════════════════════════════════════════
    private void cargarComboEmpleados() {
        cmbEmpleado.removeAllItems();
        cmbEmpleado.addItem(null);
        for (Empleados emp : empleadoService.listarEmpleados())
            cmbEmpleado.addItem(emp);
        cmbEmpleado.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> l, Object v,
                    int i, boolean s, boolean f) {
                super.getListCellRendererComponent(l, v, i, s, f);
                setText(v instanceof Empleados
                        ? ((Empleados) v).getNombre() + " "
                        + ((Empleados) v).getApellido()
                        + " (" + ((Empleados) v).getRol() + ")"
                        : "Seleccione empleado...");
                return this;
            }
        });
    }

    private void cargarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"N°", "Comprobante", "N° Comprobante",
                    "Cliente", "Subtotal", "IGV", "Total", "Fecha"}, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return false; }
        };
        tablaReporte.setModel(modeloTabla);
        tablaReporte.setRowHeight(28);
        tablaReporte.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaReporte.setFont(new Font("SansSerif", Font.PLAIN, 12));
        tablaReporte.setSelectionBackground(new Color(114, 145, 226));
        tablaReporte.setSelectionForeground(Color.WHITE);
    }

    private void generarReporte() {
        Empleados emp = (Empleados) cmbEmpleado.getSelectedItem();
        String periodo = (String) cmbPeriodo.getSelectedItem();

        if (emp == null || periodo.equals("Seleccione...")) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione empleado y período.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        List<Ventas> ventas = periodo.equals("SEMANAL")
                ? ventaService.reporteEmpleadoSemanal(emp.getIdEmpleado())
                : ventaService.reporteEmpleadoMensual(emp.getIdEmpleado());

        modeloTabla.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        int n = 1;
        for (Ventas v : ventas) {
            modeloTabla.addRow(new Object[]{
                n++,
                v.getComprobantePago(),
                v.getNumeroComprobante(),
                v.getCliente() != null
                        ? v.getCliente().getNombre() + " " + v.getCliente().getApellido()
                        : "",
                String.format("S/ %.2f", v.getSubtotal()),
                String.format("S/ %.2f", v.getIgv()),
                String.format("S/ %.2f", v.getTotal()),
                v.getFechaHora() != null ? v.getFechaHora().format(fmt) : ""
            });
        }

        // Actualizar resumen
        double total = ventaService.calcularTotalVentas(ventas);
        lblTotalVentas.setText(String.format("Total ventas: S/ %.2f", total));
        lblCantidadVentas.setText("Cantidad de ventas: " + ventas.size());
        lblEmpleadoReporte.setText("Empleado: " + emp.getNombre()
                + " " + emp.getApellido() + " | Período: " + periodo);
    }

    // ══════════════════════════════════════════════
    //  INIT COMPONENTS
    // ══════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        JPanel panelContenido = new JPanel(new BorderLayout(0, 10));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ── PANEL FILTROS ARRIBA ─────────────────
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        panelFiltros.setBackground(Color.WHITE);
        panelFiltros.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Filtros del Reporte",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 13),
                new Color(114, 145, 226)));

        JLabel lblEmp = new JLabel("Empleado:");
        lblEmp.setFont(new Font("SansSerif", Font.BOLD, 13));

        cmbEmpleado = new JComboBox<>();
        cmbEmpleado.setPreferredSize(new Dimension(280, 30));

        JLabel lblPer = new JLabel("Período:");
        lblPer.setFont(new Font("SansSerif", Font.BOLD, 13));

        cmbPeriodo = new JComboBox<>(new String[]{"Seleccione...", "SEMANAL", "MENSUAL"});
        cmbPeriodo.setPreferredSize(new Dimension(130, 30));

        JButton btnGenerar = crearBoton("📊 Generar Reporte", new Color(114, 145, 226));
        JButton btnLimpiar = crearBoton("↺ Limpiar", new Color(128, 128, 128));

        btnGenerar.addActionListener(e -> generarReporte());
        btnLimpiar.addActionListener(e -> {
            modeloTabla.setRowCount(0);
            cmbEmpleado.setSelectedIndex(0);
            cmbPeriodo.setSelectedIndex(0);
            lblTotalVentas.setText("Total ventas: S/ 0.00");
            lblCantidadVentas.setText("Cantidad de ventas: 0");
            lblEmpleadoReporte.setText("Empleado: --");
        });

        panelFiltros.add(lblEmp);
        panelFiltros.add(cmbEmpleado);
        panelFiltros.add(lblPer);
        panelFiltros.add(cmbPeriodo);
        panelFiltros.add(btnGenerar);
        panelFiltros.add(btnLimpiar);

        // ── PANEL RESUMEN ────────────────────────
        JPanel panelResumen = new JPanel(new GridLayout(1, 3, 10, 0));
        panelResumen.setOpaque(false);

        lblEmpleadoReporte = crearLblResumen("Empleado: --", new Color(70, 130, 180));
        lblCantidadVentas  = crearLblResumen("Cantidad de ventas: 0", new Color(46, 139, 87));
        lblTotalVentas     = crearLblResumen("Total ventas: S/ 0.00", new Color(178, 34, 34));

        panelResumen.add(lblEmpleadoReporte);
        panelResumen.add(lblCantidadVentas);
        panelResumen.add(lblTotalVentas);

        // ── TABLA ────────────────────────────────
        tablaReporte = new JTable();
        JScrollPane scroll = new JScrollPane(tablaReporte);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Detalle de Ventas",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        // ── PANEL NORTE (filtros + resumen) ──────
        JPanel panelNorte = new JPanel(new BorderLayout(0, 8));
        panelNorte.setOpaque(false);
        panelNorte.add(panelFiltros,  BorderLayout.NORTH);
        panelNorte.add(panelResumen,  BorderLayout.SOUTH);

        panelContenido.add(panelNorte, BorderLayout.NORTH);
        panelContenido.add(scroll,     BorderLayout.CENTER);

        panelPrincipal.add(panelCabecera,  BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter,    BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        pack();
    }

    private JLabel crearLblResumen(String texto, Color color) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        lbl.setForeground(Color.WHITE);
        lbl.setOpaque(true);
        lbl.setBackground(color);
        lbl.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        return lbl;
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
    private JComboBox<Empleados> cmbEmpleado;
    private JComboBox<String> cmbPeriodo;
    private JLabel lblTotalVentas, lblCantidadVentas, lblEmpleadoReporte;
    private JTable tablaReporte;
}