package Application.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Application.Entity.Student;
import Application.Service.StudentService;

@Controller
public class AppController {
	@Autowired
	private StudentService service;

	@RequestMapping("/")
	public String viewHomepage(Model model) {
		List<Student> listStudents = service.listAll();
		model.addAttribute("listStudents", listStudents);
		return "index";
	}

	@RequestMapping("/search")
	public ModelAndView search(@RequestParam String keyword) {
		List<Student> result = service.search(keyword);
		ModelAndView mav = new ModelAndView("search");
		mav.addObject("result", result);
		return mav;
	}

	/*
	 * @RequestMapping("/search") public ModelAndView search(@RequestParam String
	 * keyword) {     List<Student> result = service.search(keyword);
	 *     ModelAndView mav = new ModelAndView("search");
	 *     mav.addObject("result", result);       return mav;     }
	 */

	@RequestMapping("/new")
	public String showNewStudentInfo(Model model) {
		Student student = new Student();
		model.addAttribute("student", student);
		return "new_student";
	}

	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public String saveStudentInfo(@ModelAttribute("student") Student student) {
		service.save(student);
		return "redirect:/";
	}

	@RequestMapping("/edit/{id}")
	public ModelAndView editStudentInfo(@PathVariable(name = "id") int id) {
		ModelAndView mav = new ModelAndView("edit_student");
		Student student = service.get(id);
		mav.addObject("student", student);

		return mav;
	}

	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String updateStudentInfo(@ModelAttribute("student") Student student) {
		service.save(student);
		return "redirect:/";
	}

	@RequestMapping("/delete/{id}")
	public String deleteStudentInfo(@PathVariable(name = "id") int id) {
		service.delete(id);
		return "redirect:/";
	}

}
