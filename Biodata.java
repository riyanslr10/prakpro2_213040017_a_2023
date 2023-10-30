package Pertemuan7;
// Modul 7 - Studi Kasus

// Daftar Isi Import
import Pertemuan7.TableModel;

import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;


public class Biodata extends JFrame {
    private void simpanFile(ArrayList<ArrayList<String>> data) {
        // Menampilkan pesan konfirmasi
        int konfirmasi = JOptionPane.showConfirmDialog(null, "Apakah anda ingin menyimpan file '.txt' ?",
        "Konfirmasi", JOptionPane.YES_NO_OPTION);
        
        if(konfirmasi == JOptionPane.YES_OPTION) {
            try {
                // Menulis lokasi file
                BufferedWriter writer = new BufferedWriter(new FileWriter("data.txt"));
            
                for(ArrayList<String> row : data) {
                    // Memisahkan beberapa data dengan melalui tab
                    for (String s : row) {
                        writer.write(s + "\t");
                    }
                    // Memindahkan data ke baris yang baru
                    writer.newLine();
                }
                writer.close();
                JOptionPane.showMessageDialog(null, "Data berhasil disimpan ke file ini");
            }
            catch(IOException e) {
                JOptionPane.showMessageDialog(null, "Data gagal disimpan ke file ini.");
                e.printStackTrace();
            }
        }
    }

