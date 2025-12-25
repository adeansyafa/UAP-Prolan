package org.example;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;

public class LibraryApp {

    static final String FILE_BUKU = "buku.txt";
    static final String PASS_ADMIN = "admin123";
    static final String PASS_SISWA = "siswa123";

    static boolean isAdmin = false;
    static ArrayList<Buku> daftarBuku = new ArrayList<>();

    public static void main(String[] args) {
        muatData();
        SwingUtilities.invokeLater(Splash::new);
    }

    // ================= MODEL =================
    static class Buku {
        String id, judul, penulis;
        int tahun;
        boolean dipinjam = false;
        String peminjam = "-";
        LocalDate kembali;

        Buku(String i, String j, String p, int t) {
            id = i; judul = j; penulis = p; tahun = t;
        }
    }

    // ================= SPLASH =================
    static class Splash extends JWindow {
        Splash() {
            setSize(400, 250);
            setLocationRelativeTo(null);
            add(new GradientPanel("SISTEM PERPUSTAKAAN"));
            setVisible(true);

            new Timer(2000, e -> {
                ((Timer) e.getSource()).stop();
                dispose();
                new LoginRole().setVisible(true);
            }).start();
        }
    }

    // ================= LOGIN ROLE =================
    static class LoginRole extends JFrame {
        LoginRole() {
            setTitle("Login");
            setSize(400, 300);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            GradientPanel p = new GradientPanel("PILIH LOGIN");
            p.setLayout(new GridLayout(3, 1, 15, 15));
            p.setBorder(BorderFactory.createEmptyBorder(50, 60, 50, 60));

            JButton admin = btn("Admin");
            JButton siswa = btn("Siswa");

            admin.addActionListener(e -> {
                isAdmin = true;
                new LoginPassword().setVisible(true);
                dispose();
            });

            siswa.addActionListener(e -> {
                isAdmin = false;
                new LoginPassword().setVisible(true);
                dispose();
            });

            p.add(new JLabel());
            p.add(admin);
            p.add(siswa);
            add(p);
        }
    }

    // ================= LOGIN PASSWORD =================
    static class LoginPassword extends JFrame {
        LoginPassword() {
            setTitle("Password");
            setSize(350, 200);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            GradientPanel p = new GradientPanel("PASSWORD");
            p.setLayout(new GridLayout(3, 1, 10, 10));
            p.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

            JPasswordField pass = new JPasswordField();
            JButton masuk = btn("Masuk");

            masuk.addActionListener(e -> {
                String in = new String(pass.getPassword());
                if ((isAdmin && in.equals(PASS_ADMIN)) || (!isAdmin && in.equals(PASS_SISWA))) {
                    new Dashboard().setVisible(true);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Password salah");
                }
            });

            p.add(new JLabel("Masukkan Password"));
            p.add(pass);
            p.add(masuk);
            add(p);
        }
    }

    // ================= DASHBOARD =================
    static class Dashboard extends JFrame {
        Dashboard() {
            setTitle("Dashboard");
            setSize(500, 400);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            GradientPanel p = new GradientPanel(isAdmin ? "ADMIN" : "SISWA");
            p.setLayout(new GridLayout(4, 1, 15, 15));
            p.setBorder(BorderFactory.createEmptyBorder(40, 80, 40, 80));

            JButton data = btn("📚 Data Buku");
            JButton laporan = btn("📊 Laporan");
            JButton keluar = btn("❌ Keluar");

            data.addActionListener(e -> {
                new FrameBuku().setVisible(true);
                dispose();
            });

            laporan.addActionListener(e -> new FrameLaporan().setVisible(true));
            laporan.setEnabled(isAdmin);

            keluar.addActionListener(e -> {
                simpanData();
                System.exit(0);
            });

            p.add(new JLabel());
            p.add(data);
            p.add(laporan);
            p.add(keluar);
            add(p);
        }
    }

