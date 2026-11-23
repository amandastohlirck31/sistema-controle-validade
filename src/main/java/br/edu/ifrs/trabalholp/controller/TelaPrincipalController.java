
package br.edu.ifrs.trabalholp.controller;

import br.edu.ifrs.trabalholp.model.*;
import br.edu.ifrs.trabalholp.repository.*;

import javafx.beans.binding.Bindings;
import javafx.collections.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

public class TelaPrincipalController {

    @FXML private TextField fnome, ftel, femail, pnome, pcat, pqtd, busca, mqtd;
    @FXML private DatePicker pvalidade;
    @FXML private ComboBox<Fornecedor> pfornecedor;
    @FXML private ComboBox<Produto> mproduto;
    @FXML private ComboBox<String> mtipo;

    @FXML private TableView<Fornecedor> tf;
    @FXML private TableColumn<Fornecedor, String> cfn, cft, cfe;

    @FXML private TableView<Produto> tp;
    @FXML private TableColumn<Produto, String> cpn, cpc, cpv, cpf, cpsituacao;
    @FXML private TableColumn<Produto, Number> cpq;

    @FXML private TableView<Movimentacao> tm;
    @FXML private TableColumn<Movimentacao, String> cmp, cmt, cmd;
    @FXML private TableColumn<Movimentacao, Number> cmq;

    @FXML private Label total, status;

    private final FornecedorRepository fr = new FornecedorRepository();
    private final ProdutoRepository pr = new ProdutoRepository();
    private final MovimentacaoRepository mr = new MovimentacaoRepository();

    private final ObservableList<Fornecedor> fornecedores =
            FXCollections.observableArrayList();

    private final ObservableList<Produto> produtos =
            FXCollections.observableArrayList();

    private final ObservableList<Movimentacao> movimentos =
            FXCollections.observableArrayList();

    private Fornecedor fornecedorAtual;
    private Produto produtoAtual;

