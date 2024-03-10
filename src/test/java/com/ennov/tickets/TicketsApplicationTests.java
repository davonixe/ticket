package com.ennov.tickets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import com.ennov.tickets.model.Role;
import com.ennov.tickets.model.Ticket;
import com.ennov.tickets.model.TicketStatus;
import com.ennov.tickets.model.User;
import com.ennov.tickets.repo.TicketRepository;
import com.ennov.tickets.repo.UserRepository;
import com.ennov.tickets.service.TicketServiceImpl;
import com.ennov.tickets.service.UserServiceImpl;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class TicketsApplicationTests {

	@Test
	void contextLoads() {
	}

	@Mock
	UserRepository userRepository;

	@Mock
	TicketRepository ticketRepository;

	@InjectMocks
	UserServiceImpl userServiceImpl = new UserServiceImpl();

	@InjectMocks
	TicketServiceImpl ticketServiceImpl = new TicketServiceImpl();

	@BeforeEach
	public void init() {
		MockitoAnnotations.openMocks(this);
	}

	@Test
	public void testFindAllUsers() {
		List<User> uList = new ArrayList<User>();
		List<Ticket> tickets = new ArrayList<Ticket>();
		Set<Role> roles = new HashSet<>();

		User user1 = new User();
		user1.setId((long) 1);
		user1.setUsername("David");
		user1.setEmail("david@duck.com");
		user1.setRoles(roles);
		user1.setTickets(tickets);

		User user2 = new User();
		user2.setId((long) 2);
		user2.setUsername("Anne");
		user2.setEmail("anne@duck.com");
		user2.setRoles(roles);
		user2.setTickets(tickets);

		User user3 = new User();
		user3.setId((long) 3);
		user3.setUsername("Carole");
		user3.setEmail("carole@duck.com");
		user3.setRoles(roles);
		user3.setTickets(tickets);

		uList.add(user1);
		uList.add(user2);
		uList.add(user3);

		Mockito.lenient().when(userRepository.findAll()).thenReturn(uList);

		List<User> userlList = userServiceImpl.getAllUsers();

		assertEquals(3, userlList.size());
		verify(userRepository, times(1)).findAll();
		}

		@Test
		public void testSaveTicket() {
			Ticket ticket = new Ticket();
			Set<Role> roles = new HashSet<>();

			User user1 = new User();
			user1.setId((long) 1);
			user1.setUsername("David");
			user1.setEmail("david@duck.com");
			user1.setRoles(roles);
			user1.setTickets(new ArrayList<Ticket>());

			ticket.setId((long) 1);
			ticket.setTitle("Lancer les tests");
			ticket.setDescription("Lancer les tests unitaires avec Mockito et JUnit");
			ticket.setStatus(TicketStatus.DOING);
			ticket.setUser(user1);

			ticketServiceImpl.createNewTicket(ticket);
			verify(ticketRepository, times(1)).save(ticket);
		}

}
