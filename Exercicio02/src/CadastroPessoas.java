import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroPessoas extends JPanel {

    private JTextField tfDataNascimento;
    private JRadioButton rbMasculino, rbFeminino;
    private JComboBox<String> cbEstadoCivil;
    private JButton btnCadastrar;
    private JTextArea taResultado;
    private ButtonGroup bgSexo;
    public JPanel painelPrincipal;
    private JTextField tfNome;
    private JTextField tfCPF;
    private JTextField tfProfissao;


    public CadastroPessoas() {

        setLayout(new GridLayout(8, 2));

        add(new JLabel("Nome:"));
        tfNome = new JTextField();
        add(tfNome);

        add(new JLabel("CPF:"));
        tfCPF = new JTextField();
        add(tfCPF);

        add(new JLabel("Data de Nascimento (dd-MM-yyyy):"));
        tfDataNascimento = new JTextField();
        add(tfDataNascimento);

        add(new JLabel("Profissão:"));
        tfProfissao = new JTextField();
        add(tfProfissao);

        add(new JLabel("Sexo:"));
        rbMasculino = new JRadioButton("Masculino");
        rbFeminino = new JRadioButton("Feminino");
        bgSexo = new ButtonGroup();
        bgSexo.add(rbMasculino);
        bgSexo.add(rbFeminino);
        JPanel panelSexo = new JPanel();
        panelSexo.add(rbMasculino);
        panelSexo.add(rbFeminino);
        add(panelSexo);

        add(new JLabel("Estado Civil:"));
        String[] estados = {"Solteiro", "Casado", "Divorciado", "Viúvo"};
        cbEstadoCivil = new JComboBox<>(estados);
        add(cbEstadoCivil);

        btnCadastrar = new JButton("Cadastrar");
        add(btnCadastrar);
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cadastrarPessoa();
            }
        });

        taResultado = new JTextArea(4, 30);
        taResultado.setEditable(false);
        add(new JScrollPane(taResultado));
    }

    public static JPanel createCadastroPessoas() {
        CadastroPessoas panel = new CadastroPessoas();
        panel.setName("painelPrincipal");
        return panel;
    }

    private void cadastrarPessoa() {
        String nome = tfNome.getText().trim();
        String cpf = tfCPF.getText().trim();
        String profissao = tfProfissao.getText().trim();
        String sexo = rbMasculino.isSelected() ? "Masculino" : "Feminino";
        String estadoCivil = (String) cbEstadoCivil.getSelectedItem();
        String dataNascimentoStr = tfDataNascimento.getText().trim();
        LocalDate dataNascimento;

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "O nome não pode estar em branco.");
            return;
        }

        if (!validarCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido.");
            return;
        }

        if (profissao.isEmpty()) {
            profissao = "Desempregado(a)";
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Utilize o formato dd-MM-yyyy.");
            return;
        }

        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();

        String mensagem = String.format(
                "Nome: %s\nCPF: %s\nIdade: %d\nSexo: %s\nEstado Civil: %s\nProfissão: %s",
                nome, cpf, idade, sexo, estadoCivil, profissao
        );

        if (profissao.equals("Engenheiro") || profissao.equals("Analista de Sistemas")) {
            mensagem += "\nVagas disponíveis na área.";
        }

        taResultado.setText(mensagem);
    }

    private boolean validarCPF(String cpf) {
        cpf = cpf.replaceAll("[^0-9]", "");

        if (cpf.length() != 11) {
            return false;
        }

        return !cpf.matches("(\\d)\\1{10}");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cadastro de Pessoas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel painelCadastro = CadastroPessoas.createCadastroPessoas();
        frame.add(painelCadastro);

        frame.pack();
        frame.setVisible(true);
    }
}
