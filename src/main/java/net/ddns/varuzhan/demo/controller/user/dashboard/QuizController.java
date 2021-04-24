package net.ddns.varuzhan.demo.controller.user.dashboard;

import net.ddns.varuzhan.demo.dto.QuizAnswerDto;
import net.ddns.varuzhan.demo.model.ClassMaterial;
import net.ddns.varuzhan.demo.model.QuizQuestion;
import net.ddns.varuzhan.demo.model.User;
import net.ddns.varuzhan.demo.service.prototype.ClassMaterialService;
import net.ddns.varuzhan.demo.service.prototype.QuizQuestionService;
import net.ddns.varuzhan.demo.service.prototype.UserGroupInfoService;
import net.ddns.varuzhan.demo.service.prototype.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class QuizController {
    private final UserService userService;
    private final QuizQuestionService quizQuestionService;
    private final UserGroupInfoService userGroupInfoService;
    private final ClassMaterialService classMaterialService;


    public QuizController(UserService userService, QuizQuestionService quizQuestionService, UserGroupInfoService userGroupInfoService, ClassMaterialService classMaterialService) {
        this.userService = userService;
        this.quizQuestionService = quizQuestionService;
        this.userGroupInfoService = userGroupInfoService;
        this.classMaterialService = classMaterialService;
    }

    @GetMapping("/user/dashboard/classMaterials/{materialId}/quiz")
    public String getRandomQuestion(@PathVariable String materialId, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName()
                + " " + user.getMiddleName()
                + " " + user.getLastName();
        model.addAttribute("full_name", fullName);
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        model.addAttribute("material", materialById);
        if (materialById == null) return "redirect:/error";
        if (!userGroupInfoService.getGroupInfoByUser(user).getGroupInfo().equals(materialById.getManagersGroupsSubjects().getGroupInfo()))
            return "redirect:/error";
        QuizQuestion quizQuestion = quizQuestionService.getRandomQuestion(materialById);
        model.addAttribute("question", quizQuestion);
        QuizAnswerDto quizAnswerDto = new QuizAnswerDto();
        quizAnswerDto.setQuestionToken(quizQuestion.getQuestionToken());
        model.addAttribute("quizAnswer", quizAnswerDto);
        return "user/dashboard/quiz/quizView";
    }

    @PostMapping("/user/dashboard/classMaterials/{materialId}/quiz")
    public String checkQuizAnswer(@PathVariable String materialId, @ModelAttribute(name = "quizAnswer") QuizAnswerDto quizAnswerDto, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.getUserByEmail(authentication.getName());
        String fullName = user.getFirstName()
                + " " + user.getMiddleName()
                + " " + user.getLastName();
        ClassMaterial materialById = classMaterialService.getMaterialById(materialId);
        QuizQuestion questionByToken = quizQuestionService.getQuestionByToken(quizAnswerDto.getQuestionToken());

        if (materialById == null || questionByToken == null) return "redirect:/error";
        if (!userGroupInfoService.getGroupInfoByUser(user).getGroupInfo().equals(materialById.getManagersGroupsSubjects().getGroupInfo())
                || !questionByToken.getClassMaterial().equals(materialById)) return "redirect:/error";

        model.addAttribute("question", questionByToken);
        model.addAttribute("full_name", fullName);
        model.addAttribute("material",questionByToken.getClassMaterial());
        model.addAttribute("quizAnswer",quizAnswerDto);
        if (questionByToken.getCorrectOption().equals(Integer.parseInt(quizAnswerDto.getSelectedOption()))) {
            model.addAttribute("correct", true);
        } else{
            model.addAttribute("correct", false);
        }

        return "user/dashboard/quiz/quizResultView";
    }
}
