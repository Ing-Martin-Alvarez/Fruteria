//Martin Alvarez Salazar 19460870
//Contraseña de Admin: ElMelon
package fruteriaelmelon;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class FruteriaElMelon extends javax.swing.JFrame {
    String pre,canti,produ,sql;
    String url = "jdbc:mysql://localhost:3306/fruteria?userTimezone=true&serverTimezone=UTC&useSSL=false";
    String username = "root";
    String password = "root"; 
    String llave;
    
    public FruteriaElMelon() {
        initComponents();
        desabilitar();
    }
    private void desabilitar(){
        btninsertar.setEnabled(false);
        txtid.setEnabled(false);
        txtnombre.setEnabled(false);
        txtcantidad.setEnabled(false);
        txtprecio.setEnabled(false);
        btnmodificar.setEnabled(false);
        btnconsultauno.setEnabled(false);
        btncomprar.setEnabled(false);
        btneliminar.setEnabled(false);
        btnconsultar.setEnabled(false);
        txtcontra.setEnabled(true);
        txtcantidaddeseada.setEnabled(false);
    }
    private void cliente(){
        btnconsultauno.setEnabled(true);
        btncomprar.setEnabled(true);
        btnconsultar.setEnabled(true);
        txtid.setEnabled(true);
        txtcantidad.setEnabled(false);
        btneliminar.setEnabled(false);
        btnmodificar.setEnabled(false);
        btninsertar.setEnabled(false);
        txtnombre.setEnabled(false);
        txtprecio.setEnabled(false);
        txtcantidaddeseada.setEnabled(true);
    }
    private void admin(){
        if(txtcontra.getText().equals("ElMelon")){
        btninsertar.setEnabled(true);
        txtid.setEnabled(true);
        txtnombre.setEnabled(true);
        txtcantidad.setEnabled(true);
        txtprecio.setEnabled(true);
        btnmodificar.setEnabled(true);
        btnconsultauno.setEnabled(true);
        btncomprar.setEnabled(true);
        btneliminar.setEnabled(true);
        btnconsultar.setEnabled(true);
        txtcantidaddeseada.setEnabled(true);
        txtcontra.setText("");
        }
        else{
            JOptionPane.showMessageDialog(null, "Contraseña incorrecta");
        }
    }
    private void insertar(){
        try{
            //crea un objeto de conexion a la bases de datos
            Connection conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion Exitosa.....");
            //crear un statement cin el objeto de conexion
            Statement statement = conexion.createStatement();
        if(txtid.getText().equals("")){
            produ = txtnombre.getText();
            canti = txtcantidad.getText();
            pre = txtprecio.getText();
                sql = "INSERT INTO inventario (producto, cantidad, costo)"
                        + "VALUES ("
                        + "\"" + produ + "\","
                        + "\"" + canti + "\","
                        + "\"" + pre + "\")";
                System.out.println("Instrucciones SQL: "+sql);
                statement.executeUpdate(sql);            
        }
        else{
            JOptionPane.showMessageDialog(null, "Si vas a insertar un nuevo producto la id debe estar vacia debido a que se asigna automaticamente");
        }            
        }catch(SQLException e){
            System.out.println("Error de Conexion "+e);
        }
    }
    private void modificar(){
        try{
            //crea un objeto de conexion a la bases de datos
            Connection conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion Exitosa.....");
            //crear un statement cin el objeto de conexion
            Statement statement = conexion.createStatement(); 
            if(txtid.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese un ID valido para modificar");
            }
            else{
                JOptionPane.showMessageDialog(null, "Cada vez que modifique un dato de la tabla presionen nuevamente el boton consultar inventario para actualizar");
                produ = txtnombre.getText();
                canti = txtcantidad.getText();
                pre = txtprecio.getText();
                sql = "UPDATE inventario SET" + " producto=\""+ produ +"\""+" WHERE idinventario="+llave;
                statement.execute(sql);
                sql = "UPDATE inventario SET" + " cantidad=\""+ canti +"\""+" WHERE idinventario="+llave;
                statement.execute(sql);
                sql = "UPDATE inventario SET" + " costo=\""+ pre +"\""+" WHERE idinventario="+llave;
                statement.execute(sql);
            }
            }catch(SQLException e){
                System.out.println("Error de Conexion "+e);
            }        
    }
    private void consultaruno(){
        try{
            //crea un objeto de conexion a la bases de datos
            Connection conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion Exitosa.....");
            //crear un statement cin el objeto de conexion
            Statement statement = conexion.createStatement();
            if(txtid.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese un ID valido para consultar");
            }
            else{                
            llave = txtid.getText();
            ResultSet rs = statement.executeQuery("SELECT * FROM inventario WHERE idinventario="+llave ); 
                if(rs.next()==true){
                    txtnombre.setText(rs.getString("producto"));
                    txtcantidad.setText(rs.getString("cantidad"));
                    txtprecio.setText(rs.getString("costo"));
                }
            }
        }catch(SQLException e){
            System.out.println("Error de Conexion "+e);
        }
    }
    private void borrar(){
        try{
            //crea un objeto de conexion a la bases de datos
            Connection conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion Exitosa.....");
            //crear un statement cin el objeto de conexion
            Statement statement = conexion.createStatement(); 
            if(txtid.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese un ID valido para eliminar");
            }
            else{
            JOptionPane.showMessageDialog(null, "Cada vez que Elimine una fila de la tabla presionen nuevamente el boton consultar inventario para actualizar");
            sql = "DELETE FROM " + "inventario" + " WHERE idinventario = "+llave+"";
            statement.execute(sql); 
            }            
        }catch(SQLException e){
            System.out.println("Error de Conexion "+e);
        }
    }
    private void consulta(){
        try{
            //crea un objeto de conexion a la bases de datos
            Connection conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion Exitosa.....");
            //crear un statement cin el objeto de conexion
            Statement statement = conexion.createStatement();  
            String SQL = "SELECT * FROM inventario" ;
            ResultSet rs = statement.executeQuery(SQL);
            String[]titulos = {"ID"," Producto"," Cantidad"," Costo"}; 
            DefaultTableModel model = new DefaultTableModel(null, titulos);
            String[]fila = new String[4];
            while(rs.next()){
                fila[0] = rs.getString("idinventario");
                fila[1] = rs.getString("producto");
                fila[2] = rs.getString("cantidad");
                fila[3] = rs.getString("costo");
                model.addRow(fila);                
            }
            jtbconsulta.setModel(model);
        }catch(SQLException e){
            System.out.println("Error de Conexion "+e);            
        }
    }
    private void comprar(){
        try{
            //crea un objeto de conexion a la bases de datos
            Connection conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion Exitosa.....");
            //crear un statement cin el objeto de conexion
            Statement statement = conexion.createStatement();
            if(txtid.getText().equals("")){
                JOptionPane.showMessageDialog(null, "Ingrese un ID valido del producto que desea comprar y la cantidad a comprar");
            }
            else{
            ResultSet rs = statement.executeQuery("SELECT * FROM inventario WHERE idinventario="+llave ); 
                if(rs.next()==true){
                    produ = rs.getString("producto");
                    canti = rs.getString("cantidad");
                    pre = rs.getString("costo");
                    int cantidad = Integer.parseInt(canti);
                    int presio = Integer.parseInt(pre);
                    int deseo = Integer.parseInt(txtcantidaddeseada.getText());
                    if(deseo > cantidad){
                        JOptionPane.showMessageDialog(null, "No hay el suficiente producto en el inventario");
                    }
                    else{
                    int costo = presio * deseo;
                    int ncantidad = cantidad - deseo;
                    String candy = String.valueOf(ncantidad);
                    String presion = String.valueOf(costo);
                    sql = "UPDATE inventario SET" + " cantidad=\""+ candy +"\""+" WHERE idinventario="+llave;
                    statement.execute(sql);
                    JOptionPane.showMessageDialog(null, "Fruteria El Melon\n Av.Destiny #565 Colima COL. a 22/Mayo/2020 \nUstede a comprado: "+produ+ "\n En la cantidad de: "+deseo+"\n Con un costo de $: "+pre+" C/U \n Total a pagar en $: "+presion );
                    }
                }
            }
        }catch(SQLException e){
            System.out.println("Error de Conexion "+e);            
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbltitulo = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtcontra = new javax.swing.JTextField();
        btninsertar = new javax.swing.JButton();
        btnmodificar = new javax.swing.JButton();
        btneliminar = new javax.swing.JButton();
        btnconsultar = new javax.swing.JButton();
        btncomprar = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtid = new javax.swing.JTextField();
        txtnombre = new javax.swing.JTextField();
        txtcantidad = new javax.swing.JTextField();
        txtprecio = new javax.swing.JTextField();
        btnconsultauno = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        txtcantidaddeseada = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jtbconsulta = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximizedBounds(new java.awt.Rectangle(0, 0, 0, 0));
        setMaximumSize(new java.awt.Dimension(880, 600));
        setMinimumSize(new java.awt.Dimension(880, 600));
        getContentPane().setLayout(null);

        lbltitulo.setFont(new java.awt.Font("Dialog", 1, 48)); // NOI18N
        lbltitulo.setText("      Fruteria \"El Melon\"");
        lbltitulo.setToolTipText("");
        getContentPane().add(lbltitulo);
        lbltitulo.setBounds(140, 20, 580, 60);

        jButton1.setText("Administrador");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton1);
        jButton1.setBounds(20, 140, 140, 32);

        jButton2.setText("Cliente");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton2);
        jButton2.setBounds(20, 180, 140, 32);

        jLabel1.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        jLabel1.setText("Seleccione el tipo de cuenta");
        getContentPane().add(jLabel1);
        jLabel1.setBounds(30, 96, 240, 40);
        getContentPane().add(txtcontra);
        txtcontra.setBounds(250, 140, 210, 30);

        btninsertar.setText("Insertar");
        btninsertar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btninsertarActionPerformed(evt);
            }
        });
        getContentPane().add(btninsertar);
        btninsertar.setBounds(60, 250, 140, 32);

        btnmodificar.setText("Modificar");
        btnmodificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnmodificarActionPerformed(evt);
            }
        });
        getContentPane().add(btnmodificar);
        btnmodificar.setBounds(210, 250, 140, 32);

        btneliminar.setText("Elimnar");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });
        getContentPane().add(btneliminar);
        btneliminar.setBounds(670, 250, 130, 32);

        btnconsultar.setText("Consultar Inventario");
        btnconsultar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconsultarActionPerformed(evt);
            }
        });
        getContentPane().add(btnconsultar);
        btnconsultar.setBounds(360, 250, 150, 32);

        btncomprar.setText("Comprar");
        btncomprar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncomprarActionPerformed(evt);
            }
        });
        getContentPane().add(btncomprar);
        btncomprar.setBounds(520, 250, 140, 32);

        jLabel2.setText("Ingrese codigo de articulo:");
        getContentPane().add(jLabel2);
        jLabel2.setBounds(30, 306, 170, 20);

        jLabel3.setText("Nombre del articulo:");
        getContentPane().add(jLabel3);
        jLabel3.setBounds(30, 360, 170, 16);

        jLabel4.setText("Cantidad en Inventario");
        getContentPane().add(jLabel4);
        jLabel4.setBounds(30, 410, 130, 16);

        jLabel5.setText("Costo del articulo:");
        getContentPane().add(jLabel5);
        jLabel5.setBounds(30, 460, 160, 16);
        getContentPane().add(txtid);
        txtid.setBounds(30, 330, 170, 24);
        getContentPane().add(txtnombre);
        txtnombre.setBounds(30, 380, 170, 24);
        getContentPane().add(txtcantidad);
        txtcantidad.setBounds(30, 430, 170, 24);
        getContentPane().add(txtprecio);
        txtprecio.setBounds(30, 480, 170, 24);

        btnconsultauno.setText("Datos de un Articulo");
        btnconsultauno.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnconsultaunoActionPerformed(evt);
            }
        });
        getContentPane().add(btnconsultauno);
        btnconsultauno.setBounds(350, 470, 150, 40);

        jButton3.setText("Limpiar textos");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        getContentPane().add(jButton3);
        jButton3.setBounds(210, 470, 140, 40);

        jLabel6.setText("En caso de comprar Inserte cantidad:");
        getContentPane().add(jLabel6);
        jLabel6.setBounds(220, 410, 230, 20);
        getContentPane().add(txtcantidaddeseada);
        txtcantidaddeseada.setBounds(220, 430, 210, 24);

        jtbconsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Producto", "Canridad", "Costo"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jtbconsulta.setEnabled(false);
        jScrollPane1.setViewportView(jtbconsulta);
        if (jtbconsulta.getColumnModel().getColumnCount() > 0) {
            jtbconsulta.getColumnModel().getColumn(0).setResizable(false);
            jtbconsulta.getColumnModel().getColumn(1).setResizable(false);
            jtbconsulta.getColumnModel().getColumn(2).setResizable(false);
            jtbconsulta.getColumnModel().getColumn(3).setResizable(false);
        }

        getContentPane().add(jScrollPane1);
        jScrollPane1.setBounds(503, 293, 360, 230);

        jLabel7.setText("Contraseña:");
        getContentPane().add(jLabel7);
        jLabel7.setBounds(170, 140, 70, 30);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btninsertarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btninsertarActionPerformed
        insertar();
    }//GEN-LAST:event_btninsertarActionPerformed

    private void btnconsultaunoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconsultaunoActionPerformed
        consultaruno();
    }//GEN-LAST:event_btnconsultaunoActionPerformed

    private void btnmodificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnmodificarActionPerformed
       modificar();
    }//GEN-LAST:event_btnmodificarActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        txtid.setText("");
        txtnombre.setText("");
        txtcantidad.setText("");
        txtprecio.setText("");
        txtcantidaddeseada.setText("");
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        borrar();
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btnconsultarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnconsultarActionPerformed
        consulta();
    }//GEN-LAST:event_btnconsultarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        admin();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        cliente();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void btncomprarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncomprarActionPerformed
        comprar();
    }//GEN-LAST:event_btncomprarActionPerformed

    public static void main(String args[]) {
        String url = "jdbc:mysql://localhost:3306/fruteria?userTimezone=true&serverTimezone=UTC&useSSL=false";
        String username = "root";
        String password = "root";
        try{
            //crea un objeto de conexion a la bases de datos
            Connection conexion = DriverManager.getConnection(url, username, password);
            System.out.println("Conexion Exitosa.....");
            //crear un statement cin el objeto de conexion
            Statement statement = conexion.createStatement();
        }catch(SQLException e){
            System.out.println("Error de Conexion "+e);
        }
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FruteriaElMelon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FruteriaElMelon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FruteriaElMelon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FruteriaElMelon.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FruteriaElMelon().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btncomprar;
    private javax.swing.JButton btnconsultar;
    private javax.swing.JButton btnconsultauno;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btninsertar;
    private javax.swing.JButton btnmodificar;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jtbconsulta;
    private javax.swing.JLabel lbltitulo;
    private javax.swing.JTextField txtcantidad;
    private javax.swing.JTextField txtcantidaddeseada;
    private javax.swing.JTextField txtcontra;
    private javax.swing.JTextField txtid;
    private javax.swing.JTextField txtnombre;
    private javax.swing.JTextField txtprecio;
    // End of variables declaration//GEN-END:variables
}
