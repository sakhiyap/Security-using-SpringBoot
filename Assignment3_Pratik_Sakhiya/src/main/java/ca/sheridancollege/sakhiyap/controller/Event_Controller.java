package ca.sheridancollege.sakhiyap.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import ca.sheridancollege.sakhiyap.beans.BookEvent;
import ca.sheridancollege.sakhiyap.beans.User;
import ca.sheridancollege.sakhiyap.repository.Event_Repository;
import ca.sheridancollege.sakhiyap.repository.Event_Security_Repository;
import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class Event_Controller {

	private Event_Repository eventRepo;

	@GetMapping("/")
	public String Main() {
		return "Home.html";
	}

	@GetMapping("/AddTicket")
	public String goBookTickets(Model model) {
		ArrayList<User> usr = eventSecRepo.getGuestName();
		model.addAttribute("bkevent", new BookEvent());
		model.addAttribute("usr",usr);
		return "BookTickets.html";
	}

	@PostMapping("/procAddTicket") // Navigate to add
	public String processAddTicket(@ModelAttribute BookEvent bkevent, Model model) {

		eventRepo.addTicketEvent(bkevent);
		model.addAttribute("bkevent", new BookEvent());
		return "BookTickets.html";

	}

	@GetMapping("/ViewData")
	public String goViewTicketsData(Authentication auth, Model model) {
		List<BookEvent> bkeventList;
		
		String currentUserName = auth.getName();
		List<String> userRoles = new ArrayList<String>();
		
		for(GrantedAuthority ga:auth.getAuthorities()) {
			userRoles.add(ga.getAuthority());
		}
		
		if(userRoles.contains("ROLE_GUEST")) {
			bkeventList=eventRepo.getTicketByUserName(currentUserName);
			
			double subTotal=0;
			int numTicketTotal=0;
			double ticketTotal=0;
			
			for(int a = 0; a <bkeventList.size(); a++) {
				
				ticketTotal=bkeventList.get(a).getPrice();
				numTicketTotal=bkeventList.get(a).getNumTicket();
				
				subTotal+= ticketTotal*numTicketTotal;
				
			}
			
			double tax = (subTotal*13)/100;
			double Total = subTotal + tax ;
			
			model.addAttribute("bkeventList",bkeventList);
			model.addAttribute("numTicketTotal", numTicketTotal);
			model.addAttribute("ticketTotal", ticketTotal);
			model.addAttribute("subTotal", subTotal);
			model.addAttribute("tax", tax);
			model.addAttribute("total", Total);
			
		}
		
		else {
			bkeventList = eventRepo.getBookTicketEvent();
			model.addAttribute("bkeventList",bkeventList);
		}
		
		

		return "ViewTicketsData.html";
	}

	@GetMapping("/edit/{id}")
	public String editBookTicket(@PathVariable int id, Model model) {

		BookEvent bkevent = eventRepo.getTicketsById(id);
		ArrayList <User> usr = eventSecRepo.getGuestName();
		// send the data to edit page
		model.addAttribute("usr",usr);
		model.addAttribute("bkevent", bkevent);
		System.out.println(model);
		return "edit.html";
	}

	@PostMapping("/editTicket")
	public String processEdit(@ModelAttribute BookEvent bkevent) {
		
		// update the drink in my database
		eventRepo.editTicket(bkevent);
		//System.out.print(bkevent);
		
		// go back to another page
		return "redirect:/ViewData";
	}

	@GetMapping("/delete/{id}")
	public String DeleteTicket(@PathVariable int id, Model model) {

		BookEvent bkevent = eventRepo.getTicketsById(id);

		// send the data to edit page
		model.addAttribute("bkevent", bkevent);
		BookEvent b = bkevent;
		eventRepo.deleteTicketById(b);

		return "redirect:/ViewData";
	}

	@GetMapping("/login")
	public String login() {
		return "login.html";
	}

	@GetMapping("/access-denied")
	public String accessDenied() {
		return "/access-denied.html";
	}

	@GetMapping("/register")
	public String goRegistration() {
		return "Registration.html";
	}

	@Autowired
	@Lazy
	private Event_Security_Repository eventSecRepo;

//	@PostMapping("/register")
//	public String doRegistration(@RequestParam String username,
//			@RequestParam String password) {
//		return "redirect:/";
//	}
	
	
	@PostMapping("/register")
	public String regUser(@RequestParam String name, @RequestParam String password) {
		eventSecRepo.addUser(name, password);
		long userId = eventSecRepo.findUserAccount(name).getUserId();
		eventSecRepo.addRole(userId, 1);
		return "redirect:/";
	}

	@GetMapping("/ViewPageUser")
	public String goViewUsersData(Model model) {
		ArrayList<BookEvent> bkeventList = eventRepo.getBookTicketEvent();
		model.addAttribute("eventdb", bkeventList);

		return "ViewPageUser.html";
	}

}
