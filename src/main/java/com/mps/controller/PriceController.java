package com.mps.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.mps.model.DiwaliModel;
import com.mps.model.DiwaliPriceModel;
import com.mps.utility.ExtractPrice;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/dw")
public class PriceController {

	@Autowired
	private ExtractPrice extractPrice;
	@GetMapping()
	public String homePage(){
		return "Homepage";
	}
	@GetMapping("/price")
	public String checkPrice()
	{
		return "CheckPrice";
	}
	
	@GetMapping("/check")
	public String seePrice(@ModelAttribute DiwaliModel modelNumber,Model att)
	{
		try {
			DiwaliPriceModel findPrice = extractPrice.findPrice(modelNumber.getModelNumber());
			att.addAttribute("diwaliPrice", findPrice);
			return "CheckPrice";
		} catch (Exception e) {
			att.addAttribute("diwaliPrice", new DiwaliPriceModel("Model Not Found!!","","",""));
			return "CheckPrice";
		}
	}
	@GetMapping("/checkPriceAtHome")
	public String checkPrice(@ModelAttribute DiwaliModel modelNumber,Model att)
	{
		try {
			DiwaliPriceModel findPrice = extractPrice.findPrice(modelNumber.getModelNumber());
			att.addAttribute("diwaliPrice", findPrice);
			return "Homepage";
		} catch (Exception e) {
			att.addAttribute("diwaliPrice", new DiwaliPriceModel("Model Not Found!!","","",""));
			return "Homepage";
		}
	}
	@GetMapping("/list")
	public String seeWholeList(Model model,@RequestParam(name="message",required = false)String message) throws IOException {
		List<DiwaliPriceModel> wholeList = extractPrice.getWholeList();
		model.addAttribute("wholeList",wholeList);
		model.addAttribute("message",message);
		return "WholeList";
	}

	@GetMapping("/edit")
	public String editRow(@RequestParam("modelName") String modelName, Model model, RedirectAttributes attributes) throws IOException {
		DiwaliPriceModel oneModel = extractPrice.findPrice(modelName);
		if (oneModel!=null || !("Model Not Found!!").equalsIgnoreCase(oneModel.getModelName())) {
			model.addAttribute("model", oneModel);
			return "ModelEdit";
		} else {
			attributes.addAttribute("message","Model not found to edit!!");
			return "redirect:list";
		}
	}

	@PostMapping("/update")
	public String updateModel(@ModelAttribute DiwaliPriceModel diwaliPriceModel, RedirectAttributes attributes) throws IOException {
		boolean flag = extractPrice.updatePrice(diwaliPriceModel);
		if (flag){
			attributes.addAttribute("message",diwaliPriceModel.getModelName()+" updated succesfully");
		} else {
			attributes.addAttribute("message",diwaliPriceModel.getModelName()+" not updated");
		}
		return "redirect:list";
	}

	@GetMapping("/delete")
	public String deleteModel(@RequestParam("modelName") String modelName, Model model, RedirectAttributes attributes) throws IOException {
		boolean flag = extractPrice.removeModel(modelName);
		if (flag){
			attributes.addAttribute("message",modelName+" deleted succesfully");
		} else {
			attributes.addAttribute("message",modelName+" not removed");
		}
		return "redirect:list";
	}

	@GetMapping("/add")
	public String addModel(Model model,@RequestParam(name = "message",required = false)String message){
		model.addAttribute("message",message);
		return "AddModel";
	}
	@PostMapping("/save")
	public String saveModel(@ModelAttribute DiwaliPriceModel diwaliPriceModel,RedirectAttributes attributes) throws IOException {
		boolean flag = extractPrice.addModel(diwaliPriceModel);
		if (flag){
			attributes.addAttribute("message"," Added succesfully");
		} else {
			attributes.addAttribute("message"," not added");
		}
		return "redirect:add";
	}
}
