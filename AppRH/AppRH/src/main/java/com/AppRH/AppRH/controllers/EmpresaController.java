package com.AppRH.AppRH.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppRH.AppRH.repository.EmpresaRepository;
import com.AppRH.AppRH.models.Empresa; 

@Controller
public class EmpresaController {
	
	@Autowired
	private EmpresaRepository er;
	
	@RequestMapping("/cadastrarEmpresa")
	public String form () {
		return "empresas/form-empresa";
	}
	
	@RequestMapping(value="/cadastrarEmpresa", method=RequestMethod.POST)
	public String form (@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique campos inválidos");
			return"redirect:/cadastrarEmpresa";
		}
		
		this.er.save(empresa);
		attributes.addFlashAttribute("mensagem", "Empresa adicionada com sucesso");
		return"redirect:/cadastrarEmpresa";
		
	}
	
	@RequestMapping("/empresas")
	public ModelAndView listaEmpresas() {
		ModelAndView mv = new ModelAndView("empresas/lista-empresa");
		Iterable<Empresa> empresas = this.er.findAll();
		mv.addObject("empresas", empresas);
		return mv;
	}
	
	@RequestMapping("/deletarEmpresa")
	public String deletarEmpresa(long id) {
		Empresa empresa = this.er.findById(id);
		this.er.delete(empresa);
		return "redirect:/empresas";
	}
	
	
	//UPDATE
	@RequestMapping("/editarEmpresa")
	public ModelAndView editarEmpresa(long id) {
		Empresa empresa = this.er.findById(id);
		ModelAndView mv = new ModelAndView("empresas/update-empresa");
		mv.addObject("empresa", empresa);
		return mv;
	}
	
	@RequestMapping(value="/editarEmpresa", method=RequestMethod.POST)
	public String atualizarEmpresa(@Valid Empresa empresa, BindingResult result, RedirectAttributes attributes) {
		this.er.save(empresa);
		return "redirect:/empresas";
	}
	
}