    // ================= FRAME BUKU (SEARCH AKTIF) =================
    static class FrameBuku extends JFrame {
        DefaultTableModel model;
        JTable table;
        TableRowSorter<DefaultTableModel> sorter;

        FrameBuku() {
            setTitle("Data Buku");
            setSize(950, 500);
            setLocationRelativeTo(null);
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            model = new DefaultTableModel(
                    new String[]{"ID", "Judul", "Penulis", "Tahun", "Status"}, 0);
            table = new JTable(model);

            sorter = new TableRowSorter<>(model);
            table.setRowSorter(sorter);

            refresh();

            // ===== SEARCH PANEL =====
            JPanel panelSearch = new JPanel(new BorderLayout(10, 10));
            panelSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JLabel lblSearch = new JLabel("Cari Buku:");
            JTextField tfSearch = new JTextField();

            panelSearch.add(lblSearch, BorderLayout.WEST);
            panelSearch.add(tfSearch, BorderLayout.CENTER);

            tfSearch.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                void cari() {
                    String text = tfSearch.getText();
                    if (text.trim().isEmpty()) {
                        sorter.setRowFilter(null);
                    } else {
                        sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
                    }
                }

                public void insertUpdate(javax.swing.event.DocumentEvent e) { cari(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { cari(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { cari(); }
            });

            // ===== BUTTON =====
            JButton tambah = btn("Tambah");
            JButton edit = btn("Edit");
            JButton hapus = btn("Hapus");
            JButton pinjam = btn("Pinjam");
            JButton kembalikan = btn("Kembalikan");
            JButton kembali = btn("Kembali");

            tambah.setEnabled(isAdmin);
            edit.setEnabled(isAdmin);
            hapus.setEnabled(isAdmin);
            pinjam.setEnabled(!isAdmin);
            kembalikan.setEnabled(!isAdmin);

            tambah.addActionListener(e -> new FormBuku(this, null).setVisible(true));

            edit.addActionListener(e -> {
                int v = table.getSelectedRow();
                if (v < 0) return;
                int m = table.convertRowIndexToModel(v);
                new FormBuku(this, daftarBuku.get(m)).setVisible(true);
            });

            hapus.addActionListener(e -> {
                int v = table.getSelectedRow();
                if (v < 0) return;
                int m = table.convertRowIndexToModel(v);
                daftarBuku.remove(m);
                refresh();
            });

            pinjam.addActionListener(e -> {
                int v = table.getSelectedRow();
                if (v < 0) return;
                int m = table.convertRowIndexToModel(v);
                Buku b = daftarBuku.get(m);

                if (b.dipinjam) return;

                String tgl = JOptionPane.showInputDialog("Pinjam sampai (YYYY-MM-DD)");
                b.dipinjam = true;
                b.peminjam = "Siswa";
                b.kembali = LocalDate.parse(tgl);
                refresh();
            });

            kembalikan.addActionListener(e -> {
                int v = table.getSelectedRow();
                if (v < 0) return;
                int m = table.convertRowIndexToModel(v);
                Buku b = daftarBuku.get(m);

                b.dipinjam = false;
                b.peminjam = "-";
                b.kembali = null;
                refresh();
            });

            kembali.addActionListener(e -> {
                new Dashboard().setVisible(true);
                dispose();
            });

            JPanel bawah = new JPanel();
            bawah.add(tambah);
            bawah.add(edit);
            bawah.add(hapus);
            bawah.add(pinjam);
            bawah.add(kembalikan);
            bawah.add(kembali);

            add(panelSearch, BorderLayout.NORTH);
            add(new JScrollPane(table), BorderLayout.CENTER);
            add(bawah, BorderLayout.SOUTH);
        }

        void refresh() {
            model.setRowCount(0);
            daftarBuku.sort(Comparator.comparing(b -> b.judul));
            for (Buku b : daftarBuku) {
                model.addRow(new Object[]{
                        b.id, b.judul, b.penulis, b.tahun,
                        b.dipinjam ? "Dipinjam" : "Tersedia"
                });
            }
            simpanData();
        }
    }

    // ================= FORM BUKU =================
    static class FormBuku extends JFrame {
        JTextField id = new JTextField(), judul = new JTextField(),
                penulis = new JTextField(), tahun = new JTextField();
        FrameBuku parent;
        Buku edit;

        FormBuku(FrameBuku p, Buku b) {
            parent = p;
            edit = b;

            setTitle("Form Buku");
            setSize(350, 300);
            setLocationRelativeTo(null);
            setLayout(new GridLayout(5, 2, 10, 10));
            ((JComponent) getContentPane())
                    .setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

            add(new JLabel("ID"));
            add(id);
            add(new JLabel("Judul"));
            add(judul);
            add(new JLabel("Penulis"));
            add(penulis);
            add(new JLabel("Tahun"));
            add(tahun);

            JButton simpan = btn("Simpan");
            add(new JLabel());
            add(simpan);

            if (edit != null) {
                id.setText(edit.id);
                judul.setText(edit.judul);
                penulis.setText(edit.penulis);
                tahun.setText(String.valueOf(edit.tahun));
            } else {
                id.setText("B" + (daftarBuku.size() + 1));
            }

            simpan.addActionListener(e -> {
                if (edit == null) {
                    daftarBuku.add(new Buku(
                            id.getText(),
                            judul.getText(),
                            penulis.getText(),
                            Integer.parseInt(tahun.getText())
                    ));
                } else {
                    edit.id = id.getText();
                    edit.judul = judul.getText();
                    edit.penulis = penulis.getText();
                    edit.tahun = Integer.parseInt(tahun.getText());
                }
                parent.refresh();
                dispose();
            });
        }
    }

    // ================= LAPORAN =================
    static class FrameLaporan extends JFrame {
        FrameLaporan() {
            setTitle("Laporan");
            setSize(600, 300);
            setLocationRelativeTo(null);

            DefaultTableModel m = new DefaultTableModel(
                    new String[]{"Judul", "Peminjam", "Kembali"}, 0);
            JTable t = new JTable(m);

            for (Buku b : daftarBuku)
                if (b.dipinjam)
                    m.addRow(new Object[]{b.judul, b.peminjam, b.kembali});

            add(new JScrollPane(t));
        }
    }

    // ================= FILE =================
    static void muatData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_BUKU))) {
            String s;
            while ((s = br.readLine()) != null) {
                String[] d = s.split(",");
                Buku b = new Buku(d[0], d[1], d[2], Integer.parseInt(d[3]));
                if (d.length > 4 && d[4].equals("1")) {
                    b.dipinjam = true;
                    b.peminjam = d[5];
                    b.kembali = LocalDate.parse(d[6]);
                }
                daftarBuku.add(b);
            }
        } catch (Exception ignored) {}
    }

    static void simpanData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_BUKU))) {
            for (Buku b : daftarBuku) {
                bw.write(b.id + "," + b.judul + "," + b.penulis + "," + b.tahun + "," +
                        (b.dipinjam ? "1," + b.peminjam + "," + b.kembali : "0"));
                bw.newLine();
            }
        } catch (Exception ignored) {}
    }

    // ================= UTIL =================
    static JButton btn(String t) {
        JButton b = new JButton(t);
        b.setFont(new Font("Segoe UI", Font.BOLD, 14));
        return b;
    }

    // ================= HEADER TENGAH =================
    static class GradientPanel extends JPanel {
        String text;

        GradientPanel(String t) {
            text = t;
        }

        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;

            g2.setPaint(new GradientPaint(
                    0, 0, new Color(33, 150, 243),
                    0, getHeight(), new Color(156, 39, 176)));
            g2.fillRect(0, 0, getWidth(), getHeight());

            g2.setFont(new Font("Segoe UI", Font.BOLD, 22));
            g2.setColor(Color.WHITE);

            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(text)) / 2;
            int y = fm.getAscent() + 15;

            g2.drawString(text, x, y);
        }
    }
}
