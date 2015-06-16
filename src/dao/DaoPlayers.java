package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import jdbc.ConexaoProd;
import jdbc.ConexaoDev;

public class DaoPlayers {

	private String nick;
	private int score;
	private long time;
	private static Connection conexao;
	
	/**
	 * Get all data of players and return it
	 * 
	 * @return
	 */
	public static ArrayList<DaoPlayers> getRecords() {
		
		conexao = ConexaoProd.getConexao();
		
		ArrayList<DaoPlayers> players = new ArrayList<DaoPlayers>();
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT nick, score, time FROM players ORDER BY score DESC LIMIT 10";
		try {
			stmt =  conexao.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {

				DaoPlayers d = new DaoPlayers();
				
				d.setNick(rs.getString("nick"));
				d.setScore(rs.getInt("score"));
				d.setTime(rs.getLong("time"));

				players.add(d);
			}
			conexao.close();
			
			return players;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Method to insert data
	 * 
	 * @param p
	 */
	public static void doInsert(DaoPlayers p) {
		
		int score = 0;
		if( (score = hasInserted(p.getNick())) > 0 ){
			// Then, we have to update it
			doUpdate(p, score);
			
		} else {
		
			conexao = ConexaoProd.getConexao();
			
			PreparedStatement stmt = null;
			
			String sql = "INSERT INTO players (nick, score, time) VALUES (?,?,?)";
			
			try {
				stmt = conexao.prepareStatement(sql);
				
				stmt.setString(1, p.getNick());
				stmt.setInt(2, p.getScore());
				stmt.setLong(3, p.getTime());
				
				stmt.execute();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Do update something alright exist
	 * 
	 * @param p
	 * @param score
	 */
	public static void doUpdate(DaoPlayers p, int score) {
		if( p.getScore() > score){
			conexao = ConexaoProd.getConexao();
			
			PreparedStatement stmt = null;
			String sql = "UPDATE players SET nick = ?, score = ?, time = ? WHERE nick = ?";
			
			try {
				stmt = conexao.prepareStatement(sql);
				
				stmt.setString(1, p.getNick());
				stmt.setInt(2, p.getScore());
				stmt.setLong(3, p.getTime());
				stmt.setString(4, p.getNick());
				
				stmt.execute();
				conexao.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}
	}
	
	/**
	 * Get a nick
	 * 
	 * @param nick
	 * @return
	 */
	private static int hasInserted(String nick){
		conexao = ConexaoProd.getConexao();
		
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT score FROM players WHERE nick = ?";
		try {
			stmt = conexao.prepareStatement(sql);
			stmt.setString(1, nick);
			
			rs = stmt.executeQuery();
			
			while(rs.next()){
				return rs.getInt("score");
			}
			
			conexao.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}
	
	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}
	
}
