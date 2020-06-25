package it.polito.tdp.ufo.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import it.polito.tdp.ufo.model.AnnoAvvistamento;
import it.polito.tdp.ufo.model.Arco;
import it.polito.tdp.ufo.model.Sighting;

public class SightingsDAO {
	
	public List<Sighting> getSightings() {
		String sql = "SELECT * FROM sighting" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Sighting> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				list.add(new Sighting(res.getInt("id"),
						res.getTimestamp("datetime").toLocalDateTime(),
						res.getString("city"), 
						res.getString("state"), 
						res.getString("country"),
						res.getString("shape"),
						res.getInt("duration"),
						res.getString("duration_hm"),
						res.getString("comments"),
						res.getDate("date_posted").toLocalDate(),
						res.getDouble("latitude"), 
						res.getDouble("longitude"))) ;
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<AnnoAvvistamento> tendina(){
		String sql = "select year(datetime) as anno, count(*) as conta " + 
				"from sighting " + 
				"where country=\"us\" " + 
				"group by year(datetime) " + 
				"order by anno";
		
		List<AnnoAvvistamento> tendina = new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				tendina.add(new AnnoAvvistamento(res.getInt("anno"), res.getInt("conta")));
			}
			
			conn.close();
			return tendina ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}
	
	public List<String> vertici(Integer anno){
		String sql ="select distinct state " + 
				"from sighting " + 
				"where country='us' and year(datetime) =? " + 
				"order by state";
		List<String> vertici = new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				vertici.add(res.getString("state"));
			}
			
			conn.close();
			return vertici ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Set<Arco> archi(Integer anno){
		String sql ="select s.state stato1, s1.state stato2, s.datetime data1, s1.datetime data2 " + 
				"from sighting s, sighting s1 " + 
				"where year(s.datetime) = ? " + 
				"and year(s1.datetime) = ? " + 
				"and s.country=\"us\" " + 
				"and s1.country=\"us\" " + 
				"and s.state!=s1.state " ;
		
		Set<Arco> archi = new HashSet<>();
		
		try {
			Connection conn = DBConnect.getConnection() ;
			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setInt(1, anno);
			st.setInt(2, anno);
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				String stato1 = res.getString("stato1");
				String stato2 = res.getString("stato2");
				LocalDateTime data1= res.getTimestamp("data1").toLocalDateTime();
				LocalDateTime data2= res.getTimestamp("data2").toLocalDateTime();
				
				
				if(data1.isBefore(data2)){
					archi.add(new Arco(stato1, stato2));
				}else {
					archi.add(new Arco(stato2, stato1));
				}
			}
			
			conn.close();
			return archi ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
		
	}

}
