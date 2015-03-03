package ru.unn.agile.Huffman.view;

import javax.swing.*;

import ru.unn.agile.Huffman.viewmodel.ViewModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class HuffCode {
    private JPanel mainPanel;
    private JTextArea message;
    private JButton codding;
    private JTextArea code;
    private JTextArea codemess;
    private JTextField status;
    private ViewModel viewModel;
    private HuffCode() { }

    public static void main(final String[] args) {
        JFrame frame = new JFrame("HuffmanCode");
        frame.setContentPane(new HuffCode(new ViewModel()).mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void bind() {
        viewModel.setMessage(message.getText());
    }

    private HuffCode(final ViewModel viewModel) {
        this.viewModel = viewModel;
        backBind();
        codding.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent actionEvent) {
                bind();
                HuffCode.this.viewModel.gettree();
                backBind();
            }
        });
    }

    private void backBind() {
        code.setText(viewModel.getCode());
        codemess.setText(viewModel.getCodeMessage());
        status.setText(viewModel.getStatus());
    }
}
