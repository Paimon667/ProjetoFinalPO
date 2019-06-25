import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class Teste extends JFrame implements Serializable {

	Produto produtos[];
	int QtProdutos;
	Catalogo catalogo;
	Adicionar adicionar;
	RemoverEditar edit;
	JTabbedPane abas;
	boolean AcessoGerente;
	JMenu fileMenu;
	JMenuItem item;
	JMenuItem apagar;
	JMenuBar menu;
	int posicao = 404;

	public Teste() {

		this.setDefaultCloseOperation(this.EXIT_ON_CLOSE);
		this.setSize(600, 600);
		this.setVisible(true);
		//
		produtos = new Produto[9];
		for(int i=0;i<9;i++) {
			produtos[i] = new Produto();
		}
		QtProdutos = 0;
		abas = new JTabbedPane();
		AcessoGerente = false;
		fileMenu = new JMenu("Arquivo");
		item = new JMenuItem("Salvar");
		apagar = new JMenuItem("Apagar");
		fileMenu.add(item);
		fileMenu.add(apagar);
		menu = new JMenuBar();
		menu.add(fileMenu);
		setJMenuBar(menu);
		A();
		//
		this.add(abas);
	}

	public void AdicionarProduto(Produto produto) {
		produtos[QtProdutos] = produto;
		QtProdutos++;
	}

	public void R() { // remover panels
		abas.remove(catalogo);
		abas.remove(adicionar);
		abas.remove(edit);
	}

	public void A() { // colocar panels
		catalogo = new Catalogo(produtos, QtProdutos);
		abas.addTab("Catalogo", catalogo);
		adicionar = new Adicionar(AcessoGerente);
		edit = new RemoverEditar(AcessoGerente);
		if (AcessoGerente) {
			adicionar.Adicionar.addActionListener(new AdicionarAdd());
			edit.procurar.addActionListener(new editar());
			edit.remover.addActionListener(new editar());
			edit.editarPreco.addActionListener(new editar());
			edit.editarNome.addActionListener(new editar());
		} else {
			adicionar.login.addActionListener(new LoginGerente());
			edit.login.addActionListener(new LoginGerente());
		}
		abas.addTab("Adicionar", adicionar);
		abas.addTab("Editar", edit);

	}

	public class AdicionarAdd implements ActionListener {

		public void actionPerformed(ActionEvent x) {
			String nome = adicionar.campo[0].getText();
			try {
				if (adicionar.ImgEscolhida.getIcon() == null) {
					throw new Exception();
				}
				int preco = Integer.parseInt(adicionar.campo[1].getText());
				int codigo = Integer.parseInt(adicionar.campo[2].getText());
				JButton botao = new JButton(adicionar.ImgEscolhida.getIcon());
				botao.setToolTipText("Produto: "+codigo);
				botao.setBackground(new Color(238,238,238));
				botao.setForeground(new Color(238,238,238));
				AdicionarProduto(new Produto(nome, preco, codigo, botao,adicionar.file.getPath()));
				R();
				A();
			} catch (Exception erro) {
				JOptionPane.showMessageDialog(null, "Campos preenchidos incorretamente", null,
						JOptionPane.ERROR_MESSAGE);
			}

		}

	}

	public class LoginGerente implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			String login = "azul";
			String senha = "123";

			if ((adicionar.Usuario.getText().equals(login) && adicionar.senha.getText().equals(senha))
					|| edit.Usuario.getText().equals(login) && edit.senha.getText().equals(senha)) {
				AcessoGerente = true;
				JOptionPane.showMessageDialog(null, "Login efetuado com sucesso");
				R();
				A();
			} else {
				JOptionPane.showMessageDialog(null, "Usuário ou senha inválido");
			}

		}
	}

	public class editar implements ActionListener {

		public void actionPerformed(ActionEvent x) {

			if (x.getSource() == edit.procurar) {

				if (QtProdutos == 0) {
					JOptionPane.showMessageDialog(null, "Não existem produtos cadastrados");
				}

				for (int i = 0; i < QtProdutos; i++) {
					if (produtos[i].getCodigo() == Integer.parseInt(edit.codigo.getText())) {
						posicao = i;
						edit.imagem.setText("");
						edit.imagem.setIcon(new ImageIcon(produtos[posicao].getImagem()));
						edit.pn.setText(produtos[posicao].getNome() + "  R$ " + produtos[posicao].getPreco());
						break;
					}
				}
			}

			else {
				try {
					if (posicao == 404) {
						throw new Exception();
					}
					if (x.getSource() == edit.remover) {
						produtos[posicao] = produtos[QtProdutos];
						produtos[QtProdutos] = new Produto();
						QtProdutos--;
						posicao = 404;
						R();
						A();
					} else if (x.getSource() == edit.editarNome) {
						String nome = JOptionPane.showInputDialog("Digite o novo nome");
						produtos[posicao].setNome(nome);
						produtos[posicao].texto.setText(produtos[posicao].getNome()+"R$: "+produtos[posicao].getPreco());
						posicao = 404;
						R();
						A();
					} else if (x.getSource() == edit.editarPreco) {
						int preco = Integer.parseInt(JOptionPane.showInputDialog("Digite o novo preço"));
						produtos[posicao].setPreco(preco);
						produtos[posicao].texto.setText(produtos[posicao].getNome()+"R$: "+produtos[posicao].getPreco());
						posicao = 404;
						R();
						A();
					}
				}

				catch (NumberFormatException erro) {
					JOptionPane.showMessageDialog(null, "Valor inválido inserido");
				}

				catch (Exception erro) {
					JOptionPane.showMessageDialog(null, "Nenhum produto selecionado", null, JOptionPane.ERROR_MESSAGE);
				}
			}

		}

	}


}
