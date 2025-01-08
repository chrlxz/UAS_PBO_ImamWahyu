package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.sql.*;

public class StudentController {

    @FXML
    private TextField txtNama, txtNIM, txtNoTelepon;

    @FXML
    private ComboBox<String> cmbFakultas;

    @FXML
    private TableView<Student> tableView;

    @FXML
    private TableColumn<Student, Integer> colNo;

    @FXML
    private TableColumn<Student, String> colNama, colNIM, colFakultas, colNoTelepon;

    private Connection connection;

    @FXML
    public void initialize() {
        connectToDatabase();
        cmbFakultas.setItems(FXCollections.observableArrayList(
                "Fakultas Teknologi Industri", "Fakultas Matematika", "Fakultas Keguruan", "Fakultas Keagamaan"
        ));
        loadTableData();
    }

    private void connectToDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:student.db");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void addStudent(ActionEvent event) {
        String nama = txtNama.getText();
        String nim = txtNIM.getText();
        String fakultas = cmbFakultas.getValue();
        String noTelepon = txtNoTelepon.getText();

        try {
            String query = "INSERT INTO mahasiswa (nama, nim, fakultas, no_telepon) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nama);
            preparedStatement.setString(2, nim);
            preparedStatement.setString(3, fakultas);
            preparedStatement.setString(4, noTelepon);
            preparedStatement.executeUpdate();

            clearFields();
            loadTableData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void deleteStudent(ActionEvent event) {
        Student selectedStudent = tableView.getSelectionModel().getSelectedItem();
        if (selectedStudent != null) {
            try {
                String query = "DELETE FROM mahasiswa WHERE id = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setInt(1, selectedStudent.getId());
                preparedStatement.executeUpdate();

                loadTableData();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void clearFields() {
        txtNama.clear();
        txtNIM.clear();
        txtNoTelepon.clear();
        cmbFakultas.getSelectionModel().clearSelection();
    }

    private void loadTableData() {
        ObservableList<Student> studentList = FXCollections.observableArrayList();

        try {
            String query = "SELECT * FROM mahasiswa";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            int no = 1;
            while (resultSet.next()) {
                studentList.add(new Student(
                        no++,
                        resultSet.getInt("id"),
                        resultSet.getString("nama"),
                        resultSet.getString("nim"),
                        resultSet.getString("fakultas"),
                        resultSet.getString("no_telepon")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        tableView.setItems(studentList);
    }
}
