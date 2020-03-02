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
package org.springframework.samples.petclinic.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.RoomBook;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.service.RoomBookService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author Alejandro Gómez
 * @author Alejandro Fuentes

 */
@Controller
public class RoomBookController {
	
	private final RoomBookService roomBookService;
	
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@ModelAttribute("roomBook")
	public RoomBook loadPetWithVisit(@PathVariable("petId") int petId, @PathVariable("ownwerId") int ownerId) {
		Pet pet = this.roomBookService.findPetById(petId);
		RoomBook roomBook = new RoomBook();
		pet.addVisit(roomBook);
		return roomBook;
	}

	// Spring MVC calls method loadPetWithVisit(...) before initNewVisitForm is called
	@GetMapping(value = "/owners/*/pets/{petId}/visits/new")
	public String initNewVisitForm(@PathVariable("petId") int petId, Map<String, Object> model) {
		return "pets/createOrUpdateVisitForm";
	}

	// Spring MVC calls method loadPetWithVisit(...) before processNewVisitForm is called
	@PostMapping(value = "/owners/{ownerId}/pets/{petId}/visits/new")
	public String processNewVisitForm(@Valid Visit visit, BindingResult result) {
		if (result.hasErrors()) {
			return "pets/createOrUpdateVisitForm";
		}
		else {
			this.clinicService.saveVisit(visit);
			return "redirect:/owners/{ownerId}";
		}
	}

	@GetMapping(value = "/owners/*/pets/{petId}/visits")
	public String showVisits(@PathVariable int petId, Map<String, Object> model) {
		model.put("visits", this.clinicService.findPetById(petId).getVisits());
		return "visitList";
	
}