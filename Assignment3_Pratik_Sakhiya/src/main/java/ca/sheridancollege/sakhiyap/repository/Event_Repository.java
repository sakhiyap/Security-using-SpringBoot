package ca.sheridancollege.sakhiyap.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.sakhiyap.beans.BookEvent;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class Event_Repository {

	private NamedParameterJdbcTemplate jdbc;
	

	public void addTicketEvent(BookEvent bkevent) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "INSERT INTO ticket_event (dates,userName,price,numTicket,email,discount) VALUES ( :dt, :name,  :pr, :ntic,:em, :dic)";

		parameters.addValue("dt", bkevent.getDates().toString());
		parameters.addValue("name", bkevent.getUserName());
		parameters.addValue("pr", bkevent.getPrice());
		parameters.addValue("ntic", bkevent.getNumTicket());
		parameters.addValue("em", bkevent.getEmail());
		parameters.addValue("dic", bkevent.isDiscount()); 

		jdbc.update(query, parameters);

		// jdbc.update(query, new HashMap());

	}

	public ArrayList<BookEvent> getBookTicketEvent() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM ticket_event";

		ArrayList<BookEvent> bkeventList = (ArrayList<BookEvent>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<BookEvent>(BookEvent.class));

		return bkeventList;
	}

	public BookEvent getTicketsById(int id) {
		ArrayList<BookEvent> bkevent = new ArrayList<BookEvent>();

		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM ticket_event WHERE id=:tic";

		parameters.addValue("tic", id);
		List<Map<String, Object>> rows = jdbc.queryForList(query, parameters);
		for (Map<String, Object> row : rows) {
			BookEvent b = new BookEvent();
			b.setId((Integer) row.get("id"));
			b.setDates(row.get("dates").toString());
			b.setUserName((String) row.get("userName"));
			b.setEmail((String) row.get("email"));
			b.setPrice((Double) row.get("price"));
			b.setNumTicket((Integer) row.get("numTicket"));
			b.setDiscount((Boolean) row.get("discount"));

			bkevent.add(b);
		}
		if (bkevent.size() > 0) {
			return bkevent.get(0);
		} else {
			return null;
		}

	}
	
	public ArrayList<BookEvent> getTicketByUserName(String currentUserName){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM ticket_event WHERE userName=:currentUserName";
		parameters.addValue("currentUserName", currentUserName);
		
		ArrayList<BookEvent> bkeventList = (ArrayList<BookEvent>) jdbc.query(query, parameters,
				new BeanPropertyRowMapper<BookEvent>(BookEvent.class));

		return bkeventList;
		
	}
	
	public void editTicket(BookEvent bkevent) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		
		String query = "UPDATE ticket_event SET  dates=:dt, userName=:name, price=:pr, numTicket=:ntic, email=:em, discount=:dic Where id=:Id";

		parameters.addValue("Id", bkevent.getId());
		parameters.addValue("dt", bkevent.getDates().toString());
		parameters.addValue("name", bkevent.getUserName());
		parameters.addValue("pr", bkevent.getPrice());
		parameters.addValue("ntic", bkevent.getNumTicket());
		parameters.addValue("em", bkevent.getEmail());
		parameters.addValue("dic", bkevent.isDiscount());
		
		jdbc.update(query, parameters);

	}

	public void deleteTicketById(BookEvent bkevent) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = "Delete from ticket_event Where id=:id";

		parameters.addValue("id", bkevent.getId());
		jdbc.update(query, parameters);

	}
	
	

}
