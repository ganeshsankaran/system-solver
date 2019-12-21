package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import com.google.gson.Gson;

import hello.SystemOfEquations;
import hello.Solution;
import hello.SystemSolverService;

@Controller
public class SystemSolverController {

  @Autowired
  private SystemSolverService systemSolverService;
  @Autowired
  private Gson gson;

  @RequestMapping("/")
  public String index(Model model, SystemOfEquations system) {
    model.addAttribute("system", new SystemOfEquations());

    String json = systemSolverService.solve(system);
    Solution solution = gson.fromJson(json, Solution.class);
    model.addAttribute("solution", solution);

    return "index";
  }

}