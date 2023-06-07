package users;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import Exceptions.BadRequestException;
import users.payload.UserRegistrationPayload;

@Service
public class UsersService {
	@Autowired
	private UsersRepository usersRepo;

	public User create(UserRegistrationPayload u) {
		usersRepo.findByEmail(u.getEmail()).ifPresent(user -> {
			throw new BadRequestException("email" + u.getEmail() + "already in use");
		});
		User newUser = new User(u.getEmail(), u.getName(), u.getSurname());
		return usersRepo.save(newUser);
	}

	public List<User> find(int page, int size, String sortBy) {
		if (size < 0)
			size = 10;
		if (size > 100)
			size = 100;
		Pageable pageable = PageRequest.of(page, 10, Sort.by(sortBy));
		return usersRepo.findAll(pageable).getContent();
	}

	public User findById(UUID id) throws Exception {
		return usersRepo.findById(id).orElseThrow(() -> new Exception("Utente non trovato!"));
	}

	public User findByIdAndUpdate(UUID id, User u) throws Exception {
		User found = this.findById(id);

		found.setId(id);
		found.setName(u.getName());
		found.setSurname(u.getSurname());
		found.setEmail(u.getEmail());

		return usersRepo.save(found);
	}

	public void findByIdAndDelete(UUID id) throws Exception {
		User found = this.findById(id);
		usersRepo.delete(found);
	}

}
