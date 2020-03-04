/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.samples.petclinic.service;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers Also a placeholder
 * for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
@Service
public class ClinicService {

	private PetRepository	petRepository;

	private VetRepository	vetRepository;

	private OwnerRepository	ownerRepository;

	private VisitRepository	visitRepository;


	@Autowired
	public ClinicService(final PetRepository petRepository, final VetRepository vetRepository, final OwnerRepository ownerRepository, final VisitRepository visitRepository) {
		this.petRepository = petRepository;
		this.vetRepository = vetRepository;
		this.ownerRepository = ownerRepository;
		this.visitRepository = visitRepository;
	}

	@Transactional(readOnly = true)
	public Collection<PetType> findPetTypes() throws DataAccessException {
		return this.petRepository.findPetTypes();
	}

	@Transactional(readOnly = true)
	public Owner findOwnerById(final int id) throws DataAccessException {
		return this.ownerRepository.findById(id);
	}

	@Transactional(readOnly = true)
	public Collection<Owner> findOwnerByLastName(final String lastName) throws DataAccessException {
		return this.ownerRepository.findByLastName(lastName);
	}

	@Transactional
	public void saveOwner(final Owner owner) throws DataAccessException {
		this.ownerRepository.save(owner);
	}

	@Transactional
	public void saveVisit(final Visit visit) throws DataAccessException {
		this.visitRepository.save(visit);
	}

	@Transactional(readOnly = true)
	public Pet findPetById(final int id) throws DataAccessException {
		return this.petRepository.findById(id);
	}

	@Transactional
	public void savePet(final Pet pet) throws DataAccessException {
		this.petRepository.save(pet);
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "vets")
	public Collection<Vet> findVets() throws DataAccessException {
		return this.vetRepository.findAll();
	}


	public Collection<Visit> findVisitsByPetId(int petId) {
		return visitRepository.findVisitsByPetId(petId);

	/* Encontrar vet por su Id */
	@Transactional(readOnly = true)
	public Vet findVetById(final int id) throws DataAccessException {
		return this.vetRepository.findById(id);

	}

	/* Guardar vet */
	@Transactional
	public void saveVet(final Vet vet) throws DataAccessException {
		this.vetRepository.save(vet);
	}

	
	@Transactional
	public void deleteOwner(Owner owner) throws DataAccessException {
		ownerRepository.delete(owner);
	}
	
	@Transactional
	public void deleteVet(Vet vet) throws DataAccessException {
		vetRepository.delete(vet);
	}
	
	@Transactional(readOnly = true)
	public Vet findVetById(int id) throws DataAccessException {
		return vetRepository.findById(id);
	}
	
	@Transactional
	public void deleteVisit(Visit visit) throws DataAccessException {
		visitRepository.delete(visit);
	}
	
	@Transactional(readOnly = true)
	public Visit findVisitById(int id) throws DataAccessException {
		return visitRepository.findById(id);

	/* Añadir Especialidad */
	@Transactional(readOnly = true)
	public Collection<Specialty> findSpecTypes() throws DataAccessException
	{
		return this.vetRepository.findSpecTypes();
	}

	public Collection<Visit> findVisitsByPetId(final int petId) {
		return this.visitRepository.findByPetId(petId);
	}

	@Transactional
	public void deletePet(final Pet pet) throws DataAccessException {
		this.petRepository.delete(pet);
	}

	public void saveSpecialty(final Specialty a) {
		this.vetRepository.save(a);
	}
		
	public List<Specialty> findAllSpec()
	{
		return this.vetRepository.findAllSpec();
	}
	
	public Specialty findSpecById(int id)
	{
		return this.vetRepository.findSpecById(id);
	}
}