    private final DateTimeFormatter fmt =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    @FXML
    public void initialize() {

        cfn.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getNome()));

        cft.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getTelefone()));

        cfe.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getEmail()));

        tf.setItems(fornecedores);

        tf.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> {
            fornecedorAtual = b;

            if (b != null) {
                fnome.setText(b.getNome());
                ftel.setText(b.getTelefone());
                femail.setText(b.getEmail());
            }
        });

        cpn.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getNome()));

        cpc.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getCategoria()));

        cpv.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getValidade().toString()));

        cpq.setCellValueFactory(x ->
                new javafx.beans.property.SimpleIntegerProperty(
                        x.getValue().getQuantidade()));

        cpf.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getFornecedor().getNome()));

        tp.setItems(produtos);

        cpsituacao.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getValidade().isBefore(java.time.LocalDate.now())
                                ? "VENCIDO"
                                : "Dentro da validade"));

        cpsituacao.setCellFactory(col -> new TableCell<Produto, String>() {
            @Override
            protected void updateItem(String situacao, boolean vazio) {
                super.updateItem(situacao, vazio);

                if (vazio || situacao == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(situacao);

                    setStyle("VENCIDO".equals(situacao)
                            ? "-fx-text-fill: #c62828; -fx-font-weight: bold;"
                            : "-fx-text-fill: #2e7d32;");
                }
            }
        });

        total.textProperty().bind(
                Bindings.concat("Produtos exibidos: ", Bindings.size(produtos)));

        tp.getSelectionModel().selectedItemProperty().addListener((o, a, b) -> {
            produtoAtual = b;

            if (b != null) {
                pnome.setText(b.getNome());
                pcat.setText(b.getCategoria());
                pvalidade.setValue(b.getValidade());
                pqtd.setText("" + b.getQuantidade());
                pfornecedor.setValue(b.getFornecedor());
            }
        });

        cmp.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getProduto().getNome()));

        cmt.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getTipo()));

        cmq.setCellValueFactory(x ->
                new javafx.beans.property.SimpleIntegerProperty(
                        x.getValue().getQuantidade()));

        cmd.setCellValueFactory(x ->
                new javafx.beans.property.SimpleStringProperty(
                        x.getValue().getDataHora().format(fmt)));

        tm.setItems(movimentos);

        mtipo.setItems(
                FXCollections.observableArrayList("Entrada", "Saída"));

        atualizarListas();
    }

    private void atualizarListas() {
        fornecedores.setAll(fr.listar());
        produtos.setAll(pr.listar());
        movimentos.setAll(mr.listar());

        pfornecedor.setItems(
                FXCollections.observableArrayList(fornecedores));

        mproduto.setItems(
                FXCollections.observableArrayList(produtos));
    }

    // Mensagem de sucesso na própria tela
    private void msg(String s) {
        status.setText("✓  " + s);

        status.setStyle(
                "-fx-text-fill: #216e39;" +
                "-fx-background-color: #e8f5e9;" +
                "-fx-padding: 8 12;" +
                "-fx-background-radius: 6;" +
                "-fx-font-weight: bold;"
        );

        status.setVisible(true);
        status.setManaged(true);
    }

    // Mensagem de erro na própria tela
    private void erro(Exception e) {
        status.setText("⚠  " +
                (e.getMessage() == null
                        ? "Confira os dados informados."
                        : e.getMessage()));

        status.setStyle(
                "-fx-text-fill: #b3261e;" +
                "-fx-background-color: #ffebee;" +
                "-fx-padding: 8 12;" +
                "-fx-background-radius: 6;" +
                "-fx-font-weight: bold;"
        );

        status.setVisible(true);
        status.setManaged(true);
    }

    private void exigir(boolean cond, String msg) {
        if (!cond) {
            throw new IllegalArgumentException(msg);
        }
    }

    @FXML
    private void salvarF() {
        try {
            exigir(!fnome.getText().isBlank(), "Informe o nome.");

            fr.salvar(new Fornecedor(
                    fnome.getText().trim(),
                    ftel.getText().trim(),
                    femail.getText().trim()));

            limparF();
            atualizarListas();
            msg("Fornecedor salvo com sucesso!");

        } catch (Exception e) {
            erro(e);
        }
    }

    @FXML
    private void editarF() {
        try {
            exigir(fornecedorAtual != null, "Selecione um fornecedor.");
            exigir(!fnome.getText().isBlank(), "Informe o nome.");

            fornecedorAtual.setNome(fnome.getText().trim());
            fornecedorAtual.setTelefone(ftel.getText().trim());
            fornecedorAtual.setEmail(femail.getText().trim());

            fr.atualizar(fornecedorAtual);

            limparF();
            atualizarListas();
            msg("Fornecedor atualizado com sucesso!");

        } catch (Exception e) {
            erro(e);
        }
    }

    @FXML
    private void excluirF() {
        try {
            exigir(fornecedorAtual != null, "Selecione um fornecedor.");

            fr.excluir(fornecedorAtual.getId());

            limparF();
            atualizarListas();
            msg("Fornecedor excluído com sucesso!");

        } catch (Exception e) {
            erro(e);
        }
    }

    @FXML
    private void limparF() {
        fornecedorAtual = null;
        fnome.clear();
        ftel.clear();
        femail.clear();
        tf.getSelectionModel().clearSelection();
    }

    @FXML
    private void salvarP() {
        try {
            exigir(!pnome.getText().isBlank(),
                    "Informe o nome do produto.");

            exigir(pvalidade.getValue() != null,
                    "Informe a validade.");

            exigir(pfornecedor.getValue() != null,
                    "Selecione um fornecedor.");

            int q = Integer.parseInt(pqtd.getText());

            exigir(q >= 0,
                    "Quantidade não pode ser negativa.");

            pr.salvar(new Produto(
                    pnome.getText().trim(),
                    pcat.getText().trim(),
                    pvalidade.getValue(),
                    q,
                    pfornecedor.getValue()));

            limparP();
            atualizarListas();
            msg("Produto salvo com sucesso!");

        } catch (Exception e) {
            erro(e);
        }
    }

    @FXML
    private void editarP() {
        try {
            exigir(produtoAtual != null, "Selecione um produto.");

            exigir(!pnome.getText().isBlank()
                    && pvalidade.getValue() != null
                    && pfornecedor.getValue() != null,
                    "Preencha nome, validade e fornecedor.");

            int q = Integer.parseInt(pqtd.getText());

            exigir(q >= 0,
                    "Quantidade não pode ser negativa.");

            produtoAtual.setNome(pnome.getText().trim());
            produtoAtual.setCategoria(pcat.getText().trim());
            produtoAtual.setValidade(pvalidade.getValue());
            produtoAtual.setQuantidade(q);
            produtoAtual.setFornecedor(pfornecedor.getValue());

            pr.atualizar(produtoAtual);

            limparP();
            atualizarListas();
            msg("Produto atualizado com sucesso!");

        } catch (Exception e) {
            erro(e);
        }
    }

    @FXML
    private void excluirP() {
        try {
            exigir(produtoAtual != null, "Selecione um produto.");

            pr.excluir(produtoAtual.getId());

            limparP();
            atualizarListas();
            msg("Produto excluído com sucesso!");

        } catch (Exception e) {
            erro(e);
        }
    }

    @FXML
    private void limparP() {
        produtoAtual = null;
        pnome.clear();
        pcat.clear();
        pqtd.clear();
        pvalidade.setValue(null);
        pfornecedor.setValue(null);
        busca.clear();

        tp.getSelectionModel().clearSelection();
        produtos.setAll(pr.listar());
    }

    @FXML
    private void pesquisar() {
        String t = busca.getText() == null
                ? ""
                : busca.getText().trim().toLowerCase();

        produtos.setAll(
                pr.listar().stream()
                        .filter(p ->
                                p.getNome().toLowerCase().contains(t)
                                || (p.getCategoria() != null
                                && p.getCategoria().toLowerCase().contains(t)))
                        .collect(Collectors.toList()));
    }

    @FXML
    private void todos() {
        busca.clear();
        produtos.setAll(pr.listar());
    }

    @FXML
    private void movimentar() {
        try {
            exigir(mproduto.getValue() != null && mtipo.getValue() != null,
                    "Selecione produto e tipo.");

            int q = Integer.parseInt(mqtd.getText());

            exigir(q > 0,
                    "Quantidade deve ser maior que zero.");

            mr.registrar(
                    mproduto.getValue().getId(),
                    mtipo.getValue(),
                    q);

            mqtd.clear();
            atualizarListas();
            msg("Movimentação registrada com sucesso!");

        } catch (Exception e) {
            erro(e);
        }
    }
}