    public Biodata () {
        // Mengatur layout frame menjadi null
        this.setLayout(null);

        // Membuat label untuk Nama
        JLabel labelNama = new JLabel("Nama :");
        labelNama.setBounds(15, 40, 100, 10);

        // Membuat TextField untuk Nama
        JTextField textFieldNama = new JTextField();
        textFieldNama.setBounds(15, 60, 350, 30);

        // Membuat label untuk Nomor HP
        JLabel labelNomorHP = new JLabel("Nomor HP :");
        labelNomorHP.setBounds(15, 100, 100, 10);

        // Membuat TextField untuk Nomor HP
        JTextField textFieldNomorHP = new JTextField();
        textFieldNomorHP.setBounds(15, 120, 350, 30);

        // Membuat label untuk Jenis Kelamin
        JLabel labelRadio = new JLabel("Jenis Kelamin:");
        labelRadio.setBounds(15, 160, 350, 10);

        // Membuat RadioButton "Laki-Laki"
        JRadioButton radioButton1 = new JRadioButton("Laki-Laki");
        radioButton1.setBounds(15, 180, 350, 30);

        // Membuat RadioButton "Perempuan"
        JRadioButton radioButton2 = new JRadioButton("Perempuan");
        radioButton2.setBounds(15, 210, 350, 30);

        // Membuat label "Alamat"
        JLabel labelAlamat = new JLabel("Alamat:");
        labelAlamat.setBounds(15, 240, 350, 30);

        // Membuat TextArea "Alamat"
        JTextArea textAreaAlamat = new JTextArea(5, 20);
        textAreaAlamat.setBounds(15, 270, 350, 100);

        // Membuat ButtonGroup untuk mengelola RadioButton
        ButtonGroup bg = new ButtonGroup();
        bg.add(radioButton1);
        bg.add(radioButton2);

        // Membuat tombol "Simpan"
        JButton buttonAdd = new JButton("Simpan");
        buttonAdd.setBounds(15, 380, 100, 40);

        // Membuat tombol "Edit"
        JButton buttonUpdate = new JButton("Edit");
        buttonUpdate.setBounds(120, 380, 100, 40);

        // Membuat tombol "Hapus"
        JButton buttonDelete = new JButton("Hapus");
        buttonDelete.setBounds(225, 380, 100, 40);

        // Membuat tombol "Simpan ke File"
        JButton buttonSave = new JButton("Simpan Ke File");
        buttonSave.setBounds(330, 380, 120, 40);

        // Membuat tabel JTable
        javax.swing.JTable table = new JTable();
        JScrollPane scrollableTable = new JScrollPane(table);
        scrollableTable.setBounds(15, 430, 560, 150);

        // Membuat model tabel custom (TableModel)
        TableModel tableModel = new TableModel();
        table.setModel(tableModel);

        // Menambahkan ActionListener pada tombol "Simpan"
        buttonAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Mengambil data dari inputan Nama, Nomor HP, Jenis Kelamin, dan Alamat
                String jenisKelamin = "";
                String nama = textFieldNama.getText();
                String nomorHP = textFieldNomorHP.getText();
                String alamat = textAreaAlamat.getText();

                if(radioButton1.isSelected() && radioButton2.isSelected()) {
                    JOptionPane.showMessageDialog(Biodata.this, "Hanya satu jenis kelamin yang dipilih!",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                else if (radioButton1.isSelected()) {
                    jenisKelamin = radioButton1.getText();
                }
                else if(radioButton2.isSelected()) {
                    jenisKelamin = radioButton2.getText();
                }
                else {
                    // Memberikan notifikasi jika jenis kelamin belum dipilih
                    JOptionPane.showMessageDialog(Biodata.this, "Silahkan pilih jenis kelamin terlebih dahulu",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validasi data kosong
                if(nama.isEmpty() || nomorHP.isEmpty() || alamat.isEmpty()) {
                    JOptionPane.showMessageDialog(Biodata.this, "Semua input harus diisi!",
                    "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    // Mengonfirmasi sebelum melakukan penyimpanan data
                    int konfirmasi = JOptionPane.showConfirmDialog(Biodata.this, "Apakah anda yakin ingin menyimpan data?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);

                    if(konfirmasi == JOptionPane.YES_OPTION) {
                        // Menambahkan data ke model tabel
                        tableModel.add(new ArrayList<>(Arrays.asList(nama, nomorHP, jenisKelamin, alamat)));
                        // Membersihkan input setelah penyimpanan
                        textFieldNama.setText("");
                        textFieldNomorHP.setText("");
                        textAreaAlamat.setText("");
                        labelRadio.setText("Jenis Kelamin: ");
                    }
                }
            }
        });

        // Menambahkan ActionListener pada tombol "Edit"
        buttonUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                if(row == -1) {
                    JOptionPane.showMessageDialog(null, "Silahkan pilih data yang akan diubah");
                    return;
                }

                // Mengambil data dari inputan Nama, Nomor HP, Jenis Kelamin, dan Alamat
                String jenisKelamin = "";
                String nama = textFieldNama.getText();
                String nomorHP = textFieldNomorHP.getText();
                String alamat = textAreaAlamat.getText();

                if(radioButton1.isSelected() && radioButton2.isSelected()) {
                    JOptionPane.showMessageDialog(Biodata.this, "Hanya satu jenis kelamin dipilih!",
                            "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                else if (radioButton1.isSelected()) {
                    jenisKelamin = radioButton1.getText();
                }
                else if(radioButton2.isSelected()) {
                    jenisKelamin = radioButton2.getText();
                }
                else {
                    // Memberikan notifikasi jika jenis kelamin belum dipilih
                    JOptionPane.showMessageDialog(Biodata.this, "Pilih jenis kelamin terlebih dahulu!",
                            "Kesalahan", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validasi data kosong
                if(nama.isEmpty() || nomorHP.isEmpty() || alamat.isEmpty()) {
                    JOptionPane.showMessageDialog(Biodata.this, "Semua input harus diisi!",
                            "Kesalahan", JOptionPane.ERROR_MESSAGE);
                }
                else {
                    // Mengonfirmasi sebelum melakukan pengubahan data
                    int konfirmasi = JOptionPane.showConfirmDialog(Biodata.this, "Apakah anda yakin ingin mengubah data?",
                            "Konfirmasi", JOptionPane.YES_NO_OPTION);

                    if(konfirmasi == JOptionPane.YES_OPTION) {
                        // Menambahkan data ke model tabel
                        ArrayList<String> data = new ArrayList<String>(Arrays.asList(nama, nomorHP, jenisKelamin, alamat));
                        tableModel.update(data, row);

                        // Membersihkan input setelah penyimpanan
                        textFieldNama.setText("");
                        textFieldNomorHP.setText("");
                        textAreaAlamat.setText("");
                        labelRadio.setText("Jenis Kelamin: ");
                    }
                }
            }
        });

        // Menambahkan ActionListener pada tombol "Hapus"
        buttonDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if(selectedRow >= 0) {
                    int option = JOptionPane.showConfirmDialog(Biodata.this, "Apakah anda yakin ingin menghapus data?",
                    "Konfirmasi", JOptionPane.YES_NO_OPTION);
                    
                    if(option == JOptionPane.YES_OPTION) {
                        // Memanggil metode deleteRow untuk menghapus isi data tabel
                        tableModel.delete(selectedRow);
                        JOptionPane.showMessageDialog(Biodata.this, "Data berhasil dihapus!", "Informasi", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                else {
                    JOptionPane.showMessageDialog(Biodata.this, "Pilih baris yang akan dihapus!",
                    "Konfirmasi", JOptionPane.WARNING_MESSAGE);
                }
            }
        });

        // Menambahkan ActionListener pada tombol "Simpan ke File"
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simpanFile(tableModel.getData());
            }
        });

        // Mengurusi jelang penutupan frame
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int konfirmasi = JOptionPane.showConfirmDialog(Biodata.this,
                        "Anda yakin ingin keluar?", "Konfirmasi",JOptionPane.YES_NO_OPTION);
                if(konfirmasi == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                else {
                    setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
        });

        // Menambahkan komponen-komponen ke frame
        this.add(buttonAdd);
        this.add(buttonUpdate);
        this.add(buttonDelete);
        this.add(buttonSave);
        this.add(labelNama);
        this.add(textFieldNama);
        this.add(labelNomorHP);
        this.add(textFieldNomorHP);
        this.add(labelRadio);
        this.add(radioButton1);
        this.add(radioButton2);
        this.add(labelAlamat);
        this.add(textAreaAlamat);
        this.add(scrollableTable);

        // Mengatur ukuran dan tata letak frame
        this.setSize(600, 650);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Biodata m = new Biodata();
                m.setVisible(true);
            }
        });
    }
}