package infraestructure;

import controlador.VentaService;
import controlador.CompraService;
import domain.Compras;
import domain.Ventas;
import domain.Empleados;
import domain.Producto;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class MantenedorInformacion extends javax.swing.JFrame {

    private final Empleados empleadoActual;
    private final VentaService ventaService;
    private final CompraService compraService;
    private int mesActual;
    private int anioActual;
    private JLabel lblMesAnio;
    private DefaultTableModel modeloProductos;
    private JLabel lblTotalVentas, lblCantidadVentas;
    private JLabel lblTotalCompras, lblCantidadCompras;
    private JPanel panelCabecera, panelFooter, panelPrincipal;

    public MantenedorInformacion(Empleados empleadoActual) {
        this.empleadoActual = empleadoActual;
        this.ventaService   = presentation.SistemaVentasApplication
                .getBean(VentaService.class);
        this.compraService  = presentation.SistemaVentasApplication
                .getBean(CompraService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1000, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();

        mesActual  = java.time.LocalDate.now().getMonthValue();
        anioActual = java.time.LocalDate.now().getYear();
        cargarDatos(mesActual, anioActual);
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

        JLabel lblTitulo = new JLabel("💡  Información General");
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
                "© 2026 Ceger Technology. Todos los derechos reservados.  ");
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panelFooter.add(lblCopyright, BorderLayout.EAST);

        panelFooter.revalidate();
        panelFooter.repaint();
    }

    // ══════════════════════════════════════════════
    //  CARGAR DATOS
    // ══════════════════════════════════════════════
    private void cargarDatos(int mes, int anio) {
        LocalDateTime inicio = LocalDateTime.of(anio, mes, 1, 0, 0);
        LocalDateTime fin    = inicio.plusMonths(1).minusSeconds(1);

        // Ventas
        List<Ventas> ventasMes = ventaService.reporteEmpresaPorFecha(inicio, fin);
        double totalVentas = ventaService.calcularTotalVentas(ventasMes);
        lblTotalVentas.setText(String.format("S/ %.2f", totalVentas));
        lblCantidadVentas.setText(String.valueOf(ventasMes.size()));

        // Compras
        List<Compras> comprasMes = compraService.reportePorFecha(inicio, fin);
        double totalCompras = compraService.calcularTotalCompras(comprasMes);
        lblTotalCompras.setText(String.format("S/ %.2f", totalCompras));
        lblCantidadCompras.setText(String.valueOf(comprasMes.size()));

        // Top productos
        modeloProductos.setRowCount(0);
        List<Object[]> tops = ventaService.productosMasVendidosPorFecha(inicio, fin);
        int rank = 1;
        for (Object[] row : tops) {
            if (rank > 10) break;
            Producto p  = (Producto) row[0];
            Long cantidad = (Long) row[1];
            modeloProductos.addRow(new Object[]{
                rank++,
                p.getNombre(),
                p.getCategoria() != null ? p.getCategoria().getNombre() : "",
                cantidad,
                String.format("S/ %.2f", p.getPrecioVenta())
            });
        }
    }

    private String obtenerNombreMes(int mes, int anio) {
        return java.time.Month.of(mes)
                .getDisplayName(java.time.format.TextStyle.FULL,
                        new java.util.Locale("es", "PE"))
                .toUpperCase() + " " + anio;
    }

    // ══════════════════════════════════════════════
    //  INIT COMPONENTS
    // ══════════════════════════════════════════════
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        JPanel panelContenido = new JPanel(new BorderLayout(0, 15));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ── NAVEGADOR DE MES ─────────────────────
        mesActual  = java.time.LocalDate.now().getMonthValue();
        anioActual = java.time.LocalDate.now().getYear();

        JPanel panelNavegador = new JPanel(new BorderLayout(0, 5));
        panelNavegador.setBackground(new Color(114, 145, 226));
        panelNavegador.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblNavTitulo = new JLabel("📅 PERÍODO", SwingConstants.CENTER);
        lblNavTitulo.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblNavTitulo.setForeground(Color.WHITE);

        JPanel panelFlechas = new JPanel(new BorderLayout(5, 0));
        panelFlechas.setOpaque(false);

        JButton btnAnterior = new JButton("◀");
        btnAnterior.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnAnterior.setBackground(new Color(90, 120, 200));
        btnAnterior.setForeground(Color.WHITE);
        btnAnterior.setBorderPainted(false);
        btnAnterior.setFocusPainted(false);
        btnAnterior.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnSiguiente = new JButton("▶");
        btnSiguiente.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnSiguiente.setBackground(new Color(90, 120, 200));
        btnSiguiente.setForeground(Color.WHITE);
        btnSiguiente.setBorderPainted(false);
        btnSiguiente.setFocusPainted(false);
        btnSiguiente.setCursor(new Cursor(Cursor.HAND_CURSOR));

        lblMesAnio = new JLabel(obtenerNombreMes(mesActual, anioActual),
                SwingConstants.CENTER);
        lblMesAnio.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblMesAnio.setForeground(Color.WHITE);

        btnAnterior.addActionListener(e -> {
            mesActual--;
            if (mesActual < 1) { mesActual = 12; anioActual--; }
            lblMesAnio.setText(obtenerNombreMes(mesActual, anioActual));
            cargarDatos(mesActual, anioActual);
        });

        btnSiguiente.addActionListener(e -> {
            mesActual++;
            if (mesActual > 12) { mesActual = 1; anioActual++; }
            lblMesAnio.setText(obtenerNombreMes(mesActual, anioActual));
            cargarDatos(mesActual, anioActual);
        });

        panelFlechas.add(btnAnterior,  BorderLayout.WEST);
        panelFlechas.add(lblMesAnio,   BorderLayout.CENTER);
        panelFlechas.add(btnSiguiente, BorderLayout.EAST);

        panelNavegador.add(lblNavTitulo, BorderLayout.NORTH);
        panelNavegador.add(panelFlechas, BorderLayout.CENTER);

        // ── TARJETAS ─────────────────────────────
        JPanel panelTarjetas = new JPanel(new GridLayout(1, 4, 10, 0));
        panelTarjetas.setOpaque(false);

        lblTotalVentas    = new JLabel("S/ 0.00", SwingConstants.CENTER);
        lblCantidadVentas = new JLabel("0",        SwingConstants.CENTER);
        lblTotalCompras   = new JLabel("S/ 0.00", SwingConstants.CENTER);
        lblCantidadCompras = new JLabel("0",       SwingConstants.CENTER);

        panelTarjetas.add(panelNavegador);
        panelTarjetas.add(crearTarjetaConLabel("💰 VENTAS DEL MES",
                lblTotalVentas,    new Color(46, 139, 87)));
        panelTarjetas.add(crearTarjetaConLabel("🧾 N° VENTAS",
                lblCantidadVentas, new Color(70, 130, 180)));
        panelTarjetas.add(crearTarjetaConLabel("🛒 COMPRAS DEL MES",
                lblTotalCompras,   new Color(153, 101, 21)));

        // ── TABLA PRODUCTOS MÁS VENDIDOS ─────────
        modeloProductos = new DefaultTableModel(
                new String[]{"#", "Producto", "Categoría",
                    "Unidades Vendidas", "Precio Venta"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };

        JTable tablaProductos = new JTable(modeloProductos);
        tablaProductos.setRowHeight(30);
        tablaProductos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaProductos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaProductos.setSelectionBackground(new Color(114, 145, 226));
        tablaProductos.setSelectionForeground(Color.WHITE);

        // Resaltar top 3
        tablaProductos.setDefaultRenderer(Object.class,
                new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v,
                    boolean sel, boolean foc, int row, int col) {
                Component c = super.getTableCellRendererComponent(
                        t, v, sel, foc, row, col);
                if (!sel) {
                    if      (row == 0) c.setBackground(new Color(255, 215, 0,  80));
                    else if (row == 1) c.setBackground(new Color(192, 192, 192, 80));
                    else if (row == 2) c.setBackground(new Color(205, 127, 50,  80));
                    else               c.setBackground(Color.WHITE);
                }
                return c;
            }
        });

        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "🏆  Top 10 Productos Más Vendidos del Mes",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));

        panelContenido.add(panelTarjetas, BorderLayout.NORTH);
        panelContenido.add(scroll,        BorderLayout.CENTER);

        panelPrincipal.add(panelCabecera,  BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);
        panelPrincipal.add(panelFooter,    BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelPrincipal);
        pack();
    }

    private JPanel crearTarjetaConLabel(String titulo, JLabel lblValor, Color color) {
        JPanel panel = new JPanel(new GridLayout(2, 1, 0, 5));
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 11));
        lblTitulo.setForeground(Color.WHITE);

        lblValor.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblValor.setForeground(Color.WHITE);

        panel.add(lblTitulo);
        panel.add(lblValor);
        return panel;
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
}