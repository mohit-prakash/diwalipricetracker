package com.mps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mps.model.DiwaliModel;
import com.mps.model.DiwaliPriceModel;
import com.mps.utility.ExtractPrice;

@Controller
@RequestMapping("/diwali")
public class PriceController {

	@GetMapping("/price")
	public String checkPrice()
	{
		return "CheckPrice";
	}
	
	@GetMapping("/check")
	public String seePrice(@ModelAttribute DiwaliModel modelNumber,Model att)
	{
		try {
			DiwaliPriceModel findPrice = ExtractPrice.findPrice(modelNumber.getModelNumber()); 
			att.addAttribute("diwaliPrice", findPrice);
			return "CheckPrice";
		} catch (Exception e) {
			att.addAttribute("diwaliPrice", new DiwaliPriceModel("Model Not Found!!","","",""));
			return "CheckPrice";
		}
	}
}
