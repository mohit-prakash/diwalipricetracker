package com.mps.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mps.model.DiwaliModel;
import com.mps.utility.ExtractPrice;

@Controller
@RequestMapping("/diwali")
public class PriceController {

	@GetMapping("/price")
	public String checkPrice(@RequestParam(name="message",required = false)String msg,Model model)
	{
		model.addAttribute("message", msg);
		return "CheckPrice";
	}
	
	@GetMapping("/check")
	public String seePrice(@ModelAttribute DiwaliModel modelNumber,RedirectAttributes att)
	{
		try {
			String[] findPrice = ExtractPrice.findPrice(modelNumber.getModelNumber());
			String msg="Item= "+findPrice[0]+"     ||     "+"MRP= "+findPrice[1]+"     ||     "+"DP= "+findPrice[2]+"     ||     "+"SRP= "+findPrice[3]; 
			att.addAttribute("message", msg);
			return "redirect:price";
		} catch (Exception e) {
			att.addAttribute("message", "Model Not Found!!");
			return "redirect:price";
		}
	}
}
