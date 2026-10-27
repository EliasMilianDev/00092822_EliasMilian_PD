package org.example.screen;

import org.example.Connection;
import org.example.screen.components.Buttons;
import org.example.screen.components.PanelResult;
import org.example.screen.components.TextField;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.datatransfer.StringSelection;

/** Ventana principal: arma los componentes y conecta la interfaz con el motor Prolog. */
public class Window extends JFrame {

    private static final String[] OPERACIONES = {"+", "−", "×", "÷"};
    private static final String[] PREDICADOS = {"suma", "resta", "multiplicacion", "division"};

    private final TextField txtNumeroA = new TextField("Primer número");
    private final TextField txtNumeroB = new TextField("Segundo número");
    private final PanelResult panelResultado = new PanelResult();
    private final JLabel lblEstado = new JLabel(" ");
    private int indiceOperacion;
    private final JButton btnOperacion = new JButton(OPERACIONES[0]);

    public Window(Connection motor) {
        super("Calculadora básica en Prolog");

        txtNumeroA.addActionListener(e -> ejecutar(motor));
        txtNumeroB.addActionListener(e -> ejecutar(motor));

        JPanel contenido = new JPanel();
        contenido.setLayout(new BoxLayout(contenido, BoxLayout.Y_AXIS));
        contenido.setOpaque(false);
        agregar(contenido, etiqueta("Números y operación"), 6);
        agregar(contenido, crearEntrada(), 8);
        agregar(contenido, crearGuia(), 18);
        agregar(contenido, crearBotones(motor), 22);
        agregar(contenido, panelResultado, 10);
        agregar(contenido, crearPie(), 0);

        JPanel raiz = new JPanel(new BorderLayout(0, 20));
        raiz.setBackground(Theme.BG);
        raiz.setBorder(new EmptyBorder(24, 28, 24, 28));
        raiz.add(crearEncabezado(), BorderLayout.NORTH);
        raiz.add(contenido, BorderLayout.CENTER);

        setContentPane(raiz);
        setMinimumSize(new Dimension(660, 520));
        pack();
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // ------------------------------------------------------------------
    //  Construcción de secciones
    // ------------------------------------------------------------------

    private JPanel crearEncabezado() {
        JLabel titulo = new JLabel("Calculadora básica");
        titulo.setFont(Theme.sans(Font.BOLD, 24));
        titulo.setForeground(Theme.TEXT);

        JLabel subtitulo = new JLabel("Suma, resta, multiplicación y división con reglas en Prolog");
        subtitulo.setFont(Theme.sans(Font.PLAIN, 13));
        subtitulo.setForeground(Theme.MUTED);

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setOpaque(false);
        p.add(titulo);
        p.add(Box.createVerticalStrut(4));
        p.add(subtitulo);
        return p;
    }

    private JPanel crearEntrada() {
        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.setOpaque(false);
        p.add(crearCampo("Primer número", txtNumeroA));
        p.add(Box.createHorizontalStrut(10));
        p.add(crearCampo("Operación", crearSelectorOperacion()));
        p.add(Box.createHorizontalStrut(10));
        p.add(crearCampo("Segundo número", txtNumeroB));
        return p;
    }

    private JPanel crearCampo(String nombre, JComponent componente) {
        JPanel campo = new JPanel();
        campo.setLayout(new BoxLayout(campo, BoxLayout.Y_AXIS));
        campo.setOpaque(false);
        JLabel titulo = etiqueta(nombre);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        componente.setAlignmentX(Component.LEFT_ALIGNMENT);
        campo.add(titulo);
        campo.add(Box.createVerticalStrut(5));
        campo.add(componente);
        return campo;
    }

    private JButton crearSelectorOperacion() {
        btnOperacion.setFont(Theme.sans(Font.BOLD, 20));
        btnOperacion.setForeground(Theme.TEXT);
        btnOperacion.setBackground(Theme.CARD);
        btnOperacion.setFocusPainted(false);
        btnOperacion.setBorder(new LineBorder(Theme.BORDER, 1, true));
        btnOperacion.setPreferredSize(new Dimension(120, 50));
        btnOperacion.setMinimumSize(new Dimension(120, 50));
        btnOperacion.setMaximumSize(new Dimension(120, 50));
        btnOperacion.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnOperacion.addActionListener(e -> mostrarMenuOperaciones());
        return btnOperacion;
    }

    private void mostrarMenuOperaciones() {
        JPopupMenu menu = new JPopupMenu();
        menu.setBackground(Theme.CARD);
        menu.setBorder(new LineBorder(Theme.BORDER, 1, true));

        for (int i = 0; i < OPERACIONES.length; i++) {
            final int seleccion = i;
            JMenuItem opcion = new JMenuItem(OPERACIONES[i]);
            opcion.setFont(Theme.sans(Font.BOLD, 18));
            opcion.setForeground(Theme.TEXT);
            opcion.setBackground(Theme.CARD);
            opcion.setHorizontalAlignment(SwingConstants.CENTER);
            opcion.setBorder(new EmptyBorder(6, 22, 6, 22));
            opcion.addActionListener(e -> {
                indiceOperacion = seleccion;
                btnOperacion.setText(OPERACIONES[seleccion]);
            });
            menu.add(opcion);
        }
        menu.show(btnOperacion, 0, btnOperacion.getHeight());
    }

    private JPanel crearGuia() {
        JPanel guia = new JPanel(new BorderLayout(10, 0));
        guia.setBackground(Theme.CARD);
        guia.setBorder(new EmptyBorder(10, 14, 10, 14));

        JLabel icono = new JLabel("i");
        icono.setFont(Theme.sans(Font.BOLD, 14));
        icono.setForeground(Theme.BLUE);

        JLabel texto = new JLabel("Escribí los dos números, elegí una operación y presioná Calcular. "
                + "También podés usar coma o punto decimal.");
        texto.setFont(Theme.sans(Font.PLAIN, 12));
        texto.setForeground(Theme.MUTED);

        guia.add(icono, BorderLayout.WEST);
        guia.add(texto, BorderLayout.CENTER);
        return guia;
    }

    private JPanel crearBotones(Connection motor) {
        Buttons btnCalcular = new Buttons("Calcular", Theme.BLUE, Theme.BG, 26, 11, 14);
        btnCalcular.addActionListener(e -> ejecutar(motor));

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        p.setOpaque(false);
        p.add(btnCalcular);
        return p;
    }

    private JPanel crearPie() {
        lblEstado.setFont(Theme.sans(Font.PLAIN, 12));
        lblEstado.setForeground(Theme.MUTED);

        Buttons btnCopiar = new Buttons("Copiar", Theme.CARD, Theme.TEXT, 16, 7, 12);
        btnCopiar.addActionListener(e -> copiarResultado());

        JPanel p = new JPanel(new BorderLayout());
        p.setOpaque(false);
        p.add(lblEstado, BorderLayout.WEST);
        p.add(btnCopiar, BorderLayout.EAST);
        return p;
    }

    // ------------------------------------------------------------------
    //  Lógica
    // ------------------------------------------------------------------

    private void ejecutar(Connection motor) {
        String entradaA = txtNumeroA.getText().trim().replace(',', '.');
        String entradaB = txtNumeroB.getText().trim().replace(',', '.');
        int indice = indiceOperacion;
        panelResultado.setTitulo("Resultado");

        if (entradaA.isEmpty() || entradaB.isEmpty()) {
            mostrar("", "Ingresá los dos números.", Theme.AMBER);
            return;
        }
        try {
            double numeroA = Double.parseDouble(entradaA);
            double numeroB = Double.parseDouble(entradaB);
            String resultado = motor.calcular(PREDICADOS[indice], numeroA, numeroB);
            if (resultado.startsWith("No se puede")) {
                mostrar("", resultado, Theme.AMBER);
            } else {
                mostrar(entradaA + " " + OPERACIONES[indice] + " " + entradaB + " = " + resultado,
                        "Listo", Theme.GREEN);
            }
        } catch (NumberFormatException ex) {
            mostrar("", "Ingresá números válidos.", Theme.RED);
        } catch (Exception ex) {
            mostrar("", "No se pudo calcular: " + ex.getMessage(), Theme.RED);
        }
    }

    private void mostrar(String resultado, String estado, Color colorEstado) {
        panelResultado.setTexto(resultado);
        lblEstado.setText(estado);
        lblEstado.setForeground(colorEstado);
    }

    private void copiarResultado() {
        String t = panelResultado.getTexto();
        if (t.isEmpty()) {
            return;
        }
        Toolkit.getDefaultToolkit().getSystemClipboard()
                .setContents(new StringSelection(t), null);
        lblEstado.setText("Copiado al portapapeles");
        lblEstado.setForeground(Theme.BLUE);
    }

    // ------------------------------------------------------------------
    //  Utilidades
    // ------------------------------------------------------------------

    private static void agregar(JPanel panel, JComponent c, int espacioDespues) {
        c.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(c);
        if (espacioDespues > 0) {
            panel.add(Box.createVerticalStrut(espacioDespues));
        }
    }

    private static JLabel etiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(Theme.sans(Font.BOLD, 12));
        l.setForeground(Theme.MUTED);
        return l;
    }
}
