import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Catalogo extends JPanel {
	
	public Catalogo(Produto[] produtos, int QtProdutos){	
		setLayout(new GridLayout(3,3));
		int j=0;
		for(int i=0;i<3;i++) {
			for(int l=0;l<3;l++) {
				if(j<QtProdutos) {
				add(produtos[j],i,l);
				}
				j++;
			}
		}
	}	
}
