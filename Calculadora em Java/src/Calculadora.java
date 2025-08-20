import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.util.List;
import java.lang.Math;

public class Calculadora {
    int janelaVetical = 540;
    int janelaHorizontal = 360;

    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customOrange = new Color(255, 149, 0);

    String[] valorBotoes = {
        "AC", "+/-", "%", "÷",
        "7", "8", "9", "X",
        "4", "5", "6", "-",
        "1", "2", "3", "+",
        "0", ".", "√", "=",
    };
    String[] direitaSimbolos = {"÷", "X", "-", "+", "="};
    String[] topoSimbolos = {"AC", "+/-", "%"};

    JFrame janela = new JFrame("Calculadora");
    JLabel painelTextoDisplay = new JLabel();
    JPanel painelDisplay = new JPanel();
    JPanel painelBotoes = new JPanel();

    //A+B, A-B, A*B, A/B
    String A = "0";
    String operador = null;
    String B = null;

    Calculadora() {
        //janela.setVisible(true);
        janela.setSize(janelaHorizontal, janelaVetical);
        janela.setLocationRelativeTo(null);
        janela.setResizable(false);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());
        
        painelTextoDisplay.setBackground(customBlack);
        painelTextoDisplay.setForeground(Color.white);
        painelTextoDisplay.setFont(new Font("Arial", Font.PLAIN,80));
        painelTextoDisplay.setHorizontalAlignment(JLabel.RIGHT);
        painelTextoDisplay.setText("0");
        painelTextoDisplay.setOpaque(true);

        painelDisplay.setLayout(new BorderLayout());
        painelDisplay.add(painelTextoDisplay);
        janela.add(painelDisplay, BorderLayout.NORTH);

        painelBotoes.setLayout(new GridLayout(5, 4));
        painelBotoes.setBackground(customBlack);
        janela.add(painelBotoes);

        for (int i = 0; i < valorBotoes.length; i++) {
            JButton botao = new JButton();
            String valorBotao = valorBotoes[i];
            botao.setFont(new Font("Arial", Font.PLAIN, 30));
            botao.setText(valorBotao);
            botao.setFocusable(false);
            botao.setBorder(new LineBorder(customBlack));

            List<String> topoSimbolosLista = Arrays.asList(topoSimbolos); 
            List<String> direitaSimbolosLista = Arrays.asList(direitaSimbolos);

            if (topoSimbolosLista.contains(valorBotao)) {
                botao.setBackground(customLightGray);
                botao.setForeground(customBlack);
            }
            else if (direitaSimbolosLista.contains(valorBotao)) {
                botao.setBackground(customOrange);
                botao.setForeground(Color.white);
            }
            else {
                botao.setBackground(customDarkGray);
                botao.setForeground(Color.white);
            }

            painelBotoes.add(botao); 

            botao.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    JButton botao = (JButton) e.getSource();
                    String valorBotao = botao.getText();

                    if (direitaSimbolosLista.contains(valorBotao)) {
                        if (valorBotao == "=") {
                            if (A != null) {
                                B = painelTextoDisplay.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);


                                if (operador =="+") {
                                    painelTextoDisplay.setText(removeZeroDecimal(numA+numB));
                                }
                                else if (operador =="-") {
                                    painelTextoDisplay.setText(removeZeroDecimal(numA-numB));
                                }
                                else if (operador =="X") {
                                    painelTextoDisplay.setText(removeZeroDecimal(numA*numB));
                                }
                                else if (operador =="÷") {
                                    painelTextoDisplay.setText(removeZeroDecimal(numA/numB));
                                }
                                clearAll();
                            }

                        }
                        else if ("+-X÷".contains(valorBotao)) {
                            if (operador == null) {
                                A = painelTextoDisplay.getText();
                                painelTextoDisplay.setText("0");
                                B = "0";
                            }
                            operador = valorBotao;
                        }

                    }
                    else if (topoSimbolosLista.contains(valorBotao)) {
                        if (valorBotao == "AC") {
                            clearAll();
                            painelTextoDisplay.setText("0");                            
                        }
                        else if (valorBotao == "+/-") {
                            double numDisplay = Double.parseDouble(painelTextoDisplay.getText());
                            numDisplay *= -1;
                            painelTextoDisplay.setText(removeZeroDecimal(numDisplay));
                        }
                        else if (valorBotao == "%") {
                            double numDisplay = Double.parseDouble(painelTextoDisplay.getText());
                            numDisplay /= 100;
                            painelTextoDisplay.setText(removeZeroDecimal(numDisplay));

                        }
                        
                    }
                    else { // digitos or .
                        if (valorBotao == ".") {
                            if (painelTextoDisplay.getText().contains(valorBotao)) {
                                painelTextoDisplay.setText(painelTextoDisplay.getText() + valorBotao);
                            }
                        }
                        else if ("0123456789".contains(valorBotao)) {
                            if (painelTextoDisplay.getText() == "0") {
                                painelTextoDisplay.setText(valorBotao); 
                            }
                            
                            else {
                                painelTextoDisplay.setText(painelTextoDisplay.getText() + valorBotao);
                            }

                        }
                        else if (valorBotao == "√") {
                            double numDisplay = Double.parseDouble(painelTextoDisplay.getText());
                            numDisplay = Math.sqrt(numDisplay);
                            painelTextoDisplay.setText(removeZeroDecimal(numDisplay));
                        }   
                    }
                }
            });
            janela.setVisible(true);
        }
      }  
    void clearAll() {
        A = "0";
        operador = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
