package view;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class UserForm extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    private JTextField[] fields = new JTextField[9];

    public UserForm() {

        setTitle("User Registry - oracle 19c");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridLayout(3, 6));

        String[] labels = {
                "ID","First Name","Last Name","Email",
                "Phone","Username","Password","Role","Status"
        };

        for (int i = 0; i < 9; i++) {
            formPanel.add(new JLabel(labels[i]));
            fields[i] = new JTextField();
            formPanel.add(fields[i]);
        }

        add(formPanel, BorderLayout.NORTH);

        model = new DefaultTableModel(labels, 7);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();

        JButton btnCreate = new JButton("CREATE");
        JButton btnRead = new JButton("READ");
        JButton btnUpdate = new JButton("UPDATE");
        JButton btnDelete = new JButton("DELETE");

        buttonPanel.add(btnCreate);
        buttonPanel.add(btnRead);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);

        add(buttonPanel, BorderLayout.SOUTH);

        UserDAO dao = new UserDAO();

        btnCreate.addActionListener(e -> execute("INSERT", dao));
        btnRead.addActionListener(e -> execute("SELECT", dao));
        btnUpdate.addActionListener(e -> execute("UPDATE", dao));
        btnDelete.addActionListener(e -> execute("DELETE", dao));
    }

    private void execute(String action, UserDAO dao) {
        try {
            User u = new User();
            u.setId(parseInt(fields[0].getText()));
            u.setFirstName(fields[1].getText());
            u.setLastName(fields[2].getText());
            u.setEmail(fields[3].getText());
            u.setPhone(fields[4].getText());
            u.setUsername(fields[5].getText());
            u.setPassword(fields[6].getText());
            u.setRole(fields[7].getText());
            u.setStatus(fields[8].getText());

            List<User> list = dao.executeSP(action, u);

            model.setRowCount(0);

            for (User user : list) {
                model.addRow(new Object[]{
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getUsername(),
                        user.getPassword(),
                        user.getRole(),
                        user.getStatus()
                });
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private int parseInt(String value){
        try { return Integer.parseInt(value); }
        catch (Exception e) { return 0; }
    }
}
