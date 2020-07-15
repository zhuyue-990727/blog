package com.zy.web.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.zy.po.Type;
import com.zy.service.TypeService;

@Controller
@RequestMapping("/admin")
public class TypeController {

	@Autowired
	private TypeService typeService;
	
	@GetMapping("/types")
	public String types(@PageableDefault(size=3,sort= {"id"},direction=Sort.Direction.DESC) Pageable pageable,Model model) {
	model.addAttribute("page",typeService.listType(pageable));
	return "admin/types";
}
	
	@GetMapping("/types/input")
	public String input(Model model) {
		model.addAttribute("type",new Type());
		return "/admin/types-input";
	}
	
	//编辑
	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable Long id,Model model) {
		model.addAttribute("type",typeService.getType(id));
		return "/admin/types-input";
	}
	
	
	
	
	
	
	
	@PostMapping("/types")
	public String post(@Valid Type type,BindingResult result,RedirectAttributes attributes) {
		Type type1=typeService.getTypeByName(type.getName());
		if(type1!=null) {
			result.rejectValue("name", "NameError","该分类不能重复添加");
		}
		if(result.hasErrors()){
			return "/admin/types-input";
		}
		Type t=typeService.saveType(type);
		if(t==null) {
			//添加不成功
			attributes.addFlashAttribute("message","操作新增失败");
		}else {
			//添加成功
			attributes.addFlashAttribute("message","操作新增成功");

		}
		return "redirect:/admin/types";
	}
	
	
	@PostMapping("/types/{id}")
	public String editPost(@Valid Type type,BindingResult result,@PathVariable Long id,RedirectAttributes attributes) {
		Type type1=typeService.getTypeByName(type.getName());
		if(type1!=null) {
			result.rejectValue("name", "NameError","该分类不能重复添加");
		}
		if(result.hasErrors()){
			return "/admin/types-input";
		}
		Type t=typeService.updateType(id,type);
		if(t==null) {
			//添加不成功
			attributes.addFlashAttribute("message","更新失败");
		}else {
			//添加成功
			attributes.addFlashAttribute("message","更新成功");

		}
		return "redirect:/admin/types";
	}
	
	//删除
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Long id,RedirectAttributes attributes) {
		typeService.deleteType(id);
		attributes.addFlashAttribute("message","删除成功");
		return "redirect:/admin/types";
	}
	
	
	
	
	
	
	
}
