
package infraestructure;
import domain.Categoria;
import domain.Empleados;
import domain.Producto;
import controlador.CategoriaService;
import controlador.ProductoService;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class Ventana_Producto extends javax.swing.JFrame{
    private final Empleados empleado;
    private final ProductoService productoService;
    private final CategoriaService categoriaService;
    private DefaultTableModel modeloTabla;
    private Producto productoSeleccionado = null;

    public Ventana_Producto(Empleados empleado) {
        this.empleado = empleado;
        this.productoService = presentation.SistemaVentasApplication
                .getBean(ProductoService.class);
        this.categoriaService = presentation.SistemaVentasApplication
                .getBean(CategoriaService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1000, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarTabla();
        cargarComboCategorias();
    }

    // ══════════════════════════════════════════════
    //  CABECERA
    // ══════════════════════════════════════════════
    private void configurarCabecera() {
        panelCabecera.removeAll();
        panelCabecera.setBackground(new Color(114, 145, 226));
        panelCabecera.setPreferredSize(new Dimension(1000, 100));
        panelCabecera.setLayout(new BorderLayout(15, 0));

        // Logo
        JLabel lblLogo = new JLabel();
        lblLogo.setPreferredSize(new Dimension(220, 90));
        java.net.URL urlLogo = getClass().getResource("/imagenes/Logo-Empresa.png");
        if (urlLogo != null) {
            Image img = new ImageIcon(urlLogo).getImage()
                    .getScaledInstance(200, 85, Image.SCALE_SMOOTH);
            lblLogo.setIcon(new ImageIcon(img));
        }
        panelCabecera.add(lblLogo, BorderLayout.WEST);

        // Botón regresar + título centro
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

        JLabel lblTitulo = new JLabel("🛒  Productos");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        panelCentro.add(btnRegresar);
        panelCentro.add(lblTitulo);
        panelCabecera.add(panelCentro, BorderLayout.CENTER);

        // Saludo derecha
        JPanel panelSaludo = new JPanel(new GridLayout(2, 1, 0, 4));
        panelSaludo.setOpaque(false);

        JLabel lblHola = new JLabel("Hola, " + empleado.getNombre() + " " + empleado.getApellido());
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

    //  FOOTER
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
                "© 2025 Ceger Technology. Todos los derechos reservados.  ");
        lblCopyright.setForeground(Color.WHITE);
        lblCopyright.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panelFooter.add(lblCopyright, BorderLayout.EAST);

        panelFooter.revalidate();
        panelFooter.repaint();
    }

    //  CARGAR TABLA
    private void cargarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Código Barra", "Nombre", "P. Compra",
                    "P. Venta", "Stock Mín.", "Stock Act.", "Categoría", "Tipo", "Descripcion"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaProductos.setModel(modeloTabla);
        tablaProductos.setRowHeight(28);
        tablaProductos.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaProductos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaProductos.setSelectionBackground(new Color(114, 145, 226));
        tablaProductos.setSelectionForeground(Color.WHITE);

        // Ocultar columna ID
        tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
        tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTabla();

        // Al seleccionar fila → llenar formulario
        tablaProductos.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaProductos.getSelectedRow();
            if (fila >= 0) {
                Long id = (Long) modeloTabla.getValueAt(fila, 0);
                productoSeleccionado = productoService.listarProductos()
                        .stream().filter(p -> p.getCodProducto().equals(id))
                        .findFirst().orElse(null);
                if (productoSeleccionado != null) llenarFormulario(productoSeleccionado);
            }
        });
    }

    private void recargarTabla() {
        modeloTabla.setRowCount(0);
        for (Producto p : productoService.listarProductos()) {
            modeloTabla.addRow(new Object[]{
                p.getCodProducto(),
                p.getCodigoBarra(),
                p.getNombre(),
                p.getPrecioCompra(),
                p.getPrecioVenta(),
                p.getStockMinimo(),
                p.getStockActual(),
                p.getCategoria() != null ? p.getCategoria().getNombre(): "",
                p.getTipo() != null ? p.getTipo() : "",
                p.getDescripcion() != null ? p.getDescripcion() : ""
            });
        }
    }

    private void cargarComboCategorias() {
        cmbCategoria.removeAllItems();
        cmbCategoria.addItem(null);
        for (Categoria c : categoriaService.listarCategorias()) {
            cmbCategoria.addItem(c);
        }
        cmbCategoria.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                setText(value instanceof Categoria ? ((Categoria) value).getNombre() : "Seleccione...");
                return this;
            }
        });
    }

    private void llenarFormulario(Producto p) {
        txtCodigoBarra.setText(p.getCodigoBarra());
        txtNombre.setText(p.getNombre());
        txtPrecioCompra.setText(String.valueOf(p.getPrecioCompra()));
        txtPrecioVenta.setText(String.valueOf(p.getPrecioVenta()));
        txtStockMinimo.setText(String.valueOf(p.getStockMinimo()));
        txtStockActual.setText(String.valueOf(p.getStockActual()));
        cmbTipo.setSelectedItem(p.getTipo() != null ? p.getTipo() : "Seleccione...");
        txtDescripcion.setText(p.getDescripcion() != null ? p.getDescripcion() : "");
        if (p.getCategoria() != null) {
            for (int i = 0; i < cmbCategoria.getItemCount(); i++) {
                Categoria c = cmbCategoria.getItemAt(i);
                if (c != null && c.getCodCategoria().equals(p.getCategoria().getCodCategoria())) {
                    cmbCategoria.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void limpiarFormulario() {
        txtCodigoBarra.setText("");
        txtNombre.setText("");
        txtPrecioCompra.setText("");
        txtPrecioVenta.setText("");
        txtStockMinimo.setText("");
        txtStockActual.setText("");
        cmbCategoria.setSelectedIndex(0);
        productoSeleccionado = null;
        tablaProductos.clearSelection();
        cmbTipo.setSelectedIndex(0);
        txtDescripcion.setText("");
    }

    //  ACCIONES CRUD
    private String generarCodigoBarra(String nombreProducto) {
    // Toma las primeras 3 letras del nombre en mayúscula
    String prefijo = nombreProducto.length() >= 3
            ? nombreProducto.substring(0, 3).toUpperCase()
            : nombreProducto.toUpperCase();

    // Genera 6 números aleatorios
    int numeros = (int) (Math.random() * 900000) + 100000;

    return prefijo + "-" + numeros;
    }
    
    private void agregarProducto() {
        try 
        {
            Producto p = new Producto();
            p.setCodigoBarra(txtCodigoBarra.getText().trim());
            p.setNombre(txtNombre.getText().trim());
            p.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText().trim()));
            p.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText().trim()));
            p.setStockMinimo(Integer.parseInt(txtStockMinimo.getText().trim()));
            p.setStockActual(Integer.parseInt(txtStockActual.getText().trim()));
            p.setCategoria((Categoria) cmbCategoria.getSelectedItem());
            p.setTipo(cmbTipo.getSelectedIndex() == 0 ? null 
                    : (String) cmbTipo.getSelectedItem());
            p.setDescripcion(txtDescripcion.getText().trim());

            if (p.getNombre().isEmpty()) {
                JOptionPane.showMessageDialog(this, "La marca/modelo es obligatoria.",
                        "Error", JOptionPane.WARNING_MESSAGE);
                return;
        }
            productoService.guardarProducto(p);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Producto agregado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numéricos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
    }
}

    private void editarProducto() {
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            productoSeleccionado.setCodigoBarra(txtCodigoBarra.getText().trim());
            productoSeleccionado.setNombre(txtNombre.getText().trim());
            productoSeleccionado.setPrecioCompra(Double.parseDouble(txtPrecioCompra.getText().trim()));
            productoSeleccionado.setPrecioVenta(Double.parseDouble(txtPrecioVenta.getText().trim()));
            productoSeleccionado.setStockMinimo(Integer.parseInt(txtStockMinimo.getText().trim()));
            productoSeleccionado.setStockActual(Integer.parseInt(txtStockActual.getText().trim()));
            productoSeleccionado.setCategoria((Categoria) cmbCategoria.getSelectedItem());
            productoSeleccionado.setTipo(cmbTipo.getSelectedIndex() == 0 ? null
            : (String) cmbTipo.getSelectedItem());
            productoSeleccionado.setDescripcion(txtDescripcion.getText().trim());

            productoService.guardarProducto(productoSeleccionado);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Producto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Verifique los campos numéricos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        if (productoSeleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccione un producto de la tabla.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el producto: " + productoSeleccionado.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            productoService.eliminarProducto(productoSeleccionado.getCodProducto());
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this, "Producto eliminado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buscarProducto() {
        String texto = txtBuscar.getText().trim();
        modeloTabla.setRowCount(0);
        List<Producto> lista = texto.isEmpty()
                ? productoService.listarProductos()
                : productoService.buscarPorNombre(texto);
        for (Producto p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getCodProducto(), p.getCodigoBarra(), p.getNombre(),
                p.getPrecioCompra(), p.getPrecioVenta(),
                p.getStockMinimo(), p.getStockActual(),
                p.getCategoria() != null ? p.getCategoria().getNombre() : "Sin categoría"
            });
        }
    }

    //  INIT COMPONENTS
   
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        // Panel contenido dividido en 2: formulario izq, tabla der
        JPanel panelContenido = new JPanel(new BorderLayout(15, 0));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // ── FORMULARIO IZQUIERDA ─────────────────
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Registro de Productos",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));
        panelFormulario.setPreferredSize(new Dimension(340, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Campos
        txtCodigoBarra  = new JTextField(15);
        txtCodigoBarra.setEditable(false);
        txtCodigoBarra.setBackground(new Color(230, 230, 230));
        txtCodigoBarra.setFocusable(false);

        txtNombre = new JTextField(15);
        txtNombre.addFocusListener(new java.awt.event.FocusAdapter() {
    @Override
    public void focusLost(java.awt.event.FocusEvent e) {
        if (txtCodigoBarra.getText().trim().isEmpty()
                && !txtNombre.getText().trim().isEmpty()) {
            txtCodigoBarra.setText(generarCodigoBarra(txtNombre.getText().trim()));
        }
    }
});

        txtPrecioCompra = new JTextField(15);
        txtPrecioVenta  = new JTextField(15);
        txtStockMinimo  = new JTextField(15);
        txtStockActual  = new JTextField(15);
        cmbCategoria    = new JComboBox<>();


    cmbTipo = new JComboBox<>(new String[]{"Seleccione...", "Gamer", "Oficina", "Barato"});

// Área de descripción
        txtDescripcion = new JTextArea(3, 15);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);

        Object[][] campos = {
            {"Código Barra:",  txtCodigoBarra},
            {"Marca/Modelo:",  txtNombre},
            {"Precio Compra:", txtPrecioCompra},
            {"Precio Venta:",  txtPrecioVenta},
            {"Stock Mínimo:",  txtStockMinimo},
            {"Stock Actual:",  txtStockActual},
            {"Categoría:",     cmbCategoria},
            {"Tipo:",          cmbTipo},
            {"Descripción:",   scrollDesc}
        };

        for (int i = 0; i < campos.length; i++) {
            gbc.gridx = 0; gbc.gridy = i; gbc.weightx = 0;
            JLabel lbl = new JLabel((String) campos[i][0]);
            lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
            panelFormulario.add(lbl, gbc);
            gbc.gridx = 1; gbc.weightx = 1.0;
            panelFormulario.add((Component) campos[i][1], gbc);
        }

        // Botones CRUD
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panelBotones.setOpaque(false);

        JButton btnAgregar  = crearBoton("+ Agregar",  new Color(46, 139, 87));
        JButton btnEditar   = crearBoton("✎ Editar",   new Color(70, 130, 180));
        JButton btnEliminar = crearBoton("✕ Eliminar", new Color(178, 34, 34));
        JButton btnLimpiar  = crearBoton("↺ Limpiar",  new Color(128, 128, 128));

        btnAgregar.addActionListener(e  -> agregarProducto());
        btnEditar.addActionListener(e   -> editarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
        btnLimpiar.addActionListener(e  -> limpiarFormulario());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = campos.length;
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        panelFormulario.add(panelBotones, gbc);

        // TABLA DERECHA 
        JPanel panelTabla = new JPanel(new BorderLayout(0, 8));
        panelTabla.setOpaque(false);

        // Barra de búsqueda
        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBuscar.setOpaque(false);
        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnBuscar = crearBoton(" Buscar", new Color(114, 178, 226));
        btnBuscar.addActionListener(e -> buscarProducto());
        txtBuscar.addActionListener(e -> buscarProducto());
        panelBuscar.add(new JLabel("Buscar: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        tablaProductos = new JTable();
        JScrollPane scroll = new JScrollPane(tablaProductos);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Listado de Productos",
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

    //  VARIABLES
    private JPanel panelPrincipal, panelCabecera, panelFooter;
    private JTextField txtCodigoBarra, txtNombre, txtPrecioCompra,
    txtPrecioVenta, txtStockMinimo, txtStockActual, txtBuscar;
    
    private JComboBox<Categoria> cmbCategoria;
    private JTable tablaProductos;
    private JComboBox<String> cmbTipo;
    private JTextArea txtDescripcion;       
}

