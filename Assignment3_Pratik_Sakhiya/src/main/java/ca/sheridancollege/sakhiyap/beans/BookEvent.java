package ca.sheridancollege.sakhiyap.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookEvent {

		private int id;
		private String dates;
		
		private String userName;
		
		private String email;
		private double price;
			private double[] prices= {30,40,50};
		
		private int numTicket;
		private boolean discount;
		
		
		
	
}
