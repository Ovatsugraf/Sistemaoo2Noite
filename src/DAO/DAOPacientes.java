package DAO;

import sistemaoo2noite.Pacientes;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
//import java.sql.* paga todos os imports sql de cima
//IPC - para acessar o banco de dados é preciso add biblioteca do Mysql clicando com  botão direito
//do mouse no projeto,propriedades,biblioteca,JDBC do MySQL

public class DAOPacientes {//DAO DATA ACCESS OBJECT - pesquisar

    private static Connection conn;//serve de acesso ao banco
    private static Statement comando;// serve para aplicar instruções sql na concxão
    //private static Banco banco;
    
    public static void conectar() {

        try {
            conn = Banco.conexao();
            comando = conn.createStatement();
          
        } catch (SQLException ex) {
            Logger.getLogger(DAOPacientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void desconectar() {

        try {
            conn.close();
            comando.close();
            

        } catch (SQLException ex) {
            Logger.getLogger(DAOPacientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void incluir(Pacientes p1) {
        conectar();
        String sql = "insert into pacientes values(default,'"
                //+ p1.getPRONTUARIO() + "','"
                + p1.getNOME_PACIENTE() + "','" //QUANDO FOR TEXTO COLOCA(STRING,VARCHAR) APÓSTROFO''
                + p1.getNOME_MAE() +"','"
                + p1.getDATA_NASCIMENTO() +"','"
                + p1.getENDERECO_PACIENTE() +"','"
                + p1.getRG_PACIENTE() +"','"
                + p1.getCPF_PACIENTE() +"','"
                + p1.getEMAIL_PACIENTE() +"','"
                + p1.getCONTATO_PACIENTE() + "');";
        JOptionPane.showMessageDialog(null,sql);//caixa de mensagem
        //System.out.println(sql);

        try {
            comando.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"inserido com sucesso!");//+ default,p1.getPRONTUARIO());
             sql = ("select * from pacientes value default");
            JOptionPane.showMessageDialog(null,sql);
            /*sql = "select from pacientes where PRONTUARIO ="  " ;";
            JOptionPane.showMessageDialog(null,"Seu prontuário é :" );*/
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"erro na inserção!");
            System.out.println(ex);
        }

        desconectar();

    }

    public static Pacientes excluir(Pacientes p) {
         conectar();
        //Medicos med = new Medicos();
        try {
            
            PreparedStatement pst = conn.prepareStatement("delete from pacientes where PRONTUARIO = ?");
            pst.setInt(1,p.getPRONTUARIO());
            pst.execute();
            JOptionPane.showMessageDialog(null, "Excluído com sucesso !");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir dados !");
            System.out.println(ex);
        }

        desconectar();
        return p;

    }

    public static Pacientes pesquisar(int PRONTUARIO) {
        conectar();
        Pacientes p1 = new Pacientes();
        //int num_pessoa = codigo;
        String sql = ("select* from pacientes where PRONTUARIO = " + PRONTUARIO + " ;");
        
        ResultSet rs;
        //System.out.println(sql);

        try {
            //ResultSet rs = comando.executeQuery(sql);//rs = recebe
            rs = comando.executeQuery(sql);
            if (rs.last())
            {
                rs.first();//posiciona o cursor no primeiro da lista(pesquisar)
                p1.setPRONTUARIO(rs.getInt("PRONTUARIO"));
                p1.setNOME_PACIENTE(rs.getString("NOME_PACIENTE"));
                p1.setNOME_MAE(rs.getString("NOME_MAE"));
                p1.setDATA_NASCIMENTO(rs.getString("DATA_NASCIMENTO"));
                p1.setENDERECO_PACIENTE(rs.getString("ENDERECO_PACIENTE"));
                p1.setRG_PACIENTE(rs.getString("RG_PACIENTE"));
                p1.setCPF_PACIENTE(rs.getString("CPF_PACIENTE"));
                p1.setEMAIL_PACIENTE(rs.getString("EMAIL_PACIENTE"));
                p1.setCONTATO_PACIENTE(rs.getString("CONTATO_PACIENTE"));
                
                //JnptionPane.ShowMessageDialog(null,p1.getNOME_PACIENTE());
                //System.out.println(p1.getNOME_PACIENTE());
                
            }
            else{
                JOptionPane.showMessageDialog(null,"não encontrado");
                //P = null;
                p1 = null;
            }
            
            
        } catch (SQLException ex) {
            p1 = null;
           JOptionPane.showMessageDialog(null,"erro buscando o registro!");
           System.out.println(ex);
        }

        desconectar();

        return p1;

    }
    
    public List<Pacientes> exibir(int PRONTUARIO){
        conectar();
        
        ArrayList<Pacientes> ListaPac = new ArrayList<Pacientes>();
        try{
            String  sql = ("select* from pacientes ");//where PRONTUARIO = " + PRONTUARIO + " ;");
            ResultSet rs = comando.executeQuery(sql);
            
                    while(rs.next ()){
                
                         Pacientes p1 = new Pacientes();
                        
                        p1.setPRONTUARIO(rs.getInt("PRONTUARIO"));
                        p1.setNOME_PACIENTE(rs.getString("NOME_PACIENTE"));
                        p1.setNOME_MAE(rs.getString("NOME_MAE"));
                        p1.setDATA_NASCIMENTO(rs.getString("DATA_NASCIMENTO"));
                        p1.setENDERECO_PACIENTE(rs.getString("ENDERECO_PACIENTE"));
                        p1.setRG_PACIENTE(rs.getString("RG_PACIENTE"));
                        p1.setCPF_PACIENTE(rs.getString("CPF_PACIENTE"));
                        p1.setEMAIL_PACIENTE(rs.getString("EMAIL_PACIENTE"));
                        p1.setCONTATO_PACIENTE(rs.getString("CONTATO_PACIENTE"));
                        ListaPac.add(p1);
                    }
            
        }catch(SQLException ex){
        
        }
      
        desconectar();
        //JOptionPane.showMessageDialog(null, ListaPac);
          return ListaPac;
    }
   
    public static boolean alterar(Pacientes p1) {
        conectar();
        String sql = "update pacientes set NOME_PACIENTE = '" + p1.getNOME_PACIENTE()
                +"',CPF_PACIENTE ='"+ p1.getCPF_PACIENTE()
                +"',RG_PACIENTE ='"+ p1.getRG_PACIENTE()
                +"',DATA_NASCIMENTO ='"+ p1.getDATA_NASCIMENTO()
                +"',EMAIL_PACIENTE ='"+ p1.getEMAIL_PACIENTE()
                +"',CONTATO_PACIENTE ='"+ p1.getCONTATO_PACIENTE()
                +"',ENDERECO_PACIENTE ='"+ p1.getENDERECO_PACIENTE()
                +"',NOME_MAE ='"+ p1.getNOME_MAE()
                +"' where PRONTUARIO ="+p1.getPRONTUARIO()
                +";";
       
        try {
            comando.executeUpdate(sql);
            JOptionPane.showMessageDialog(null,"alterado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"erro na alteração!");
        }

        desconectar();
        return true;

    }

    //public static List<Pessoa> lerLista() {
        //conectar();

        //desconectar();
       // List<Pessoa> listaPessoa = new ArrayList<Pessoa>();
       // return listaPessoa;

    //}

    //public static boolean salvarLista(List<Pessoa> listaPessoa) {
        //conectar();

        //desconectar();
        //return true;

    //}

}
