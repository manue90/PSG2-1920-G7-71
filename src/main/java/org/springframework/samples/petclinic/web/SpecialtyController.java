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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Specialty;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 */
@Controller
@RequestMapping("/vets/{vetId}")
public class SpecialtyController {

	private static final String	VIEWS_SPECIALTY_CREATE_OR_UPDATE_FORM	= "specialties/createOrUpdateSpecialtyForm";

	private final ClinicService clinicService;

	@Autowired
	public SpecialtyController(ClinicService clinicService) {
		this.clinicService = clinicService;
	}
	
	@ModelAttribute("vet")
	public Vet findVet(@PathVariable("vetId") int vetId) {
		return this.clinicService.findVetById(vetId);
	}
	
	@InitBinder("vet")
	public void initOwnerBinder(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("vetId");
	}
	
	//Crear Specialty

	@ModelAttribute("spec")
	public Collection<Specialty> populateSpecialtyTypes() {
		return this.clinicService.findSpecTypes();
	}
		
	@GetMapping(value = "/specialty/new")
	public String initCreationForm(Vet vet, ModelMap model) {
		Specialty specialty = new Specialty();
		vet.addSpecialty(specialty);
		model.put("specialty", specialty);
		return SpecialtyController.VIEWS_SPECIALTY_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/specialty/new")
	public String addSpecialty(Vet vet, Specialty specialty, final BindingResult result,ModelMap model) {
						
		if (StringUtils.hasLength(specialty.getName()) && specialty.isNew() && vet.getSpecialty(specialty.getName(), true) != null) {
			result.rejectValue("name", "duplicate", "already exists");
		}
		if (result.hasErrors()) {
			model.put("specialty", specialty);
			return SpecialtyController.VIEWS_SPECIALTY_CREATE_OR_UPDATE_FORM;
		}
		else {
			List<Specialty> l = this.clinicService.findAllSpec();

			for(Specialty s:l)
			{
				if(s.getName().toLowerCase().equals(specialty.getName().toLowerCase()))
					specialty.setId(s.getId());
			}

			vet.addSpecialty(specialty);
			this.clinicService.saveSpecialty(specialty);
			return "redirect:/vets/{vetId}";
		}
	}	
	
	@GetMapping(value = "/specialty/{specialtyId}/edit")
	public String initUpdateForm(@PathVariable("specialtyId")int id, ModelMap model) {
		Specialty specialty = this.clinicService.findSpecById(id);
		model.put("specialty", specialty);
		return SpecialtyController.VIEWS_SPECIALTY_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping(value = "/specialty/{specialtyId}/edit")
	public String updateSpecialty(@PathVariable("specialtyId")int id, Specialty specialty, Vet vet, final BindingResult result,ModelMap model) {
						
		if (result.hasErrors()) {
			model.put("specialty", specialty);
			return SpecialtyController.VIEWS_SPECIALTY_CREATE_OR_UPDATE_FORM;
		}
		else {
			Specialty spec = this.clinicService.findSpecById(id);

			List<Specialty> l = this.clinicService.findAllSpec();

			for(Specialty s:l)
			{
				if(s.getName().toLowerCase().equals(specialty.getName().toLowerCase()))
					spec.setId(s.getId());
			}
			
			spec.setName(specialty.getName());
			
			vet.addSpecialty(spec);
			this.clinicService.saveSpecialty(spec);
			return "redirect:/vets/{vetId}";
		}
	}	
	
}
