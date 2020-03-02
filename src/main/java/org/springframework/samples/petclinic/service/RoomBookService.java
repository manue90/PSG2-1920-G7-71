/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.RoomBook;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.RoomBookRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Alejandro Gómez
 * @author Alejandro Fuentes
 */
@Service
public class RoomBookService {

	private PetRepository petRepository;

	private OwnerRepository ownerRepository;

	private RoomBookRepository roomBookRepository;

	@Autowired
	public RoomBookService(PetRepository petRepository, VetRepository vetRepository, OwnerRepository ownerRepository,
			RoomBookRepository roomBookRepository) {
		this.petRepository = petRepository;
		this.ownerRepository = ownerRepository;
		this.roomBookRepository = roomBookRepository;
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(int id) throws DataAccessException {
		return ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
		return ownerRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveOwner(Owner owner) throws DataAccessException {
		ownerRepository.save(owner);
	}

	@Transactional
	public void saveRoomBook(RoomBook roomBook) throws DataAccessException {
		roomBookRepository.save(roomBook);
	}

//	@Transactional(readOnly = true)
//	public Pet findPetById(int id) throws DataAccessException {
//		return petRepository.findById(id);
//	}

	@Transactional
	public void savePet(Pet pet) throws DataAccessException {
		petRepository.save(pet);
	}


//	public Collection<RoomBook> findVisitsByPetId(int petId) {
//		return roomBookRepository.findByPetId(petId);
//	}
//	

}
