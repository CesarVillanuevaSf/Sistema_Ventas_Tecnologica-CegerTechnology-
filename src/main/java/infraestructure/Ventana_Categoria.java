package infraestructure;

import domain.Categoria;
import domain.Empleados;
import controlador.CategoriaService;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Ventana_Categoria extends javax.swing.JFrame {

    private final Empleados empleado;
    private final CategoriaService categoriaService;
    private DefaultTableModel modeloTabla;
    private Categoria categoriaSeleccionada = null;

    public Ventana_Categoria(Empleados empleado) {
        this.empleado = empleado;
        this.categoriaService = presentation.SistemaVentasApplication
                .getBean(CategoriaService.class);

        initComponents();
        this.setLocationRelativeTo(null);
        this.setSize(1000, 720);
        this.setResizable(false);

        configurarCabecera();
        configurarFooter();
        cargarTabla();
    }

    //  CABECERA

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

        JLabel lblTitulo = new JLabel("📋  Categorías");
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

    //  TABLA
    private void cargarTabla() {
        modeloTabla = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Tipo"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablaCategoria.setModel(modeloTabla);
        tablaCategoria.setRowHeight(28);
        tablaCategoria.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaCategoria.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaCategoria.setSelectionBackground(new Color(114, 145, 226));
        tablaCategoria.setSelectionForeground(Color.WHITE);

        tablaCategoria.getColumnModel().getColumn(0).setMinWidth(0);
        tablaCategoria.getColumnModel().getColumn(0).setMaxWidth(0);

        recargarTabla();


        tablaCategoria.getSelectionModel().addListSelectionListener(e -> {
            int fila = tablaCategoria.getSelectedRow();
            if (fila >= 0) {
                Long id = (Long) modeloTabla.getValueAt(fila, 0);
                categoriaSeleccionada = categoriaService.listarCategorias()
                        .stream().filter(c -> c.getCodCategoria().equals(id))
                        .findFirst().orElse(null);
                if (categoriaSeleccionada != null) llenarFormulario(categoriaSeleccionada);
            }
        });
    }

    private void recargarTabla() {
        modeloTabla.setRowCount(0);
        for (Categoria c : categoriaService.listarCategorias()) {
            modeloTabla.addRow(new Object[]{
                c.getCodCategoria(),
                c.getNombre(),
                c.getDescripcion()
            });
        }
    }

    private void llenarFormulario(Categoria c) {
        txtNombre.setText(c.getNombre());
        txtDescripcion.setText(c.getDescripcion());
    }

    private void limpiarFormulario() {
        txtNombre.setText("");
        txtDescripcion.setText("");
        categoriaSeleccionada = null;
        tablaCategoria.clearSelection();
    }

    //  ACCIONES CRUD

    private void agregarCategoria() {
        try {
            Categoria c = new Categoria();
            c.setNombre(txtNombre.getText().trim());
            c.setDescripcion(txtDescripcion.getText().trim());

            categoriaService.guardarCategoria(c);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Categoría agregada correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editarCategoria() {
        if (categoriaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una categoría de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        try {
            categoriaSeleccionada.setNombre(txtNombre.getText().trim());
            categoriaSeleccionada.setDescripcion(txtDescripcion.getText().trim());

            categoriaService.guardarCategoria(categoriaSeleccionada);
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Categoría actualizada correctamente.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarCategoria() {
        if (categoriaSeleccionada == null) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione una categoría de la tabla.", "Aviso",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar la categoría: "
                + categoriaSeleccionada.getNombre() + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            categoriaService.eliminarCategoria(categoriaSeleccionada.getCodCategoria());
            recargarTabla();
            limpiarFormulario();
            JOptionPane.showMessageDialog(this,
                    "Categoría eliminada.", "Éxito",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void buscarCategoria() {
        String texto = txtBuscar.getText().trim();
        modeloTabla.setRowCount(0);
        List<Categoria> lista = texto.isEmpty()
                ? categoriaService.listarCategorias()
                : categoriaService.buscarPorNombre(texto);
        for (Categoria c : lista) {
            modeloTabla.addRow(new Object[]{
                c.getCodCategoria(), c.getNombre(), c.getDescripcion()
            });
        }
    }

    //  INIT COMPONENTS
    @SuppressWarnings("unchecked")
    private void initComponents() {
        panelPrincipal = new JPanel(new BorderLayout());
        panelCabecera  = new JPanel();
        panelFooter    = new JPanel();

        JPanel panelContenido = new JPanel(new BorderLayout(15, 0));
        panelContenido.setBackground(new Color(242, 242, 242));
        panelContenido.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBackground(Color.WHITE);
        panelFormulario.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Registro de Categorías",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("SansSerif", Font.BOLD, 14),
                new Color(114, 145, 226)));
        panelFormulario.setPreferredSize(new Dimension(300, 0));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNombre      = new JTextField(15);
        txtDescripcion = new JTextArea(4, 15);
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setWrapStyleWord(true);
        txtDescripcion.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JScrollPane scrollDesc = new JScrollPane(txtDescripcion);

  
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelFormulario.add(lblNombre, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        panelFormulario.add(txtNombre, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        JLabel lblDesc = new JLabel("Descripción:");
        lblDesc.setFont(new Font("SansSerif", Font.BOLD, 13));
        panelFormulario.add(lblDesc, gbc);
        gbc.gridx = 1; gbc.weightx = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panelFormulario.add(scrollDesc, gbc);

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 8, 10));
        panelBotones.setOpaque(false);

        JButton btnAgregar  = crearBoton("+ Agregar",  new Color(46, 139, 87));
        JButton btnEditar   = crearBoton("✎ Editar",   new Color(70, 130, 180));
        JButton btnEliminar = crearBoton("✕ Eliminar", new Color(178, 34, 34));
        JButton btnLimpiar  = crearBoton("↺ Limpiar",  new Color(128, 128, 128));

        btnAgregar.addActionListener(e  -> agregarCategoria());
        btnEditar.addActionListener(e   -> editarCategoria());
        btnEliminar.addActionListener(e -> eliminarCategoria());
        btnLimpiar.addActionListener(e  -> limpiarFormulario());

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2; gbc.weightx = 1.0;
        panelFormulario.add(panelBotones, gbc);

        JPanel panelTabla = new JPanel(new BorderLayout(0, 8));
        panelTabla.setOpaque(false);

        JPanel panelBuscar = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelBuscar.setOpaque(false);
        txtBuscar = new JTextField(25);
        txtBuscar.setFont(new Font("SansSerif", Font.PLAIN, 13));
        JButton btnBuscar = crearBoton("🔍 Buscar", new Color(114, 145, 226));
        btnBuscar.addActionListener(e -> buscarCategoria());
        txtBuscar.addActionListener(e -> buscarCategoria());
        panelBuscar.add(new JLabel("Buscar: "));
        panelBuscar.add(txtBuscar);
        panelBuscar.add(btnBuscar);

        tablaCategoria = new JTable();
        JScrollPane scroll = new JScrollPane(tablaCategoria);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(114, 145, 226), 2),
                "Listado de Categorías",
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
    private JTextField txtNombre, txtBuscar;
    private JTextArea txtDescripcion;
    private JTable tablaCategoria;
}